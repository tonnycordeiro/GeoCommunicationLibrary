package usp.ime.gclib.hit;

import usp.ime.gclib.hit.example.Device;


public class HitCalculations {
	private TargetRestrictions targetRestrictions;
	private ShootingRestrictions shootRestrictions;

	public TargetRestrictions getShootRestrictions() {
		return targetRestrictions;
	}

	public void setShootRestrictions(TargetRestrictions shootRestrictions) {
		this.targetRestrictions = shootRestrictions;
	}

	public ShootingRestrictions getTargetRestrictions() {
		return shootRestrictions;
	}

	public void setTargetRestrictions(ShootingRestrictions targetRestrictions) {
		this.shootRestrictions = targetRestrictions;
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
			 									Device dstDevice, Device virtualTarget,double radiusRangeOfShoot){
		double distanceSrcToDst, distanceDstToVitual;
		Device virtualTargetAux;
		
		distanceSrcToDst = GeodesicManager.getDistanceBetween(srcDevice, dstDevice);
		virtualTargetAux = GeodesicManager.getHitDeviceVirtual(srcDevice,srcAzimuth,distanceSrcToDst);
		
		if (virtualTarget != null){
			virtualTarget.setLatitude(virtualTargetAux.getLatitude());
			virtualTarget.setLongitude(virtualTargetAux.getLongitude());
		}
		
		distanceDstToVitual = GeodesicManager.getDistanceBetween(virtualTargetAux, dstDevice);
		
		return (radiusRangeOfShoot >= distanceDstToVitual);
	}
	
	
	public boolean hitTheDestinationByEndRangeOfShoot(double srcAzimuth, Device srcDevice, 
			 									Device dstDevice, Device virtualTarget){
		double radiusRange = this.targetRestrictions.getRadiusRangeOfShoot() + this.targetRestrictions.getGpsLocationAccuracy()
							+ (this.shootRestrictions.getWidthShoot()/2);
		return hitTheDestinationByEndRangeOfShoot(srcAzimuth, dstDevice, dstDevice, virtualTarget, radiusRange);
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

		return(  isValidTheDistanceBetweenDevices(srcDevice, dstDevice,this.shootRestrictions.getMaximumDistanceSrcToDst()) &&
				  ( hitTheDestinationByEndRangeOfShoot(srcAzimuth,srcDevice,dstDevice,virtualTarget,
						  this.targetRestrictions.getRadiusRangeOfShoot()+(this.shootRestrictions.getWidthShoot()/2)
						  	+this.targetRestrictions.getGpsLocationAccuracy()) 
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
	
