package fr.marvinlabs.unlocker.provider.policy;

import android.net.Uri;

/**
 * Always authorize everything
 * 
 * @author Vincent Prat @ MarvinLabs
 */
public class AuthorizeEverything extends AuthorizePackagePolicy {

	public AuthorizeEverything(String packageName) {
		super(packageName);
	}

	@Override
	public Uri getQueryUri() {
		return getBaseUriBuilder().build();
	}

	@Override
	public String[] getQuerySelectionArgs() {
		return null;
	}

	@Override
	public boolean isAuthorized(Uri uri, String[] selectionArgs) {
		return true;
	}

}
