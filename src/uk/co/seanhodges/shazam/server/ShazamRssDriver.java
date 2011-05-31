package uk.co.seanhodges.shazam.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;

/**
 * Helper for sending queries to the Shazam server
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class ShazamRssDriver {
	
	private final String SERVER_ENDPOINT = "http://www.shazam.com/music/web";
	
	private final String TAGLIST_REQUEST = "/taglistrss?mode=xml&userName={username}";
	
	/**
	 * Get the tag list for a given user
	 * @param userName
	 * @return the FeedChannel containing the tag list
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public InputStream getTagRssFeed(String userName) throws IOException, URISyntaxException {
		URI url = new URI(getTaglistRequestUrl(userName));
		
		// Send request and return response
		HttpClient client = AndroidHttpClient.newInstance("");
		HttpGet request = new HttpGet(url);
		HttpResponse resp = client.execute(request);
		int status = resp.getStatusLine().getStatusCode();
		if (status != 200) {
			throw new IOException("Unexpected HTTP response from server: " + status);
		}
		
		return resp.getEntity().getContent();
	}

	private String getTaglistRequestUrl(String userName) {
		return SERVER_ENDPOINT + TAGLIST_REQUEST.replace("{username}", userName);
	}

}
