package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.Device;

/**
 * This class extends ProtocolInformation and use a LibConfigurationObject object for additional informations.
 * @see LibConfigurationObject
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class ProtocolLIBCONFIGInformation extends ProtocolInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	private LibConfigurationObject libConfig;

	public ProtocolLIBCONFIGInformation(Device deviceSrc, byte[] message, AppProtocol app, LibConfigurationObject libConfig) {
		super(deviceSrc, message, app);
		this.libConfig = libConfig;
	}
	/**
	 * The object parameter must be Serializable, otherwise it will be throws a IllegalArgumentException.
	 * 
	 * @param deviceSrc
	 * @param object
	 * @param app
	 * @param libConfig
	 */
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
