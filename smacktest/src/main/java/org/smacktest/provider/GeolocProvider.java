package org.smacktest.provider;

import org.smacktest.event.UserLocationEvent;
import org.smacktest.item.UserLocation;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;

public class GeolocProvider implements PacketExtensionProvider {

	public PacketExtension parseExtension(XmlPullParser parser) throws Exception {
		boolean stop = false;
        String id = null;
        double latitude = 0;
        double longitude = 0;
        String openTag = null;

        while (!stop) {
            int eventType = parser.next();

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    openTag = parser.getName();
                    if ("item".equals(openTag)) {
                        id = parser.getAttributeValue("", "id");
                    }

                    break;

                case XmlPullParser.TEXT:
                    if ("lat".equals(openTag)) {
                        try {
                            latitude = Double.parseDouble(
                                parser.getText());
                        } catch (NumberFormatException ex) {
                            /* ignore */
                        }
                    } else if ("lon".equals(openTag)) {
                        try {
                            longitude = Double.parseDouble(
                                parser.getText());
                        } catch (NumberFormatException ex) {
                            /* ignore */
                        }
                    }

                    break;

                case XmlPullParser.END_TAG:
                    // Stop parsing when we hit </item>
                    stop = "item".equals(parser.getName());
                    openTag = null;
                    break;
            }
        }

        return new UserLocationEvent(
            new UserLocation(id, latitude, longitude));
	}

}
