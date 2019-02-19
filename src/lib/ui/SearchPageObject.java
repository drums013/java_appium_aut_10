package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import java.util.List;

public class SearchPageObject extends MainPageObject {

  private static final String
          SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
          SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
          SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
          SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                  "//*[@text='{SUBSTRING}']",
          SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                  "/*[@resource-id='org.wikipedia:id/page_list_item_container']",
          SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
          SEARCH_INPUT_PLACEHOLDER = "org.wikipedia:id/search_src_text",
          SEARCH_PROGRESS_BAR = "org.wikipedia:id/search_progress_bar",
          SEARCH_RESULTS_BY_TITLE_AND_DESCRIPTION_TPL = "//*[android.widget.TextView[@text='{ARTICLE_TITLE}']" +
                  " and android.widget.TextView[@text='{ARTICLE_DESCRIPTION}']]";

  public SearchPageObject(AppiumDriver driver) {
    super(driver);
  }

  /* TEMPLATES METHODS */
  private static String getResultSearchElement(String substring) {
    return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
  }

  private static String getResultSearchByTitleAndDescription(String title, String description) {
    return SEARCH_RESULTS_BY_TITLE_AND_DESCRIPTION_TPL
            .replace("{ARTICLE_TITLE}", title)
            .replace("{ARTICLE_DESCRIPTION}", description);
  }
  /* TEMPLATES METHODS */

  public void waitForElementByTitleAndDescription(String articleTitle, String description) {
    String searchResultXpath = getResultSearchByTitleAndDescription(articleTitle, description);
    this.waitForElementPresent(
            By.xpath(searchResultXpath),
            "Cannot find an article with the title '"
                    + articleTitle + "' and the description '" + description + "'",
            5);
  }

  public void initSearchInput() {
    this.waitForElementAndClick
            (By.xpath(SEARCH_INIT_ELEMENT),
                    "Cannot find and click search init element",
                    5);
    this.waitForElementPresent(
            By.xpath(SEARCH_INIT_ELEMENT),
            "Cannot find search input after clicking init element");
  }

  public void typeSearchLine(String searchLine) {
    this.waitForElementAndSendKeys(
            By.xpath(SEARCH_INPUT),
            searchLine,
            "Cannot find and type into search input",
            5);
    this.waitForElementNotPresent(
            By.id(SEARCH_PROGRESS_BAR),
            "Results still not loaded",
            15);
  }

  public void waitForSearchResult(String substring) {
    String searchResultXpath = getResultSearchElement(substring);
    this.waitForElementPresent(
            By.xpath(searchResultXpath),
            "Cannot find search result with substring " + substring);
  }

  public void waitForCancelButtonToAppear() {
    this.waitForElementPresent(
            By.id(SEARCH_CANCEL_BUTTON),
            "Cannot find search cancel button!",
            5);
  }

  public void waitForCancelButtonToDisappear() {
    this.waitForElementNotPresent(
            By.id(SEARCH_CANCEL_BUTTON),
            "Cancel button is still present!",
            5);
  }

  public void clickCancelSearch() {
    this.waitForElementAndClick(
            By.id(SEARCH_CANCEL_BUTTON),
            "Cannot find and click search cancel button",
            5);
  }

  public void clickByArticleWithSubstring(String substring) {
    String searchResultXpath =getResultSearchElement(substring);
    this.waitForElementAndClick(
            By.xpath(searchResultXpath),
            "Cannot find and click search result with substring " + substring,
            10);
  }

  public int getAmountOfFoundArticles() {
    this.waitForElementPresent(
            By.xpath(SEARCH_RESULT_ELEMENT),
            "Cannot find anything by the request",
            15);
    return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
  }

  public void waitForEmptyResultLabel() {

    this.waitForElementPresent(
            By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
            "Cannot find empty result element",
            15);
  }

  public void assertThereIsNoResultOfSearch() {
    this.assertElementNotPresent(
            By.xpath(SEARCH_RESULT_ELEMENT),
            "We supposed not to find any results");
  }

  public boolean checkSearchFieldPlaceholder(String placeholder) {
    return this.checkTextAttribute(
            By.id(SEARCH_INPUT_PLACEHOLDER),
            placeholder,
            "Cannot find search input",
            5);
  }

  public boolean checkIfSpecifiedNumberOfArticlesFound(int numberOfArticles) {
    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    List<String> articles = articlePageObject.articlesWithDescription();
    if (articles.size() / 2 < numberOfArticles) {
      throw new AssertionError("The number of articles in the list is less than " + numberOfArticles);
    }
    int i = 0;
    while (i < numberOfArticles) {
      String articleTitle = articles.iterator().next();
      articles.remove(articleTitle);
      String description = articles.iterator().next();
      articles.remove(description);
      waitForElementByTitleAndDescription(articleTitle, description);
      i++;
    }
    return true;
  }
}
