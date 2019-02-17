package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

import java.util.List;

public class SearchTests extends CoreTestCase{
  @Test
  public void testSearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.waitForSearchResult("Object-oriented programming language");
  }

  @Test
  public void testCancelSearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.waitForCancelButtonToAppear();
    searchPageObject.clickCancelSearch();
    searchPageObject.waitForCancelButtonToDisappear();
  }

  @Test
  public void testAmountOfNotEmptySearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String searchLine = "Linkin Park Diskography";
    searchPageObject.typeSearchLine(searchLine);
    int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles();
    assertTrue("We found too few results", amountOfSearchResults > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String searchLine = "zscasczxc";
    searchPageObject.typeSearchLine(searchLine);
    searchPageObject.waitForEmptyResultLabel();
    searchPageObject.assertThereIsNoResultOfSearch();
  }

  @Test //Exercise #2
  public void testPlaceholderSearchField() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    boolean placeholder = searchPageObject.checkSearchFieldPlaceholder("Search…");
    assertTrue("We see unexpected placeholder", placeholder);
  }

  @Test //Exercise #3
  public void testSearchQueryAndCancelResults() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String searchQuery = "Java";
    searchPageObject.typeSearchLine(searchQuery);
    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    int numberOfSearchResultsBefore = articlePageObject.listOfFoundArticles().size();
    searchPageObject.clickCancelSearch();
    int numberOfSearchResultsAfter = articlePageObject.listOfFoundArticles().size();
    assertTrue("No results found for " + searchQuery, numberOfSearchResultsBefore > 0);
    assertTrue("Query results are still displayed", numberOfSearchResultsAfter == 0);
  }

  @Test //Exercise #4
  public void testWordPresenceInSearchResults() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String searchQuery = "java";
    searchPageObject.typeSearchLine(searchQuery);
    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    List<String> articles = articlePageObject.articles();
    assertTrue("No results found for " + searchQuery, articles.size() > 0);
    assertTrue("Some articles found do not contain the word '" + searchQuery + "'",
            articles.stream().allMatch((s) -> s.toLowerCase().contains(searchQuery)));
  }
}
