package WebRegFlows;

import java.util.concurrent.TimeUnit;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

//import org.bouncycastle.asn1.DERApplicationSpecific;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.BrowserFactory;
import Util.ConfigReader;
import Util.WebRegUtil;
import Util.XlsUtil;
import Util.emailReport;
import Util.ConfigReader;

public class HousingOnly {

	WebDriver driver;
	WebDriverWait wait;
	ConfigReader config = new ConfigReader();
	String fName = "G1";
	String lName = "G2";
	// String NamePay=fName+" "+lName;
	// private double MAXVALUE;
	int RND;

	Select sel;

	String sheet = "Housing_Only";
	int column = 6;

	XlsUtil xls = new XlsUtil(config.getwebregexcelpath());

	@BeforeClass
	public int RandomNo() {
		RND = (int) (Math.random() * 10000);
		return RND;

	}

	@Test()
	public void RoomSearch() throws Exception {

		driver = BrowserFactory.getBrowser(); 
		wait = new WebDriverWait(driver, 100);
		
		// Get the URL from config file and append showcode
		StringBuffer newurl = new StringBuffer(config.getFlowpath());
		newurl.append(config.getshowcode());
		driver.get(newurl.toString());

		driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 70);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

//		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Welcome')]")).isDisplayed(),"The welcome page was not loaded");

		int welcomePage = driver.findElements(By.xpath("//span[contains(text(),'Welcome')]")).size();
		if (welcomePage>0) {
			System.out.println("The welcome page is loaded");
			xls.setCellData(sheet, column, 3, "Pass");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 3, "Fail");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		}

		// Click on the Flow Name
		jse.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//a[normalize-space()='" + config.gethouflowname() + "']")));
		
		driver.findElement(By.xpath("//a[normalize-space()='" + config.gethouflowname() + "']")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//span[contains(text(),'Add Room')]")).click();

//		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Room Search')]")).isDisplayed(),"The Room Search page was not loaded");

		int roomsearch = driver.findElements(By.xpath("//span[contains(text(),'Room Search')]")).size();
		if (roomsearch>0) {
			System.out.println("The Room Search page is loaded");
			xls.setCellData(sheet, column, 4, "Pass");
			xls.setCellData(sheet, column - 1, 4, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 4, "Fail");
			xls.setCellData(sheet, column - 1, 4, System.getProperty("user.name"));
		}

//		driver.findElement(By.xpath("//button[@type='submit']")).click();

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//button[contains(text(),'View Rooms')])[1]")));
		driver.findElement(By.xpath("(//button[contains(text(),'View Rooms')])[1]")).click();
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Room
		// Choice')]")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Room Choice')]")));

//		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Room Choice')]")).isDisplayed(),"The Room choice page was not loaded");
		int roomchoice = driver.findElements(By.xpath("//span[contains(text(),'Room Choice')]")).size();
		if (roomchoice>0) {
			System.out.println("The Room choice page is loaded");
			xls.setCellData(sheet, column, 5, "Pass");
			xls.setCellData(sheet, column - 1, 5, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 5, "Fail");
			xls.setCellData(sheet, column - 1, 5, System.getProperty("user.name"));
		}

		// driver.findElement(By.xpath("(//button[contains(text(),'Reserve')])[1]")).click();

		jse.executeScript("arguments[0].click();",
				driver.findElement(By.xpath("(//button[contains(text(),'Reserve')])[1]")));

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[@class='dark']")));
		driver.findElement(By.xpath("//button[@class='dark']")).click();
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Room
		// Occupants')]")));
//		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Room Occupants')]")).isDisplayed(),"The Room Occupant page was not loaded");
		int roomoccupant = driver.findElements(By.xpath("//span[contains(text(),'Room Occupants')]")).size();

		if (roomoccupant > 0) {
			System.out.println("Room Occupant page is loaded");
			xls.setCellData(sheet, column, 6, "Pass");
			xls.setCellData(sheet, column - 1, 6, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 6, "Fail");
			xls.setCellData(sheet, column - 1, 6, System.getProperty("user.name"));
		}

		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@data-ng-click,'addOccupant')]")));
		jse.executeScript("scroll(0, 100);");
		// jse.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//button[contains(@data-ng-click,'addOccupant')]")));
		driver.findElement(By.xpath("//button[contains(@data-ng-click,'addOccupant')]")).click();

		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@name='frmOccupant']")));
