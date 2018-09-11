package bdd_lib;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import bdd_core.framework.Globals;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.DataTable;


public class Web {
	private static WebDriver webDriver= null;
	public static Exception exception;
	private static Select objSelect;
	public static Robot robot;
	private static boolean isLastIteration = false;
	public static NextGenWebDriver nextGenDriver = null;

	private static ThreadLocal<WebDriver> multiDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<RemoteWebDriver> multiRemoteDriver = new ThreadLocal<RemoteWebDriver>();
	private static String progressBar = ".//*[@id='pscSpinnerId']";
	static HttpURLConnection connection;

	public static boolean isLastIteration() {
		return isLastIteration;
	}

	public static void setLastIteration(boolean isLastIteration) {
		Web.isLastIteration = isLastIteration;
	}

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
	 * Method to check if elements exists on the page and is displayed
	 * </pre>
	 * 
	 * @param element
	 *            - List<WebElement> object which is to be check for
	 * @param waitForElement
	 *            - <b>true</b> if user wants to wait for the element before
	 *            checking if it is displayed. <b>false</b> otherwise
	 * @return <b>boolean - true</b> if elements are displayed on the page.
	 *         <b>false</b> otherwise
	 */
	public static boolean isWebElementsDisplayed(List<WebElement> elements,
			boolean... waitForElement) {
		boolean blnElementDisplayed = false;
		try {
			try {
				if (waitForElement.length > 0) {
					if (waitForElement[0] == true) {
						Web.waitForElements(elements);
					}
				}
			} catch (Exception e) {
				// Do nothing
			}
			try {
				if ((new WebDriverWait(Web.getDriver(), Long.parseLong("1")))
						.ignoring(StaleElementReferenceException.class)
						.until(ExpectedConditions
								.visibilityOfAllElements(elements)).size() == elements
								.size()) {
					blnElementDisplayed = true;
				}
			} catch (Exception e) {
				// Do nothing
			}
		} catch (NoSuchElementException e) {
			blnElementDisplayed = false;
		}
		return blnElementDisplayed;
	}

