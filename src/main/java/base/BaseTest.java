package base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {
	protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	protected static ThreadLocal<String> platform = new ThreadLocal<String>();
	protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
	protected static ThreadLocal<String> methodName = new ThreadLocal<String>();
	protected static ThreadLocal<Boolean> isVideoRecoringSupported = new ThreadLocal<Boolean>();
	private static ThreadLocal<AppiumDriverLocalService> servers = new ThreadLocal<AppiumDriverLocalService>();
	private AppiumDriverLocalService server;

	public AppiumDriver getDriver() {
		return driver.get();
	}

	public void setDriver(AppiumDriver driver2) {
		driver.set(driver2);
	}

	public AppiumDriverLocalService getAppiumServer() {
		return servers.get();
	}

	public void setAppiumServer(AppiumDriverLocalService service) {
		servers.set(service);
	}

	public Properties getProps() {
		return props.get();
	}

	public void setProps(Properties props2) {
		props.set(props2);
	}
	
	public String getProperty(String propertyName) {
		return props.get().getProperty(propertyName);
	}
	
	public void loadProperties()
	{
		InputStream inputStream = null;
		Properties props = new Properties();
		props = new Properties();
		String propFileName = getTestContext() + ".properties";
		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
			props.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setProps(props);
	}

	public String getPlatform() {
		return platform.get();
	}

	public void setPlatform(String platform2) {
		platform.set(platform2);
	}

	public static String getDateTime() {
		return dateTime.get();
	}

	public void setDateTime(String dateTime2) {
		dateTime.set(dateTime2);
	}

	public String getDeviceName() {
		return deviceName.get();
	}

	public void setDeviceName(String deviceName2) {
		deviceName.set(deviceName2);
	}


	public String getTestContext() {
		return methodName.get();
	}

	public void setTestContext(String deviceName2) {
		methodName.set(deviceName2);
	}

	public boolean getIsVideoRecoringSupported() {
		return isVideoRecoringSupported.get();
	}

	public void setIsVideoRecoringSupported(boolean videoRecoringSupported) {
		isVideoRecoringSupported.set(videoRecoringSupported);
	}

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	public void startAppium(int port) throws Exception {
		servers.set(getAppiumServerDefault(port));
		if (!checkIfAppiumServerIsRunnning(port)) {
			getAppiumServer().start();
			getAppiumServer().clearOutPutStreams();
			System.out.println("Appium server started");
		} else {
			System.out.println("Appium server already running");
		}
	}

	public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
		boolean isAppiumServerRunning = false;
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			socket.close();
		} catch (IOException e) {
			isAppiumServerRunning = true;
		} finally {
			socket = null;
		}
		return isAppiumServerRunning;
	}

	public AppiumDriverLocalService getAppiumServerDefault(int port) {
		// return AppiumDriverLocalService.buildDefaultService();
		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingPort(port)
				.withArgument(() -> "--allow-insecure", "chromedriver_autodownload"));
	}


	@Parameters({ "emulator", "port", "platformName", "udid", "deviceName", "systemPort", "chromeDriverPort",
			"wdaLocalPort", "webkitDebugProxyPort" })
	@BeforeMethod
	public void beforeTest(@Optional("androidOnly") String emulator, String port, String platformName, String udid,
			String deviceName, @Optional("androidOnly") String systemPort,
			@Optional("androidOnly") String chromeDriverPort, @Optional("iOSOnly") String wdaLocalPort,
			@Optional("iOSOnly") String webkitDebugProxyPort, ITestResult result) throws Exception {
		startAppium(Integer.parseInt(port));
		setDateTime(dateTime());
		setPlatform(platformName);
		setDeviceName(deviceName);
		setTestContext(result.getMethod().getMethodName());
		// setPort(Integer.parseInt(port));
		URL url;
		InputStream inputStream = null;
		Properties props = new Properties();
		AppiumDriver driver;

		String strFile = "logs" + File.separator + platformName + "_" + deviceName;
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		try {
			props = new Properties();
			String propFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			props.load(inputStream);
			setProps(props);
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability("platformName", platformName);
			desiredCapabilities.setCapability("deviceName", deviceName);
			desiredCapabilities.setCapability("udid", udid);
			desiredCapabilities.setCapability("fullReset", true);
			desiredCapabilities.setCapability("chromedriverExecutable",
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
							+ "resources" + File.separator + "executable" + File.separator + "chromedriver.exe");

			url = new URL("http://127.0.0.1:" + port + "/wd/hub");

			switch (platformName) {
			case "Android":
				desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));
				desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
				desiredCapabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
				if (emulator.equalsIgnoreCase("true")) {
					desiredCapabilities.setCapability("avd", deviceName);
					desiredCapabilities.setCapability("avdLaunchTimeout", 60000);
					desiredCapabilities.setCapability("uiautomator2ServerInstallTimeout", 60000);
					desiredCapabilities.setCapability("adbExecTimeout", 30000);
				}
				// desiredCapabilities.setCapability("systemPort", systemPort);
				//desiredCapabilities.setCapability("chromeDriverPort", chromeDriverPort);
				String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
						+ File.separator + "resources" + File.separator + "app" + File.separator + "General-Store.apk";
				System.out.println("appUrl is" + androidAppUrl);
				desiredCapabilities.setCapability("app", androidAppUrl);

				driver = new AndroidDriver(url, desiredCapabilities);
				break;
			case "iOS":
				desiredCapabilities.setCapability("automationName", props.getProperty("iOSAutomationName"));
				String iOSAppUrl = "";
				System.out.println("appUrl is" + iOSAppUrl);
				desiredCapabilities.setCapability("bundleId", props.getProperty("iOSBundleId"));
				desiredCapabilities.setCapability("wdaLocalPort", wdaLocalPort);
				desiredCapabilities.setCapability("webkitDebugProxyPort", webkitDebugProxyPort);
				desiredCapabilities.setCapability("app", iOSAppUrl);

				driver = new IOSDriver(url, desiredCapabilities);
				break;
			default:
				throw new Exception("Invalid platform! - " + platformName);
				
				
			}
			setDriver(driver);
			System.out.println("driver initialized: " + driver);
			
			try {
				((CanRecordScreen) getDriver()).startRecordingScreen();
				isVideoRecoringSupported.set(true);;
				System.out.println("Video recording started");
			} catch (Exception e) {
				isVideoRecoringSupported.set(false);;
				System.out.println(
						"Video recording not supported for this device. Plz add or grant permission to screenrecord module in /sys/bin");
			}
		} catch (Exception e) {
			System.out.println("driver initialization failure. ABORT!!!\n" + e.toString());
			throw e;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	

	@AfterMethod
	public synchronized void afterMethod(ITestResult result) throws Exception {
		if (isVideoRecoringSupported.get()) {
			String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();

			Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
			String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
					+ File.separator + getDateTime() + File.separator
					+ result.getTestClass().getRealClass().getSimpleName();

			// ExtentReport.getTest().info("Video Recording",
			// MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")
			// + File.separator + dirPath + File.separator + result.getName() +
			// ".mp4").build());
			// ExtentReport.getTest().info("Video recording in file",
			// MediaEntityBuilder.createScreenCaptureFromBase64String(media).build());
			ExtentReport.getTest()
					.info("<video width='200' height='320' controls>" + "		  <source src='"
							+ System.getProperty("user.dir") + File.separator + dirPath + File.separator
							+ result.getName() + ".mp4" + "' type='video/mp4'>"
							+ "		  Your browser does not support the video tag." + "		</video>");
			File videoDir = new File(dirPath);

			synchronized (videoDir) {
				if (!videoDir.exists()) {
					videoDir.mkdirs();
				}
			}
			FileOutputStream stream = null;
			try {
				stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
				stream.write(Base64.decodeBase64(media));
				stream.close();
				ExtentReport.getTest().info("video path: " + videoDir + File.separator + result.getName() + ".mp4");
				System.out.println("video path: " + videoDir + File.separator + result.getName() + ".mp4");
			} catch (Exception e) {
				ExtentReport.getTest().fail("error during video capture" + e.toString());
			} finally {
				if (stream != null) {
					stream.close();
				}
			}
		}
		
		if (getDriver() != null) {
			getDriver().quit();
			System.out.println("Driver stopped");
		}

		if (getAppiumServer().isRunning()) {
			getAppiumServer().stop();
			System.out.println("Appium server stopped");
		}
	}

	public String dateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
