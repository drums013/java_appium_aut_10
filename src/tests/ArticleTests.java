package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase{

  @Test
  public void testCompareArticleTitle() {
    SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
    String article_title = articlePageObject.getArticleTitle();

    assertEquals("We see unexpected title!", "Java (programming language)", article_title);
  }

  @Test
  public void testSwipeArticle() {
    SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
    articlePageObject.waitForTitleElement();
    articlePageObject.swipeToFooter();
  }

  @Test //Exercise #6
  public void testAssertTitle() {
    ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
    String searchQuery = "Java";
    articlePageObject.findArticleInSearch(searchQuery);
    String articleTitle = articlePageObject.selectRandomArticle("No results found for " + searchQuery);
    articlePageObject.selectArticleByTitle(articleTitle);
    articlePageObject.checkIfArticleHasTitle();
  }
}
