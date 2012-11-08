package usp.ime.tcc.SenderAndReceiver;

import usp.ime.tcc.Communication.CommunicationPacket;

public interface ReceiveListener {

	public void onReceiveMessage(CommunicationPacket packet);
}
