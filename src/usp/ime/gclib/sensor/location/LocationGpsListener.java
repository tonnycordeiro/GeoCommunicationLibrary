package usp.ime.gclib.sensor.location;

import usp.ime.gclib.device.Device;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationGpsListener implements LocationListener {

	private LocationManager lm;
	private DeviceLocation deviceLocation;
	private Location oldLocation;

	int limitPeriodToControlBetterLocation; //InMiliseconds

	public LocationGpsListener(DeviceLocation deviceLocation){
		initVariables();
		this.deviceLocation = deviceLocation;
	}

	public LocationGpsListener(Device device){
		initVariables();
		this.deviceLocation = device.getDeviceLocation();
	}
	
	private void initVariables(){
		this.deviceLocation = null;
		this.limitPeriodToControlBetterLocation = 0;
		this.oldLocation = null;
	}
	
	public boolean isGpsEnable() {
		return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public int getLimitPeriodToControlBetterLocation() {
		return limitPeriodToControlBetterLocation;
	}

	public void setLimitPeriodToControlBetterLocation(int limitPeriodToControlBetterLocation) {
		this.limitPeriodToControlBetterLocation = limitPeriodToControlBetterLocation;
	}

	public void enableLocationService(Context context, int timeWait, int minDist){
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeWait, minDist, this);
	}
	
	protected void disableLocationListener(){
		lm.removeUpdates(this);
		/*lm = null;*/
	}

	public void onLocationChanged(Location location) {
		if(isBetterLocation(location, this.oldLocation, this.limitPeriodToControlBetterLocation)){
			deviceLocation.setLatitude(location.getLatitude());
			deviceLocation.setLongitude(location.getLongitude());
			deviceLocation.setGpsEnable(true);
			deviceLocation.setAccuracy(location.getAccuracy());
		}
		this.oldLocation = location;
	}

    /** Source code modified basing in the Android's documentation   
     * Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
	public static boolean isBetterLocation(Location location, Location currentBestLocation, int limitPeriodInMiliseconds) {
		
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > limitPeriodInMiliseconds;
		boolean isSignificantlyOlder = timeDelta < -limitPeriodInMiliseconds;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}	

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
