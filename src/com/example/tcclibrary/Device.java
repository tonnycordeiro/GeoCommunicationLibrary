package com.example.tcclibrary;

/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class Device {
	
	private static final String DEFAULT_NICK = "DeviceApp";
	private double latitude;
	private double longitude;
	private String id;
	private String nick;
	private double[] direction;
	
	/**
	 * Initialize a device with a default nick name.
	 */
	public Device(){
		this.id = getDeviceMac();
		this.nick = DEFAULT_NICK;
		this.direction = new double[3];
	}
	
	/**
	 * Initialize a device with nick name.
	 * 
	 * @param nick Nick name to identify easily the device by application.
	 */
	public Device(String nick) {
		this.id = getDeviceMac();
		this.nick = nick;
		this.direction = new double[3];
	}

	/**
	 * This method search the MAC Address of device.
	 *  
	 * @return MAC Address
	 */
	private String getDeviceMac() {
		
		return null;
	}
	
	
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getId() {
		return id;
	}

	public String getNick() {
		return nick;
	}

	public double[] getDirection() {
		return direction;
	}

	public void setDirection(double[] direction) {
		this.direction = direction;
	}

}
