package WebRegFlows;

import java.io.File;
import java.util.List;
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
import org.testng.asserts.SoftAssert;

import Util.ConfigReader;
import Util.LatestFile;
import Util.WebRegUtil;
import Util.XlsUtil;
//import Util.XlsUtil;
import Util.emailReport;

public class ExhibitorBlock {

	// Set the Chrome Download Path here
	String username = System.getProperty("user.name");
	String downloadPath = "C:\\Users\\" + username + "\\Downloads";

	WebDriver driver;
	ConfigReader config = new ConfigReader();
	WebDriverWait wait;
	int RND;
	Select sel;
	String sheet = "ExhibitorBlock";
	int column = 6;
	JavascriptExecutor jse;
	XlsUtil xls = new XlsUtil(config.getwebregexcelpath());
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public int RandomNo() {
		RND = (int) (Math.random() * 10000);
		return RND;
	}

	@Test(priority = 0)
	public void exhibitorMainFlow() throws Exception {

		String firstName = "FN" + RND;
		String lastName = "LN" + RND;

		System.setProperty("webdriver.chrome.driver", config.getChromeFilePath());
		driver = new ChromeDriver();
		JavascriptExecutor jse = (JavascriptExecutor) driver; // Initializing // Java Script// Executer

		wait = new WebDriverWait(driver, 70);
		driver.manage().window().maximize();
		
		// Get the URL from config file and append showcode
		StringBuffer newurl = new StringBuffer(config.getFlowpath());
		newurl.append(config.getshowcode());
		driver.get(newurl.toString());

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		// Click on the Flow Name
		jse.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//a[normalize-space()='" + config.getexhiblkflowname() + "']")));
		driver.findElement(By.xpath("//a[normalize-space()='" + config.getexhiblkflowname() + "']")).click();
		Thread.sleep(1000);

		// Validate the Company Search Page and write it to Excel sheet
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='Search']")));
		int CompanySearchPage = driver.findElements(By.xpath("//button[normalize-space()='Search']")).size();
		if (CompanySearchPage > 0) {
			xls.setCellData(sheet, column, 3, "Pass");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 3, "Fail");
			xls.setCellData(sheet, column - 1, 3, System.getProperty("user.name"));
		}

		Assert.assertTrue(CompanySearchPage > 0, "Failed to launch Company Search page");

		// Enter the Company Name and click Search button
		driver.findElement(By.xpath("//form[contains(@name,'CompanySearch')]/input")).sendKeys(config.getcompanyName());
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

		// Validate for the Search Result
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//button[normalize-space()='" + config.getcompanyName() + "']")));

		int CompanySearchResult = driver
				.findElements(By.xpath("//button[normalize-space()='" + config.getcompanyName() + "']")).size();
		if (CompanySearchResult > 0) {
			xls.setCellData(sheet, column, 4, "Pass");
			xls.setCellData(sheet, column - 1, 4, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 4, "Fail");
			xls.setCellData(sheet, column - 1, 4, System.getProperty("user.name"));
		}

		Assert.assertTrue(CompanySearchResult > 0, "Failed to launch Company Search Results");

		// Click on the Company Name from results
		driver.findElement(By.xpath("//button[normalize-space()='" + config.getcompanyName() + "']")).click();

		// Validate Page appears with Password box.
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']")));
		int passwordBox = driver.findElements(By.xpath("//input[@type='password']")).size();
		if (passwordBox > 0) {
			xls.setCellData(sheet, column, 5, "Pass");
			xls.setCellData(sheet, column - 1, 5, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 5, "Fail");
			xls.setCellData(sheet, column - 1, 5, System.getProperty("user.name"));
		}
		Assert.assertTrue(passwordBox > 0, "Password Box was not appeared");

		// Enter Password and click
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(config.getcompanyPassword());
		driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();

		// Enter details if 'Contact Profile' page is loaded
