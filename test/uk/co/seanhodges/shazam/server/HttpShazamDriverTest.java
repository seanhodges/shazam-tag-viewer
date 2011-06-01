package uk.co.seanhodges.shazam.server;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.*;

import uk.co.seanhodges.shazam.rss.RssFeedStatics;
import uk.co.seanhodges.shazam.server.HttpShazamDriver;

public class HttpShazamDriverTest {
	
	@Test
	public void testLoadingAnRssFeedFromShazam() throws Exception {
		IShazamDriver driver = new HttpShazamDriver();
		InputStream is = driver.getTagRssFeed("shazam");
		
		String output = IOUtils.toString(is, RssFeedStatics.RSS_ENCODING);
		assertNotNull(output);
		
		assertNotSame(0, output.length());
		assertNotSame(-1, output.indexOf("<copyright>Copyright: (C) Shazam Entertainment Ltd, http://www.shazam.com</copyright>"));
	}
}
