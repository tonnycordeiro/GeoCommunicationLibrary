package usp.ime.gclib.net.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import usp.ime.gclib.net.protocol.ProtocolGEOACKInformation;
import usp.ime.gclib.net.protocol.ProtocolGEOMSGInformation;
import usp.ime.gclib.net.protocol.ProtocolInformation;
import usp.ime.gclib.net.protocol.ProtocolLIBCONFIGInformation;

public class TCPReceiver implements Runnable{

	public static int SERVERPORT = 2389;
	private ReceiveListener listener;
	public static ServerSocket serverSocket;
	
	public TCPReceiver(ReceiveListener listener) {
		this.listener = listener;
	}
	
	public void run() {
		
		try {
			serverSocket = new ServerSocket(SERVERPORT);
			while(true) {
				Socket connection;
				try {
					connection = serverSocket.accept();
				}
				catch(SocketException e){
					e.printStackTrace();
					break;
				}
				ObjectInputStream inFromClient = new ObjectInputStream(connection.getInputStream());
				
				try {
					ProtocolInformation appInfo = (ProtocolInformation) inFromClient.readObject();
					
					switch (appInfo.getTypeMsg()) {
						case GEOMSG:
							listener.onReceiveGEOMSG((ProtocolGEOMSGInformation)appInfo);
							break;
						case GEOACK:
							listener.onReceiveGEOACK((ProtocolGEOACKInformation)appInfo);
							break;
						case APPDATA:
							listener.onReceiveAPPDATA(appInfo);
							break;
						case ONLINE:
							listener.onReceiveONLINE(appInfo);
							break;
						case ONLINEANSWER:
							listener.onReceiveONLINEANSWER(appInfo);
							break;
						case LIBCONFIG:
							listener.onReceiveLIBCONFIG((ProtocolLIBCONFIGInformation)appInfo);
							break;
						default:
							break;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
