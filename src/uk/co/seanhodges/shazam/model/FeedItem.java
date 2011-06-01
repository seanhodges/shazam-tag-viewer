package uk.co.seanhodges.shazam.model;

import java.io.Serializable;

import android.net.Uri;

/**
 * A single item in the FeedChannel item list
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class FeedItem implements Serializable {
	
	private static final long serialVersionUID = -9199321602507592085L;

	private String trackName;
	private String trackArtist;
	private Uri link;
	
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
	
	public Uri getLink() {
		return link;
	}
	
	public void setLink(Uri link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "FeedItem [trackName=" + trackName + ", trackArtist="
				+ trackArtist + ", link=" + link + "]";
	}
}
