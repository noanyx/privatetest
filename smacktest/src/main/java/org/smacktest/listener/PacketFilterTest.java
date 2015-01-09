package org.smacktest.listener;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class PacketFilterTest implements PacketFilter {

	/**
	 * Accepts all type of packets.
	 */
	public boolean accept(Packet packet) {
		return true;
	}
	
	

}
