package usp.ime.gclib.sensor.location;

import java.io.Serializable;

/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class DeviceLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private double latitude;
	private double longitude;
	/**
	 * radius of 68% confidence, in meters
	 */
	private float accuracy;
	private boolean isGpsEnable;
	private boolean isDefined;
	
	public DeviceLocation() {
		this.latitude = 0.0;
		this.longitude = 0.0;
		this.accuracy = 0f;
		this.isGpsEnable = false;
		this.isDefined = false;
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
		this.setDefined(true);
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
		this.setDefined(true);
	}
	
	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public boolean isDefined() {
		return isDefined;
	}

	public void setDefined(boolean isDefined) {
		this.isDefined = isDefined;
	}
	

}
