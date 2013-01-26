package usp.ime.gclib.device;

import java.io.Serializable;

import usp.ime.gclib.net.communication.IP;


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
	private double latitude;
	private double longitude;
	private float locationAccuracy;
	private float[] orientation;
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

	public String getIp(){
		return this.ip;
	}
	
	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}

	public float[] getOrientation() {
		return this.orientation;
	}
	
	public void setOritentation(float[] orientation) {
		this.orientation = orientation;
	}

	public boolean isAccessPoint() {
		return isAccessPoint;
	}

	public void setAccessPoint(boolean isAccessPoint) {
		this.isAccessPoint = isAccessPoint;
	}
	public float getLocationAccuracy() {
		return locationAccuracy;
	}

	public void setLocationAccuracy(float locationAccuracy) {
		this.locationAccuracy = locationAccuracy;
	}

}
