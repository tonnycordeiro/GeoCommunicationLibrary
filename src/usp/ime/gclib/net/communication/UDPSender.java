package usp.ime.gclib.net.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This class is for internal use, it must not called.
 * It must use {@link CommunicationSocket} for send messages.
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class UDPSender {

	protected UDPSender() {
		
	}
	
	/**
	 * Send a message on the network.
	 * 
	 * @param sndData Specify the message that will be send.
	 * 
	 * @param ipAddress Specify the IP address of the destine node.
	 * 
	 * @return Result of send:
	 * <ul>
	 * 	<li>0: Successful</li>
	 * 	<li>1: Unsuccessful</li>
	 * 	<li>2: Undefined error</li>
	 * </ul>
	 */
	protected int send(byte[] sndData, String ipAddress){
		try{
			InetAddress serverAddr = InetAddress.getByName(ipAddress);

			DatagramPacket sndPacket = new DatagramPacket(sndData, sndData.length, serverAddr, UDPReceiver.SERVER_PORT);
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);
			socket.send(sndPacket);

			socket.close();
		}
		catch(IOException e){
			e.printStackTrace();
			return 1;
		}
		catch(Exception e){
			e.printStackTrace();
			return 2;
		}
		
		return 0;
	}
	

}