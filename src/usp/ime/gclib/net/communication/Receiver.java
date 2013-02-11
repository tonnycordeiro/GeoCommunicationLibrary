package usp.ime.gclib.net.communication;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import usp.ime.gclib.Device;
import usp.ime.gclib.hit.GeodesicManager;
import usp.ime.gclib.hit.HitCalculations;
import usp.ime.gclib.hit.ShootingRestrictions;
import usp.ime.gclib.hit.TargetRestrictions;
import usp.ime.gclib.net.protocol.ProtocolGEOMSGInformation;
import usp.ime.gclib.net.protocol.ProtocolInformation;
import usp.ime.gclib.net.protocol.ProtocolLIBCONFIGInformation;
import android.os.Environment;
import android.util.Log;

/**
 * This class is for internal use, it must not called.
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class Receiver{
	protected IReceiveListener listener;
	private TargetRestrictions targetRestrictions;
	private Device receiverDevice;

	FileWriter fileOutput;
	DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");
	
	protected Receiver(IReceiveListener listener, Device receiverDevice) {
		this.listener = listener;
		this.receiverDevice = receiverDevice;
		targetRestrictions = new TargetRestrictions();
		try{
			fileOutput = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/log_tcc.txt", true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected Receiver(IReceiveListener listener, Device receiverDevice, TargetRestrictions targetRestrictions) {
		this.listener = listener;
		this.receiverDevice = receiverDevice;
		this.targetRestrictions = targetRestrictions;
		try{
			fileOutput = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/log_tcc.txt", true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	protected Receiver(IReceiveListener listener, TargetRestrictions targetRestrictions, Device receiverDevice) {
		this.listener = listener;
		this.targetRestrictions = targetRestrictions;
		this.receiverDevice = receiverDevice;
	}
	
	protected TargetRestrictions getTargetRestrictions() {
		return targetRestrictions;
	}

	protected void setTargetRestrictions(TargetRestrictions targetRestrictions) {
		this.targetRestrictions = targetRestrictions;
	}	
	
	protected void activateEvents(ProtocolInformation appInfo){
		switch(appInfo.getTypeMsg()){
			case GEOMSG:
				listener.onReceiveGEOMSG((ProtocolGEOMSGInformation)appInfo);
				break;
			case GEOACK:
				listener.onReceiveGEOACK(appInfo);
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
	
	protected boolean isValidDestination(ProtocolInformation appInfo) {
		if(receiverDevice.getIp().equals(appInfo.getDeviceSrc().getIp()))
			return false;
		switch(appInfo.getTypeMsg()){
			case GEOMSG:
				
				if(!appInfo.getDeviceSrc().getDeviceLocation().isDefined() || 
						!receiverDevice.getDeviceLocation().isDefined())
					return false;
				
				ProtocolGEOMSGInformation geoData;
				HitCalculations hitCalculations;
				
				geoData = (ProtocolGEOMSGInformation) appInfo;
				hitCalculations = new HitCalculations();
				
				if(geoData.getShootRestrictions() == null)
					hitCalculations.setShootingRestrictions(new ShootingRestrictions());
				else
					hitCalculations.setShootingRestrictions(geoData.getShootRestrictions());
				hitCalculations.setTargetRestrictions(getTargetRestrictions());
				Device devVirtual = new Device();
				boolean isForMe =  hitCalculations.hitTheDestination(Math.toDegrees(appInfo.getDeviceSrc().getDeviceOrientation().getAzimuth()), 
														  appInfo.getDeviceSrc(),
														  receiverDevice, devVirtual);
				String s = "hora:" + dateFormat.format(new Date()) +
						" evt:" + "recebe" +
						" ip_src:" + appInfo.getDeviceSrc().getIp() + 
						" lat_src:" + appInfo.getDeviceSrc().getDeviceLocation().getLatitude() +
						" long_src:" + appInfo.getDeviceSrc().getDeviceLocation().getLongitude() +
						" azi_src:" + Math.toDegrees(appInfo.getDeviceSrc().getDeviceOrientation().getAzimuth()) +
						" ip_dst:" + receiverDevice.getIp() + 
						" lat_dst:" + receiverDevice.getDeviceLocation().getLatitude() +
						" long_dst:" + receiverDevice.getDeviceLocation().getLongitude() +
						" azi_rel:" + GeodesicManager.azimuthBetween(appInfo.getDeviceSrc(), receiverDevice) +
						" lat_devVirtual:" + devVirtual.getDeviceLocation().getLatitude() + 
						" long_devVirtual:" + devVirtual.getDeviceLocation().getLongitude() +
						" dist_virtual_destino:" + GeodesicManager.distanceBetween(receiverDevice, devVirtual) +
						"m raio_dst:" + getTargetRestrictions().getRadius() + "m" +
						" acertou:" + (isForMe ? "SIM" : "NAO") + "\n\n";
				Log.d("TCC_LOG", s);
				
				
				synchronized (fileOutput) {
        			try {
        				fileOutput.write(s);
        				fileOutput.flush();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		}
				
				return isForMe;
				
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
