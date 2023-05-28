package base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;

import base.BaseTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport extends BaseTest {
	static ExtentReports extent;
	final static String filePath = "Extent.html";
	static Map<Integer, ExtentTest> extentTestMap = new HashMap();
	public static String screenshot = "";

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			ExtentSparkReporter html = null;
			ExtentSparkReporter latesthtml = null;

			try {
				html = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "Reports" + File.separator + "Extent Reports" + File.separator  
				+ getDateTime() + File.separator +  "ExtentReport.html");
				latesthtml = new ExtentSparkReporter("ExtentReport.html");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			html.config().setDocumentTitle("Appium Framework");
			html.config().setReportName("General Store");
			extent = new ExtentReports();
			extent.attachReporter(html);
			extent.attachReporter(latesthtml);
		}

		return extent;
	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = getReporter().createTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}


	public static synchronized void pass(String message) {
		ExtentReport.getTest().pass(message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(ExtentReport.getScreenshot()).build());
	}

	public static void addScreenshot(String message) {
		getScreenshot();
		getTest().pass(message,
				MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
	}

	public static String getScreenshot() throws WebDriverException {
		screenshot = new BaseTest().getDriver().getScreenshotAs(OutputType.BASE64);
		return screenshot;
	}

}
