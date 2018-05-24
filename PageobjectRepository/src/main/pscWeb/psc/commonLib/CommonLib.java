package psc.commonLib;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import psc.accountverification.AccountVerificationPage;
import psc.homepage.HomePage;
import psc.reports.ReportsPage;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class CommonLib {
	static ResultSet queryResultSet;
	static HomePage homePage;
	public static String browserName;
	static AccountVerificationPage accountVerPage;
	static ReportsPage reportsPage;

	// private static String progressBar =".//*[@id='pscSpinnerId']";
	public CommonLib() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	public static void HighlightElement(WebElement ele, WebDriver driver) {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"color: yellow; border: 2px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"");
		}
	}

	private static WebElement menuElement(String menuName) {
		return Web.getDriver().findElement(
				By.xpath("//ul[@id='newMenu']/li/a[contains(text(),'"
						+ menuName + "')]"));
	}

	public static void enterData(WebElement ele, String value) {
		String tagName = "";
		try {
			tagName = ele.getTagName();
			if (tagName.equals("input")) {
				if (ele.getAttribute("type").equals("text")) {
					Web.setTextToTextBox(ele, value);
				} else if (ele.getAttribute("type").equals("radio")) {
					Web.clickOnElement(ele);
				}
			}
			if (tagName.equals("select")) {
				Select select = new Select(ele);
				for (WebElement opt : select.getOptions()) {
					if (Web.VerifyPartialText(value, opt.getText(), true)) {
						opt.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method converts the List of WebElements to List of string
	 * 
	 * @param refList
	 * @return
	 */

	public static List<String> getWebElementstoListofStrings(
			List<WebElement> refList) {
		List<String> list = new ArrayList<String>();
		for (WebElement refWebElement : refList) {
			list.add(refWebElement.getText());
		}
		return list;
	}

	public static void fillForm(WebElement parentNode, String... coLNames) {
		String tagName = "";
		try {
			for (String colNm : coLNames) {
				tagName = parentNode.findElement(By.id(colNm)).getTagName();
				if (tagName.equals("input")) {
					if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("text")) {
						Thread.sleep(500);
						Web.setTextToTextBox(
								parentNode.findElement(By.id(colNm)),
								Stock.GetParameterValue(colNm));
					} else if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("radio")) {
						Thread.sleep(500);
						Web.clickOnElement(parentNode.findElement(By.id(colNm)));
					}
				}
				if (tagName.equals("select")) {
					Select select = new Select(parentNode.findElement(By
							.id(colNm)));
					for (WebElement opt : select.getOptions()) {
						if (Web.VerifyPartialText(
								Stock.GetParameterValue(colNm), opt.getText(),
								true)) {
							opt.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public static ResultSet getParticipantInfoFromDataBase(String userName)
			throws SQLException {

		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("getParticipantInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlQuery[0] = getUserDBName(userName) + "DB_"
				+ checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				userName.substring(0, 9));

		if (DB.getRecordSetCount(participantInfo) > 0) {
			try {
				participantInfo.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						com.aventstack.extentreports.Status.WARNING,
						"Query Participant Info from DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		}
		return participantInfo;
	}

	public static String getUserDBName(String userName) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet participantDB = null;
		try {
			sqlQuery = Stock.getTestQuery("getPartcipantDBInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantDB = DB.executeQuery(sqlQuery[0], sqlQuery[1], "K_"
				+ userName);
		if (DB.getRecordSetCount(participantDB) > 0) {
			try {
				participantDB.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}

		}
		System.out.println("DATA BASE Name"
				+ participantDB.getString("DATABASE_INSTANCE"));
		String db = participantDB.getString("DATABASE_INSTANCE");
		if (db.equals("QASK")) {
			db = "ISIS";
		}
		participantDB.close();
		return db;
	}

	public static String getParticipantID(String ssn) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet participantID = null;

		try {
			sqlQuery = Stock.getTestQuery("getParticipantID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

		if (DB.getRecordSetCount(participantID) > 0) {
			try {
				participantID.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		} else {
			try {
				sqlQuery = Stock.getTestQuery("getParticipantIDfromDiffDBISIS");
			} catch (Exception e) {
				e.printStackTrace();
			}

			participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

			if (DB.getRecordSetCount(participantID) > 0) {
				try {
					participantID.last();
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query Participant DB:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
			} else {
				try {
					sqlQuery = Stock.getTestQuery("getParticipantIDfromDiffDB");
				} catch (Exception e) {
					e.printStackTrace();
				}

				participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

				if (DB.getRecordSetCount(participantID) > 0) {
					try {
						participantID.last();
					} catch (SQLException e) {
						e.printStackTrace();
						Reporter.logEvent(
								Status.WARNING,
								"Query Participant DB:" + sqlQuery[0],
								"The Query did not return any results. Please check participant test data as the appropriate data base.",
								false);
					}
				}
			}
		}
		System.out.println("ID is " + participantID.getString("ID"));
		return participantID.getString("ID");
	}

	public static String checkEnv(String envName) {
		if (envName.contains("PROJ")) {
			return Globals.DB_TYPE.get("PROJ");
		}
		if (envName.contains("QA")) {
			return Globals.DB_TYPE.get("QA");
		}
		if (envName.contains("PROD")) {
			return Globals.DB_TYPE.get("PROD");
		}
		return null;
	}

	/**
	 * @author smykjn<br>
	 *         This method returns true if xpath exist else false.
	 */

	public static boolean isElementExistByXpath(String xpath) {
		boolean xpathExist = false;
		List<WebElement> webElements = Web.getDriver().findElements(
				By.xpath(xpath));
		if (webElements.size() == 0) {
			xpathExist = false;
		} else {
			xpathExist = false;
		}
		return xpathExist;
	}

	private static String progressBar = ".//*[@id='pscSpinnerId']";

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

	/**
	 * @author smykjn
	 * @Objective This method switches to default plan page
	 * @throws SQLException
	 * @throws Exception
	 */
	public static void switchToDefaultPlan() throws SQLException, Exception {
		if (!Stock.getConfigParam("DataType").equals("Apple")) {
			String defaultPlan = null;
			homePage = new HomePage();
			queryResultSet = DB.executeQuery(
					Stock.getTestQuery("selectDefaultPlanQuery")[0],
					Stock.getTestQuery("selectDefaultPlanQuery")[1], "K_"
							+ Stock.GetParameterValue("username"));
			while (queryResultSet.next()) {
				defaultPlan = queryResultSet.getString("DEFAULT_GA_ID");
			}
			Web.getDriver().switchTo().defaultContent();
			homePage.searchPlanWithIdOrName(defaultPlan);
		}
	}

	/**
	 * @author smykjn
	 * @Objective This method returns true of list is sorted in ascending order
	 * @return boolean
	 */

	public static boolean isSortedByDescOrder(List<Double> list) {
		boolean sorted = false;
		if (list.size() == 1) {
			sorted = true;
			Reporter.logEvent(
					Status.INFO,
					"Only one allocation is found so sorting validation is not required.",
					"", true);
		} else {
			for (int i = 1; i < list.size(); i++) {

				if (list.get(i - 1).compareTo(list.get(i)) > 0) {
					sorted = true;
				} else {
					sorted = false;
					break;
				}
			}
		}

		return sorted;
	}

	/**
	 * @author smykjn
	 * @Objective This method returns true if list is sorted based on sorting
	 *            argument.
	 * @return boolean
	 * @param List
	 *            ,String
	 */
	/*
	 * public static boolean isSorted(List<String> list,String order) { boolean
	 * sorted = false; if(list.size()==1) { sorted = true;
	 * Reporter.logEvent(Status.INFO,
	 * "Only one record is found so sorting validation is not required.", "",
	 * true); } else{ for (int i = 1; i < list.size(); i++) { if
	 * (list.get(i-1).compareTo(list.get(i)) > 0) { sorted = true;} else{ sorted
	 * = false; break; } } }
	 * 
	 * return sorted; }
	 */

	/**
	 * @author smykjn
	 * @param actHeaders
	 *            <pre>
	 * this parameter represents List of header WebElements captured from Xpath or any locators.
	 * </pre>
	 * @param expHeaders
	 *            <pre>
	 * This parameter represents List of expected headers that can be taken from test data source ex. Excel,XML.
	 * </pre>
	 * @return boolean
	 * 
	 *         <pre>
	 * This method returns true if all actual headers are present in exppcted header list.
	 * if any of the header is missing from expHeaders the returns false.
	 * </pre>
	 * @throws Exception
	 * @Date 2nd-May-2017
	 */
	public static boolean isAllHeadersDisplayed(List<WebElement> actHeaders,
			List<String> expHeaders) throws Exception {
		String[] headers = expHeaders.toArray(new String[expHeaders.size()]);
		boolean isdisplayed = false;
		int headerCount = 0;
		Arrays.sort(headers);
		for (WebElement header : actHeaders) {
			System.out.println("Actual Header is" + header.getText().trim());	
			int index = binarySearch(headers, header.getText().replaceAll(":", "").trim(), 0, headers.length-1);
			System.out.println(header.getText().trim()+" found at "+index);
			if(index>=0)
				headerCount++;
			
		}
		System.out.println("headerCount: "+headerCount);
		if(headerCount==headers.length)
			return true;
		return isdisplayed;
	}

	/**
	 * @author smykjn
	 * @param actHeaders
	 *            <pre>
	 * this parameter represents List of header WebElements captured from Xpath or any locators.
	 * </pre>
	 * @param expHeaders
	 *            <pre>
	 * This parameter represents List of expected headers that can be taken from test data source ex. Excel,XML.
	 * </pre>
	 * @return boolean
	 * 
	 *         <pre>
	 * This method returns true if all actual headers are present in exppcted header list.
	 * if any of the header is missing from expHeaders the returns false.
	 * </pre>
	 * @throws Exception
	 * @Date 2nd-May-2017
	 */
	public static boolean isAllHeadersDisplayedWhiteSpace(
			List<WebElement> actHeaders, List<String> expHeaders)
			throws Exception {
		boolean isdisplayed = false;
		for (WebElement header : actHeaders) {
			System.out.println("Actual Header is"
					+ header.getText().replaceAll("\\s+", " ").trim());
			if (expHeaders.contains(header.getText().replaceAll(":", "")
					.replaceAll("\\s+", " ").trim())) {
				isdisplayed = true;
			} else {
				isdisplayed = false;
				break;
			}
		}
		return isdisplayed;
	}

	/**
	 * <pre>
	 * This method converts String list into Date List and sort it in descending order.
	 * </pre>
	 * 
	 * @Date 3rd-May-2017
	 * @author smykjn
	 * @return boolean
	 * 
	 *         <pre>
	 * returns true if list is sorted in descending order else false.
	 * </pre>
	 * @throws Exception
	 * @Parameter List of WebElements
	 */
	public static boolean validateDateSorting(
			List<WebElement> dateStringElements) throws Exception {
		boolean isSortedInDescen = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		List<String> dateStringList = new ArrayList<String>();
		List<Date> dateList = new ArrayList<Date>();
		for (WebElement dateStringEle : dateStringElements) {
			dateStringList.add(dateStringEle.getText().trim());
		}
		for (String dateString : dateStringList) {
			dateList.add(simpleDateFormat.parse(dateString));
		}
		List<Date> dateListOriginalOrder = new ArrayList<Date>(dateList);
		System.out
				.println("Copy List to another list:" + dateListOriginalOrder);
		Collections.sort(dateList);
		System.out.println("Natural sorting:" + dateList);
		Collections.reverse(dateList);
		System.out.println("Descending:" + dateList);
		if (dateList.equals(dateListOriginalOrder))
			isSortedInDescen = true;
		else
			isSortedInDescen = false;
		return isSortedInDescen;
	}

	/**
	 * <pre>
	 * This method deletes all cookies.
	 * </pre>
	 * 
	 * @Date 11th-May-2017
	 * @author smykjn
	 * @return void
	 * 
	 *         <pre>
	 * returns true if list is sorted in descending order else false.
	 * </pre>
	 * @throws Exception
	 * @Parameter List of WebElements
	 */
	public static void deleteAllCookies() throws Exception {
		Web.getDriver().manage().deleteAllCookies();
	}

	public static int getRandomNumber(int range) {

		int random = (int) (Math.random() * range + 1);

		return random;

	}

	public static String appendRandomNumberToText(String text) {
		return text + getRandomNumber(7);
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public boolean textEquality(String existingText, String newText,
			boolean exactMatch) {
		if (exactMatch) {
			if (newText.equalsIgnoreCase(existingText))
				return true;
			return false;
		} else if (newText.contains(existingText)) {
			return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * This method Takes you to the specified menu or submenu page.
	 * </pre>
	 * 
	 * @author smykjn
	 */
	public static boolean navigateToProvidedPage(String... specifiedTab)
			throws Exception {
		String bredCrumbValue = "";
		WebElement breadCrumb;
		boolean isPageDisplayed = false;
		Actions act = new Actions(Web.getDriver());
		String xpath1 = "//a[contains(text(),'" + specifiedTab[0]
				+ "')]/following-sibling::ul";
		String xpath2 = "//a[contains(text(),'" + specifiedTab[1]
				+ "')]/following-sibling::ul";
		String xpath3 = "//a[contains(text(),'" + specifiedTab[0]
				+ "')]/following-sibling::ul//a[contains(text(),'"
				+ specifiedTab[1] + "')]";
		String xpath4 = "//a[contains(text(),'" + specifiedTab[1]
				+ "')]/following-sibling::ul//a[.='" + specifiedTab[2] + "']";
		if (Web.getDriver().findElements(By.xpath(xpath1)).size() > 0) {
			act.moveToElement(Web.returnElement(new HomePage(), "Welcome"))
					.build().perform();
			// Web.clickOnElement(menuElement(specifiedTab[0]));
			act.moveToElement(menuElement(specifiedTab[0])).click().build()
					.perform();
			Web.waitForPageToLoad(Web.getDriver());
			if (Web.getDriver().findElements(By.xpath(xpath2)).size() > 0) {
				// Web.clickOnElement(Web.getDriver().findElement(By.xpath(xpath3)));
				act.click(Web.getDriver().findElement(By.xpath(xpath3)))
						.build().perform();
				Web.waitForElements(Web.getDriver().findElements(
						By.xpath(xpath4)));
				if (Web.getDriver().findElements(By.xpath(xpath4)).size() > 0) {
					Web.isWebElementDisplayed(
							Web.getDriver().findElement(By.xpath(xpath4)), true);
					act.click(Web.getDriver().findElement(By.xpath(xpath4)))
							.perform();
					Web.waitForPageToLoad(Web.getDriver());
					bredCrumbValue = specifiedTab[2];
				}
			} else {
				Web.clickOnElement(Web.getDriver()
						.findElement(By.xpath(xpath3)));
				Web.waitForPageToLoad(Web.getDriver());
				bredCrumbValue = specifiedTab[1];
			}
		} else {
			Web.clickOnElement(menuElement(specifiedTab[0]));
			Web.waitForPageToLoad(Web.getDriver());
			bredCrumbValue = specifiedTab[0];
		}
		breadCrumb = Web.getDriver().findElement(By.tagName("i"));
		Web.waitForElement(breadCrumb);
		if (Web.getDriver().findElement(By.tagName("i")).getText()
				.contains(bredCrumbValue))
			isPageDisplayed = true;
		else
			isPageDisplayed = false;
		return isPageDisplayed;
	}

	/*	*//**
	 * <pre>
	 * This method is used to switch to child window in case there is only two window.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return parentWindowID
	 */
	/*
	 * public static String switchToWindow() {
	 * 
	 * Web.clickOnElement(menuElement(specifiedTab[0]));
	 * Web.waitForPageToLoad(Web.getDriver()); bredCrumbValue=specifiedTab[0]; }
	 * breadCrumb = Web.getDriver().findElement(By.tagName("i"));
	 * Web.waitForElement(breadCrumb);
	 * if(Web.getDriver().findElement(By.tagName(
	 * "i")).getText().contains(bredCrumbValue)) isPageDisplayed = true; else
	 * isPageDisplayed = false; return isPageDisplayed; }
	 */

	/**
	 * <pre>
	 * This method is used to switch to child window in case there is only two window.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return parentWindowID
	 */
	public static String switchToWindow() {
		int count = 0;
		String parentWindow = "";
		try {
			parentWindow = Web.getDriver().getWindowHandle();
			while (Web.getDriver().getWindowHandles().size() == 1) {
				if (count == 10)
					break;
				Thread.sleep(500);
				count++;
				System.out.println("Counter : " + count);
			}
			System.out.println("Window size is:"
					+ Web.getDriver().getWindowHandles().size());
			Set<String> chiledWindows = Web.getDriver().getWindowHandles();
			for (String activeWindow : chiledWindows) {
				if (!activeWindow.equals(parentWindow)) {
					Web.getDriver().switchTo().window(activeWindow);
					break;
				}
			}
			return parentWindow;
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL,
					"Exception occured while switching to window.",
					e.getMessage(), true);
			return parentWindow = "";
		}
	}

	/**
	 * <pre>
	 * This method is used to switch to specified frame.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return void
	 */
	/*
	 * public static void switchToFrame(WebElement frameIDorName) { try{
	 * //Web.waitForPageToLoad(Web.getDriver());
	 * Web.getDriver().switchTo().defaultContent();
	 * Web.waitForElement(frameIDorName);
	 * Web.getDriver().switchTo().frame(frameIDorName);
	 * }catch(NoSuchFrameException e){ Reporter.logEvent(Status.FAIL,
	 * "Exception occured while switching to window.",e.getMessage(),true); int
	 * count=0; String parentWindow=""; try{ parentWindow =
	 * Web.getDriver().getWindowHandle();
	 * while(Web.getDriver().getWindowHandles().size()==1) { if(count==10)
	 * break; Thread.sleep(500); count++;
	 * System.out.println("Counter : "+count); }
	 * System.out.println("Window size is:"
	 * +Web.getDriver().getWindowHandles().size()); Set<String> chiledWindows =
	 * Web.getDriver().getWindowHandles(); for(String activeWindow :
	 * chiledWindows){ if(!activeWindow.equals(parentWindow)){
	 * Web.getDriver().switchTo().window(activeWindow);break;} } return
	 * parentWindow; }catch(Exception e){ Reporter.logEvent(Status.FAIL,
	 * "Exception occured while switching to window.",e.getMessage(),true);
	 * return parentWindow=""; } }
	 */

	/**
	 * <pre>
	 * This method is used to switch to specified frame.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return void
	 */
	public static void switchToFrame(WebElement frameIDorName) {
		try {
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().defaultContent();
			Web.waitForElement(frameIDorName);
			Web.getDriver().switchTo().frame(frameIDorName);
		} catch (NoSuchFrameException e) {
			Reporter.logEvent(Status.FAIL,
					"Exception occured while switching to window.",
					e.getMessage(), true);
		}
	}

	/**
	 * <pre>
	 * This method is used to switch to specified frame from another frame.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return void
	 */
	/*
	 * public static void switchToFrameFromAnotherFrame(WebElement
	 * frameFrom,WebElement frameTo) { try{
	 * Web.getDriver().switchTo().defaultContent();
	 * Web.getDriver().switchTo().frame(frameFrom); Web.waitForElement(frameTo);
	 * Web.getDriver().switchTo().frame(frameTo); }catch(NoSuchFrameException
	 * e){ Reporter.logEvent(Status.FAIL,
	 * "Exception occured while switching to window.",e.getMessage(),true); } }
	 */

	/**
	 * This method returns the broser name and version in runtime execution
	 * 
	 * @author smykjn
	 * @return
	 */
	/*
	 * public static String getBrowserName(){ Capabilities caps =
	 * ((RemoteWebDriver) Web.getDriver()).getCapabilities(); String browserName
	 * = caps.getBrowserName(); String browserVersion = caps.getVersion();
	 * System.out.println("Browser name:"+browserName);
	 * System.out.println("Browser version:"+browserVersion); return
	 * browserName; }
	 */

	/**
	 * <pre>
	 * This method returns the browser name and version in runtime execution
	 * </pre>
	 * 
	 * @author smykjn
	 * @return
	 */
	/*
	 * public static void waitForLoader(WebElement loader) throws Exception{
	 * Web.waitForElement(loader); do{ Thread.sleep(1000);
	 * System.out.println("Loading......................");
	 * }while(loader.isDisplayed()); }
	 */

	/**
	 * <pre>
	 * This method sorts list<INTEGER> in descending order.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return
	 */
	/*
	 * public static boolean sortIntegerListDesc(List<Double> expSortedList)
	 * throws Exception{ boolean isSorted = false; List<Double> copyList = new
	 * ArrayList<Double>(expSortedList);
	 * System.out.println("List copied:"+copyList); Collections.sort(copyList);
	 * System.out.println("copied List in ascending order:"+copyList);
	 * Collections.reverse(copyList);
	 * System.out.println("copied List in descending order:"+copyList);
	 * System.out.println("Original List:"+expSortedList);
	 * if(expSortedList.equals(copyList)) isSorted = true; else isSorted =
	 * false; return isSorted; }
	 */

	/**
	 * @author smykjn
	 * @param downloadDir
	 * @param fileExtension
	 * @return downloaded file name
	 */
	/*
	 * public static String getDownloadedDocumentName(String downloadDir, String
	 * fileExtension) { String downloadedFileName = null; boolean valid = true;
	 * boolean found = false;
	 * 
	 * //default timeout in seconds long timeOut = 20; try { Path
	 * downloadFolderPath = Paths.get(downloadDir); WatchService watchService =
	 * FileSystems.getDefault().newWatchService();
	 * downloadFolderPath.register(watchService,
	 * StandardWatchEventKinds.ENTRY_CREATE); long startTime =
	 * System.currentTimeMillis(); do { WatchKey watchKey; watchKey =
	 * watchService.poll(timeOut,TimeUnit.SECONDS); long currentTime =
	 * (System.currentTimeMillis()-startTime)/1000; if(currentTime>timeOut) {
	 * System.out.println(
	 * "Download operation timed out.. Expected file was not downloaded");
	 * return downloadedFileName; }
	 * 
	 * for (WatchEvent event : watchKey.pollEvents()) }
	 */

	/**
	 * <pre>
	 * This method is used to switch to specified frame from another frame.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return void
	 */
	public static void switchToFrameFromAnotherFrame(WebElement frameFrom,
			WebElement frameTo) {
		try {
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(frameFrom);
			Web.getDriver().switchTo().frame(frameTo);
		} catch (NoSuchFrameException e) {
			Reporter.logEvent(Status.FAIL,
					"Exception occured while switching to window.",
					e.getMessage(), true);
		}
	}

	/**
	 * This method returns the broser name and version in runtime execution
	 * 
	 * @author smykjn
	 * @return
	 */
	public static String getBrowserName() {
		Capabilities caps = ((RemoteWebDriver) Web.getDriver())
				.getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		System.out.println("Browser name:" + browserName);
		System.out.println("Browser version:" + browserVersion);
		return browserName;
	}

	/**
	 * <pre>
	 * This method returns the browser name and version in runtime execution
	 * </pre>
	 * 
	 * @author smykjn
	 * @return
	 */
	public static void waitForLoader(WebElement loader) throws Exception {
		Web.waitForElement(loader);
		do {
			Thread.sleep(1000);
			System.out.println("Loading......................");
		} while (loader.isDisplayed());
	}

	/**
	 * <pre>
	 * This method sorts list<INTEGER> in descending order.
	 * </pre>
	 * 
	 * @author smykjn
	 * @return
	 */
	public static boolean sortIntegerListDesc(List<Double> expSortedList)
			throws Exception {
		boolean isSorted = false;
		List<Double> copyList = new ArrayList<Double>(expSortedList);
		System.out.println("List copied:" + copyList);
		Collections.sort(copyList);
		System.out.println("copied List in ascending order:" + copyList);
		Collections.reverse(copyList);
		System.out.println("copied List in descending order:" + copyList);
		System.out.println("Original List:" + expSortedList);
		if (expSortedList.equals(copyList))
			isSorted = true;
		else
			isSorted = false;
		return isSorted;
	}

	/**
	 * @author smykjn
	 * @param downloadDir
	 * @param fileExtension
	 * @return downloaded file name
	 */
	public static String getDownloadedDocumentName(String downloadDir,
			String fileExtension) {
		String downloadedFileName = null;
		boolean valid = true;
		boolean found = false;

		// default timeout in seconds
		long timeOut = 20;
		try {
			Path downloadFolderPath = Paths.get(downloadDir);
			WatchService watchService = FileSystems.getDefault()
					.newWatchService();
			downloadFolderPath.register(watchService,
					StandardWatchEventKinds.ENTRY_CREATE);
			long startTime = System.currentTimeMillis();
			do {
				WatchKey watchKey;
				watchKey = watchService.poll(timeOut, TimeUnit.SECONDS);
				long currentTime = (System.currentTimeMillis() - startTime) / 1000;
				if (currentTime > timeOut) {
					System.out
							.println("Download operation timed out.. Expected file was not downloaded");
					return downloadedFileName;
				}

				for (WatchEvent event : watchKey.pollEvents()) {
					WatchEvent.Kind kind = event.kind();
					if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
						String fileName = event.context().toString();
						System.out.println("New File Created:" + fileName);
						if (fileName.endsWith(fileExtension)) {
							downloadedFileName = fileName;
							System.out
									.println("Downloaded file found with extension "
											+ fileExtension
											+ ". File name is "
											+ fileName);
							Thread.sleep(500);
							found = true;
							break;
						}
					}
				}
				if (found) {
					return downloadedFileName;
				} else {
					currentTime = (System.currentTimeMillis() - startTime) / 1000;
					if (currentTime > timeOut) {
						System.out.println("Failed to download expected file");
						return downloadedFileName;
					}
					valid = watchKey.reset();
				}
			} while (valid);
		}

		catch (InterruptedException e) {
			System.out.println("Interrupted error - " + e.getMessage());
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out
					.println("Download operation timed out.. Expected file was not downloaded");
		} catch (Exception e) {
			System.out.println("Error occured - " + e.getMessage());
			e.printStackTrace();
		}
		return downloadedFileName;
	}

	/**
	 * <pre>
	 * This method is to validate if user is assigned with particular transaction code/codes.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param Resultset
	 *            which should return distinct transaction codes
	 * @param String
	 *            [] of expected transaction codes
	 */
	public static boolean isTxnCodesPresent(ResultSet resultSet,
			String... expTxnCodes) throws SQLException {
		boolean isPresent = false;
		List<String> expTxnCodesList = Arrays.asList(expTxnCodes);
		List<String> acttxncodes = new ArrayList<String>();

		while (resultSet.next()) {
			acttxncodes.add(resultSet.getString("TXN_CODE"));
		}
		System.out.println("ActTxn codes:" + acttxncodes);
		System.out.println("ExpTxn codes:" + expTxnCodes);
		if (acttxncodes.size() > 0) {
			if (expTxnCodesList.equals(acttxncodes))
				isPresent = true;
			else
				isPresent = false;
		}
		return isPresent;
	}

	/**
	 * <pre>
	 * This method is to insert a Txn code for a specified uscs id.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param txncode
	 * @param uscsId
	 */
	public static void insertTxnCode(String txncode, String uscsId)
			throws SQLException {
		DB.executeQuery(Stock.getTestQuery("insertSpecifiedTxnCode")[0],
				Stock.getTestQuery("insertSpecifiedTxnCode")[1], txncode,
				uscsId);

	}

	/**
	 * <pre>
	 * This method is to know the list of Uscs ids to which specific txn codes are assigned with.
	 * </pre>
	 * 
	 * @author smykjn
	 * @param resultSet
	 * @return : list of Uscs ids
	 */
	public static List<String> getUscsIDForTxnCodes(ResultSet resultSet)
			throws SQLException {
		ArrayList<String> uscsId = new ArrayList<String>();
		while (resultSet.next()) {
			uscsId.add(resultSet.getString("USCS_ID"));
		}
		System.out.println("Assigned uscs ids for txn codes :" + uscsId);
		return uscsId;
	}

	/**
	 * <pre>
	 * Return date in specific time zone.
	 * </pre>
	 * 
	 * @author smykjn
	 */
	public static Date getSysDateWithTimeZone(String timezone) {
		Date date = null;
		Calendar present;
		try {
			SimpleDateFormat isoFormat = new SimpleDateFormat();
			isoFormat.setTimeZone(TimeZone.getTimeZone(timezone));
			present = Calendar.getInstance();
			present.setTime(present.getTime());
			date = present.getTime();
		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occurred.",
					e.getMessage(), true);
		}
		return date;
	}

	/**
	 * @author smykjn
	 */
	public static String getDateStringInDateFormat(String dateformat, Date date) {
		String dateString = "";
		try {
			DateFormat df = new SimpleDateFormat(dateformat);
			dateString = df.format(date);

		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occurred.",
					e.getMessage(), true);
		}
		return dateString;
	}

	/**
	 * @author smykjn
	 */
	public static Date getDateInDateFormatFromDateString(String dateformat,
			String dateString) {
		Date date = null;
		try {
			DateFormat df = new SimpleDateFormat(dateformat);
			date = df.parse(dateString);

		} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Exception occurred.",
					e.getMessage(), true);
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * <pre>
	 * This method let user select a random date between two specified dates
	 * </pre>
	 * 
	 * @author smykjn
	 */
	/*
	 * public static Date getRandomDateBetweenTwoDates(Date startRange,Date
	 * endRange) throws Exception{ List<Date> listDate = new ArrayList<Date>();
	 * Calendar calendar = new GregorianCalendar();
	 * calendar.setTime(startRange); int count=0; while
	 * (calendar.getTime().before(endRange)) { calendar.add(Calendar.DATE, 1);
	 * Date result = calendar.getTime();
	 * if(calendar.getTime().before(endRange)){ count++; listDate.add(result); }
	 * } System.out.println("Number of days:"+count);
	 * System.out.println("Dates:"+listDate); int randomNumber =
	 * CommonLib.getRandomNumber(listDate.size());
	 * 
	 * return listDate.get(randomNumber);
	 * 
	 * }
	 * 
	 * 
	 * public static Date calculateDay(int offset) { final Calendar cal =
	 * Calendar.getInstance(); if(offset!=0){ cal.add(Calendar.DATE, offset);
	 * return cal.getTime();} else { return cal.getTime(); } }
	 */

	/*
	 * public static String getDate(String dateformat,int offset){
	 * 
	 * String date=null; try { DateFormat df = new SimpleDateFormat(dateformat);
	 * date= df.parse(dateString);
	 * 
	 * }catch(Exception e){
	 * Reporter.logEvent(Status.FAIL,"Exception occurred.",e.getMessage(),
	 * true); e.printStackTrace(); } return date; }
	 */

	/**
	 * <pre>
	 * This method let user select a random date between two specified dates
	 * </pre>
	 * 
	 * @author smykjn
	 */
	public static Date getRandomDateBetweenTwoDates(Date startRange,
			Date endRange) throws Exception {
		List<Date> listDate = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startRange);
		int count = 0;
		while (calendar.getTime().before(endRange)) {
			calendar.add(Calendar.DATE, 1);
			Date result = calendar.getTime();
			if (calendar.getTime().before(endRange)) {
				count++;
				listDate.add(result);
			}
		}
		System.out.println("Number of days:" + count);
		System.out.println("Dates:" + listDate);
		int randomNumber = CommonLib.getRandomNumber(listDate.size());

		return listDate.get(randomNumber);

	}

	public static Date calculateDay(int offset) {
		final Calendar cal = Calendar.getInstance();
		if (offset != 0) {
			cal.add(Calendar.DATE, offset);
			return cal.getTime();
		} else {
			return cal.getTime();
		}
	}

	public static String getDate(String dateformat, int offset) {
		String date = null;
		DateFormat df = new SimpleDateFormat(dateformat);
		df.setLenient(false);
		try {
			date = df.format(calculateDay(offset));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Formatted date is:" + date);
		return date;
	}

	/**
	 * <pre>
	 * This method validates that event table is having entry with 
	 * specified event id(confirmation number in UI)
	 * </pre>
	 * 
	 * @author smykjn
	 */
	/*
	 * public static boolean validateEventID(String evenId) throws SQLException
	 * { boolean isRecordFound = false; String dbName =
	 * getUserDBName(Stock.GetParameterValue("username")) +
	 * "DB_"+CommonLib.checkEnv(Stock.getConfigParam("TEST_ENV"));
	 * queryResultSet = DB.executeQuery(dbName,
	 * Stock.getTestQuery("validateEventId")[1],evenId);
	 * if(DB.getRecordSetCount(queryResultSet)>0) isRecordFound=true; else
	 * isRecordFound=false; return isRecordFound; } df.setLenient(false); try{
	 * date = df.format(calculateDay(offset)); } catch(Exception e) {
	 * System.out.println (e.getMessage()) ; }
	 * System.out.println("Formatted date is:"+date); return date; }
	 */

	/**
	 * <pre>
	 * This method validates that event table is having entry with specified event id(confirmation number in UI)
	 * </pre>
	 * 
	 * @author smykjn
	 */
	public static boolean validateEventID(String evenId) throws SQLException {
		boolean isRecordFound = false;
		queryResultSet = DB.executeQuery(
				CommonLib.getUserDBName(Stock.GetParameterValue("username"))
						+ "DB_"
						+ CommonLib.checkEnv(Stock.getConfigParam("TEST_ENV")),
				Stock.getTestQuery("validateEventId")[1], evenId);
		if (DB.getRecordSetCount(queryResultSet) > 0)
			isRecordFound = true;
		else
			isRecordFound = false;
		return isRecordFound;

	}

	public static String getUserRole(String[] getUserRoleQuery, String userName) {
		String userRole = null;
		try {
			queryResultSet = DB.executeQuery(getUserRoleQuery[0],
					getUserRoleQuery[1], "K_" + userName);
			while (queryResultSet.next()) {
				userRole = queryResultSet.getString("ROLE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userRole;
	}

	public static void switchToDefaultPlanPartnerLinkUserAndNumberOfPlansLessThan25()
			throws Exception {
		accountVerPage = new AccountVerificationPage();
		reportsPage = new ReportsPage();
		String userRole = getUserRole(Stock.getTestQuery("getUserRoleQuery"),
				Stock.GetParameterValue("username"));
		int numberOfPlans = accountVerPage.getNumberOfplans(
				Stock.getTestQuery("getNumberOfplansQuery"),
				"K_" + Stock.GetParameterValue("username"));
		if (userRole.contains("PL") && numberOfPlans >= 1
				&& numberOfPlans <= 25) {
			if (Web.isWebElementDisplayed(new HomePage(), "Plan Drop Down",
					false)) {
				Web.clickOnElement(new HomePage(), "Plan Drop Down Go Button");
			}
		} else
			throw new Exception(
					"User is not PL user or user has more than 25 plans");

	}

	/**
	 * <pre>
	 * Method to check if a given transaction code already exists in user_auth_txn table<br> for any USCS ID of a given user</br>
	 * </pre>
	 * 
	 * @param query
	 *            to be used
	 * @param userName
	 *            given user
	 * @param txnCode
	 *            given transaction code
	 * @return
	 */
	public static boolean isTxnCode(String[] query, String userName,
			String txnCode) {
		try {
			queryResultSet = DB.executeQuery(query[0], query[1], "K_"
					+ userName, txnCode);
			int rowCount = 0;
			while (queryResultSet.next()) {
				if (queryResultSet.last()) {
					rowCount = queryResultSet.getRow();
					if (rowCount > 0)
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <pre>
	 * Method to insert the transaction code for a given USCS ID. Before inserting <br>
	 * it verifies if txn code exists for given user otherwise it inserts</br>
	 * 
	 * <pre>
	 * @param query insert query to be used
	 * @param userName User name 
	 * @param txnCode Transaction code to be inserted.
	 * @param uscsId
	 * 
	 * <pre>
	 * USCS id for which txn code need to be inserted.This can be <br>fetched from <b>user#user_class</b> table</br>
	 * </pre>
	 * 
	 * @return
	 */
	public static boolean insertTxnCode(String[] query, String userName,
			String txnCode, String uscsId) {
		try {
			if (isTxnCode(Stock.getTestQuery("getTransactionCodeAvailability"),
					userName, txnCode))
				return true;
			else {
				int rowsUpdated = DB.executeUpdate(query[0], query[1], txnCode,
						uscsId, "0", "N");
				if (rowsUpdated > 0)
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getIterationDataAsString(Map<String, String> testdata) {
		String result = "";
		for (Map.Entry<String, String> td : testdata.entrySet()) {
			result = result + (td.getKey() + " : " + td.getValue()) + " | ";
		}
		return result.substring(0, result.lastIndexOf("|"));
	}

	public static void FrameSwitchONandOFF(boolean switchFrame,
			WebElement... frm) {
		if (frm.length > 0) {
			if (Web.isWebElementDisplayed(frm[0], false) && switchFrame) {
				Web.getDriver().switchTo().frame(frm[0]);
			}
		}
		if (!switchFrame) {
			Web.getDriver().switchTo().defaultContent();
		}
	}

	public static int parse(String str) {
		Number number = null;
		try {
			number = Float.parseFloat(str);
		} catch (NumberFormatException e) {
			try {
				number = Double.parseDouble(str);
			} catch (NumberFormatException e1) {
				try {
					number = Integer.parseInt(str);
				} catch (NumberFormatException e2) {
					try {
						number = Long.parseLong(str);
					} catch (NumberFormatException e3) {
						throw e3;
					}
				}
			}
		}
		return number.intValue();
	}
	
	/**
	    * Searches an array of words for a given value using a recursive binary 
	    * search.  Returns the index that contains the value or -1 if the value 
	    * is not found.
	    * @param words
	    * @param value
	    * @return
	    */    
	   public static int binarySearch(String[] words, String value, int min, int max) {
	       if (min > max) {
	           return -1;
	       }
	       
	       int mid = (max + min) / 2;
	       
	       if (words[mid].equals(value)) {
	           return mid;
	       } else if(words[mid].compareTo(value) > 0) {
	           return binarySearch(words, value, min, mid - 1);
	       } else {
	           return binarySearch(words, value, mid + 1, max);
	       }
	   }
	   
		/**
	    * Searches an array of words for a given value using a recursive binary 
	    * search.  Returns the index that contains the value or -1 if the value 
	    * is not found.
	    * @param words
	    * @param value
	    * @return
	    */    
	   public static int binarySearch(List<String> words, String value, int min, int max) {
	       if (min > max) {
	           return -1;
	       }
	       
	       int mid = (max + min) / 2;
	       
	       if (words.get(mid).equals(value)) {
	           return mid;
	       } else if(words.get(mid).compareTo(value) > 0) {
	           return binarySearch(words, value, min, mid - 1);
	       } else {
	           return binarySearch(words, value, mid + 1, max);
	       }
	   }

}