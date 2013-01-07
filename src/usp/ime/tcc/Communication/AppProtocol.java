package usp.ime.tcc.Communication;

import java.io.Serializable;
import java.util.List;

import usp.ime.tcc.Auxiliaries.Device;

public class AppProtocol implements Serializable{
	private static final long serialVersionUID = 1L;
	final static String CRLF = "\r\n";
	final static String dividerHeader = " ";
	
	private Device deviceSrc;
	private List<String> listIpDst;
	private byte[] message;
	private EProtocolMessages typeMsg;
	private ESendTo sendTo;
	private EProtocolTranspLayer protocol;
	
	public AppProtocol(Device deviceSrc, byte[] message, EProtocolMessages typeMsg, ESendTo sendTo, EProtocolTranspLayer protocol) {
		this.deviceSrc = deviceSrc;
		this.message = message;
		this.typeMsg = typeMsg;
		this.sendTo = sendTo;
		this.protocol = protocol;
	}
	
	public AppProtocol(Device deviceSrc, byte[] message, EProtocolMessages typeMsg, List<String> listIpDst, EProtocolTranspLayer protocol) {
		this.deviceSrc = deviceSrc;
		this.message = message;
		this.typeMsg = typeMsg;
		this.listIpDst = listIpDst;
		this.protocol = protocol;
		this.sendTo = ESendTo.LISTIPS;
	}

	protected void setListIpDst(List<String> listIpDst) {
		this.listIpDst = listIpDst;
	}

	public List<String> getListIpDst() {
		return this.listIpDst;
	}
	public Device getDeviceSrc() {
		return this.deviceSrc;
	}

	public void setDeviceSrc(Device deviceSrc) {
		this.deviceSrc = deviceSrc;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public EProtocolMessages getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(EProtocolMessages typeMsg) {
		this.typeMsg = typeMsg;
	}

	public ESendTo getSendTo() {
		return sendTo;
	}

	public void setSendTo(ESendTo sendTo) {
		this.sendTo = sendTo;
	}

	public EProtocolTranspLayer getProtocol() {
		return protocol;
	}

	public void setProtocol(EProtocolTranspLayer protocol) {
		this.protocol = protocol;
	}
	
	
}
