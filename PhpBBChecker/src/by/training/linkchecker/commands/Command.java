package by.training.linkchecker.commands;

import by.training.linkchecker.model.Report;

public interface Command {
	
    /**
     * Interface of Command Object which should execute command. Used for polymorphic code.
     * @param args Arguments for command.
     * @return Report.
     */
	public Report executeCommand(String... args);

}
