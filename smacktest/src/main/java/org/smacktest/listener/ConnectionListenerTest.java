package org.smacktest.listener;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

public class ConnectionListenerTest implements ConnectionListener {

	private XMPPConnection xmppConnection;
	
	public ConnectionListenerTest(XMPPConnection xmppConnection) {
		this.xmppConnection = xmppConnection;
	}
	
	public void connected(XMPPConnection connection) {
		// TODO Auto-generated method stub
		
	}

	public void authenticated(XMPPConnection connection) {
		// TODO Auto-generated method stub
		
	}

	public void connectionClosed() {
		
	}

	public void connectionClosedOnError(Exception e) {
		// TODO Auto-generated method stub
		
	}

	public void reconnectingIn(int seconds) {
		// TODO Auto-generated method stub
		
	}

	public void reconnectionSuccessful() {
		// TODO Auto-generated method stub
		
	}

	public void reconnectionFailed(Exception e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
