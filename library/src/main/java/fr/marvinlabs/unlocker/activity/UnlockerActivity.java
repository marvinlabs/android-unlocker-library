package fr.marvinlabs.unlocker.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import fr.marvinlabs.unlocker.R;

public class UnlockerActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlocker_activity);

        TextView tv = findViewById(R.id.tvText);

        tv.setText(getString(R.string.unlocker_info, getString(R.string.unlocker_info_app)));
    }
}
