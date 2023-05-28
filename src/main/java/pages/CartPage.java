package pages;

import org.openqa.selenium.support.PageFactory;

import base.BaseTest;
import base.PageBase;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import testutils.Assertion;

public class CartPage extends PageBase{


	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	@iOSXCUITFindBy(id = "")
	public MobileElement completePurchaseBtn;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Send me e-mails on discounts related to selected products in future\");")
	@iOSXCUITFindBy(id = "")
	public MobileElement sendEmailsConsentBtn;
	
	@AndroidFindBy(id  = "com.androidsample.generalstore:id/totalAmountLbl")
	@iOSXCUITFindBy(id = "")
	public MobileElement purchaseAmountTxt;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.androidsample.generalstore:id/toolbar_title' and @text='Cart']")
	@iOSXCUITFindBy(id = "")
	public MobileElement cartTitleBar;
	
	
	
	
	public CartPage()
	{
		//PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}
	
	@Override
	public Boolean isPageLoad() {
		return cartTitleBar.isDisplayed() &&
				completePurchaseBtn.isDisplayed() &&
				sendEmailsConsentBtn.isDisplayed();
	}
	
	public Boolean VerifyProductInCart(String productName)
	{
		MobileElement cartProduct1 = (MobileElement) getDriver()
				.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + productName + "\");"));
		return(cartProduct1.isDisplayed());
	}
	
	public CartPage verifyTotalAmount(double expectedCartValue)
	{
		Assertion.assertEquals(purchaseAmountTxt.getText(), String.format("$ %.2f" , expectedCartValue), "Verify cart - Total Amount");
		return this;
	}
	
	public void completePurchase()
	{
		sendEmailsConsentBtn.click();
		completePurchaseBtn.click();
	}

}
