package by.training.linkchecker.utils;

import by.training.linkchecker.parsers.Parser;
import by.training.linkchecker.parsers.TxtParser;
import by.training.linkchecker.parsers.XlsParser;
import by.training.linkchecker.writers.Writer;
import by.training.linkchecker.writers.WriterToHtml;
import by.training.linkchecker.writers.WriterToTxt;
import by.training.linkchecker.writers.WriterToXls;
import by.training.linkchecker.writers.WriterToXml;
/**
 * Utility class for Checking input from user cmd(path and type of log file).
 */
public class InputChecker {
	String message = "Incorrect Input! Only path to txt/xls file! For example: H:/hahah.txt, H:/hahaha.xls HTML, H:/hahaha.txt XML etc";
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
			case "XLS":
				return new WriterToXls();
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

	public Parser getParser() {
		if (args[0].toLowerCase().endsWith(".xls")) {
			return new XlsParser();
		}else if (args[0].toLowerCase().endsWith(".txt")){
			return new TxtParser();
		}
		System.err.println(message);
		System.exit(0);
		return null;
	}
	
	
	
}
