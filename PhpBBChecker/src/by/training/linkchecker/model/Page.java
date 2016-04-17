package by.training.linkchecker.model;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import by.training.linkchecker.utils.GeneralUtils;

/**
 * Object representing phpbb3 page with methods to work with it.
 */
public class Page {

	private static CustomWebDriver driver = null;
	private int waitTime = 9;//default wait time.

	private List<WebElement> results;
	private WebElement rootElement;

	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report open(String... args) {
		try {
			if (args.length >= 2 && GeneralUtils.validInteger(args[1])) {
				if (driver == null) {
					driver = new CustomWebDriver(new FirefoxDriver(), Integer.parseInt(args[1]));
				}
				driver.get(args[0]);
				return new Report(true, "");
			} else {
				if (driver == null) {
					driver = new CustomWebDriver(new FirefoxDriver(), waitTime);
				}
				driver.get(args[0]);
				return new Report(true, "Using default timeout!");
			}
		} catch (Exception e) {
			return new Report(false, "Cant open Page");
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report checkPageContainsText(String... args) {
		if (driver.getPageSource().contains(args[0])) {
			return new Report(true, "");
		} else {
			return new Report(false, "Page doesnt have text: " + args[0]);
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report addMessage(String[] args) {
		if (args.length < 2) {
			return new Report(false, "This command need two arguments: Name of Forum and name of Topic");
		}
		backToIndex();
		results = driver.findElements(By.className("forumtitle"));
		if (getInside(results, args[0])) {
			results = driver.findElements(By.className("topictitle"));
			if (getInside(results, args[1])) {
				String randomString = randomizeString(50);
				results = driver.findElements(By.xpath("//a[contains(@title,'Post a reply')]"));
				if(results.size() > 0) {
					results.get(0).click();
					results = driver.findElements(By.id("message-box"));
					if(results.size() > 0) {
						driver.findElement(By.id("message-box")).click();
						try {
							driver.sendKeysWait(driver.findElement(By.id("message")), randomString, 1500);
						} catch (InterruptedException e) {
							return new Report(false, "Interrupted Thread!");
						}
						driver.findElement(By.name("post")).click();
						return checkPageContainsText(new String[] { randomString });
					}else{
						return new Report(false, "Message-box not found. Maybe user isn't logged in!");
					}
				}else{
					return new Report(false, "Button [Post reply] not found");
				}
			} else {
				return new Report(false, "Topic not found!");
			}
		} else {
			return new Report(false, "Forum not found!");
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report checkPageContainsLink(String[] args) {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		for (WebElement link : links) {
			if (link.getText().equals(args[0])) {
				return new Report(true, "");
			}
		}
		return new Report(false, "Page doesnt have link: " + args[0]);
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report checkWhoIsOnline(String[] args) {
		backToIndex();
		rootElement = driver.findElement(By.xpath("//div[@class='stat-block online-list']"));
		results = rootElement.findElements(By.className("username-coloured"));
		if (results.size() > 0) {
			for (WebElement r : results) {
				if (r.getText().equals(args[0])) {
					return new Report(true, "");
				}
			}
		}
		return new Report(false, args[0] + "is not online!");
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report editMessage(String[] args) {
		if (args.length < 2) {
			return new Report(false, "This command need two arguments: Name of Forum and name of Topic");
		}
		backToIndex();
		results = driver.findElements(By.className("forumtitle"));
		if (getInside(results, args[0])) {
			results = driver.findElements(By.className("topictitle"));
			if (getInside(results, args[1])) {
				String randomString = randomizeString(50);
				results = driver.findElements(By.xpath("//a[contains(@title,'Edit post')]"));
				if(results.size() > 0) {
					results.get(0).click();
					results = driver.findElements(By.id("message-box"));
					if(results.size() > 0) {
						driver.findElement(By.id("message-box")).click();
						try {
							driver.sendKeysWait(driver.findElement(By.id("message")), randomString, 1500);
						} catch (InterruptedException e) {
							return new Report(false, "Interrupted Thread!");
						}
						driver.findElement(By.name("post")).click();
						return checkPageContainsText(new String[] { randomString });
					}else{
						return new Report(false, "Message-box isn't found. Maybe user isn't logged in!");
					}
				}else{
					return new Report(false, "Button [Edit Message] not found!");
				}
					
			} else {
				return new Report(false, "Topic not found!");
			}
		} else {
			return new Report(false, "Forum not found!");
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report login(String[] args) {
		backToIndex();
		String login;
		String password;
		String expected = "positive";
		if (args.length > 2) {
			login = args[0];
			password = args[1];
			expected = args[2];
		} else if (args.length >= 2) {
			login = args[0];
			password = args[1];
		} else {
			return new Report(false, "Command needs two or more arguments!");
		}
		results = driver.findElements(By.xpath("//a[contains(@title,'Login')]"));
		if (results.size() > 0) {
			results.get(0).click();
			driver.sendKeys(driver.findElement(By.id("username")), login);
			driver.sendKeys(driver.findElement(By.id("password")), password);
			driver.findElement(By.className("fields1")).findElement(By.className("button1")).click();
			results = driver.findElements(By.className("error"));
			if (results.size() > 0) {
				if (!expected.equals("!")) {
					return new Report(false, results.get(0).getText());
				} else {
					return new Report(true, results.get(0).getText());
				}

			}
			if (!expected.equals("!")) {
				return new Report(true, "");
			} else {
				return new Report(false, "logged");
			}

		} else {
			if (!expected.equals("!")) {
				return new Report(false, "Someone already logged");
			} else {
				return new Report(true, "Someone already logged");
			}

		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report logout(String[] args) {
		if (checkIfLogged()) {
			rootElement = driver.findElement(By.id("nav-main"));
			String username = rootElement.findElement(By.className("username-coloured")).getText();
			if (username.equals(args[0])) {
				driver.findElement(By.className("username-coloured")).click();
				driver.findElement(By.xpath("//a[contains(@title,'Logout')]")).click();
				return new Report(true, "");
			} else {
				return new Report(false, "Unknown user: " + username);
			}
		} else {
			return new Report(true, "Nothing to Logout!");
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report search(String[] args) {
		String result = "";
		if (args.length < 2 || !GeneralUtils.validInteger(args[1])) {
			driver.sendKeys(driver.findElement(By.id("keywords")), args[0]);
			;
			driver.findElement(By.xpath("//button[contains(@title,'Search')]")).submit();
			results = driver.findElements(By.className("searchresults-title"));
			if (results.size() >= 1) {
				result = results.get(0).getText();
				return new Report(true, result);
			} else {
				return new Report(false, "Impossible Search");
			}

		} else {
			driver.sendKeys(driver.findElement(By.id("keywords")), args[0]);
			;
			driver.findElement(By.xpath("//button[contains(@title,'Search')]")).submit();
			results = driver.findElements(By.className("searchresults-title"));
			if (results.size() >= 1) {
				result = results.get(0).getText().split(":")[0];
				result = result.replaceAll("\\D+", "");
			} else {
				return new Report(false, "Impossible Search");
			}

			if (Integer.parseInt(result) == Integer.parseInt(args[1])) {
				return new Report(true, "");
			} else {
				return new Report(false, "Expected: " + args[1] + " Getting: " + result);
			}

		}

	}
	
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report addTopic(String[] args) {
		backToIndex();
		results = driver.findElements(By.className("forumtitle"));
		if (getInside(results, args[0])) {
				String randomSubject = randomizeString(10);
				String randomContent = randomizeString(30);
				driver.findElement(By.xpath("//a[contains(@title,'Post a new topic')]")).click();
				results = driver.findElements(By.id("message-box"));
				if(results.size() > 0) {
					try {
						driver.sendKeysWait(driver.findElement(By.id("message")), randomContent, 1500);
					} catch (InterruptedException e) {
						return new Report(false, "Interrupted Thread!");
					}
					try {
						driver.sendKeysWait(driver.findElement(By.id("subject")), randomSubject, 1500);
					} catch (InterruptedException e) {
						return new Report(false, "Interrupted Thread!");
					}
					driver.findElement(By.name("post")).click();
					Report report = checkPageContainsText(new String[] { randomSubject });
					if (report.getState()) {
						return checkPageContainsText(new String[] { randomContent });
					} else {
						return new Report(false, "Cant find Topic Text");
					}
				}else{
					return new Report(false, "Message-box not found. Maybe user isn't logged in!");
				}
				
		} else {
			return new Report(false, "Forum not found!");
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report registerUser(String[] args) {
		backToIndex();
		String login;
		String password;
		String email;
		String expected = "positive";
		if (args.length > 3) {
			login = args[0];
			password = args[1];
			email = args[2];
			expected = args[3];
		} else if (args.length >= 3) {
			login = args[0];
			password = args[1];
			email = args[2];
		} else {
			return new Report(false, "Command needs three or more arguments!");
		}
		WebElement root = driver.findElement(By.id("nav-main"));
		results = root.findElements(By.id("notification_list_button"));
		if (!(results.size() > 0)) {
			root.findElement(By.xpath("//*[text()[contains(.,'Register')]]")).click();
			driver.findElement(By.id("agreed")).click();
			driver.sendKeys(driver.findElement(By.id("username")), login);
			driver.sendKeys(driver.findElement(By.id("email")), email);
			driver.sendKeys(driver.findElement(By.id("new_password")), password);
			driver.sendKeys(driver.findElement(By.id("password_confirm")), password);
			driver.findElement(By.id("submit")).click();
			results = driver.findElements(By.className("error"));
			if (results.size() > 0) {
				if (!expected.equals("!")) {
					return new Report(false, results.get(0).getText());
				} else {
					return new Report(true, "");
				}
			}
			if (!expected.equals("!")) {
				return new Report(true, "");
			} else {
				return new Report(false, "logged");
			}
		} else {
			if (!expected.equals("!")) {
				return new Report(false, "Someone already logged");
			} else {
				return new Report(true, "Someone already logged");
			}
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report checkPostsCount(String[] args) {
		backToIndex();
		int before = checkStats(0);
		Report report = addMessage(args);
		if (report.getState()) {
			backToIndex();
			int now = checkStats(0);
			if (now - before == 1) {
				return new Report(true, "");
			} else {
				return new Report(false, "Count is not raised!");
			}
		} else {
			return report;
		}

	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report checkTopicsCount(String[] args) {
		backToIndex();
		int before = checkStats(1);
		Report report = addTopic(args);
		if (report.getState()) {
			backToIndex();
			int now = checkStats(1);
			if (now - before == 1) {
				return new Report(true, "");
			} else {
				return new Report(false, "Count is not raised!");
			}
		} else {
			return report;
		}
	}
	/**
	 * @param args arguments for command
	 * @return Report object with info about execution of command.
	 */
	public Report checkUsersCount(String[] args) {
		backToIndex();
		int before = checkStats(2);
		Report report = registerUser(args);
		if (report.getState()) {
			backToIndex();
			int now = checkStats(2);
			if (now - before == 1) {
				return new Report(true, "");
			} else {
				return new Report(false, "Count is not raised!");
			}
		} else {
			return report;
		}
	}

	// Utility methods
	
	/**
	 * @param length of string we needs
	 * @return random string
	 */
	private String randomizeString(int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyzабвгдфдывдфолыводот12314оырвагрйшрцвщзлхaosdh".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	/**
	 * @param inside list of elements.
	 * @param string element that we need to go into.
	 * @return true if we can go inside.
	 */
	private boolean getInside(List<WebElement> inside, String string) {
		if (inside.size() > 0) {
			for (WebElement el : inside) {
				if (el.getText().equals(string)) {
					el.click();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * go to main page method.
	 */
	private void backToIndex() {
		driver.findElement(By.xpath("//a[contains(@title,'Board index')]")).click();
	}

	/**
	 * Check if someone is logged.
	 * @return true if logged.
	 */
	private boolean checkIfLogged() {
		rootElement = driver.findElement(By.id("nav-main"));
		results = rootElement.findElements(By.id("username_logged_in"));
		if (results.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param partOfString which stat we needs 0 for posts 1 for topics 2 for members
	 * @return return only number of stat. 3 members 1 post etc.
	 */
	private int checkStats(int partOfString) {
		rootElement = driver.findElement(By.xpath("//div[@class='stat-block statistics']"));
		String current = rootElement.findElement(By.tagName("p")).getAttribute("outerHTML").split("•")[partOfString];
		current = current.replaceAll("\\D+", "");
		return Integer.parseInt(current);
	}

	/**
	 * close webdriver
	 */
	public static void close() {
		driver.close();
	}
}
