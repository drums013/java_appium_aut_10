import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstTest {

  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "8.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability
            ("app", "C://Program Files/Git/JavaAppiumAutomation/apks/org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void firstTest() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);
    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by Java",
            15);
  }

  @Test
  public void testCancelSearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);
    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            5);
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5);
    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            5);
  }

  @Test
  public void testCompareArticleTitle() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Search Wikipedia' input",
            5);
    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15);
    String article_title = title_element.getAttribute("text");
    assertEquals("We see unexpected title!", "Java (programming language)", article_title);
  }

  @Test
  public void testSwipeArticle() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Appium",
            "Cannot find search input",
            5);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']" +
                    "[@text='Appium']"),
            "Cannot find 'Appium' article in search",
            5);
    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15);
    swipeUpToFindElement(
            By.xpath("//*[@text='View page in browser']"),
            "Cannot find the end of the article",
            20);
  }

  @Test
  public void saveFirstArticleToMyList() {
    String nameOfFolder = "Learning programming";
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Java' article in search",
            5);
    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15);
    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5);
    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find options to add article to reading list",
            5);
    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            5);
    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5);
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            nameOfFolder,
            "Cannot put text into article folder input",
            5);
    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5);
    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5);
    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My List",
            5);
    waitForElementAndClick(
            By.xpath("//*[@text='" + nameOfFolder + "']"),
            "Cannot find created folder",
            5);
    swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
    "Cannot find saved article");
    waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
    "Cannot delete saved article",
            5);
  }

  @Test
  public void testAmountOfNotEmptySearch() {
    String searchLine = "Linkin Park Diskography";
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchLine,
            "Cannot find 'Search Wikipedia' input",
            5);
    String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    waitForElementPresent(
            By.xpath(searchResultLocator),
            "Cannot find anything by the request " + searchLine,
            15);
    int amountOfSearchResults = getAmountOfElements(
            By.xpath(searchResultLocator));
    assertTrue("We found too few results", amountOfSearchResults > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    String searchLine = "zscasczxc";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchLine,
            "Cannot find 'Search Wikipedia' input",
            5);
    String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String emptyResultLabel = "//*[@text='No results found']";
    waitForElementPresent(
            By.xpath(emptyResultLabel),
            "Cannot find empty result label by the request " + searchLine,
            15);
    assertElementNotPresent(
            By.xpath(searchResultLocator),
            "We've found some results by request " + searchLine);
  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    String searchLine = "Java";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchLine,
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + searchLine,
            15);
    String titleBeforeLocation = waitForElementAndGetAttrebute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15);
    driver.rotate(ScreenOrientation.LANDSCAPE);
    String titleAfterLocation = waitForElementAndGetAttrebute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15);
    assertEquals("Article title have been changed after screen rotation",
            titleBeforeLocation, titleAfterLocation);
    driver.rotate(ScreenOrientation.PORTRAIT);
    String titleAfterSecondLocation = waitForElementAndGetAttrebute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15);
    assertEquals("Article title have been changed after screen rotation",
            titleBeforeLocation, titleAfterSecondLocation);
  }

  @Test
  public void testCheckSearchArticleInBackground() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find 'Search Wikipedia' input",
            5);
    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Java' article in search",
            5);
    driver.runAppInBackground(2);
    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find article after returning from background",
            5);
  }

  @Test //Exercise #2
  public void testPlaceholderSearchField() {
    initSearch();
    boolean placeholder = checkSearchFieldPlaceholder("Search…");
    assertTrue("We see unexpected placeholder", placeholder);
  }

  @Test //Exercise #3
  public void testSearchQueryAndCancelResults() {
    String searchQuery = "Java";
    initSearch();
    typeSearchQuery(searchQuery);
    int numberOfSearchResultsBefore = listOfFoundArticles().size();
    cancelSearch();
    int numberOfSearchResultsAfter = listOfFoundArticles().size();
    assertTrue("No results found for " + searchQuery, numberOfSearchResultsBefore > 0);
    assertTrue("Query results are still displayed", numberOfSearchResultsAfter == 0);
  }

  @Test //Exercise #4
  public void testWordPresenceInSearchResults() {
    String searchQuery = "java";
    initSearch();
    typeSearchQuery(searchQuery);
    List<String> articles = articles();
    assertTrue("No results found for " + searchQuery, articles.size() > 0);
    assertTrue("Some articles found do not contain the word '" + searchQuery + "'",
            articles.stream().allMatch((s) -> s.toLowerCase().contains(searchQuery)));
  }

  private WebElement waitForElementPresent(By by, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  private WebElement waitForElementPresent(By by, String errorMessage) {
    return waitForElementPresent(by, errorMessage, 5);
  }

  private WebElement waitForElementAndClick(By by, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private boolean waitForElementNotPresent(By by, String errorMessage, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(errorMessage + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  private WebElement waitForElementAndClear(By by, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    element.clear();
    return element;
  }

  private boolean checkSearchFieldPlaceholder(String placeholder) {
    return checkTextAttribute(
            By.id("org.wikipedia:id/search_src_text"),
            placeholder,
            "Cannot find search input",
            5);
  }

  private void initSearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5);
  }

  private void cancelSearch() {
    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            5);
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5);
    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            5);
  }

  private void typeSearchQuery(String searchQuery) {
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchQuery,
            "Cannot find search input",
            5);
    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_progress_bar"),
            "Results still not loaded",
            15);
  }

  private boolean checkTextAttribute(By by, String value, String errorMessage, long timeOutInSeconds) {
    WebElement search_field = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    String text = search_field.getAttribute("text");
    return text.equals(value);
  }

  private List<WebElement> listOfFoundArticles() {
    return By.id("org.wikipedia:id/page_list_item_title").findElements(driver);
  }

  private ArrayList<String> articles() {
    List<String> searchingResults = new ArrayList<>();
    List<WebElement> elements = listOfFoundArticles();
    for (WebElement element : elements) {
      String text = element.getAttribute("text");
      searchingResults.add(text);
    }
    return new ArrayList<String>(searchingResults);
  }

  protected void swipeUp(int timeOfSwipe) {
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

  protected void swipeUpQuick() {
    swipeUp(200);
  }

  protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
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

  protected void swipeElementToLeft(By by, String errorMessage) {
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

  private int getAmountOfElements(By by) {
    List element = driver.findElements(by);
    return element.size();
  }

  private void assertElementNotPresent(By by, String errorMessage) {
    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements > 0) {
      String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(defaultMessage + " " + errorMessage);
    }
  }

  private String waitForElementAndGetAttrebute(By by, String attribute, String errorMessage, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
    return element.getAttribute(attribute);
  }
}
