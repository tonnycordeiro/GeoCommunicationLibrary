package WifiAPConfig;

public class NetworkConfiguration {

	private static final String SSID_PREFIX = "ANDROID_LIBRARY_";
	private String sSID;
	private String password;
	private int channel;
	
	
	public NetworkConfiguration() {
		
	}	
		
	public String getSSID() {
		String[] values = sSID.split(SSID_PREFIX);
		return values[0];
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

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public static String getSsidPrefix() {
		return SSID_PREFIX;
	}
	
}
