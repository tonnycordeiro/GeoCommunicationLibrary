package WifiAPConfig;

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
