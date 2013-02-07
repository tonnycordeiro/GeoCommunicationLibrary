package usp.ime.gclib.net.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import usp.ime.gclib.Device;
import usp.ime.gclib.hit.TargetRestrictions;
import usp.ime.gclib.net.protocol.AppProtocol;
import usp.ime.gclib.net.protocol.EProtocolTranspLayer;
import usp.ime.gclib.net.protocol.ESendTo;
import usp.ime.gclib.net.protocol.ProtocolInformation;


/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class CommunicationSocket {

	private Thread threadUdp;
	private Thread threadTcp;
	private UDPReceiver udpReceiver;
	private TCPReceiver tcpReceiver;
	
	private boolean alreadyInitializeListener = false;
	
	/**
	 * This method will send the message that is specified in packet parameter.
	 * 
	 * @return A integer that indicates the result of the sending.
	 * The possibles values are:
	 * <ul>
	 * 	<li>0: Successful</li>
	 * 	<li>1: Unsuccessful</li>
	 * 	<li>2: Undefined error</li>
	 * </ul>
	 */
	public int sendMessage(AppProtocol protocol, ProtocolInformation appInfo){
		int sendReturn = 1;
		
		byte[] header = new byte[2048]; //TODO Pode ser um problema no futuro. Pensar num jeito de contornar isso.
		
		if(protocol.getProtocol() == EProtocolTranspLayer.TCP) {
			TCPSender tcp = new TCPSender();
			return tcp.send(protocol, appInfo);
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(baos);
			out.writeObject(appInfo);
			header = baos.toByteArray();
			
			out.flush();
			out.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return sendReturn;
		}

		
		UDPSender udp = new UDPSender();
		
		if(protocol.getSendTo() == ESendTo.LISTIPS){
			if(protocol.getListIpDst() == null)
				sendReturn = 2;
			else{
				for (String ip : protocol.getListIpDst()) {
					sendReturn += udp.send(header, ip);
				}
				if(sendReturn > 1)
					sendReturn = 1;
			}
		}
		else if(protocol.getSendTo() == ESendTo.ALL){
			sendReturn = udp.send(header, IP.getBroadcastAddress(appInfo.getDeviceSrc().getIp()));
		}
		else if(protocol.getSendTo() == ESendTo.GATEWAY){
			sendReturn = udp.send(header, IP.getGatewayAddress(appInfo.getDeviceSrc().getIp()));
		}
		
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
	public synchronized int acceptListener(final IReceiveListener listener, final Device receiverDevice){
		if(alreadyInitializeListener)
			return 1;
		
		alreadyInitializeListener = true;
		threadUdp = new Thread(new UDPReceiver(listener, receiverDevice));
		threadTcp = new Thread(new TCPReceiver(listener, receiverDevice));
		
		threadUdp.start();
		threadTcp.start();
		
		return 0;
	}
	
	public synchronized int acceptListener(final IReceiveListener listener, final Device receiverDevice, final TargetRestrictions targetRestrictions){
		if(alreadyInitializeListener)
			return 1;
		
		alreadyInitializeListener = true;
		tcpReceiver = new TCPReceiver(listener, receiverDevice, targetRestrictions);
		udpReceiver = new UDPReceiver(listener, receiverDevice, targetRestrictions);
		
		threadUdp = new Thread(udpReceiver);
		threadTcp = new Thread(tcpReceiver);
		
		threadUdp.start();
		threadTcp.start();
		
		return 0;
	}
	
	public int stopListener() {
		try {
			udpReceiver.closeSocket();
			tcpReceiver.closeSocket();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}

}
