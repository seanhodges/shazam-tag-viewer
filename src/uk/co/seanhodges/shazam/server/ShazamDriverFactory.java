package uk.co.seanhodges.shazam.server;

import uk.co.seanhodges.shazam.R;
import android.content.Context;
import android.util.Log;

/**
 * Factory for returning correct Shazam server driver
 * @author Sean Hodges <seanhodges84@gmail.com>
 */
public class ShazamDriverFactory {
	
	public static final String USE_DRIVER_FAKE = "FakeShazamDriver";
	public static final String USE_DRIVER_HTTP = "HttpShazamDriver";
	
	private static IShazamDriver driver;
	
	/**
	 * Get the driver for the running application
	 * @param app context
	 * @return the driver
	 */
	public static IShazamDriver getDriver(Context context) {
		if (driver == null) {
			String driverSetting = context.getResources().getString(R.string.shazam_server);
			driver = getDriver(driverSetting);
		}
		return driver;
	}

	/**
	 * Get a specific driver - this is for testing only
	 * @param driverSetting - the driver class name
	 * @return the driver
	 */
	public static IShazamDriver getDriver(String driverSetting) {
		Log.d(ShazamDriverFactory.class.getSimpleName(), "Using Shazam connector: " + driverSetting);
		IShazamDriver driver = null;
		if (USE_DRIVER_FAKE.equals(driverSetting)) {
			driver = new FakeShazamDriver();
		}
		else {
			driver = new HttpShazamDriver();
		}
		return driver;
	}
	
}
