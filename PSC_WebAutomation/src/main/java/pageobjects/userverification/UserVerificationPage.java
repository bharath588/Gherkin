package pageobjects.userverification;

import java.sql.ResultSet;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.login.LoginPage;
import lib.DB;
import lib.Reporter;

import com.aventstack.extentreports.*;

import framework.util.CommonLib;
import lib.Web;
import lib.Stock;

public class UserVerificationPage extends LoadableComponent<UserVerificationPage> {

	/* Object declarations for UserVerificationPage */
	@FindBy(css = "input[class *= 'emailBox']")
	private WebElement txtUserVerificationEmail;
	@FindBy(css = "input[class *='answerBox']")
	private WebElement txtUserVerificationSecAns;
	@FindBy(css = "button[class *= 'swap-prelogin-cancel']")
	private WebElement btnUserVerfificationCancel;
	@FindBy(css = "button[class *= 'btn-u next']")
	private WebElement btnUserVerificationNext;
	@FindBy(xpath = ".//*[@id='logo']/img")
	private WebElement imgEmpowerPsc;
	@FindBy(xpath = ".//*[@class='verificationTable']/tbody/tr[1]/td/table/tbody/tr[1]/td[2]/label")
	private WebElement dispUserId;
	@FindBy(css = "div[class = 'ui-growl-item']")
	private WebElement errorMsgBox;
	@FindBy(css = "li[class = 'lastLink'] a")
	private WebElement linkLogOut;
	@FindBy(xpath = ".//*[@id='branding-topnav']/div/div[1]/h2/a/span")
	private WebElement linkPSC;
	@FindBy(xpath = ".//*[@id='gritter-item-1']/div/div[1]")
	private WebElement dismissErrorBox;	
	@FindBy(xpath=".//*[@class='verificationTable']/tbody/tr[1]/td/table/tbody/tr[3]/td[2]/label")
	private WebElement securityQuestion;
	@FindBy(xpath=".//div//input[contains(@id,'changePasswordForm')]")
	private WebElement planTextFieldDefaultPlanNull;
	@FindBy(xpath=".//button//span[text()='Next']")
	private WebElement nextButton;
	/* variable declaration */
	LoadableComponent<?> parent;
	ResultSet resultset;
	private String[] loginData = new String[2];

	public UserVerificationPage() {
		this.parent = new LoginPage();
		PageFactory.initElements(Web.getDriver(), this);
	}

