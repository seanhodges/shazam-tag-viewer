package uk.co.seanhodges.shazam.rss;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.net.Uri;
import android.util.Log;

import uk.co.seanhodges.shazam.model.FeedChannel;
import uk.co.seanhodges.shazam.model.FeedItem;

/**
 * SAX parser for the Shazam tag RSS feed
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class RssFeedReader extends DefaultHandler {
	
	private FeedChannel result;
	private FeedItem currentFeedItem;
	private String currentTag;
	private boolean rootNodeFound = false;
	
	public RssFeedReader() {
		result = null;
	}
	
	
	@Override
	public void startDocument() throws SAXException {
		result = new FeedChannel();
		result.setEntries(new ArrayList<FeedItem>());
		currentTag = null;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentTag = qName;
		
		if (!rootNodeFound && !RssFeedStatics.FEED_RSS_ROOT.equals(currentTag)) {
			// Detect incorrect feed data, since we don't know what the problem
			// was, we assume the user was not found...
			fatalError(new UserNotFoundException());
		}
		else if (RssFeedStatics.FEED_CHANNEL_ITEM.equals(currentTag)) {
			currentFeedItem = new FeedItem();
			result.getEntries().add(currentFeedItem);
		}
		
		rootNodeFound = true;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String text = new String(ch, start, length);
		
		if (currentTag == null || text.length() == 0) {
			// Skip text outside the tags
		}
		else if (RssFeedStatics.FEED_CHANNEL_ITEM_NAME.equals(currentTag) && currentFeedItem != null) {
			currentFeedItem.setTrackName(text.trim());
		}
		else if (RssFeedStatics.FEED_CHANNEL_ITEM_ARTIST.equals(currentTag) && currentFeedItem != null) {
			currentFeedItem.setTrackArtist(text.trim());
		}
		else if (RssFeedStatics.FEED_CHANNEL_ITEM_LINK.equals(currentTag) && currentFeedItem != null) {
			currentFeedItem.setLink(Uri.parse(text.trim()));
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (Log.isLoggable(getClass().getSimpleName(), Log.DEBUG)) {
			if (RssFeedStatics.FEED_CHANNEL_ITEM.equals(currentTag)) {
				Log.d(getClass().getSimpleName(), "Feed item found: " + currentTag.toString());
			}
		}
		currentTag = null;
	}
	
	
	public FeedChannel getFeed() {
		return result;
	}
}