//		if (driver.findElement(By.xpath("//span[contains(text(),'Contact Profile')]")).isDisplayed())  {
		
		if (driver.findElements(By.xpath("//span[contains(text(),'Contact Profile')]")).size() > 0) {

			driver.findElement(By.id("firstName")).sendKeys("FN_Cont" + RND);
			driver.findElement(By.id("lastName")).sendKeys("LN_Cont" + RND);
			driver.findElement(By.id("address")).sendKeys("Test Address");
			driver.findElement(By.id("zipCode")).clear();
			driver.findElement(By.id("zipCode")).sendKeys("22222", Keys.TAB);
			driver.findElement(By.id("email")).sendKeys("contemail" + RND + "@gmail.com");
			driver.findElement(By.xpath("//a[contains(.,'Terms of Use')]/preceding-sibling::input")).click();
			driver.findElement(By.xpath("//button//span[contains(text(),'Save ')]")).click();
		}

		// Validate Dashboard appears
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Manage Group')]")));
		int dashboard = driver.findElements(By.xpath("//button[contains(text(),'Manage Group')]")).size();
		if (dashboard > 0) {
			xls.setCellData(sheet, column, 6, "Pass");
			xls.setCellData(sheet, column - 1, 6, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 6, "Fail");
			xls.setCellData(sheet, column - 1, 6, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 6, "Dashboard was not loaded");
		}

		Assert.assertTrue(dashboard > 0, "Dashboard was not loaded");

		// Click on Add/Edit Personnel
		driver.findElement(By.xpath("//button[contains(text(),'Manage Group')]")).click();

		// Validate Group Summary Page
		driver.manage().timeouts().pageLoadTimeout(70, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Group Summary')]")));
		int groupSummaryPage = driver.findElements(By.xpath("//span[contains(text(),'Group Summary')]")).size();
		if (groupSummaryPage > 0) {
			xls.setCellData(sheet, column, 7, "Pass");
			xls.setCellData(sheet, column - 1, 7, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 7, "Fail");
			xls.setCellData(sheet, column - 1, 7, System.getProperty("user.name"));
		}

		Assert.assertTrue(dashboard > 0, "Group Summary was not loaded");

		// Add New Person
		driver.findElement(By.xpath("//span[contains(text(),'Add New Person')]")).click();
		Thread.sleep(1000);

		// Enter Details and click ADD button
		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.xpath("//button[contains(.,'Badge Only')]")).click();
		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("email")));
		
		if (driver.findElements(By.xpath("(//span[text()='Please Select'])[1]")).size()>0) {
			
			driver.findElement(By.xpath("(//span[text()='Please Select'])[1]")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[normalize-space()='Select a Registration Type']")));
			driver.findElement(By.xpath("//div[contains(text(),'Exhibitor')]")).click();
//			driver.findElement(By.xpath("//div[contains(text(),'Exhibitor')]")).sendKeys(Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[text()='Continue']")).click();								

		}	
		
//		driver.findElement(By.xpath("(//span[text()='Please Select'])[1]")).click();
//		wait.until(ExpectedConditions
//				.presenceOfElementLocated(By.xpath("//h2[normalize-space()='Select a Registration Type']")));
//		driver.findElement(By.xpath("//div[contains(text(),'Exhibitor')]")).click();

		Actions action = new Actions(driver);
//		action.moveToElement(driver.findElement(By.xpath("//button[text()='Continue']"))).build().perform();

//		action.sendKeys(Keys.DOWN, Keys.DOWN).build().perform();
		Thread.sleep(2000);
		// action.sendKeys(Keys.ARROW_DOWN).build().perform();
//		action.sendKeys(Keys.TAB, Keys.ENTER).build().perform();

		// driver.findElement(By.xpath("//button[text()='Continue']")).click();
		Thread.sleep(1000);
		// action.moveToElement(driver.findElement(By.xpath("//span[text()='Add']"))).build().perform();
		// action.sendKeys(Keys.DOWN, Keys.DOWN,
		// Keys.DOWN,Keys.DOWN,Keys.DOWN).build().perform();
//		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("firstName")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Add']")).click();

		// Validate the Person is added
		int personAdded = driver.findElements(By.xpath("//strong[text()='" + firstName + "']")).size();
		if (personAdded > 0) {
			xls.setCellData(sheet, column, 8, "Pass");
			xls.setCellData(sheet, column - 1, 8, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 8, "Fail");
			xls.setCellData(sheet, column - 1, 8, System.getProperty("user.name"));
		}

		softAssert.assertTrue(dashboard > 0, "Add New Person was failed");
		// Keep adding to reach Booth Allotment limit and Validate Payment
		// option for additional person
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys("FN1" + RND);
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("LN1" + RND);
		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("firstName")));
		// action.moveToElement(driver.findElement(By.xpath("//span[text()='Add']"))).build().perform();
		// action.sendKeys(Keys.DOWN,Keys.DOWN,Keys.DOWN,Keys.DOWN,Keys.DOWN).build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Add']")).click();
		Thread.sleep(2000);

		// Check that Payment option has not appeared yet.
		int poBeforeLimit = driver.findElements(By.xpath("//span[text()='Balance Due']")).size();
		if (poBeforeLimit == 0) {
			xls.setCellData(sheet, column, 9, "Pass");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 9, "Fail");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 9, "Payment Option appeared before exceeding booth allotment limit");
		}

		// Add one more person to exceed Booth Allotment limit and validate
		// payment option appears
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys("FN2" + RND);
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("LN2" + RND);
		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("firstName")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Add']")).click();
		Thread.sleep(2000);

		int poAfterLimit = driver.findElements(By.xpath("//span[text()='Balance Due']")).size();
		if (poAfterLimit > 0) {
			xls.setCellData(sheet, column, 9, "Pass");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 9, "Fail");
			xls.setCellData(sheet, column - 1, 9, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 9,
					"Payment Option did not appear after exceeding booth allotment limit");
		}

		// Import Personnel
		driver.navigate().refresh();

		jse.executeScript("scroll(0, 0);"); // This line will scroll to top of
											// the page

		driver.findElement(By.xpath("//span[contains(text(),'Import Personnel')]")).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Download Template')]")));
		driver.findElement(By.xpath("//span[contains(text(),'Download Template')]")).click();
		Thread.sleep(5000);

		String excelPath = LatestFile.lastFileModified(downloadPath).getAbsolutePath();
		XlsUtil templateUtil = new XlsUtil(excelPath);
		for (int row = 2; row <= 4; row++) {

			templateUtil.setCellData("Contacts", 0, row, "FN4" + RND + row + "");
			templateUtil.setCellData("Contacts", 1, row, "LN4" + RND + row + "");
			templateUtil.setCellData("Contacts", 2, row, config.getcompanyName());
			templateUtil.setCellData("Contacts", 3, row, "TESTFN4" + RND + row + "@infinite.com");
			templateUtil.setCellData("Contacts", 4, row, "E");

		}

		driver.findElement(By.xpath("//input[@name='file' and @type='file']")).sendKeys(excelPath);
		jse.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//span[contains(text(),'Download Template')]")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Upload Spreadsheet']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Continue']")).click();

		// Validate the contacts were added successfully
		int importPerson = driver.findElements(By.xpath("//td[contains(*,'TESTFN4')]")).size();
		if (importPerson > 0) {
			xls.setCellData(sheet, column, 10, "Pass");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 10, "Fail");
			xls.setCellData(sheet, column - 1, 10, System.getProperty("user.name"));
		}
		softAssert.assertTrue(dashboard > 0, "Import Personnel was failed");

		// Export Personnel
		driver.navigate().refresh();
		jse.executeScript("scroll(0, 0);"); // This line will scroll to top of
											// the page

		driver.findElement(By.xpath("//span[contains(text(),'Export Personnel')]")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//span[contains(text(),'Registration Export')]")).click();
		Thread.sleep(1000);

		boolean err = checkErrorOnPage();
		if (err) {
			xls.setCellData(sheet, column, 11, "Pass");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 11, "Fail");
			xls.setCellData(sheet, column - 1, 11, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 11, "Registration Export showed Error on page");
		}
		softAssert.assertTrue(err, "Registration Export was failed");

		driver.findElement(By.xpath("//span[contains(text(),'Export Group')]")).click();
		Thread.sleep(1000);

		boolean err1 = checkErrorOnPage();
		if (err1) {
			xls.setCellData(sheet, column, 12, "Pass");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 12, "Fail");
			xls.setCellData(sheet, column - 1, 12, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 12, "Export Group showed Error on page");
		}
		softAssert.assertTrue(err1, "Export Group was failed");

		driver.findElement(By.xpath("//span[contains(text(),'Export All')]")).click();
		Thread.sleep(1000);

		boolean err2 = checkErrorOnPage();
		if (err1) {
			xls.setCellData(sheet, column, 13, "Pass");
			xls.setCellData(sheet, column - 1, 13, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 13, "Fail");
			xls.setCellData(sheet, column - 1, 13, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 13, "Export All showed Error on page");
		}
		softAssert.assertTrue(err2, "Export Group was failed");

		// Validate Filter
		driver.navigate().refresh();
		jse.executeScript("scroll(0, 0);"); // This line will scroll to top of
											// the page
		driver.findElement(By.xpath("//span[contains(text(),'Filter')]")).click();
		driver.findElement(By.xpath("//input[@name='filter.company']")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//span[text()='First Name']/ancestor::div/input")).sendKeys(firstName);
		Thread.sleep(1000);

		int searchResult = driver.findElements(By.xpath("//strong[text()='" + firstName + "']")).size();
		if (searchResult > 0) {
			xls.setCellData(sheet, column, 14, "Pass");
			xls.setCellData(sheet, column - 1, 14, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 14, "Fail");
			xls.setCellData(sheet, column - 1, 14, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 14, "First Name search result was not found");
		}
		softAssert.assertTrue(searchResult > 0, "Filter was failed");

		// Validate record update
		jse.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//input[@name='filter.firstName']")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//strong[text()='" + firstName + "']/ancestor::tr/td/button")).click();
		Thread.sleep(1000);

		String firstNameUpdated = "FN2" + RND + "Updated";
		driver.findElement(By
				.xpath("//span[text()='First Name']/ancestor::form[contains(@name,'personnelProfileEditCtrl')]//input[@id='firstName']"))
				.clear();
		driver.findElement(By
				.xpath("//span[text()='First Name']/ancestor::form[contains(@name,'personnelProfileEditCtrl')]//input[@id='firstName']"))
				.sendKeys(firstNameUpdated);
		Thread.sleep(1000);

		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("lastName")));
		action.sendKeys(Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB).build().perform();
		driver.findElement(By.xpath("//span[text()='Update']")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='First Name']/ancestor::div/input")).clear();
		driver.findElement(By.xpath("//span[text()='First Name']/ancestor::div/input")).sendKeys(firstNameUpdated);
		Thread.sleep(1000);

		int searchResult1 = driver.findElements(By.xpath("//strong[text()='" + firstNameUpdated + "']")).size();
		if (searchResult1 > 0) {
			xls.setCellData(sheet, column, 15, "Pass");
			xls.setCellData(sheet, column - 1, 15, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 15, "Fail");
			xls.setCellData(sheet, column - 1, 15, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 15, "First Name was not updated");
		}
		softAssert.assertTrue(searchResult1 > 0, "First Name was not updated");

		// Validate Remove Registration
		jse.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//input[@name='filter.company']")));
		driver.findElement(By.xpath("//span[text()='Remove Registration']/ancestor::button")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[text()='Yes']")).click();
		Thread.sleep(1000);

		int removeReg = driver.findElements(By.xpath("//span[text()='Register']")).size();
		if (removeReg > 0) {
			xls.setCellData(sheet, column, 16, "Pass");
			xls.setCellData(sheet, column - 1, 16, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 16, "Fail");
			xls.setCellData(sheet, column - 1, 16, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 16, "Registration was not removed");
		}

		// Add registration again and validate
		jse.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//input[@name='filter.company']")));
		if (driver.findElements(By.xpath("//button/span[contains(.,'Cancelled')]")).size() > 0) {
			driver.findElement(By.xpath("//button/span[contains(.,'Cancelled')]")).click();
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("(//span[contains(.,'Cancelled')])[2]/following-sibling::button")));
			driver.findElement(By.xpath("(//span[contains(.,'Cancelled')])[2]/following-sibling::button")).click();
		} else

		{
			action.moveToElement(driver.findElement(By.xpath("//span[text()='Register']"))).build().perform();
			driver.findElement(By.xpath("//span[text()='Register']")).click();
			Thread.sleep(1000);
		}

		// driver.findElement(By.xpath("//span[text()='Register']")).click();
		Thread.sleep(1000);
		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("(//input[@id='email'])[2]")));
		driver.findElement(By.xpath("//span[text()='Update']/ancestor::button")).click();
		Thread.sleep(1000);

		int addReg = driver.findElements(By.xpath("//span[text()='Remove Registration']/ancestor::button")).size();
		if (addReg > 0) {
			xls.setCellData(sheet, column, 17, "Pass");
			xls.setCellData(sheet, column - 1, 17, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 17, "Fail");
			xls.setCellData(sheet, column - 1, 17, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 17, "Registration was not added");
		}

		// Click and go to Dashboard and validate
		driver.navigate().refresh();
		
		jse.executeScript("scroll(0, -250);"); 
		
