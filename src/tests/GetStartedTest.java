package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

  @Test
  public void testPassThroughWelcome() {
    if (Platform.getInstance().isAndroid()) {
      return;
    }
    WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

    welcomePageObject.waitForLearnMoreLink();
    welcomePageObject.clickNextButton();

    welcomePageObject.waitForNewWayToExplore();
    welcomePageObject.clickNextButton();

    welcomePageObject.waitForAddOrEditPreferredLangText();
    welcomePageObject.clickNextButton();

    welcomePageObject.waitForLearnMoreAboutDataCollectedText();
    welcomePageObject.clickGetStartedButton();
  }
}