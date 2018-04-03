/**
 * 
 */
package pageobjects.myProfile;

import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;
import framework.util.CommonLib;
import gwgwebdriver.ByAngularAttribute;
import gwgwebdriver.ByAngularBinding;
import gwgwebdriver.ByAngularCssContainingText;
import gwgwebdriver.ByAngularModel;
import gwgwebdriver.ByAngularOptions;
import gwgwebdriver.ByAngularRepeater;
import gwgwebdriver.GwgWebDriver;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;

import com.aventstack.extentreports.Status;

/**
 * @author rvpndy
 *
 */
public class MyProfilePage extends LoadableComponent<MyProfilePage>{

	private static final String rootSelector = "[ng-app]";
	private static String randomEmailId;
	Actions actions;

	@FindBy(xpath=".//*[@id='myProfileModule']//h3[text()='My Profile']")
	private WebElement myPrfilePageIdentity;
	@FindBy(id="profileTest")
	private WebElement myProfileFrame;	
	@FindBy (id="ifrmFooter")
	private WebElement faqIframe;
	@FindBy(xpath = "/html/body/table/tbody/tr[2]/td/div[3]/div[1]/a/span")
	private WebElement closeFaq;
	
	ByAngularAttribute changeDefaultPlanButton = new ByAngularAttribute(rootSelector,
			"click","changeDefaultPlan()");
	ByAngularAttribute changeEmailButton = new ByAngularAttribute(rootSelector, 
			"click", "changeCurrentEmail");
	ByAngularAttribute changeUserNameButton = new ByAngularAttribute(rootSelector, 
			"click", "changeUsername()");
	ByAngularAttribute updateUserNameButton = new ByAngularAttribute(rootSelector, 
			"click", "updateUsername(");
	ByAngularAttribute changePasswordButton = new ByAngularAttribute(rootSelector, 
			"click", "changePassword()");
	ByAngularAttribute changeSecurityQuestnButton = new ByAngularAttribute(rootSelector, 
			"click", "showSecurityQuestions()");
	ByAngularAttribute updateSecurityQuestnButton = new ByAngularAttribute(rootSelector, 
			"click", "updateSecurityQuestions");
	ByAngularModel newEmail = new ByAngularModel(rootSelector,"newEmail");
	ByAngularModel confirmEmail = new ByAngularModel(rootSelector,"confirmEmail");
	ByAngularBinding currentEmail = new ByAngularBinding(rootSelector, "currentEmail");
	ByAngularAttribute updateEmailButton = new ByAngularAttribute(rootSelector, 
			"click", "updateEmail");
	ByAngularModel confirmationModalBox = new ByAngularModel(rootSelector,"modalPassword");
	ByAngularBinding defaultPlan = new ByAngularBinding(rootSelector, "defaultPlan");
	ByAngularBinding assignedUsername = new ByAngularBinding(rootSelector, "assignedUsername");
	ByAngularBinding regUsername = new ByAngularBinding(rootSelector, "regUsername");
	ByAngularModel searchPlanBox = new ByAngularModel(rootSelector, "query");
	ByAngularRepeater planOptionSuggestions = new ByAngularRepeater(rootSelector, "option in options", 
			true);
	ByAngularAttribute updateDefaultPlanBtn = new ByAngularAttribute(rootSelector,
			"click","updateDefaultPlan");
	ByAngularCssContainingText invalidEmailError = new ByAngularCssContainingText
			(rootSelector,"span.text-muted.ng-binding","Email address is invalid.");
	ByAngularCssContainingText incorrectPwdError = new ByAngularCssContainingText
			(rootSelector,"div.alert.alert-error-em-alert-KO","Incorrect Password.");
	ByAngularCssContainingText incorrectCurrentPasswordError = new ByAngularCssContainingText
			(rootSelector,"span.ng-binding.ng-scope","Incorrect current password");
	ByAngularModel updateRegisteredUsrname = new ByAngularModel(rootSelector,"updatedUsername");
	ByAngularCssContainingText regUserNameRequiredMessage = new ByAngularCssContainingText
			(rootSelector,"span.text-muted.ng-binding","Username is required");
	ByAngularCssContainingText regUserNameLengthMessage = new ByAngularCssContainingText
			(rootSelector,"span.text-muted.ng-binding","Must be 7 - 64 characters");
	ByAngularCssContainingText regUserNameAllowedCharsMessage = new ByAngularCssContainingText
			(rootSelector,"span.text-muted.ng-binding","The only special characters allowed");
	ByAngularCssContainingText regUserNameValidMessage = new ByAngularCssContainingText
			(rootSelector,"p.success.ng-binding.ng-scope","Valid username");
	ByAngularRepeater securityQuestionDropDowns = new ByAngularRepeater(rootSelector,
			"securityQsn in securityQsnsRange(securityQuestionLength)",false);
	ByAngularOptions securityQuestionsList = new ByAngularOptions(rootSelector,
			"q for q in securityQuestList");
	ByAngularModel securityAnswerTextBox = new ByAngularModel(rootSelector, "securityAns[$index]"); 
	ByAngularAttribute duplicateQuestnError = new ByAngularAttribute
			(rootSelector,"show","duplicateSecurityQsn");
	ByAngularAttribute duplicateAnswerError = new ByAngularAttribute
			(rootSelector,"show","duplicateAnswer");
	ByAngularModel currentPwdBox = new ByAngularModel(rootSelector,"currentPassword");
	ByAngularModel newPwdBox = new ByAngularModel(rootSelector,"newPassword");
	ByAngularModel confirmPwdBox = new ByAngularModel(rootSelector,"confirmPassword");
	ByAngularAttribute updatePwdBtn = new ByAngularAttribute(rootSelector,"click","updatePassword");
	ByAngularCssContainingText pwdRequiredMessage = new ByAngularCssContainingText
			(rootSelector,"span.text-muted.ng-binding","Password is required");
	ByAngularCssContainingText newPwdRequiredMessage = new ByAngularCssContainingText
			(rootSelector,"span.text-muted.ng-binding","New password is required");
	ByAngularCssContainingText newPasswordLengthMsg = new ByAngularCssContainingText
			(rootSelector,"span.text-muted.ng-binding","Must be 8 - 64 characters");

