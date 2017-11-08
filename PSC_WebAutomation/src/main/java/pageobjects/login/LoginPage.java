package pageobjects.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.DB;
import lib.Stock;
import lib.Web;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

public class LoginPage extends LoadableComponent<LoginPage> {

	// Object Declarations

	@FindBy(id = "username")
	private WebElement txtUserName;
	@FindBy(id = "password")
	private WebElement txtPassword;
	@FindBy(id = "helpBlock")
	private WebElement weHelpBlock;
	@FindBy(xpath = ".//*[@href='#/loginHelp' and text()='Forgot Password?']")
	private WebElement lnkForgotPassword;
	@FindBy(css = "a[href*='register']")
	private WebElement btnRegister;
	@FindBy(css = "footer div[role='navigation'] ul li")
	private List<WebElement> weFooterLinkListPreLogin;
	@FindBy(css = "div[class='col-sm-6 col-md-5 no-pad-right'] span[class='tagline text-uppercase']")
	private WebElement wePSCHeader;
	@FindBy(css = "div[class='breadcrumbs'] strong")
	private WebElement wePSCBreadCrum;
	/*@FindBy(css = "div[class='breadcrumbs'] a")
	private WebElement linkPSCBreadCrumHome;*/
	@FindBy(css = "div[class='container'] a")
	private WebElement linkPSCBreadCrumHome;
	@FindBy(css = "iframe[id='mpwr_login']")
	private WebElement frmLogin;
	@FindBy(css = "button[id='login']")
	private WebElement btnLogin;
	@FindBy(css = "ul[class='list-inline'] li")
	private List<WebElement> weHeaderLinkListPreLogin;
	@FindBy(css = "site-header span[class='site-tagline ng-binding']")
	private WebElement weSiteTagLine;
	@FindBy(xpath = ".//*[@id='headNav']/li[2]/a")
	private WebElement linkLogOut;
	@FindBy(css = "input[class = 'chekbox']")
	private WebElement chkBoxrememberPassword;
	@FindBy(css = "div[id = 'errorMessage']")
	private WebElement wePreLoginError;
	@FindBy(css = "a[class = 'prelogin-pod']")
	private WebElement lnkPlanFeeds;
	@FindBy(css = "img[src *= 'redirect_logo.png']")
	private WebElement imgGWRS;
	@FindBy(css = "input[id = 'welcomeForm:username']")
	private WebElement txtForceLoginUsernameField;
	@FindBy(css = "img[alt = 'Empower Retirement']")
	private WebElement logoEmpower;
	@FindBy(xpath = ".//*[@id='loginSpinner']")
	private WebElement loginSpinner;
	ResultSet queryResultSet;
	LoadableComponent<?> parent;
	/*-----------------------------------------------------------------*/

	private List<String> getFooterLinkList = null;
	ArrayList<String> uscsIDsForUser;

