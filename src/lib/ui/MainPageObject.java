package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

  protected AppiumDriver driver;

  public MainPageObject(AppiumDriver driver) {
    this.driver = driver;
  }

  public WebElement waitForElementPresent(By by, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  public WebElement waitForElementPresent(By by, String errorMessage) {
    return waitForElementPresent(by, errorMessage, 5);
  }

  public WebElement waitForElementAndClick(By by, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    element.click();
    return element;
  }

  public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    element.sendKeys(value);
    return element;
  }

  public boolean waitForElementNotPresent(By by, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  public WebElement waitForElementAndClear(By by, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
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

  public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
    int alreadySwiped = 0;
    while (driver.findElements(by).size() == 0) {
      if (alreadySwiped > maxSwipes) {
        waitForElementPresent(
                by, "Cannot find element by swiping up. \n" + errorMessage, 0);
        return;
      }
      swipeUpQuick();
      ++alreadySwiped;
    }
  }

  public void swipeElementToLeft(By by, String errorMessage) {
    WebElement element = waitForElementPresent(by, errorMessage, 10);
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

  public int getAmountOfElements(By by) {
    List element = driver.findElements(by);
    return element.size();
  }

  public void assertElementNotPresent(By by, String errorMessage) {
    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements > 0) {
      String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(defaultMessage + " " + errorMessage);
    }
  }

  public String waitForElementAndGetAttrebute(By by, String attribute, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    return element.getAttribute(attribute);
  }
}
