package SenderAndReceiver;

import Communication.CommunicationPacket;

public interface ReceiveListener {

	public void onReceiveMessage(CommunicationPacket packet);
}
