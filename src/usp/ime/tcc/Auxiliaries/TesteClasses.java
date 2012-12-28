package usp.ime.tcc.Auxiliaries;

import usp.ime.tcc.Communication.AppProtocol;
import usp.ime.tcc.Communication.CommunicationSocket;
import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolMessages;
import usp.ime.tcc.SenderAndReceiver.ReceiveListener;
import android.content.Context;

public class TesteClasses implements ReceiveListener{

	private static Context context;
	
	
	public void onReceiveGEOMSG(ProtocolInformation appInfo) {
		/*System.out.println("IP DO CARA - " + deviceSource.getIp());
		System.out.println("MEU IP - " + IP.getLocalIpAddress());
		System.out.println("NICK DO CARA - " + deviceSource.getNick());
		System.out.println("LATITUDE DO CARA - " + deviceSource.getLatitude());*/
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
	 	AppProtocol o = new AppProtocol(dev, s.getBytes(), ProtocolMessages.GEOMSG);
		ret = sndSocket.sendMessage(o);
		
		System.out.println("Envio " + ret);
		
	}

}
