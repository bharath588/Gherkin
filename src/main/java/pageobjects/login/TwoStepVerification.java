package pageobjects.login;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;


public class TwoStepVerification extends LoadableComponent<TwoStepVerification> {
	
	//Declarations 
	private LoadableComponent<?> parent;
	private boolean isPageMandatory = false;	//Does the page expected to load in the scenario flow?
	@SuppressWarnings("unused")
	private boolean isMandatoryAfterLoad = true;	//(Temp variable for internal use) is the page expected after load() is invoked?
	@SuppressWarnings("unused")
	private String username;
	@SuppressWarnings("unused")
	private String password;
	
	@FindBy(id="securityAnswer") private WebElement txtSecurityAnswer;
	@FindBy(id="deliveryOption") private WebElement lstDeliveryOption;
	@FindBy(xpath=".//form/.//a[text()[normalize-space()='Already have a code?']][1]") private WebElement lnkAlreadyHaveCodeFogotPsw;
	@FindBy(xpath=".//form/.//a[text()[normalize-space()='Already have a code?']][2]") private WebElement lnkAlreadyHaveCodeMFA;
	@FindBy(id="submit") private WebElement btnContinue;
	@FindBy(xpath=".//*[@id='submit' and @value='Continue']") private WebElement btnContinueFogotPsw;
	@FindBy(id="deliveryOptionsContinue") private WebElement btnSendMeACode;
	@FindBy(id="verification_codeInput") private WebElement txtCodeInput;
	@FindBy(id="remember_deviceInput") private WebElement chkRememberDevice;
	@FindBy(id="signin") private WebElement btnSignIn;
	@FindBy(linkText="Didn't receive the code?") private WebElement lnkDidntReceiveCode;
	@FindBy(xpath=".//*[text()[normalize-space()='Login help']]") private WebElement lblLoginHelp;
	@FindBy(xpath="//button[contains(text(),'Continue to My Account')]") private WebElement btnContinueToMyAccount;
	
	/** Empty args constructor
	 * 
	 */
	public TwoStepVerification() {
		this.parent = new LoginPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/**Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public TwoStepVerification(LoadableComponent<?> parent){
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	public TwoStepVerification(String username, String password){
		this.username = username;
		this.password = password;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Getter for isPageMandatory flag
	 * 
	 * @return boolean
	 */
	public boolean isPageMandatory() {
		return isPageMandatory;
	}

	/** Setter for isPageMandatory flag
	 * 
	 * @param isPageMandatory - <b>true</b> to choose mandatory to display page option. <b>false</b> otherwise.
	 */
	public void setPageMandatory(boolean isPageMandatory) {
		this.isPageMandatory = isPageMandatory;
	}
	
	@Override
    protected void isLoaded() throws Error {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(Web.isWebElementDisplayed(lstDeliveryOption));
    }
	
	@Override
	protected void load() {
		LoginPage login = (LoginPage) this.parent;
		this.parent.get();
		
		login.submitLoginCredentials(lib.Stock.GetParameterValue("username"),lib.Stock.GetParameterValue("password"));
		if (!isPageMandatory)
            isMandatoryAfterLoad = false;
	}
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	CHOOSE DELIVERY METHOD - [Drop down]
	 * 	ALREADY HAVE A CODE? - [Link]
	 * 	CONTINUE - [Button]
	 * 	VERIFICATION CODE - [Text box]
	 * 	SIGN IN - [Button]
	 *  DIDN'T RECEIVE THE CODE' - [Link]
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		//CHOOSE DELIVERY METHOD
		if (fieldName.trim().equalsIgnoreCase("CHOOSE DELIVERY METHOD")) {
			return this.lstDeliveryOption;
		}
		
		//ALREADY HAVE A CODE?
		if (fieldName.trim().equalsIgnoreCase("ALREADY HAVE A CODE?")) {
			
			if (Web.isWebElementDisplayed(lblLoginHelp)) {
				return this.lnkAlreadyHaveCodeFogotPsw;
			} else {
				return this.lnkAlreadyHaveCodeMFA;
			}
			
		}
		
		//CONTINUE
		if (fieldName.trim().equalsIgnoreCase("CONTINUE")) {
			return this.btnContinue;
		}
		
		//VERIFICATION CODE
		if (fieldName.trim().equalsIgnoreCase("VERIFICATION CODE")) {
			return this.txtCodeInput;
		}
		
		//SIGN IN
		if (fieldName.trim().equalsIgnoreCase("SIGN IN")) {
			return this.btnSignIn;
		}
		
		//DIDN'T RECEIVE THE CODE
		if (fieldName.trim().equalsIgnoreCase("DIDN'T RECEIVE THE CODE")) {
			return this.lnkDidntReceiveCode;
		}
		
		if (fieldName.trim().equalsIgnoreCase("CONTINUE TO MY ACCOUNT")) {
			return this.btnContinueToMyAccount;
		}
		
		
		return null;
	}

