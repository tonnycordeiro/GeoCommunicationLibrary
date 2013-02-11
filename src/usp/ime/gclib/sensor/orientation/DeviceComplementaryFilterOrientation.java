package usp.ime.gclib.sensor.orientation;

import java.util.Timer;
import java.util.TimerTask;

import usp.ime.gclib.Device;

/*Versão do Android >= 2.3*/
/**
 * This class creates a device orientation by the fusion of Compass Orientation and Gyroscope Orientation
 * It intends eliminate the error restricts to each sensor
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 * @see DeviceOrientation, DeviceCompassOrientation
 * 
 */

public class DeviceComplementaryFilterOrientation extends DeviceOrientation{

	private static final long serialVersionUID = -2077738169884031117L;

	private DeviceGyroscopeOrientation gyroOrientation;
	
	private Timer fuseTimer;
	
	/**
	 * Delay to update the orientation vector with current samples of sensor values  
	 */
	public static final long TIME_SAMPLE_DEFAULT = 30;
	/**
	 * filter coeficient default  
	 */
	public static final float FILTER_COEFICIENT_DEFAULT = 0.8f;
	
	/**
	 * The higher it is, the more Gyroscope orientation will be privileged
	 * The lower it is, the more Compass orientation will be privileged  
	 * It is necessary a tradeoff to choice the best value for an application 
	 */
	private float filter_coeficient = 0.8f;
	
	public DeviceComplementaryFilterOrientation(){
		super();
		this.filter_coeficient = FILTER_COEFICIENT_DEFAULT;
		initVariables();
	}
	
	/**
	 * 
	 * @param device
	 * @param filter_coeficient %
	 */
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
	
	/**
	 * Init the task to calculate the orientation vector 
	 * @param timeSample amount of time in milliseconds between subsequent executions. 
	 */
	public void startFilter(long timeSample){
		initCalculateTask(timeSample);
	}
	
	/**
	 * Init the task to calculate the orientation vector 
	 * @param timeSample amount of time in milliseconds between subsequent executions. 
	 */
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
