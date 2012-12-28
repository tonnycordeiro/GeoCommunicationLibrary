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
	
	private static final long serialVersionUID = 3L;

	private static final String DEFAULT_NICK = "DeviceApp";
	
	private String nick;
	private String ip;
	private Context context;//TODO(Tonny): deixa dependente da biblioteca Android
	private DeviceLocation devLocation;
	private DeviceOrientation devOrient;
	
	/**
	 * Initialize a device with a default nick name.
	 */
	public Device(Context context){
		this.context = context;
		this.nick = DEFAULT_NICK;
		this.ip = IP.getLocalIpAddress();
	}
	
	/**
	 * Initialize a device with nick name.
	 * 
	 * @param nick Nick name to identify easily the device by application.
	 */
	public Device(String nick, Context context) {
		this.context = context;
		this.nick = nick;
		this.ip = IP.getLocalIpAddress();
	}

	public void initializeLocation(int timeWait, int minDistance){
		devLocation = new DeviceLocation(this.context);
		devLocation.enableLocationListener(timeWait, minDistance);
	}
	
	public void initializeOrientation(){//TODO(Tonny): sensorType deixa dependente da biblioteca Android 
		devOrient = new DeviceOrientation(this.context);
		devOrient.enableSensorListener();
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

	public float getOrientation() {
		return devOrient.getOrientatio();
	}

	public void waitGpsEnable(){
		while(!devLocation.gpsIsReady()){
			devLocation.waitGps();	
		}
	}
}
