package by.training.DBChecker;

import org.testng.annotations.Test;

import by.training.DBChecker.utils.ColumnInitializer;
import by.training.DBChecker.utils.CustomWebDriver;
import by.training.DBChecker.utils.DBColumn;
import by.training.DBChecker.utils.DBRow;
import by.training.DBChecker.utils.RowsInitializer;
import junit.framework.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

/**
 *	testNG testing class for checking proper db creation.
 */
public class CheckDB {
	
	CustomWebDriver driver;
	
	/**
	 * opens our pma page.
	 */
	@BeforeClass
	public void beforeClass() {
		driver = new CustomWebDriver(new FirefoxDriver(), 9);
		PmaPage pma = new PmaPage(driver);
		pma.login("root", "root", "http://127.0.0.1:8080/phpmyadmin/");
	}

	/**
	 * close driver after completion.
	 */
	@AfterClass
	public void afterClass() {
		driver.close();
	}
	
	/**
	 * @return options for checking
	 */
	@DataProvider(name = "tableOptions")
	public Object[][] tableOptions() {
		return new Object[][] {
			
				new Object[] { "auth", "users", ColumnInitializer.getList()}, 
				};
	}

	/**
	 * @param dbName name of database.
	 * @param tableName name of table.
	 * @param list list of columns to check.
	 */
	@Test(dataProvider = "tableOptions")
	public void checkDBStructure(String dbName, String tableName, List<DBColumn> list) {
		String dataTableLocator = "//html/body/div[5]/div[3]/form[1]/table/tbody/tr[";
		//elegant way but doesnt work.
		//WebElement root = driver.findElement(By.id("tablestructure")).findElement(By.tagName("tbody"));
		//System.out.println(root.findElement(By.xpath("//tr[count]/th/label")).getText());
		driver.findElement(By.linkText(dbName)).click();
		driver.findElement(By.linkText(tableName)).click();
		driver.findElement(By.linkText(" Structure")).click();
		//cycle for checking every column.
		for (int k = 1; k < list.size(); k++) {
			DBColumn column = list.get(k-1);
			Assert.assertEquals(column.getName(), driver.findElement(By.xpath(dataTableLocator + k + "]/th/label")).getText());
			if(!column.getIndex().equals("---")) {
				Assert.assertEquals(column.getIndex().toLowerCase(), driver.findElement(By.xpath(dataTableLocator + k + "]/th/label/img")).getAttribute("title").toLowerCase());
			}
			Assert.assertEquals(column.getType().toLowerCase() + "(" + column.getLength() + ")", driver.findElement(By.xpath(dataTableLocator + k + "]/td[3]/bdo")).getText());
			Assert.assertEquals(column.getAttributes(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[5]")).getText());
			Assert.assertEquals(column.getIsNull() ? "Yes" : "No", driver.findElement(By.xpath(dataTableLocator + k + "]/td[6]")).getText());
			Assert.assertEquals(column.getDefaultType().toLowerCase(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[7]/i")).getText().toLowerCase());
			Assert.assertEquals(column.getAI() ? "AUTO_INCREMENT" : "", driver.findElement(By.xpath(dataTableLocator + k + "]/td[8]")).getText());
		}
	}
	/**
	 * @return options for checking
	 */
	@DataProvider(name = "tableContent")
	public Object[][] inValidInput() {
		return new Object[][] {
			
				new Object[] { "auth", "users", RowsInitializer.getList()}, 
				};
	}
	
	/**
	 * @param dbName name of database.
	 * @param tableName name of table.
	 * @param list list of rows to check.
	 */
	@Test(dataProvider = "tableContent")
	public void checkDBContent(String dbName, String tableName, List<DBRow> list) {
		String dataTableLocator = "/html/body/div[5]/div[2]/form[3]/div[1]/table[2]/tbody/tr[";
		//elegant way but doesnt work
		//WebElement root = driver.findElement(By.className("data")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		//System.out.println(root.findElement(By.xpath("tr[count]/td[5]/span")).getText());
		driver.findElement(By.linkText(dbName)).click();
		driver.findElement(By.linkText(tableName)).click();
		driver.findElement(By.linkText(" Browse")).click();
		//cycle for row checking
		for (int k = 1; k < list.size(); k++) {
			DBRow row = list.get(k-1);
			Assert.assertEquals(row.getId(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[5]/span")).getText());
			Assert.assertEquals(row.getLogin(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[6]/span")).getText());
			Assert.assertEquals(row.getPassword(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[7]/span")).getText());
			Assert.assertEquals(row.getEmail(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[8]/span")).getText());
			Assert.assertEquals(row.getName(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[9]/span")).getText());
			Assert.assertEquals(row.getRemember(), driver.findElement(By.xpath(dataTableLocator + k + "]/td[10]/span")).getText());
		}
	}

}
