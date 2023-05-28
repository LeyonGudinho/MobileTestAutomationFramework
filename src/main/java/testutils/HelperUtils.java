package testutils;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import base.ExtentReport;
import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;

public class HelperUtils extends BaseTest {
	public void waitForVisibility(MobileElement e) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void waitForVisibility(WebElement e) {
		Wait<WebDriver> wait = null;
		try {
			wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void clear(MobileElement e) {
		waitForVisibility(e);
		e.clear();
	}

	public void click(MobileElement e) {
		waitForVisibility(e);
		e.click();
	}

	public void click(MobileElement e, String msg) {
		waitForVisibility(e);
		ExtentReport.getTest().log(Status.INFO, msg);
		e.click();
	}

	public void sendKeys(MobileElement e, String txt) {
		waitForVisibility(e);
		e.sendKeys(txt);
	}

	public void sendKeys(MobileElement e, String txt, String msg) {
		waitForVisibility(e);
		ExtentReport.getTest().log(Status.INFO, msg);
		e.sendKeys(txt);
	}

	public String getAttribute(MobileElement e, String attribute) {
		waitForVisibility(e);
		return e.getAttribute(attribute);
	}

	public String getText(MobileElement e, String msg) {
		String txt = null;
		switch (getPlatform()) {
		case "Android":
			txt = getAttribute(e, "text");
			break;
		case "iOS":
			txt = getAttribute(e, "label");
			break;
		}
		// utils.log().info(msg + txt);
		ExtentReport.getTest().log(Status.INFO, msg + txt);
		return txt;
	}

	public void closeApp() {
		((InteractsWithApps) getDriver()).closeApp();
	}

	public void launchApp() {
		((InteractsWithApps) getDriver()).launchApp();
	}

	public static void DeviceBackKey() {
		BaseTest base = new BaseTest();
		if (base.getDriver() instanceof AndroidDriver) {
			((AndroidDriver) base.getDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
			((AndroidDriver) base.getDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
		} else if (base.getDriver() instanceof IOSDriver)
			((IOSDriver) base.getDriver()).findElementByAccessibilityId("Back");
		else
			base.getDriver().navigate().back();
	}
}
