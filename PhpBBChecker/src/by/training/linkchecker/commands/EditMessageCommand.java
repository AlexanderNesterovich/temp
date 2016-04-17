package by.training.linkchecker.commands;

import by.training.linkchecker.model.Invoker;
import by.training.linkchecker.model.Report;

public class EditMessageCommand implements Command{

    /**
     * Execute the command, and return the result.
     * @return report object with the result of execution.
     * @param args arguments for execution command.
     */
	@Override
	public Report executeCommand(String... args) {
		return Invoker.page.editMessage(args);
	}

}