//		jse.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.xpath("(//span[text()='Dashboard'])[2]")));
		driver.findElement(By.xpath("(//span[text()='Dashboard'])[2]")).click();
		
		Thread.sleep(1000);

		int dashbd = driver.findElements(By.xpath("//button[contains(text(),'Manage Group')]")).size();
		if (dashbd > 0) {
			xls.setCellData(sheet, column, 18, "Pass");
			xls.setCellData(sheet, column - 1, 18, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 18, "Fail");
			xls.setCellData(sheet, column - 1, 18, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 18, "Dashboard was not loaded");
		}

		// Hotel Reservation
		// Click on Add Room and Validate Room Search Page
		jse.executeScript("scroll(0, 0);"); // This line will scroll to top of
											// the page
		driver.findElement(By.xpath("//button[contains(text(),'Hotel Reservation')]")).click();
		Thread.sleep(1000);

		int roomSearch = driver.findElements(By.xpath("//span[contains(text(),'Room Search')]")).size();
		if (roomSearch > 0) {
			xls.setCellData(sheet, column, 19, "Pass");
			xls.setCellData(sheet, column - 1, 19, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 19, "Fail");
			xls.setCellData(sheet, column - 1, 19, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 19, "Room Search was not loaded");
		}

		// click Search Hotels and Validate Room Search page opens with all the
		// hotels configured
