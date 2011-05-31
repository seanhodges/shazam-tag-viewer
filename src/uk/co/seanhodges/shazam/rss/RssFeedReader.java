package uk.co.seanhodges.shazam.rss;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.co.seanhodges.shazam.model.FeedChannel;
import uk.co.seanhodges.shazam.model.FeedItem;

public class RssFeedReader extends DefaultHandler {
	
	private FeedChannel result;
	private FeedItem currentFeedItem;
	private String currentTag;
	
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
		
		if (RssFeedStatics.FEED_CHANNEL_ITEM.equals(currentTag)) {
			currentFeedItem = new FeedItem();
			result.getEntries().add(currentFeedItem);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String text = new String(ch, start, length);
		
		if (currentTag == null) {
			// Skip text outside the tags
		}
		else if (RssFeedStatics.FEED_CHANNEL_ITEM_NAME.equals(currentTag) && currentFeedItem != null) {
			currentFeedItem.setTrackName(text);
		}
		else if (RssFeedStatics.FEED_CHANNEL_ITEM_ARTIST.equals(currentTag) && currentFeedItem != null) {
			currentFeedItem.setTrackArtist(text);
		}
		else if (RssFeedStatics.FEED_CHANNEL_ITEM_LINK.equals(currentTag) && currentFeedItem != null) {
			try {
				currentFeedItem.setLink(new URI(text));
			} catch (URISyntaxException e) {
				e.printStackTrace();
				currentFeedItem.setLink(null);
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentTag = null;
	}
	
	
	public FeedChannel getFeed() {
		return result;
	}
}
