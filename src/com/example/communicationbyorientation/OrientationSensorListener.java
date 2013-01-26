package com.example.communicationbyorientation;

import java.io.Serializable;

import usp.ime.tcc.LocationAndOrientation.DeviceOrientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationSensorListener implements SensorEventListener, Serializable{

	private static final long serialVersionUID = 6L;

	private boolean isAcclerometerActivated;
	private boolean isGyroscopeActivated;
	private boolean isMagneticFieldActivated;
	private DeviceOrientation deviceOrientation;
	
	private SensorManager sensorManager;
	
	public OrientationSensorListener(DeviceOrientation deviceOrientation) {
		this.deviceOrientation = deviceOrientation;
		this.isAcclerometerActivated = false;
		this.isGyroscopeActivated = false;
		this.isMagneticFieldActivated = false;
	}
	
	/*Obrigatório chamar esse método*/
	public void enableSensorService(Context context){
		sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);		
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

	private int getAndroidConstantOfSensorType(SensorType sensorType){
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
	
	private int getAndroidConstantOfSensorDelay(SensorDelayType sensorDelayType){
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
	
	private void updateControlVariables(SensorType sensorType, boolean isEnable){
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
	
	public void enableSensorListener(SensorType sensorType, SensorDelayType sensorDelayType) {
		
		int sensorTypeForAndoid = getAndroidConstantOfSensorType(sensorType);  
		int sensorDelayForAndoid = getAndroidConstantOfSensorDelay(sensorDelayType);
		
		updateControlVariables(sensorType, true);
		
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensorTypeForAndoid), sensorDelayForAndoid);
	}
	
	public void enableSensorListener(SensorType sensorType, int microsecondsDelay) {
		
		int sensorTypeForAndoid = getAndroidConstantOfSensorType(sensorType);  

		updateControlVariables(sensorType, true);
		
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(sensorTypeForAndoid), microsecondsDelay);
	}
	
	public void enableAllSensorListener(SensorDelayType sensorDelayType) {
		
		int sensorDelayForAndoid = getAndroidConstantOfSensorDelay(sensorDelayType);
		
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorDelayForAndoid);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), sensorDelayForAndoid);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensorDelayForAndoid);
		
		updateAllControlVariables(true);
		
	}

	public void disableAllSensorListener() {
		sensorManager.unregisterListener(this);
		updateAllControlVariables(false);
	}
	
	public void disableSensorListener(SensorType sensorType){
		int sensorTypeForAndoid = getAndroidConstantOfSensorType(sensorType);  
		sensorManager.unregisterListener(this,sensorManager.getDefaultSensor(sensorTypeForAndoid));
		updateControlVariables(sensorType,false);
	}	

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	public void onSensorChanged(SensorEvent event) {
		switch(event.sensor.getType()) {
		    case Sensor.TYPE_ACCELEROMETER:
		    	deviceOrientation.sensorManager(SensorType.ACCELEROMETER, event.values, event.timestamp);
		        break;
		 
		    case Sensor.TYPE_GYROSCOPE:
		    	deviceOrientation.sensorManager(SensorType.GYROSCOPE, event.values, event.timestamp);
		        break;
		 
		    case Sensor.TYPE_MAGNETIC_FIELD:
		    	deviceOrientation.sensorManager(SensorType.MAGNETIC_FIELD, event.values, event.timestamp);
		        break;
		    default:
		    	break;
		}
	}
}
