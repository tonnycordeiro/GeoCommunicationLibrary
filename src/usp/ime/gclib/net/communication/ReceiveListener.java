package usp.ime.gclib.net.communication;

import usp.ime.gclib.net.protocol.ProtocolGEOACKInformation;
import usp.ime.gclib.net.protocol.ProtocolGEOMSGInformation;
import usp.ime.gclib.net.protocol.ProtocolInformation;
import usp.ime.gclib.net.protocol.ProtocolLIBCONFIGInformation;

public interface ReceiveListener {

	public void onReceiveGEOMSG(ProtocolGEOMSGInformation appInfo);
	
	public void onReceiveAPPDATA(ProtocolInformation appInfo);
	
	public void onReceiveONLINE(ProtocolInformation appInfo);
	
	public void onReceiveLIBCONFIG(ProtocolLIBCONFIGInformation appInfo);

	public void onReceiveGEOACK(ProtocolGEOACKInformation appInfo);

	public void onReceiveONLINEANSWER(ProtocolInformation appInfo);
	
}
