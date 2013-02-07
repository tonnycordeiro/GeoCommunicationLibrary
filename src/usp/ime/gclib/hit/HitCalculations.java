package usp.ime.gclib.hit;

import usp.ime.gclib.Device;
import usp.ime.gclib.sensor.orientation.AngleManager;


public class HitCalculations {
	private TargetRestrictions targetRestrictions;
	private ShootingRestrictions shootRestrictions;

	public TargetRestrictions getTargetRestrictions() {
		return targetRestrictions;
	}

	public void setTargetRestrictions(TargetRestrictions targetRestrictions) {
		this.targetRestrictions = targetRestrictions;
	}

	public ShootingRestrictions getShootingRestrictions() {
		return shootRestrictions;
	}

	public void setShootingRestrictions(ShootingRestrictions shootingRestrictions) {
		this.shootRestrictions = shootingRestrictions;
	}
	
	public boolean isValidTheDistanceBetweenDevices(Device srcDevice,Device dstDevice, double maximumDistanceSrcToDst){
		double distanceSrcToDst;
		distanceSrcToDst = GeodesicManager.getDistanceBetween(srcDevice, dstDevice);

		if(maximumDistanceSrcToDst > 0 && distanceSrcToDst>maximumDistanceSrcToDst)
			return false;
		return true;
	}

	public boolean isValidTheDistanceBetweenDevices(Device srcDevice,Device dstDevice){
		return isValidTheDistanceBetweenDevices(srcDevice,dstDevice,this.shootRestrictions.getMaximumDistanceSrcToDst());
	}
	
	public boolean hitTheDestinationByEndRangeOfShoot(double srcAzimuth, Device srcDevice, 
			 									Device dstDevice, Device virtualTarget, double radiusRangeOfShoot){
		double distanceSrcToDst, distanceDstToVitual;
		Device virtualTargetAux;
		
		distanceSrcToDst = GeodesicManager.getDistanceBetween(srcDevice, dstDevice);
		virtualTargetAux = GeodesicManager.getHitDeviceVirtual(srcDevice,srcAzimuth,distanceSrcToDst);
		
		if (virtualTarget != null){
			virtualTarget.getDeviceLocation().setLatitude(virtualTargetAux.getDeviceLocation().getLatitude());
			virtualTarget.getDeviceLocation().setLongitude(virtualTargetAux.getDeviceLocation().getLongitude());
		}
		
		distanceDstToVitual = GeodesicManager.getDistanceBetween(virtualTargetAux, dstDevice);
		
		return (radiusRangeOfShoot >= distanceDstToVitual);
	}
	
	
	public boolean hitTheDestinationByEndRangeOfShoot(double srcAzimuth, Device srcDevice, 
			 									Device dstDevice, Device virtualTarget){
		
		double radiusRange = this.getRadiusRange(dstDevice);
		return hitTheDestinationByEndRangeOfShoot(srcAzimuth, dstDevice, dstDevice, virtualTarget, radiusRange);
	}
	
	
	private double getRadiusRange(Device dstDevice) {
		double accuracy = 0;
		if(this.targetRestrictions.isUseGpsLocationAccuracy())
			accuracy = dstDevice.getDeviceLocation().getAccuracy();
		double radiusRange = this.targetRestrictions.getRadius() + accuracy
							+ (this.shootRestrictions.getWidthShoot()/2);
		return radiusRange;
	}

	public boolean hitTheDestinationByEndRangeOfShoot(double srcAzimuth, Device srcDevice,Device dstDevice){
		return hitTheDestinationByEndRangeOfShoot(srcAzimuth, dstDevice, dstDevice, null);
	}

	public boolean hitTheDestinationByOpeningAngleOfShoot(double srcAzimuth, Device srcDevice, 
				Device dstDevice, double openingAngleShoot){
		
		double srcAzimuthR, srcAzimuthL, shootAzimuth, currentOpeningAngleShoot;
		boolean hitLeft, hitRight;
		
		srcAzimuthR = AngleManager.addAngleBasedIn180Graus(srcAzimuth,openingAngleShoot/2);
		srcAzimuthL = AngleManager.addAngleBasedIn180Graus(srcAzimuth,-(openingAngleShoot/2));

		currentOpeningAngleShoot = this.shootRestrictions.getOpeningAngleShoot();
		this.shootRestrictions.setOpeningAngleShoot(0);
		
		hitLeft = hitTheDestination(srcAzimuthL, srcDevice,dstDevice, new Device());
		hitRight = hitTheDestination(srcAzimuthR, srcDevice,dstDevice, new Device());
		
		this.shootRestrictions.setOpeningAngleShoot(currentOpeningAngleShoot); 
		
		shootAzimuth = GeodesicManager.getAzimuthBetween(srcDevice, dstDevice);
		
		return ((hitRight || hitLeft) || 
				AngleManager.isAngleContainedInArcBasedIn180Graus(shootAzimuth,srcAzimuthR,srcAzimuthL));
	}


	public boolean hitTheDestinationByOpeningAngleOfShoot(double srcAzimuth, Device srcDevice, 
				Device dstDevice){
		
		return hitTheDestinationByOpeningAngleOfShoot(srcAzimuth,srcDevice,dstDevice,this.shootRestrictions.getOpeningAngleShoot());
	}
	
	public boolean hitTheDestination(double srcAzimuth, Device srcDevice, 
									 Device dstDevice, Device virtualTarget){

		double radiusRange = this.getRadiusRange(dstDevice);
		
		return( isValidTheDistanceBetweenDevices(srcDevice, dstDevice, this.shootRestrictions.getMaximumDistanceSrcToDst()) &&
				  ( hitTheDestinationByEndRangeOfShoot(srcAzimuth, srcDevice, dstDevice, virtualTarget, radiusRange) 
					||
				  (this.shootRestrictions.getOpeningAngleShoot()> 0 && 
				     hitTheDestinationByOpeningAngleOfShoot(srcAzimuth,srcDevice,dstDevice,this.shootRestrictions.getOpeningAngleShoot()))
				  )
			  );
	}
	
	public boolean hitTheDestination(double srcAzimuth, Device srcDevice, 
			 Device dstDevice){

		return hitTheDestination(srcAzimuth,srcDevice,dstDevice,null);
	}
	
}
	
