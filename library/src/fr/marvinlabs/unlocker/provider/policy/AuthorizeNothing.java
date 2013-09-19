package fr.marvinlabs.unlocker.provider.policy;

import android.net.Uri;

/**
 * Never authorize anything
 * 
 * @author Vincent Prat @ MarvinLabs
 */
public class AuthorizeNothing extends AuthorizePackagePolicy {

	public AuthorizeNothing(String packageName, String authority) {
		super(packageName, authority);
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
		return false;
	}
}
