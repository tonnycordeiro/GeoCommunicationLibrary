package usp.ime.gclib.net.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * This class function is organize informations about a message that will be sent through network.
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class AppProtocol implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<String> listIpDst;
	private EProtocolMessages typeMsg;
	private ESendTo sendTo;
	private EProtocolTranspLayer protocol;
	
	/**
	 * If sendTo parameter is set to ALL, so protocol parameter must be set to UDP,
	 * otherwise will be throws a IllegalArgumentException. 
	 * 
	 * @param typeMsg
	 * @param sendTo
	 * @param protocol
	 */
	public AppProtocol(EProtocolMessages typeMsg, ESendTo sendTo, EProtocolTranspLayer protocol) {
		if(sendTo == ESendTo.ALL && protocol != EProtocolTranspLayer.UDP)
			throw new IllegalArgumentException("Send message to ALL is only available for UDP protocol");
		this.typeMsg = typeMsg;
		this.sendTo = sendTo;
		this.protocol = protocol;
	}
	
	/**
	 * The ESendTo attribute will be set to LISTIPS.
	 * 
	 * @param typeMsg
	 * @param listIpDst
	 * @param protocol
	 */
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
