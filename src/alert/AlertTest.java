package alert;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * TEST CASE:
1. go to http://demoqa.com/automation-practice-form
2. click on Alerts, Frames & Windows menu
3. click on Alerts menu item
4. click on First "click me button", get alert text and accept alert
5. click on Second "click me button", get alert text and accept alert
6. click on Third "click me button", dismiss alert, get confirmation text
7. click on Fourth "click me button", sent text to alert, click on OK button in alert,  get confirmation text on the page
 */

public class AlertTest {

	private WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		AlertTest alert = new AlertTest();
		alert.setUp(); // call SetUp() method
		alert.expandLeftMenu("Alerts, Frame & Windows");//expands Alerts, Frame & Windows left menu
		Thread.sleep(3000);
		alert.clickSubMenuItem("Alerts"); //click on Alerts item in left menu
		alert.invokeAlertGetTextAndAccept("alertButton");
		alert.invokeSecondAlert("timerAlertButton");
		alert.dimissThirdAlert("confirmButton");
		alert.enterTextFourthAlert("promtButton");
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


public void invokeAlertGetTextAndAccept(String elementId) throws InterruptedException {
	
	driver.findElement(By.id(elementId)).click();

	String alertText = driver.switchTo().alert().getText();//gets alert text
	System.out.println("This is first alert text "+alertText);
	//Thread.sleep(2000);
	driver.switchTo().alert().accept();
}


public void invokeSecondAlert(String elementId) 
{
	
	driver.findElement(By.id(elementId)).click();
	//explicit wait
	WebDriverWait wait = new WebDriverWait(driver, 10);
	
	wait.until(ExpectedConditions.alertIsPresent());
	
	String alertText = driver.switchTo().alert().getText();//gets alert text
	System.out.println("This is second alert text "+alertText);
	//Thread.sleep(2000);
	driver.switchTo().alert().accept();

}


public void dimissThirdAlert(String elementId) {
   
	  driver.findElement(By.id(elementId)).click();
	  driver.switchTo().alert().dismiss();
	  String text = driver.findElement(By.id("confirmResult")).getText();
	  System.out.println("Text after dismiss is "+ text);

}

public void enterTextFourthAlert(String elementId) {

	
	 driver.findElement(By.id(elementId)).click();
	 driver.switchTo().alert().sendKeys("nahla");
	 
	 driver.switchTo().alert().accept();
	 String text = driver.findElement(By.id("promptResult")).getText();
	 System.out.println("Text entered to prompt "+ text);
	
	
}

}