package usp.ime.tcc.Auxiliaries;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class IP {

	public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { 
                    	return inetAddress.getHostAddress(); 
                    }
                }
            }
        } catch (SocketException ex) {
        	ex.printStackTrace();
        }
   
        return null;
    }
	
	public static List<String> getAllIPs() {
		//TODO
		return null;
	}
	
	public static String getBroadcastAddress() {
		//TODO
		return "192.168.255.255";
	}
	
	//TODO(Tonny): (baixo nível de importância) setar a máscara apenas se conseguir setar o ip do dispositivo
}
