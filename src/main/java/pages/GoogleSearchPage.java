package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseTest;
import base.PageBase;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class GoogleSearchPage extends PageBase {

	@FindBy(xpath = "//node()[(name()='input' and @type='text') or name()='textarea'][@title='Search' or contains(@aria-label,'Search')]")
	public WebElement searchTxt;

	@FindBy(xpath = "//img[@alt='Google']")
	public WebElement logoImage;

	@FindBy(xpath = "//button[@aria-label='Google Search']")
	public WebElement searchBtn;

	public GoogleSearchPage() {
		//PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	@Override
	public Boolean isPageLoad() {
		return logoImage.isDisplayed() && searchTxt.isDisplayed();
	}

	public void doGoogleSearch(String searchKey) {
		// JavaScript alternative
		// ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click()",googleSearchPage.searchTxt);
		// ((JavascriptExecutor)getDriver()).executeScript("arguments[0].setAttribute('value',
		// 'General Store')",googleSearchPage.searchTxt);

		searchTxt.sendKeys("General Store");
		((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));
		searchTxt.sendKeys(Keys.ENTER);
	}
}
