package usp.ime.tcc.WifiAPConfig;

public class NetworkConfiguration {

	private static final String SSID_PREFIX = "AND_LIB_";
	private String sSID = null;
	private String password = null;
	private boolean isOpen = false;
	
	
	public String getSSID() {
		return this.sSID;
	}

	public void setSSID(String sSID) {
		this.sSID = sSID;
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

	protected static String removePrefix(String ssid){
		if(ssid == null)
			return null;
		int mid = SSID_PREFIX.length();
		String s = ssid.substring(mid);
		return s;
	}
	protected static String getSsidPrefix() {
		return SSID_PREFIX;
	}
	
}
