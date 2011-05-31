package uk.co.seanhodges.shazam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class UserTagList extends Activity {

	public static final String PARAM_USER_NAME = "username";
	
	private ListView tags;
	
	private String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.user_tag_list);
        
        tags = (ListView)findViewById(R.id.lst_tags);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		// Get the user name
		userName = savedInstanceState.getString(UserTagList.PARAM_USER_NAME);
	}

}
