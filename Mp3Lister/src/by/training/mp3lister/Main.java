package by.training.mp3lister;

import by.training.mp3lister.utils.FolderChecker;
import by.training.mp3lister.writers.WriterToHtml;

public class Main {
	
    /**
     * Program reads commands from commandLine file, executes them and writes results in HTML log file.
     * Input argument from console {path where to save HTML log + paths of folders for checking}
     * 
     * @param args User input from command line.
     */
	public static void main(String[] args) {
		//checking arguments
		if (args.length < 2) {
			System.out.println("2 or more arguments required. 1. Path for html_log 2. Path to mp3 files 3+ another paths to mp3 files");
			System.exit(0);
		}
		//check every folder
		for(int k = args.length; k > 1; k--) {
			FolderChecker checker = new FolderChecker();
			checker.checkFolder(args[k-1]);
		}
		//write HTML report
		WriterToHtml writer = new WriterToHtml();
		writer.writeReport(args[0]);
	}

}
