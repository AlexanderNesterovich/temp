package by.training.linkchecker.utils;

import by.training.linkchecker.writers.Writer;
import by.training.linkchecker.writers.WriterToHtml;
import by.training.linkchecker.writers.WriterToTxt;
import by.training.linkchecker.writers.WriterToXml;
/**
 * Utility class for Checking input from user from cmd(path and type of log file).
 */
public class InputChecker {
	String message = "Incorrect Input! Only path to txt file! For example: H:/hahah.txt, H:/hahaha.txt HTML, H:/hahaha.txt XML etc";
	String args[];
	
	public InputChecker(String... args) {
		this.args = args;
		if(args.length < 1) {
			System.err.println(message);
			System.exit(0);
		}
	}
	
	/**
	 * Getting writer base on user selection or using default.
	 * @return Writer object based on user selection.
	 */
	public Writer getWriter() {
		
		if (args.length == 1) {
			return new WriterToTxt();
		} else if (args.length > 1) {
			switch (args[1]) {
			case "TXT":
				return new WriterToTxt();
			case "HTML":
				return new WriterToHtml();
			case "XML":
				return new WriterToXml();
			default:
				System.out.println("Malformed format: Using standart TXT");
				return new WriterToTxt();
			}
		}
		
		System.err.println(message);
		System.exit(0);
		return new WriterToTxt();

	}
	
	public String getPath() {
		return args[0];
	}
	
	
	
}
