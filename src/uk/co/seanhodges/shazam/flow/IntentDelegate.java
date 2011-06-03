package uk.co.seanhodges.shazam.flow;

import uk.co.seanhodges.shazam.activity.UserTagListActivity;
import uk.co.seanhodges.shazam.model.FeedItem;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Handles activity flow transitions and launching other apps
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class IntentDelegate {
	
	// Used when launching browser with Shazam.com URL, to ensure the mobile site is displayed
	private static final String DISABLE_MOBILE_QUERY_KEY = "no_mobile";
	private static final String DISABLE_MOBILE_QUERY_VALUE = "1";
	
	/**
	 * Navigate to the UserTagListActivity
	 * 
	 * @param context - app context
	 * @param userName - username to pass to activity
	 */
	public static void launchUserTagListActivity(Context context, String userName) {
        Log.i(IntentDelegate.class.getSimpleName(), "Launching tag list activity");
		Intent destination = new Intent(context, UserTagListActivity.class);
		destination.putExtra(UserTagListActivity.PARAM_USER_NAME, userName);
		context.startActivity(destination);
	}

	/**
	 * Navigate to the tag details page on Shazam.com
	 * 
	 * @param context - app context
	 * @param item - selected FeedItem object from feed
	 */
	public static void launchTagDetailsInBrowser(Context context, FeedItem item) {
		Uri.Builder targetUrlBuilder = item.getLink().buildUpon();
		
		// Add the "no_mobile=1" parameter to the URL to ensure we display the correct page
		if (!item.getLink().toString().contains(DISABLE_MOBILE_QUERY_KEY)) {
			targetUrlBuilder.appendQueryParameter(DISABLE_MOBILE_QUERY_KEY, DISABLE_MOBILE_QUERY_VALUE);
		}
		
		Uri targetUrl = targetUrlBuilder.build();
		
		Log.i(IntentDelegate.class.getSimpleName(), "Opening browser with URL: " + targetUrl);
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, targetUrl);
		context.startActivity(browserIntent);
	}
}
