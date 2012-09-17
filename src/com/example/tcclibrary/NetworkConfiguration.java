package com.example.tcclibrary;

/**
 * This class is responsible to configure network that will be used by all devices.
 * Must be configure just once by each application.
 *   
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class NetworkConfiguration {
	
	private static final String PREFIX_SSID = "BibLocDisp_";
	private String ssid;
	private String password; 
	
	/**
	 * Start a new Network to be available to application.
	 * 
	 * @param ssid Name of network
	 * @param password Password of network
	 */
	public NetworkConfiguration(String ssid, String password) {
		this.ssid = PREFIX_SSID + ssid;
		this.password = password;
	}
	
	public String getSsid(){
		return this.ssid;
	}
	
	protected String getPassword(){
		return this.password;
	}
	
	//TODO 
	/*Here we will add more code and information to configure network*/

}
