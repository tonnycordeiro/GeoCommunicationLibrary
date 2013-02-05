package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.device.Device;

public class ProtocolLIBCONFIGInformation extends ProtocolInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	private LibConfigurationObject libConfig;

	public ProtocolLIBCONFIGInformation(Device deviceSrc, byte[] message, AppProtocol app, LibConfigurationObject libConfig) {
		super(deviceSrc, message, app);
		this.setLibConfig(libConfig);
	}
	
	public ProtocolLIBCONFIGInformation(Device deviceSrc, Object object, AppProtocol app, LibConfigurationObject libConfig) {
		super(deviceSrc, object, app);
		this.setLibConfig(libConfig);
	}

	public LibConfigurationObject getLibConfig() {
		return libConfig;
	}

	public void setLibConfig(LibConfigurationObject libConfig) {
		this.libConfig = libConfig;
	}

}
