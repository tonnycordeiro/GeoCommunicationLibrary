package WifiAPConfig;

public class NetworkConfiguration {

	private static final String SSID_PREFIX = "ANDROID_LIBRARY_";
	private String sSID = null;
	private String password = null;
	private boolean isOpen = false;
	
	
	public NetworkConfiguration() {
		
	}	
		
	public String getSSID() {
		if(sSID == null)
			return null;
		int mid = SSID_PREFIX.length();
		String s = sSID.substring(mid);
		return s;
	}

	public void setSSID(String sSID) {
		this.sSID = SSID_PREFIX + sSID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOpen() {
		return this.isOpen;
	}

	public void setisOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public static String getSsidPrefix() {
		return SSID_PREFIX;
	}
	
}
