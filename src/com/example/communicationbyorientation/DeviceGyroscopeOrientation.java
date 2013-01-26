package com.example.communicationbyorientation;

import javax.vecmath.Vector3f;

import usp.ime.tcc.Auxiliaries.Device;
import usp.ime.tcc.LocationAndOrientation.DeviceOrientation;
import android.content.Context;
import android.hardware.SensorManager;
import android.util.FloatMath;

public class DeviceGyroscopeOrientation extends DeviceOrientation{

	// angular speeds from gyro
	private float[] gyro;
	
	// rotation matrix from gyro data
	private float[] gyroMatrix;

	// orientation angles from gyro matrix
	private float[] orientation;
	
	private boolean isGyroOrientationDefined;
	
	private DeviceCompassOrientation compassOrientation;

	public static final float EPSILON = 0.000000001f;
	
	private static final float NS2S = 1.0f / 1000000000.0f;
	
	private float gyroTimestamp;
	
	private boolean updateWithCompassOrientation;
	
	private boolean disableCompassFilterAfterFirstUse;
	
	public DeviceGyroscopeOrientation(Device device) {
		super(device);
		gyro = new float[3];
		
		// rotation matrix from gyro data
		gyroMatrix = new float[9];
		
		// orientation angles from gyro matrix
		orientation = new float[3];
		
		compassOrientation = new DeviceCompassOrientation(device);
		this.disableCompassFilterAfterFirstUse = false;
		
		preparationForOrientationObtaining();
	}
	
	@Override
	public float[] getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(float[] gyroOrientation) {
		this.orientation = gyroOrientation;
	}
	
	public float[] getGyroMatrix() {
		return gyroMatrix;
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

	public void preparationForOrientationObtaining(){
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
	public void sensorManager(SensorType sensorType, float[] sample, long timestampSample) {
		switch(sensorType) {
		    case ACCELEROMETER:
		    case MAGNETIC_FIELD:
		    	compassOrientation.sensorManager(sensorType, sample, timestampSample);
		        break;
		        
		    case GYROSCOPE:
		        // process gyro data
		    	//TODO: alterar no 2.2
		    	gyroFunction(sample, timestampSample);
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
	
	private void gyroFunction(float[] gyroSample, long timestampSample) {
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
	        	this.getCompassOrientation().disableSensors();
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
	
	public void renewOrientationWithCompass(){
		if(!this.getListener().isAcclerometerActivated()){
			this.getListener().enableSensorListener(SensorType.ACCELEROMETER, SensorDelayType.GAME_DELAY);
		}
		if(!this.getListener().isMagneticFieldActivated()){
			this.getListener().enableSensorListener(SensorType.MAGNETIC_FIELD, SensorDelayType.GAME_DELAY);
		}
		
		this.updateWithCompassOrientation = true;
	}
	
	public void disableSensors(){
		compassOrientation.disableSensors();
		this.getListener().disableSensorListener(SensorType.GYROSCOPE);
	}
	
	public void enableSensors(){
		this.getListener().enableSensorListener(SensorType.GYROSCOPE, SensorDelayType.GAME_DELAY);
	}
	
	@Override
	public void enableSensorListener(Context context){
		/*super.enableSensorService(context);*/
		compassOrientation.enableSensorListener(context);
		enableSensors();
	}	
	
}
