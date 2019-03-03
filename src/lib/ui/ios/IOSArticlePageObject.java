package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {

  static {
    TITLE = "id:Java (programming language)";
    FOOTER_ELEMENT = "id:View article in browser";
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
    CLOSE_ARTICLE_BUTTON = "id:Back";
    TITLES_ELEMENTS = "id:org.wikipedia:id/page_list_item_title";
    ARTICLE_BY_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
            "//*[@text='{ARTICLE_TITLE}']";
    FOLDER_TO_SAVE_BY_NAME_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_container']" +
            "//*[@text='{NAME_OF_FOLDER}']";
    CREATE_FOLDER_TO_SAVE_BUTTON = "org.wikipedia:id/create_button";
    ARTICLE_CONTAINER = "id:org.wikipedia:id/page_list_item_container";
    TEXT_VIEW_ELEMENT = "xpath://android.widget.TextView";
    NAME_ATTRIBUTE = "name";
  }

  public IOSArticlePageObject(AppiumDriver driver) {
    super(driver);
  }
}