	public LoginPage() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertEquals(Web.getDriver().getTitle(), "Empower Retirement - Plan Service Center");
	}

	@Override
	protected void load() {
		Web.getDriver().get(Stock.getConfigParam("AppURL"+"_"+Stock.getConfigParam("TEST_ENV")));	
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	USERNAME - [TEXTBOX]
	 * 	PASSWORD - [TEXTBOX]
	 *  SIGN IN -[BUTTON]
	 *  REGISTER - [BUTTON]
	 *  FORGOT PASSWORD - [LINK]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		// Username
		if (fieldName.trim().equalsIgnoreCase("USERNAME")) {
			return this.txtUserName;
		}
		if(fieldName.trim().equalsIgnoreCase("PreLoginErrorMsg")){
			return this.wePreLoginError;
		}
		if (fieldName.trim().equalsIgnoreCase("PASSWORD")) {
			return this.txtPassword;
		}

		if (fieldName.trim().equalsIgnoreCase("SIGN IN")) {
			return this.btnLogin;
		}

		if (fieldName.trim().equalsIgnoreCase("REGISTER")) {
			return this.btnRegister;
		}

		if (fieldName.trim().equalsIgnoreCase("FORGOT PASSWORD")) {
			return this.lnkForgotPassword;
		}

		if (fieldName.trim().equalsIgnoreCase("FORCELOGIN USERNAME")) {
			return this.txtForceLoginUsernameField;
		}

		if (fieldName.trim().equalsIgnoreCase("GWRS IMAGE")) {
			return this.imgGWRS;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGIN FRAME")) {
			return this.frmLogin;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGO EMPOWER")) {
			return this.logoEmpower;
		}

		// Reporter.logEvent(
		// Status.WARNING,
		// "Get WebElement for field '" + fieldName + "'",
		// "No WebElement mapped for this field\nPage: <b>
		// "+this.getClass().getName()+"</b>"
		// ,true);
		return null;

	}

	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 * @throws InterruptedException
	 */
	public void submitLoginCredentials(String[] loginData) {
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(frmLogin);
		Web.setTextToTextBox(this.txtUserName, loginData[0]);
		Web.setTextToTextBox(this.txtPassword, loginData[1]);
		Web.clickOnElement(this.btnLogin);
		Reporter.logEvent(Status.PASS, "Submit login credentials", "Login credentials submitted successfully", false);
		Web.getDriver().switchTo().defaultContent();
		try {
			Web.waitForPageToLoad(Web.getDriver());
			Web.ispageloaded("");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			ThrowException.Report(TYPE.INTERRUPTED, "Exception occurred for thread sleep");
		}
	}

	/**
	 * <pre>
	 * Method to validate if entered login credentials are invalid and an error message is displayed
	 * Returns the error message displayed if an error block is displayed
	 * Returns empty string if no error block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed error message
	 */
	public String getErrorMessageText() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.weHelpBlock);
		if (isElementPresent)
			return this.weHelpBlock.getText();
		else
			return "";
	}

	/**
	 * Method to open each footer link before login and validate if the
	 * respective page is opening
	 * 
	 * @throws Exception
	 */
	public void checkFooterLinkPreLogin() throws Exception {
		checkHeaderFooterLink(weFooterLinkListPreLogin, "link_Footer");
	}

	/**
	 * Method to open each header link before login and validate if the
	 * respective page is opening
	 */
	public void checkHeaderLinkPreLogin() {
		checkHeaderFooterLink(weHeaderLinkListPreLogin, "link_Header");
		Web.getDriver().navigate().back();
	}

	/**
	 * Method to get link text for each footer link before link
	 */
	private void getPreLoginFooterList() {
		List<String> footerLinkText = new LinkedList<String>();
		for (int iLoopCnt = 0; iLoopCnt <= weFooterLinkListPreLogin.size() - 1; iLoopCnt++) {
			footerLinkText.add(weFooterLinkListPreLogin.get(iLoopCnt).findElement(By.cssSelector("a")).getText());
		}
		setPreLoginFooterLinkList(footerLinkText);
	}

	/**
	 * This method verifies the error messages pre login for invalid credentials
	 * @param errorMsg
	 */
	public void verifyErrorforRespectiveLogin(String errorMsg) {
		boolean textMatch = false;
		try {
			new LoginPage();
			Web.getDriver().switchTo().frame(frmLogin);	
			Web.isWebElementDisplayed(wePreLoginError, true);
			textMatch = Web.VerifyPartialText(errorMsg, wePreLoginError.getText(), true);

			if(textMatch){
				Reporter.logEvent(Status.PASS, "Check if the appropiate error displayed",
						"Expected Error message displayed: "+wePreLoginError.getText(), false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if the appropiate error displayed",
						"Failed to display expected error message", true);
			}		
			Web.getDriver().switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for getFooterLinkList
	 */
	public List<String> getPreLoginFooterLinkList() {
		getPreLoginFooterList();
		return getFooterLinkList;
	}

	/**
	 * Setter for getFooterLinkList
	 */
	public void setPreLoginFooterLinkList(List<String> getFooterLinkList) {
		this.getFooterLinkList = getFooterLinkList;
	}

	/**
	 * Method to click on each Header and Footer links and validate if the
	 * redirection is correct
	 * 
	 * @param linkObjList
	 *            : WebElement list
	 * @param testDataColNm
	 *            : Test Data column name
	 * 
	 */
	private void checkHeaderFooterLink(List<WebElement> linkObjList, String testDataColNm) {
		String currentURL = "";
		String testData;
		int linkObjSize = linkObjList.size();
		try{
			//Looping through the list of Header/Footer links
			for (int iLoopCnt = 0; iLoopCnt < linkObjSize - 1; iLoopCnt++) {
				new LoginPage();
				testData = Stock.GetParameterValue(testDataColNm + (iLoopCnt + 1));
				Thread.sleep(500);
				if (Web.isWebElementDisplayed(linkObjList.get(iLoopCnt).findElement(By.cssSelector("a")), true)) {
					Web.clickOnElement((linkObjList.get(iLoopCnt).findElement(By.cssSelector("a"))));
					Thread.sleep(3000);
				}

				if ((testDataColNm + (iLoopCnt + 1)).equals("link_Footer7")) {
					String winHandleBefore = Web.getDriver().getWindowHandle();
					for (String winHandle : Web.getDriver().getWindowHandles()) {
						Web.getDriver().switchTo().window(winHandle);
						currentURL = Web.getDriver().getCurrentUrl();
					}
					Web.getDriver().close();
					Web.getDriver().switchTo().window(winHandleBefore);
				} else {
					currentURL = Web.getDriver().getCurrentUrl();
				}

				// Verification against redirected URLs

				if((Web.VerifyPartialText(testData, currentURL, false))){
					Reporter.logEvent(Status.PASS,"Check if the respective page is loaded",
							"Verified that the respective page :" + testData + " is loaded successfully", false);
				}else{
					Reporter.logEvent(Status.FAIL,"Check if the respective page is loaded",
							"Respective page :" + testData + " is not loaded", true);
				}

				if (!(testDataColNm + (iLoopCnt + 1)).equals("link_Footer7")
						&& !(testDataColNm + (iLoopCnt + 1)).equals("link_Header2")) {
					Web.waitForElement(linkPSCBreadCrumHome);
					linkPSCBreadCrumHome.click();
				}
			}
		}catch(Exception e){
			throw new Error("Error occurred while checking " +
					testDataColNm.split("link_")[1] +
					" links , Exception:"+e.getMessage());
		}
	}

	public void waitForSuccessfulLogin() throws InterruptedException
	{
		if(Web.isWebElementDisplayed(frmLogin))
		{
			Web.getDriver().switchTo().frame(frmLogin);
			do
			{
				Thread.sleep(3000);	
			}while(Web.isWebElementDisplayed(loginSpinner));
		}
		Web.getDriver().switchTo().defaultContent();
	}

	/**
	 * @param updateInvalidLogonAttemptQuery
	 * @param userName
	 */
	public void updateInvalidLogonAttempt(String[] updateInvalidLogonAttemptQuery,String userName)
	{
		try{
			int rows_update=DB.executeUpdate(updateInvalidLogonAttemptQuery[0], updateInvalidLogonAttemptQuery[1], "K_" + userName);
			if(rows_update==1)
			{
				Reporter.logEvent(Status.PASS, "Execute query to update Invalid_logon_count in ISIS_Password table", 
						"Query is executed successfully", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Execute query to update Invalid_logon_count in ISIS_Password table", 
						"Query did not execut successfully", false);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param queryToTerminateUser
	 * @param termDate
	 * @param userName
	 */
	public void updateTermDateForUser(String[] queryToTerminateUser, String termDate, String userName)
	{
		try{
			int rows_update=DB.executeUpdate(queryToTerminateUser[0], queryToTerminateUser[1], termDate,"K_" + userName);
			if(rows_update==1)
			{
				Reporter.logEvent(Status.PASS, "Execute query to update termdate in Users table", 
						"Query is executed successfully", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Execute query to update termdate in Users table", 
						"Query did not execut successfully", false);

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * @author smykjn
	 * @return boolean
	 */
	public boolean checkPlanTabTxnCodes() throws SQLException
	{
		boolean isAllTxnCodesDisplayed = false;
		String[] txnCodes = new String[10];
		queryResultSet = DB.executeQuery(Stock.getTestQuery("checkPlanTxnCodes")[0],
				Stock.getTestQuery("checkPlanTxnCodes")[1],"K_"+Stock.GetParameterValue("username"));
		int count=0;
		while(queryResultSet.next()){
			txnCodes[count] = queryResultSet.getString("TXN_CODE");
			count++;
		}
		System.out.println("Number of txnocdes:"+txnCodes.length);
		if(Arrays.asList(txnCodes).contains("PSOVPG")&&Arrays.asList(txnCodes).contains("PSWVRS")&&
					Arrays.asList(txnCodes).contains("PSPROV")&&Arrays.asList(txnCodes).contains("PSVSCH")&&
					Arrays.asList(txnCodes).contains("PSOVMN")&&Arrays.asList(txnCodes).contains("PSCTOD")&&
					Arrays.asList(txnCodes).contains("EMFIAR")&&Arrays.asList(txnCodes).contains("ESCPPE")&&
					Arrays.asList(txnCodes).contains("ESCVPE")&&Arrays.asList(txnCodes).contains("PSCCTR"))
			isAllTxnCodesDisplayed = true;
		else
			isAllTxnCodesDisplayed = false;	
		
		return isAllTxnCodesDisplayed;
	}
	
	
	/**
	 * <pre>This method deletes below transaction codes from all user types assigned to a particular PSC user.
	 * 'PSWVRS','PSPROV','PSVSCH','PSCTOD','EMFIAR','ESCPPE'</pre>
	 * @author smykjn
	 * @Date 1st-June-2017
	 * @return void
	 * 
	 */
	public void deletePlanMenuTxnCodes1() throws SQLException
	{
		uscsIDsForUser = new ArrayList<String>();
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUserClassesForAUser1")[0],
				Stock.getTestQuery("getUserClassesForAUser1")[1],"K_"+Stock.GetParameterValue("username"));
		while(queryResultSet.next()){
			uscsIDsForUser.add(queryResultSet.getString("USCS_ID"));
		}
		System.out.println("User classes for a user:"+uscsIDsForUser);
		queryResultSet = DB.executeQuery(Stock.getTestQuery("deletePlanTxnCodes")[0],
				Stock.getTestQuery("deletePlanTxnCodes")[1],"K_"+Stock.GetParameterValue("username"));
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUserClassesForAUser1")[0],
				Stock.getTestQuery("getUserClassesForAUser1")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(queryResultSet)==0)
			Reporter.logEvent(Status.PASS, "Delete following txn codes from DB from all user types assigned to a user."
					+ "'PSWVRS','PSPROV','PSVSCH','PSCTOD','EMFIAR','ESCPPE'","Txn codes are "
							+ "deleted.", false);
		else
			Reporter.logEvent(Status.FAIL, "Delete following txn codes from DB from all user types assigned to a user."
					+ "'PSWVRS','PSPROV','PSVSCH','PSCTOD','EMFIAR','ESCPPE'","Txn codes are "
							+ "not deleted.", false);
	}
	
	/**
	 * <pre>This method inserts below transaction codes to all user types assigned to a particular PSC user.
	 * 'PSWVRS','PSPROV','PSVSCH','PSCTOD','EMFIAR','ESCPPE','ESCVPE','PSCCTR'</pre>
	 * @author smykjn
	 * @Date 1st-June-2017
	 * @return void
	 */
	public void insertPlanMenuTxnCodes() throws SQLException
	{
		String[] txnCodes = new String[]{"PSWVRS","PSPROV","PSVSCH","PSCTOD","EMFIAR","ESCPPE"};
		for(int i=0;i<uscsIDsForUser.size();i++){
			for(int j=0;j<txnCodes.length;j++)
				DB.executeQuery(Stock.getTestQuery("insertTxnCodesForPlanModule")[0],
						Stock.getTestQuery("insertTxnCodesForPlanModule")[1],txnCodes[j],uscsIDsForUser.get(i));
		}
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUserClassesForAUser1")[0],
				Stock.getTestQuery("getUserClassesForAUser1")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(queryResultSet)>0)
			Reporter.logEvent(Status.INFO,"All txn codes are inserted back.","",false);
		else
			Reporter.logEvent(Status.WARNING,"All txn codes are not inserted back.","",false);
		queryResultSet = DB.executeQuery(Stock.getTestQuery("insertTxnCodesESCVPE")[0],
				Stock.getTestQuery("insertTxnCodesESCVPE")[1],"K_"+Stock.GetParameterValue("username"));
		
	}
	
	/**
	 * <pre>This method deletes below transaction codes from all user types assigned to a particular PSC user.
	 * PSOVPG,PSOVMN</pre>
	 * @author smykjn
	 * @Date 2nd-June-2017
	 * @return void
	 * 
	 */
	public void deletePlanMenuTxnCodes2() throws SQLException
	{
		uscsIDsForUser.clear();
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUserClassesForAUser2")[0],
				Stock.getTestQuery("getUserClassesForAUser2")[1],"K_"+Stock.GetParameterValue("username"));
		while(queryResultSet.next()){
			uscsIDsForUser.add(queryResultSet.getString("USCS_ID"));
		}
		System.out.println("User classes for a user:"+uscsIDsForUser);
		queryResultSet = DB.executeQuery(Stock.getTestQuery("deletePSOVPGandPSOVMNTxnCode")[0],
				Stock.getTestQuery("deletePSOVPGandPSOVMNTxnCode")[1],"K_"+Stock.GetParameterValue("username"));
		
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUserClassesForAUser2")[0],
				Stock.getTestQuery("getUserClassesForAUser2")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(queryResultSet)==0)
			Reporter.logEvent(Status.PASS, "Delete following txn codes from DB from all user types assigned to a user."
					+ "'PSOVPG','PSOVMN'","Txn codes are "
							+ "deleted.", false);
		else
			Reporter.logEvent(Status.FAIL, "Delete following txn codes from DB from all user types assigned to a user."
					+ "'PSOVPG','PSOVMN'","Txn codes are "
							+ "not deleted.", false);
	}
	
	/**
	 * <pre>This method inserts below transaction codes to all user types assigned to a particular PSC user.
	 * 'PSOVPG','PSOVMN'</pre>
	 * @author smykjn
	 * @Date 2nd-June-2017
	 * @return void
	 */
	public void insertPSOVPGandPSOVMNTxnCodes() throws SQLException
	{
		String[] txnCodes = new String[]{"PSOVPG","PSOVMN"};
		for(int i=0;i<uscsIDsForUser.size();i++){
			for(int j=0;j<txnCodes.length;j++)
				DB.executeQuery(Stock.getTestQuery("insertTxnCodesForPlanModule")[0],
						Stock.getTestQuery("insertTxnCodesForPlanModule")[1],txnCodes[j],uscsIDsForUser.get(i));
		}
		queryResultSet = DB.executeQuery(Stock.getTestQuery("getUserClassesForAUser2")[0],
				Stock.getTestQuery("getUserClassesForAUser2")[1],"K_"+Stock.GetParameterValue("username"));
		if(DB.getRecordSetCount(queryResultSet)>0)
			Reporter.logEvent(Status.INFO,"All txn codes are inserted back.","",false);
		else
			Reporter.logEvent(Status.WARNING,"All txn codes are not inserted back.","",false);
		
	}
	

	private Date calculateDay(int offset)
	{
		final Calendar cal = Calendar.getInstance();
		if(offset!=0){
			cal.add(Calendar.DATE, offset);
			return cal.getTime();}
		else
		{
			return cal.getTime();
		}
	}

	private String getDate(String dateformat,int offset)
	{
		String date=null;
		DateFormat df = new SimpleDateFormat(dateformat);
		df.setLenient(false);
		try{
			date = df.format(calculateDay(offset));
		}
		catch(Exception e)
		{
			System.out.println (e.getMessage()) ;
		}
		System.out.println("Formatted date is:"+date);
		return date;
	}

	private boolean updateInvalidLoginDpDate(String[] queryToUpdateInvalidLoginDpDate,
			String dateToUpdate, String userName) throws Exception
	{

		int rowsUpdated = DB.executeUpdate(queryToUpdateInvalidLoginDpDate[0], 
				queryToUpdateInvalidLoginDpDate[1],
				dateToUpdate,userName);
		if(rowsUpdated>0)
			return true;
		else
			return false;


	}

	public boolean isInvalidLoginDpDateUpdated()
	{
		boolean updated = false;
		try{
			updated = updateInvalidLoginDpDate(Stock.getTestQuery("updateInvalidLoginDpDate"),
					getDate("dd-MMM-yy",-1),"K_"+Stock.GetParameterValue("username"));
		}
		catch(Exception e)
		{

		}
		return updated;
	}
}
