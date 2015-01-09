package org.smacktest.listener;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smackx.pep.packet.PEPEvent;
import org.jivesoftware.smackx.pep.packet.PEPPubSub;
import org.jivesoftware.smackx.pubsub.packet.PubSub;

public class PacketListenerTest implements PacketListener {

	private XMPPConnection xmppConnection;

	public PacketListenerTest(XMPPConnection xmppConnection) {
		this.xmppConnection = xmppConnection;
	}

	public void processPacket(Packet packet) throws NotConnectedException {

		if(packet instanceof Presence){
			Presence presence = (Presence)packet;
//			System.out.println("presence: " + presence.getFrom() + "; type: " + presence.getType() + "; to: " + presence.getTo() + "; " + presence.toXML());
			Roster roster = xmppConnection.getRoster();
			for(RosterEntry rosterEntry : roster.getEntries()){
//				System.out.println("jid: " + rosterEntry.getUser() + "; type: " + rosterEntry.getType() + "; status: " + rosterEntry.getStatus());
			}
//			System.out.println("\n\n\n");

			if( presence.getType().equals(Presence.Type.subscribe) ){
				Presence newp = new Presence(Presence.Type.subscribed);
				newp.setMode(Presence.Mode.available);
				newp.setPriority(24);
				newp.setTo(presence.getFrom());
				xmppConnection.sendPacket(newp);
			} else if(presence.getType().equals(Presence.Type.unsubscribe)){
				Presence newp = new Presence(Presence.Type.unsubscribed);
				newp.setMode(Presence.Mode.available);
				newp.setPriority(24);
				newp.setTo(presence.getFrom());
				xmppConnection.sendPacket(newp);
			}
			
			
		} else if(packet instanceof Message) {
			Message message = (Message) packet;
			
			
		} else if(packet instanceof PubSub) {
			PubSub pubSub = (PubSub) packet;
//			System.out.println("PubSub : " + pubSub.getElementName());
		} else if(packet instanceof PEPPubSub) {
			PEPPubSub pepPubSub = (PEPPubSub) packet;
//			System.out.println("PEPPubSub : " + pepPubSub.getElementName());
		} else if(packet instanceof RosterPacket) {
			RosterPacket rosterPacket = (RosterPacket) packet;
			
//			System.out.println(rosterPacket.toXML());
		} else {
//        	System.out.println(packet.getXmlns());
//        	System.out.println(packet.getClass().getName());
//        	System.out.println(packet.toXML().toString());
        }

	}

}
