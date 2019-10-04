package WebRegFlows;

import java.util.concurrent.TimeUnit;
//import org.bouncycastle.asn1.DERApplicationSpecific;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Util.BrowserFactory;
import Util.ConfigReader;
import Util.WebRegUtil;
import Util.XlsUtil;
//import Util.XlsUtil;
import Util.emailReport;

public class AttendeeOnly extends BrowserFactory {

	int RND;
	Select sel;
	String firstN;
	String lastN;
	String sheet = "Attendee_Only";
	int column = 6;	
		
//	JavascriptExecutor jse;
	XlsUtil xls = new XlsUtil(config.getwebregexcelpath());
	JavascriptExecutor jse = (JavascriptExecutor) driver;

	@BeforeClass
	public int RandomNo() {
		RND = (int) (Math.random() * 10000);
		return RND;
	}

	@Test(priority = 0)
	public void welcomePage() throws Exception {

		driver = BrowserFactory.getBrowser(); 
		wait = new WebDriverWait(driver, 100);
		
		StringBuffer newurl = new StringBuffer(config.getFlowpath());
		newurl.append(config.getshowcode());
		driver.get(newurl.toString());
		

		// Click on the Flow Name
		driver.findElement(By.xpath("//a[normalize-space()='" + config.getattflowname() + "']")).click();
		Thread.sleep(1000);

		Assert.assertTrue(driver.findElement(By.xpath("//button[@ng-click='PersonSwitcher.signIn()']")).isDisplayed(),
				"Failed to launch Dashboard page");
		driver.findElement(By.xpath("(//button[@data-ng-click='dashboardSectionsCtrl.goToTask(section)'])[1]")).click();

		Thread.sleep(1000);
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//exl-static-text[@id-code='BUTTON_CONTINUE_AS_NONMEMBER']")));
	

		Assert.assertTrue(driver.findElement(By.xpath("//exl-static-text[@id-code='BUTTON_CONTINUE_AS_NONMEMBER']"))
				.isDisplayed(), "Could not load Membership Search page");
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//exl-static-text[@id-code='BUTTON_CONTINUE_AS_NONMEMBER']")));

		Thread.sleep(1000);

		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Profile']")).isDisplayed(),
				"Could not Load Profile Page");
		Boolean profile = driver.findElement(By.xpath("//span[contains(text(),'Profile')]")).isDisplayed();
		if (profile) {
			xls.setCellData(sheet, column, 3, "Pass");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 3, "Fail");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		}
	}

	@Test(priority = 1)
	public void profilePage() throws Exception {
		// String mainWindow = driver.getWindowHandle();

		wait = new WebDriverWait(driver, 100);
		driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("sree" + RND);
		firstN = "sree" + RND;
		driver.findElement(By.xpath("//input[@name='middle']")).sendKeys("Middle");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("keyan" + RND);
		lastN = "keyan" + RND;
		driver.findElement(By.xpath("//input[@name='prefix']")).sendKeys("Prefix");
		// driver.findElement(By.xpath("//input[@name='suffix']")).sendKeys("Suffix");
		driver.findElement(By.xpath("//input[@name='company']")).sendKeys("ICS");
		driver.findElement(By.xpath("//input[@name='company2']")).sendKeys("ICS 1");
		driver.findElement(By.xpath("//input[@name='address']")).sendKeys("Whitefield");
		driver.findElement(By.xpath("//input[@name='address2']")).sendKeys("Whitefield 1");
		driver.findElement(By.xpath("//input[@name='zipCode']")).sendKeys("22222");
		driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("7676703939");
		driver.findElement(By.xpath("//input[@name='phoneExtension']")).sendKeys("1087");
		driver.findElement(By.xpath("//input[@name='phone2']")).sendKeys("9900401524");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("sreeja" + RND + "@test.com");
		// driver.findElement(By.xpath("//input[@name='fax']")).sendKeys("1111111111");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("ssssssssss");
		driver.findElement(By.xpath("//input[@name='nickName']")).sendKeys("paaru");
		driver.findElement(By.xpath("//input[@name='badgeCompany']")).sendKeys("Badge Co Infinte");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/ng-transclude")));
		 						
		driver.findElement(By.xpath("//button/ng-transclude")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(.,'Terms of Use')]/preceding-sibling::input")).click();
		jse.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//input[@name='badgeCompany']")));
		
		driver.findElement(By.xpath("//button/ng-transclude")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Show Questions')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Show Questions')]")).isDisplayed(),
				"Show Questions page was not loaded");
		int showquestion = driver.findElements(By.xpath("//span[contains(text(),'Show Questions')]")).size();
		if (showquestion > 0) {
			xls.setCellData(sheet, column, 4, "Pass");
			xls.setCellData(sheet, column - 1, 4, System.getProperty("user.name"));
			System.out.println("Show question page is loaded");

		} else {
			xls.setCellData(sheet, column, 4, "Fail");
			xls.setCellData(sheet, column - 1, 4, System.getProperty("user.name"));
		}
		
		driver.findElement(By.xpath("//button[@ng-click=\"SelectList.clickitem(item, $event)\"]/div[contains(.,'Development')]")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/ng-transclude")));

		WebElement webElement = driver.findElement(By.xpath("//button/ng-transclude"));
		jse.executeScript("arguments[0].click();", webElement);
		// webElement.click();
		
		driver.findElement(By.xpath("//button/ng-transclude")).click();
		
		Thread.sleep(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[contains(@class,'itemtable')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//table[contains(@class,'itemtable')]")).isDisplayed(),"Registration page was not loaded");
		int registration = driver.findElements(By.xpath("//table[contains(@class,'itemtable')]")).size();
		if (registration > 0) {
			System.out.println("Registration page is loaded");
			xls.setCellData(sheet, column, 5, "Pass");
			xls.setCellData(sheet, column - 1, 5, System.getProperty("user.name"));

		} else {
			xls.setCellData(sheet, column, 5, "Fail");
			xls.setCellData(sheet, column - 1, 5, System.getProperty("user.name"));
		}

		driver.findElement(By.xpath("//button/ng-transclude")).click();

		Thread.sleep(3000);
		/*
		 * Boolean toast =
		 * driver.findElement(By.xpath("//*[@id='toast-container']//button")).
		 * isDisplayed(); if(toast) {
		 * driver.findElement(By.xpath("//*[@id='toast-container']//button")).
		 * click();
		 */
		// driver.findElement(By.xpath("//span[contains(text(),'Next:
		// Guests')]")).click();
		// }

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Guests')]")));

		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Guests')]")).isDisplayed(),
				"Guest page was not loaded");
		int guests = driver.findElements(By.xpath("//span[contains(text(),'Guests')]")).size();
		if (guests > 0) {
			System.out.println("Guest page is loaded");
			xls.setCellData(sheet, column, 6, "Pass");
			xls.setCellData(sheet, column - 1, 6, System.getProperty("user.name"));

		} else {
			xls.setCellData(sheet, column, 6, "Fail");
			xls.setCellData(sheet, column - 1, 6, System.getProperty("user.name"));
		}
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id='firstName']")).sendKeys("G First Name");
		driver.findElement(By.xpath("//*[@id='lastName']")).sendKeys("G Last Name");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		Thread.sleep(3000);
		Boolean toast1 = driver.findElement(By.xpath("//*[@id='toast-container']//button")).isDisplayed();
		if (toast1) {
			driver.findElement(By.xpath("//*[@id='toast-container']//button")).click();
			// driver.findElement(By.xpath("//span[contains(text(),'Next:
			// Guests')]")).click();
		}
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Boolean addguest = driver.findElement(By.xpath("//h2[1][contains(text(),'Guests')]")).isDisplayed();
		if (addguest) {
			System.out.println("One guest is added");
		}

		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/ng-transclude")));

		driver.findElement(By.xpath("//button/ng-transclude")).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Your Events')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Your Events')]")).isDisplayed(),
				"Your event page was not loaded");
		int yourevents = driver.findElements(By.xpath("//span[contains(text(),'Your Events')]")).size();

		if (yourevents > 0) {
			System.out.println("Your event page is loaded");
			xls.setCellData(sheet, column, 7, "Pass");
			xls.setCellData(sheet, column - 1, 7, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 7, "Fail");
			xls.setCellData(sheet, column - 1, 7, System.getProperty("user.name"));
		}			
		
		

		
		// Invite Page
		driver.findElement(By.xpath("//button/ng-transclude")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));

		
