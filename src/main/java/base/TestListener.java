package base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

	public void onTestFailure(ITestResult result) {
		BaseTest base = new BaseTest();
		File file = base.getDriver().getScreenshotAs(OutputType.FILE);

		byte[] encoded = null;
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Map<String, String> params = new HashMap<String, String>();
		params = result.getTestContext().getCurrentXmlTest().getAllParameters();

		String imagePath = "Screenshots" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
				+ File.separator + base.getDateTime() + File.separator
				+ result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

		String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

		try {
			FileUtils.copyFile(file, new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExtentReport.getTest().fail("Test Failed - " + result.getTestContext().getName(),
				MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
		ExtentReport.getTest().fail("Test Failed", MediaEntityBuilder
				.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
		// ExtentReport.getTest().fail(result.getThrowable());

		StringWriter sw = new StringWriter();
		result.getThrowable().printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString(); // Write the stack trace to extent reports test.log(LogStatus.INFO, "<span
											// class='label failure'>" + result.getName() + "</span>",
											// "<pre>Stacktrace:\n" + stacktrace + "</pre>");
		boolean b = sw.toString().contains("openqa");
		ExtentReport.getTest().fail(
				"<script type=\"text/javascript\">function myFunc(element) {if (element.style.height === 'auto') {element.style.height = '50px';} else {element.style.height = 'auto';}}</script></script>"
						+ "<div class='test-heading heading' style='font-weight:bolder; font-size: 15px; color:red;'>"
						+ ((b == false) ? sw.toString().substring(0, sw.toString().indexOf("\n") + 1)
								: sw.toString().substring(0, sw.toString().indexOf("}") + 1))
						+ "</div><br /><span class='label failure'>" + result.getName()
						+ "</span>,<style>::-webkit-scrollbar{background: transparent;width: 7px;}::-webkit-scrollbar-thumb {background-color: rgba(0, 0, 0, 0.1);border: solid rgba(80,80,80,.3) 1px;}</style>"
						+ "<pre style=\"height: 100px;\" onClick=\"myFunc(this)\">Stacktrace:\n" + stacktrace
						+ "</pre>");
	}

	@Override
	public void onTestStart(ITestResult result) {
		BaseTest base = new BaseTest();
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
				.assignCategory(base.getPlatform() + "_" + base.getDeviceName()).assignAuthor("Leyon");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReport.getTest().log(Status.PASS, "Test Passed");

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReport.getTest().log(Status.SKIP, "Test Skipped");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReport.getReporter().flush();
	}

}
