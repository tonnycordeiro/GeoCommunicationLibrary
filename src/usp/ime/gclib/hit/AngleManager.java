package usp.ime.gclib.hit;
/**
 * This class manage specific angle calculations
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 * 
 */

public class AngleManager {

	/**
	 * Verify if the angleIn is between the angleInf and the angleSup  
	 * @param angleIn range -180 to 180 degrees
	 * @param angleSup range -180 to 180 degrees
	 * @param angleInf range -180 to 180 degrees
	 * @return if the angleIn is between the angleInf and the angleSup
	 */
	public static boolean isAngleContainedInArcBasedIn180Graus(double angleIn, double angleSup, double angleInf){
		angleSup = convertAngleFrom180To360Graus(angleSup);
		angleInf = convertAngleFrom180To360Graus(angleInf);
		
		if(angleSup < angleInf)
			angleSup += 360;
		
		return (angleSup >= angleIn && angleInf <= angleIn);
	}
	
	/**
	 * Sum the angleAdd in the angleInitial in the range -180 to 180 degrees
	 * @param angleInitial range -180 to 180 degrees
	 * @param angleAdd range -180 to 180 degrees
	 * @return new angle range -180 to 180 degrees
	 */
	public static double addAngleBasedIn180Graus(double angleInitial, double angleAdd){
		double newAngle;
		newAngle = convertAngleFrom180To360Graus(angleInitial) + angleAdd;
		return convertAngleFrom360To180Graus(newAngle);
	}
	
	/**
	 * Convert angle in the range -180 to 180 degrees to 0 to 360 degrees 
	 * @param angle in the range -180 to 180 degrees
	 * @return new angle in the range 0 to 360 degrees 
	 */
	public static double convertAngleFrom180To360Graus(double angle){
		angle = Math.signum(angle)*(Math.abs(angle)%360);
		if(angle<0)
			angle = angle + 360;
		return angle;
	}

	/**
	 * Convert angle in the range 0 to 360 degrees to -180 to 180 degrees  
	 * @param angle in the range 0 to 360 degrees
	 * @return new angle in the range -180 to 180 degrees 
	 */
	public static double convertAngleFrom360To180Graus(double angle){
		angle %= 360;
		if(angle>180)
			angle = angle - 360; 
		return angle;
	}
	
}
