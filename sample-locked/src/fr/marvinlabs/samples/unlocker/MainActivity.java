package fr.marvinlabs.samples.unlocker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import fr.marvinlabs.samples.unlocker.common.Configuration;
import fr.marvinlabs.samples.unlocker.common.provider.SampleUnlockerProvider;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	/**
	 * Called when we click on a button in the activity layout
	 * 
	 * @param v
	 *            The button that has been clicked
	 */
	public void onButtonClick(View v) {
		switch (v.getId()) {
			// Button that tests if the unlocker application is indeed installed
			case R.id.test_unlocker: {
				boolean isAuthorized = SampleUnlockerProvider.getPackageLevelAuthorization(getContentResolver());
				if (isAuthorized) {
					Toast.makeText(this, R.string.unlocker_installed, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.unlocker_not_installed, Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case R.id.unlocked_feature: {
				boolean isAuthorized = SampleUnlockerProvider.getFeatureLevelAuthorization(getContentResolver(),
						Configuration.MY_LOCKED_FEATURE);
				if (isAuthorized) {
					Toast.makeText(this, R.string.feature_available, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.feature_unavailable, Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case R.id.locked_feature: {
				boolean isAuthorized = SampleUnlockerProvider.getFeatureLevelAuthorization(getContentResolver(),
						"An Arbitrary feature to be unlocked somewhere else");
				if (isAuthorized) {
					Toast.makeText(this, R.string.feature_available, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.feature_unavailable, Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}
}