package fr.marvinlabs.unlocker.provider;

import android.net.Uri;

/**
 * Policy to encapsulate authorization algorithm
 * 
 * @author Vincent Prat @ MarvinLabs
 */
public interface AuthorizationPolicy {

	/**
	 * Return the path to be used by the URI matcher for this authorization
	 * 
	 * @return A string to be used by the URI matcher
	 */
	public String getUriMatcherPath();

	/**
	 * Function to provide the URI to be passed to a content provider's
	 * {@link android.content.ContentProvider#query(android.net.Uri, String[], String, String[], String)} function.
	 * 
	 * @return The URI to pass to the query function of the content provider
	 */
	public Uri getQueryUri();

	/**
	 * Function to provide the selection arguments to be passed to a content provider's
	 * {@link android.content.ContentProvider#query(android.net.Uri, String[], String, String[], String)} function.
	 * 
	 * @return The arguments to pass to the query function of the content provider
	 */
	public String[] getQuerySelectionArgs();

	/**
	 * Given the selection arguments from the query, decide whether the request is authorized or not
	 * 
	 * @param uri
	 *            The URI that has been passed to the content provider
	 * @param selectionArgs
	 *            The selection arguments passed to the
	 *            {@link android.content.ContentProvider#query(android.net.Uri, String[], String, String[], String)}
	 *            function
	 * @return true if the content provider authorizes the request
	 */
	public boolean isAuthorized(Uri uri, String[] selectionArgs);
}
