package usp.ime.tcc.Communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import usp.ime.tcc.SenderAndReceiver.ReceiveListener;
import usp.ime.tcc.SenderAndReceiver.UDPReceiver;
import usp.ime.tcc.SenderAndReceiver.UDPSender;


/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class CommunicationSocket {

	private Thread t;
	private boolean alreadyInitializeListener = false;
	
	/**
	 * This method will send the message that is specified in packet parameter.
	 * 
	 * @return A integer that indicates the result of the sending.
	 * The possibles values are:
	 * <ul>
	 * 	<li>0: Successful</li>
	 * 	<li>1: Unsuccessful</li>
	 * 	<li>2: Undefined error</li>
	 * </ul>
	 */
	public int sendMessage(AppProtocol protocol){
		int sendReturn = 1;
		
		byte[] header = new byte[1024];
		
		ProtocolInformation appInfo = new ProtocolInformation(protocol);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(baos);
			out.writeObject(appInfo);
			header = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return sendReturn;
		}
		
		//Copia dois byte[] em outro byte[]
		/*byte[] data = protocol.getMessage();
		byte[] buffer = new byte[header.length + data.length];

		System.arraycopy(header, 0, buffer, 0, header.length);
		System.arraycopy(data, 0, buffer, header.length, data.length);*/

		UDPSender udp = new UDPSender();
		
		sendReturn = udp.send(header, appInfo.getIp());
		
		try {
			out.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sendReturn;
	}
	
	/**
	 * This method is used to receive a message from another device.
	 * It's create a new thread to wait the packets.
	 * 
	 * @return
	 * <ul>
	 * 	<li>0: Listener create successfully</li>
	 * 	<li>1: Listener was already create</li>
	 * </ul>
	 */
	public synchronized int acceptListener(final ReceiveListener listener){
		if(alreadyInitializeListener)
			return 1;
		
		alreadyInitializeListener = true;
		t = new Thread(new UDPReceiver(listener));
		t.start();
		
		return 0;
	}

}
