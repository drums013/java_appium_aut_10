package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

  @Test
  public void testSaveFirstArticleToMyList() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    articlePageObject.waitForTitleElement();
    String nameOfFolder = "Learning programming";
    String articleTitle = articlePageObject.getArticleTitle();
    articlePageObject.addArticleToMyList(nameOfFolder);
    articlePageObject.closeArticle();

    NavigationUI navigationUI = new NavigationUI(driver);
    navigationUI.clickMyLists();

    MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
    myListsPageObject.openFolderByName(nameOfFolder);
    myListsPageObject.swipeByArticleToDelete(articleTitle);
  }

  @Test //Exercise #5
  public void testSaveTwoArticlesToMyList() {
    String firstQuery = "Java";
    String secondQuery = "Appium";
    String nameOfFolder = "My list";

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    String firstSavedArticle = articlePageObject.saveAnyFoundArticle(firstQuery, nameOfFolder);
    String secondSavedArticle = articlePageObject.saveAnyFoundArticle(secondQuery, nameOfFolder);

    NavigationUI navigationUI = new NavigationUI(driver);
    navigationUI.clickMyLists();

    MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
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
