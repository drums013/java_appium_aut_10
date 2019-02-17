package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase{


  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
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
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.waitForSearchResult("Object-oriented programming language");

    this.backgroundApp(2);
    searchPageObject.waitForSearchResult("Object-oriented programming language");
  }
}
