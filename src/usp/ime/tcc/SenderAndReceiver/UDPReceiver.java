package usp.ime.tcc.SenderAndReceiver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import usp.ime.tcc.Communication.ProtocolGEOACKInformation;
import usp.ime.tcc.Communication.ProtocolGEOSMSGInformation;
import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolLIBCONFIGInformation;



public class UDPReceiver implements Runnable {

	static final int SERVER_PORT = 27385;
	private ReceiveListener listener;
	public static DatagramSocket serverSocket;
	
	public UDPReceiver(ReceiveListener listener) {
		this.listener = listener;
	}
	
	public void run() {
		
		try{
			serverSocket = new DatagramSocket(SERVER_PORT);
	        byte[] rcvData = new byte[1024];

	        while(true){
				DatagramPacket rcvPacket = new DatagramPacket(rcvData, rcvData.length);
	            try{
	            	serverSocket.receive(rcvPacket);
	            }
	            catch(SocketException e){
	            	e.printStackTrace();
	            	break;
	            }
	            
	            ByteArrayInputStream bis = new ByteArrayInputStream(rcvPacket.getData());
	            ObjectInput in = null;
	            try {
					in = new ObjectInputStream(bis);
					ProtocolInformation appInfo = (ProtocolInformation) in.readObject();
					  
					if(thisMessageIsForMe(appInfo)){
						switch(appInfo.getTypeMsg()){
							case GEOMSG:
								listener.onReceiveGEOMSG((ProtocolGEOSMSGInformation)appInfo);
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
						
					}
	            } catch(ClassNotFoundException e) {
	            	e.printStackTrace();
	            }

			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	private boolean thisMessageIsForMe(ProtocolInformation appInfo) {
		//TODO terminar isso aqui!!
		return true;
	}

	
}