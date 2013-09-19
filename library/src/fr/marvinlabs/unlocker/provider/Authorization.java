package fr.marvinlabs.unlocker.provider;

import android.database.MatrixCursor;
import android.provider.BaseColumns;

/**
 * 
 * 
 * @author Vincent Prat @ MarvinLabs
 */
public class Authorization {
	
	public static final class AuthorizationColumns implements BaseColumns {

		public static final String IS_AUTHORIZED = "IS_AUTHORIZED";

		private static final String[] COLUMN_NAMES = new String[] { IS_AUTHORIZED };

		/**
		 * Instanciate a new matrix cursor
		 * 
		 * @return
		 */
		static MatrixCursor newCursor() {
			return new MatrixCursor(COLUMN_NAMES);
		}

		/**
		 * Add a row to the given cursor
		 * 
		 * @param cursor
		 *            The cursor to update
		 * @param isAuthorized
		 *            The result of the authorization
		 */
		static void addRowToCursor(MatrixCursor cursor, boolean isAuthorized) {
			Object[] values = new Object[COLUMN_NAMES.length];
			values[0] = new Integer(isAuthorized ? 1 : 0);

			cursor.addRow(values);
		}

		private AuthorizationColumns() {
		}
	}
}
