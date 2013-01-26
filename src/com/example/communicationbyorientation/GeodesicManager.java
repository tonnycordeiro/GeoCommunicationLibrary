package com.example.communicationbyorientation;

import org.geotools.referencing.GeodesicPosition;
import org.geotools.referencing.GeodeticCalculator;

import usp.ime.tcc.Auxiliaries.Device;

public class GeodesicManager {

	public static double getDistanceBetween(Device dev1, Device dev2){
		GeodeticCalculator gcTarget = new GeodeticCalculator();
		gcTarget.setStartingGeographicPoint(dev1.getLongitude(),dev1.getLatitude());
		gcTarget.setDestinationGeographicPoint(dev2.getLongitude(),dev2.getLatitude());
		return gcTarget.getOrthodromicDistance();
	}
	
	public static double getAzimuthBetween(Device dev1, Device dev2){
		GeodeticCalculator gcTarget = new GeodeticCalculator();
		gcTarget.setStartingGeographicPoint(dev1.getLongitude(),dev1.getLatitude());
		gcTarget.setDestinationGeographicPoint(dev2.getLongitude(),dev2.getLatitude());
		return gcTarget.getAzimuth();
	}

	public static Device getHitDeviceVirtual(Device srcDevice, double azimuth, double distance){
		GeodesicPosition virtualPos = null;
		Device virtualDevice = new Device();
		
		GeodeticCalculator gcShoot = new GeodeticCalculator();
		gcShoot.setStartingGeographicPoint(srcDevice.getLongitude(),srcDevice.getLatitude());
		gcShoot.setDirection(azimuth,distance); //mesmo raio
		virtualPos = gcShoot.getDestinationGeographicPoint();
		
		virtualDevice.setLatitude(virtualPos.getY());
		virtualDevice.setLongitude(virtualPos.getX());
		
		return virtualDevice;
		
	}	
}
