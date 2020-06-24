package draganddrop;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/*
Test Case:
1. go to http://demoqa.com/automation-practice-form
2. click on Interactions
3. click on Droppable menu item
4. drag element to drop section
5. go to http://demo.guru99.com/test/simple_context_menu.html
6. right click on "right click me" button and click on "cut" option
 * 
 */

class DragAndDrop {

	private WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
		
		DragAndDrop drop = new DragAndDrop();
		drop.setUp(); // call SetUp() method
		drop.expandLeftMenu("Interactions");//expands Alerts, Frame & Windows left menu
		Thread.sleep(3000);
     	drop.clickSubMenuItem("Droppable");
   
		
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
