package fr.marvinlabs.unlocker.core;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class ConfigUtil {

    public static final String META_APP_PACKAGE_NAME = "unlocker_app_package_name";
    public static final String META_FEATURE_NAME = "unlocker_app_feature_name";
    public static final String META_DEBUG = "unlocker_debug";

    public static String getAppPackageName(Context context) {
        return readManifestValue(context, META_APP_PACKAGE_NAME, true);
    }

    public static String getAppFeatureName(Context context) {
        return readManifestValue(context, META_FEATURE_NAME, false);
    }

    public static boolean isDebugEnabled(Context context) {
        String debug = readManifestValue(context, META_DEBUG, false);
        return debug != null && debug.equalsIgnoreCase("true");
    }

    private static String readManifestValue(Context context, String name, boolean mandatory) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(name);
            return value.toString();
        } catch (PackageManager.NameNotFoundException e) {

        }
        if (mandatory) {
            throw new RuntimeException("Could not read required unlockers manifest meta data!");
        } else {
            return null;
        }
    }
}
