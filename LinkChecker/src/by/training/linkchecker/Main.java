package by.training.linkchecker;

import by.training.linkchecker.model.Action;
import by.training.linkchecker.model.Invoker;
import by.training.linkchecker.parsers.Parser;
import by.training.linkchecker.parsers.TxtParser;
import by.training.linkchecker.utils.InputChecker;
import by.training.linkchecker.writers.Writer;

public class Main {
	
    /**
     * Program reads commands from txt file, executes them and writes results of executing in log file
     * Input argument from console {path + type of report} or just path(default type is txt)
     * For example H:\haha.txt or H:\haha.txt HTML or H:\haha.txt XML or H:\haha.txt TXT
     * 
     * Input file needs to be encoded in UTF8
     * Possible commands in txt file:
     * open "url" "timeout" /default timeout is 3000/
     * ping url/domainName/ip "number of pings" "delay between pings" /default 3 pings with delay of 1000/
     * checkPageTitle "title"
     * checkPageContains "text"
     * checkLinkPresentByHref "href"
     * checkLinkPresentByName "name"
     * 
     * Time settings in Millisecond
     * For difficult queries possible to escape quotes inside quotes with -QUOT-. 
     * Instead of "content="blabla"" — "content=-QUOT-blabla-QUOT-"
     * 
     * Reportline  
     * positive+/negative! + original command line + time of execution + message
     * 
     * @param args User input from command line.
     */

	public static void main(String[] args) {

		Parser parser = new TxtParser();
		InputChecker selector = new InputChecker(args);

		Writer writer = selector.getWriter();

		parser.parseByPath(selector.getPath());

		Invoker invoker = new Invoker();

		for (Action p : parser.getCommandsList()) {
			writer.addReport(invoker.executeCommand(p.getKeyword(), p.getOriginal(), p.getArguments()));
		}

		writer.printReports();
		writer.writeReports(selector.getPath());

	}

}