	/**<pre> Method to select two step verification code delivery option
	 * options available:
	 * 	CALL ME
	 * 	TEXT ME
	 * 	EMAIL
	 * 	ALREADY_HAVE_CODE</pre>
	 * 
	 * @param deliveryOption
	 * 
	 */
	public void selectCodeDeliveryOption(String deliveryOption,boolean... args){
		
		if (deliveryOption.trim().equalsIgnoreCase("ALREADY_HAVE_CODE")) {
			
			try {
				Thread.sleep(2000);
			} catch (Exception e1) {
				//do nothing
			}
			
			if (Web.isWebElementDisplayed(lblLoginHelp)) {
				
				this.lnkAlreadyHaveCodeFogotPsw.click();;
			} else {
				this.lnkAlreadyHaveCodeMFA.click();
			}
			
			Reporter.logEvent(Status.INFO, "Select verification code delivery option", 
					"Clicked on link 'Already have a code'", false);
			return;
		}
		else {
			
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// do nothing
			}
			//deliveryOption.replaceAll("_", " ")) == false;
			if (!Web.selectDropDownOption(lstDeliveryOption, deliveryOption.replaceAll("_", " "),true)) {
				throw new Error("Unable to select verification code delivery option: " + deliveryOption.replaceAll("_", " "));
			}
		}
		
		
		this.btnSendMeACode.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**<pre> Method to submit verification code
	 * Actions performed:
	 * 	1. Enter verification code
	 * 	2. select 'remember this device' checkbox based on user input and check box availability
	 * 	3. click on 'Sign in' button</pre>
	 * 
	 * @param verificationCode
	 * @param rememberDevice
	 */
	public void submitVerificationCode(String verificationCode, boolean OptionDisplayed_rememberDevice, boolean rememberDevice) throws Exception{
			
		Thread.sleep(2000);
		this.txtCodeInput.sendKeys(verificationCode);
		Reporter.logEvent(Status.INFO, "Enter Verification Code", "Verification code ["+ verificationCode +"] has been entered", true);
		
		//Select 'Remember this device' checkbox based on user input and availability
		if (OptionDisplayed_rememberDevice) {
			if ((rememberDevice && !this.chkRememberDevice.isSelected()) //if rememberDevice = true and CheckBoxIsChecked = false 
					|| (!rememberDevice && this.chkRememberDevice.isSelected())) //OR if rememberDevice = false and CheckBoxIsChecked = true
				this.chkRememberDevice.click();
		}
		else if (Web.isWebElementDisplayed(chkRememberDevice)) {
			Reporter.logEvent(Status.FAIL, "Check if 'Remember this device' checkbox is displayed", 
					"Verification failed.\n\tExpected: Checkbox IS NOT displayed\n\tActual: Checkbox IS displayed", true);
		}
		
		if (Web.isWebElementDisplayed(lblLoginHelp)) {
			this.btnContinueFogotPsw.click();;
		} else {
			this.btnSignIn.click();
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** Method to fetch and return verification/activation code
	 * 
	 * @param bGetDefaultCode - 'true' to return default code, 'false' otherwise
	 * @return Verification Code - String
	 */
	public String getVerificationCode(boolean bGetDefaultCode) {
		String verificationCode = "";
		if (bGetDefaultCode) {
			verificationCode =Stock.getConfigParam("defaultActivationCode");
		}
		else {
			// Code to fetch activation/verification code from database
			String[] sqlQuery = null;
			
			try {
				sqlQuery = Stock.getTestQuery("fetchActivationCode");
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			if (sqlQuery.length == 0) {
				Reporter.logEvent(Status.FAIL, "Get SQL Query [fetchActivationCode]", "Query with the name [fetchActivationCode] not found in SQL_Queries sheet", false);
				return verificationCode;
			}
			
			ResultSet recSet = DB.executeQuery(sqlQuery[0], sqlQuery[1]);
			
			if (DB.getRecordSetCount(recSet) > 0) {
				try {
					recSet.first();
					verificationCode = recSet.getString("TAG_VALUE");
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(Status.WARNING, "Get verification code from DB", "No verification code generated.", false);
				}
			}
			
			try {
				recSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return verificationCode;
	}
	
	/**Method to check if ActivationCode is generated. Query used from query sheet is <b>isActivationCodeGenerated</b>
	 * 
	 * @param codeDeliveryOption - TEXT ME, CALL ME, EMAIL
	 * @return <b>boolean - true</b> if code is generated after delivery option is selected. <b>false</b> otherwise 
	 */
	public boolean isActivationCodeGenerated(String codeDeliveryOption) {
		boolean isGenerated = false;
		Date dt = new Date();
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		SimpleDateFormat dtTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("isActivationCodeGenerated");
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		ResultSet rSet = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				(codeDeliveryOption.trim().equalsIgnoreCase("TEXT_ME")? "TEXT":
					(codeDeliveryOption.trim().equalsIgnoreCase("CALL_ME")? "VOICE" : "EMAIL")),
				month.format(dt), day.format(dt),dtTime.format(dt));
		
		if (DB.getRecordSetCount(rSet) == 1) {
			isGenerated = true;
		}
		
		try {
			rSet.close();
		} catch (SQLException e) {
			// Do Nothing
		}
		
		return isGenerated;
	}

}
