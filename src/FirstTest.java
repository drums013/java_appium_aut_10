import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FirstTest extends CoreTestCase {

  private MainPageObject MainPageObject;

  protected void setUp() throws Exception {

    super.setUp();

    MainPageObject = new MainPageObject(driver);
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

  @Test //Exercise #5
  public void testSaveTwoArticlesToMyList() {
    String firstQuery = "Java";
    String secondQuery = "Appium";
    String nameOfFolder = "My list";
    String firstSavedArticle = saveAnyFoundArticle(firstQuery, nameOfFolder);
    String secondSavedArticle = saveAnyFoundArticle(secondQuery, nameOfFolder);
    removeSavedArticleFromFolder(nameOfFolder, firstSavedArticle);
    List<String> articlesRemainingInList = articles();
    selectArticleByTitle(secondSavedArticle);
    String ActualTitleOfOpenArticle = getArticleTitle();
    assertTrue("Article titled '" + secondSavedArticle + "' is missing",
            articlesRemainingInList.stream().anyMatch(secondSavedArticle::equals));
    assertEquals("Instead of an article called '" + secondSavedArticle + "'" +
                    ", an article called '" + ActualTitleOfOpenArticle + "' was opened",
            secondSavedArticle, ActualTitleOfOpenArticle);
  }

  @Test //Exercise #6
  public void testAssertTitle() {
    String searchQuery = "Java";
    findArticleInSearch(searchQuery);
    String articleTitle = selectRandomArticle("No results found for " + searchQuery);
    selectArticleByTitle(articleTitle);
    checkIfArticleHasTitle();
  }

  public boolean checkSearchFieldPlaceholder(String placeholder) {
    return checkTextAttribute(
            By.id("org.wikipedia:id/search_src_text"),
            placeholder,
            "Cannot find search input",
            5);
  }

  public void initSearch() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5);
  }

  public void cancelSearch() {
    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            5);
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5);
    MainPageObject.waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            5);
  }

  public void typeSearchQuery(String searchQuery) {
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchQuery,
            "Cannot find search input",
            5);
    MainPageObject.waitForElementNotPresent(
            By.id("org.wikipedia:id/search_progress_bar"),
            "Results still not loaded",
            15);
  }

  public boolean checkTextAttribute(By by, String value, String errorMessage, long timeOutInSeconds) {
    WebElement search_field = MainPageObject.waitForElementPresent(by, errorMessage, timeOutInSeconds);
    String text = search_field.getAttribute("text");
    return text.equals(value);
  }

  public List<WebElement> listOfFoundArticles() {
    return By.id("org.wikipedia:id/page_list_item_title").findElements(driver);
  }

  public ArrayList<String> articles() {
    List<String> searchingResults = new ArrayList<>();
    List<WebElement> elements = listOfFoundArticles();
    for (WebElement element : elements) {
      String text = element.getAttribute("text");
      searchingResults.add(text);
    }
    return new ArrayList<String>(searchingResults);
  }

  public void openFolderByName(String nameOfFolder) {
    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My List",
            5);
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + nameOfFolder + "']"),
            "there is no folder named " + nameOfFolder,
            5);
  }

  public void checkIfArticleHasTitle() {
    assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "The article has no title");
  }

  public String getArticleTitle() {
    return MainPageObject.waitForElementAndGetAttrebute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15);
  }

  public void fillFolderNameInput(String nameOfFolder) {
    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5);
    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            nameOfFolder,
            "Cannot put text into article folder input",
            5);
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5);
  }

  public void removeSavedArticle(String articleTitle) {
    MainPageObject.swipeElementToLeft(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + articleTitle + "']"),
            "Cannot find saved article");
  }

  public void selectGotIt() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            5);
  }

  public void selectAddToReadingList() {
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find options to add article to reading list",
            5);
  }

  public void selectMoreOptions() {
    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5);
  }

  public void selectArticleByTitle(String label) {
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='" + label + "']"),
            "Cannot find '" + label + "' article in search",
            5);
  }

  public void initFolderCreation() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/create_button"),
            "Cannot find a button to create a new folder",
            5);
  }

  public void selectFolderToSave(String nameOfFolder) {
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + nameOfFolder + "']"),
            "Cannot find folder " + nameOfFolder,
            5);
  }

  public boolean folderAlreadyCreated(String nameOfFolder) {
    List elements = driver.findElements(
            By.xpath("//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + nameOfFolder + "']"));
    return elements.size() > 0;
  }

  public boolean isFirstFolderCreation() {
    List elements = driver.findElements(By.id("org.wikipedia:id/onboarding_button"));
    return (elements.size() > 0);
  }


  public void removeSavedArticleFromFolder(String nameOfFolder, String articleTitle) {
    openFolderByName(nameOfFolder);
    removeSavedArticle(articleTitle);
  }

  public void findArticleInSearch(String searchQuery) {
    initSearch();
    typeSearchQuery(searchQuery);
  }

  public void assertElementPresent(By by, String errorMessage) {
    int amountOfElements = MainPageObject.getAmountOfElements(by);
    if (amountOfElements == 0) {
      String defaultMessage = "An element '" + by.toString() + "' supposed to be present";
      throw new AssertionError(defaultMessage + "\n" + errorMessage);
    }
  }

  public String saveAnyFoundArticle(String searchQuery, String nameOfFolder) {
    findArticleInSearch(searchQuery);
    String articleTitle = selectRandomArticle("No results found for " + searchQuery);
    selectArticleByTitle(articleTitle);
    addArticleToReadingList(nameOfFolder);
    comeBack();
    return articleTitle;
  }

  public void comeBack() {
    driver.navigate().back();
  }

  public void addArticleToReadingList(String nameOfFolder) {
    selectMoreOptions();
    selectAddToReadingList();
    if (isFirstFolderCreation()) {
      selectGotIt();
      fillFolderNameInput(nameOfFolder);
    } else {
      selectFolder(nameOfFolder);
    }
  }

  public void selectFolder(String nameOfFolder) {
    if (folderAlreadyCreated(nameOfFolder)) {
      selectFolderToSave(nameOfFolder);
    } else {
      createNewFolderToSave(nameOfFolder);
    }
  }

  public void createNewFolderToSave(String nameOfFolder) {
    initFolderCreation();
    fillFolderNameInput(nameOfFolder);
  }

  public String selectRandomArticle(String errorMessage) {
    List articles = articles();
    if (articles.size() > 0) {
      Random random = new Random();
      return (String) articles.get(random.nextInt(articles.size()));
    } else {
      throw new AssertionError(errorMessage);
    }
  }
}