package testNG_1;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testNg1 {
	
	String url;
	String userName;
	String password;
	String browser;
	
	
	String dashboardvalidationtext = "Dashboard";
	String userAlertValidationText = "Please enter your user name";
	String passwordAlertValidationText = "Please enter your password";
	String addCustomerValidationText = "New Customer";
	String fullname = "Techfios123";
	String company = "Walgreen";
	String email = "abc@hotmail.com";
	String phone = "123456";
	String address1 = "Plano";
	String address2 = "Texas";
	String zipcode = "75070";
	String country = "Algeria";
	String group = "SDLC";
	
	
	
	
	
	WebDriver driver;

	By USER_NAME_FIELD = By.xpath("//input[@id='user_name']");
	By USER_PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By USER_SUBMIT_FIELD = By.xpath("//button[@id='login_submit']");
	By DASHBOARD_VALIDATION_FIELD = By.xpath("//strong[contains(text(),'Dashboard')]");
	By CUSTOMER_MENU_FIELD = By.xpath("//body[1]/div[1]/aside[1]/div[1]/nav[1]/ul[2]/li[2]/a[1]/span[1]");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(),'Add Customer')]");
	By ADD_CUSTOMER_VALIDATION_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div[1]/div[1]/div/div/header/div/strong");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
    By dropdownCompany = By.xpath("//*[@id=\"general_compnay\"]/div[2]/div/select");
    By Email_Field = By.xpath("//*[@id=\"general_compnay\"]/div[3]/div/input");
    By Phone_Field = By.xpath("//*[@id=\"phone\"]");
    By Add1_Field = By.xpath("//*[@id=\"general_compnay\"]/div[5]/div/input"); 
    By Add2_Field = By.xpath("//*[@id=\"general_compnay\"]/div[6]/div/input");
    By Zipcode_Field = By.xpath("//*[@id=\"port\"]");
    By dropdownCountry = By.xpath("//*[@id=\"general_compnay\"]/div[8]/div[1]/select");
    By dropdownGroup = By.xpath("//*[@id=\"customer_group\"]");

	
	@BeforeClass
	public void readConfig() {
		
		try {
			
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			
			Properties prop = new Properties();
			prop.load(input);
			
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
			userName = prop.getProperty("userName");
			password = prop.getProperty("password");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

	@BeforeMethod
	public void init() {  
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver","driver\\chromedriver.exe");
			driver = new ChromeDriver();			
		}
		
		else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver","driver\\msedgedriver.exe");
			driver = new EdgeDriver();	
				}
		
		else {
			System.out.println("Please define a valid browser.");
		}

		
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

//	@Test
	public void positiveLoginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(USER_PASSWORD_FIELD).sendKeys(password);
		driver.findElement(USER_SUBMIT_FIELD).click();

		Assert.assertEquals(dashboardvalidationtext , driver.findElement(DASHBOARD_VALIDATION_FIELD).getText(), "Expected Page Not Found");

	}
	
	
//	@Test
	public void atestAlert() {
		driver.findElement(USER_SUBMIT_FIELD).click();
		Assert.assertEquals(driver.switchTo().alert().getText(),userAlertValidationText , "Alert is not available");
		driver.switchTo().alert().accept();
		
		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(USER_SUBMIT_FIELD).click();
		Assert.assertEquals(driver.switchTo().alert().getText(), passwordAlertValidationText, "Alert is not available!");
		driver.switchTo().alert().accept();
		
	}
	
	
//	@AfterMethod
	
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
	@Test
	public void addcustomer() {
		positiveLoginTest();
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		Assert.assertEquals(driver.findElement(ADD_CUSTOMER_VALIDATION_FIELD).getText(), addCustomerValidationText, "Page Not Found");
		
		driver.findElement(FULL_NAME_FIELD).sendKeys(fullname + generateRandomNum(99));
		
		
	//	Select select = new Select(driver.findElement(dropdownCompany));		
	//  select.selectByVisibleText(company);
		
	    dropdownmenu(dropdownCompany, company);
		
	    
	    driver.findElement(Email_Field).sendKeys( generateRandomNum(99) + email);
	    driver.findElement(Phone_Field).sendKeys(phone +  generateRandomNum(9999));
	    driver.findElement(Add1_Field).sendKeys(address1);
	    driver.findElement(Add2_Field).sendKeys(address2);
	    driver.findElement(Zipcode_Field).sendKeys(zipcode);
	    
	    
 
	    
  //      Select select11 = new Select(driver.findElement(dropdownCountry));
		
//		select11.selectByVisibleText(country);
	    dropdownmenu(dropdownCountry, country);
	    
	    dropdownmenu(dropdownGroup, group);
		
		

	//	Select select2 = new Select(driver.findElement(dropdownGroup));
		
	//	select2.selectByVisibleText(group);	
	    
	    
		
	}
	
	private void dropdownmenu(By elementField, String visibleText) {
		
		Select select = new Select(driver.findElement(elementField));		
	    select.selectByVisibleText(visibleText);
				
	}
	
	public int generateRandomNum(int boundNum) {
		Random rnd = new Random();
		int randomNum = rnd.nextInt(boundNum);
		return randomNum;
	}
	
	


} 
