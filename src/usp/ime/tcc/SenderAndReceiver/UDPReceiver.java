package usp.ime.tcc.SenderAndReceiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import usp.ime.tcc.Communication.CommunicationPacket;



public class UDPReceiver {

	static final int SERVER_PORT = 27384;
	
	public void receive(CommunicationPacket packet, ReceiveListener listener) {
		try{
			DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);
	        byte[] rcvData = new byte[1024];
			
	        while(true){
				DatagramPacket rcvPacket = new DatagramPacket(rcvData, rcvData.length);
	            serverSocket.receive(rcvPacket);
	            
	            if(thisMessageIsForMe(rcvPacket)){
	            	packet.setData(rcvPacket.getData());
	            	packet.setSrcIP(rcvPacket.getAddress().getHostAddress());
	            	getHeader(rcvPacket, packet);
	            	listener.onReceiveMessage(packet);
	            }
			}
		}
		catch(IOException e){
			e.printStackTrace();
			listener.onReceiveMessage(null);
		}
	}

	private void getHeader(DatagramPacket rcvPacket, CommunicationPacket packet) {
		//TODO
	}

	private boolean thisMessageIsForMe(DatagramPacket rcvPacket) {
		//TODO
		return true;
	}
	
}