package usp.ime.tcc.SenderAndReceiver;

import usp.ime.tcc.Communication.ProtocolGEOACKInformation;
import usp.ime.tcc.Communication.ProtocolGEOMSGInformation;
import usp.ime.tcc.Communication.ProtocolInformation;
import usp.ime.tcc.Communication.ProtocolLIBCONFIGInformation;

public interface ReceiveListener {

	public void onReceiveGEOMSG(ProtocolGEOMSGInformation appInfo);
	
	public void onReceiveAPPDATA(ProtocolInformation appInfo);
	
	public void onReceiveONLINE(ProtocolInformation appInfo);
	
	public void onReceiveLIBCONFIG(ProtocolLIBCONFIGInformation appInfo);

	public void onReceiveGEOACK(ProtocolGEOACKInformation appInfo);

	public void onReceiveONLINEANSWER(ProtocolInformation appInfo);
	
}
