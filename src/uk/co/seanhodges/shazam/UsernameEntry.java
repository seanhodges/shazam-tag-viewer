package uk.co.seanhodges.shazam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UsernameEntry extends Activity implements OnClickListener {
	
	private EditText userNameField;
	private Button submit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_entry);
        
        userNameField = (EditText)findViewById(R.id.txt_user_name);
        submit = (Button)findViewById(R.id.btn_submit);
        
        submit.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		if (submit.getId() == v.getId()) {
			launchUserTagListActivity();
		}
	}

	private void launchUserTagListActivity() {
		String userName = userNameField.getText().toString();
		Intent destination = new Intent(this, UserTagList.class);
		destination.putExtra(UserTagList.PARAM_USER_NAME, userName);
		startActivity(destination);
	}
    
}