	public static boolean isWebElementsDisplayed(Object pageClassObj,
			String fieldName,boolean... waitForElement) {
		boolean blnElementDisplayed = false;
		try {
			List<WebElement> displayedElement = getLisOfPageObjectFields(pageClassObj,fieldName);
			try {
				if (waitForElement.length > 0) {
					if (waitForElement[0] == true) {
						Web.waitForElements(displayedElement);
					}
				}
			} catch (Exception e) {
				// Do nothing
			}
			try {
				if ((new WebDriverWait(Web.getDriver(), Long.parseLong("1")))
						.ignoring(StaleElementReferenceException.class)
						.until(ExpectedConditions
								.visibilityOfAllElements(displayedElement)).size() == displayedElement
								.size()) {
					blnElementDisplayed = true;
				}
			} catch (Exception e) {
				// Do nothing
			}
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
	 * @throws Exception
	 */
	public static boolean isWebElementDisplayed(Object pageClassObj,
			String fieldName, boolean... waitForEle) {
		boolean blnElementDisplayed = false;
		try {
			WebElement displayedElement = getPageObjectFields(pageClassObj,
					fieldName);
			try {
				if (waitForEle.length > 0) {
					if (waitForEle[0] == true) {
						Web.waitForElement(displayedElement);
					}
				}
			} catch (Exception t) {
				// nothing
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
	 * @throws Exception
	 */
	public static String setTextToTextBox(String textBoxFieldName,
			Object pageClassObj, CharSequence... valueToSet) throws Exception {
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

	/**
	 * Method to return parent element of the specified element
	 * 
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
	 * @throws Exception
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

	public static boolean isCheckBoxChecked(WebElement fieldName, 
			boolean... waitForElement)
	{
		boolean isElementChecked = false;
		try
		{
			if(isWebElementDisplayed(fieldName, waitForElement)){
				isElementChecked = fieldName.isSelected();	
			}			
		}
		catch(Exception e)
		{
			throw new Error(e.getMessage());
		}
		return isElementChecked;
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
	 * @throws Exception
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
			throw new Error("Error getting page obejct fields : "
					+ e.getMessage());
		}
		return element;
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
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static List<WebElement> getLisOfPageObjectFields(Object pageObjectClass,
			String fieldName) {
		Method getWebElementMethod = null;
		List<WebElement> elements = null;
		try {
			getWebElementMethod = pageObjectClass.getClass().getDeclaredMethod(
					"getWebElements", String.class);
		} catch (NoSuchMethodException e) {
			throw new Error("getWebElements() method is not found in "
					+ pageObjectClass.getClass().toString());
		}
		getWebElementMethod.setAccessible(true);
		try {
			elements = (List<WebElement>) getWebElementMethod.invoke(pageObjectClass,
					fieldName);
		} catch (Exception e) {
			throw new Error("Error getting page obejct fields : "
					+ e.getMessage());
		}
		return elements;
	}

	public static WebElement returnWebElement(Object pageObjectClass,String fieldName)
	{		
		WebElement element = null;
		try {
			element = getPageObjectFields(pageObjectClass,fieldName);			
		}
		catch(Exception e) { }
		return element;
	}

	public static List<WebElement> returnWebElements(Object pageObjectClass,String fieldName)
	{		
		List<WebElement> elements = null;
		try {
			elements = getLisOfPageObjectFields(pageObjectClass,fieldName);			
		}
		catch(Exception e) { }
		return elements;
	}

	/**
	 * Method to wait for the specified element's presence
	 * 
	 * @param webElememnt
	 * @throws Exception
	 */
	public static void waitForElement(WebElement element) {
		try {
			(new WebDriverWait(Web.getDriver(), Long.parseLong(Stock
					.getConfigParam("objectSyncTimeout")))).ignoring(
							StaleElementReferenceException.class).until(
									ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			//			e.printStackTrace();
		}
	}

	/**
	 * Method to wait for the list element's presence
	 * 
	 * @param webElememnt
	 * @throws Exception
	 */
	public static void waitForElements(List<WebElement> elements) {
		try {
			(new WebDriverWait(Web.getDriver(), Long.parseLong(Stock
					.getConfigParam("objectSyncTimeout")))).ignoring(
							StaleElementReferenceException.class).until(
									ExpectedConditions.visibilityOfAllElements(elements));
		} catch (Exception e) {
			//			e.printStackTrace();
		}
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
		(new WebDriverWait(Web.getDriver(), Long.parseLong(Stock
				.getConfigParam("objectSyncTimeout"))))
				.until(ExpectedConditions.visibilityOf(presentElement));
	}

	/**
	 * Method to return WebElement using Page Object as reference
	 * 
	 * @param webElememnt
	 * @throws Exception
	 */
	public static WebElement returnElement(Object pageClassObj,
			String webElementName) throws Exception {
		WebElement presentElement = getPageObjectFields(pageClassObj,
				webElementName);
		return presentElement;
	}

	//To access WebDriver directly
	public static WebDriver getDriver(){
		if(Stock.getConfigParam("type").equalsIgnoreCase("grid"))
		{
			return multiRemoteDriver.get();
		}else{
			return multiDriver.get();
			//return webdriverMap.get(Thread.currentThread().getId());
		}
	}	
	/**
	 * <pre>
	 * Method to initiate webDriver object based on used specified browser type
	 * Browser Types:
	 *     1. <b>INTERNET_EXPLORER</b> or <b>IEXPLORE</b> or <b>IE</b>
	 *     2. <b>FIREFOX</b> or <b>FF</b>
	 *     3. <b>CHROME</b>
	 * </pre>
	 * 
	 * @param webBrowser
	 * @return
	 */
	public static WebDriver getWebDriver(String webBrowser) {
		webDriver = multiDriver.get();

		if(webDriver == null){
			if (webBrowser.trim().equalsIgnoreCase("INTERNET_EXPLORER")
					|| webBrowser.trim().equalsIgnoreCase("IEXPLORE")
					|| webBrowser.trim().equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						Stock.getConfigParam("IEDriverClassPath"));
				DesiredCapabilities capabilities = DesiredCapabilities
						.internetExplorer();
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("ie.ensureCleanSession", true);
				capabilities.setCapability("requireWindowFocus", true);
				capabilities.setCapability(InternetExplorerDriver.
						INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);


				webDriver = new InternetExplorerDriver(capabilities);

			} else if (webBrowser.trim().equalsIgnoreCase("CHROME")) {
				System.setProperty("webdriver.chrome.driver",
						Stock.getConfigParam("ChromeDriverClassPath"));
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");
				options.addArguments("test-type");
				capabilities.setCapability("chrome.exe","C:\\Program Files (x86)\\Google\\Chrome\\Application");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				webDriver = new ChromeDriver(capabilities);

			} else if (webBrowser.trim().equalsIgnoreCase("FIREFOX")
					|| webBrowser.trim().equalsIgnoreCase("FF")) {
				System.setProperty("webdriver.firefox.marionette", Stock.getConfigParam("GeckoDriverClassPath"));
				ProfilesIni profiles = new ProfilesIni();
				FirefoxProfile ffProfile = profiles.getProfile("default");

				// ffProfile.setPreference("signon.autologin.proxy", true);

				if (ffProfile == null) {
					System.out.println("Initiating Firefox with dynamic profile");
					webDriver = new FirefoxDriver();
				} else {
					System.out.println("Initiating Firefox with default profile");
					webDriver = new FirefoxDriver(ffProfile);
				}

			} else {
				throw new Error("Unknown browser type specified: " + webBrowser);
			}
			Capabilities cap = ((RemoteWebDriver) webDriver).getCapabilities();
			/*     String browserName = cap.getBrowserName().toUpperCase();
                   System.out.println("BROWSER NAME:"+browserName);
                   String os = cap.getPlatform().toString();
                   System.out.println("OPERATING SYSTEM:"+os);
                   String browserVersion = cap.getVersion().toString().substring(0, 4);
                   System.out.println("BROWSER VERSION:"+browserVersion);*/
			webDriver.manage().window().maximize();

		}
		//webdriverMap.put(Thread.currentThread().getId(), webDriver);
		multiDriver.set(webDriver);
		return webDriver;
	}

	public static WebDriver getRemoteWebDriver(String webBrowser,String nodeUrl) throws MalformedURLException {           
		RemoteWebDriver remoteWebDriver = multiRemoteDriver.get();
		if (webBrowser.trim().equalsIgnoreCase("INTERNET_EXPLORER")
				|| webBrowser.trim().equalsIgnoreCase("IEXPLORE")
				|| webBrowser.trim().equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver",Stock.getConfigParam("IEDriverClassPath"));
			DesiredCapabilities  capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability("ie.ensureCleanSession", true);
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability(InternetExplorerDriver.
					INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability("enablePersistentHover", true);
			//setCapabilities(webBrowser,Platform.XP);

			remoteWebDriver = new RemoteWebDriver(new URL(nodeUrl),capabilities);

		} else if (webBrowser.trim().equalsIgnoreCase("CHROME")) {
			System.setProperty("webdriver.chrome.driver",Stock.getConfigParam("ChromeDriverClassPath"));
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			//  setCapabilities(webBrowser,Platform.XP);
			remoteWebDriver = new RemoteWebDriver(new URL(nodeUrl),capabilities);
		} else if (webBrowser.trim().equalsIgnoreCase("FIREFOX")
				|| webBrowser.trim().equalsIgnoreCase("FF")) {
			//System.setProperty("webdriver.firefox.marionette", Stock.getConfigParam("GeckoDriverClassPath"));
			ProfilesIni profiles = new ProfilesIni();
			FirefoxProfile ffProfile = profiles.getProfile("default");
			DesiredCapabilities capabilities = DesiredCapabilities
					.firefox();
			capabilities.setBrowserName("firefox");
			capabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);
			//capabilities.setPlatform(Platform.XP);
			System.out.println("node url is"+nodeUrl);
			remoteWebDriver = new RemoteWebDriver(new URL(nodeUrl),capabilities);
		} else {

			throw new Error("Unknown browser type specified: " + webBrowser);
		}

		/*String nodeUrl="http://143.199.162.200:5566/wd/hub";

      DesiredCapabilities capability = DesiredCapabilities.firefox();

      capability.setBrowserName("firefox");

      capability.setPlatform(Platform.XP);

      webDriver=new  RemoteWebDriver(new URL(nodeUrl), capability);*/
		remoteWebDriver.manage().window().maximize();
		// webdriverMap.put(Thread.currentThread().getId(), remoteWebDriver);
		multiRemoteDriver.set(remoteWebDriver);
		return remoteWebDriver;
	}

	/**
	 * <pre>
	 * Method to verify two strings. 
	 * Method also takes care of reporting the result into the log file.
	 * Trims off white spaces at both the ends.
	 * If opted ignores case while comparing.
	 * </pre>
	 * 
	 * @param inExpectedText
	 * @param inActualText
	 * @param ignoreCase
	 *            - <b>true</b> or <b>false</b>
	 * 
	 *            <pre>
	 * Pass <b>true</b> to ignore case while comparing. 
	 * 	Takes default value if this argument is vomited. 
	 * 	Default value: <b>false</b>
	 * </pre>
	 * @return - boolean - true</b> if both the string matches. <b>false</b>
	 *         otherwise
	 */
	public static boolean VerifyText(String inExpectedText,
			String inActualText, boolean... ignoreCase) {
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

	/**
	 * <pre>
	 * Method to verify partial text of two strings. 
	 * Trims off white spaces at both the ends.
	 * @param inPartialText
	 * @param inActualText
	 * @param ignoreCase
	 * @Default value: <b>false</b>
	 * </pre>
	 * 
	 * @return - boolean - true</b> if both the string matches. <b>false</b>
	 *         otherwise
	 */
	public static boolean VerifyPartialText(String inPartialText,
			String inActualText, boolean ignoreCase) {
		boolean isMatching = false;
		if (ignoreCase
				&& inActualText.trim().toLowerCase()
				.contains(inPartialText.toLowerCase().trim())) {
			isMatching = true;
		} else if (inActualText.trim().contains(inPartialText.trim())) {
			isMatching = true;
		}
		return isMatching;
	}

	// ------------------------------------------------------------------------DROP
	// DOWN Methods
	// ----------------------------------------------------------------------

	/**
	 * <pre>
	 * Method to initialize the Drop down object*
	 * </pre>
	 * 
	 * @param pageObjectClass
	 *            - Object of the Page class
	 * @param dropDownElementName
	 *            - Name of the Dropdown element as displayed in the page
	 * @throws Exception
	 */
	private static void initDropDownObj(Object pageClassObj,
			String dropDownElementName) throws Exception {
		try {
			WebElement dropDownElement = getPageObjectFields(pageClassObj,
					dropDownElementName);
			if (Web.isWebElementDisplayed(dropDownElement)) {
				objSelect = new Select(dropDownElement);
			} else {
				throw new NoSuchElementException(
						"Unable to locate the drop down object "
								+ dropDownElementName);
			}
		} catch (NoSuchElementException e) {
			throw new Error(e.getMessage());
		}
	}

	/**
	 * <pre>
	 * Method to initialize the Drop down object*
	 * </pre>
	 * 
	 * @param dropDownElement
	 *            - Dropdown WebElement
	 */
	private static void initDropDownObj(WebElement dropDownElement) {
		try {
			if (Web.isWebElementDisplayed(dropDownElement)) {
				objSelect = new Select(dropDownElement);
			} else {
				throw new NoSuchElementException(
						"Unable to locate the drop down object "
								+ dropDownElement.toString());
			}
		} catch (NoSuchElementException e) {
			throw new Error(e.getMessage());
		}
	}

	/**
	 * <pre>
	 * Method to get the item index in Dropdown*
	 * </pre>
	 * 
	 * @param itemValue
	 *            - Value of the item listed in Dropdown box
	 */
	private static int getDropDownItemIndex(String itemValue, boolean... args) {
		int iCntr = 0;
		boolean selected = false;

		ListIterator<WebElement> lstIter = objSelect.getOptions()
				.listIterator();
		while (lstIter.hasNext()) {
			WebElement currElement = lstIter.next();
			String txt = currElement.getText();	

			if (args.length > 0) {
				if (txt.toUpperCase().toString().trim().contains(itemValue.toUpperCase().toString().trim())) {
					selected = true;
					break;
				}
			} else {
				if (txt.toUpperCase().equalsIgnoreCase(itemValue.toUpperCase())) {
					selected = true;
					break;
				}
			}
			iCntr++;
		}
		if (selected)
			return iCntr;
		else
			return -1;
	}

	/**
	 * <pre>
	 * Method to select a specified item in Dropdown box
	 * </pre>
	 * 
	 * @param pageObjectClass
	 *            - Object of the Page class
	 * @param dropDownElementName
	 *            - Name of the Dropdown element as displayed in the page
	 * @param selValue
	 *            - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is selected
	 *         <b>false</b> otherwise
	 * @throws Exception
	 */
	public static boolean selectDropDownOption(Object pageClassObj,
			String dropDownElementName, String selValue, boolean... args)
					throws Exception {
		int itemIndex = -1;
		boolean selected = false;
		String selectedItemText;

		initDropDownObj(pageClassObj, dropDownElementName);
		itemIndex = getDropDownItemIndex(selValue, args);

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			objSelect.selectByIndex(itemIndex);
			Reporter.logEvent(Status.INFO, "Select drop down box item: "
					+ selValue, "Selected item: " + selectedItemText, false);
			selected = true;
		} else {
			Reporter.logEvent(Status.WARNING, "Select drop down box item: "
					+ selValue, "Option not present in the drop down box",
					false);
		}
		return selected;
	}

	/**
	 * <pre>
	 * Method to select a specified item in Dropdown box
	 * </pre>
	 * 
	 * @param dropDownElement
	 *            - Dropdown WebElement
	 * @param selValue
	 *            - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is selected
	 *         <b>false</b> otherwise
	 */
	public static boolean selectDropDownOption(WebElement dropDownElement,
			String selValue, boolean... args) {
		int itemIndex = -1;
		boolean selected = false;
		String selectedItemText;
		initDropDownObj(dropDownElement);
		itemIndex = getDropDownItemIndex(selValue, args);

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			objSelect.selectByIndex(itemIndex);
			Reporter.logEvent(Status.INFO, "Select drop down box item: "
					+ selValue, "Selected item: " + selectedItemText, false);
			selected = true;
		} else {
			Reporter.logEvent(Status.WARNING, "Select drop down box item: "
					+ selValue, "Option not present in the drop down box",
					false);
		}
		return selected;
	}

	/**
	 * <pre>
	 * Method to verify if the specified item is found in Dropdown box
	 * </pre>
	 * 
	 * @param pageObjectClass
	 *            - Object of the Page class
	 * @param dropDownElementName
	 *            - Name of the Dropdown element as displayed in the page
	 * @param selValue
	 *            - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is found
	 *         <b>false</b> otherwise
	 * @throws Exception
	 */
	public static boolean verifyDropDownOptionExists(Object pageClassObj,
			String dropDownElementName, String selValue, boolean... args)
					throws Exception {
		initDropDownObj(pageClassObj, dropDownElementName);
		int itemIndex = getDropDownItemIndex(selValue, args);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			Reporter.logEvent(Status.PASS, "Verify option [" + selValue
					+ "] exists in drop down box", "Option " + selectedItemText
					+ " present in the drop down box", false);
			return true;
		} else {
			Reporter.logEvent(Status.FAIL, "Verify option [" + selValue
					+ "] exists in drop down box",
					"Option not present in the drop down box", false);
			return false;
		}
	}

	/**
	 * <pre>
	 * Method to verify if the specified item is found in Dropdown box
	 * </pre>
	 * 
	 * @param dropDownElement
	 *            - WebElement Dropdown
	 * @param selValue
	 *            - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is found
	 *         <b>false</b> otherwise
	 */
	public static boolean verifyDropDownOptionExists(
			WebElement dropDownElement, String selValue, boolean... args) {
		initDropDownObj(dropDownElement);
		int itemIndex = getDropDownItemIndex(selValue, args);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			Reporter.logEvent(Status.PASS, "Verify option [" + selValue
					+ "] exists in drop down box", "Option " + selectedItemText
					+ " present in the drop down box", false);
			return true;
		} else {
			Reporter.logEvent(Status.FAIL, "Verify option [" + selValue
					+ "] exists in drop down box",
					"Option not present in the drop down box", false);
			return false;
		}
	}

	/**
	 * <pre>
	 * Method to return the complete text of specified item in Dropdown box if item exists
	 * </pre>
	 * 
	 * @param pageObjectClass
	 *            - Object of the Page class
	 * @param dropDownElementName
	 *            - Name of the Dropdown element as displayed in the page
	 * @param selValue
	 *            - Value of the item listed in Dropdown box (Full or partial)
	 * @return <b>String</b> - Dropdown option text</b> if the specified element
	 *         is found <b>Empty string</b> otherwise
	 * @throws Exception
	 */
	public static String getDropDownOptionAsText(Object pageClassObj,
			String dropDownElementName, String selValue, boolean... args)
					throws Exception {
		initDropDownObj(pageClassObj, dropDownElementName);
		int itemIndex = getDropDownItemIndex(selValue, args);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			return selectedItemText;
		} else {
			return "";
		}
	}

	/**
	 * <pre>
	 * Method to return the complete text of specified item in Dropdown box if item exists
	 * </pre>
	 * 
	 * @param dropDownElement
	 *            - WebElement Dropdown
	 * @param selValue
	 *            - Value of the item listed in Dropdown box (Full or partial)
	 * @return <b>String</b> - Dropdown option text</b> if the specified element
	 *         is found <b>Empty string</b> otherwise
	 */
	public static String getDropDownOptionAsText(WebElement dropDownElement,
			String selValue, boolean... args) {
		initDropDownObj(dropDownElement);
		int itemIndex = getDropDownItemIndex(selValue, args);
		String selectedItemText;

		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			return selectedItemText;
		} else {
			return "";
		}
	}

	public static void selectDropnDownOptionAsIndex(WebElement dropDownElement,
			String dropDownIndex) {
		initDropDownObj(dropDownElement);
		int dropDownIndexElement = Integer.parseInt(dropDownIndex);
		if (dropDownIndexElement > 0)
			objSelect.selectByIndex(dropDownIndexElement);
		else
			objSelect.selectByIndex(0);

	}

	// -------------------------------------------------------------- End of
	// DROP DOWN Methods
	// ----------------------------------------------------------------------

	/**
	 * <pre>
	 * Method to convert amount displayed as String with $ symbol and returns a float value.
	 * If opted ignores case while comparing.
	 * </pre>
	 * 
	 * @param curValue
	 *            - a String value - the value that is read from the application
	 *            eg "$12,123.50"
	 * 
	 * @return - float
	 */

	public static float getIntegerCurrency(String curValue) {
		float parsedIntCurValue = 0;

		if (curValue.equalsIgnoreCase("N/A"))
			parsedIntCurValue = 0;
		else {
			String removedDollarSign = curValue.substring(1);

			try {
				NumberFormat usFormat = NumberFormat
						.getNumberInstance(Locale.US);
				parsedIntCurValue = usFormat.parse(removedDollarSign)
						.floatValue();
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
			Globals.GBL_strScreenshotsFolderPath = Globals.GC_TEST_REPORT_DIR
					+ Globals.GBL_TestCaseName.replaceAll(" ", "_")
					+ File.separator+"Screenshots";

			// File screenShotDir = new
			// File(Globals.GBL_strScreenshotsFolderPath);
			if (!new File(Globals.GBL_strScreenshotsFolderPath).exists())
				new File(Globals.GBL_strScreenshotsFolderPath).mkdirs();
			File screenShotDir = new File(Globals.GBL_strScreenshotsFolderPath);
			if (!screenShotDir.exists()) {
				// Try any one of these conditions
				System.out.println("SCREENSHOT DIRECTORY IS NOT EXISTS");
				Globals.GBL_strScreenshotsFolderPath = System
						.getProperty("user.dir")
						+ File.separator+"TestReport"+File.separator
						+ Globals.GBL_TestCaseName.replaceAll(" ", "_")
						+ File.separator+"Screenshots";

				screenShotDir = new File(Globals.GBL_strScreenshotsFolderPath);
			}

			int randomInt = screenShotDir.listFiles().length;
			File scrFile = ((TakesScreenshot) Web.getDriver())
					.getScreenshotAs(OutputType.FILE);

			fileName = Globals.GBL_TestCaseName + "_Itr"
					+ Globals.GBL_CurrentIterationNumber + "_" + randomInt
					+ ".png";
			FileUtils.copyFile(scrFile, new File(
					Globals.GBL_strScreenshotsFolderPath + File.separator + fileName));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				//				e.printStackTrace();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "./" + Globals.GBL_TestCaseName.replaceAll(" ", "_")
				+ File.separator+"Screenshots" + File.separator + fileName;
	}

	/*
	 * Method to Mouse hover on specific web element
	 * 
	 * @PARAMETER = WebElement
	 * 
	 * @Author:Ranjan
	 */
	public static void mouseHover(WebElement webElement) {
		Actions actions;
		actions = new Actions(Web.getDriver());
		actions.moveToElement(webElement);
		actions.build().perform();
	}


	/**
	 * <pre>
	 * Method to select a specified item in Dropdown box on Multiple options basis
	 * </pre>
	 * 
	 * @param dropDownElement
	 *            - Dropdown WebElement
	 * @param selValue
	 *            - Value of the item listed in Dropdown box
	 * @return <b>boolean</b> - true</b> if the specified element is selected
	 *         <b>false</b> otherwise
	 */
	public static boolean selectDropDownOptionVarArgs(
			WebElement dropDownElement, String... selValue) {
		int itemIndex = -1;
		boolean selected = false;
		String selectedItemText;

		initDropDownObj(dropDownElement);
		if (selValue.length > 0) {
			for (int i = 0; i < selValue.length; i++) {
				itemIndex = getDropDownItemIndex(selValue[i]);
				if (itemIndex > 0) {
					break;
				}
			}
		}
		if (itemIndex > -1) {
			selectedItemText = objSelect.getOptions().get(itemIndex).getText();
			objSelect.selectByIndex(itemIndex);
			Reporter.logEvent(Status.INFO, "Select drop down box item: "
					+ selValue, "Selected item: " + selectedItemText, false);
			selected = true;
		} else {
			Reporter.logEvent(Status.WARNING, "Select drop down box item: "
					+ selValue, "Option not present in the drop down box",
					false);
		}
		return selected;
	}
	/**
	 * <pre>
	 * Method to wait until page loads
	 * </pre>
	 * 
	 * @param driver
	 */
	public static void waitForPageToLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 5);
		try {
			wait.until(pageLoad);
		} catch (Throwable pageLoadWaitError) {
			pageLoadWaitError.printStackTrace();
		}
	}
	/**
	 * <pre>
	 * Method to set password to specified text box element.
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
	/*public static String setPassWordToTextBox(WebElement textBoxField,
                   String valueToSet) {
            String fieldTextValue = "";
            if (Web.isWebElementDisplayed(textBoxField)) {
                   textBoxField.clear();
                   textBoxField.click();
                   try {
                         textBoxField.sendKeys( new PassWord().decrypt(valueToSet));
                   } catch (Exception e) {

                   }
                   textBoxField.click();
                   fieldTextValue = textBoxField.getAttribute("value");
            }
            return fieldTextValue;
     }
	 */
	public static void removeWebDriverInstance(){
		multiDriver.set(null);
	}

	public static void FrameSwitchONandOFF(boolean switchFrame,WebElement... frm){
		if(frm.length>0){
			if(isWebElementDisplayed(frm[0],false) && switchFrame){
				webDriver.switchTo().frame(frm[0]);
			}
		}

		if(!switchFrame){
			webDriver.switchTo().defaultContent();
		}		
	}

	public static void scrollDown(){
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");

	}
	
	public static List<Map<String, String>> rawValues(DataTable creds){
		return creds.asMaps(String.class, String.class);
	}
	
	/**
	 * Method to click on a specified web element using Actions click.
	 * @param webElementName
	 *            - Name of the web element to be clicked
	 * @return <b>true</b> if successful, <b>false</b> otherwise.
	 * @throws Exception
	 */
	public static boolean actionsClickOnElement(WebElement webElementName) {
		Actions act = new Actions(Web.getDriver());
		boolean success = false;
		if (webElementName != null) {
			
			if (Web.isWebElementDisplayed(webElementName)) {
				act.moveToElement(webElementName).click().perform();
				success = true;
			}
		}
		return success;
	}
	
	public static void waitForProgressBar() {
		int iTimeInSecond = 100;
		try {

			List<WebElement> ele = Web.getDriver().findElements(
					By.xpath(progressBar));
			if (ele.size() > 0) {
				Web.waitForElement(ele.get(0));
				int iCount = 0;
				while (ele.get(0).isDisplayed()) {

					if (iCount == iTimeInSecond) {
						break;
					}

					System.out.println("Progress Bar displaying..");
					Thread.sleep(1000);
					iCount++;
				}
			}

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	public static void jsClick(WebElement element)
	{
		try{
			JavascriptExecutor executor = (JavascriptExecutor)Web.getDriver();
			executor.executeScript("arguments[0].click();", element);
			//waitForPageToLoad(Web.getDriver());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String isLinkBroken(URL url) throws Exception{
		connection = (HttpURLConnection)url.openConnection();
		try{
			connection.connect();
			String response = connection.getResponseMessage();
			connection.disconnect();
			return response;
		}
		catch(Exception e){
			return e.getMessage();
		}
	}
	
	public static int getStatusCode(URL url) throws IOException{
		connection = (HttpURLConnection)url.openConnection();
		try{
			connection.connect();
			int statusCode = connection.getResponseCode();
			connection.disconnect();
			return statusCode;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0;
		
	}

}
