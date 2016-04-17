package by.training.linkchecker.model;

import org.openqa.selenium.NoSuchElementException;

public class NoSuchTextException extends NoSuchElementException {

	/**
	 * Custom exception for bad sendkeys case.
	 */
	private static final long serialVersionUID = 3954090113286688234L;

	public NoSuchTextException(String message) {
		super(message);
	}

}