	public UserVerificationPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(Web.getDriver(), this);
	}
	

	@Override
	protected void isLoaded() throws Error {
		Web.getDriver().switchTo().defaultContent();
		try {
			Assert.assertTrue(Web.isWebElementDisplayed(txtUserVerificationEmail,true));
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@Override
	protected void load() {
		LoginPage login = new LoginPage();
		login.get();
		try {
			loginData[0] = Stock.GetParameterValue("username");
			loginData[1] = Stock.GetParameterValue("password");
			login.submitLoginCredentials(loginData);
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		try {
			if(Web.isWebElementDisplayed(txtUserVerificationEmail,true)){
				Reporter.logEvent(Status.INFO,"Loading User Verification Page"
						                     ,"User verfication page loaded",false);
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	EMAIL ADDRESS- [TEXTBOX]
	 * 	SECURITY ANSWER - [TEXTBOX]
	 *  NEXT -[BUTTON]
	 *  CANCEL - [BUTTON]
	 *  CONTACT US - [LINK]
	 *  LOG OUT - [LINK]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */

	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("EMAIL ADDRESS")) {
			return this.txtUserVerificationEmail;
		}

		if (fieldName.trim().equalsIgnoreCase("SECURITY ANSWER")) {
			return this.txtUserVerificationSecAns;
		}

		if (fieldName.trim().equalsIgnoreCase("NEXT")) {
			return this.btnUserVerificationNext;
		}

		if (fieldName.trim().equalsIgnoreCase("CANCEL")) {
			return this.btnUserVerfificationCancel;
		}

		if (fieldName.trim().equalsIgnoreCase("LOG OUT")) {
			return this.linkLogOut;
		}
		if (fieldName.trim().equalsIgnoreCase("DISMISS")) {
			return this.dismissErrorBox;
		}
		if (fieldName.trim().equalsIgnoreCase("ERRORMSG")) {
			return this.errorMsgBox;
		}
		if(fieldName.trim().equalsIgnoreCase("SECURITYQUESTION")){
			return this.securityQuestion;
		}
		if(fieldName.trim().equalsIgnoreCase("DEFAULT_PLAN_INPUT_FIELD")){
			return this.planTextFieldDefaultPlanNull;
		}
		//
		// Reporter.logEvent(
		// Status.WARNING,
		// "Get WebElement for field '" + fieldName + "'",
		// "No WebElement mapped for this field\nPage: <b>
		// "+this.getClass().getName()+"</b>"
		// ,true);
		return null;
	}
	
	/** This method Performs user verification based on the user input 
	 * @throws InterruptedException */
	public void performVerification(String[] userVerfiData) throws InterruptedException {
		new UserVerificationPage();	
		Thread.sleep(3000);
		if (Web.isWebElementDisplayed(txtUserVerificationEmail,true)) {			
			Web.setTextToTextBox(txtUserVerificationEmail, userVerfiData[0]);
			Web.setTextToTextBox(txtUserVerificationSecAns, this.getSecurityAnswer());
			//Web.setTextToTextBox(txtUserVerificationSecAns, userVerfiData[1]);
			Web.clickOnElement(btnUserVerificationNext);
			Web.waitForElement(imgEmpowerPsc);
		}		
		if (!Web.isWebElementDisplayed(txtUserVerificationEmail)) {
				Reporter.logEvent(Status.INFO, "Verify if the user verification screen is loaded",
						"The user verification screen is not loaded", false);
			}else{
				Reporter.logEvent(Status.PASS, "Verify if the user verification screen is loaded",
						"The user verification screen is loaded", true);
			}
		}
	
	/** This method Performs user verification based on the user input 
	 * @throws InterruptedException */
	public void performErrorVerification(String[] userVerfiData) throws InterruptedException {
		new UserVerificationPage();	
		Thread.sleep(3000);
		if (Web.isWebElementDisplayed(txtUserVerificationEmail,true)) {			
			Web.setTextToTextBox(txtUserVerificationEmail, userVerfiData[0]);
			
			Web.setTextToTextBox(txtUserVerificationSecAns, userVerfiData[1]);
			Web.clickOnElement(btnUserVerificationNext);
			Web.waitForElement(imgEmpowerPsc);
		}		
		if (!Web.isWebElementDisplayed(txtUserVerificationEmail)) {
				Reporter.logEvent(Status.INFO, "Verify if the user verification screen is loaded",
						"The user verification screen is not loaded", false);
			}else{
				Reporter.logEvent(Status.PASS, "Verify if the user verification screen is loaded",
						"The user verification screen is loaded", true);
			}
		}

	/**
	 * <pre>
	 * This method verifies the actual user id with the userid displayed on the
	 * user verification screen
	 * </pre>
	 */
	public void verifyUserIdDisplayed(String userName) {
		if((Web.VerifyText(userName, dispUserId.getText(), true))){
			Reporter.logEvent(Status.PASS, "Verify if the userid displayed","The user id is verified successfully",
					false);			
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if the userid displayed", "The user id displayed on screen is not displayed correctly",
					true);
		}
	}

	/**
	 * <pre>
	 * This method checks whether the user id which is entered during logging into the application is 
	 * 	displayed on the user verification page
	 * </pre>
	 * 
	 * @return string
	 */

	public String getErrorMessageText() {
		try{
		if(Stock.getConfigParam("BROWSER").equalsIgnoreCase("IE"))
			Web.waitForPageToLoad(Web.getDriver());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		boolean isElementPresent = Web.isWebElementDisplayed(this.errorMsgBox,true);

		if (isElementPresent)
			return this.errorMsgBox.getText();
		else
			return "";
	}

	/**
	 * This method fetches the email address of user from database
	 * @param getEmailQuery
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public String getEmailAddressOfuser(String[] getEmailQuery, String userid) throws Exception {
		String emailAddr = "";
		getEmailQuery[0]=CommonLib.getUserDBName(Stock.GetParameterValue("username"))
				+ "DB_"+CommonLib.checkEnv(Stock.getConfigParam("TEST_ENV"));
		resultset = DB.executeQuery(getEmailQuery[0], getEmailQuery[1], "K_" + userid);
		if (resultset != null) {
			while (resultset.next()) {
				emailAddr = resultset.getString("EMAIL_ADDRESS");
			}
		}
		return emailAddr;
	}
	
	public String getSecurityQuestion()
	{
		String securityQuestionText = "";
		try{
			if(securityQuestion.getText().contains("car"))
				return securityQuestionText="car";
			else if(securityQuestion.getText().contains("drink"))
				return securityQuestionText="drink";
			else if(securityQuestion.getText().contains("spouse"))
				return securityQuestionText="spouse";
			return
					securityQuestionText;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return securityQuestionText;
	}
	
	public String getSecurityAnswer()
	{
		String securityAnswer = "";
		try{
			if(this.getSecurityQuestion().equalsIgnoreCase("car"))
				//securityAnswer =    Stock.GetParameterValue("dreamCar");
				securityAnswer = "testr";
			else if(this.getSecurityQuestion().equalsIgnoreCase("drink"))
				//securityAnswer = Stock.GetParameterValue("favDrink");
				securityAnswer = "testk";
			else if(this.getSecurityQuestion().equalsIgnoreCase("spouse"))
				//securityAnswer = Stock.GetParameterValue("spouseMidName");
				securityAnswer = "teste";
			else
				securityAnswer = Stock.GetParameterValue("UserSecondaryAns");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return securityAnswer;
	}
	
	
	/**
	 * <pre>This method Enters plan number when a user logs in for whom default plan is set to null</pre>
	 * @param Plan Number
	 * @return
	 * @throws Exception
	 */
	public void enterPlanWhenDefaultPlanIsNull() throws Exception {
		String planNumber = "";
		/*resultset = DB.executeQuery(Stock.getTestQuery("getNumberOfplansQuery")[0],
				Stock.getTestQuery("getNumberOfplansQuery")[1],
				"K_"+Stock.GetParameterValue("username"));
		while(resultset.next()){
			planNumber = resultset.getString("GA_ID");
			break;
		}*/
		if(planNumber==null || planNumber.isEmpty()){
			planNumber = Stock.GetParameterValue("planNumber");
		}
		Web.isWebElementDisplayed(planTextFieldDefaultPlanNull,true);
		Web.setTextToTextBox(planTextFieldDefaultPlanNull, planNumber);
		Web.clickOnElement(nextButton);
		Web.waitForPageToLoad(Web.getDriver());
		Web.isWebElementDisplayed(new AccountVerificationPage(), "Account Verification Title");
	}	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
