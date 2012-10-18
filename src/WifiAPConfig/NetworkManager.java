package WifiAPConfig;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetworkManager {

	private final WifiManager mWifiManager;
	private WifiConfiguration wifiConfig;
	private boolean wasStarted;
	
	public NetworkManager(Context context) {
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wasStarted = false;
	}
	
	public NetworkStatus getNetworkState(){
		
		return NetworkStatus.ACTIVATED;
	}
	
	public int startNetwork(NetworkConfiguration netConfig){
		
		if(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
			mWifiManager.setWifiEnabled(false);
		wifiConfig = parseConfiguration(netConfig);
		boolean res = setWifiApEnabled(wifiConfig, true);
		if(!res)
			return 1;
		wasStarted = true;
		return 0;
	}
	
	/**
	 * 
	 * @return
	 * <ul>
	 * 	<li>0: Successful</li>
	 * 	<li>1: Occurred a exception during the stopping.</li>
	 * 	<li>2: Network was not started.</li>
	 * <ul>
	 */
	public int stopNetwork(){
		if(!wasStarted){
			return 2;
		}
		boolean res = setWifiApEnabled(wifiConfig, false);
		if(!res)
			return 1;
		return 0;
	}
	
	private WifiConfiguration parseConfiguration(NetworkConfiguration netConfig) {
		WifiConfiguration wc = new WifiConfiguration();
		wc.SSID = "\"" + netConfig.getSSID() + "\"";
		wc.preSharedKey = "\"" + netConfig.getPassword() + "\"";
		try{
			WifiConfiguration.class.getField("frequency").setInt(wc, netConfig.getChannel());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return wc;
	}

	private boolean setWifiApEnabled(WifiConfiguration wifiConfig, boolean enabled) {
		try {
			Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			return (Boolean) method.invoke(mWifiManager, wifiConfig, enabled);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return false;
		}
	}
}
