package lib;

import java.io.File;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ListIterator;
import java.util.Locale;
import java.awt.AWTException;
import java.awt.Robot;

import lib.Reporter.Status;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.framework.Globals;
import core.utils.Stock;

public class Web {
	public static WebDriver webdriver;
	public static Exception exception;
	private static Select objSelect;
	public static Robot robot;

	/**
	 * <pre>
	 * Method to check if element exists on the page and is displayed
	 * </pre>
	 * 
	 * @param element
	 *            - WebElement object which is to be check for
	 * @param waitForElement
	 *            - <b>true</b> if user wants to wait for the element before
	 *            checking if it is displayed. <b>false</b> otherwise
	 * @return <b>boolean - true</b> if element is displayed on the page.
	 *         <b>false</b> otherwise
	 */
	public static boolean isWebElementDisplayed(WebElement element,
			boolean... waitForElement) {
		boolean blnElementDisplayed = false;
		try {
			try {
				if (waitForElement.length > 0) {
					if (waitForElement[0] == true) {
						Web.waitForElement(element);
					}
				}

			} catch (Exception e) {
				// Do nothing
			}
			blnElementDisplayed = element.isDisplayed();
		} catch (NoSuchElementException e) {
			blnElementDisplayed = false;
		}
		return blnElementDisplayed;
	}

	/**
	 * <pre>
	 * Method to check if element exists on the page and is displayed
	 * </pre>
	 * 
	 * @param pageClassObj
	 *            - Object of the Page class
	 * @param fieldName
	 *            - Name of the web element as displayed in the page
	 * @return <b>boolean - true</b> if element is displayed on the page.
	 *         <b>false</b> otherwise
	 */
	public static boolean isWebElementDisplayed(Object pageClassObj,
			String fieldName) {
		boolean blnElementDisplayed = false;
		try {
			WebElement displayedElement = getPageObjectFields(pageClassObj,
					fieldName);
			try {
				Web.waitForElement(displayedElement);
			} catch (Exception e) {
				// Do Nothing
			}
			blnElementDisplayed = displayedElement.isDisplayed();
		} catch (NoSuchElementException e) {
			// throw new Error("Object: " + fieldName +
			// " not found in the page");
			blnElementDisplayed = false;
		}
		return blnElementDisplayed;
	}

	/**
	 * <pre>
	 * Method to set text to specified text box element.
	 * Returns value of <b>Value</b> attribute after setting provided text.
	 * Returns empty string if unable to set text.
	 * </pre>
	 * 
	 * @param textBoxFieldName
	 *            - Text box field name as displayed in the page
	 * @param pageObjectClass
	 *            - Object of the Page class
	 * @param valueToSet
	 *            - Value/String to be set
	 * @return <b>Value</b> - Text set in <b>value</b> attribute of text box.
	 */
	public static String setTextToTextBox(String textBoxFieldName,
			Object pageClassObj, CharSequence... valueToSet) {
		String fieldTextValue = "";
		if (textBoxFieldName != null) {
			WebElement textBoxElement = getPageObjectFields(pageClassObj,
					textBoxFieldName);
			if (Web.isWebElementDisplayed(textBoxElement)) {
				textBoxElement.clear();
				textBoxElement.sendKeys(valueToSet);
				fieldTextValue = textBoxElement.getAttribute("value");
			}
		}
		return fieldTextValue;
	}

	/** Method to return parent element of the specified element
	 * @param element
	 * @return WebElement - Parent element
	 */
	public static WebElement getParent(WebElement element) {
		return element.findElement(By.xpath("parent::*"));
	}


	/**
	 * <pre>
	 * Method to set text to specified text box element.
	 * Returns value of <b>Value</b> attribute after setting provided text.
	 * Returns empty string if unable to set text.
	 * </pre>
	 * 
	 * @param textBoxField
	 *            - Text box WebElement
	 * @param valueToSet
	 *            - Value/String to be set
	 * @return <b>Value</b> - Text set in <b>value</b> attribute of text box.
	 */
	public static String setTextToTextBox(WebElement textBoxField,
			CharSequence... valueToSet) {
		String fieldTextValue = "";
		if (Web.isWebElementDisplayed(textBoxField)) {
			textBoxField.clear();
			textBoxField.sendKeys(valueToSet);
			textBoxField.click();
			fieldTextValue = textBoxField.getAttribute("value");
		}
		return fieldTextValue;
	}

