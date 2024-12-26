package Tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObjects.DashboardPage;
import PageObjects.LoginPage;

public class LoginTest {

	public String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("Before Test executed");
		driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

	}
	
	@Test(priority  = 1, enabled=true)
	public void doLoginwithInvalidCredential() throws InterruptedException 
	{
		LoginPage pg = new LoginPage(driver);
		
		//enter username
		pg.setUserName("Admin");
		
		//enter password
		pg.setPassword("1234");
		
		//click on login button
		pg.clickLoginButton();
		
		String message_expected = "Invalid credentials";

		String message_actual = pg.getInvalidText();

		//Assert.assertTrue(message_actual.contains(message_expected));

		Assert.assertEquals(message_expected, message_actual);

		Thread.sleep(1500);

	}
	
	@Test(priority = 2, enabled=true)
	public void loginTestwithValidCredential()
	{
		LoginPage pg = new LoginPage(driver);
		
		DashboardPage dashboardPage = new DashboardPage(driver);
		
		//enter username
		pg.setUserName("Admin");
		
		//enter password
		pg.setPassword("admin123");
		
		//click on login button
		pg.clickLoginButton();
		
		//verify if the login was successful by checking the page title
		String pageTitle = dashboardPage.getPageTitle();

		//		if (pageTitle.equals("OrangeHRM")) {
		//			System.out.println("Login Successful");
		//		} else {
		//			System.out.println("Login Failed");
		//		}

		
		Assert.assertEquals("OrangeHRM", pageTitle);
	}
	
	@AfterTest
	public void tearDown() throws InterruptedException
	{
		//logOut();
		Thread.sleep(5000);
		driver.close();
		driver.quit();
	}
}
