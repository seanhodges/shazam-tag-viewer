package uk.co.seanhodges.shazam.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public interface IShazamDriver {

	/**
	 * Get the tag list for a given user
	 * @param userName
	 * @return the FeedChannel containing the tag list
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	InputStream getTagRssFeed(String userName) throws IOException,
			URISyntaxException;

}