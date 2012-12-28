package usp.ime.tcc.LocationAndOrientation;

import java.io.Serializable;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class DeviceLocation implements LocationListener, Serializable {
	
	private static final long serialVersionUID = 4L;
	
	private double latitude;
	private double longitude;
	private boolean isGpsEnable = false;
	private Object waitGps = new Object();
	
	/*TODO: separar em classe específica para Android*/
	/*AndroidLocation al*/
	private LocationManager lm;
	private Context context;
	
	public DeviceLocation(Context context) {
		this.context = context;
		this.latitude = 0.0;
		this.longitude = 0.0;
	}
	
	public boolean gpsIsReady(){
		return this.isGpsEnable;
	}
	
	public void waitGps(){
		try {
			waitGps.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void notifyGps(){
		waitGps.notify();
	}
	
	public void enableLocationListener(int timeWait, int minDist){
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeWait, minDist, this);
	}

	public void disableLocationListener(){
		lm.removeUpdates(this);
		lm = null;
	}
	
	public void onLocationChanged(Location location) {
		isGpsEnable = true;
		notifyGps();
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	public void onProviderDisabled(String provider) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

}
