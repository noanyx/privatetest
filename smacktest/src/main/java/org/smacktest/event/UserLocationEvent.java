package org.smacktest.event;

import org.smacktest.item.UserLocation;

import org.jivesoftware.smackx.pep.packet.PEPEvent;

public class UserLocationEvent extends PEPEvent {
	private final UserLocation location;

    public UserLocationEvent(UserLocation location) {
        this.location = location;
    }

    public UserLocation getLocation() {
        return location;
    }

    @Override
    public String getNamespace() {
        return "http://jabber.org/protocol/pubsub#event";
    }

    @Override
    public String toXML() {
        return String.format("<event xmlns=" +
            "'http://jabber.org/protocol/pubsub#event' >" +
            "<items node='%s' >%s</items></event>",
            UserLocation.NODE, location.toXML());
    }
}
