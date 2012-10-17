package Communication;

import Auxiliaries.SenderType;

public class AppProtocol {
   final static String CRLF = "\r\n";
   final static String dividerHeader = " ";  
   private SenderType senderType;
   private double latitudeFrom;
   private double longitudeFrom;
   private float orientationFrom;
   
   public AppProtocol(String header) {
       String[] atributes = header.split(dividerHeader);
       this.senderType = SenderType.valueOf(atributes[0]);
       this.latitudeFrom = Float.parseFloat(atributes[1]);
       this.longitudeFrom = Float.parseFloat(atributes[2]);
       this.orientationFrom = Float.parseFloat(atributes[3]);
   }
   
   public AppProtocol(SenderType senderType, double latitudeFrom, double longitudeFrom, float orientationFrom) {
	   this.senderType = senderType;
	   this.latitudeFrom = latitudeFrom;
       this.longitudeFrom = longitudeFrom;
       this.orientationFrom = orientationFrom;
   }
   
   public AppProtocol(SenderType senderType) {
	   this.senderType = senderType;
	}

   public String toString(){
	   if(this.senderType == SenderType.BROADCAST_TO_ALL){
		   return this.senderType.toString() + CRLF + CRLF; 
	   }
	   return this.senderType.toString() + dividerHeader + this.latitudeFrom + dividerHeader + this.longitudeFrom + dividerHeader + this.orientationFrom + CRLF + CRLF;
   }
}
