package org.smacktest.test;

import java.io.Console;
import java.io.IOException;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.smacktest.core.Connection;
import org.smacktest.listener.MessageListenerTest;

public class Test {
	
	private static final String USERNAME = "windows";
	private static final String PASSWORD = "ercan";
	
	public static void main(String[] args) {
		
		Connection connection = new Connection();
		connection.connect();
		
		connection.login(USERNAME, PASSWORD);
		
		
		
		Presence presencePacket = new Presence(Presence.Type.subscribe);
		presencePacket.setTo("linux@ercan.net");
		connection.sendPacket(presencePacket);
		
		
		int ch = ' ';
		try {
			while(ch != 'X' && ch != 'x') {
				ch = System.in.read();
				if(ch == 'L' || ch == 'l')
					connection.publishPEP();
				else if(ch == 'M' || ch == 'm')
					connection.sendMessage("linux@ercan.net", "Windows Message...");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		presencePacket = new Presence(Presence.Type.unsubscribe);
		presencePacket.setTo("linux@ercan.net");
		connection.sendPacket(presencePacket);
		System.out.println("Disconnecting...");
		connection.disconnect();
		
	
		
		
		
		
		
		
		
		
	}

}
