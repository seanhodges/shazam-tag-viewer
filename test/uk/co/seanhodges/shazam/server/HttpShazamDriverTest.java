package uk.co.seanhodges.shazam.server;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import android.test.AndroidTestCase;

import uk.co.seanhodges.shazam.rss.RssFeedStatics;
import uk.co.seanhodges.shazam.server.HttpShazamDriver;

public class HttpShazamDriverTest extends AndroidTestCase {
	
	public void testLoadingAnRssFeedSuccessfullyFromShazam() throws Exception {
		IShazamDriver driver = new HttpShazamDriver();
		InputStream is = driver.getTagRssFeed("shazam");
		
		String output = IOUtils.toString(is, RssFeedStatics.RSS_ENCODING);
		assertNotNull(output);
		
		assertNotSame(0, output.length());
		
		// Check the copyright is present
		assertNotSame(-1, output.indexOf("<copyright>Copyright: (C) Shazam Entertainment Ltd, http://www.shazam.com</copyright>"));
	}
	
	public void testFailedFeedLoadWithIncorrectUser() throws Exception {
		IShazamDriver driver = new HttpShazamDriver();
		InputStream is = driver.getTagRssFeed("shazamsdskjnfsdkjn");
		
		String output = IOUtils.toString(is, RssFeedStatics.RSS_ENCODING);
		assertNotNull(output);
		
		assertNotSame(0, output.length());
		
		// Check the copyright is not present
		assertSame(-1, output.indexOf("<copyright>Copyright: (C) Shazam Entertainment Ltd, http://www.shazam.com</copyright>"));
	}
}
