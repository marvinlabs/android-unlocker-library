package fr.marvinlabs.unlocker.core.provider;


import android.content.Context;
import android.util.Log;

import fr.marvinlabs.unlocker.core.AuthorizationContentProvider;
import fr.marvinlabs.unlocker.core.AuthorizationPolicy;
import fr.marvinlabs.unlocker.core.ConfigUtil;
import fr.marvinlabs.unlocker.core.policy.AuthorizeFeaturesPolicy;
import fr.marvinlabs.unlocker.core.policy.AuthorizePackagePolicy;

public class UnlockerProvider extends AuthorizationContentProvider {
    private static final String TAG = "UnlockerProvider";

    private static String APP_PACKAGE_NAME;
    private static String MY_LOCKED_FEATURE;

    private static AuthorizationPolicy PACKAGE_LEVEL_POLICY;
    private static AuthorizationPolicy FEATURE_LEVEL_POLICY;

    public UnlockerProvider() {
        super();
    }

    /**
     * Call this function to know if the unlocker is installed for our package
     *
     * @param context The context
     * @return true if the unlock application is installed for our package
     */
    public static boolean getPackageLevelAuthorization(Context context) {
        String packageName = context.getPackageName();
        return getPackageLevelAuthorization(context, packageName);
    }

    /**
     * Call this function to know if the unlocker installed for our package
     * authorizes the given feature
     *
     * @param context        The context
     * @param queriedFeature The feature that needs to be authorized
     * @return true if the unlock application is installed for our package and
     * it authorizes the given feature
     */
    public static boolean getFeatureLevelAuthorization(Context context, String queriedFeature) {
        String packageName = context.getPackageName();
        return getFeatureLevelAuthorization(context, packageName, queriedFeature);
    }

    /**
     * Call this function to know if the unlocker is installed for our package
     *
     * @param context The context
     * @param packageName    The package name
     * @return true if the unlock application is installed for our package
     */
    public static boolean getPackageLevelAuthorization(Context context, String packageName) {
        return getAuthorization(context.getContentResolver(), AuthorizePackagePolicy.newInstance(packageName, packageName));
    }

    /**
     * Call this function to know if the unlocker installed for our package
     * authorizes the given feature
     *
     * @param context        The context
     * @param queriedFeature The feature that needs to be authorized
     * @param packageName    The package name
     * @return true if the unlock application is installed for our package and
     * it authorizes the given feature
     */
    public static boolean getFeatureLevelAuthorization(Context context, String queriedFeature, String packageName) {
        return getAuthorization(context.getContentResolver(),
                AuthorizeFeaturesPolicy.newInstanceForQuery(
                        packageName, packageName,
                        queriedFeature));
    }

    @Override
    public boolean onCreate() {
        boolean result = super.onCreate();

        APP_PACKAGE_NAME = ConfigUtil.getAppPackageName(getContext());
        MY_LOCKED_FEATURE = ConfigUtil.getAppFeatureName(getContext());
        PACKAGE_LEVEL_POLICY = AuthorizePackagePolicy.newInstance(APP_PACKAGE_NAME, APP_PACKAGE_NAME);
        FEATURE_LEVEL_POLICY = AuthorizeFeaturesPolicy.newInstanceForAuthorization(APP_PACKAGE_NAME, APP_PACKAGE_NAME, new String[]{MY_LOCKED_FEATURE});
        boolean debug = ConfigUtil.isDebugEnabled(getContext());

        if (debug) {
            Log.d(TAG, String.format("PackageName: %s", APP_PACKAGE_NAME));
            Log.d(TAG, String.format("Locked feature: %s", MY_LOCKED_FEATURE));
        }

        init(APP_PACKAGE_NAME);

        setOutputDebugInformation(debug);
        addAuthorizationPolicy(PACKAGE_LEVEL_POLICY);
        addAuthorizationPolicy(FEATURE_LEVEL_POLICY);

        return result;
    }
}