//		  int displayed = driver.findElements(By.id("MainContent_ddlFriendLimit")).size();
		 
		 System.out.println("Reached Invite A Friend Page"); 
//		 boolean disablecheckbox = driver.findElement(By.id("CheckBoxDisableTask")).isSelected();
//		 if (disablecheckbox) {
//		  driver.findElement(By.id("CheckBoxDisableTask")).click(); }
		 
		Thread.sleep(1000);
//		wait.until(ExpectedConditions
//				.elementToBeClickable(By.xpath("//ng-transclude[contains(text(),'Next: Review and Check Out')]")));
		Assert.assertTrue(
				driver.findElement(By.xpath("//button/ng-transclude")).isDisplayed(),
				"Review and Checkout page was not loaded");
		driver.findElement(By.xpath("//button/ng-transclude")).click();

		int reviewpage = driver.findElements(By.xpath("//span[contains(text(),'Review and Check Out')]")).size();
		if (reviewpage > 0) {
			System.out.println("Review and Checkout page is loaded");
			xls.setCellData(sheet, column, 8, "Pass");
			xls.setCellData(sheet, column - 1, 8, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 8, "Fail");
			xls.setCellData(sheet, column - 1, 8, System.getProperty("user.name"));
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/ng-transclude")));

		driver.findElement(By.xpath("//button/ng-transclude")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(.,'Summary of Charges')]")));

		int paymentPage = driver.findElements(By.xpath("//h2[contains(.,'Summary of Charges')]")).size();
		/*if (paymentPage > 0) {
			System.out.println("Payment page is loaded");
			xls.setCellData(sheet, column, 9, "Pass");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 9, "Fail");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'Summary of Charges')]")).isDisplayed(),
				"Payment page was not loaded");
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")));

		int securemitPage = driver
				.findElements(By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")).size();
//		if (securemitPage > 0) {
*/		if (paymentPage > 0) {
			System.out.println("Securemit page is loaded");
			xls.setCellData(sheet, column, 10, "Pass");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 10, "Fail");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'Summary of Charges')]")).isDisplayed(),
				"Payment page was not loaded.");
	}

	@Test(priority = 2, dependsOnMethods = {"profilePage"})
	public void payment() throws Exception {

		WebRegUtil util = new WebRegUtil();
		boolean paymentResult = util.payment(driver);

		if (paymentResult) {
			System.out.println("Confirmation page is loaded");
			xls.setCellData(sheet, column, 11, "Pass");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 11, "Fail");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
		}

		Thread.sleep(1000);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-ng-click='taskCtrl.signOut()']")));		
		
		driver.findElement(By.xpath("//span[contains(.,'View Confirmation')]/ancestor::button")).click();
		Assert.assertTrue(driver.findElement(By.xpath("(//button[@class='small dark'])[2]")).isDisplayed(), "Confirmation page not loaded");
				
		driver.findElement(By.xpath("//button[@class='close-reveal-modal empty']")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[@data-ng-click='taskCtrl.signOut()']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Welcome')]")));

		int welcomePage = driver.findElements(By.xpath("//span[contains(text(),'Welcome')]")).size();
		if (welcomePage > 0) {
			System.out.println("Welcome page is loaded");
			xls.setCellData(sheet, column, 12, "Pass");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 12, "Fail");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Welcome')]")).isDisplayed(),
				"Could not Sign Out");
		System.out.println("SUCCESS, HURREYY");
	}

	@AfterClass
	public void sendEmailReport() {

		emailReport rept = new emailReport();
		rept.sendEmail(this.getClass().getSimpleName(), "Attendee_Only");

	}
}