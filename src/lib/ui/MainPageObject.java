package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

  protected AppiumDriver driver;

  public MainPageObject(AppiumDriver driver) {
    this.driver = driver;
  }

  public WebElement waitForElementPresent(String locator, String errorMessage, long timeOutInSeconds) {
    By by = this.getLocatorByString(locator);
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  public WebElement waitForElementPresent(String locator, String errorMessage) {
    return waitForElementPresent(locator, errorMessage, 5);
  }

  public WebElement waitForElementAndClick(String locator, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
    element.click();
    return element;
  }

  public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
    element.sendKeys(value);
    return element;
  }

  public boolean waitForElementNotPresent(String locator, String errorMessage, long timeOutInSeconds) {
    By by = this.getLocatorByString(locator);
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  public WebElement waitForElementAndClear(String locator, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
    element.clear();
    return element;
  }

  public void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);

    action.press(x, start_y)
            .waitAction(timeOfSwipe)
            .moveTo(x, end_y)
            .release()
            .perform();
  }

  public void swipeUpQuick() {
    swipeUp(200);
  }

  public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
    By by = this.getLocatorByString(locator);
    int alreadySwiped = 0;
    while (driver.findElements(by).size() == 0) {
      if (alreadySwiped > maxSwipes) {
        waitForElementPresent(
                locator, "Cannot find element by swiping up. \n" + errorMessage, 0);
        return;
      }
      swipeUpQuick();
      ++alreadySwiped;
    }
  }

  public void swipeElementToLeft(String locator, String errorMessage) {
    WebElement element = waitForElementPresent(locator, errorMessage, 10);
    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2;

    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(300)
            .moveTo(left_x, middle_y)
            .release()
            .perform();
  }

  public int getAmountOfElements(String locator) {
    By by = this.getLocatorByString(locator);
    List element = driver.findElements(by);
    return element.size();
  }

  public void assertElementNotPresent(String locator, String errorMessage) {
    int amountOfElements = getAmountOfElements(locator);
    if (amountOfElements > 0) {
      String defaultMessage = "An element '" + locator + "' supposed to be not present";
      throw new AssertionError(defaultMessage + " " + errorMessage);
    }
  }

  public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
    return element.getAttribute(attribute);
  }

  public boolean checkTextAttribute(String locator, String value, String errorMessage, long timeOutInSeconds) {
    WebElement search_field = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
    String text = search_field.getAttribute("text");
    return text.equals(value);
  }

  public void assertElementPresent(String locator, String errorMessage) {
    int amountOfElements = getAmountOfElements(locator);
    if (amountOfElements == 0) {
      String defaultMessage = "An element '" + locator + "' supposed to be present";
      throw new AssertionError(defaultMessage + "\n" + errorMessage);
    }
  }

  private By getLocatorByString(String locatorWithType) {
    String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
    String byType = explodedLocator[0];
    String locator = explodedLocator[1];

    if (byType.equals("xpath")) {
      return By.xpath(locator);
    } else if (byType.equals("id")) {
      return By.id(locator);
    } else {
      throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
    }
  }
}