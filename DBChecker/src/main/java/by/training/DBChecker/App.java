package by.training.DBChecker;

import org.openqa.selenium.firefox.FirefoxDriver;

import by.training.DBChecker.utils.ColumnInitializer;
import by.training.DBChecker.utils.CustomWebDriver;
import by.training.DBChecker.utils.RowsInitializer;

/**
 * Program for creating database in phpmyadmin;
 */
public class App {
	public static void main(String[] args) {
		//creating custom driver
		CustomWebDriver driver = new CustomWebDriver(new FirefoxDriver(), 9);
		//creating page object
		PmaPage pma = new PmaPage(driver);
		pma.login("root","root", "http://127.0.0.1:8080/phpmyadmin/");
		//just example of method for working with CodeMirror.js.
		pma.executeSql("DROP DATABASE auth;");
		pma.createDB("auth", "utf8_general_ci");
		pma.createTableWithColumns("users", ColumnInitializer.getList());
		pma.setDBOptions("InnoDB", 3);
		pma.fillRows(RowsInitializer.getList());
	}
}