	/**
	 * Method to click on a specified web element.
	 * 
	 * @param pageClassObj
	 *            - Object of the Page class
	 * @param webElementName
	 *            - Name of the web element to be clicked
	 * @return <b>true</b> if successful, <b>false</b> otherwise.
	 */
	public static boolean clickOnElement(Object pageClassObj,
			String webElementName) {
		boolean success = false;
		if (webElementName != null) {
			WebElement clickableElement = getPageObjectFields(pageClassObj,
					webElementName);
			if (Web.isWebElementDisplayed(clickableElement)) {
				clickableElement.click();
				success = true;
			}
		}
		return success;
	}

	/**
	 * Method to click on a specified web element.
	 * 
	 * @param clickableElement
	 *            - Name of the web element to be clicked
	 * @return <b>true</b> if successful, <b>false</b> otherwise.
	 */
	public static boolean clickOnElement(WebElement clickableElement) {
		boolean success = false;
		if (Web.isWebElementDisplayed(clickableElement)) {
			clickableElement.click();
			success = true;
		}
		return success;
	}
	/**
	 * <pre>
	 * Method to get declared WebElement from a specified page.
	 * Returns corresponding <b>WebElement</b> from <b>getWebElement</b> method available in PageObject class.
	 * Returns null is case of no element found in the page
	 * </pre>
	 * 
	 * @param pageObjectClass
	 *            - Object of the Page class
	 * @param fieldName
	 *            - Name of the Element listed in getWebElement method
	 * @return <b>WebElement</b> - Corresponding WebElement mapped against the
	 *         fieldName
	 */
	private static WebElement getPageObjectFields(Object pageObjectClass,
			String fieldName) {
		Method getWebElementMethod = null;
		WebElement element = null;
		try {
			getWebElementMethod = pageObjectClass.getClass().getDeclaredMethod(
					"getWebElement", String.class);
		} catch (NoSuchMethodException e) {
			throw new Error("getWebElement() method is not found in "
					+ pageObjectClass.getClass().toString());
		}
		getWebElementMethod.setAccessible(true);
		try {
			element = (WebElement) getWebElementMethod.invoke(pageObjectClass,
					fieldName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}
	/**
	 * Method to wait for the specified element's presence
	 * 
	 * @param webElememnt
	 * @throws Exception
	 */
	public static void waitForElement(WebElement element) throws Exception {
		(new WebDriverWait(Web.webdriver, Long.parseLong(Stock.globalParam.get("objectSyncTimeout"))))
		.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Method to wait for the specified element's presence
	 * 
	 * @param webElememnt
	 * @throws Exception
	 */
	public static void waitForElement(Object pageClassObj, String webElementName)
			throws Exception {
		WebElement presentElement = getPageObjectFields(pageClassObj,
				webElementName);
		(new WebDriverWait(Web.webdriver, Long.parseLong(Stock.globalParam.get("objectSyncTimeout"))))
		.until(ExpectedConditions.visibilityOf(presentElement));
	}

	/**
	 * <pre>
	 * Method to initiate webDriver object based on used specified browser type
	 * Browser Types:
	 * 	1. <b>INTERNET_EXPLORER</b> or <b>IEXPLORE</b> or <b>IE</b>
	 * 	2. <b>FIREFOX</b> or <b>FF</b>
	 * 	3. <b>CHROME</b>
	 * </pre>
	 * 
	 * @param webBrowser
	 * @return
	 */
	public static WebDriver getWebDriver(String webBrowser) {
		WebDriver webDriver;

		try {
			robot = new Robot();
		} catch (AWTException e) { 
			e.printStackTrace();
		}


		if (webBrowser.trim().equalsIgnoreCase("INTERNET_EXPLORER")
				|| webBrowser.trim().equalsIgnoreCase("IEXPLORE")
				|| webBrowser.trim().equalsIgnoreCase("IE")) {
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability("ie.ensureCleanSession", true);
			System.setProperty("webdriver.ie.driver",
					Stock.globalParam.get("IEDriverClassPath"));
			webDriver = new InternetExplorerDriver(capabilities);

		} else if (webBrowser.trim().equalsIgnoreCase("CHROME")) {
			System.setProperty("webdriver.chrome.driver",
					Stock.globalParam.get("ChromeDriverClassPath"));
			webDriver = new ChromeDriver();
		} else if (webBrowser.trim().equalsIgnoreCase("FIREFOX")
				|| webBrowser.trim().equalsIgnoreCase("FF")) {
			ProfilesIni profiles = new ProfilesIni();
			FirefoxProfile ffProfile = profiles.getProfile("default");
			ffProfile.setPreference("signon.autologin.proxy", true);
			webDriver = new FirefoxDriver(ffProfile);
		} else {
			throw new Error("Unknown browser type specified: " + webBrowser);
		}

		return webDriver;
	}

	/**<pre> Method to verify two strings. 
	 * Method also takes care of reporting the result into the log file.
	 * Trims off white spaces at both the ends.
	 * If opted ignores case while comparing.</pre>
	 * @param inExpectedText
	 * @param inActualText
	 * @param ignoreCase - <b>true</b> or <b>false</b>
	 * <pre>	Pass <b>true</b> to ignore case while comparing. 
	 * 	Takes default value if this argument is vomited. 
	 * 	Default value: <b>false</b></pre>
	 * @return - boolean - true</b> if both the string matches. <b>false</b> otherwise
	 */
	public static boolean VerifyText(String inExpectedText,String inActualText, boolean... ignoreCase) {
		boolean isMatching = false;

		if (ignoreCase.length > 0) {
			if (ignoreCase[0]) {
				if (inExpectedText.trim().equalsIgnoreCase(inActualText.trim())) {
					isMatching = true;
				}
			} else {
				if (inExpectedText.trim().equals(inActualText.trim())) {
					isMatching = true;
				}
			}
		} else {
			if (inExpectedText.trim().equals(inActualText.trim())) {
				isMatching = true;
			}
		}
		return isMatching;
	}
	/**<pre> Method to verify partial text of two strings. 
	 * Trims off white spaces at both the ends.
	 * @param inPartialText
	 * @param inActualText
	 * @param ignoreCase
	 * @Default value: <b>false</b></pre>
	 * @return - boolean - true</b> if both the string matches. <b>false</b> otherwise
	 */
	public static boolean VerifyPartialText(String inPartialText,String inActualText,boolean ignoreCase) {
		boolean isMatching = false;
		if (ignoreCase && inActualText.trim().toLowerCase().contains(inPartialText.toLowerCase().trim())){
			isMatching = true;
		}else if (inActualText.trim().contains(inPartialText.trim())){
			isMatching = true;
		}             
		return isMatching;
	}


	//------------------------------------------------------------------------DROP DOWN Methods ----------------------------------------------------------------------	

	/**<pre> Method to initialize the Drop down object* </pre>
	 * @param pageObjectClass - Object of the Page class
	 * @param dropDownElementName - Name of the Dropdown element as displayed in the page
	 */
	private static void initDropDownObj(Object pageClassObj,String dropDownElementName){
		try {
			WebElement dropDownElement = getPageObjectFields(pageClassObj,dropDownElementName);
			if (Web.isWebElementDisplayed(dropDownElement)) {
				objSelect = new Select(dropDownElement);			
			}else {
				throw new NoSuchElementException("Unable to locate the drop down object " + dropDownElementName);
			}
		} catch (NoSuchElementException e) {
			throw new Error(e.getMessage());
		}
	}

	/**<pre> Method to initialize the Drop down object* </pre>
	 * @param dropDownElement - Dropdown WebElement
	 */
	private static void initDropDownObj(WebElement dropDownElement){
		try {
			if (Web.isWebElementDisplayed(dropDownElement)) {
				objSelect = new Select(dropDownElement);			
			}else {
				throw new NoSuchElementException("Unable to locate the drop down object " + dropDownElement.toString());
			}
		} catch (NoSuchElementException e) {
			throw new Error(e.getMessage());
		}
	}

	/**<pre> Method to get the item index in Dropdown* </pre>
	 * @param itemValue - Value of the item listed in Dropdown box
	 */
	private static int getDropDownItemIndex(String itemValue) {
		int iCntr = 0;
		boolean selected = false;

		ListIterator<WebElement> lstIter = objSelect.getOptions().listIterator();
		while (lstIter.hasNext()){
			WebElement currElement = lstIter.next();
			String txt = currElement.getText();
			if (txt.toUpperCase().contains(itemValue.toUpperCase())){
				selected = true;
				break;
			}
			iCntr++;
		}
		if (selected)
			return iCntr;
		else
			return -1;
	}

	/**<pre> Method to select a specified item in Dropdown box</pre>
	 * @param pageObjectClass - Object of the Page class
	 * @param dropDownElementName - Name of the Dropdown element as displayed in the page
	 * @param selValue - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is selected <b>false</b> otherwise
	 */
	public static boolean selectDropDownOption(Object pageClassObj,String dropDownElementName,String selValue){
		int itemIndex = -1;
		boolean selected = false;
		String selectedItemText;

		initDropDownObj(pageClassObj,dropDownElementName);
		itemIndex = getDropDownItemIndex(selValue);

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			objSelect.selectByIndex(itemIndex);
			Reporter.logEvent(Status.INFO, "Select drop down box item: " + selValue, 
					"Selected item: " + selectedItemText, false);
			selected = true;
		} else {
			Reporter.logEvent(Status.WARNING, "Select drop down box item: " + selValue, 
					"Option not present in the drop down box", false);
		}
		return selected;
	}

	/**<pre> Method to select a specified item in Dropdown box</pre>
	 * @param dropDownElement - Dropdown WebElement
	 * @param selValue - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is selected <b>false</b> otherwise
	 */
	public static boolean selectDropDownOption(WebElement dropDownElement,String selValue){
		int itemIndex = -1;
		boolean selected = false;
		String selectedItemText;

		initDropDownObj(dropDownElement);
		itemIndex = getDropDownItemIndex(selValue);

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			objSelect.selectByIndex(itemIndex);
			Reporter.logEvent(Status.INFO, "Select drop down box item: " + selValue, 
					"Selected item: " + selectedItemText, false);
			selected = true;
		} else {
			Reporter.logEvent(Status.WARNING, "Select drop down box item: " + selValue, 
					"Option not present in the drop down box", false);
		}
		return selected;
	}

	/**<pre> Method to verify if the specified item is found in Dropdown box</pre>
	 * @param pageObjectClass - Object of the Page class
	 * @param dropDownElementName - Name of the Dropdown element as displayed in the page
	 * @param selValue - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is found <b>false</b> otherwise
	 */
	public static boolean verifyDropDownOptionExists(Object pageClassObj,String dropDownElementName,String selValue){
		initDropDownObj(pageClassObj,dropDownElementName);
		int itemIndex = getDropDownItemIndex(selValue);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			Reporter.logEvent(Status.PASS, "Verify option [" + selValue + "] exists in drop down box", 
					"Option " + selectedItemText + " present in the drop down box", false);
			return true;
		} else {
			Reporter.logEvent(Status.FAIL, "Verify option [" + selValue + "] exists in drop down box", 
					"Option not present in the drop down box", false);
			return false;
		}
	}

