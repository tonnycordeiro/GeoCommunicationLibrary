package usp.ime.tcc.SenderAndReceiver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import usp.ime.tcc.Auxiliaries.IP;
import usp.ime.tcc.Communication.AppProtocol;
import usp.ime.tcc.Communication.EProtocolMessages;
import usp.ime.tcc.Communication.ESendTo;
import usp.ime.tcc.Communication.ProtocolGEOSMSGInformation;
import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolSTSAPPInformation;

public class TCPSender {

	public int send(AppProtocol protocol) {
		int sendReturn = 0;
		
		if(protocol.getSendTo() == ESendTo.ALL)
			return 2;
		
		ProtocolInformation appInfo;
		if(protocol.getTypeMsg() == EProtocolMessages.GEOMSG)
			appInfo = new ProtocolGEOSMSGInformation(protocol);
		else if(protocol.getTypeMsg() == EProtocolMessages.STSAPP)
			appInfo = new ProtocolSTSAPPInformation(protocol, 2, 10);
		else
			appInfo = new ProtocolInformation(protocol);
		
		if(protocol.getSendTo() == ESendTo.GATEWAY)
			sendReturn = send(appInfo, IP.getGatewayAddress(protocol.getDeviceSrc().getIp()));
		else {
			for (String ip : protocol.getListIpDst()) {
				sendReturn += send(appInfo, ip);
			}
			if(sendReturn > 1)
				sendReturn = 1;
		}
		
		return sendReturn;
	}
	
	private int send(ProtocolInformation appInfo, String ipDst) {
		try {
			InetAddress address = InetAddress.getByName(ipDst);
			
			Socket socket = new Socket(address, TCPReceiver.SERVERPORT);
			ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
			
			outToServer.writeObject(appInfo);
			
			socket.close();
			outToServer.close();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
			return 2;
		} 
		catch(IOException e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}
}