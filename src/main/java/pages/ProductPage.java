package pages;

import org.openqa.selenium.By;

import base.PageBase;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import testutils.Assertion;
import testutils.Wait;

public class ProductPage extends PageBase {

	@AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
	@iOSXCUITFindBy(id = "")
	public MobileElement productsTitleBar;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
	@iOSXCUITFindBy(id = "")
	public MobileElement cartButton;

	@AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView/android.widget.RelativeLayout[1]/android.widget.LinearLayout/android.widget.ImageView")
	@iOSXCUITFindBy(id = "")
	public MobileElement productImage;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/counterText")
	@iOSXCUITFindBy(id = "")
	public MobileElement cartCounter;

	public ProductPage() {
		// PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	@Override
	public Boolean isPageLoad() {
		return productsTitleBar.isDisplayed() && cartButton.isDisplayed() && productImage.isDisplayed();
	}

	public double addProductToCart(String productName) {
		new Wait().forElementToBeVisible(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + productName + "\"));"));
		
		getDriver().findElement(By.xpath("//android.widget.LinearLayout[*[@text='" + productName
				+ "']]/android.widget.LinearLayout/android.widget.TextView[@text='ADD TO CART']")).click();

		return Double.parseDouble(getDriver()
				.findElement(By.xpath("//android.widget.LinearLayout[*[@text='" + productName
						+ "']]/android.widget.LinearLayout/android.widget.TextView"))
				.getAttribute("text").replaceAll("[^0-9.]", ""));
	}

	public void goToCart() {
		cartButton.click();
	}

	public Boolean verifyCartItemCount(int expectedItemCount) {
		Assertion.assertEquals(cartCounter.getText(), String.valueOf(expectedItemCount), "Cart product count matched");
		return true;
	}
}
