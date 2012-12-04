package usp.ime.tcc.Auxiliaries;

import org.geotools.referencing.GeodesicPosition;
import org.geotools.referencing.GeodeticCalculator;

public class GeodesicManager {
	

	public GeodesicManager(){};
	
	public boolean hitTheDestination(double srcAzimuth, double srcLatitude, double srcLongitude, 
			double dstLatitude, double dstLongitude, double dstRaio,
			double srcAlcance, double srcAngulo, double srcWidthShot){

		double srcAzimuthR, srcAzimuthL;
		double distanceSrcToDst, distanceDstToVitual;
		System.out.println("entrou na função");
		GeodesicPosition virtualPos = null;

		GeodeticCalculator gcTarget = new GeodeticCalculator();
		gcTarget.setStartingGeographicPoint(srcLatitude,srcLongitude);
		gcTarget.setDestinationGeographicPoint(dstLatitude,dstLongitude);
		distanceSrcToDst = gcTarget.getOrthodromicDistance();
		
		if(srcAlcance > 0 && distanceSrcToDst>srcAlcance)
			return false;

		//check if the virtual point was hit in the same circle with radius = distance 
		GeodeticCalculator gcShoot = new GeodeticCalculator();
		gcShoot.setStartingGeographicPoint(srcLatitude,srcLongitude);
		gcShoot.setDirection(srcAzimuth,distanceSrcToDst); //mesmo raio
		virtualPos = gcShoot.getDestinationGeographicPoint();

		GeodeticCalculator gcProximity = new GeodeticCalculator();
		gcProximity.setStartingGeographicPoint(dstLatitude,dstLongitude);
		gcProximity.setDestinationGeographicPoint(virtualPos.getX(),virtualPos.getY());
		distanceDstToVitual = gcProximity.getOrthodromicDistance();

		if(srcWidthShot>0)
			srcWidthShot = srcWidthShot/2;
		else
			srcWidthShot = 0;

		if(dstRaio + srcWidthShot < distanceDstToVitual)
			return false;
		else if(srcAngulo > 0){
			srcAzimuthL = srcAzimuth + srcAngulo;
			srcAzimuthR = srcAzimuth + srcAngulo;
			return (hitTheDestination(srcAzimuthL, srcLatitude, srcLongitude, 
					dstLatitude, dstLongitude, dstRaio,
					srcAlcance, 0, srcWidthShot)) ||			
					(hitTheDestination(srcAzimuthR, srcLatitude, srcLongitude, 
							dstLatitude, dstLongitude, dstRaio,
							srcAlcance, 0, srcWidthShot));
		}
		
		return true;

	}

}
