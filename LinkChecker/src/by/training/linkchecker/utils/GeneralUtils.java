package by.training.linkchecker.utils;

import java.text.DecimalFormat;
/**
 * "static" class with general utility methods frequently used in this app.
 */
public class GeneralUtils {

	private static DecimalFormat threeDigitsFormatter = new DecimalFormat("0.000");

	/**
	 * Checking for valid integer(0 to maxInt).
	 * @param argument String as number.
	 * @return true for valid
	 */
	static public boolean validInteger(String argument) {
		try {
			int tmp = Integer.parseInt(argument);
			if (tmp > 0 && tmp < Integer.MAX_VALUE) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Transform long number to string with with precision of three digits.
	 * @param time Long type representing time for example.
	 * @return String with precision of three digits.
	 */
	static public String getThreeDigitsTimeInSeconds(Long time) {

		if (time > 0) {
			Double d = time / 1000000000.0;
			return threeDigitsFormatter.format(d);
		}else{
			return "0";
		}

	}

}
