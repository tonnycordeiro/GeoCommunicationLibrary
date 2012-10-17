package Auxiliaries;

import LocationAndOrientation.DeviceLocation;
import LocationAndOrientation.DeviceOrientation;
import android.content.Context;


/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class Device {
	
	private static final String DEFAULT_NICK = "DeviceApp";
	
	private String id;
	private String nick;
	private Context context;
	private DeviceLocation devLocation;
	private DeviceOrientation devOrient;
	
	/**
	 * Initialize a device with a default nick name.
	 */
	public Device(Context context){
		this.context = context;
		this.id = getDeviceMacAddress();
		this.nick = DEFAULT_NICK;
	}
	
	/**
	 * Initialize a device with nick name.
	 * 
	 * @param nick Nick name to identify easily the device by application.
	 */
	public Device(String nick, Context context) {
		this.context = context;
		this.id = getDeviceMacAddress();
		this.nick = nick;
	}

	/**
	 * This method search the MAC Address of device.
	 *  
	 * @return MAC Address
	 */
	private String getDeviceMacAddress() {
		//TODO
		return "";
	}
	
	public void initializeLocation(int timeWait, int minDist){
		devLocation = new DeviceLocation(this.context);
		devLocation.enableLocationListener(timeWait, minDist);
	}
	
	public void initializeOrientation(int sensorType){
		devOrient = new DeviceOrientation(this.context, sensorType);
		devOrient.enableSensorListener();
	}
	
	public double getLatitude() {
		return devLocation.getLatitude();
	}

	public double getLongitude() {
		return devLocation.getLongitude();
	}

	public String getId() {
		return id;

	}

	public String getNick() {
		return nick;
	}

	public float getOrientation() {
		return devOrient.getOrientatio();
	}

	public void waitGpsEnable(){
		while(!devLocation.gpsIsReady()){
			devLocation.waitGps();	
		}
	}
}
