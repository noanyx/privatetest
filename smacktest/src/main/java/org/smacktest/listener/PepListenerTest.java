package org.smacktest.listener;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.pep.PEPListener;
import org.jivesoftware.smackx.pep.packet.PEPEvent;
import org.smacktest.event.UserLocationEvent;

public class PepListenerTest implements PEPListener {

	private final XMPPConnection xmppConnection;
	
	public PepListenerTest(XMPPConnection xmppConnection) {
		this.xmppConnection = xmppConnection;
	}
	
	public void eventReceived(String from, PEPEvent event) {
		UserLocationEvent userLocationEvent = (UserLocationEvent) event;
		if(xmppConnection.getUser().startsWith(from))
			return;
		System.out.println("Get PEPEvent from : " + from + "\n\tlat : " + userLocationEvent.getLocation().latitude + "\n\tlon : " + userLocationEvent.getLocation().longitude);
		
		
	}
	

}
