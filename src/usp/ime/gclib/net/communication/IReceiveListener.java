package usp.ime.gclib.net.communication;

import usp.ime.gclib.net.protocol.ProtocolGEOMSGInformation;
import usp.ime.gclib.net.protocol.ProtocolInformation;
import usp.ime.gclib.net.protocol.ProtocolLIBCONFIGInformation;

/**
 * This interface is used by application. When a new message arrives
 * from network, these methods will be called.
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public interface IReceiveListener {

	public void onReceiveGEOMSG(ProtocolGEOMSGInformation appInfo);
	
	public void onReceiveAPPDATA(ProtocolInformation appInfo);
	
	public void onReceiveONLINE(ProtocolInformation appInfo);
	
	public void onReceiveLIBCONFIG(ProtocolLIBCONFIGInformation appInfo);

	public void onReceiveGEOACK(ProtocolInformation appInfo);

	public void onReceiveONLINEANSWER(ProtocolInformation appInfo);
	
}
