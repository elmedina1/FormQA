package windows;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
Test case
1. go to http://demoqa.com/automation-practice-form
2. click on Alerts, Frames & Windows menu
3. click on Browser Windows menu item
4. click on "new tab" button
5.get url of all open tabs
6. close second tab
 * 
 */
public class WindowsTest {

		
		private WebDriver driver;
		public static void main(String[] args) throws InterruptedException {
			
			WindowsTest wind = new WindowsTest();
			wind.setUp(); // call SetUp() method
			wind.expandLeftMenu("Alerts, Frame & Windows");//expands Alerts, Frame & Windows left menu
			Thread.sleep(3000);
	     	wind.clickSubMenuItem("Browser Windows"); 
			Thread.sleep(3000);
	
		}
		
		

		public void setUp() {
			String dir = System.getProperty("user.dir");
			// setting webdriver.chrome.driver property to location of chromedriver.exe
			System.setProperty("webdriver.chrome.driver", dir + "\\executable\\chromedriver.exe");
			driver = new ChromeDriver(); // Instantiate chromedriver
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // set implicit wait
			driver.manage().window().maximize(); // maximizes browser window
			driver.get("http://demoqa.com/automation-practice-form"); // open specify url
		}
			
		public void expandLeftMenu(String menuItem) {
			
			WebElement sub = driver.findElement(By.xpath("//div[@class=\"header-text\"][contains(.,\""+menuItem+"\")]"));
			String script = "arguments[0].scrollIntoView()";
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript(script,sub );
		sub.click();
		}

		public void clickSubMenuItem(String subItem ) {
			
			WebElement sub = driver.findElement(By.xpath("//div[contains(@class,\"show\")]//li[contains(.,\""+subItem+"\")]"));
			String script = "arguments[0].scrollIntoView()";
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript(script,sub );
		sub.click();
		}

		

	 

	 
}


		


