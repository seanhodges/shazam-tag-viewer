package uk.co.seanhodges.shazam.activity;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import uk.co.seanhodges.shazam.R;
import uk.co.seanhodges.shazam.model.FeedChannel;
import uk.co.seanhodges.shazam.model.FeedItem;
import uk.co.seanhodges.shazam.rss.RssFeedReader;
import uk.co.seanhodges.shazam.rss.RssFeedStatics;
import uk.co.seanhodges.shazam.server.IShazamDriver;
import uk.co.seanhodges.shazam.server.ShazamDriverFactory;
import android.app.ListActivity;
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
public class UserTagList extends ListActivity {

	public static final String PARAM_USER_NAME = "username";
	
	private static final String DISABLE_MOBILE_QUERY_KEY = "no_mobile";
	private static final String DISABLE_MOBILE_QUERY_VALUE = "1";
	
	private String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.user_tag_list);

		// Get the user name
        Bundle intentExtras = getIntent().getExtras();
        if (intentExtras != null) {
        	userName = intentExtras.getString(UserTagList.PARAM_USER_NAME);
		
			// TODO: Move this to a task
	        Log.i(getClass().getSimpleName(), "Loading tags for user " + userName);
			FeedChannel channel = null;
			try {
		        // Load the RSS XML
		        IShazamDriver driver = ShazamDriverFactory.getDriver(this);
		        InputStream is = driver.getTagRssFeed(userName);
		        
		        // Parse the feed
				SAXParserFactory spf = SAXParserFactory.newInstance(); 
				SAXParser sp = spf.newSAXParser(); 
				XMLReader xr = sp.getXMLReader(); 
				
				RssFeedReader handler = new RssFeedReader();
				xr.setContentHandler(handler);
				
				InputSource inputSource = new InputSource(is);
				inputSource.setEncoding(RssFeedStatics.RSS_ENCODING);
				xr.parse(inputSource);
				
				channel = handler.getFeed();
				
		        // Get the data, and pass to an adapter for displaying in the list
		        ArrayAdapter<FeedItem> adapter 
		        	= new ArrayAdapter<FeedItem>(this, R.layout.user_tag_entry, channel.getEntries());
		        setListAdapter(adapter);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
        }
        else {
        	Log.w(getClass().getSimpleName(), "No username was provided to activity");
        }
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(getClass().getSimpleName(), "Tag row selected: " + position);
		
		FeedItem item = (FeedItem)getListAdapter().getItem(position);
		
		Uri.Builder targetUrlBuilder = item.getLink().buildUpon();
		targetUrlBuilder.appendQueryParameter(DISABLE_MOBILE_QUERY_KEY, DISABLE_MOBILE_QUERY_VALUE);
		Uri targetUrl = targetUrlBuilder.build();
		Log.i(getClass().getSimpleName(), "Opening browser with URL: " + targetUrl);
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, targetUrl);
		startActivity(browserIntent);
	}

}
