package com.example.communicationbyorientation;

public class ShootRestrictions {
	double openingAngleShoot;
	double widthShoot;
	double maximumDistanceSrcToDst;
	
	public static final double OPENING_ANGLE_SHOOT_DEFAULT = 0.0;
	public static final double WIDTH_SHOOT_DEFAULT = 0.0;
	public static final double MAXIMUM_DISTANCE_SRC_TO_DST_DEFAULT = 0.0;
	
	public ShootRestrictions() {
		this.openingAngleShoot = OPENING_ANGLE_SHOOT_DEFAULT;
		this.widthShoot = WIDTH_SHOOT_DEFAULT;
		this.maximumDistanceSrcToDst = MAXIMUM_DISTANCE_SRC_TO_DST_DEFAULT;
	}
	
	public ShootRestrictions(double openingAngleShoot,
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
