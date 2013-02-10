package usp.ime.gclib.net.protocol;

import java.io.Serializable;
import java.util.List;

public class AppProtocol implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<String> listIpDst;
	private EProtocolMessages typeMsg;
	private ESendTo sendTo;
	private EProtocolTranspLayer protocol;
	
	public AppProtocol(EProtocolMessages typeMsg, ESendTo sendTo, EProtocolTranspLayer protocol) {
		this.typeMsg = typeMsg;
		this.sendTo = sendTo;
		this.protocol = protocol;
	}
	
	public AppProtocol(EProtocolMessages typeMsg, List<String> listIpDst, EProtocolTranspLayer protocol) {
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
