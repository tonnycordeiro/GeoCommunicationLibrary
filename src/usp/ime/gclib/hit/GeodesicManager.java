package usp.ime.gclib.hit;

import org.geotools.referencing.GeodesicPosition;
import org.geotools.referencing.GeodeticCalculator;

import usp.ime.gclib.Device;
import usp.ime.gclib.sensor.location.DeviceLocation;
/**
 * This class manage geodesic calculations
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 * 
 */
public class GeodesicManager {
	/**
	 * Get the distance between dev1 and dev2
	 * @param dev1 {@link Device} with {@link DeviceLocation} not null
	 * @param dev2 {@link Device} with {@link DeviceLocation} not null
	 * @return distance in meters 
	 */
	public static double getDistanceBetween(Device dev1, Device dev2){
		GeodeticCalculator gcTarget = new GeodeticCalculator();
		gcTarget.setStartingGeographicPoint(dev1.getDeviceLocation().getLongitude(),dev1.getDeviceLocation().getLatitude());
		gcTarget.setDestinationGeographicPoint(dev2.getDeviceLocation().getLongitude(),dev2.getDeviceLocation().getLatitude());
		return gcTarget.getOrthodromicDistance();
	}
	
	/**
	 * Get the azimuth between dev1 and dev2
	 * @param dev1 {@link Device} with {@link DeviceLocation} not null
	 * @param dev2 {@link Device} with {@link DeviceLocation} not null
	 * @return azimuth in degrees 
	 */
	public static double getAzimuthBetween(Device dev1, Device dev2){
		GeodeticCalculator gcTarget = new GeodeticCalculator();
		gcTarget.setStartingGeographicPoint(dev1.getDeviceLocation().getLongitude(),dev1.getDeviceLocation().getLatitude());
		gcTarget.setDestinationGeographicPoint(dev2.getDeviceLocation().getLongitude(),dev2.getDeviceLocation().getLatitude());
		return gcTarget.getAzimuth();
	}

	/**
	 * Get the destination device  
	 * @param srcDevice Device with deviceLocation not null
	 * @param azimuth degree
	 * @param distance meters
	 * @return destination device
	 */
	public static Device getHitDeviceVirtual(Device srcDevice, double azimuth, double distance){
		GeodesicPosition virtualPos = null;
		Device virtualDevice = new Device();
		
		GeodeticCalculator gcShoot = new GeodeticCalculator();
		gcShoot.setStartingGeographicPoint(srcDevice.getDeviceLocation().getLongitude(),srcDevice.getDeviceLocation().getLatitude());
		gcShoot.setDirection(azimuth,distance); //mesmo raio
		virtualPos = gcShoot.getDestinationGeographicPoint();
		
		virtualDevice.getDeviceLocation().setLatitude(virtualPos.getY());
		virtualDevice.getDeviceLocation().setLongitude(virtualPos.getX());
		
		return virtualDevice;
		
	}	
}
