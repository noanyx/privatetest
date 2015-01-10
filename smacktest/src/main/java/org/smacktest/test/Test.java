package org.smacktest.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.smacktest.core.Connection;

public class Test {
	
	private static final String PROPERTIES_FILE = "config.properties"; 
	private static String USERNAME;
	private static String PASSWORD;
	private static String SERVICE_NAME;
	private static String WINDOWS_USER;
	private static String LINUX_USER;
	private static String MAC_USER;
	
	public static void main(String[] args) {
		
		new Test().getProperties();
		
		Connection connection = new Connection();
		connection.connect();
		
		connection.login(USERNAME, PASSWORD);
		
		sendPresence(connection, LINUX_USER, Presence.Type.subscribe);
		sendPresence(connection, MAC_USER, Presence.Type.subscribe);
		
		
		int ch = ' ';
		try {
			while(ch != 'X' && ch != 'x') {
				ch = System.in.read();
				if(ch == 'L' || ch == 'l')
					connection.publishPEP();
				else if(ch == 'M' || ch == 'm')
					connection.sendMessage(getJID(LINUX_USER), "Windows Message...");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		sendPresence(connection, LINUX_USER, Presence.Type.unsubscribe);
		sendPresence(connection, MAC_USER, Presence.Type.unsubscribe);
		System.out.println("Disconnecting...");
		connection.disconnect();
		
	}
	
	private static String getJID(String user) {
		return user + "@" + SERVICE_NAME;
	}
	
	private static void sendPresence(Connection conn, String user, Type type) {
		Presence presencePacket = new Presence(type);
		presencePacket.setTo(getJID(user));
		conn.sendPacket(presencePacket);
	}
	
	private void getProperties() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
		Properties properties = new Properties();
		if(is != null)
			try {
				properties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		USERNAME = properties.getProperty("user");
		PASSWORD = properties.getProperty("password");
		SERVICE_NAME = properties.getProperty("serviceName");
		WINDOWS_USER = properties.getProperty("windowsUser");
		LINUX_USER = properties.getProperty("linuxUser");
		MAC_USER = properties.getProperty("macUser");
	}

}
