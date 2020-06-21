package form;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/* 
Automate following Test Steps:
1.	Open http://demoqa.com/automation-practice-form
2.	Enter FistName, LastName, Email
3.	Verify the number of radio buttons  is equal to 3 
4.	Verify that by default none of radio button is selected
5.	Select Female as gender
6.	Enter phone Number
7.	Enter Date Of Birth
8.	Fill in Subject - Science
// ZADACA use ArrorKeyDown to select 4th element in  autocomplete list when user enters "m" in subject input
9.	Verify that number of checkboxes is 3 ZADACA
10.	Verify that all checkbox are unchecked ZADAVA
11.	Check Sport and Music ZADACA
12.	Upload file
13.	Enter Current Address ZADACA
14.	Verify that select city is disabled ZADACA
15.	Verify  the content of State dropdown
16.	Select the state NCR and Gurgaon
17.	Click on Submit button
18.	Get student name from modal ZADACA
19.	Close modal ZADACA

*/

public class Form {

	private WebDriver driver; // declare driver

	public static void main(String[] args) throws InterruptedException {
		/*
		 * create formy object which is instance of Form class. Need to do this in order
		 * to access Form class method from inside the main method
		 */
		Form formy = new Form();
		formy.SetUp(); // call SetUp() method

		/*
		 * fillInField(id_elementa, value) - first parameter is id of element and second
		 * is value which we want to sent
		 */
		formy.fillInField("firstName", "amina"); // fills in First Name field
		formy.fillInField("lastName", "Prezime"); // fills in Last Name field
		formy.fillInField("userEmail", "amina.pez@gmail.com"); // fills in email field
		int number = formy.getAllRadioBtn("radio"); // saves number of found radio buttons to variable number
		Assert.assertEquals(number, 3, "Number of radiobuttons is not 3"); // check if number of element script found is
																			// same as we expected

		boolean sel = formy.isRadioSelected("radio");
		Assert.assertFalse(sel, "Radio button is selected by default but it should not be");
		formy.clickRadioBtn("Female");
		formy.fillInField("userNumber", "1112223334");
		Thread.sleep(2000);

		formy.selectDOB("19 April 1965"); // use format which is the easiest to deal with
		String selcDate =formy.getDOB();
		System.out.println("Selected date is:" + selcDate);
		
		//Assert.assertEquals(formy.getDOB(), "19 Apr 1965", "Correct date is not selected");

		formy.selectSubject("subjectsInput", "Computer Science"); // select Computer Science as subject
		formy.uploadFile("C:\\Users\\esunje\\Desktop\\Eclipse_workspace\\FormyQADemo\\files\\nikitos.jpg"); // upload file with name nikitos
		// you can add here verification for showing file name after upload
		formy.scrollToChooseFileBtn("uploadPicture"); // scrolls to Choose Button

		formy.clickSubmitBtn("submit"); // click on submit button
		// Thread.sleep(3000); to see the modal
		// formy.tearDown();

	}

	// always have to have Setup method
	public void SetUp() {

		String dir = System.getProperty("user.dir");
		// setting webdriver.chrome.driver property to location of chromedriver.exe
		System.setProperty("webdriver.chrome.driver", dir + "\\executable\\chromedriver.exe");
		driver = new ChromeDriver(); // Instantiate chromedriver
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // set implicit wait
		driver.manage().window().maximize(); // maximizes browser window
		driver.get("http://demoqa.com/automation-practice-form"); // open specify url
	}

	/**
	 * method finds element and sends value to it
	 * 
	 * @param locator - value of id locator for specific element
	 * @param value   - value which will be sent to specifi element
	 */

	public void fillInField(String locator, String value) {

		driver.findElement(By.id(locator)).sendKeys(value);

	}

	/**
	 * method finds all radio button on the page
	 * 
	 * @param attValue - unique attribute for all radio buttons on the age
	 * @return number of found radio element
	 */
	public int getAllRadioBtn(String attValue) {

		// radioList contains all found radio elements
		List<WebElement> radioList = driver.findElements(By.cssSelector("[type=\"" + attValue + "\"]"));
		/*
		 * .size() - returns number of elements in the radioList list print out the how
		 * many element are in the list
		 */
		System.out.println("Number of radio buttons is:" + radioList.size());

		return radioList.size(); // return number of the elements
	}

