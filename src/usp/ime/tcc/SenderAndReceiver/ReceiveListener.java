package usp.ime.tcc.SenderAndReceiver;

import usp.ime.tcc.Communication.ProtocolGEOSMSGInformation;
import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolSTSAPPInformation;

public interface ReceiveListener {

	public void onReceiveGEOMSG(ProtocolGEOSMSGInformation appInfo);
	
	public void onReceiveAPPDATA(ProtocolInformation appInfo);
	
	public void onReceiveONLINE(ProtocolInformation appInfo);
	
	public void onReceiveSTSAPP(ProtocolSTSAPPInformation appInfo);
	
}
