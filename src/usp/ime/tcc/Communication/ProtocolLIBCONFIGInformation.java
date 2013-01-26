package usp.ime.tcc.Communication;

import java.io.Serializable;

import usp.ime.tcc.Auxiliaries.Device;

public class ProtocolLIBCONFIGInformation extends ProtocolInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	
	public ProtocolLIBCONFIGInformation(Device deviceSrc, AppProtocol app) {
		super(deviceSrc, app);
	}

}
