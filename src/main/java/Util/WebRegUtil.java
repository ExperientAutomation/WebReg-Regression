package Util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebRegUtil {
	Select sel;
	ConfigReader config = new ConfigReader();
	
	
	public boolean payment (WebDriver driver) throws Exception{
			
			WebDriverWait wait = new WebDriverWait(driver, 60);
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")));

			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(By.xpath("//div[contains(@id,'ContentMain_CreditCardControl1_tdPayorDropDown')]/select"))).click().build().perform();

			sel = new Select(driver.findElement(
					By.xpath("//div[contains(@id,'ContentMain_CreditCardControl1_tdPayorDropDown')]/select")));
			sel.selectByIndex(1);
//			sel.selectByValue("G1 G2");

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Credit Card Number']")));
			System.out.println("crossed visibility account number");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@placeholder='Credit Card Number']")).sendKeys(config.getCCNumber(),
					Keys.TAB);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@title='Card expiration year']")));

			sel = new Select(driver.findElement(By.xpath("//select[@title='Card expiration year']")));
			// *[@id='ctl00_ContentMain_CreditCardControl1_DropDownListYear']
			sel.selectByValue("20");

			// driver.findElement(By.xpath(".//select[@title='Card expiration
			// year']")).sendKeys("2019");
			Thread.sleep(1000);
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Submit
			// Payment']")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Submit']")));
			driver.findElement(By.xpath("//input[@value='Submit']")).click();
			// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			// Thread.sleep(50000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[contains(text(),'Confirmation')])[1]")));
			Assert.assertTrue(driver.findElement(By.xpath("(//span[contains(text(),'Confirmation')])[1]")).isDisplayed(), "Confirmation page was not loaded");
			int confirm = driver.findElements(By.xpath("(//span[contains(text(),'Confirmation')])[1]")).size();
			if (confirm > 0) {
				System.out.println("Confirmation page is loaded");
				return true;
			}
			else {
				return false;
			}		
	}
	
	public boolean paymentExhibitor (WebDriver driver) throws Exception{
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")));

		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div[contains(@id,'ContentMain_CreditCardControl1_tdPayorDropDown')]/select"))).click().build().perform();

		sel = new Select(driver.findElement(
				By.xpath("//div[contains(@id,'ContentMain_CreditCardControl1_tdPayorDropDown')]/select")));
		sel.selectByIndex(1);
//		sel.selectByValue("G1 G2");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Credit Card Number']")));
		System.out.println("crossed visibility account number");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@placeholder='Credit Card Number']")).sendKeys(config.getCCNumber(),
				Keys.TAB);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@title='Card expiration year']")));

		sel = new Select(driver.findElement(By.xpath("//select[@title='Card expiration year']")));
		// *[@id='ctl00_ContentMain_CreditCardControl1_DropDownListYear']
		sel.selectByValue("20");

		// driver.findElement(By.xpath(".//select[@title='Card expiration
		// year']")).sendKeys("2019");
		Thread.sleep(1000);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Submit
		// Payment']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Submit Payment']")));
		jse.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.id("ctl00_ContentMain_CreditCardControl1_TextBoxEmail")));
		
		driver.findElement(By.xpath("//input[@value='Submit Payment']")).click();
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Thread.sleep(50000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Completed Personnel')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Completed Personnel')]")).isDisplayed(), "Completed Personnel page was not loaded");
		int confirm = driver.findElements(By.xpath("//span[contains(text(),'Completed Personnel')]")).size();
		if (confirm > 0) {
			System.out.println("Completed Personnel page is loaded");
			return true;
		}
		else {
			return false;
		}
	
}

}
