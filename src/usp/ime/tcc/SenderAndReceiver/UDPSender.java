package usp.ime.tcc.SenderAndReceiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPSender {

	
	/**
	 * Send a message on the network.
	 * 
	 * @param message Specify the message that will be send.
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
	public int send(byte[] sndData, String ipAddress){
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