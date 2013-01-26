package usp.ime.tcc.Auxiliaries;

import usp.ime.tcc.Communication.AppProtocol;
import usp.ime.tcc.Communication.CommunicationSocket;
import usp.ime.tcc.Communication.EProtocolMessages;
import usp.ime.tcc.Communication.EProtocolTranspLayer;
import usp.ime.tcc.Communication.ESendTo;
import usp.ime.tcc.Communication.LibConfigurationObject;
import usp.ime.tcc.Communication.ProtocolGEOACKInformation;
import usp.ime.tcc.Communication.ProtocolGEOMSGInformation;
import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolLIBCONFIGInformation;
import usp.ime.tcc.LocationAndOrientation.DeviceLocation;
import usp.ime.tcc.SenderAndReceiver.ReceiveListener;
import android.content.Context;

import com.example.communicationbyorientation.DeviceGyroscopeOrientation;
import com.example.communicationbyorientation.ShootRestrictions;
import com.example.communicationbyorientation.TargetRestrictions;

public class TesteClasses implements ReceiveListener{

	private static Context context;
	
	public void onReceiveGEOMSG(ProtocolGEOMSGInformation appInfo) {
		System.out.println("NICK do cara: " + appInfo.getDeviceSrc().getNick());
	}
	
	public static void main(String[] args) {
		System.out.println("Recebendo...");
		CommunicationSocket socket = new CommunicationSocket();
		TesteClasses listener = new TesteClasses();
		int ret = socket.acceptListener(listener);
		System.out.println("Recebimento " + ret +"\n");
		
		System.out.println("Enviando...");
		String s = "Mensagem";
		CommunicationSocket sndSocket = new CommunicationSocket();
		Device dev = new Device("nick");
		
		DeviceGyroscopeOrientation devOr = new DeviceGyroscopeOrientation(dev);
		DeviceLocation devLoc = new DeviceLocation(dev);
		
		devLoc.enableLocationListener(context, 0, 0);
		devOr.enableSensorListener(context);
		
	 	AppProtocol o = new AppProtocol(s.getBytes(), EProtocolMessages.GEOMSG, ESendTo.ALL, EProtocolTranspLayer.UDP);
	 	TargetRestrictions targetRest = new TargetRestrictions();
	 	ShootRestrictions shootRest = new ShootRestrictions();
	 	LibConfigurationObject libConfig = new LibConfigurationObject(targetRest, shootRest);
	 	ProtocolLIBCONFIGInformation pc = new ProtocolLIBCONFIGInformation(dev, o, libConfig);
		ret = sndSocket.sendMessage(o, pc);
		
		System.out.println("Envio " + ret);
		
	}

	public void onReceiveAPPDATA(ProtocolInformation appInfo) {
		
	}

	public void onReceiveONLINE(ProtocolInformation appInfo) {
		
	}

	public void onReceiveLIBCONFIG(ProtocolLIBCONFIGInformation appInfo) {
		
	}

	public void onReceiveGEOACK(ProtocolGEOACKInformation appInfo) {
		
	}

	public void onReceiveONLINEANSWER(ProtocolInformation appInfo) {
		
	}

}
