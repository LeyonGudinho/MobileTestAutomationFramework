package testutils;

import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;

import base.ExtentReport;

public class Assertion extends Assert {

	public static void assertTrue(boolean condition, String message) {
		Assert.assertTrue(condition);
		ExtentReport.pass(message);
	}

	public static void assertFalse(boolean condition, String message) {
		Assert.assertFalse(condition);
		ExtentReport.pass(message);
	}

	public static void assertEquals(String actual, String expected, String message) {
		Assert.assertEquals(actual, expected);
		ExtentReport.pass(message);
	}

	public static void assertEquals(Object actual, Object expected, String message) {
		Assert.assertEquals(actual, expected);
		ExtentReport.pass(message);
	}

	public static void assertEquals(int actual, int expected, String message) {
		Assert.assertEquals(actual, expected);
		ExtentReport.pass(message);
		}

	public static void assertNotEqual(String actual, String expected, String message) {
		Assert.assertNotEquals(actual, expected);
		ExtentReport.pass(message);
	}

	public static void assertNotNull(Object actual, String message) {
		Assert.assertNotNull(String.valueOf(actual));
		ExtentReport.pass(message);
	}
}