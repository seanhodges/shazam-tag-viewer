package uk.co.seanhodges.shazam.model;

import java.io.Serializable;
import java.util.List;

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
