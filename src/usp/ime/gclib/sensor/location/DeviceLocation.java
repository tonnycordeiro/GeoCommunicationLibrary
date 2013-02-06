package usp.ime.gclib.sensor.location;

import java.io.Serializable;

public class DeviceLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double latitude;
	private double longitude;
	private float accuracy;
	private boolean isGpsEnable = false;
	
	public DeviceLocation() {
		this.latitude = 0.0;
		this.longitude = 0.0;
		this.accuracy = 0f;
	}

	public DeviceLocation(boolean activeListener) {
		this.latitude = 0.0;
		this.longitude = 0.0;
		this.accuracy = 0f;
	}
		
	
	public boolean isGpsEnable() {
		return isGpsEnable;
	}

	public void setGpsEnable(boolean isGpsEnable) {
		this.isGpsEnable = isGpsEnable;
	}

	public boolean gpsIsReady(){
		return this.isGpsEnable;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

}
