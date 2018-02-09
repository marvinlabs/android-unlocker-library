package fr.marvinlabs.unlocker.provider.policy;

import java.util.Arrays;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import fr.marvinlabs.unlocker.provider.AuthorizationPolicy;

/**
 * Authorize only a given set of features
 * 
 * @author Vincent Prat @ MarvinLabs
 */
public class AuthorizePackagePolicy implements AuthorizationPolicy {

	private String packageName;
	private Uri.Builder baseUriBuilder;

	/**
	 * Create a new instance of this class using a context. The package name will be extracted from this context. This
	 * function shall be used in the locked application to build the queries.
	 * 
	 * @param context
	 *            The context from which to extract the package name
	 * @return The policy
	 */
	public static AuthorizePackagePolicy newInstanceFromContext(Context context, String authority) {
		AuthorizePackagePolicy policy = new AuthorizePackagePolicy(context.getPackageName(), authority);
		return policy;
	}

	/**
	 * Create a new instance of this class by directly passing it the package name to be authorized.
	 * 
	 * @param packageName
	 *            The package we want to authorize
	 * @return The policy
	 */
	public static AuthorizePackagePolicy newInstance(String packageName, String authority) {
		AuthorizePackagePolicy policy = new AuthorizePackagePolicy(packageName, authority);
		return policy;
	}

	@Override
	public String getUriMatcherPath() {
		return packageName;
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
		final List<String> pathSegments = uri.getPathSegments();
		if (pathSegments.size() > 0) {
			return packageName.equals(pathSegments.get(0));
		}
		return false;
	}

	protected String getPackageName() {
		return packageName;
	}

	protected Builder getBaseUriBuilder() {
		return baseUriBuilder;
	}

	/**
	 * We don't want anybody to instantiate directly the class
	 */
	protected AuthorizePackagePolicy(String packageName, String authority) {
		this.packageName = packageName;
		this.baseUriBuilder = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(authority)
				.path(packageName);
	}

	@Override
	public String toString() {
		final String[] querySelectionArgs = getQuerySelectionArgs();
		return String.format("%s\nUriMatcher path = %s\nQuery Uri = %s\nSelection Args = %s", getClass().getName(),
				getUriMatcherPath(), getQueryUri(), querySelectionArgs != null ? Arrays.toString(querySelectionArgs)
						: null);
	}

}
