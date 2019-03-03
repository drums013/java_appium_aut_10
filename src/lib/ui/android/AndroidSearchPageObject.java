package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

  static {
          SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
          SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]";
          SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
          SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                  "//*[@text='{SUBSTRING}']";
          SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']" +
                  "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
          SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
          SEARCH_INPUT_PLACEHOLDER = "id:org.wikipedia:id/search_src_text";
          SEARCH_PROGRESS_BAR = "id:org.wikipedia:id/search_progress_bar";
          SEARCH_RESULTS_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[android.widget.TextView[@text='{ARTICLE_TITLE}']" +
                  " and android.widget.TextView[@text='{ARTICLE_DESCRIPTION}']]";
  }

  public AndroidSearchPageObject(AppiumDriver driver) {
    super(driver);
  }
}