//		Assert.assertTrue(driver.findElement(By.xpath("//form[@name='frmOccupant']")).isDisplayed(),"Guest form was not loaded");
		int addperson = driver.findElements(By.id("firstName")).size();
		if (addperson>0) {
			System.out.println("Guest form is loaded");
			xls.setCellData(sheet, column, 7, "Pass");
			xls.setCellData(sheet, column - 1, 7, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 7, "Fail");
			xls.setCellData(sheet, column - 1, 7, System.getProperty("user.name"));
		}

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
		driver.findElement(By.xpath("//button[contains(@data-ng-click,'validateSave')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(.,'Terms of Use')]/preceding-sibling::input")).click();
		driver.findElement(By.xpath("//button[contains(@data-ng-click,'validateSave')]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Edit Guest')]")));
		// Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Edit
		// Guest')]")).isDisplayed(), "Guest was not added");
		int guestadd = driver.findElements(By.xpath("//span[contains(text(),'Edit Guest')]")).size();
		if (guestadd > 0) {
			System.out.println("Guest is added");
			xls.setCellData(sheet, column, 8, "Pass");
			xls.setCellData(sheet, column - 1, 8, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 8, "Fail");
			xls.setCellData(sheet, column - 1, 8, System.getProperty("user.name"));
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Edit Guest')]")));
		jse.executeScript("scroll(0, 100);");
		driver.findElement(By.xpath("//button/ng-transclude")).click();
		// Thread.sleep(5000);

		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[contains(text(),'Review and Check Out')]")));
//		Assert.assertTrue(driver.findElement(By.xpath("//span/span[contains(text(),'Review and Check Out')]")).isDisplayed(),"Review and checkout page was not loaded");
		int reviewcheckout = driver.findElements(By.xpath("//span[contains(text(),'Review and Check Out')]")).size();
		if (reviewcheckout > 0) {
			System.out.println("Review and checkout page is loaded");
			xls.setCellData(sheet, column, 9, "Pass");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 9, "Fail");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[3]/button[contains(text(),'Add Another Room')]")));
		driver.findElement(By.xpath("//p[3]/button[contains(text(),'Add Another Room')]")).click();
		
		// Add Another Room
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Room Search')]")));
		int roomsearch1 = driver.findElements(By.xpath("//span[contains(text(),'Room Search')]")).size();

		if (roomsearch1 > 0)
			{
			System.out.println("The Room Search page is loaded");
			xls.setCellData(sheet, column, 10, "Pass");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
			} else {
			xls.setCellData(sheet, column, 10, "Fail");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
			}
		Thread.sleep(5000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[contains(text(),'View Rooms')])[1]")));
//		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("(//button[contains(text(),'View Rooms')])[1]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Room Choice')]")));
		int roomchoice1 = driver.findElements(By.xpath("//span[contains(text(),'Room Choice')]")).size();
		if (roomchoice1 > 0) {
			System.out.println("The Room choice page is loaded");
			xls.setCellData(sheet, column, 11, "Pass");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 11, "Fail");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
		}
		Thread.sleep(5000);

		// driver.findElement(By.xpath("(//button[contains(text(),'Reserve')])[2]")).click();
		jse.executeScript("arguments[0].click();",
				driver.findElement(By.xpath("(//button[contains(text(),'Reserve')])[1]")));

		driver.findElement(By.xpath("//button[@class='dark']")).click();
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Room
		// Occupants')]")));

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Room Occupants')]")));
		int roomoccupant1 = driver.findElements(By.xpath("//span[contains(text(),'Room Occupants')]")).size();
		if (roomoccupant1 > 0) {
			System.out.println("Room Occupant page is loaded");
			xls.setCellData(sheet, column, 12, "Pass");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 12, "Fail");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
		}
		// Thread.sleep(5000);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[@class='dark']")));
		driver.findElement(By.xpath("//button[@class='dark']")).click();
		// driver.findElement(By.xpath("//button[@data-ng-hide='reservation.addRoomRegistrant')]")).click();

		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@name='frmOccupant']")));

		driver.findElement(By.xpath("//button[contains(text(),'Create New')]")).click();
		driver.switchTo().parentFrame();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("firstName"))));
		Boolean addperson1 = driver.findElement(By.id("firstName")).isDisplayed();
		if (addperson1) {
			System.out.println("Guest form is loaded");
			xls.setCellData(sheet, column, 13, "Pass");
			xls.setCellData(sheet, column - 1, 13, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 13, "Fail");
			xls.setCellData(sheet, column - 1, 13, System.getProperty("user.name"));
		}

		driver.findElement(By.id("firstName")).sendKeys("G11");
		driver.findElement(By.id("lastName")).sendKeys("G22");
		driver.findElement(By.id("address")).sendKeys("Address");
		RND = RND + 1;
		driver.findElement(By.id("email")).sendKeys("sreeja" + RND + "@test.com");
		driver.findElement(By.id("zipCode")).sendKeys("22222");
		// driver.findElement(By.id("zipCode")).sendKeys("tab");
		driver.findElement(By.id("phone")).sendKeys("9900401524");
		driver.findElement(By.xpath("//input[@name='frequentGuestID']")).sendKeys("1233");
		driver.findElement(By.id("password")).sendKeys("ssssssss");
