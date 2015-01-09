package org.smacktest.item;

import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.pep.packet.PEPItem;

public class UserLocation extends PEPItem {

	public static final String NODE = "http://jabber.org/protocol/geoloc";

	public final double latitude, longitude;

	public UserLocation(double latitude, double longitude) {
		this(StringUtils.randomString(16), latitude, longitude);
	}

	public UserLocation(String id, double latitude, double longitude) {
		super(id);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	@Override
	public String getNode() {
		return NODE;
	}

	// return an XML element approximately inline
			// with the XEP-0080 spec
	@Override
	public String getItemDetailsXML() {
		return String.format(
				"<geoloc xmlns='%s'><lat>%f</lat>" +
						"<lon>%f</lon></geoloc>",
						NODE, latitude, longitude);
	}



}
