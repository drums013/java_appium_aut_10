package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

  public static final String
          FOLDER_BY_NAME_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']",
          ARTICLE_BY_TITLE_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']";

  /* TEMPLATES METHODS */
  private static String getFolderXpathByName(String nameOfFolder) {
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
  }

  private static String getSavedArticleXpathByTitle(String articleTitle) {
    return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
  }
  /* TEMPLATES METHODS */

  public MyListsPageObject (AppiumDriver driver) {
    super(driver);
  }

  public void openFolderByName(String nameOfFolder) {
    String folderNameXpath = getFolderXpathByName(nameOfFolder);
    this.waitForElementAndClick(
            By.xpath(folderNameXpath),
            "Cannot find folder by name " + nameOfFolder,
            5);
  }

  public void swipeByArticleToDelete(String articleTitle) {
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementPresent(By.xpath(articleXpath),
            "Articles titled " + articleTitle + " are not listed.", 5);
    this.swipeElementToLeft(
            By.xpath(articleXpath),
            "Cannot find saved article");
    this.waitForArticleToDisappearByTitle(articleTitle);
  }

  public void waitForArticleToDisappearByTitle(String articleTitle) {
    waitForArticleToAppearByTitle(articleTitle);
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementNotPresent(
            By.xpath(articleXpath),
            "Saved article still present with title " + articleTitle,
            15);
  }

  public void waitForArticleToAppearByTitle(String articleTitle) {
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementPresent(
            By.xpath(articleXpath),
            "Cannot find saved article " + articleTitle,
            15);
  }

  public void removeSavedArticleFromFolder(String nameOfFolder, String articleTitle) {
    openFolderByName(nameOfFolder);
    swipeByArticleToDelete(articleTitle);
  }
}
