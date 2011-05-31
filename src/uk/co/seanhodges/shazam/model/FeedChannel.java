package uk.co.seanhodges.shazam.model;

import java.io.Serializable;
import java.util.List;

/**
 * The entire feed channel response for tag lists
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class FeedChannel implements Serializable {
	
	private static final long serialVersionUID = 5342808426455852767L;
	
	private List<FeedItem> entries;
	
	public List<FeedItem> getEntries() {
		return entries;
	}

	public void setEntries(List<FeedItem> entries) {
		this.entries = entries;
	}
}
