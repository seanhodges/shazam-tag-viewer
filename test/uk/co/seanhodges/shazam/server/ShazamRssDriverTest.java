package uk.co.seanhodges.shazam.server;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import uk.co.seanhodges.shazam.rss.RssFeedStatics;

@RunWith(RobolectricTestRunner.class)
public class ShazamRssDriverTest {

	@Test
	public void testLoadingAnRssFeedFromShazam() throws Exception {
		ShazamRssDriver driver = new ShazamRssDriver();
		InputStream is = driver.getTagRssFeed("shazam");
		
		String output = IOUtils.toString(is, RssFeedStatics.RSS_ENCODING);
		assertNotNull(output);
		
		assertNotSame(0, output.length());
		assertNotSame(-1, output.indexOf("<copyright>Copyright: (C) Shazam Entertainment Ltd, http://www.shazam.com</copyright>"));
	}
}
