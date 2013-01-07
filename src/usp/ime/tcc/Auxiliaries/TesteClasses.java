package usp.ime.tcc.Auxiliaries;

import usp.ime.tcc.Communication.AppProtocol;
import usp.ime.tcc.Communication.CommunicationSocket;
import usp.ime.tcc.Communication.EProtocolMessages;
import usp.ime.tcc.Communication.EProtocolTranspLayer;
import usp.ime.tcc.Communication.ESendTo;
import usp.ime.tcc.Communication.ProtocolGEOSMSGInformation;
import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolSTSAPPInformation;
import usp.ime.tcc.SenderAndReceiver.ReceiveListener;
import android.content.Context;

public class TesteClasses implements ReceiveListener{

	private static Context context;
	
	
	public void onReceiveGEOMSG(ProtocolGEOSMSGInformation appInfo) {
		System.out.println("NICK do cara: " + appInfo.getNick());
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
		Device dev = new Device("nick", context);
		dev.initializeLocation(0, 0);
		dev.initializeOrientation();
	 	AppProtocol o = new AppProtocol(dev, s.getBytes(), EProtocolMessages.GEOMSG, ESendTo.ALL, EProtocolTranspLayer.UDP);
		ret = sndSocket.sendMessage(o);
		
		System.out.println("Envio " + ret);
		
	}

	public void onReceiveAPPDATA(ProtocolInformation appInfo) {
		// TODO Auto-generated method stub
		
	}

	public void onReceiveONLINE(ProtocolInformation appInfo) {
		// TODO Auto-generated method stub
		
	}

	public void onReceiveSTSAPP(ProtocolSTSAPPInformation appInfo) {
		// TODO Auto-generated method stub
		
	}

}
