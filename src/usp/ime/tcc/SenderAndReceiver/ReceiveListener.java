package usp.ime.tcc.SenderAndReceiver;

import usp.ime.tcc.Communication.ProtocolInformation;

public interface ReceiveListener {

	public void onReceiveGEOMSG(ProtocolInformation appInfo);
	
}
