package org.smacktest.listener;

import java.awt.TrayIcon.MessageType;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

public class MessageListenerTest implements MessageListener {

	public void processMessage(Chat chat, Message message) {
		if(message.getType() == Type.chat)
			System.out.println(message.getFrom() + " -> " + message.getBody());
		
	}
	
	
	

}
