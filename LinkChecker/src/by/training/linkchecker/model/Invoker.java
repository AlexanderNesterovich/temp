package by.training.linkchecker.model;

import java.util.Map;

import java.util.TreeMap;
import by.training.linkchecker.commands.CheckLinkByHrefCommand;
import by.training.linkchecker.commands.CheckLinkByNameCommand;
import by.training.linkchecker.commands.CheckPageContainsCommand;
import by.training.linkchecker.commands.CheckPageTitleCommand;
import by.training.linkchecker.commands.Command;
import by.training.linkchecker.commands.OpenCommand;
import by.training.linkchecker.commands.PingCommand;


/**
 * Object representing Invoker for connecting keywords from user and actual command objects.
 */
public class Invoker {

	private long start;
	private long elapsedTime;
	private Report report;
	/*static page for working with one object for every command. Open command works as initializer*/
	public static Page page = new Page();
	private Map<String, Command> commandMap = new TreeMap<String, Command>();

	
    /**
     * Filling map of keyword-commandObject connection asap.
     */
	public Invoker() {
		commandMap.put("ping", new PingCommand());
		commandMap.put("checkLinkPresentByHref", new CheckLinkByHrefCommand());
		commandMap.put("checkLinkPresentByName", new CheckLinkByNameCommand());
		commandMap.put("checkPageTitle", new CheckPageTitleCommand());
		commandMap.put("checkPageContains", new CheckPageContainsCommand());
		commandMap.put("open", new OpenCommand());
	}

	
    /**
     * Creating Action object with array of parsed words and original string from user.
     * @param keyWord /open/ping etc.
     * @param original string from user for proper report.
     * @param args Arguments for command.
     * @return report with information about execution.
     */
	public Report executeCommand(String keyWord, String original, String... args) {

		
			/*if proper keyword - execute command, count time and add information to existing report from command*/
			if (commandMap.containsKey(keyWord)) {
				Command command = commandMap.get(keyWord);
				start = System.nanoTime();
				report = command.executeCommand(args);
				elapsedTime = System.nanoTime() - start;
				report.setTime(elapsedTime);
				report.setOriginal(original);
				report.setKeyword(keyWord);
				return report;
			}else{
				/*bad input from user(malformed keyword), form report with skipped message for skipping it in final  reply to user*/
				report = new Report(false, "(skipped)");
				report.setOriginal(original);
				return report;
			}

	}

}
