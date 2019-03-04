package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

  protected static String
          FOLDER_BY_NAME_TPL,
          ARTICLE_BY_TITLE_TPL;

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
            folderNameXpath,
            "Cannot find folder by name " + nameOfFolder,
            5);
  }

  public void swipeByArticleToDelete(String articleTitle) {
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementPresent(articleXpath,
            "Articles titled " + articleTitle + " are not listed.", 5);
    this.swipeElementToLeft(
            articleXpath,
            "Cannot find saved article");
    if (Platform.getInstance().isIOS()) {
      this.clickElementToTheRightUpperCorner(articleXpath, "Cannot find saved article");
    }
    this.waitForArticleToDisappearByTitle(articleTitle);
  }

  public void waitForArticleToDisappearByTitle(String articleTitle) {
    waitForArticleToAppearByTitle(articleTitle);
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementNotPresent(
            articleXpath,
            "Saved article still present with title " + articleTitle,
            15);
  }

  public void waitForArticleToAppearByTitle(String articleTitle) {
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementPresent(
            articleXpath,
            "Cannot find saved article " + articleTitle,
            15);
  }

  public void removeSavedArticleFromFolder(String nameOfFolder, String articleTitle) {
    openFolderByName(nameOfFolder);
    swipeByArticleToDelete(articleTitle);
  }
}