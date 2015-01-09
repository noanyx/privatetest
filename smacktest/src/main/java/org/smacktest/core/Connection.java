package org.smacktest.core;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.security.sasl.SaslException;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPConnection.FromMode;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ.Type;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.caps.EntityCapsManager;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.pep.PEPManager;
import org.jivesoftware.smackx.pep.provider.PEPProvider;
import org.smacktest.item.UserLocation;
import org.smacktest.listener.ConnectionListenerTest;
import org.smacktest.listener.MessageListenerTest;
import org.smacktest.listener.PacketFilterTest;
import org.smacktest.listener.PacketListenerSentTest;
import org.smacktest.listener.PacketListenerTest;
import org.smacktest.listener.PepListenerTest;
import org.smacktest.provider.GeolocProvider;
import org.smacktest.util.Constants;

public class Connection {
	
	private final XMPPConnection xmppConnection;
	private final PEPManager pepManager;
	
	

	public Connection() {
		ConnectionConfiguration config = new ConnectionConfiguration(Constants.HOST, Constants.PORT, Constants.DOMAIN);
		config.setSecurityMode(SecurityMode.disabled);
		xmppConnection = new XMPPTCPConnection(config);
		xmppConnection.setFromMode(FromMode.USER);
		xmppConnection.addPacketListener(new PacketListenerTest(xmppConnection), new PacketFilterTest());
		xmppConnection.addPacketSendingListener(new PacketListenerSentTest(), new PacketFilterTest());
		xmppConnection.addConnectionListener(new ConnectionListenerTest(xmppConnection));
		// add peplistener
		ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(xmppConnection);
		sdm.addFeature("http://jabber.org/protocol/geoloc");
		sdm.addFeature("http://jabber.org/protocol/geoloc+notify");

		EntityCapsManager capsManager = EntityCapsManager.getInstanceFor(xmppConnection);
		capsManager.enableEntityCaps();

		PEPProvider pepProvider = new PEPProvider();
		pepProvider.registerPEPParserExtension("http://jabber.org/protocol/geoloc",	new GeolocProvider());
		ProviderManager.addExtensionProvider("event", "http://jabber.org/protocol/pubsub#event", pepProvider);
		pepManager = new PEPManager(xmppConnection);
		pepManager.addPEPListener(new PepListenerTest(xmppConnection));
		
	}
	
	public void connect() {
		try {
			xmppConnection.connect();
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		System.out.println(xmppConnection.isConnected() ? "Connected!" : "Not Connected!");
	}
	
	public void sendPacket(Packet packet) {
		try {
			xmppConnection.sendPacket(packet);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public void publishPEP() {
		UserLocation userLocation = new UserLocation(10d, 6d);
		try {
			pepManager.publish(userLocation);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			xmppConnection.disconnect();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public void getRosters() {
		RosterPacket rosterPacket = new RosterPacket();
		rosterPacket.setType(Type.GET);
		rosterPacket.setFrom("linux@ercan.net");
		System.out.println("RosterPacket Sent : " + rosterPacket.toXML());
		try {
			xmppConnection.sendPacket(rosterPacket);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
		
		
		
//		for(RosterEntry rosterEntry : xmppConnection.getRoster().getEntries()) {
//			System.out.println("RosterEntry : " + rosterEntry.getUser() + rosterEntry.getName());
//		}
	}
	
	/**
	 * 
	 * @param username as username not JID
	 * @param password
	 */
	public void login(final String username, final String password) {
		try {
			xmppConnection.login(username, password);
		} catch (SaslException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(xmppConnection.isAuthenticated() ? "Authenticated!" : "Not Authenticated");
	}
	
	public XMPPConnection getXmppConnection() {
		return xmppConnection;
	}
	
	public void sendMessage(String to, String message) {
		ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
		chatManager.addChatListener(new ChatManagerListener() {
			
			public void chatCreated(Chat chat, boolean createdLocally) {
				chat.addMessageListener(new MessageListenerTest());
			}
		});
		Chat chat = chatManager.createChat(to, null);
		try {
			chat.sendMessage(message);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	

}
