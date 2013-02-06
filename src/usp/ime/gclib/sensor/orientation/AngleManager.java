package usp.ime.gclib.sensor.orientation;

public class AngleManager {

	public static boolean isAngleContainedInArcBasedIn180Graus(double angleIn, double angleSup, double angleInf){
		angleSup = convertAngleFrom180To360Graus(angleSup);
		angleInf = convertAngleFrom180To360Graus(angleInf);
		
		if(angleSup < angleInf)
			angleSup += 360;
		
		return (angleSup >= angleIn && angleInf <= angleIn);
	}
	
	public static double addAngleBasedIn180Graus(double angleInitial, double angleAdd){
		double newAngle;
		newAngle = convertAngleFrom180To360Graus(angleInitial) + angleAdd;
		return convertAngleFrom360To180Graus(newAngle);
	}
	
	public static double convertAngleFrom180To360Graus(double angle){
		angle = Math.signum(angle)*(Math.abs(angle)%360);
		if(angle<0)
			angle = 180 + -angle;
		return angle;
	}

	public static double convertAngleFrom360To180Graus(double angle){
		angle %= 360;
		if(angle>180)
			angle = -(angle - 180); 
		return angle;
	}
	
}
