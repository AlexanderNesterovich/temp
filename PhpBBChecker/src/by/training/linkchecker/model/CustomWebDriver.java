package by.training.linkchecker.model;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomWebDriver implements WebDriver{
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	/**
	 * @param driver original driver.
	 * @param timeLimit waiting time.
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
		waitForJStoLoad();
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
		//overriden findElement with expectedconditions
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
	 * sendKeys with autovalidation
	 * @param element element for keys
	 * @param text keys
	 */
	public void sendKeys(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
		if(!element.getAttribute("value").equals(text)) {
			throw new NoSuchTextException(text);
		}
	}
	
	/**
	 * hard wait for element, for difficult js/ajax cases.
	 * @param element element for keys.
	 * @param text keys
	 * @param wait time to wait
	 * @throws InterruptedException default exception for sleep
	 */
	public void sendKeysWait(WebElement element, String text, int wait) throws InterruptedException {
		sendKeys(element, text);
		Thread.sleep(wait);
	}
	
	/**
	 * Possible custom click.
	 * @param element webelemnt to click.
	 */
	public void click(WebElement element) {
		element.click();
		waitForJStoLoad();
	}
	
	/**
	 * @return true if js loaded.
	 */
	private boolean waitForJStoLoad() {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long)jse.executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jse.executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }
	
}
