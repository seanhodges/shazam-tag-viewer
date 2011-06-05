package uk.co.seanhodges.shazam.activity;

import uk.co.seanhodges.shazam.R;
import uk.co.seanhodges.shazam.flow.IntentDelegate;
import uk.co.seanhodges.shazam.model.FeedChannel;
import uk.co.seanhodges.shazam.model.FeedItem;
import uk.co.seanhodges.shazam.task.LoadUserTagsTask;
import uk.co.seanhodges.shazam.task.LoadUserTagsTask.LoadUserTagsTaskListener;
import uk.co.seanhodges.shazam.util.FeedItemListAdapter;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Displays the tag table for a given user
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class UserTagListActivity extends ListActivity implements LoadUserTagsTaskListener {

	public static final String PARAM_USER_NAME = "username";

	private static final int DIALOG_LOADING_PROGRESS = 200;
	
	private static volatile boolean isLoading = false;
	
	private String userName;
	private FeedChannel loadedFeed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.user_tag_list);
        
        loadedFeed = (FeedChannel)getLastNonConfigurationInstance();
        if (loadedFeed == null && !isLoading) {
        	isLoading = true;
        	
        	Bundle intentExtras = getIntent().getExtras();
        	if (intentExtras != null) {
        		// Get the user name
            	userName = intentExtras.getString(UserTagListActivity.PARAM_USER_NAME);
    		
    			// Load the user tags
    	        Log.i(getClass().getSimpleName(), "Loading tags for user " + userName);
    			LoadUserTagsTask task = new LoadUserTagsTask(this, this);
    			task.execute(userName);
    			
    			// Display a progress bar whilst the task is executing
    			showDialog(DIALOG_LOADING_PROGRESS);
    			
    	        // See the onLoadUserTagsTaskComplete() method for the result handling
        	}
        	else {
            	Log.e(getClass().getSimpleName(), "No username was provided to activity");
            	isLoading = false;
            }
        }
        else if (!isLoading) {
        	// Handle activity orientation changes
        	loadListAdapterContents();
        }
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog out = null;
		switch(id) {
		case DIALOG_LOADING_PROGRESS:
	    	Log.d(getClass().getSimpleName(), "Showing loading progress dialog");
			ProgressDialog progress = new ProgressDialog(this);
			progress.setTitle(R.string.lbl_tag_load_progress_title); 
			progress.setMessage(getString(R.string.lbl_tag_load_progress_message));
			progress.setIndeterminate(true);
			progress.setCancelable(false);
			out = progress;
			break;
		}
		return out;
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return loadedFeed;
	}

	@Override
	public void onLoadUserTagsTaskComplete(FeedChannel result) {
    	Log.d(getClass().getSimpleName(), "Feed loaded, setting list adapter and closing progress dialog");
		loadedFeed = result;
		loadListAdapterContents();
		
        // Close the progress bar
        removeDialog(DIALOG_LOADING_PROGRESS);
        
        isLoading = false;
	}
	
	private void loadListAdapterContents() {
		// Get the data, and pass to an adapter for displaying in the list
        FeedItemListAdapter adapter 
        	= new FeedItemListAdapter(this, R.layout.user_tag_entry, loadedFeed.getEntries());
        setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(getClass().getSimpleName(), "Tag row selected: " + position);
		FeedItem item = (FeedItem)getListAdapter().getItem(position);
		IntentDelegate.launchTagDetailsInBrowser(this, item);
	}
}
