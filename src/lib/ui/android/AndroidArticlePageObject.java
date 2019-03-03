package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {

  static {
    TITLE = "id:org.wikipedia:id/view_page_title_text";
    FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
    OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
    ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
    MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
    MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
    CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
    TITLES_ELEMENTS = "id:org.wikipedia:id/page_list_item_title";
    ARTICLE_BY_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='{ARTICLE_TITLE}']";
    FOLDER_TO_SAVE_BY_NAME_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_container']" +
                    "//*[@text='{NAME_OF_FOLDER}']";
    CREATE_FOLDER_TO_SAVE_BUTTON = "org.wikipedia:id/create_button";
    ARTICLE_CONTAINER = "id:org.wikipedia:id/page_list_item_container";
    TEXT_VIEW_ELEMENT = "xpath://android.widget.TextView";
    TEXT_ATTRIBUTE = "text";
  }

  public AndroidArticlePageObject(AppiumDriver driver) {
    super(driver);
  }
}
