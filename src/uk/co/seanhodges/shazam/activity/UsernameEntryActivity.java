package uk.co.seanhodges.shazam.activity;

import uk.co.seanhodges.shazam.R;
import uk.co.seanhodges.shazam.flow.IntentDelegate;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
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
	
	private static final int DIALOG_NO_USER_ENTRY = 200;
	
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
	protected Dialog onCreateDialog(int id) {
		Dialog out = null;
		AlertDialog.Builder builder;
		switch(id) {
		case DIALOG_NO_USER_ENTRY:
			Log.d(getClass().getSimpleName(), "Showing user not entered dialog");
			builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.lbl_user_not_entered_message)
			.setCancelable(false)
			.setPositiveButton(R.string.btn_close, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dismissDialog(DIALOG_NO_USER_ENTRY);
				}
			});
			out = builder.create();
			break;
		}
		
		return out;
    }

	@Override
	public void onClick(View v) {
		if (submit.getId() == v.getId()) {
			String userName = encodeQueryData(userNameField.getText().toString());
			if (userName.length() > 0) {
				Log.d(getClass().getSimpleName(), "Username was " + userName);
				IntentDelegate.launchUserTagListActivity(this, userName);
			}
			else {
				// A username must be entered
				showDialog(DIALOG_NO_USER_ENTRY);
			}
		}
	}

	private String encodeQueryData(String userName) {
		userName = userName.trim();
		userName = Uri.encode(userName);
		return userName;
	}
    
}