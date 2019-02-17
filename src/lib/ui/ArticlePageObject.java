package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArticlePageObject extends MainPageObject {

  private static final String
          TITLE = "org.wikipedia:id/view_page_title_text",
          FOOTER_ELEMENT = "//*[@text='View page in browser']",
          OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
          OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
          ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
          MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
          MY_LIST_OK_BUTTON = "//*[@text='OK']",
          CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
          TITLES_ELEMENTS = "org.wikipedia:id/page_list_item_title",
          ARTICLE_BY_TITLE_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                  "//*[@text='{ARTICLE_TITLE}']",
          FOLDER_TO_SAVE_BY_NAME_TPL = "//*[@resource-id='org.wikipedia:id/item_container']" +
                  "//*[@text='{NAME_OF_FOLDER}']",
          CREATE_FOLDER_TO_SAVE_BUTTON = "org.wikipedia:id/create_button";

  /* TEMPLATES METHODS */
  private static String getArticleXpathByName(String articleTitle) {
    return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", articleTitle);
  }

  private static String getFolderXpathByName(String nameOfFolder) {
    return FOLDER_TO_SAVE_BY_NAME_TPL.replace("{NAME_OF_FOLDER}", nameOfFolder);
  }
  /* TEMPLATES METHODS */

  public ArticlePageObject(AppiumDriver driver) {
    super(driver);
  }

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(
            By.id(TITLE),
            "Cannot find article title on page!",
            15);
  }

  public String getArticleTitle() {
    WebElement titleElement = waitForTitleElement();
    return titleElement.getAttribute("text");
  }

  public void swipeToFooter() {
    this.swipeUpToFindElement(
            By.xpath(FOOTER_ELEMENT),
            "Cannot the end of article",
            20);
  }

  public void addArticleToMyList(String nameOfFolder) {
    selectMoreOptions();
    selectAddToReadingList();
    selectGotIt();
    fillFolderNameInput(nameOfFolder);
  }

  public void closeArticle() {
    this.waitForElementAndClick(
            By.xpath(CLOSE_ARTICLE_BUTTON),
            "Cannot close article, cannot find X link",
            5);
  }

  public List<WebElement> listOfFoundArticles() {
    return By.id(TITLES_ELEMENTS).findElements(driver);
  }

  public void initFolderCreation() {
    this.waitForElementAndClick(
            By.id(CREATE_FOLDER_TO_SAVE_BUTTON),
            "Cannot find a button to create a new folder",
            5);
  }

  public void selectMoreOptions() {
    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "Cannot find button to open article options",
            5);
  }

  public void selectAddToReadingList() {
    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find options to add article to reading list",
            5);
  }

  public void selectGotIt() {
    this.waitForElementAndClick(
            By.id(ADD_TO_MY_LIST_OVERLAY),
            "Cannot find 'Got it' tip overlay",
            5);
  }

  public void fillFolderNameInput(String nameOfFolder) {
    this.waitForElementAndClear(
            By.id(MY_LIST_NAME_INPUT),
            "Cannot find input to set name of articles folder",
            5);
    this.waitForElementAndSendKeys(
            By.id(MY_LIST_NAME_INPUT),
            nameOfFolder,
            "Cannot put text into article folder input",
            5);
    this.waitForElementAndClick(
            By.xpath(MY_LIST_OK_BUTTON),
            "Cannot press OK button",
            5);
  }

  public void createNewFolderToSave(String nameOfFolder) {
    initFolderCreation();
    fillFolderNameInput(nameOfFolder);
  }

  public void checkIfArticleHasTitle() {
    assertElementPresent(By.id(TITLE), "The article has no title");
  }

  public void findArticleInSearch(String searchQuery) {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine(searchQuery);
  }

  public void selectFolderToSave(String nameOfFolder) {
    String folderNameXpath = getFolderXpathByName(nameOfFolder);
    this.waitForElementAndClick(
            By.xpath(folderNameXpath),
            "Cannot find folder " + nameOfFolder,
            5);
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

  public String saveAnyFoundArticle(String searchQuery, String nameOfFolder) {
    findArticleInSearch(searchQuery);
    String articleTitle = selectRandomArticle("No results found for " + searchQuery);
    selectArticleByTitle(articleTitle);
    addArticleToReadingList(nameOfFolder);
    NavigationUI navigationUI = new NavigationUI(driver);
    navigationUI.comeBack();
    return articleTitle;
  }

  public void selectArticleByTitle(String articleTitle) {
    String articleTitleXpath = getArticleXpathByName(articleTitle);
    this.waitForElementAndClick(
            By.xpath(articleTitleXpath),
            "Cannot find '" + articleTitle + "' article in search",
            5);
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

  public boolean isFirstFolderCreation() {
    List elements = driver.findElements(By.id("org.wikipedia:id/onboarding_button"));
    return (elements.size() > 0);
  }

  public void selectFolder(String nameOfFolder) {
    if (folderAlreadyCreated(nameOfFolder)) {
      selectFolderToSave(nameOfFolder);
    } else {
      createNewFolderToSave(nameOfFolder);
    }
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

  public boolean folderAlreadyCreated(String nameOfFolder) {
    String folderNameXpath = getFolderXpathByName(nameOfFolder);
    List elements = driver.findElements(
            By.xpath(folderNameXpath));
    return elements.size() > 0;
  }
}
