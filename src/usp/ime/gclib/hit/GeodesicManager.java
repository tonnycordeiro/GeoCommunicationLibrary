package usp.ime.gclib.hit;

import org.geotools.referencing.GeodesicPosition;
import org.geotools.referencing.GeodeticCalculator;

import usp.ime.gclib.Device;

public class GeodesicManager {

	public static double getDistanceBetween(Device dev1, Device dev2){
		GeodeticCalculator gcTarget = new GeodeticCalculator();
		gcTarget.setStartingGeographicPoint(dev1.getDeviceLocation().getLongitude(),dev1.getDeviceLocation().getLatitude());
		gcTarget.setDestinationGeographicPoint(dev2.getDeviceLocation().getLongitude(),dev2.getDeviceLocation().getLatitude());
		return gcTarget.getOrthodromicDistance();
	}
	
	public static double getAzimuthBetween(Device dev1, Device dev2){
		GeodeticCalculator gcTarget = new GeodeticCalculator();
		gcTarget.setStartingGeographicPoint(dev1.getDeviceLocation().getLongitude(),dev1.getDeviceLocation().getLatitude());
		gcTarget.setDestinationGeographicPoint(dev2.getDeviceLocation().getLongitude(),dev2.getDeviceLocation().getLatitude());
		return gcTarget.getAzimuth();
	}

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
