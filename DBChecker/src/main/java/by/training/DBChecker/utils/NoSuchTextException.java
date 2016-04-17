package by.training.DBChecker.utils;

import org.openqa.selenium.NoSuchElementException;

//custom exception for customwebdriver.
public class NoSuchTextException extends NoSuchElementException {

	private static final long serialVersionUID = 1L;

	public NoSuchTextException(String message) {
		super(message);
	}

}
