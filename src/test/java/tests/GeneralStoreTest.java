package tests;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import base.BaseTest;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import pages.CartPage;
import pages.GoogleSearchPage;
import pages.HomePage;
import pages.ProductPage;
import testutils.Assertion;
import testutils.ExcelDataProvider;
import testutils.HelperUtils;
import testutils.Wait;

public class GeneralStoreTest extends BaseTest {

	@Test(dataProvider = "VerifyProductPurchaseDataProvider", 
			dataProviderClass = ExcelDataProvider.class, 
			enabled = true)

	public void VerifyProductPurchase(String username, String country, String gender, String product1, String product2,
			String searchKey) {
		Wait wait = new Wait();
		double cartValue = 0;
		int itemCount = 0;

		WebDriver driver = getDriver();
		
		//Adding data to General Store form
		HomePage homePage = new HomePage();
		wait.forPageToLoad(homePage);

		homePage.selectCountry(country)
				.enterUsername(username)
				.selectGender(gender)
				.clickLetsShopButton();

		//Selecting Products from Products screen and validating cart
		ProductPage productPage = new ProductPage();
		wait.forPageToLoad(productPage);

		double productPrice = productPage.addProductToCart(product1);
		cartValue = cartValue + productPrice;
		itemCount++;

		productPrice = productPage.addProductToCart(product2);
		cartValue = cartValue + productPrice;
		itemCount++;

		productPage.verifyCartItemCount(itemCount);
		productPage.goToCart();

		//Verifying Products and total amount on Cart Screen
		CartPage cartPage = new CartPage();

		wait.forPageToLoad(cartPage);

		Assertion.assertTrue(cartPage.VerifyProductInCart(product1), "Product 1 found");
		Assertion.assertTrue(cartPage.VerifyProductInCart(product2), "Product 2 found");

		cartPage.verifyTotalAmount(cartValue)
			.completePurchase();

		//Switch to WebView and perform search	

		wait.forContextAnSwitch("WEBVIEW_com.androidsample.generalstore");
		//getDriver().context("WEBVIEW_com.androidsample.generalstore");

		GoogleSearchPage googleSearchPage = new GoogleSearchPage();
		wait.forPageToLoad(googleSearchPage);
		googleSearchPage.doGoogleSearch(searchKey);

		HelperUtils.DeviceBackKey();
		
		//Switch to Native Context and verify General Store screen loaded 
		System.out.println(new ArrayList<String>(getDriver().getContextHandles()).get(0));
		getDriver().context(new ArrayList<String>(getDriver().getContextHandles()).get(0));
		wait.forPageToLoad(homePage);
		Assertion.assertTrue(homePage.isPageLoad(), "General Store screen loaded successfully");
	}
	
	@Test(enabled = true)
	public void VerifyProductPurchaseFailing() {
		Wait wait = new Wait();
		double cartValue = 0;
		int itemCount = 0;
		
		loadProperties();

		WebDriver driver = getDriver();
		HomePage homePage = new HomePage();
		wait.forPageToLoad(homePage);

		homePage.selectCountry(getProperty("country"))

				.enterUsername(getProperty("username"))

				.selectGender(getProperty("gender"))

				.clickLetsShopButton();

		// Wait.waitForInVisibility(homePage.letsShopBtn);
		ProductPage productPage = new ProductPage();
		wait.forPageToLoad(productPage);

		double productPrice = productPage.addProductToCart(getProperty("product1"));
		cartValue = cartValue + productPrice;
		itemCount++;

		productPrice = productPage.addProductToCart(getProperty("product2"));
		cartValue = cartValue + productPrice;
		itemCount++;

		productPage.verifyCartItemCount(3);  //Intentionally failing it here
		productPage.goToCart();
	}
}