	/**
	 * method checks if each radio buttons is selected or not by default
	 * 
	 * @param attValue - unique identifier for all radio buttons
	 * @return true - if any of element is checked, otherwise it returns false
	 */

	public boolean isRadioSelected(String attValue) {

		List<WebElement> radioList = driver.findElements(By.cssSelector("[type=\"" + attValue + "\"]"));
		// use loop to go though each element of the list
		for (int i = 0; i < radioList.size(); i++) {

			/*
			 * .get(int Index) - allows us to access specific element of the list by its
			 * index. Note: list starts with index 0
			 */
			if (radioList.get(i).isSelected()) {

				return true;
			}
		}
		return false;

	}

	public void clickRadioBtn(String gender) {

		driver.findElement(By.xpath("//label[contains(.,\"" + gender + "\")]")).click();
	}

	/**
	 * method takes date, splits it to day, month and year . Than it select value
	 * from specific dropdown
	 * 
	 * @param dateValue - date which we want to select
	 */
	public void selectDOB(String dateValue) {

		driver.findElement(By.id("dateOfBirthInput")).click();// to invoke calendar we have to click first on input
																// field

		// split dateValue by " " and save it to the array
		String[] date = dateValue.split(" ");
		String day = date[0]; // array starts at index 0, first element of array is value of the day
		String month = date[1];
		String year = date[2];

		System.out.println("dan" + day); // recheck if day contains correct value

		/*
		 * When dropdown has select tagname, than we can user Select class format:
		 * Select objectName = new Select(WebElement) WebElement is dropdown element and
		 * we need to pass location for it firt we are selecting Month
		 */
		Select selMonth = new Select(driver.findElement(By.className("react-datepicker__month-select")));
		// .selectByVisibleText() - use this method
		
		selMonth.selectByVisibleText(month);
		
		
		
		Select selYear = new Select(driver.findElement(By.className("react-datepicker__year-select")));
		selYear.selectByValue(year);

//In selecting date, we have to find all days from active month
		List<WebElement> activeDay = driver.findElements(By.xpath(
				"//div[starts-with(@class,\"react-datepicker__day react-datepicker__day\")][not(contains(@class, \"react-datepicker__day--outside-month\"))]"));
//find in list element that has text same as day and click on it
		for (int i = 0; i < activeDay.size(); i++) {
			if (activeDay.get(i).getText().contentEquals(day)) {
				activeDay.get(i).click();
				break; // once day is found and clicked, there is no need to continue with loop
			} // end of if

		} // end of loop

	}// end of method

// returns date from DOB field

	public String getDOB() {

		return driver.findElement(By.id("dateOfBirthInput")).getAttribute("value");

	}

	/**
	 * Method selects subject 
	 * 
	 * @param elementId - id of subject input field
	 * @param subject   - name of subject the will need to be selecter
	 * @throws InterruptedException
	 * 
	 */

	public void selectSubject(String elementId, String subject)  {
		driver.findElement(By.id(elementId)).sendKeys(subject);

		// need to sent enter in order to add subject to the input field
		driver.findElement(By.id(elementId)).sendKeys(Keys.ENTER);

	}



	/**
	 * method takes a file name and uploads it to the form
	 * 
	 * @param fileName- name of the file you want to upload
	 */

	public void uploadFile(String filePath) {

		//driver.findElement(By.id("uploadPicture")).sendKeys(System.getProperty("user.dir") + "\\files\\" + fileName); // find
																														// "Choose
		driver.findElement(By.id("uploadPicture")).sendKeys(filePath);																											// File"
																														// button

	}

	/**
	 * methods moves Choose File button to the top of the screen
	 * 
	 * @param fileName -name of the file to upload
	 * 
	 */

	public void scrollToChooseFileBtn(String elementId) throws InterruptedException {
		Thread.sleep(2000);
		WebElement chooseBtn = driver.findElement(By.id(elementId));
		//scroll Choose File button into the view
		String script = "arguments[0].scrollIntoView()";
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript(script, chooseBtn);
	}

	/**
	 * methods click on submit button
	 * 
	 * @param elementId - submit button id
	 */

	public void clickSubmitBtn(String elementId) {

		
		WebElement element = driver.findElement(By.id(elementId));
		//before clicking on element, scroll it into the view 
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		element.click();

	}

	
	
	public void teatDown() {
		driver.quit();
	}
	

}// end of class
