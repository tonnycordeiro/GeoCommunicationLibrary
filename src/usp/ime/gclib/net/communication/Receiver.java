package usp.ime.gclib.net.communication;

import usp.ime.gclib.device.Device;
import usp.ime.gclib.hit.HitCalculations;
import usp.ime.gclib.hit.TargetRestrictions;
import usp.ime.gclib.net.protocol.ProtocolGEOACKInformation;
import usp.ime.gclib.net.protocol.ProtocolGEOMSGInformation;
import usp.ime.gclib.net.protocol.ProtocolInformation;
import usp.ime.gclib.net.protocol.ProtocolLIBCONFIGInformation;

public class Receiver{
	protected ReceiveListener listener;
	private TargetRestrictions targetRestrictions;
	private Device receiverDevice;

	public Receiver(ReceiveListener listener, Device receiverDevice) {
		this.listener = listener;
		this.receiverDevice = receiverDevice;
		targetRestrictions = new TargetRestrictions();
	}
	
	public Receiver(ReceiveListener listener, Device receiverDevice, TargetRestrictions targetRestrictions) {
		this.listener = listener;
		this.receiverDevice = receiverDevice;
		this.targetRestrictions = targetRestrictions;
	}

	public Receiver(ReceiveListener listener, TargetRestrictions targetRestrictions, Device receiverDevice) {
		this.listener = listener;
		this.targetRestrictions = targetRestrictions;
		this.receiverDevice = receiverDevice;
	}
	
	public TargetRestrictions getTargetRestrictions() {
		return targetRestrictions;
	}

	public void setTargetRestrictions(TargetRestrictions targetRestrictions) {
		this.targetRestrictions = targetRestrictions;
	}	
	
	protected void activateEvents(ProtocolInformation appInfo){
		switch(appInfo.getTypeMsg()){
			case GEOMSG:
				listener.onReceiveGEOMSG((ProtocolGEOMSGInformation)appInfo);
				break;
			case GEOACK:
				listener.onReceiveGEOACK((ProtocolGEOACKInformation)appInfo);
				break;
			case APPDATA:
				listener.onReceiveAPPDATA(appInfo);
				break;
			case ONLINE:
				listener.onReceiveONLINE(appInfo);
				break;
			case ONLINEANSWER:
				listener.onReceiveONLINEANSWER(appInfo);
				break;
			case LIBCONFIG:
				listener.onReceiveLIBCONFIG((ProtocolLIBCONFIGInformation)appInfo);
				break;
			default:
				break;
		}
	}
	
	protected boolean thisMessageIsForMe(ProtocolInformation appInfo) {
		switch(appInfo.getTypeMsg()){
			case GEOMSG:
				ProtocolGEOMSGInformation geoData;
				HitCalculations hitCalculations;
				
				geoData = (ProtocolGEOMSGInformation) appInfo;
				hitCalculations = new HitCalculations();
				
				hitCalculations.setShootingRestrictions(geoData.getShootRestrictions());
				hitCalculations.setTargetRestrictions(this.getTargetRestrictions());
				return hitCalculations.hitTheDestination(Math.toDegrees(appInfo.getDeviceSrc().getDeviceOrientation().getAzimuth()), 
														  appInfo.getDeviceSrc(),
														  receiverDevice);
				
			case GEOACK:
			case APPDATA:
			case ONLINE:
			case ONLINEANSWER:
			case LIBCONFIG:
				return true;
			default:
				break;
		}

		return true;
	}
}
