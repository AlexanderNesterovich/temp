package by.training.linkchecker.parsers;

import java.util.List;

import by.training.linkchecker.model.Action;

/**
 * Interface for parsers. Parsers should parse txt files with String path as input and getting list of commands. Used for polymorphic code.
 */
public interface Parser {
	
	public void parseByPath(String path);
	public List<Action> getCommandsList();

}
