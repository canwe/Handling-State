package utils;

/**
 * net.finetunes.ftgw.application.controllers.calendar.Strings
 *
 * A variety of static String utility methods.
 * FE: check a String for emptiness ({@link #isEmpty(CharSequence)}
 *
 * @author victor.konopelko
 */
public final class Strings {
	
	private Strings() {

	}

	/**
	 * Checks whether the <code>string</code> is considered empty.
	 * Empty means that the string may contain whitespace,
	 * but no visible characters.
	 *
	 * "\n\t " is considered empty, while " a" is not.
	 *
	 * @param string
	 *            The string
	 * @return True if the string is null or ""
	 */
	public static boolean isEmpty(final CharSequence string) {
		return string == null
		    || string.length() == 0
		    || string.toString().trim().length() == 0;
	}

	/**
	 * Checks whether two strings are equals taken care of 'null' values
	 * and treating 'null' same as
	 * trim(string).equals("")
	 *
	 * @param string1
	 * @param string2
	 * @return true, if both strings are equal
	 */
	public static boolean isEqual(final String string1,
			                      final String string2) {
		if ((string1 == null) && (string2 == null)) {
			return true;
		}

		if (isEmpty(string1) && isEmpty(string2)) {
			return true;
		}
		if (string1 == null || string2 == null) {
			return false;
		}

		return string1.equals(string2);
	}
}