//		driver.findElement(By.xpath("//button[text()[contains(.,'Search Hotels')]]")).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='View Rooms']")));
		
		int viewRooms = driver.findElements(By.xpath("//button[text()='View Rooms']")).size();
		if (viewRooms > 0) {
			xls.setCellData(sheet, column, 20, "Pass");
			xls.setCellData(sheet, column - 1, 20, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 20, "Fail");
			xls.setCellData(sheet, column - 1, 20, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 20, "Hotels configured were not loaded");
		}

		// Click View Rooms button of any of the hotel and Validate Room Choice
		// Page was loaded
		driver.findElement(By.xpath("//button[text()='View Rooms']")).click();

		int roomChoice = driver.findElements(By.xpath("//span[contains(text(),'Room Choice')]")).size();
		if (roomChoice > 0) {
			xls.setCellData(sheet, column, 21, "Pass");
			xls.setCellData(sheet, column - 1, 21, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 21, "Fail");
			xls.setCellData(sheet, column - 1, 21, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 21, "Room Choice page was not loaded");
		}

		// Click on each tabs - Maps-Amenities, Cancellation and Reserve Rooms
		// and Validate all the tabs open without any error
		List<WebElement> list = driver.findElements(By.xpath("//li[contains(@role,'button')]"));
		int failed = 0;
		StringBuffer allErrors = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {

			List<WebElement> list1 = driver.findElements(By.xpath("//li[contains(@role,'button')]"));
			String errorTabName = list1.get(i).getText();
			list1.get(i).click();
			if (checkErrorOnPage()) {
				System.out.println(" Clicked  " + errorTabName);
			} else {
				allErrors.append(errorTabName + " has error");
				failed++;
			}
		}

		if (failed == 0) {
			xls.setCellData(sheet, column, 22, "Pass");
			xls.setCellData(sheet, column - 1, 22, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 22, "Fail");
			xls.setCellData(sheet, column - 1, 22, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 22, allErrors.toString());
		}

		// Click Reserve button and I Agree to the cancellation Policy" and
		// Validate Room Occupants page
		jse.executeScript("arguments[0].scrollIntoView()",
				driver.findElement(By.xpath("//li[contains(@role,'button')]")));
		driver.findElement(By.xpath("//button[contains(text(),'Reserve')]")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[contains(.,'I agree to the cancellation policy')]")).click();
		Thread.sleep(1000);

		int roomOccupants = driver.findElements(By.xpath("//span[contains(text(),'Room Occupants')]")).size();
		if (roomOccupants > 0) {
			xls.setCellData(sheet, column, 23, "Pass");
			xls.setCellData(sheet, column - 1, 23, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 23, "Fail");
			xls.setCellData(sheet, column - 1, 23, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 23, "Room Occupants page was not loaded");
		}

		// Click Add Guest and Validate Create New Form below the Add Guest
		// button
		driver.findElement(By.xpath("//button[contains(.,'Add Person')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//exl-select-list//li[1]/button")).click();
		jse.executeScript("scroll(0,-250);");
		driver.findElement(By.xpath("//button[contains(.,'Select')]")).click();

		int saveButton = driver.findElements(By.xpath("//button[contains(.,'Save')]")).size();
		if (saveButton > 0) {
			xls.setCellData(sheet, column, 24, "Pass");
			xls.setCellData(sheet, column - 1, 24, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 24, "Fail");
			xls.setCellData(sheet, column - 1, 24, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 24, "Guest form was not loaded");
		}

		// Enter all mandatory details. Save and Validate Guest to be added
		driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();
		Thread.sleep(1000);
		if (checkErrorOnPage()) {
			xls.setCellData(sheet, column, 25, "Pass");
			xls.setCellData(sheet, column - 1, 25, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 25, "Fail");
			xls.setCellData(sheet, column - 1, 25, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 25, "Guest was not added");
		}

		// Click Next and Validate DashBoard Appear
		driver.findElement(By.xpath("//button[contains(.,'Next: Dashboard')]")).click();
		Thread.sleep(1000);

		int dashbod = driver.findElements(By.xpath("//button[contains(text(),'Manage Group')]")).size();
		if (dashbod > 0) {
			xls.setCellData(sheet, column, 26, "Pass");
			xls.setCellData(sheet, column - 1, 26, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 26, "Fail");
			xls.setCellData(sheet, column - 1, 26, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 26, "Dashboard was not loaded");
		}

		// Click Pay Now and validate Payment Options page
		jse.executeScript("scroll(0, -250);"); // This line will scroll to bottom of the page
		

		driver.findElement(By.xpath("//button//span[text()='Pay Now']")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(.,'Payment Options')])[2]")));
		
		jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("(//h2[contains(.,'Payment')])[1]")));
		int paymentOptionsPage = driver.findElements(By.xpath("//span//strong[contains(text(),'I authorize')]")).size();
		if (paymentOptionsPage > 0) {
			xls.setCellData(sheet, column, 27, "Pass");
			xls.setCellData(sheet, column - 1, 27, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 27, "Fail");
			xls.setCellData(sheet, column - 1, 27, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 27, "Payment Options Page was not loaded");
		}

		// Click I authorize and complete Payment and Validate Completed Personnel Page		
		
		driver.findElement(By.xpath("//span//strong[contains(text(),'I authorize')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Pay Now')]")).click();

		WebRegUtil util = new WebRegUtil();
		boolean paymentResult = util.paymentExhibitor(driver);

		if (paymentResult) {
			xls.setCellData(sheet, column, 28, "Pass");
			xls.setCellData(sheet, column - 1, 28, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 28, "Fail");
			xls.setCellData(sheet, column - 1, 28, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 28, "Payment was not successul");
		}

		// Send Email
		driver.findElement(By.xpath("//span[contains(text(),'Send Confirmation')]")).click();
		boolean errSendEmail = checkErrorOnPage();
		if (errSendEmail) {
			xls.setCellData(sheet, column, 29, "Pass");
			xls.setCellData(sheet, column - 1, 29, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 29, "Fail");
			xls.setCellData(sheet, column - 1, 29, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 29, "Send Confirmation email showed Error on page");
		}

		// Click and get back to Dashboard and validate
		// jse.executeScript("arguments[0].scrollIntoView()",
		// driver.findElement(By.xpath("(//span[text()='Dashboard'])[2]")));
		jse.executeScript("scroll(0,-250);");
		driver.findElement(By.xpath("(//span[text()='Dashboard'])[2]")).click();
		Thread.sleep(1000);

		int dshboard = driver.findElements(By.xpath("//button[contains(text(),'Manage Group')]")).size();
		if (dshboard > 0) {
			xls.setCellData(sheet, column, 30, "Pass");
			xls.setCellData(sheet, column - 1, 30, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 30, "Fail");
			xls.setCellData(sheet, column - 1, 30, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 30, "Dashboard was not loaded");
		}

		// Validate Incomplete Registration to be 0
		String incompleteReg = driver.findElement(By.xpath("//span[text()='Incomplete']/ancestor::table//td[2]"))
				.getText();
		if (incompleteReg.contentEquals("0")) {
			xls.setCellData(sheet, column, 31, "Pass");
			xls.setCellData(sheet, column - 1, 31, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 31, "Fail");
			xls.setCellData(sheet, column - 1, 31, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 31, "Incomplete Registration was not 0");
		}

		// Click Signout and Validate
		driver.findElement(By.xpath("//exl-booking-selection//button")).click();
		driver.findElement(By.xpath("//span[text()='Sign Out']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Welcome')]")));

		int welcomePage = driver.findElements(By.xpath("//span[contains(text(),'Welcome')]")).size();
		if (welcomePage > 0) {
			System.out.println("Welcome page is loaded");
			xls.setCellData(sheet, column, 32, "Pass");
			xls.setCellData(sheet, column - 1, 32, System.getProperty("user.name"));
		} else {
			xls.setCellData(sheet, column, 32, "Fail");
			xls.setCellData(sheet, column - 1, 32, System.getProperty("user.name"));
			xls.setCellData(sheet, column + 1, 32, "Did not sign out");
		}
	}

	@AfterClass
	public void sendEmailReport() {

		emailReport rept = new emailReport();
		rept.sendEmail(this.getClass().getSimpleName(), "ExhibitorOnly");

	}

	// The below mthod will check for errors on page.
	public Boolean checkErrorOnPage() {
		boolean result = true;

		try {
			Assert.assertFalse(driver.getCurrentUrl().toLowerCase().contains("error"), "Page shows Appology Error");
			Assert.assertFalse(driver.getTitle().toLowerCase().contains("404"), "Page shows 404 Error");
			Assert.assertFalse(driver.getTitle().toLowerCase().contains("403"), "Page shows 403 Error");
			// Assert.assertFalse(driver.findElements(By.xpath("//*[starts-with(@*,'error')]")).size()>0,
			// "Page has Error");

		} catch (AssertionError ex) {
			result = false;
			driver.navigate().back();
		}
		return result;
	}
}