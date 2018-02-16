package fr.marvinlabs.unlocker.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fr.marvinlabs.unlocker.core.provider.UnlockerProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvStatus = findViewById(R.id.tvStatus);

        boolean isUnlockerInstalled = UnlockerProvider.getPackageLevelAuthorization(this);
        boolean isProVersion = UnlockerProvider.getFeatureLevelAuthorization(this, "pro");
        boolean isPro2Version = UnlockerProvider.getFeatureLevelAuthorization(this, "pro2");

        tvStatus.setText(String.format("Unlocker App installed: %b\nPro feature found in unlocker: %b\nPro feature 2 found in unlocker: %b (should not be found!)",
                isUnlockerInstalled,
                isProVersion,
                isPro2Version));
    }
}
