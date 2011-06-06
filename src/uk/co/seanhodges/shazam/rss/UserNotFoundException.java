package uk.co.seanhodges.shazam.rss;

import org.xml.sax.SAXParseException;

public class UserNotFoundException extends SAXParseException {

	private static final long serialVersionUID = 6802426858649850237L;
	
	public UserNotFoundException() {
		super("User not found", null);
	}
}
