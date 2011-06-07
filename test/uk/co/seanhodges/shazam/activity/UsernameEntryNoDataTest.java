package uk.co.seanhodges.shazam.activity;

import uk.co.seanhodges.shazam.R;
import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

public class UsernameEntryNoDataTest extends ActivityInstrumentationTestCase2<UsernameEntryActivity> {

	private UsernameEntryActivity activity;
	private EditText userNameField;
	private Button submit;
	
	public UsernameEntryNoDataTest() {
		super(UsernameEntryActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		activity = getActivity();
		userNameField = (EditText)activity.findViewById(R.id.txt_user_name);
		submit = (Button)activity.findViewById(R.id.btn_submit);
	}
	
	public void testEnteringNoUserDisplaysErrorDialog() throws Exception {
		Instrumentation instrumentation = getInstrumentation();
		
		Instrumentation.ActivityMonitor monitor 
			= new Instrumentation.ActivityMonitor(UserTagListActivity.class.getName(), null, false);
		instrumentation.addMonitor(monitor);
		
		activity.runOnUiThread(new Runnable() {
			public void run() {
				userNameField.setText("");
				assertTrue(submit.performClick());
			}
		});
		
		// Check the destination activity
		Activity result = instrumentation.waitForMonitorWithTimeout(monitor, 3000);
		assertEquals(0, monitor.getHits());
		assertNull(result);
	}
}
