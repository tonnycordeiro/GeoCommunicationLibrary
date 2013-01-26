package usp.ime.gclib.net.communication;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
	
	public static String getBroadcastAddress(String ipBase) {
		String[] s = ipBase.split("\\.");
		if(s.length != 4)
			return "255.255.255.255";
		return s[0] + "." + s[1] + "." + s[2] + ".255";
	}
	
	public static String getGatewayAddress(String ipBase) {
		String[] s = ipBase.split("\\.");
		if(s.length != 4)
			return "192.168.43.1";
		return s[0] + "." + s[1] + "." + s[2] + ".1";
	}
	
	//TODO(Tonny): (baixo nível de importância) setar a máscara apenas se conseguir setar o ip do dispositivo
}
