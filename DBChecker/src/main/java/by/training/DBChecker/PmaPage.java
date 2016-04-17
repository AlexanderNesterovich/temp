package by.training.DBChecker;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import by.training.DBChecker.utils.CustomWebDriver;
import by.training.DBChecker.utils.DBColumn;
import by.training.DBChecker.utils.DBRow;

/**
 *	Object with all methods for working with phpmyadmin.
 */
public class PmaPage {

	private CustomWebDriver driver;
	private String indexPage;

	/**
	 * @param driver Using our wrapper driver with some upgrades.
	 */
	public PmaPage(CustomWebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * @param query sql query for executing.
	 */
	public void executeSql(String query) {
		System.out.println("Executing sql...");
		driver.findElement(By.linkText(" SQL")).click();
		WebElement queryInput = driver.findElement(By.id("sqlquerycontainerfull")).findElement(By.className("CodeMirror"));
		//calling js code for working with CodeMirror.js
		JavascriptExecutor js = driver.getJSE();
		js.executeScript("arguments[0].CodeMirror.setValue(\"" + query + "\");", queryInput);
		driver.findElement(By.id("button_submit_query")).submit();
		driver.get(indexPage);
	}

	/**
	 * @param login login for pma.
	 * @param password password for pma.
	 * @param pmaUrl address for pma.
	 */
	public void login(String login, String password, String pmaUrl) {
		System.out.println("Login...");
		driver.get(pmaUrl);
		indexPage = pmaUrl;
		new Select(driver.findElement(By.id("sel-lang"))).selectByValue("en");
		driver.sendKeys(driver.findElement(By.id("input_username")), login);
		driver.sendKeys(driver.findElement(By.id("input_password")), password);
		driver.findElement(By.id("input_go")).click();
	}

	/**
	 * @param name name of DB
	 * @param coding coding of DB
	 */
	public void createDB(String name, String coding) {
		System.out.println("Create DB...");
		driver.findElement(By.linkText(" Databases")).click();
		driver.sendKeys(driver.findElement(By.id("text_create_db")), name);
		new Select(driver.findElement(By.name("db_collation"))).selectByValue(coding);
		driver.findElement(By.id("buttonGo")).click();
	}

	/**
	 * @param name name of table.
	 * @param list list of columns.
	 */
	public void createTableWithColumns(String name, List<DBColumn> list) {
		System.out.println("Create Table...");
		driver.sendKeys(driver.findElement(By.name("table")), name);
		driver.sendKeys(driver.findElement(By.name("num_fields")), new Integer(list.size()).toString());
		WebElement button = driver.findElement(By.className("tblFooters"));
		//cycle for automatic filling every field with proper info that DBColumn object contains.
		button.findElement(By.tagName("input")).click();
		for (int k = 0; k < list.size(); k++) {
			DBColumn column = list.get(k);
			driver.sendKeys(driver.findElement(By.id("field_" + k + "_1")), column.getName());
			new Select(driver.findElement(By.id("field_" + k + "_2"))).selectByVisibleText(column.getType());
			driver.sendKeys(driver.findElement(By.id("field_" + k + "_3")), column.getLength());
			new Select(driver.findElement(By.id("field_" + k + "_4"))).selectByValue(column.getDefaultType()); // NONE
			new Select(driver.findElement(By.name("field_collation[" + k + "]"))).selectByValue(column.getCollation());
			new Select(driver.findElement(By.name("field_attribute[" + k + "]"))).selectByValue(column.getAttributes());
			if (column.getIsNull()) {
				if (!driver.findElement(By.id("field_" + k + "_6")).isSelected()) {
					driver.findElement(By.id("field_" + k + "_6")).click();
				}
				;
			}
			//selecting index with popup.
			if (!column.getIndex().equals("---")) {
				new Select(driver.findElement(By.id("field_" + k + "_7"))).selectByVisibleText(column.getIndex());
				driver.findElement(By.xpath("/html/body/div[10]/div[3]/div/button[1]")).click();
			}
			//selecting AI popup.
			if (column.getAI() && !column.getIndex().equals("---")) {
				if (!driver.findElement(By.id("field_" + k + "_8")).isSelected()) {
					driver.findElement(By.id("field_" + k + "_8")).click();
				}
				;
			}
			driver.sendKeys(driver.findElement(By.id("field_" + k + "_9")), column.getComments());
			new Select(driver.findElement(By.id("field_" + k + "_5"))).selectByValue(column.getVirtuality());
		}
		button = driver.findElement(By.name("do_save_data"));
		button.submit();
		//waiting for element that shows when table will be created.
		driver.findElement(By.className("data"));
	}

	/**
	 * @param engine selecting DBengine
	 * @param i selecting AI count.
	 */
	public void setDBOptions(String engine, int i) {
		System.out.println("Settting DB Options...");
		driver.findElement(By.linkText(" Operations")).click();
		new Select(driver.findElement(By.name("new_tbl_storage_engine"))).selectByValue(engine);
		driver.findElement(By.id("auto_increment_opt")).clear();
		driver.sendKeys(driver.findElement(By.id("auto_increment_opt")), new Integer(i).toString());
		WebElement button = driver.findElement(By.id("tableOptionsForm")).findElement(By.className("tblFooters"));
		button.findElement(By.tagName("input")).submit();
		//waiting for element that shows when options will be defined.
		driver.findElement(By.className("success"));
	}

	/**
	 * @param list of rows objects for table filling with content.
	 */
	public void fillRows(List<DBRow> list) {
		System.out.println("Settting DB Data...");
		for (int k = 0; k < list.size(); k++) {
			DBRow row = list.get(k);
			driver.findElement(By.linkText(" Insert")).click();
			driver.sendKeys(driver.findElement(By.id("field_1_3")), row.getId());
			driver.sendKeys(driver.findElement(By.id("field_2_3")), row.getLogin());
			driver.sendKeys(driver.findElement(By.id("field_3_3")), row.getPassword());
			driver.sendKeys(driver.findElement(By.id("field_4_3")), row.getEmail());
			driver.sendKeys(driver.findElement(By.id("field_5_3")), row.getName());
			driver.sendKeys(driver.findElement(By.id("field_6_3")), row.getRemember());
			driver.findElement(By.className("tblFooters")).findElement(By.tagName("input")).submit();
			driver.findElement(By.className("success"));
		}
		driver.close();
	}

}
