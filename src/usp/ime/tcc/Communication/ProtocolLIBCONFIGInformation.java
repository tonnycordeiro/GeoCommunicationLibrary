package usp.ime.tcc.Communication;

import java.io.Serializable;

import usp.ime.tcc.Auxiliaries.Device;

public class ProtocolLIBCONFIGInformation extends ProtocolInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	private LibConfigurationObject libConfig;

	public ProtocolLIBCONFIGInformation(Device deviceSrc, AppProtocol app, LibConfigurationObject libConfig) {
		super(deviceSrc, app);
		this.setLibConfig(libConfig);
	}

	public LibConfigurationObject getLibConfig() {
		return libConfig;
	}

	public void setLibConfig(LibConfigurationObject libConfig) {
		this.libConfig = libConfig;
	}

}