	private WebElement changeDefaultPlanButton()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(changeDefaultPlanButton);
	}
	private WebElement defaultPlanValue()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(defaultPlan);
	}
	private WebElement searchPlanBox()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(searchPlanBox);
	}
	private WebElement planOptionSuggestions(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(planOptionSuggestions);
	}
	private WebElement updateDefaultPlanBtn()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(updateDefaultPlanBtn);
	}
	private WebElement updateCurrentEmailBtn()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(changeEmailButton);
	}
	private WebElement updateUserNameBtn(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(changeUserNameButton);
	}
	private WebElement assignedUserNameValue(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(assignedUsername);
	}
	/*private WebElement regUserNameValue(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(regUsername);
	}*/
	private WebElement changePasswordButton()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(changePasswordButton);
	}
	private WebElement currentPwdBox()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(currentPwdBox);
	}
	private WebElement newPwdBox()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(newPwdBox);
	}
	private WebElement confirmPwdBox()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(confirmPwdBox);
	}
	private WebElement updatePwdBtn()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(updatePwdBtn);
	}
	private WebElement pwdRequiredMessage()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(pwdRequiredMessage);
	}
	private WebElement newPwdRequiredMessage()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(newPwdRequiredMessage);
	}
	private WebElement newPasswordLengthMsg()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(newPasswordLengthMsg);
	}
	private WebElement incorrectCurrentPasswordError(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(incorrectCurrentPasswordError);
	}
	private WebElement changeSecurityQuestnButton(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(changeSecurityQuestnButton);
	}
	private List<WebElement> securityQuestionDropDowns(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElements(securityQuestionDropDowns);
	}
	private WebElement updateSecurityQuestnButton(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(updateSecurityQuestnButton);
	}
	private WebElement newEmailBox()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(newEmail);
	}

	private WebElement confirmEmailBox()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(confirmEmail);
	}
	private WebElement updateEmailBtn()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(updateEmailButton);
	}
	private WebElement currentEmailAddress()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(currentEmail);
	}
	private WebElement confirmModalBox()
	{
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(confirmationModalBox);
	}
	private WebElement invalidEmailError(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(invalidEmailError);
	}
	private WebElement incorrectPwdError(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(incorrectPwdError);
	}
	private WebElement updateUserNameButton(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(updateUserNameButton);
	}
	private WebElement updateRegisteredUsrnameBox(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(updateRegisteredUsrname);
	}
	private WebElement regUserNameRequiredMessage(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(regUserNameRequiredMessage);
	}
	private WebElement regUserNameLengthMessage(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(regUserNameLengthMessage);
	}
	private WebElement regUserNameAllowedCharsMessage(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(regUserNameAllowedCharsMessage);
	}
	private WebElement regUserNameValidMessage(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(regUserNameValidMessage);
	}
	private WebElement duplicateQuestnError(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(duplicateQuestnError);
	}
	private WebElement duplicateAnswerError(){
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElement(duplicateAnswerError);
	}
	@FindBy(xpath =".//*[@id='verifyPasswordModal']//button[text()='OK']")
	private WebElement confirmPwdButton;

	@FindBy(xpath="//div[@ng-show='successMessageFlag']")
	private WebElement successMessageDiv;

	LoadableComponent<?> parent;

	public MyProfilePage()
	{
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.gwgWebDriver = new GwgWebDriver((JavascriptExecutor) Web.getDriver());
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(myPrfilePageIdentity));

	}

	@SuppressWarnings("unused")
	@Override
	protected void load() {
		// TODO Auto-generated method stub
		Web.getDriver().switchTo().defaultContent();
		HomePage homepage = (HomePage) this.parent;
		actions = new Actions(Web.getDriver());

		new HomePage(new LoginPage(), false, new String[] {
			Stock.GetParameterValue("username"),
			Stock.GetParameterValue("password") }).get();

		Reporter.logEvent(Status.PASS,
				"Check if the user has landed on homepage",
				"The user has landed on homepage", true);
		try {
			WebElement myProfileLink = Web.returnElement(new HomePage(), "MY PROFILE");
			if(myProfileLink.isDisplayed())
				actions.moveToElement(myProfileLink).click().build().perform();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Web.getDriver().switchTo().frame(myProfileFrame);
		if(Web.isWebElementDisplayed(myPrfilePageIdentity, true))
		{
			Reporter.logEvent(Status.PASS, "Check if user has landed on My Profile page",
					"User has landed on My Profile page", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Check if user has landed on My Profile page", 
					"My Profile page did not load. It will result in assertion error",
					true);
		}
	}

	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Current Default Plan")) {
			return defaultPlanValue();
		}
		if(fieldName.trim().equalsIgnoreCase("Current Assigned Name")){
			return this.assignedUserNameValue();
		}
		if(fieldName.trim().equalsIgnoreCase("Update Email Button"))
			return this.updateEmailBtn();
		if(fieldName.trim().equalsIgnoreCase("New Email"))
			return this.newEmailBox();
		if(fieldName.trim().equalsIgnoreCase("Confirm Email"))
			return this.confirmEmailBox();
		if(fieldName.trim().equalsIgnoreCase("Duplicate Answer Error")){
			return this.duplicateAnswerError();
		}
		if(fieldName.trim().equalsIgnoreCase("Duplicate Question Error")){
			return this.duplicateQuestnError();
		}
		return null;
	}

	public boolean updateEmailAddress()
	{
		boolean emailAddressUpdated = false;
		try
		{
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(myProfileFrame);
			Web.clickOnElement(updateCurrentEmailBtn());
			if(!Web.VerifyText(currentEmailAddress().getText(), 
					this.getRandomEmailId(Stock.GetParameterValue("newEmailID"),true))){
				Web.setTextToAngularTextBox(newEmailBox(),randomEmailId);
				Web.setTextToAngularTextBox(confirmEmailBox(), randomEmailId);
				Web.clickOnElement(updateEmailBtn());
			}
			else{
				Web.setTextToAngularTextBox(newEmailBox(),
						this.getRandomEmailId(Stock.GetParameterValue("newEmailID"),true));
				Web.setTextToAngularTextBox(confirmEmailBox(), randomEmailId);
				Web.clickOnElement(updateEmailBtn());
			}


			if(Web.isWebElementDisplayed(confirmModalBox()))
			{
				Web.setTextToAngularTextBox(confirmModalBox(), Stock.GetParameterValue("Password"));
				Web.clickOnElement(confirmPwdButton);
				Web.gwgWebDriver.waitForAngularRequestsToFinish();
			}
			if(successMessageDiv.isDisplayed()&&
					currentEmailAddress().getText().equalsIgnoreCase
					(randomEmailId))
			{
				emailAddressUpdated = true;
				Reporter.logEvent(Status.PASS, "Check user is able to update email address", 
						"User has updated email successfully", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Check user is able to update email address", 
						"User was not able to update email", true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return emailAddressUpdated;
	}

	public boolean updateEmailAddress(boolean isNegative)
	{
		boolean emailAddressUpdated = false;

		try
		{
			if(isNegative){	
				Web.getDriver().switchTo().defaultContent();
				Web.getDriver().switchTo().frame(myProfileFrame);
				Web.clickOnElement(updateCurrentEmailBtn());
				Web.setTextToAngularTextBox(newEmailBox(),
						this.getRandomEmailId(Stock.GetParameterValue("newEmailID"),false));
				Web.setTextToAngularTextBox(confirmEmailBox(), 
						this.getRandomEmailId(Stock.GetParameterValue("newEmailID"),false));
				if(!Web.isWebEementEnabled(this, "Update Email Button")
						&&Web.isWebElementDisplayed(invalidEmailError())){
					System.out.println(invalidEmailError().getText());
					emailAddressUpdated = true;
					Reporter.logEvent(Status.PASS, "Check email update button is disabled for "
							+ "invalid email address", 
							"Email update button is disabled for invalid email address.\n Error displayed is:"+invalidEmailError().getText(), false);
				}
				else
				{
					Web.clickOnElement(updateEmailBtn());
					Reporter.logEvent(Status.FAIL, "Check email update button is disabled for "
							+ "invalid email address", 
							"email update button is enabled for invalid email address", true);
				}
			}	
			else
			{
				emailAddressUpdated = this.updateEmailAddress();
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return emailAddressUpdated;
	}

	public boolean updateDefaultGaID(String gaID)
	{
		boolean defaultGaIdUpdated = false;
		try
		{
			Web.clickOnElement(changeDefaultPlanButton());
			Web.setTextToAngularTextBox(searchPlanBox(), gaID);
			Web.clickOnElement(planOptionSuggestions());
			Web.clickOnElement(updateDefaultPlanBtn());
			Web.gwgWebDriver.waitForAngularRequestsToFinish();
			System.out.println(defaultPlanValue().getText());
			if(successMessageDiv.isDisplayed()&&
					defaultPlanValue().getText().contains
					(Stock.GetParameterValue("defaultGaId")))
			{
				defaultGaIdUpdated = true;
				Reporter.logEvent(Status.PASS, "Check user is able to update default GA ID", 
						"User has updated default GA ID successfully", false);
			}
			else
			{
				Reporter.logEvent(Status.INFO, "Check user is able to update default GA ID", 
						"User was not able to update default GA ID", true);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return defaultGaIdUpdated;
	}

	public void updateDefaultGaIdInDB()
	{
		try{
			String[] updateGaIdQuery = Stock.getTestQuery("updateDefaultPlanQuery");
			updateGaIdQuery[0] = CommonLib.getUserDBName(Stock.GetParameterValue("username"))
					+ "DB_"+CommonLib.checkEnv(Stock.getConfigParam("TEST_ENV"));
			DB.executeUpdate(updateGaIdQuery[0], updateGaIdQuery[1], 
					Stock.GetParameterValue("defaultGaId"),
					"K_"+Stock.GetParameterValue("username"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getDefaultGaIdFromDb()
	{
		String defaultGaId = null;
		try{
			String[] getDefaultGaIdQuery = Stock.getTestQuery("selectDefaultPlanQuery");
			getDefaultGaIdQuery[0] = CommonLib.getUserDBName(Stock.GetParameterValue("username"))
					+ "DB_"+CommonLib.checkEnv(Stock.getConfigParam("TEST_ENV"));
			ResultSet rs = DB.executeQuery(getDefaultGaIdQuery[0], getDefaultGaIdQuery[1], 
					"K_"+Stock.GetParameterValue("username"));
			while(rs.next()){
				defaultGaId = rs.getString("default_ga_id");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return defaultGaId;
	}

	public String getRandomEmailId(String emailID,boolean isValidationRequired){
		if(isValidationRequired){
			if(CommonLib.isValidEmailAddress(emailID)){
				String[] emailIdParts = emailID.split("@");
				randomEmailId = CommonLib.appendRandomNumberToText(emailIdParts[0])+"@"+emailIdParts[1];
				return randomEmailId;
			}
			else
				throw new IllegalArgumentException("Invalid email ID");
		}
		else
			return emailID;
	}

	public void enterWrongPasswordAndUpdate(String[] values){
		try{
			if(values.length>1){
				for(String value : values){
					Web.clickOnElement(updateEmailBtn());
					Web.gwgWebDriver.waitForAngularRequestsToFinish();
					Web.setTextToAngularTextBox(confirmModalBox(), value);
					Web.clickOnElement(confirmPwdButton);
					Web.gwgWebDriver.waitForAngularRequestsToFinish();
					Reporter.logEvent(Status.INFO, "Incorrect password error message", 
							incorrectPwdError().getText(), true);
				}
			}
			else{
				throw new IllegalArgumentException("Invalid String[] arguement");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void enterWrongPasswordAndUpdate(String[] values,WebElement button){
		try{
			if(values.length>1){
				for(String value : values){
					Web.clickOnElement(button);
					Web.gwgWebDriver.waitForAngularRequestsToFinish();
					Web.setTextToAngularTextBox(confirmModalBox(), value);
					Web.clickOnElement(confirmPwdButton);
					Web.gwgWebDriver.waitForAngularRequestsToFinish();
					Reporter.logEvent(Status.INFO, "Incorrect password error message", 
							incorrectPwdError().getText(), true);
				}
				if(Web.isWebElementDisplayed(Web.returnElement(new LoginPage(), "LOGIN FRAME"),true)){
					Reporter.logEvent(Status.PASS, "User is logged out", "User is logged out", true);
				}
			}
			else{
				throw new IllegalArgumentException("Invalid String[] arguement");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void enterWrongPasswordAndUpdate(String[] values,WebElement button,WebElement textBox){
		try{
			if(values.length>1){
				for(String value : values){
					Web.gwgWebDriver.waitForAngularRequestsToFinish();
					Web.setTextToAngularTextBox(textBox, value);
					Web.setTextToAngularTextBox(newPwdBox(), Stock.GetParameterValue("newPassword"));
					Web.setTextToAngularTextBox(confirmPwdBox(), Stock.GetParameterValue("newPassword"));
					Web.clickOnElement(button);
					Web.gwgWebDriver.waitForAngularRequestsToFinish();
					Reporter.logEvent(Status.INFO, "Incorrect password error message", 
							incorrectCurrentPasswordError().getText(), true);
				}
				if(Web.isWebElementDisplayed(Web.returnElement(new LoginPage(), "LOGIN FRAME"),true)){
					Reporter.logEvent(Status.PASS, "User is logged out", "User is logged out", true);
				}
			}
			else{
				throw new IllegalArgumentException("Invalid String[] arguement");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean VerifyUpdateRegisteredUserName(boolean negativeVerify){
		boolean isUpdated = false;
		try{
			Web.clickOnElement(updateUserNameBtn());
			Web.setTextToAngularTextBox(updateRegisteredUsrnameBox(), 
					Stock.GetParameterValue("NewAssignedUserName"));
			if(!negativeVerify&&updateUserNameButton().isEnabled()&&Web.isWebElementDisplayed(regUserNameValidMessage()) 
					){
				Reporter.logEvent(Status.INFO, "Enter new assigned user name", 
						"New assigned user name is entered", false);
				Web.clickOnElement(updateUserNameButton());
				Web.gwgWebDriver.waitForAngularRequestsToFinish();
				Web.setTextToAngularTextBox(confirmModalBox(), Stock.GetParameterValue("password"));
				Web.clickOnElement(confirmPwdButton);
				Web.gwgWebDriver.waitForAngularRequestsToFinish();
				if(successMessageDiv.isDisplayed())
				{
					isUpdated = true;
					Reporter.logEvent(Status.PASS, "Check user is able to update registered user name", 
							"User has updated registered user name successfully\n"+successMessageDiv.getText(), false);
				}
				else
				{
					return isUpdated;
				}
			}
			if(negativeVerify&&!updateUserNameButton().isEnabled()&&Web.isWebElementDisplayed(regUserNameRequiredMessage())){
				Reporter.logEvent(Status.INFO, "Enter new assigned user name", 
						"New assigned user name is not entered\nError message is:"+regUserNameRequiredMessage().getText(), true);
			}
			if(negativeVerify&&!updateUserNameButton().isEnabled()&&Web.isWebElementDisplayed(regUserNameLengthMessage())){
				Reporter.logEvent(Status.INFO, "Update assigned user name", 
						"New assigned user name is not updated\nError message is:"+regUserNameLengthMessage().getText(), true);
			}
			if(negativeVerify&&!updateUserNameButton().isEnabled()&&Web.isWebElementDisplayed(regUserNameAllowedCharsMessage())){
				Reporter.logEvent(Status.INFO, "Enter new assigned user name", 
						"New assigned user name is not entered\nError message is:"+regUserNameAllowedCharsMessage().getText(), true);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return isUpdated;
	}

	private void selectSecurityQuestion(boolean sameQuestions, int index){
		try{
			if(sameQuestions){
				for (WebElement ele : securityQuestionDropDowns()){
					List<WebElement> securityQuestions = ele.findElements(securityQuestionsList);
					if(index<=securityQuestions.size())
						securityQuestions.get(index).click();
					else
						throw new Exception("Index is out of bound");
				}
				if(duplicateQuestnError().isDisplayed()){
					Reporter.logEvent(Status.INFO, "Set same questions in three dropdowns", 
							"Same question is set\n"+duplicateQuestnError().getText(), true);
				}

			}
			else{
				int i=0;
				for (WebElement ele : securityQuestionDropDowns()){
					List<WebElement> securityQuestions = ele.findElements(securityQuestionsList);
					System.out.println(securityQuestions.size());
					if(index<=securityQuestions.size()-4)
						securityQuestions.get(index+i).click();
					else if(index<=securityQuestions.size()-1)
						securityQuestions.get(index-i).click();
					else
						throw new Exception("Index is out of bound");
					i++;

				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void FillSecurityAnswer(boolean sameAnswer,String ans0,String ans1,String ans2){
		if(sameAnswer){
			for(WebElement ele : securityQuestionDropDowns()){
				WebElement answerBoxes = ele.findElement(securityAnswerTextBox);
				Web.setTextToAngularTextBox(answerBoxes, ans0);
			}
			Reporter.logEvent(Status.INFO, "Set same answer in three text boxes", 
					"Same answer is set\n"+duplicateAnswerError().getText(), true);
		}
		else
		{
			int i = 0;
			WebElement answerBoxes;
			for(WebElement ele : securityQuestionDropDowns()){
				answerBoxes = ele.findElement(securityAnswerTextBox);
				if(i == 0)
					Web.setTextToAngularTextBox(answerBoxes,ans0);
				if(i == 1)
					Web.setTextToAngularTextBox(answerBoxes,ans1);
				if(i == 2)
					Web.setTextToAngularTextBox(answerBoxes,ans2);
				i++;
			}
		}
	}
	public boolean selectSecQuestnAndAnswers(boolean sameQuestions,boolean sameAnswer,int index,boolean negativeVerify) throws Exception{
		boolean secQuestnAnsUpdated = false;
		Web.clickOnElement(changeSecurityQuestnButton());
		selectSecurityQuestion(sameQuestions,index);
		if(!sameQuestions){
			FillSecurityAnswer(sameAnswer,Stock.GetParameterValue("Answer1"), 
					Stock.GetParameterValue("Answer2"), 
					Stock.GetParameterValue("Answer3"));
		}
		if(updateSecurityQuestnButton().isEnabled()){
			Web.clickOnElement(updateSecurityQuestnButton());
			Web.gwgWebDriver.waitForAngularRequestsToFinish();
			if(!negativeVerify)
				fillPasswordInModalBox();
			else
				enterWrongPasswordAndUpdate(Stock.getCsvParameterValue("badPasswords"), updateSecurityQuestnButton());
		}
		if(Web.isWebElementDisplayed(successMessageDiv))
		{
			secQuestnAnsUpdated = true;
			Reporter.logEvent(Status.INFO, "Check user is able to update security questions and answers", 
					"User has updated security questions and answers successfully\n"+successMessageDiv.getText(), false);
			DB.executeUpdate(Stock.getTestQuery("updateSecurityQuestions")[0], Stock.getTestQuery("updateSecurityQuestions")[1], "K_"+Stock.GetParameterValue("username"));
		}
		return secQuestnAnsUpdated;

	}
	private void fillPasswordInModalBox(){
		Web.setTextToAngularTextBox(confirmModalBox(), Stock.GetParameterValue("password"));
		Web.clickOnElement(confirmPwdButton);
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
	}

	private boolean isPasswordUpdated(String currentPwd, String newPassword){
		boolean isPwdUpdated = false;
		try{
			Web.clickOnElement(changePasswordButton());
			Web.setTextToAngularTextBox(currentPwdBox(), currentPwd);
			Web.setTextToAngularTextBox(newPwdBox(), newPassword);
			Web.setTextToAngularTextBox(confirmPwdBox(), newPassword);
			if(updatePwdBtn().isEnabled()){
				Web.clickOnElement(updatePwdBtn());
				Web.gwgWebDriver.waitForAngularRequestsToFinish();
				if(successMessageDiv.isDisplayed()){
					isPwdUpdated = true;
					Reporter.logEvent(Status.INFO, "Password updated", 
							"Password updated\nSuccess message is:"+successMessageDiv.getText(), true);
				}
			}
			else if(Web.isWebElementDisplayed(pwdRequiredMessage())
					||Web.isWebElementDisplayed(newPwdRequiredMessage())
					||Web.isWebElementDisplayed(newPasswordLengthMsg())){
				Reporter.logEvent(Status.INFO, "Password not updated", 
						"Password not updated as update button is disabled due to page "
								+ "validations\nValidation message(s) is/are: "
								+Web.getWebElementText(pwdRequiredMessage())+"\n"+
								Web.getWebElementText(newPwdRequiredMessage())+"\n"+
								Web.getWebElementText(newPasswordLengthMsg()), true);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return isPwdUpdated;
	}

	public boolean updateUserPassword(boolean negativeVerify,String currentPwd, String newPassword){
		boolean pwdUpdated = false;
		try{
			if(!negativeVerify){
				isPasswordUpdated(currentPwd,newPassword);
				pwdUpdated = true;
			}
			if(negativeVerify){
				enterWrongPasswordAndUpdate(Stock.getCsvParameterValue("badPasswords"), updatePwdBtn(),currentPwdBox());
				DB.executeUpdate(Stock.getTestQuery("updatePasswordTotesting1")[0], Stock.getTestQuery("updatePasswordTotesting1")[1], "K_"+Stock.GetParameterValue("username"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pwdUpdated;
	}

	public boolean verifyFaqPageLoad(){
		boolean faqPageLoaded = false;
		try{
			Web.getDriver().switchTo().defaultContent();
			Web.clickOnElement(new HomePage(), "FAQ");
			Thread.sleep(5000);
			if(Web.isWebElementDisplayed(faqIframe, true)){

				faqPageLoaded = true;
				Web.getDriver().switchTo().defaultContent();
				Web.clickOnElement(closeFaq);

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return faqPageLoaded;
	}


}
