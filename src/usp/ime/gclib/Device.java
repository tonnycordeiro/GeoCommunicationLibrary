package usp.ime.gclib;

import java.io.Serializable;

import usp.ime.gclib.net.communication.IP;
import usp.ime.gclib.sensor.location.DeviceLocation;
import usp.ime.gclib.sensor.orientation.DeviceOrientation;

/**
 * This class is used to identify each device in application.
 * If the application doesn't specify the attributes, the default values will be set. 
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class Device implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nick;
	private String ip;
	private DeviceLocation deviceLocation;
	private DeviceOrientation deviceOrientation;
	private boolean isAccessPoint;
	
	public Device() {
		this.nick = "";
		deviceLocation = new DeviceLocation();
		deviceOrientation = new DeviceOrientation();
	}

	public Device(String nick) {
		this.nick = nick;
		deviceLocation = new DeviceLocation();
		deviceOrientation = new DeviceOrientation();
	}
	
	public Device(String nick, DeviceLocation deviceLocation) {
		this.nick = nick;
		this.deviceLocation = deviceLocation;
		deviceOrientation = new DeviceOrientation();
	}
	
	public Device(String nick, DeviceOrientation deviceOrientation) {
		this.nick = nick;
		this.deviceLocation = new DeviceLocation();
		this.deviceOrientation = deviceOrientation;
	}
	
	public Device(String nick, DeviceLocation deviceLocation, DeviceOrientation deviceOrientation) {
		this.nick = nick;
		this.deviceLocation = deviceLocation;
		this.deviceOrientation = deviceOrientation;
	}
	
	public DeviceLocation getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(DeviceLocation deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public DeviceOrientation getDeviceOrientation() {
		return deviceOrientation;
	}

	public void setDeviceOrientation(DeviceOrientation deviceOrientation) {
		this.deviceOrientation = deviceOrientation;
	}
	
	/**
	 * throws a {@link IllegalArgumentException} if a problem occurs when try to get IP address.
	 * Then the IP address attribute will set to <code>null</code>. 
	 */
	public void startIp() {
		this.ip = IP.getLocalIpAddress();
		if(this.ip == null)
			throw new IllegalArgumentException("A problem occurred when try to get IP address. Certify that you are connected a network.");
	}

	public String getIp(){
		return this.ip;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}

	public boolean isAccessPoint() {
		return isAccessPoint;
	}

	public void setAccessPoint(boolean isAccessPoint) {
		this.isAccessPoint = isAccessPoint;
	}
}
