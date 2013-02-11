package usp.ime.gclib.sensor.orientation;

import usp.ime.gclib.Device;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/**
 * This class creates a sensor listener to the motion and position sensors: magnetometer, accelerometer and gyroscope 
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 * @see SensorEventListener
 * 
 */
public class OrientationSensorListener implements SensorEventListener {

	private boolean isAcclerometerActivated;
	private boolean isGyroscopeActivated;
	private boolean isMagneticFieldActivated;
	private DeviceOrientation deviceOrientation;

	private SensorManager sensorManager;

	private ESensorDelayType delay;
	/**
	 * @param deviceOrientation it will be updated by the listener
	 */
	public OrientationSensorListener(DeviceOrientation deviceOrientation) {
		initVariables();
		this.deviceOrientation = deviceOrientation;
	}	
	
	/**
	 * @param device its attribute deviceOrientation will be updated by the listener
	 */
	public OrientationSensorListener(Device device) {
		initVariables();
		this.deviceOrientation = device.getDeviceOrientation();
	}

	private void initVariables(){
		this.deviceOrientation = null;
		this.delay = ESensorDelayType.NORMAL_DELAY;
		this.isAcclerometerActivated = false;
		this.isGyroscopeActivated = false;
		this.isMagneticFieldActivated = false;
	}
	/**
	 * Enable the sensor service by the context
	 * @param context
	 */
	public void enableSensorService(Context context){
		sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		enableDeviceSensors();
	}

	public ESensorDelayType getDelay() {
		return delay;
	}

	public void setDelay(ESensorDelayType delay) {
		this.delay = delay;
	}

	public boolean isAcclerometerActivated() {
		return isAcclerometerActivated;
	}

	public boolean isGyroscopeActivated() {
		return isGyroscopeActivated;
	}

	public boolean isMagneticFieldActivated() {
		return isMagneticFieldActivated;
	}

	private int getAndroidConstantOfSensorType(ESensorType sensorType){
		switch(sensorType){
			case ACCELEROMETER:
				return Sensor.TYPE_ACCELEROMETER;
			case GYROSCOPE:
				return Sensor.TYPE_GYROSCOPE;
			case MAGNETIC_FIELD:
				return Sensor.TYPE_MAGNETIC_FIELD;	
		}
		return -1;
	}

	private int getAndroidConstantOfSensorDelay(ESensorDelayType sensorDelayType){
		switch(sensorDelayType){
			case NORMAL_DELAY:
				return SensorManager.SENSOR_DELAY_NORMAL;
			case USER_INTERFACE_DELAY:
				return SensorManager.SENSOR_DELAY_UI;
			case GAME_DELAY:
				return SensorManager.SENSOR_DELAY_GAME;
			case WITHOUT_DELAY:
				return SensorManager.SENSOR_DELAY_FASTEST;
		}
		return -1;
	}

	private void updateControlVariables(ESensorType sensorType, boolean isEnable){
		switch(sensorType){
			case ACCELEROMETER:
				this.isAcclerometerActivated = true;
			case GYROSCOPE:
				this.isGyroscopeActivated = true;
			case MAGNETIC_FIELD:
				this.isMagneticFieldActivated = true;	
			default:
				break;
		}
	}

	private void updateAllControlVariables(boolean isEnable){
		this.isAcclerometerActivated = isEnable;
		this.isGyroscopeActivated = isEnable;
		this.isMagneticFieldActivated = isEnable;
	}

	/**
	 * Enable sensor lister defined by sensor type and set the type of sample rate 
	 * @param sensorType
	 * @param sensorDelayType sample rate 
	 */
	protected void enableSensorListener(ESensorType sensorType, ESensorDelayType sensorDelayType) {

		int sensorTypeForAndoid = getAndroidConstantOfSensorType(sensorType);  
		int sensorDelayForAndoid = getAndroidConstantOfSensorDelay(sensorDelayType);

		updateControlVariables(sensorType, true);

		sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensorTypeForAndoid), sensorDelayForAndoid);
	}

	/**
	 * Enable sensor lister defined by sensor type and set its sample rate
	 * @param sensorType
	 * @param microsecondsDelay sample rate in microseconds
	 */
	protected void enableSensorListener(ESensorType sensorType, int microsecondsDelay) {

		int sensorTypeForAndoid = getAndroidConstantOfSensorType(sensorType);  

		updateControlVariables(sensorType, true);

		sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensorTypeForAndoid), microsecondsDelay);
	}
	
	/**
	 * Enable all sensors
	 */
	protected void enableDeviceSensors(){
		for(int i=0; i< deviceOrientation.getSensors().length; i++)
			enableSensorListener(deviceOrientation.getSensors()[i], delay);
		//enableAllSensorListener(delay);
	}
	/**
	 * Enable a sensor list
	 * @param sensors
	 */
	protected void enableDeviceSensors(ESensorType[] sensors){
		for(int i=0; i< sensors.length; i++)
			enableSensorListener(sensors[i], delay);
	}

	/**
	 * Disable all sensors
	 */
	protected void disableDeviceSensors(){
		for(int i=0; i< deviceOrientation.getSensors().length; i++)
			disableSensorListener(deviceOrientation.getSensors()[i]);
	}
	
	/**
	 * Disable a sensors list
	 * @param sensors
	 */
	protected void disableDeviceSensors(ESensorType[] sensors){
		for(int i=0; i< sensors.length; i++)
			disableSensorListener(sensors[i]);
	}
	
	/**
	 * Enable all sensors listeners with a delay type 
	 * @param sensorDelayType
	 */
	protected void enableAllSensorListener(ESensorDelayType sensorDelayType) {

		int sensorDelayForAndoid = getAndroidConstantOfSensorDelay(sensorDelayType);

		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorDelayForAndoid);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), sensorDelayForAndoid);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensorDelayForAndoid);

		updateAllControlVariables(true);

	}

	/**
	 * Disable all sensors listeners
	 */
	protected void disableAllSensorListener() {
		sensorManager.unregisterListener(this);
		updateAllControlVariables(false);
	}

	/**
	 * Disable sensor listener defined by sensor type
	 * @param sensorType
	 */
	protected void disableSensorListener(ESensorType sensorType){
		int sensorTypeForAndoid = getAndroidConstantOfSensorType(sensorType);  
		sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(sensorTypeForAndoid));
		updateControlVariables(sensorType,false);
	}	

	/**
	 * Not implemented
	 */
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see SensorEventListener 
	 */
	public void onSensorChanged(SensorEvent event) {
		switch(event.sensor.getType()) {
		    case Sensor.TYPE_ACCELEROMETER:
		    	deviceOrientation.sensorManager(ESensorType.ACCELEROMETER, event.values, event.timestamp, this);
		        break;

		    case Sensor.TYPE_GYROSCOPE:
		    	deviceOrientation.sensorManager(ESensorType.GYROSCOPE, event.values, event.timestamp, this);
		        break;

		    case Sensor.TYPE_MAGNETIC_FIELD:
		    	deviceOrientation.sensorManager(ESensorType.MAGNETIC_FIELD, event.values, event.timestamp, this);
		        break;
		    default:
		    	break;
		}
	}
}
