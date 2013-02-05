package usp.ime.gclib.sensor.orientation;

import java.util.Timer;
import java.util.TimerTask;

import usp.ime.gclib.device.Device;
import android.content.Context;

/*Versão do Android >= 2.3*/
public class DeviceComplementaryFilterOrientation extends DeviceOrientation{

	// final orientation angles from sensor fusion
	private float[] orientation;

	private DeviceGyroscopeOrientation gyroOrientation;
	
	private Timer fuseTimer;
	
	public static final long TIME_SAMPLE_DEFAULT = 30;
	
	public static final float FILTER_COEFICIENT_DEFAULT = 0.8f;
	
	private float filter_coeficient = 0.8f;
	
	public DeviceComplementaryFilterOrientation(Device device){
		super();
		this.filter_coeficient = FILTER_COEFICIENT_DEFAULT;
		initVariables();
	}
	
	public DeviceComplementaryFilterOrientation(Device device, float filter_coeficient){
		super();
		this.filter_coeficient = filter_coeficient;
		initVariables();
	}

	public float getFilter_coeficient() {
		return filter_coeficient;
	}

	public void setFilter_coeficient(float filter_coeficient) {
		this.filter_coeficient = filter_coeficient;
	}
	
	@Override
	public float[] getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(float[] fusedOrientation) {
		this.orientation = fusedOrientation;
	}
	
	private void initVariables(){
		
		// orientation angles from gyro matrix
		gyroOrientation = new DeviceGyroscopeOrientation();
		
		// final orientation angles from sensor fusion
		orientation = new float[3];
		
		sensors = new ESensorType[3];
		sensors[0] = ESensorType.ACCELEROMETER;
		sensors[1] = ESensorType.GYROSCOPE;
		sensors[2] = ESensorType.MAGNETIC_FIELD;

	}
	
	public void startFilter(long timeSample){
		initCalculateTask(timeSample);
	}
	
	public void initCalculateTask(long timeSample){
		fuseTimer = new Timer();
		fuseTimer.scheduleAtFixedRate(new CalculateFusedOrientationTask(),
			       1000, timeSample);
	}

	class CalculateFusedOrientationTask extends TimerTask {
		
	    public void run() {
	    	if(gyroOrientation.isGyroOrientationDefined()){
		        float oneMinusCoeff = 1.0f - filter_coeficient;
		        orientation[0] =
		            filter_coeficient * gyroOrientation.getOrientation()[0]
		            + oneMinusCoeff * gyroOrientation.getCompassOrientation().getOrientation()[0];
		 
		        orientation[1] =
		            filter_coeficient * gyroOrientation.getOrientation()[1]
		            + oneMinusCoeff * gyroOrientation.getCompassOrientation().getOrientation()[1];
		 
		        orientation[2] =
		            filter_coeficient * gyroOrientation.getOrientation()[2]
		            + oneMinusCoeff * gyroOrientation.getCompassOrientation().getOrientation()[2];
	
		        // overwrite gyro matrix and orientation with fused orientation
		        // to comensate gyro drift
		        gyroOrientation.setGyroMatrix(OrientatonManager.getRotationMatrixFromOrientation(orientation));
		        gyroOrientation.setOrientation(orientation);
		        //System.arraycopy(fusedOrientation, 0, gyroOrientation, 0, 3);
	    	}
	    }
	}	
	
	@Override
	public void sensorManager(ESensorType sensorType, float[] sample, long timestampSample,OrientationSensorListener listener) {
		this.gyroOrientation.sensorManager(sensorType, sample, timestampSample,listener);
	}
		
}
