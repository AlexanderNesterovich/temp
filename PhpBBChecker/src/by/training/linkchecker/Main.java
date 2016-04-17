package by.training.linkchecker;

import java.util.ArrayList;
import java.util.List;

import by.training.linkchecker.model.Action;
import by.training.linkchecker.model.Invoker;
import by.training.linkchecker.model.Page;
import by.training.linkchecker.model.Report;
import by.training.linkchecker.parsers.Parser;
import by.training.linkchecker.parsers.TxtParser;
import by.training.linkchecker.parsers.XlsParser;
import by.training.linkchecker.utils.InputChecker;
import by.training.linkchecker.writers.Writer;
import by.training.linkchecker.writers.WriterToHtml;
import by.training.linkchecker.writers.WriterToTxt;
import by.training.linkchecker.writers.WriterToXml;

public class Main {
	
    /**
     * Program reads commands from txt/xls file, executes them and writes results of executing in log file.
     * Input argument from console {path + type of report} or just path(default type is txt)
     * For example H:\haha.txt or H:\haha.txt HTML or H:\haha.xls XML or H:\haha.xls XLS
     * 
     * Input file needs to be encoded in UTF8
     * Possible commands in txt/xls file:
     * open "url" "waiting time in seconds" /default waiting time is 9 seconds/
     * ping url/domainName/ip "number of pings" "delay between pings" /default 3 pings with delay of 1000ms/
     * addMessage "forumname" "topicname" 
     * addTopic  "forumname"
     * checkPageContainsLink "text"
     * checkPageContainsText "text"
     * checkPostsCount "forumname" "topicname" path for creating testing message.
     * checkTopicsCount "forumname" path for creating testing topic
     * checkUsersCount "username" "password" "email" creating testing user for checking.
     * checkWhoIsOnline "name who needs to be online"
     * editMessage "forumname" "topicname" for edit message if possible.
     * login "name" "password" "!" login if possible. ! invert result of command. if we expect fail.
     * logout "name" logout from given account.
     * registerUser "name" "password" "email" "!" register user if possible. ! invert result of command.
     * search "what to search" "expected number of results"
     * 
     * For difficult queries possible to escape quotes inside quotes with -QUOT-. 
     * Instead of "content="blabla"" — "content=-QUOT-blabla-QUOT-"
     * 
     * Reportline  
     * positive+/negative! + original command line + time of execution + message
     * 
     * @param args User input from command line.
     */

	/**
	 * @param args 1. path of input file. 2. extension for output
	 */
	public static void main(String[] args) {
		InputChecker selector = new InputChecker(args);
		//creating parser for startup checks.
		Parser startupParser = new XlsParser();
		startupParser.parseByPath("src/onstartup.xls");
		//creating parser for checks from file.
		Parser parserFromFolder = selector.getParser();
		//getting writer that selected by args[1] or default.
		Writer writer = selector.getWriter();
		parserFromFolder.parseByPath(selector.getPath());
		//invoker of commands
		Invoker invoker = new Invoker();
		//connects list of command from startup parser and fromFileParser.
		List<Action> commandList = new ArrayList<Action>();
		commandList.addAll(startupParser.getCommandsList());
		commandList.addAll(parserFromFolder.getCommandsList());

		//cycle by command and form reportlist.
		for (Action p : commandList) {
			Report r = invoker.executeCommand(p.getKeyword(), p.getOriginal(), p.getCoord(), p.getArguments());
			writer.addReport(r);
		}
		//close browser
		Page.close();
		//reports to string
		writer.printReports();
		//write reports
		writer.writeReports(selector.getPath());
		
		
	}
}
