package usp.ime.gclib.net.communication;

import usp.ime.gclib.net.protocol.ProtocolGEOMSGInformation;
import usp.ime.gclib.net.protocol.ProtocolInformation;
import usp.ime.gclib.net.protocol.ProtocolLIBCONFIGInformation;

public interface IReceiveListener {

	public void onReceiveGEOMSG(ProtocolGEOMSGInformation appInfo);
	
	public void onReceiveAPPDATA(ProtocolInformation appInfo);
	
	public void onReceiveONLINE(ProtocolInformation appInfo);
	
	public void onReceiveLIBCONFIG(ProtocolLIBCONFIGInformation appInfo);

	public void onReceiveGEOACK(ProtocolInformation appInfo);

	public void onReceiveONLINEANSWER(ProtocolInformation appInfo);
	
}