//		driver.findElement(By.xpath(".//button[1][@data-ng-click='roomOccupantCtrl.validateSave(frmOccupant)']")).click();
		
		driver.findElement(By.xpath("//button[contains(@data-ng-click,'validateSave')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@type='checkbox'])[2]")).click();
		driver.findElement(By.xpath("//button[contains(@data-ng-click,'validateSave')]")).click();
		
		/*
		 * if(driver.findElement(By.xpath("//*[@id='toast-container']/div"))
		 * .isDisplayed()) { driver.findElement(By.xpath(
		 * "//input[@data-ng-model='roomOccupantCtrl.occupant.registrant.overrideDupeCheck']"
		 * )).click(); }
		 */

		Boolean guestadd1 = driver.findElement(By.xpath("//button/i/span[contains(text(),'Edit Guest')]"))
				.isDisplayed();
		if (guestadd1) {
			System.out.println("Guest is added");
			xls.setCellData(sheet, column, 14, "Pass");
			xls.setCellData(sheet, column - 1, 14, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 14, "Fail");
			xls.setCellData(sheet, column - 1, 14, System.getProperty("user.name"));
		}
		// Thread.sleep(50000);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Next:
		// Review and Check Out')]")));

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button/ng-transclude")));
		jse.executeScript("scroll(0, 200);"); 
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button/ng-transclude")).click();

		// span[contains(text(),'Next: Review and Check Out')]

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Review and Check Out')]")));
		// Assert.assertTrue(driver.findElement(By.xpath("//span/span[contains(text(),'Review
		// and Check Out')]")).isDisplayed(),"Review and Checkout page was not
		// loaded");
		int reviewpage = driver.findElements(By.xpath("//span[contains(text(),'Review and Check Out')]")).size();
		if (reviewpage > 0) {
			System.out.println("Review and Checkout page is loaded");
			xls.setCellData(sheet, column, 15, "Pass");
			xls.setCellData(sheet, column - 1, 15, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 15, "Fail");
			xls.setCellData(sheet, column - 1, 15, System.getProperty("user.name"));
		}

		driver.navigate().refresh();
		// Clicked on 'I authorize' check box

		driver.switchTo().defaultContent();
		// driver.switchTo().window(mainWindow);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button/ng-transclude")));

		//// span[contains(text(),' Next: Payment')]
		jse.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("(//button[contains(.,'Add Another Room')])[2]")));
		
		driver.findElement(By.xpath("//button/ng-transclude")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/ng-transclude")));
		// Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Payment')]")).isDisplayed(),
		// "Payment page was not loaded");
		int payment = driver.findElements(By.xpath("//span[contains(text(),'Payment')]")).size();
		if (payment > 0) {
			System.out.println("Payment page loaded");
			xls.setCellData(sheet, column, 16, "Pass");
			xls.setCellData(sheet, column - 1, 16, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 16, "Fail");
			xls.setCellData(sheet, column - 1, 16, System.getProperty("user.name"));
		}
		driver.switchTo().parentFrame();
		driver.findElement(By.xpath("//button/ng-transclude")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")));
		int securemitPage = driver.findElements(By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")).size();
		if (securemitPage>0) {
			System.out.println("Securemit page loaded");
			xls.setCellData(sheet, column, 17, "Pass");
			xls.setCellData(sheet, column - 1, 17, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 17, "Fail");
			xls.setCellData(sheet, column - 1, 17, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'ContentMain_CreditCardControl1_DropDownListPayor')]")).isDisplayed(),"Payment page was not loaded.");

	}

	@Test(dependsOnMethods={"RoomSearch"}, enabled = true)
	
	public void payment() throws Exception {

		WebRegUtil util = new WebRegUtil();
		boolean paymentResult = util.payment(driver);

		if (paymentResult) {
			System.out.println("Confirmation page is loaded");
			xls.setCellData(sheet, column, 18, "Pass");
			xls.setCellData(sheet, column - 1, 18, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 18, "Fail");
			xls.setCellData(sheet, column - 1, 18, System.getProperty("user.name"));
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
			xls.setCellData(sheet, column, 19, "Pass");
			xls.setCellData(sheet, column - 1, 19, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 19, "Fail");
			xls.setCellData(sheet, column - 1, 19, System.getProperty("user.name"));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Welcome')]")).isDisplayed(),
				"Could not Sign Out");
		System.out.println("SUCCESS, HURREYY");
	}

	@AfterClass
	public void sendEmailReport() {

		emailReport rept = new emailReport();
		rept.sendEmail(this.getClass().getSimpleName(), "Housing_Only");

	}
}
