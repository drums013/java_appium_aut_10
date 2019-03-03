package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase{


  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
    String titleBeforeLocation = articlePageObject.getArticleTitle();

    this.rotateScreenLandscape();
    String titleAfterLocation = articlePageObject.getArticleTitle();

    assertEquals("Article title have been changed after screen rotation",
            titleBeforeLocation, titleAfterLocation);

    this.rotateScreenPortrait();
    String titleAfterSecondLocation = articlePageObject.getArticleTitle();

    assertEquals("Article title have been changed after screen rotation",
            titleBeforeLocation, titleAfterSecondLocation);
  }

  @Test
  public void testCheckSearchArticleInBackground() {
    SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.waitForSearchResult("Object-oriented programming language");

    this.backgroundApp(2);
    searchPageObject.waitForSearchResult("Object-oriented programming language");
  }
}
