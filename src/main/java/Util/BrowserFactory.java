package Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BrowserFactory {	

	public static WebDriver driver;
	public static ConfigReader config = new ConfigReader();
	public static WebDriverWait wait;

	public static XlsUtil xls;
	public static JavascriptExecutor jse;

	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
    static String browserName = config.getbrowser();


	/*
	 * Factory method for getting browsers
	 */
	@BeforeSuite
	public static WebDriver getBrowser() {		
			
		try {
			switch (browserName) {
			case "Firefox":
				driver = drivers.get("Firefox");
				if (driver == null) {
					driver = new FirefoxDriver();
					drivers.put("Firefox", driver);
				}
				break;
			case "IE":
				driver = drivers.get("IE");
				if (driver == null) {
					System.setProperty("webdriver.ie.driver", "C:\\Users\\abc\\Desktop\\Server\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
					drivers.put("IE", driver);
				}
				break;
			case "Chrome":
				driver = drivers.get("Chrome");
				if (driver == null) {
					System.setProperty("webdriver.chrome.driver", config.getChromeFilePath());
					driver = new ChromeDriver();
					drivers.put("Chrome", driver);
					driver.manage().window().maximize();
					driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				}
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return driver;
	}

	@AfterSuite(enabled = true)
	public void tear() {
		driver.quit();
	}
}
