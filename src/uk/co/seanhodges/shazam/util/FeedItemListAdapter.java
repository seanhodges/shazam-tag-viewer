package uk.co.seanhodges.shazam.util;

import java.util.List;

import uk.co.seanhodges.shazam.R;
import uk.co.seanhodges.shazam.model.FeedItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Custom list adapter for the tag list activity
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class FeedItemListAdapter extends BaseAdapter {

	private Context context;
	private int entryLayoutId;
	private List<FeedItem> feedItems;
	
	public FeedItemListAdapter(Context context, int entryLayoutId, List<FeedItem> feedItems) {
		this.context = context;
		this.entryLayoutId = entryLayoutId;
		this.feedItems = feedItems;
	}
	
	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View convertView, ViewGroup root) {
		RelativeLayout itemLayout = (RelativeLayout)LayoutInflater.from(context).inflate(entryLayoutId, root, false);
		FeedItem item = feedItems.get(location);
		
		// Bind the item data to the row
		TextView title = (TextView)itemLayout.findViewById(R.id.lbl_entry_title);
		title.setText(item.getTrackName());
		TextView artist = (TextView)itemLayout.findViewById(R.id.lbl_entry_artist);
		artist.setText(item.getTrackArtist());
		return itemLayout;
	}

}
