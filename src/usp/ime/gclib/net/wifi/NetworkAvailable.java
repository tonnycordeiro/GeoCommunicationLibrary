package usp.ime.gclib.net.wifi;

/**
 * This class is used to be a returned object of getAllNetworkAvailable method.
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class NetworkAvailable {

	private String SSID;
	private boolean isOpen;
	
	public NetworkAvailable(String SSID, boolean isOpen) {
		this.SSID = SSID;
		this.isOpen = isOpen;
	}
	
	public String getSSID() {
		return SSID;
	}
	public boolean isOpen() {
		return isOpen;
	}
}
