/**
 * Copyright 2011 MarvinLabs
 * 
 * This file is part of the Android Authorization Library.
 * 
 * The Android Authorization Library is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * The Android Authorization Library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with The Android Authorization
 * Library. If not, see http://www.gnu.org/licenses/.
 */
package fr.marvinlabs.unlocker.provider.policy;

import java.util.Arrays;

import android.net.Uri;
import android.net.Uri.Builder;

/**
 * Authorize only a given set of features
 * 
 * @author Vincent Prat @ MarvinLabs
 */
public class AuthorizeFeaturesPolicy extends AuthorizePackagePolicy {

	private String queriedFeature;
	private String[] authorizedFeatures;

	/**
	 * Create a new instance of this class to be used to authorize a query
	 * 
	 * @param authorizedFeatures
	 *            The set of features that are authorized
	 * @return The policy
	 */
	public static AuthorizeFeaturesPolicy newInstanceForAuthorization(String packageName, String authority,
			String[] authorizedFeatures) {
		AuthorizeFeaturesPolicy policy = new AuthorizeFeaturesPolicy(packageName, authority);

		// The array must be sorted so that we can use the {@link java.util.Arrays#binarySearch(Object[], Object)}
		// function later on.
		policy.authorizedFeatures = authorizedFeatures;
		Arrays.sort(policy.authorizedFeatures);

		return policy;
	}

	/**
	 * Create a new instance of this class to be used to build a query
	 * 
	 * @param queriedFeature
	 *            The feature we want to authorize
	 * @return The policy
	 */
	public static AuthorizeFeaturesPolicy newInstanceForQuery(String packageName, String authority,
			String queriedFeature) {
		AuthorizeFeaturesPolicy policy = new AuthorizeFeaturesPolicy(packageName, authority);
		policy.queriedFeature = queriedFeature;
		return policy;
	}

	@Override
	public String getUriMatcherPath() {
		return super.getUriMatcherPath() + "/features/*";
	}

	@Override
	public Uri getQueryUri() {
		if (queriedFeature == null) return null;
		return getBaseUriBuilder().appendPath(queriedFeature).build();
	}

	@Override
	public String[] getQuerySelectionArgs() {
		return null;
	}

	@Override
	public boolean isAuthorized(Uri uri, String[] selectionArgs) {
		// The package name must be coherent
		if (!super.isAuthorized(uri, selectionArgs)) return false;

		// Feature name is supplied as the last segment of the path of the URI. Check to see if we find it.
		final String feature = uri.getLastPathSegment();
		if (Arrays.binarySearch(authorizedFeatures, feature) >= 0) return true;

		return false;
	}

	@Override
	protected Builder getBaseUriBuilder() {
		return super.getBaseUriBuilder().appendPath("features");
	}

	/**
	 * We don't want anybody to instanciate directly the class
	 */
	protected AuthorizeFeaturesPolicy(String packageName, String authority) {
		super(packageName, authority);
	}
}
