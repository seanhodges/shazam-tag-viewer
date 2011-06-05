package uk.co.seanhodges.shazam.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Helper for sending queries to the Shazam server
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class HttpShazamDriver implements IShazamDriver {
	
	private final int HTTP_CONNECTION_TIMEOUT = 6000; // Connection timeout in millisecs
	private final int HTTP_SOCKET_TIMEOUT = 6000; // Socket timeout in millisecs
	
	private final String SERVER_ENDPOINT = "http://www.shazam.com/music/web";
	
	private final String TAGLIST_REQUEST = "/taglistrss?mode=xml&userName={username}";
	
	private HttpClient client;
	
	public HttpShazamDriver() {
		client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, HTTP_SOCKET_TIMEOUT);
	}
	
	@Override
	public InputStream getTagRssFeed(String userName) throws IOException, URISyntaxException {
		URI url = new URI(getTaglistRequestUrl(userName));
		
		// Send request and return response
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
