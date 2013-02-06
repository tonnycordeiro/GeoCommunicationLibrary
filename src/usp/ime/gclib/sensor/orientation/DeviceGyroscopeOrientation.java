package usp.ime.gclib.sensor.orientation;

import javax.vecmath.Vector3f;

import android.hardware.SensorManager;
import android.util.FloatMath;

public class DeviceGyroscopeOrientation extends DeviceOrientation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8089121916401443406L;

	// angular speeds from gyro
	private float[] gyro;
	
	// rotation matrix from gyro data
	private float[] gyroMatrix;

	private boolean isGyroOrientationDefined;
	
	private DeviceCompassOrientation compassOrientation;

	private static final float EPSILON = 0.000000001f;
	
	private static final float NS2S = 1.0f / 1000000000.0f;
	
	private float gyroTimestamp;
	
	private boolean updateWithCompassOrientation;
	
	private boolean disableCompassFilterAfterFirstUse;
	
	public DeviceGyroscopeOrientation() {
		super();
		gyro = new float[3];
		
		// rotation matrix from gyro data
		gyroMatrix = new float[9];
		
		// orientation angles from gyro matrix
		orientation = new float[3];
		
		compassOrientation = new DeviceCompassOrientation();
		this.disableCompassFilterAfterFirstUse = false;
	
		sensors = new ESensorType[3];
		sensors[0] = ESensorType.ACCELEROMETER;
		sensors[1] = ESensorType.GYROSCOPE;
		sensors[2] = ESensorType.MAGNETIC_FIELD;
		
		prepareForOrientationObtaining();
	}
	
	@Override
	public float[] getOrientation() {
		return orientation;
	}

	public void setGyroMatrix(float[] gyroMatrix) {
		this.gyroMatrix = gyroMatrix;
	}
	
	public DeviceCompassOrientation getCompassOrientation() {
		return compassOrientation;
	}

	public void setCompassOrientation(DeviceCompassOrientation compassOrientation) {
		this.compassOrientation = compassOrientation;
	}

	public boolean isGyroOrientationDefined() {
		return isGyroOrientationDefined;
	}

	public void setGyroOrientationDefined(boolean isGyroOrientationDefined) {
		this.isGyroOrientationDefined = isGyroOrientationDefined;
	}

	public void prepareForOrientationObtaining(){
		this.updateWithCompassOrientation = true;
		this.isGyroOrientationDefined= false;
		
		orientation[0] = 0.0f;
		orientation[1] = 0.0f;
		orientation[2] = 0.0f;
		 
		gyroMatrix[0] = 1.0f; gyroMatrix[1] = 0.0f; gyroMatrix[2] = 0.0f;
		gyroMatrix[3] = 0.0f; gyroMatrix[4] = 1.0f; gyroMatrix[5] = 0.0f;
		gyroMatrix[6] = 0.0f; gyroMatrix[7] = 0.0f; gyroMatrix[8] = 1.0f;
	}
	
	@Override
	public void sensorManager(ESensorType sensorType, float[] sample, long timestampSample, OrientationSensorListener listener) {
		switch(sensorType) {
		    case ACCELEROMETER:
		    case MAGNETIC_FIELD:
		    	compassOrientation.sensorManager(sensorType, sample, timestampSample, listener);
		        break;
		        
		    case GYROSCOPE:
		        // process gyro data
		    	//TODO: alterar no 2.2
		    	gyroFunction(sample, timestampSample,listener);
		        break;
		 
		    default:
		    	break;
	    }
	}
		
	private void getRotationVectorFromGyro(float[] gyroValues,
	                                       float[] deltaRotationVector,
	                                       float timeFactor){
	    float[] normValues = new float[3];
	    
	    Vector3f gyroValuesV3f = new Vector3f(gyroValues);
	    
	    // Calculate the angular speed of the sample
	    float omegaMagnitude = gyroValuesV3f.lengthSquared(); 
	        
	    // Normalize the rotation vector if it's big enough to get the axis
	    if(omegaMagnitude > EPSILON) {
	        normValues[0] = gyroValues[0] / omegaMagnitude;
	        normValues[1] = gyroValues[1] / omegaMagnitude;
	        normValues[2] = gyroValues[2] / omegaMagnitude;
	    }
	 
	    // Integrate around this axis with the angular speed by the timestep
	    // in order to get a delta rotation from this sample over the timestep
	    // We will convert this axis-angle representation of the delta rotation
	    // into a quaternion before turning it into the rotation matrix.
	    float thetaOverTwo = omegaMagnitude * timeFactor;
	    float sinThetaOverTwo = (float)FloatMath.sin(thetaOverTwo);
	    float cosThetaOverTwo = (float)FloatMath.cos(thetaOverTwo);
	    deltaRotationVector[0] = sinThetaOverTwo * normValues[0];
	    deltaRotationVector[1] = sinThetaOverTwo * normValues[1];
	    deltaRotationVector[2] = sinThetaOverTwo * normValues[2];
	    deltaRotationVector[3] = cosThetaOverTwo;
	}
	
	private void gyroFunction(float[] gyroSample, long timestampSample, OrientationSensorListener listener) {
	    // don't start until first accelerometer/magnetometer orientation has been acquired
	    if (!compassOrientation.isCompassOrientationDefined())
	        return;
	 
	    // initialization of the gyroscope based rotation matrix
	    if(updateWithCompassOrientation) {
	        float[] initMatrix = new float[9];
	        initMatrix = OrientatonManager.getRotationMatrixFromOrientation(compassOrientation.getOrientation());
	        
	        gyroMatrix = OrientatonManager.matrixMultiplication(gyroMatrix, initMatrix);
	        
	        this.updateWithCompassOrientation = false;
	        
	        if(this.disableCompassFilterAfterFirstUse)
	        	listener.disableDeviceSensors(this.getCompassOrientation().getSensors());
	    }
	 
	    // copy the new gyro values into the gyro array
	    // convert the raw gyro data into a rotation vector
	    float[] deltaVector = new float[4];
	    if(gyroTimestamp != 0) {
	        final float dT = (timestampSample - gyroTimestamp) * NS2S;
		    System.arraycopy(gyroSample, 0, gyro, 0, 3);
		    getRotationVectorFromGyro(gyro, deltaVector, dT / 2.0f);
	    }
	 
	    // measurement done, save current time for next interval
	    gyroTimestamp = timestampSample;
	 
	    // convert rotation vector into rotation matrix
	    float[] deltaMatrix = new float[9];
	    //TODO: adaptar para o 2.2
	    SensorManager.getRotationMatrixFromVector(deltaMatrix, deltaVector);
	
	    // apply the new rotation interval on the gyroscope based rotation matrix
	    gyroMatrix = OrientatonManager.matrixMultiplication(gyroMatrix, deltaMatrix);
	 
	    // get the gyroscope based orientation from the rotation matrix
	    SensorManager.getOrientation(gyroMatrix, orientation);
	    
	    this.isGyroOrientationDefined= true; 
	}	
	
	public void renewOrientationWithCompass(OrientationSensorListener listener){
		if(!listener.isAcclerometerActivated()){
			listener.enableSensorListener(ESensorType.ACCELEROMETER, ESensorDelayType.GAME_DELAY);
		}
		if(!listener.isMagneticFieldActivated()){
			listener.enableSensorListener(ESensorType.MAGNETIC_FIELD, ESensorDelayType.GAME_DELAY);
		}
		
		this.updateWithCompassOrientation = true;
	}
	
}