	/**<pre> Method to verify if the specified item is found in Dropdown box</pre>
	 * @param dropDownElement - WebElement Dropdown
	 * @param selValue - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is found <b>false</b> otherwise
	 */
	public static boolean verifyDropDownOptionExists(WebElement dropDownElement,String selValue){
		initDropDownObj(dropDownElement);
		int itemIndex = getDropDownItemIndex(selValue);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			Reporter.logEvent(Status.PASS, "Verify option [" + selValue + "] exists in drop down box", 
					"Option " + selectedItemText + " present in the drop down box", false);
			return true;
		} else {
			Reporter.logEvent(Status.FAIL, "Verify option [" + selValue + "] exists in drop down box", 
					"Option not present in the drop down box", false);
			return false;
		}
	}

	/**<pre> Method to return the complete text of specified item in Dropdown box if item exists</pre>
	 * @param pageObjectClass - Object of the Page class
	 * @param dropDownElementName - Name of the Dropdown element as displayed in the page
	 * @param selValue - Value of the item listed in Dropdown box (Full or partial)
	 * @return <b>String</b> - Dropdown option text</b> if the specified element is found <b>Empty string</b> otherwise
	 */
	public static String getDropDownOptionAsText(Object pageClassObj,String dropDownElementName,String selValue){
		initDropDownObj(pageClassObj,dropDownElementName);
		int itemIndex = getDropDownItemIndex(selValue);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			return selectedItemText;
		} else {
			return "";
		}
	}

	/**<pre> Method to return the complete text of specified item in Dropdown box if item exists</pre>
	 * @param dropDownElement - WebElement Dropdown
	 * @param selValue - Value of the item listed in Dropdown box (Full or partial)
	 * @return <b>String</b> - Dropdown option text</b> if the specified element is found <b>Empty string</b> otherwise
	 */
	public static String getDropDownOptionAsText(WebElement dropDownElement,String selValue){
		initDropDownObj(dropDownElement);
		int itemIndex = getDropDownItemIndex(selValue);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			return selectedItemText;
		} else {
			return "";
		}
	}

	public static void selectDropnDownOptionAsIndex(WebElement dropDownElement, String dropDownIndex) {
		initDropDownObj(dropDownElement);
		int dropDownIndexElement = Integer.parseInt(dropDownIndex);
		if(dropDownIndexElement>0) 
			objSelect.selectByIndex(dropDownIndexElement);
		else 
			objSelect.selectByIndex(0);		

	}

	//-------------------------------------------------------------- End of DROP DOWN Methods ----------------------------------------------------------------------

	/**<pre> Method to convert amount displayed as String with $ symbol and returns a float value.
	 * If opted ignores case while comparing.</pre>
	 * @param curValue - a String value - the value that is read from the application eg "$12,123.50"
	 * 
	 * @return - float
	 */

	public static float getIntegerCurrency(String curValue){
		float parsedIntCurValue = 0;

		if(curValue.equalsIgnoreCase("N/A"))
			parsedIntCurValue = 0;
		else{
			String removedDollarSign = curValue.substring(1);

			try {
				NumberFormat usFormat = NumberFormat.getNumberInstance(Locale.US);
				parsedIntCurValue = usFormat.parse(removedDollarSign).floatValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return parsedIntCurValue;
	}

	/**
	 * <pre>
	 * Method to capture screenshots from active window handle
	 * Files are placed under folder named specific to test case
	 * Base folder location is provided in property "ScreenshotFolder" 
	 * File name:
	 *        < TestCaseName>_I< IterationNumber>_< TotalNumberOfFilesInFolder>.png
	 * Example:
	 *        MyTestCase_I1_6.png
	 * </pre>
	 * 
	 * @return <b>String</b>&nbsp; Returns screenshot file name with path</pre>
	 */
	public static String captureScreenshot() {
		String fileName = null;
		try {
			//			WebActions.strScreenshotsFolderPath = DriveSuite.currRunPath
			//					+ "/Reports/"
			//					+ ReadProperties.getEnvVariableValue("testSuiteName")
			//							.replaceAll(" ", "_")
			//					+ "/"
			//					+ ReadProperties.getEnvVariableValue("currTestCaseName")
			//							.replaceAll(" ", "_") + "\\Screenshots";

			Globals.GBL_strScreenshotsFolderPath = Globals.GC_TEST_REPORT_DIR
					+ "/"
					+ Globals.GBL_TestCaseName.replaceAll(" ", "_") + "\\Screenshots";

			//			File screenShotDir = new File(WebActions.strScreenshotsFolderPath);
			File screenShotDir = new File(Globals.GBL_strScreenshotsFolderPath);
			//			if (!new File(WebActions.strScreenshotsFolderPath).exists())
			//				new File(WebActions.strScreenshotsFolderPath).mkdirs();
			if (!new File(Globals.GBL_strScreenshotsFolderPath).exists())
				new File(Globals.GBL_strScreenshotsFolderPath).mkdirs();

			int randomInt = screenShotDir.listFiles().length;

			//			File scrFile = ((TakesScreenshot) DriveSuite.webDriver)
			//					.getScreenshotAs(OutputType.FILE);
			File scrFile = ((TakesScreenshot) Web.webdriver)
					.getScreenshotAs(OutputType.FILE);

			//			fileName = ReadProperties.getEnvVariableValue("currTestCaseName")
			//					+ "_Itr"
			//					+ TestDataContainer.GetParameterValue("IterationNumber")
			//					+ "_" + randomInt + ".png";

			fileName = Globals.GBL_TestCaseName
					+ "_Itr"
					+ Globals.GBL_CurrentIterationNumber
					+ "_" + randomInt + ".png";

			//			FileUtils.copyFile(scrFile, new File(
			//					WebActions.strScreenshotsFolderPath + "\\" + fileName));
			FileUtils.copyFile(scrFile, new File(
					Globals.GBL_strScreenshotsFolderPath + "\\" + fileName));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Globals.GBL_strScreenshotsFolderPath + "\\" + fileName;
	}



}
