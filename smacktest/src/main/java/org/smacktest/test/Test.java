package org.smacktest.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jivesoftware.smack.packet.Presence;
import org.smacktest.core.Connection;

public class Test {
	
	private static final String PROPERTIES_FILE = "config.properties"; 
	private static String USERNAME;
	private static String PASSWORD;
	private static String SERVICE_NAME;
	private static String WINDOWS_USER;
	private static String LINUX_USER;
	
	public static void main(String[] args) {
		
		new Test().getProperties();
		
		Connection connection = new Connection();
		connection.connect();
		
		connection.login(USERNAME, PASSWORD);
		
		
		
		Presence presencePacket = new Presence(Presence.Type.subscribe);
		presencePacket.setTo(getJID(WINDOWS_USER));
		connection.sendPacket(presencePacket);
		
		
		int ch = ' ';
		try {
			while(ch != 'X' && ch != 'x') {
				ch = System.in.read();
				if(ch == 'L' || ch == 'l')
					connection.publishPEP();
				else if(ch == 'M' || ch == 'm')
					connection.sendMessage(getJID(WINDOWS_USER), "Linux Message...");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		presencePacket = new Presence(Presence.Type.unsubscribe);
		presencePacket.setTo(getJID(WINDOWS_USER));
		connection.sendPacket(presencePacket);
		System.out.println("Disconnecting...");
		connection.disconnect();
		
	}
	
	private static String getJID(String user) {
		return user + "@" + SERVICE_NAME;
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
		
	}

}
