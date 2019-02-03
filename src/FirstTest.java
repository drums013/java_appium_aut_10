import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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
    System.out.println(articles);
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
}
