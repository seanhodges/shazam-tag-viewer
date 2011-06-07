package uk.co.seanhodges.shazam.server;

import android.content.Context;
import android.test.AndroidTestCase;

public class ShazamDriverFactoryTest extends AndroidTestCase {
	
	public void testHttpShazamDriverIsReturned() throws Exception {
		Context context = getContext();
		
		IShazamDriver driver = ShazamDriverFactory.getDriver(context);
		assertTrue("Incorrect driver instance returned by ShazamDriverFactory", driver instanceof HttpShazamDriver);
	}
}
