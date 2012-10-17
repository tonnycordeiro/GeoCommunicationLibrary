package Auxiliaries;

import Communication.CommunicationPacket;
import Communication.CommunicationSocket;
import SenderAndReceiver.ReceiveListener;
import android.content.Context;

public class TesteClasses implements ReceiveListener{

	private Context context;
	public void onReceiveMessage(CommunicationPacket packet) {
		if(packet == null){
			System.out.println("Packet is null. Something wrong happens.");
			return;
		}
		System.out.println("Renato recebeu!");
		
		if(packet.getSenderType() == SenderType.BROADCAST_TO_ONE){
			CommunicationSocket socket = new CommunicationSocket();
			int ret = socket.sendResponse(new Device(context), new byte[1024], packet.getSrcIP());
			
			System.out.println("Resposta: " + ret);
		}
		else{
			System.out.println("Nao eh esperado resposta");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Recebendo...");
		CommunicationPacket packet = new CommunicationPacket(new byte[1024]);
		CommunicationSocket socket = new CommunicationSocket();
		TesteClasses listener = new TesteClasses();
		int ret = socket.acceptListener(packet, listener);
		System.out.println("Recebimento " + ret +"\n");
		
		/*System.out.println("Enviando...");
		String s = "Mensagem";
		CommunicationPacket sndPacket = new CommunicationPacket(new Device(), SenderType.BROADCAST_TO_ALL, s.getBytes());
		CommunicationSocket sndSocket = new CommunicationSocket();
		int ret = sndSocket.sendMessage(sndPacket);
		System.out.println("Envio " + ret);*/
	}

}
