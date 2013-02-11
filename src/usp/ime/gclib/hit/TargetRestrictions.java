package usp.ime.gclib.hit;

import java.io.Serializable;

/**
 * This class creates a target restrictions to a Destination Device to verify if it can receive a message
 * @author Tonny
 *
 */
public class TargetRestrictions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * meter
	 */
	private double radius;
	private boolean useGpsLocationAccuracy;
	
	public static final double RADIUS_DEFAULT = 3.0; 
	public static final boolean USE_GPS_LOCATION_ACCURACY_DEFAULT = false;

	
	public TargetRestrictions() {
		this.radius = RADIUS_DEFAULT;
		this.useGpsLocationAccuracy = USE_GPS_LOCATION_ACCURACY_DEFAULT;
	}
	
	/**
	 * 
	 * @param radiusRangeOfShoot radius range in meters
	 * @param useGpsLocationAccuracy if it is true the accuracy will be considered as a range radius of the target 
	 */
	public TargetRestrictions(double radiusRangeOfShoot, boolean useGpsLocationAccuracy) {
		this.radius = radiusRangeOfShoot;
		this.useGpsLocationAccuracy = useGpsLocationAccuracy;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = (this.radius<0 ? RADIUS_DEFAULT : radius);
	}

	public boolean isUseGpsLocationAccuracy() {
		return useGpsLocationAccuracy;
	}

	public void setUseGpsLocationAccuracy(boolean useGpsLocationAccuracy) {
		this.useGpsLocationAccuracy = useGpsLocationAccuracy;
	}
	
}
