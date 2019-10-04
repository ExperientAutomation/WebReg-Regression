package WebRegFlows;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Util.emailReport;

public class Unified extends BrowserFactory{
	
	WebDriver driver;
	ConfigReader config = new ConfigReader();
	WebDriverWait wait;
	int RND;
	Select sel;
	String firstN;
	String lastN;
	String fName = "G1";
	String lName = "G2";
	String sheet = "Unified";
	int column = 6;
	XlsUtil xls = new XlsUtil(config.getwebregexcelpath());

	@BeforeClass
	public int RandomNo() {
		RND = (int) (Math.random() * 10000);
		return RND;
	}

	@Test(priority = 0)
	public void welcomePage() throws Exception {
		
		driver = BrowserFactory.getBrowser(); 
		wait = new WebDriverWait(driver, 100);
		
		// Get the URL from config file and append showcode
		StringBuffer newurl = new StringBuffer(config.getFlowpath());
		newurl.append(config.getshowcode());
		driver.get(newurl.toString());
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		Thread.sleep(1000);
		// Click on the Flow Name
		driver.findElement(By.xpath("//a[normalize-space()='"+config.getuniflowname()+"']")).click();
		Thread.sleep(1000);

		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//button[@data-ng-click='dashboardSectionsCtrl.goToTask(section)'])[1]")));
		driver.findElement(By.xpath("(//button[@data-ng-click='dashboardSectionsCtrl.goToTask(section)'])[1]")).click();

		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//exl-static-text[@id-code='BUTTON_CONTINUE_AS_NONMEMBER']")));
		driver.findElement(By.xpath("//exl-static-text[@id-code='BUTTON_CONTINUE_AS_NONMEMBER']")).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[contains(text(),'Profile')]")));
		int profile = driver.findElements(By.xpath("//span[contains(text(),'Profile')]")).size();
		if (profile > 0)

		{
			System.out.print("Profile page is loaded");
			xls.setCellData(sheet, column, 3, "Pass");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 3, "Fail");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Profile')]")).isDisplayed(),"Profile page was not loaded");
	}

	@Test(priority = 1)
	public void profilePage() throws Exception {

		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
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
//		driver.findElement(By.xpath("(//input[@type='checkbox'])[1]")).click();
		// driver.findElement(By.xpath("//input[@name='fax']")).sendKeys("1111111111");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("ssssssssss");
		driver.findElement(By.xpath("//input[@name='nickName']")).sendKeys("paaru");
		driver.findElement(By.xpath("//input[@name='badgeCompany']")).sendKeys("Badge Co Infinte");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/ng-transclude")));
			
		driver.findElement(By.xpath("//button/ng-transclude")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(.,'Terms of Use')]/preceding-sibling::input")).click();
		jse= (JavascriptExecutor) driver;
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

				
//		int displayed = driver.findElements(By.id("MainContent_ddlFriendLimit")).size();
				 
		System.out.println("Reached Invite A Friend Page"); 

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/ng-transclude")));
		driver.findElement(By.xpath("//button/ng-transclude")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Room Search')]")));
		int roomsearch = driver.findElements(By.xpath("//span[contains(text(),'Room Search')]")).size();

		if (roomsearch > 0) {
			System.out.println("The Room Search page is loaded");
			xls.setCellData(sheet, column, 9, "Pass");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 9, "Fail");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		}

//		driver.findElement(By.xpath("//button[@type='submit']")).click();

		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//button[contains(text(),'View Rooms')])[1]")));
		int viewRoom = driver.findElements(By.xpath("(//button[contains(text(),'View Rooms')])[1]")).size();

		if (viewRoom > 0) {
			System.out.println("The View Room page is loaded");
			xls.setCellData(sheet, column, 10, "Pass");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 10, "Fail");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
		}

