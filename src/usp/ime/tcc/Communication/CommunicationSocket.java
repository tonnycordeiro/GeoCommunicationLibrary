package usp.ime.tcc.Communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import usp.ime.tcc.Auxiliaries.IP;
import usp.ime.tcc.SenderAndReceiver.ReceiveListener;
import usp.ime.tcc.SenderAndReceiver.TCPReceiver;
import usp.ime.tcc.SenderAndReceiver.TCPSender;
import usp.ime.tcc.SenderAndReceiver.UDPReceiver;
import usp.ime.tcc.SenderAndReceiver.UDPSender;


/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class CommunicationSocket {

	private Thread threadUdp;
	private Thread threadTcp;
	
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
	public int sendMessage(AppProtocol protocol){
		int sendReturn = 1;
		
		byte[] header = new byte[1024]; //TODO Pode ser um problema no futuro. Pensar num jeito de contornar isso.
		
		ProtocolInformation appInfo;
		if(protocol.getTypeMsg() == EProtocolMessages.GEOMSG)
			appInfo = new ProtocolGEOSMSGInformation(protocol);
		else if(protocol.getTypeMsg() == EProtocolMessages.GEOACK)
			appInfo = new ProtocolGEOACKInformation(protocol, 10); //TODO como fazer pra pegar esse argumento??
		else if(protocol.getTypeMsg() == EProtocolMessages.LIBCONFIG)
			appInfo = new ProtocolLIBCONFIGInformation(protocol, 2, 10); //TODO como fazer pra pegar esse argumento??
		else
			appInfo = new ProtocolInformation(protocol);
		
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
			sendReturn = udp.send(header, IP.getBroadcastAddress(protocol.getDeviceSrc().getIp()));
		}
		else if(protocol.getSendTo() == ESendTo.GATEWAY){
			sendReturn = udp.send(header, IP.getGatewayAddress(protocol.getDeviceSrc().getIp()));
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
	public synchronized int acceptListener(final ReceiveListener listener){
		if(alreadyInitializeListener)
			return 1;
		
		alreadyInitializeListener = true;
		threadUdp = new Thread(new UDPReceiver(listener));
		threadTcp = new Thread(new TCPReceiver(listener));
		
		threadUdp.start();
		threadTcp.start();
		
		return 0;
	}
	
	public int stopListener() {
		UDPReceiver.serverSocket.close();
		try {
			TCPReceiver.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}

}
