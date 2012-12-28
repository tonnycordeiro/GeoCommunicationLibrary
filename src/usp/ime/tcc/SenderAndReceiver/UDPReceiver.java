package usp.ime.tcc.SenderAndReceiver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolMessages;



public class UDPReceiver implements Runnable{

	static final int SERVER_PORT = 27385;
	private ReceiveListener listener;
	
	public UDPReceiver(ReceiveListener listener) {
		this.listener = listener;
	}
	
	public void run() {
		DatagramSocket serverSocket;
		try{
			serverSocket = new DatagramSocket(SERVER_PORT);
	        byte[] rcvData = new byte[1024];

	        while(true){
	        	System.out.println("ALORAAA!!!!!! - To no UDPREceive no while(true)");
				DatagramPacket rcvPacket = new DatagramPacket(rcvData, rcvData.length);
	            serverSocket.receive(rcvPacket);
	            
	            System.out.println("ALORAAA!!!!!! - To no UDPREceive no while(true)");
	            System.out.println("ALORAAA!!!!!! - To no UDPREceive no while(true)");
	            System.out.println("ALORAAA!!!!!! - To no UDPREceive no while(true)");
	            System.out.println("ALORAAA!!!!!! - To no UDPREceive no while(true)");
	            
	            ByteArrayInputStream bis = new ByteArrayInputStream(rcvPacket.getData());
	            ObjectInput in = null;
	            try {
					in = new ObjectInputStream(bis);
					ProtocolInformation appInfo = (ProtocolInformation) in.readObject();
					  
					if(thisMessageIsForMe(appInfo)){
						if(appInfo.getTypeMsg().equals(ProtocolMessages.GEOMSG)){
							listener.onReceiveGEOMSG(appInfo);
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
		//TODO
		return true;
	}

	
}