		driver.findElement(By.xpath("(//button[contains(text(),'View Rooms')])[1]")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Room Choice')]")));

		int roomchoice = driver.findElements(By.xpath("//span[contains(text(),'Room Choice')]")).size();
		if (roomchoice > 0) {
			System.out.println("The Room choice page is loaded");
			xls.setCellData(sheet, column, 11, "Pass");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 11, "Fail");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
		}

//		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Room Choice')]")).isDisplayed(),"The Room choice page was not loaded");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'Reserve')])[1]")));
		driver.findElement(By.xpath("(//button[contains(text(),'Reserve')])[1]")).click();
		
		Thread.sleep(2000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='dark']")));
		
		int hotelDetails = driver.findElements(By.xpath("//button[@class='dark']")).size();
		if (hotelDetails > 0) {
			System.out.println("The Hotel Details page is loaded");
			xls.setCellData(sheet, column, 12, "Pass");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 12, "Fail");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
		}

		driver.findElement(By.xpath("//button[@class='dark']")).click();

		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Room
		// Occupants')]")));
		int roomoccupant = driver.findElements(By.xpath("//span[contains(text(),'Room Occupants')]")).size();
		if (roomoccupant > 0) {
			System.out.println("Room Occupant page is loaded");
			xls.setCellData(sheet, column, 13, "Pass");
			xls.setCellData(sheet, column - 1, 13, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 13, "Fail");
			xls.setCellData(sheet, column - 1, 13, System.getProperty("user.name"));
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@data-ng-click,'addOccupant')]")));
		
		driver.findElement(By.xpath("//button[contains(@data-ng-click,'addOccupant')]")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),' Create New')]")));
		
		int addperson = driver.findElements(By.xpath("//button[contains(text(),' Create New')]")).size();
		if (addperson > 0) {
			System.out.println("Guest form is loaded");
			xls.setCellData(sheet, column, 14, "Pass");
			xls.setCellData(sheet, column - 1, 14, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 14, "Fail");
			xls.setCellData(sheet, column - 1, 14, System.getProperty("user.name"));
		}

		driver.findElement(By.xpath("//button[contains(text(),' Create New')]")).click();
		driver.findElement(By.id("firstName")).sendKeys(fName);
		driver.findElement(By.id("lastName")).sendKeys(lName);
		driver.findElement(By.id("address")).sendKeys("Address");
		driver.findElement(By.id("email")).sendKeys("sreeja" + RND + "@test.com");

		// HousingOnly random = new HousingOnly();

		// String emailID = "sree"+random.RandomNo()+"@test.com";
		// driver.findElement(By.id("email")).sendKeys(RND);
		// driver.findElement(By.id("email")).sendKeys("sree1003@test.com");
		driver.findElement(By.id("zipCode")).sendKeys("22222");
		// driver.findElement(By.id("zipCode")).sendKeys("tab");
		driver.findElement(By.id("phone")).sendKeys("9900401524");
		driver.findElement(By.xpath("//input[@name='frequentGuestID']")).sendKeys("1233");
		driver.findElement(By.id("password")).sendKeys("ssssssss");
		driver.findElement(By.xpath("//a[contains(.,'Terms of Use')]/preceding-sibling::input")).click();
		driver.findElement(By.xpath("//button[contains(@data-ng-click,'validateSave')]")).click();
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button/i/span[contains(text(),'Edit
		// Guest')]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Edit Guest')]")));

		int guestadd = driver.findElements(By.xpath("//span[contains(text(),'Edit Guest')]")).size();
		if (guestadd > 0) {
			System.out.println("Guest is added");
			xls.setCellData(sheet, column, 15, "Pass");
			xls.setCellData(sheet, column - 1, 15, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 15, "Fail");
			xls.setCellData(sheet, column - 1, 15, System.getProperty("user.name"));
		}

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Edit Guest')]")));
		driver.findElement(By.xpath("//button/ng-transclude")).click();
		// Thread.sleep(5000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button/ng-transclude")));

		int reviewcheckout = driver.findElements(By.xpath("//span[contains(text(),'Review and Check Out')]")).size();
		if (reviewcheckout > 0) {
			System.out.println("Review and checkout page is Loaded");
			xls.setCellData(sheet, column, 16, "Pass");
			xls.setCellData(sheet, column - 1, 16, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 16, "Fail");
			xls.setCellData(sheet, column - 1, 16, System.getProperty("user.name"));
		}
		
//		Assert.assertTrue(driver.findElement(By.xpath("//span/span[contains(text(),'Review and Check Out')]")).isDisplayed(),"Review and checkout page was not Loaded");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/ng-transclude")));
		driver.findElement(By.xpath("//button/ng-transclude")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Pay Now')]")));

		int groupSummary = driver.findElements(By.xpath("//span[contains(text(),'Pay Now')]")).size();
		if (groupSummary > 0) {
			System.out.println("Group Summary page is Loaded");
			xls.setCellData(sheet, column, 17, "Pass");
			xls.setCellData(sheet, column - 1, 17, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 17, "Fail");
			xls.setCellData(sheet, column - 1, 17, System.getProperty("user.name"));
		}
		
//		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Pay Now')]")).isDisplayed(),"Group Summary was not loaded");

		driver.findElement(By.xpath("//span[contains(text(),'Pay Now')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Payment Options')]")));
		
		int payment = driver.findElements(By.xpath("//span[contains(text(),'Payment Options')]")).size();

		if (payment > 0) {
			System.out.println("Payment page loaded");
			xls.setCellData(sheet, column, 18, "Pass");
			xls.setCellData(sheet, column - 1, 18, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 18, "Fail");
			xls.setCellData(sheet, column - 1, 18, System.getProperty("user.name"));
		}

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/ng-transclude")));
		driver.findElement(By.xpath("//button/ng-transclude")).click();

		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")));
		int secureMit = driver
				.findElements(By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")).size();

		if (secureMit > 0) {
			System.out.println("Securemit page loaded");
			xls.setCellData(sheet, column, 19, "Pass");
			xls.setCellData(sheet, column - 1, 19, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 19, "Fail");
			xls.setCellData(sheet, column - 1, 19, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")).isDisplayed(),
				"Securemit page was not loaded.");

	}

	@Test(priority = 2, dependsOnMethods = {"profilePage"})
	public void payment() throws Exception {

		WebRegUtil util = new WebRegUtil();
		boolean paymentResult = util.payment(driver);

		if (paymentResult) {
			System.out.println("Confirmation page is loaded");
			xls.setCellData(sheet, column, 20, "Pass");
			xls.setCellData(sheet, column - 1, 20, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 20, "Fail");
			xls.setCellData(sheet, column - 1, 20, System.getProperty("user.name"));
		}

		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-ng-click='taskCtrl.signOut()']")));
		
		driver.findElement(By.xpath("(//span[contains(.,'View Confirmation')]/ancestor::button)[2]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='small dark'])[2]")));
		Assert.assertTrue(driver.findElement(By.xpath("(//button[@class='small dark'])[2]")).isDisplayed(), "Confirmation page not loaded");
		
		driver.findElement(By.xpath("//button[@class='close-reveal-modal empty']")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[@data-ng-click='taskCtrl.signOut()']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Welcome')]")));

		int welcomePage = driver.findElements(By.xpath("//span[contains(text(),'Welcome')]")).size();
		if (welcomePage > 0) {
			System.out.println("Welcome page is loaded");
			xls.setCellData(sheet, column, 21, "Pass");
			xls.setCellData(sheet, column - 1, 21, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 21, "Fail");
			xls.setCellData(sheet, column - 1, 21, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Welcome')]")).isDisplayed(),
				"Could not Sign Out");
		System.out.println("SUCCESS, HURREYY");
	}

	@AfterClass
	public void sendEmailReport() {

		emailReport rept = new emailReport();
		rept.sendEmail(this.getClass().getSimpleName(),"Unified");

	}
}