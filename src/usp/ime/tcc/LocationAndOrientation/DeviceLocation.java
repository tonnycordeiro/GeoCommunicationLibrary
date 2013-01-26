package usp.ime.tcc.LocationAndOrientation;

import usp.ime.tcc.Auxiliaries.Device;
import android.content.Context;

import com.example.communicationbyorientation.LocationGpsListener;

public class DeviceLocation {
	
	private double latitude;
	private double longitude;
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
	
	public void enableLocationListener(Context context, int timeWait, int minDist){
		this.locationGpsListener.enableLocationListener(context, timeWait, minDist);
	}
	
	public void disableLocationListener(){
		this.locationGpsListener.disableLocationListener();
	}	
}
