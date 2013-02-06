package usp.ime.gclib.device;

import java.io.Serializable;

import usp.ime.gclib.net.communication.IP;
import usp.ime.gclib.sensor.location.DeviceLocation;
import usp.ime.gclib.sensor.orientation.DeviceOrientation;

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
	private DeviceLocation deviceLocation;
	private DeviceOrientation deviceOrientation;
	private boolean isAccessPoint;
	/**
	 * Initialize a device with nick name.
	 * 
	 * @param nick Nick name to identify easily the device by application.
	 */
	public Device() {
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
	
	public void startIp() {
		this.ip = IP.getLocalIpAddress();
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
