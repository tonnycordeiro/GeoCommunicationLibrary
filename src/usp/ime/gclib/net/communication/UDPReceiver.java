package usp.ime.gclib.net.communication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import usp.ime.gclib.Device;
import usp.ime.gclib.hit.TargetRestrictions;
import usp.ime.gclib.net.protocol.ProtocolInformation;

/**
 * This class is for internal use, it must not called.
 * It must use {@link CommunicationSocket} and {@link IReceiveListener} for receive messages.
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class UDPReceiver extends Receiver implements Runnable {

	protected static final int SERVER_PORT = 27385;
	protected static DatagramSocket serverSocket;
	
	protected UDPReceiver(IReceiveListener listener, Device receiverDevice) {
		super(listener, receiverDevice);
	}
	
	protected UDPReceiver(IReceiveListener listener, Device receiverDevice, TargetRestrictions targetRestrictions) {
		super(listener, receiverDevice, targetRestrictions);
	}
	
	public void run() {
		
		try{
			serverSocket = new DatagramSocket(SERVER_PORT);
	        byte[] rcvData = new byte[2048];

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
	            	System.out.println("RECEBI UMA MENSAGEM");
					in = new ObjectInputStream(bis);
					ProtocolInformation appInfo = (ProtocolInformation) in.readObject();
					if(isValidDestination(appInfo)){
						activateEvents(appInfo);
					}
	            } catch(Exception e) {
	            	e.printStackTrace();
	            }

			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
}