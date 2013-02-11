package usp.ime.gclib.hit;

import usp.ime.gclib.Device;
import usp.ime.gclib.sensor.location.DeviceLocation;

/**
 * This class calculate if a device hit another device
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 * @see TargetRestrictions, ShootingRestrictions
 * 
 */
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
	
	/**
	 * Verify if the distance between srcDevice and dstDevice is less then the maximumDistanceSrcToDst  
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @param maximumDistanceSrcToDst maximum distance from source to destination in meters
	 * @return if the distance between srcDevice and dstDevice is less then the maximumDistanceSrcToDst
	 */
	public boolean isValidTheDistanceBetweenDevices(Device srcDevice,Device dstDevice, double maximumDistanceSrcToDst){
		double distanceSrcToDst;
		distanceSrcToDst = GeodesicManager.getDistanceBetween(srcDevice, dstDevice);

		if(maximumDistanceSrcToDst > 0 && distanceSrcToDst>maximumDistanceSrcToDst)
			return false;
		return true;
	}

	/**
	 * Verify if the distance between srcDevice and dstDevice is less then the maximum distance defined in {@link ShootingRestrictions}   
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @return if the distance between srcDevice and dstDevice is less then the maximum distance defined in {@link ShootingRestrictions}
	 */
	public boolean isValidTheDistanceBetweenDevices(Device srcDevice,Device dstDevice){
		return isValidTheDistanceBetweenDevices(srcDevice,dstDevice,this.shootRestrictions.getMaximumDistanceSrcToDst());
	}
	
	/**
	 * Verify if the srcDevice hit the dstDevice with a radius range
	 * @param srcAzimuth degree by end range of shoot 
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @param virtualTarget Destination {@link Device} returned
	 * @param radiusRange meters
	 * @return if the srcDevice hit the dstDevice with a radius range
	 */
	public boolean hitTheDestinationByRadiusRange(double srcAzimuth, Device srcDevice, 
			 									Device dstDevice, Device virtualTarget, double radiusRange){
		double distanceSrcToDst, distanceDstToVitual;
		Device virtualTargetAux;
		
		distanceSrcToDst = GeodesicManager.getDistanceBetween(srcDevice, dstDevice);
		virtualTargetAux = GeodesicManager.getHitDeviceVirtual(srcDevice,srcAzimuth,distanceSrcToDst);
		
		if (virtualTarget != null){
			virtualTarget.getDeviceLocation().setLatitude(virtualTargetAux.getDeviceLocation().getLatitude());
			virtualTarget.getDeviceLocation().setLongitude(virtualTargetAux.getDeviceLocation().getLongitude());
		}
		
		distanceDstToVitual = GeodesicManager.getDistanceBetween(virtualTargetAux, dstDevice);
		
		return (radiusRange >= distanceDstToVitual);
	}
	
	/**
	 * Verify if the srcDevice hit the dstDevice with a radius defined by default
	 * @param srcAzimuth degree by end range of shoot 
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @param virtualTarget Destination {@link Device} returned
	 * @return if the srcDevice hit the dstDevice with a radius defined by default
	 */
	public boolean hitTheDestinationByRadiusRange(double srcAzimuth, Device srcDevice, 
			 									Device dstDevice, Device virtualTarget){
		
		double radiusRange = this.getRadiusRange(dstDevice);
		return hitTheDestinationByRadiusRange(srcAzimuth, dstDevice, dstDevice, virtualTarget, radiusRange);
	}
	
	private double getRadiusRange(Device dstDevice) {
		double accuracy = 0;
		if(this.targetRestrictions.isUseGpsLocationAccuracy())
			accuracy = dstDevice.getDeviceLocation().getAccuracy();
		double radiusRange = this.targetRestrictions.getRadius() + accuracy
							+ (this.shootRestrictions.getWidthShoot()/2);
		return radiusRange;
	}

	/**
	 * Verify if the srcDevice hit the dstDevice with a radius defined by default
	 * @param srcAzimuth degree by end range of shoot 
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @param virtualTarget Destination {@link Device} returned
	 * @return if the srcDevice hit the dstDevice with a radius defined by default
	 */
	public boolean hitTheDestinationByRadiusRange(double srcAzimuth, Device srcDevice,Device dstDevice){
		return hitTheDestinationByRadiusRange(srcAzimuth, dstDevice, dstDevice, null);
	}

	/**
	 * Verify if the srcDevice hit the dstDevice by opening angle of shoot
	 * @param srcAzimuth degree by end range of shoot 
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @param openingAngleShoot degree
	 * @return if the srcDevice hit the dstDevice by opening angle of shoot
	 */
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

	/**
	 * Verify if the srcDevice hit the dstDevice by opening angle of shoot defined in {@link ShootingRestrictions}
	 * @param srcAzimuth degree by end range of shoot 
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @return if the srcDevice hit the dstDevice by opening angle of shoot defined in {@link ShootingRestrictions}
	 */
	public boolean hitTheDestinationByOpeningAngleOfShoot(double srcAzimuth, Device srcDevice, 
				Device dstDevice){
		
		return hitTheDestinationByOpeningAngleOfShoot(srcAzimuth,srcDevice,dstDevice,this.shootRestrictions.getOpeningAngleShoot());
	}
	
	/**
	 * Verify if the srcDevice hit the dstDevice by opening angle of shoot defined in {@link ShootingRestrictions}
	 * @param srcAzimuth degree by end range of shoot 
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @param virtualTarget Destination {@link Device} returned
	 * @return if the srcDevice hit the dstDevice by opening angle of shoot defined in {@link ShootingRestrictions}
	 */
	public boolean hitTheDestination(double srcAzimuth, Device srcDevice, 
									 Device dstDevice, Device virtualTarget){

		double radiusRange = this.getRadiusRange(dstDevice);
		
		return( isValidTheDistanceBetweenDevices(srcDevice, dstDevice, this.shootRestrictions.getMaximumDistanceSrcToDst()) &&
				  ( hitTheDestinationByRadiusRange(srcAzimuth, srcDevice, dstDevice, virtualTarget, radiusRange) 
					||
				  (this.shootRestrictions.getOpeningAngleShoot()> 0 && 
				     hitTheDestinationByOpeningAngleOfShoot(srcAzimuth,srcDevice,dstDevice,this.shootRestrictions.getOpeningAngleShoot()))
				  )
			  );
	}

	/**
	 * Verify if the srcDevice hit the dstDevice by attributes default defined in {@link ShootingRestrictions} and {@link TargetRestrictions} 
	 * @param srcAzimuth degree by end range of shoot 
	 * @param srcDevice {@link Device} with {@link DeviceLocation} not null
	 * @param dstDevice {@link Device} with {@link DeviceLocation} not null
	 * @return if the srcDevice hit the dstDevice by attributes default defined in {@link ShootingRestrictions} and {@link TargetRestrictions}
	 */
	public boolean hitTheDestination(double srcAzimuth, Device srcDevice, 
			 Device dstDevice){

		return hitTheDestination(srcAzimuth,srcDevice,dstDevice,null);
	}
	
}
	
