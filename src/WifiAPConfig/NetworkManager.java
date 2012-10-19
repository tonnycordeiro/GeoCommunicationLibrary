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
	private Context context;
	
	public NetworkManager(Context context) {
		this.context = context;
		mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
		wasStarted = false;
	}
	
	public NetworkStatus getNetworkState(){
		//TODO
		return NetworkStatus.ACTIVATED;
	}
	
	
	public int startNetwork(NetworkConfiguration netConfig){
		
		if(wasStarted || netConfig == null || netConfig.getSSID() == null || (!netConfig.isOpen() && netConfig.getPassword() == null))
			return 2;
		
		if(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
			mWifiManager.setWifiEnabled(false);
		
		int ret = parseConfiguration(netConfig);
		if(ret > 0)
			return ret;
		int id = mWifiManager.addNetwork(wifiConfig);
        mWifiManager.enableNetwork(id, true);
        mWifiManager.saveConfiguration();
		
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
		mWifiManager.removeNetwork(wifiConfig.networkId);
		mWifiManager.saveConfiguration();
		
		wasStarted = false;
		return 0;
	}
	
	private int parseConfiguration(NetworkConfiguration netConfig) {
		wifiConfig = new WifiConfiguration();
		
		wifiConfig.SSID = netConfig.getSSID();
		wifiConfig.status = WifiConfiguration.Status.ENABLED;
		if(netConfig.isOpen()){
			wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		}
		else {
			wifiConfig.preSharedKey = netConfig.getPassword();
			wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
	        wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		}
		
		return 0;
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
