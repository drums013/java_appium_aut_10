package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

  private final static String nameOfFolder = "Learning programming";

  @Test
  public void testSaveFirstArticleToMyList() {
    SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
    articlePageObject.waitForTitleElement();
    String articleTitle = articlePageObject.getArticleTitle();
    if (Platform.getInstance().isAndroid()) {
      articlePageObject.addArticleToMyList(nameOfFolder);
    } else {
      articlePageObject.addArticlesToMySaved();
    }

    articlePageObject.closeArticle();

    NavigationUI navigationUI = NavigationUIFactory.get(driver);
    navigationUI.clickMyLists();

    MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
    if (Platform.getInstance().isAndroid()) {
      myListsPageObject.openFolderByName(nameOfFolder);
    }
    myListsPageObject.swipeByArticleToDelete(articleTitle);
  }

  @Test //Exercise #5
  public void testSaveTwoArticlesToMyList() {
    String firstQuery = "Java";
    String secondQuery = "Appium";
    String nameOfFolder = "My list";

    ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
    String firstSavedArticle = articlePageObject.saveAnyFoundArticle(firstQuery, nameOfFolder);
    String secondSavedArticle = articlePageObject.saveAnyFoundArticle(secondQuery, nameOfFolder);

    NavigationUI navigationUI = NavigationUIFactory.get(driver);
    navigationUI.clickMyLists();

    MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
    myListsPageObject.removeSavedArticleFromFolder(nameOfFolder, firstSavedArticle);
    List<String> articlesRemainingInList = articlePageObject.articles();
    articlePageObject.selectArticleByTitle(secondSavedArticle);
    String ActualTitleOfOpenArticle = articlePageObject.getArticleTitle();

    assertTrue("Article titled '" + secondSavedArticle + "' is missing",
            articlesRemainingInList.stream().anyMatch(secondSavedArticle::equals));
    assertEquals("Instead of an article called '" + secondSavedArticle + "'" +
                    ", an article called '" + ActualTitleOfOpenArticle + "' was opened",
            secondSavedArticle, ActualTitleOfOpenArticle);
  }
}
