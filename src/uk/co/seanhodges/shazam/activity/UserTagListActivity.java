package uk.co.seanhodges.shazam.activity;

import uk.co.seanhodges.shazam.R;
import uk.co.seanhodges.shazam.model.FeedChannel;
import uk.co.seanhodges.shazam.model.FeedItem;
import uk.co.seanhodges.shazam.task.LoadUserTagsTask;
import uk.co.seanhodges.shazam.task.LoadUserTagsTask.LoadUserTagsTaskListener;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Displays the tag table for a given user
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class UserTagListActivity extends ListActivity implements LoadUserTagsTaskListener {

	public static final String PARAM_USER_NAME = "username";
	
	private static final String DISABLE_MOBILE_QUERY_KEY = "no_mobile";
	private static final String DISABLE_MOBILE_QUERY_VALUE = "1";
	
	private String userName;
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.user_tag_list);

		// Get the user name
        Bundle intentExtras = getIntent().getExtras();
        if (intentExtras != null) {
        	userName = intentExtras.getString(UserTagListActivity.PARAM_USER_NAME);
		
			// Load the user tags
	        Log.i(getClass().getSimpleName(), "Loading tags for user " + userName);
			LoadUserTagsTask task = new LoadUserTagsTask(this, this);
			task.execute(userName);
			
			// Display a progress bar whilst the task is executing
			progressDialog = ProgressDialog.show(this, 
					getString(R.string.lbl_tag_load_progress_title), 
					getString(R.string.lbl_tag_load_progress_message), true, false);
			
	        // See the onLoadUserTagsTaskComplete() method for the result handling
        }
        else {
        	Log.w(getClass().getSimpleName(), "No username was provided to activity");
        }
	}

	@Override
	public void onLoadUserTagsTaskComplete(FeedChannel result) {
		// Get the data, and pass to an adapter for displaying in the list
        ArrayAdapter<FeedItem> adapter 
        	= new ArrayAdapter<FeedItem>(this, R.layout.user_tag_entry, result.getEntries());
        setListAdapter(adapter);
        
        // Close the progress bar
        if (progressDialog != null && progressDialog.isShowing()) {
        	progressDialog.dismiss();
        }
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(getClass().getSimpleName(), "Tag row selected: " + position);
		
		FeedItem item = (FeedItem)getListAdapter().getItem(position);
		launchTagDetailsInBrowser(item);
	}

	private void launchTagDetailsInBrowser(FeedItem item) {
		Uri.Builder targetUrlBuilder = item.getLink().buildUpon();
		
		// Add the "no_mobile=1" parameter to the URL to ensure we display the correct page
		if (!item.getLink().toString().contains(DISABLE_MOBILE_QUERY_KEY)) {
			targetUrlBuilder.appendQueryParameter(DISABLE_MOBILE_QUERY_KEY, DISABLE_MOBILE_QUERY_VALUE);
		}
		
		Uri targetUrl = targetUrlBuilder.build();
		
		Log.i(getClass().getSimpleName(), "Opening browser with URL: " + targetUrl);
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, targetUrl);
		startActivity(browserIntent);
	}
}