package usp.ime.gclib.sensor.location;

import usp.ime.gclib.device.Device;
import android.content.Context;


public class DeviceLocation {
	
	private double latitude;
	private double longitude;
	private float accuracy;
	private boolean isGpsEnable = false;
	
	private Device device;
	
	private LocationGpsListener locationGpsListener;
	
	public DeviceLocation(Device device) {
		this.latitude = 0.0;
		this.longitude = 0.0;
		this.device = device;
		locationGpsListener = new LocationGpsListener(this);
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
		device.setLatitude(this.latitude);
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
		this.device.setLongitude(this.latitude);
	}
	
	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
		this.device.setLocationAccuracy(accuracy);
	}

	public void enableLocationListener(Context context, int timeWait, int minDist){
		this.locationGpsListener.enableLocationListener(context, timeWait, minDist);
	}
	
	public void disableLocationListener(){
		this.locationGpsListener.disableLocationListener();
	}	
}
