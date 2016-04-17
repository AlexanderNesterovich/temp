package by.training.DBChecker.utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import by.training.DBChecker.utils.NoSuchTextException;

public class CustomWebDriver implements WebDriver{
	
	private WebDriver driver;
	WebDriverWait wait;
	
	/**
	 * @param driver original driver.
	 * @param timeLimit setting timelimit.
	 */
	public CustomWebDriver(WebDriver driver, int timeLimit) {
		this.driver = driver;
		this.driver.manage().timeouts().pageLoadTimeout(timeLimit, TimeUnit.SECONDS);
		this.driver.manage().timeouts().implicitlyWait(timeLimit, TimeUnit.SECONDS);
		wait = new WebDriverWait(this.driver, timeLimit);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	public void get(String url) {
		driver.get(url);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	public String getTitle() {
		return driver.getTitle();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
	 */
	public WebElement findElement(By by) {
		//custom findElement with expectedConditions.
		try {
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		}catch(NoSuchElementException e) {
			throw new NoSuchElementException("Element was not found! " + by.toString());
		}
		return driver.findElement(by);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	public String getPageSource() {
		return driver.getPageSource();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	public void close() {
		driver.close();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	public void quit() {
		driver.quit();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	public Navigation navigate() {
		return driver.navigate();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	public Options manage() {
		return driver.manage();
	}
	
	/**
	 * @param element webelement for inputting.
	 * @param text actual text for input into webelement.
	 */
	public void sendKeys(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
		//checking for succes send keys.
		if(!element.getAttribute("value").equals(text)) {
			throw new NoSuchTextException(text);
		}
		
	}
	
	/**
	 * Possible custom click(). not used in this app.
	 * @param element element to click.
	 */
	public void click(WebElement element) {
		element.click();
	}

	/**
	 * @return return jsexecutor from original webdriver, because our wrapper cant be casted to it.
	 */
	public JavascriptExecutor getJSE() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js;
	}

}
