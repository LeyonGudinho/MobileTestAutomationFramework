package testutils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;
import base.ExtentReport;
import base.PageBase;
import io.appium.java_client.MobileElement;

public class Wait extends BaseTest {

	public WebDriver driver = getDriver();

	public void forPageToLoad(PageBase page) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until((e) -> {
			try {
				System.out.println("waiting for " + page.getClass().getSimpleName());
				return page.isPageLoad();
			} catch (Exception e1) {
				return null;
			}
			// return null;
		});
		ExtentReport.addScreenshot(page.getClass().getSimpleName() + " objects loaded");
	}

	public void forContextAnSwitch(String contextName) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		wait.until((e) -> {
			try {
				if (getDriver().getContextHandles().size() > 1) {
					getDriver().context(contextName);
					return true;
				} else
					return false;
			} catch (Exception e1) {
				return null;
			}
			// return null;
		});
	}

	public MobileElement forElementToBeVisible(By by)
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		return wait.until((e) -> {
			try {
				  return (MobileElement) getDriver().findElement(by);
			} catch (Exception e1) {
				return null;
			}
		});
	}
	
	public void waitForInVisibility(MobileElement e) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions.invisibilityOf(e));
	}
}
