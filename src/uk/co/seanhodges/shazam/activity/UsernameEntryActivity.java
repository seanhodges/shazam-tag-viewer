package uk.co.seanhodges.shazam.activity;

import uk.co.seanhodges.shazam.R;
import uk.co.seanhodges.shazam.flow.IntentDelegate;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Request a user name and send to the UserTagList activity
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class UsernameEntryActivity extends Activity implements OnClickListener {
	
	private EditText userNameField;
	private Button submit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.username_entry);
        
        // Bind fields to views
        userNameField = (EditText)findViewById(R.id.txt_user_name);
        submit = (Button)findViewById(R.id.btn_submit);
        
        // Add listeners
        submit.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		if (submit.getId() == v.getId()) {
			String userName = userNameField.getText().toString();
        	Log.d(getClass().getSimpleName(), "Username was " + userName);
			IntentDelegate.launchUserTagListActivity(this, userName);
		}
	}
    
}