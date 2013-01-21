package usp.ime.tcc.Auxiliaries;

import java.io.Serializable;

import usp.ime.tcc.LocationAndOrientation.DeviceLocation;
import usp.ime.tcc.LocationAndOrientation.DeviceOrientation;
import android.content.Context;


/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class Device implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nick;
	private String ip;
	private DeviceLocation devLocation;
	private DeviceOrientation devOrient;
	private boolean isAccessPoint;
	
	/**
	 * Initialize a device with nick name.
	 * 
	 * @param nick Nick name to identify easily the device by application.
	 */
	public Device(String nick) {
		this.nick = nick;
	}

	public Device() {
		
	}
	
	public void startIp() {
		ip = IP.getLocalIpAddress();
	}

	public void initializeLocation(int timeWait, int minDistance, Context context){
		devLocation = new DeviceLocation(context);
		devLocation.enableLocationListener(timeWait, minDistance);
	}
	
	public void stopLocation() {
		devLocation.disableLocationListener();
		devLocation = null;
	}
	
	public void initializeOrientation(Context context){//TODO(Tonny): sensorType deixa dependente da biblioteca Android 
		devOrient = new DeviceOrientation(context);
		devOrient.enableSensorListener();
	}
	
	public boolean gpsIsReady() {
		return devLocation.gpsIsReady();
	}
	
	public boolean gpsIsEnable() {
		return devLocation.gpsIsEnable();
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public double getLatitude() {
		return devLocation.getLatitude();
	}

	public double getLongitude() {
		return devLocation.getLongitude();
	}

	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}

	public float getOrientation() {
		return devOrient.getOrientatio();
	}

	public boolean isAccessPoint() {
		return isAccessPoint;
	}

	public void setAccessPoint(boolean isAccessPoint) {
		this.isAccessPoint = isAccessPoint;
	}

}
