package pages;

import base.PageBase;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import testutils.Wait;

public class HomePage extends PageBase {

	@AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
	@iOSXCUITFindBy(id = "")
	public MobileElement countrySpinner;

	@AndroidFindBy(xpath = "//*[@text='India']")
	@iOSXCUITFindBy(id = "")
	public MobileElement countryDropdownValue;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	@iOSXCUITFindBy(id = "")
	public MobileElement nameTextbox;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	@iOSXCUITFindBy(id = "")
	public MobileElement letsShopBtn;


	public HomePage() {
		//PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	@Override
	public Boolean isPageLoad() {
		return countrySpinner.isDisplayed() && letsShopBtn.isDisplayed();
	}

	public HomePage selectCountry(String country) {
		countrySpinner.click();
		MobileElement countryItem = new Wait().forElementToBeVisible(MobileBy.AndroidUIAutomator(
							"new UiScrollable(new UiSelector()).scrollIntoView(" + "new UiSelector().text(\"" + country + "\"));"));
		countryItem.click();
		return this;
	}

	public HomePage enterUsername(String username) {
		nameTextbox.sendKeys(username);
		return this;
	}

	public HomePage selectGender(String gender) {
		MobileElement genderCheckBox = (MobileElement) getDriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + gender + "\");"));
		genderCheckBox.click();
		return this;
	}

	public void clickLetsShopButton() {
		letsShopBtn.click();
	}
}
