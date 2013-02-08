package usp.ime.gclib.net.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import usp.ime.gclib.Device;
import usp.ime.gclib.hit.TargetRestrictions;
import usp.ime.gclib.net.protocol.ProtocolInformation;

public class TCPReceiver extends Receiver implements Runnable{

	protected static int SERVERPORT = 2389;
	protected static ServerSocket serverSocket;
	
	protected TCPReceiver(IReceiveListener listener, Device receiverDevice) {
		super(listener, receiverDevice);
	}
	
	protected TCPReceiver(IReceiveListener listener, Device receiverDevice, TargetRestrictions targetRestrictions) {
		super(listener,targetRestrictions, receiverDevice);
	}
	
	public void run() {
		
		try {
			serverSocket = new ServerSocket(SERVERPORT);
			while(true) {
				Socket connection;
				try {
					connection = serverSocket.accept();
				}
				catch(SocketException e){
					e.printStackTrace();
					break;
				}
				ObjectInputStream inFromClient = new ObjectInputStream(connection.getInputStream());
				
				try {
					ProtocolInformation appInfo = (ProtocolInformation) inFromClient.readObject();
					
					if(isValidDestination(appInfo)){
						activateEvents(appInfo);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
