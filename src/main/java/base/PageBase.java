package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class PageBase extends BaseTest {

	protected WebDriver driver;
	
	public PageBase() {
		driver = super.getDriver();
	}

	public abstract Boolean isPageLoad();
}
