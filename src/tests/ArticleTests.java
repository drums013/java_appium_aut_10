package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase{

  @Test
  public void testCompareArticleTitle() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    String article_title = articlePageObject.getArticleTitle();

    assertEquals("We see unexpected title!", "Java (programming language)", article_title);
  }

  @Test
  public void testSwipeArticle() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Appium");
    searchPageObject.clickByArticleWithSubstring("Appium");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    articlePageObject.waitForTitleElement();
    articlePageObject.swipeToFooter();
  }

  @Test //Exercise #6
  public void testAssertTitle() {
    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    String searchQuery = "Java";
    articlePageObject.findArticleInSearch(searchQuery);
    String articleTitle = articlePageObject.selectRandomArticle("No results found for " + searchQuery);
    articlePageObject.selectArticleByTitle(articleTitle);
    articlePageObject.checkIfArticleHasTitle();
  }
}
