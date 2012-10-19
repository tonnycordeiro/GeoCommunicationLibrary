package WifiAPConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetworkManager {

	private final WifiManager wifiManager;
	private WifiConfiguration wifiConfig;
	private boolean wasStarted;
	private Context context;
	
	public NetworkManager(Context context) {
		this.context = context;
		wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
		wasStarted = false;
	}
	
	/**
	 * This method returns a wifi access point state.
	 * 
	 * @return @see {@link NetworkState}
	 */
	public NetworkState getNetworkState(){
		//TODO
		return NetworkState.ACTIVATED;
	}
	
	/**
	 * This method returns a list of all Networks available that was create
	 * by another device running the same application.
	 * 
	 * @return A list of {@link NetworkAvailable}. If the return was null is
	 * because wifi is turned off. If the return was empty is because no networks
	 * are available.
	 */
	public List<NetworkAvailable> getAllNetworksAvailable(){
		if(!wifiManager.isWifiEnabled())
			return null;
		List<ScanResult> scans = wifiManager.getScanResults();
		List<NetworkAvailable> nets = new ArrayList<NetworkAvailable>();
		
		for (ScanResult scan : scans) {
			String ssid = scan.SSID;
			if(ssid.startsWith(NetworkConfiguration.getSsidPrefix())){
				boolean isopen = scan.capabilities == null ? true : false;
				NetworkAvailable net = new NetworkAvailable(NetworkConfiguration.removePrefix(ssid), isopen);
				nets.add(net);
			}
		}
		return nets;
	}
	
	/**
	 * Try start a new Access Point. It will turn off wifi.
	 * 
	 * @param netConfig @see {@link NetworkConfiguration}
	 * 
	 * @return These are return codes:
	 * <ul>
	 * 	<li>0: The network was started successfully.</li>
	 * 	<li>1: A exception occurred. Verify if your device can start a wifi Access Point.</li>
	 * 	<li>2: The network was started. Verify the {@see getNetworkState()} before calling this method.</li>
	 * 	<li>3: netConfig is null or has null parameters.
	 * </ul>
	 */
	public int startNetwork(NetworkConfiguration netConfig){
		
		if(wasStarted)
			return 2;
		if(netConfig == null || netConfig.getSSID() == null || (!netConfig.isOpen() && netConfig.getPassword() == null))
			return 3;
		
		if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
			wifiManager.setWifiEnabled(false);
		
		int ret = parseConfiguration(netConfig);
		if(ret > 0)
			return ret;
		int id = wifiManager.addNetwork(wifiConfig);
        wifiManager.enableNetwork(id, true);
        wifiManager.saveConfiguration();
		
        boolean res = setWifiApEnabled(wifiConfig, true);
		if(!res)
			return 1;
		
		wasStarted = true;
		return 0;
	}
	
	/**
	 * 
	 * @return These are return codes:
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
		wifiManager.removeNetwork(wifiConfig.networkId);
		wifiManager.saveConfiguration();
		
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
			Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			return (Boolean) method.invoke(wifiManager, wifiConfig, enabled);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return false;
		}
	}
}
