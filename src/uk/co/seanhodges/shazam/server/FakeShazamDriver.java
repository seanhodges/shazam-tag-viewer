package uk.co.seanhodges.shazam.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * This is a fake driver for testing the app offline
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class FakeShazamDriver implements IShazamDriver {

	@Override
	public InputStream getTagRssFeed(String userName) throws IOException,
			URISyntaxException {
		
		InputStream is = getClass().getResourceAsStream("test_rss_feed.xml");
		return is;
	}

}
