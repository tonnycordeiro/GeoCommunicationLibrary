package Communication;

import Auxiliaries.Device;
import Auxiliaries.SenderType;
import SenderAndReceiver.ReceiveListener;
import SenderAndReceiver.*;


/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class CommunicationSocket {

	private boolean alreadyInitializeListener = false;
	
	
	/**
	 * Response the message to the source device.
	 * 
	 * @param device The device that is responding the message
	 * @param message The message that will be send
	 * @param dstIP The destine IP.
	 * 
	 * @return A integer that indicates the result of the responding.
	 * The possibles values are:
	 * <ul>
	 * 	<li>0: Successful</li>
	 * 	<li>1: Unsuccessful</li>
	 * 	<li>2: Undefined error</li>
	 * </ul>
	 */
	public int sendResponse(Device device, byte[] message, String dstIP){
		CommunicationPacket packet = new CommunicationPacket(device, SenderType.UNICAST, message);
		packet.setDstIP(dstIP);
		
		return sendMessage(packet);
	}
	/**
	 * This method will be sent the message that is specified in packet parameter.
	 * 
	 * @return A integer that indicates the result of the sending.
	 * The possibles values are:
	 * <ul>
	 * 	<li>0: Successful</li>
	 * 	<li>1: Unsuccessful</li>
	 * 	<li>2: Undefined error</li>
	 * </ul>
	 */
	public int sendMessage(CommunicationPacket packet){
		int sendReturn = 1;
		
		AppProtocol protocol;
		
		if(packet.getSenderType() == SenderType.BROADCAST_TO_ALL || packet.getSenderType() == SenderType.UNICAST)
			protocol = new AppProtocol(packet.getSenderType());
		else if(packet.getSenderType() == SenderType.BROADCAST_TO_ONE){
			Device device = packet.getSrcDevice();
			protocol = new AppProtocol(packet.getSenderType(), device.getLatitude(), device.getLongitude(), device.getOrientation());
		}
		else{
			System.out.println("Tipo inexistente");
			return 1;
		}
		
		byte[] header = protocol.toString().getBytes();
		byte[] data = packet.getData();
		byte[] buffer = new byte[header.length + data.length];
		
		System.arraycopy(header, 0, buffer, 0, header.length);
		System.arraycopy(data, 0, buffer, header.length, data.length);
		
		UDPSender udp = new UDPSender();
		if(packet.getDstIP() != null)
			sendReturn = udp.send(buffer, packet.getDstIP());
		else
			sendReturn = udp.send(buffer);
			
		
		return sendReturn;
	}
	
	/**
	 * This method is used to receive a message from another device.
	 * It's create a new thread to wait the packets.
	 * 
	 * @return
	 * <ul>
	 * 	<li>0: Listener create successfully</li>
	 * 	<li>1: Listener was already create</li>
	 * </ul>
	 */
	public synchronized int acceptListener(final CommunicationPacket packet, final ReceiveListener listener){
		if(alreadyInitializeListener)
			return 1;
		
		alreadyInitializeListener = true;
		Thread t = new Thread(new Runnable() {
			
			public void run() {
				UDPReceiver udp = new UDPReceiver();
				udp.receive(packet, listener);
			}
		});
		t.start();
		
		return 0;
	}

}
