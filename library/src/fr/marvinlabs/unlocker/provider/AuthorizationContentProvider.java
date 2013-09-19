package fr.marvinlabs.unlocker.provider;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import fr.marvinlabs.unlocker.provider.Authorization.AuthorizationColumns;

public abstract class AuthorizationContentProvider extends ContentProvider {

	private final Map<Integer, AuthorizationPolicy> authorizationPolicies;
	private final UriMatcher uriMatcher;
	private int currentUriType;
	private boolean debugEnabled;
	private final String authority;
	private final String mimeType;

	@Override
	public String getType(Uri uri) {
		if (uriMatcher.match(uri) > 0) {
			return mimeType;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String order) {
		MatrixCursor result = AuthorizationColumns.newCursor();

		if (debugEnabled) Log.d("AuthorizationProvider", "Authorization resquested at " + uri.toString());

		// False if Uri does not match
		final int uriType = uriMatcher.match(uri);
		if (uriType <= 0) {
			AuthorizationColumns.addRowToCursor(result, false);
			if (debugEnabled) Log.d("AuthorizationProvider", "Authorization rejected, URI not recognized");
			return result;
		}

		// False if no matching policy
		AuthorizationPolicy policy = authorizationPolicies.get(uriType);
		if (policy == null) {
			AuthorizationColumns.addRowToCursor(result, false);
			if (debugEnabled) Log.d("AuthorizationProvider", "Authorization rejected, no patching policy");
			return result;
		}
		if (debugEnabled) Log.d("AuthorizationProvider", "Matching policy: " + policy.toString());

		// Build the result according to the policy's result
		boolean isAuthorized = policy.isAuthorized(uri, selectionArgs);
		AuthorizationColumns.addRowToCursor(result, isAuthorized);

		if (debugEnabled) Log.d("AuthorizationProvider", "The policy returned " + isAuthorized);

		return result;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

	public String getAuthority() {
		return authority;
	}

	protected void setOutputDebugInformation(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	/**
	 * Static function to be used by subclasses in order to provide easy access to authorization. Given a content
	 * resolver and a policy, it makes the appropriate query and returns the result of the authorization. We intercept
	 * security exceptions if the application is not granted the permission required to access the content provider.
	 * 
	 * @param cr
	 *            The content resolver to be used to make the query
	 * @return true if the unlocker application authorizes our package
	 */
	protected static boolean getAuthorization(ContentResolver cr, AuthorizationPolicy policy) {
		Cursor result = null;
		try {
			Uri uri = policy.getQueryUri();
			String[] selectionArgs = policy.getQuerySelectionArgs();
			result = cr.query(uri, null, null, selectionArgs, null);
			if (result == null || !result.moveToFirst()) {
				return false;
			}
			return (1 == result.getInt(result.getColumnIndex(AuthorizationColumns.IS_AUTHORIZED)));
		} catch (SecurityException e) {
			Log.e("AuthorizationProvider", "Permission to access the content provider is missing: " + e.getMessage());
			return false;
		} finally {
			if (result != null) {
				result.close();
				result = null;
			}
		}
	}

	protected AuthorizationContentProvider(String authority) {
		this.authorizationPolicies = new HashMap<Integer, AuthorizationPolicy>();
		this.authority = authority;
		this.mimeType = "vnd.android.cursor.item/" + authority;

		// Setup URI matcher
		this.currentUriType = 0;
		this.uriMatcher = new UriMatcher(0);
	}

	/**
	 * Add an authorization policy for a given path
	 * 
	 * @param policy
	 *            The policy to be used when the query Uri matches its path
	 */
	protected void addAuthorizationPolicy(AuthorizationPolicy policy) {
		++currentUriType;
		this.uriMatcher.addURI(authority, policy.getUriMatcherPath(), currentUriType);
		authorizationPolicies.put(currentUriType, policy);

		if (debugEnabled)
			Log.d("AuthorizationProvider",
					String.format("URI matcher will match %d (%s) to policy: %s", currentUriType,
							policy.getUriMatcherPath(), policy.toString()));
	}
}
