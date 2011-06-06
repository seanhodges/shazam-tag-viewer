package uk.co.seanhodges.shazam.task;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import uk.co.seanhodges.shazam.model.FeedChannel;
import uk.co.seanhodges.shazam.rss.RssFeedReader;
import uk.co.seanhodges.shazam.rss.RssFeedStatics;
import uk.co.seanhodges.shazam.rss.UserNotFoundException;
import uk.co.seanhodges.shazam.server.IShazamDriver;
import uk.co.seanhodges.shazam.server.ShazamDriverFactory;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Retrieve the user tag feed and return to caller
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class LoadUserTagsTask extends AsyncTask<String, Integer, FeedChannel> {

	private static IShazamDriver driver;
	
	private LoadUserTagsTaskListener listener;
	
	/**
	 * Task callback interface
	 */
	public interface LoadUserTagsTaskListener {
		public void onLoadUserTagsTaskComplete(FeedChannel result);
		public void onLoadUserTagsTaskFailed();
		public void onLoadUserTagsTaskUserNotFound();
	}
	
	/**
	 * Initialise the task
	 * @param context - the current application context
	 * @param listener - an implementing class to pass the result back to
	 */
	public LoadUserTagsTask(Context context, LoadUserTagsTaskListener listener) {
		driver = ShazamDriverFactory.getDriver(context);
		this.listener = listener;
	}
	
	@Override
	protected FeedChannel doInBackground(String... params) {
		FeedChannel output = null;
		
		String userName = params[0];
		Log.i(getClass().getSimpleName(), "Loading tags for user " + userName);
		
		try {
			publishProgress(20);
			
	        // Load the RSS XML
			Log.d(getClass().getSimpleName(), "Retrieving RSS feed");
	        InputStream is = driver.getTagRssFeed(userName);
			publishProgress(60);
	        
	        // Parse the feed
			Log.d(getClass().getSimpleName(), "Initialising SAX parser");
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
			SAXParser sp = spf.newSAXParser(); 
			XMLReader xr = sp.getXMLReader(); 
			
			RssFeedReader handler = new RssFeedReader();
			xr.setContentHandler(handler);
			publishProgress(80);

			Log.d(getClass().getSimpleName(), "Processing feed XML");
			InputSource inputSource = new InputSource(is);
			inputSource.setEncoding(RssFeedStatics.RSS_ENCODING);
			xr.parse(inputSource);
			
			output = handler.getFeed();
			output.setValidUser(true);
			publishProgress(100);
			
			Log.i(getClass().getSimpleName(), "User tag loading task finished");
		}
		catch (UserNotFoundException e) {
			Log.w(getClass().getSimpleName(), "User tag data was not in server response");
			output = new FeedChannel();
			output.setValidUser(false);
		}
		catch (Exception e) {
			Log.e(getClass().getSimpleName(), "Task failed to complete", e);
			output = null;
		}
		
		return output;
	}

	@Override
	protected void onPostExecute(FeedChannel result) {
		super.onPostExecute(result);
		
		// Pass the result back to the listener
		if (result == null) {
			Log.d(getClass().getSimpleName(), "Executing failed callback");
			listener.onLoadUserTagsTaskFailed();
		}
		else if (!result.isValidUser()) {
			Log.d(getClass().getSimpleName(), "Executing user not found callback");
			listener.onLoadUserTagsTaskUserNotFound();
		}
		else {
			Log.d(getClass().getSimpleName(), "Executing completed callback");
			listener.onLoadUserTagsTaskComplete(result);
		}
	}
}
