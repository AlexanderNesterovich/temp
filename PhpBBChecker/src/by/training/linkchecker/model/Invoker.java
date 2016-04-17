package by.training.linkchecker.model;

import java.util.Map;

import java.util.TreeMap;
import by.training.linkchecker.commands.CheckPageContainsTextCommand;
import by.training.linkchecker.commands.CheckPostsCountCommand;
import by.training.linkchecker.commands.CheckTopicsCountCommand;
import by.training.linkchecker.commands.CheckUsersCountCommand;
import by.training.linkchecker.commands.CheckWhoIsOnlineCommand;
import by.training.linkchecker.commands.AddMessageCommand;
import by.training.linkchecker.commands.AddTopicCommand;
import by.training.linkchecker.commands.CheckPageContainsLinkCommand;
import by.training.linkchecker.commands.LoginCommand;
import by.training.linkchecker.commands.LogoutCommand;
import by.training.linkchecker.commands.SearchCommand;
import by.training.linkchecker.commands.Command;
import by.training.linkchecker.commands.EditMessageCommand;
import by.training.linkchecker.commands.OpenCommand;
import by.training.linkchecker.commands.PingCommand;
import by.training.linkchecker.commands.RegisterUserCommand;


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
		commandMap.put("checkPageContainsText", new CheckPageContainsTextCommand());
		commandMap.put("checkPageContainsLink", new CheckPageContainsLinkCommand());
		commandMap.put("search", new SearchCommand());
		commandMap.put("login", new LoginCommand());
		commandMap.put("logout", new LogoutCommand());
		commandMap.put("checkWhoIsOnline", new CheckWhoIsOnlineCommand());
		commandMap.put("addMessage", new AddMessageCommand());
		commandMap.put("editMessage", new EditMessageCommand());
		commandMap.put("addTopic", new AddTopicCommand());
		commandMap.put("registerUser", new RegisterUserCommand());
		commandMap.put("checkPostsCount", new CheckPostsCountCommand());
		commandMap.put("checkTopicsCount", new CheckTopicsCountCommand());
		commandMap.put("checkUsersCount", new CheckUsersCountCommand());
		commandMap.put("open", new OpenCommand());
	}

	
    /**
     * Creating Action object with array of parsed words and original string from user.
     * @param keyWord /open/ping etc.
     * @param original string from user for proper report.
     * @param coord coords for writing into same cells for xls writing.
     * @param args Arguments for command.
     * @return report with information about execution.
     */
	public Report executeCommand(String keyWord, String original, int[] coord, String... args) {

		
			/*if proper keyword - execute command, count time and add information to existing report from command*/
			if (commandMap.containsKey(keyWord)) {
				Command command = commandMap.get(keyWord);
				start = System.nanoTime();
				try {
					report = command.executeCommand(args);
				}catch(Exception e){
					System.out.println("Problem in executing command: " + original);
					System.out.println(e.getMessage());
					report = new Report(false, "Problem in executing command: " + original);
				}
				elapsedTime = System.nanoTime() - start;
				report.setTime(elapsedTime);
				report.setOriginal(original);
				report.setKeyword(keyWord);
				report.setCoord(coord);
				return report;
			}else{
				/*bad input from user(malformed keyword), form report with skipped message for skipping it in final  reply to user*/
				report = new Report(false, "(skipped)");
				report.setOriginal(original);
				return report;
			}
	}

}
