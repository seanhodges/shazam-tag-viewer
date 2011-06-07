package uk.co.seanhodges.shazam.flow;

import uk.co.seanhodges.shazam.activity.UserTagListActivity;
import uk.co.seanhodges.shazam.model.FeedItem;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

public class IntentDelegateTest extends AndroidTestCase {

	private Intent output;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		output = null;
	}
	
	public void testLaunchingUserTagList() throws Exception {
		Context context = new MockContext() {

			@Override
			public String getPackageName() {
				return "abc";
			}

			@Override
			public void startActivity(Intent intent) {
				output = intent;
			}
			
		};
		
		IntentDelegate.launchUserTagListActivity(context, "testuser");
		
		assertNotNull(output);
		assertEquals(UserTagListActivity.class.getName(), output.getComponent().getClassName());
		assertEquals("testuser", output.getExtras().getString(UserTagListActivity.PARAM_USER_NAME));
	}
	
	public void testLaunchingTagDetailsInBrowser() throws Exception {
		Context context = new MockContext() {

			@Override
			public String getPackageName() {
				return "abc";
			}

			@Override
			public void startActivity(Intent intent) {
				output = intent;
			}
			
		};
		
		FeedItem item = new FeedItem();
		item.setLink(Uri.parse("www.example.com"));
		IntentDelegate.launchTagDetailsInBrowser(context, item);
		
		assertNotNull(output);
		assertEquals(Intent.ACTION_VIEW, output.getAction());
		assertEquals("www.example.com?no_mobile=1", output.getDataString());
	}
}
