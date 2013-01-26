package com.example.communicationbyorientation;

import java.io.Serializable;


public class TargetRestrictions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	double radiusRangeOfShoot;
	float gpsLocationAccuracy;
	
	public static final double RADIUS_RANGE_OF_SHOOT_DEFAULT = 1.0; 
	public static final float GPS_LOCATION_ACCURACY_DEFAULT = 0.0f;

	
	public TargetRestrictions() {
		this.radiusRangeOfShoot = RADIUS_RANGE_OF_SHOOT_DEFAULT;
		this.gpsLocationAccuracy = GPS_LOCATION_ACCURACY_DEFAULT;
	}
	
	public TargetRestrictions(double radiusRangeOfShoot, float gpsLocationAccuracy) {
		this.radiusRangeOfShoot = radiusRangeOfShoot;
		this.gpsLocationAccuracy = gpsLocationAccuracy;
	}

	public float getGpsLocationAccuracy() {
		return gpsLocationAccuracy;
	}

	public void setGpsLocationAccuracy(float gpsLocationAccuracy) {
		this.gpsLocationAccuracy = (this.gpsLocationAccuracy<0?GPS_LOCATION_ACCURACY_DEFAULT:gpsLocationAccuracy);
	}

	public double getRadiusRangeOfShoot() {
		return radiusRangeOfShoot;
	}

	public void setRadiusRangeOfShoot(double radiusRangeOfShoot) {
		this.radiusRangeOfShoot = (this.radiusRangeOfShoot<0?RADIUS_RANGE_OF_SHOOT_DEFAULT:radiusRangeOfShoot);
	}


	
	
}
