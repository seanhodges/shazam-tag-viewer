package uk.co.seanhodges.shazam.model;

import java.io.Serializable;
import java.net.URI;

public class FeedItem implements Serializable {
	
	private static final long serialVersionUID = -9199321602507592085L;

	private String trackName;
	private String trackArtist;
	private URI link;
	
	public String getTrackName() {
		return trackName;
	}
	
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	
	public String getTrackArtist() {
		return trackArtist;
	}
	
	public void setTrackArtist(String trackArtist) {
		this.trackArtist = trackArtist;
	}
	
	public URI getLink() {
		return link;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}
}
