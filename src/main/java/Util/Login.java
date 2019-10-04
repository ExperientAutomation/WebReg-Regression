package Util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login extends BrowserFactory {

	public static void login(WebDriver driver) throws Throwable {
		
		config = new ConfigReader();
		// Login to SSW
		driver.get(config.geturl());
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.id("MainContent_LoginMain_UserName")).sendKeys(config.LoginCredentails("USER_NAME"));
		driver.findElement(By.id("MainContent_LoginMain_Password")).sendKeys(config.LoginCredentails("PASSWORD"));
		driver.findElement(By.id("MainContent_LoginMain_LoginButton")).click();
				
	}
}
