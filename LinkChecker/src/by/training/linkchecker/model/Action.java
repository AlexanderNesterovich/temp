package by.training.linkchecker.model;

import java.util.Arrays;


/**
 * Object representing properly parsed command line from user.
 */
public class Action {
	
	private String[] words = {""};
	private String original;

    /**
     * Creating Action object with array of parsed words and original string from user.
     * @param words Array of words.
     * @param original Original string from user.
     */
	public Action(String[] words, String original) {

		this.original = original;
		this.words = words;
	}
    /**
     * Creating Action object with with original string only for situations when 0 words have been parsed.
     * @param original Original input string.
     */
	public Action(String original) {
		
		this.original = original;
		
	}
    /**
     * Getting keyword, first element of words array.
     * @return Keyword open/ping/checkpageTitle etc.
     */
	public String getKeyword() {
		return words[0];
		
	}
    /**
     * Getting arguments for command, all elements of words array except the first.
     * @return Array of arguments.
     */
	public String[] getArguments() {
		return Arrays.copyOfRange(words, 1, words.length);
	}
    /**
     * Getting original input string from user.
     * @return Original input string.
     */
	public String getOriginal() {
		return original;
	}
}
