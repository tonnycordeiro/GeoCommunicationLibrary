package usp.ime.tcc.Communication;

public class AppProtocol {
   final static String CRLF = "\r\n";
   final static String dividerHeader = " ";  
   private double latitudeFrom;
   private double longitudeFrom;
   private float orientationFrom;
   
   public AppProtocol(String header) {
       String[] atributes = header.split(dividerHeader);
       
       this.latitudeFrom = Float.parseFloat(atributes[1]);
       this.longitudeFrom = Float.parseFloat(atributes[2]);
       this.orientationFrom = Float.parseFloat(atributes[3]);
   }
   
   public AppProtocol(double latitudeFrom, double longitudeFrom, float orientationFrom) {
	   this.latitudeFrom = latitudeFrom;
       this.longitudeFrom = longitudeFrom;
       this.orientationFrom = orientationFrom;
   }
   
   public AppProtocol() {
   }

   public String toString(){
	   return this.latitudeFrom + dividerHeader + this.longitudeFrom + dividerHeader + this.orientationFrom + CRLF + CRLF;
   }
}
