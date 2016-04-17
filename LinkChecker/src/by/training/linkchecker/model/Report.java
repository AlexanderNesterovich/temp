package by.training.linkchecker.model;

import by.training.linkchecker.utils.GeneralUtils;

/**
 * Object representing report with information about execution of command.
 */
public class Report {

	private boolean state;
	private String message = "";
	private String original = "";
	private Long time = 0L;
	private String keyword = "";
	GeneralUtils utils = new GeneralUtils();

	/**
	 * Creating with message and state, other params like time or original setting in another parts of code.
	 * @param state State true for positive test.
	 * @param message Information about executing.
	 */
	public Report(boolean state, String message) {
		this.state = state;
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (state == true) {
			result.append("+");
		} else {
			result.append("!");
		}
		result.append(" [" + original + "] " + GeneralUtils.getThreeDigitsTimeInSeconds(time) + "s " + message);
		return result.toString();
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public boolean getState() {
		return state;
	}

	public String getOriginal() {
		return original;
	}
	
	public Long getTime() {
		return time;
	}
	

}
