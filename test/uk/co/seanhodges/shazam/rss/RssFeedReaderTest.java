package uk.co.seanhodges.shazam.rss;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.test.AndroidTestCase;

import uk.co.seanhodges.shazam.model.FeedChannel;
import uk.co.seanhodges.shazam.model.FeedItem;

public class RssFeedReaderTest extends AndroidTestCase {
	
	public void testParsingAnRssFeed() throws Exception {
		InputStream is = getClass().getResourceAsStream("test_rss_feed.xml");
		
		// Load the RSS XML
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		// Parse the feed
		RssFeedReader handler = new RssFeedReader();
		xr.setContentHandler(handler);
		InputSource isource = new InputSource(is);
		isource.setEncoding(RssFeedStatics.RSS_ENCODING);
		xr.parse(isource); 
		
		FeedChannel feed = handler.getFeed(); 
		
		// Ensure the feed was loaded correctly
		assertNotNull(feed);
		assertNotNull(feed.getEntries());
		assertNotSame("Feed list was empty", 0, feed.getEntries());
		
		for (FeedItem item : feed.getEntries()) {
			assertNotNull(item);
			assertNotSame(0, item.getTrackName().length());
			assertNotSame(0, item.getTrackArtist().length());
			assertNotSame(0, item.getLink().getHost().length());
		}
	}

}
