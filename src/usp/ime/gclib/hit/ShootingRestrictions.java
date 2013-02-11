package usp.ime.gclib.hit;

import java.io.Serializable;
/**
 * This class creates a shooting restrictions to a Device that send a message to another Device 
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 * @see DeviceOrientation, DeviceCompassOrientation
 * 
 */
public class ShootingRestrictions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * range -180 to 180 degrees
	 */
	private double openingAngleShoot;
	/**
	 * meter
	 */
	private double widthShoot;
	/**
	 * meter
	 */
	private double maximumDistanceSrcToDst;
	
	public static final double OPENING_ANGLE_SHOOT_DEFAULT = 0.0;
	public static final double WIDTH_SHOOT_DEFAULT = 0.0;
	public static final double MAXIMUM_DISTANCE_SRC_TO_DST_DEFAULT = 0.0;
	
	public ShootingRestrictions() {
		this.openingAngleShoot = OPENING_ANGLE_SHOOT_DEFAULT;
		this.widthShoot = WIDTH_SHOOT_DEFAULT;
		this.maximumDistanceSrcToDst = MAXIMUM_DISTANCE_SRC_TO_DST_DEFAULT;
	}
	/**
	 * 
	 * @param openingAngleShoot opening angle shoot: range -180 to 180 degrees
	 * All destinations between the two orientation vector projected with an opening angle will be hit by the source
	 * @param widthShoot width shoot in meters. 
	 * All destinations between the two parallel orientation vector projected by the shoot will be hit by the source  
	 * @param maximumDistanceSrcToDst maximum distance from source to destination in meters
	 */
	public ShootingRestrictions(double openingAngleShoot,
						double widthShoot, double maximumDistanceSrcToDst) {
		this.openingAngleShoot = openingAngleShoot;
		this.widthShoot = widthShoot;
		this.maximumDistanceSrcToDst = maximumDistanceSrcToDst;
	}

	public double getOpeningAngleShoot() {
		return openingAngleShoot;
	}

	public void setOpeningAngleShoot(double openingAngleShoot) {
		openingAngleShoot = Math.abs(openingAngleShoot)%360;
		this.openingAngleShoot = openingAngleShoot;
	}

	public double getWidthShoot() {
		return widthShoot;
	}

	public void setWidthShoot(double widthShoot) {
		this.widthShoot = widthShoot;
	}

	public double getMaximumDistanceSrcToDst() {
		return maximumDistanceSrcToDst;
	}

	public void setMaximumDistanceSrcToDst(double maximumDistanceSrcToDst) {
		this.maximumDistanceSrcToDst = (this.maximumDistanceSrcToDst<0?MAXIMUM_DISTANCE_SRC_TO_DST_DEFAULT:maximumDistanceSrcToDst);
	}	
}
