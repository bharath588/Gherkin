
package pageobjects.accountverification;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import lib.DB;
import lib.Reporter;
import lib.Web;
import lib.Reporter.Status;
import lib.Stock;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;
import pageobjects.login.*;

public class AccountVerificationPage extends LoadableComponent<AccountVerificationPage> {

	/* Object declarations for AccountVerificationPage */

	@FindBy(css = "input[id *= 'mpwrPlanBox']")
	private WebElement txtPlanNumber;
	@FindBy(css = "span[class *= 'growl-title']")
	private WebElement errorMsgBox;
	@FindBy(css = "div[id = 'logo']")
	private WebElement empowerPscLogo;
	@FindBy(css = "button[id *= 'changePasswordForm']:nth-of-type(1)")
	private WebElement btnNextPlanNumber;
	@FindBy(css = "button[class *= 'btn-u btn-u-default'] span")
	private WebElement btnCancel;
	@FindBy(css = "select[class = 'securityQuestionListBox']")
	private WebElement listDropDownBox;
	@FindBy(css = "div[class *= 'securityQuestionContentTable'] table tr:nth-of-type(1) input")
	private WebElement txtFirstDropdownBox;
	@FindBy(css = "div[class *= 'securityQuestionContentTable'] table tr:nth-of-type(2) input")
	private WebElement txtSecondDropdownBox;
	@FindBy(css = "div[class *= 'securityQuestionContentTable'] table tr:nth-of-type(3) input")
	private WebElement txtThirdDropdownBox;
	@FindBy(xpath = "(//select[contains(@class,'securityQuestionListBox')])[1]")
	private WebElement drpdownFirstSecurityQues;
	@FindBy(xpath = "(//select[contains(@class,'securityQuestionListBox')])[2]")
	private WebElement drpdownSecondSecurityQues;
	@FindBy(xpath = "(//select[contains(@class,'securityQuestionListBox')])[3]")
	private WebElement drpdownThirdSecurityQues;
	@FindBy(css = "button[class *= 'btn-u next ui-button']")
	private WebElement btnNext;
	@FindBy(css = "button[class *= 'btn-u btn-u-default swap']")
	private WebElement btnCancelSecurityQues;
	@FindBy(css = "input[id *= 'mpwrUsername']")
	private WebElement txtUsernameReg;
	@FindBy(css = "label[class = 'assigned']")
	private WebElement displayedUserID;
	@FindBy(css = "ul[id = 'footBottomLinks'] li")
	private List<WebElement> footerLinks;
	@FindBy(css = "ul[id = 'headNav'] li")
	private List<WebElement> headerLinks;
	@FindBy(css = "select[class = 'defaultPlanListBox']")
	private WebElement defaultPlandrpdwn;
	@FindBy(xpath = "//input[contains(@type,'text')]")
	private WebElement searchDefaultPlanfield;
	@FindBy(css = "span[class = 'ui-button-text']")
	private WebElement btnSkipCreateUsername;
	@FindBy(xpath = "(//td[contains(@class,'formLeftColumn')])[2]//input")
	private WebElement txtEmailAddress;
	@FindBy(xpath = "(//td[contains(@class,'formLeftColumn')])[3]//input")
	private WebElement txtCurrentPassword;
	@FindBy(xpath = "(//td[contains(@class,'formLeftColumn')])[4]//input")
	private WebElement txtNewPassword;
	@FindBy(xpath = "(//td[contains(@class,'formLeftColumn')])[5]//input")
	private WebElement txtConfirmPassword;
	@FindBy(css = "table[class='changePasswordTable'] tr>td[class=' formLeftColumn'] input")
	private List<WebElement> createNewPwdfields;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(css = "div[class = 'ui-growl-item']")
	private WebElement errorMsg;
	@FindBy(xpath = ".//*[@id='gritter-item-1']/div/div[1]")
	private WebElement dismissErrorBox;
	@FindBy(css = "button[id='mpwrPlanList:j_idt69'] span") 
	private WebElement btnCancelDefaultPlan;
	@FindBy(id = "logOutLink")
	private WebElement linkLogout;
	@FindBy(xpath = ".//*[@id='headNav']/li[2]/a")
	private WebElement linkLogoutAccveri;
	
	LoadableComponent<?> parent;
	Select dropDownSelect;
	List<String> footerlinksList;
	List<String> headerLinkslist;
	String[] queryGetPlanNumber;
	String[] querySetDefaultPlanNull;
	String[] queryDeleteSecurityQuestions;
	ResultSet resultset;

	public AccountVerificationPage() {
		this.parent = new LoginPage();
		PageFactory.initElements(Web.webdriver, this);
	}

	public AccountVerificationPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(empowerPscLogo));
	}
	
	@Override
	protected void load() {
		LoginPage login = (LoginPage) this.parent;
		this.parent.get();
		try {
			login.submitLoginCredentials(new String[]{Stock.globalTestdata.get("USERNAME"), Stock.globalTestdata.get("PASSWORD")});
			Web.waitForElement(empowerPscLogo);
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, "Login for PSC failed : " + e.getMessage());
		}
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * PLAN NUMBER - [TEXT BOX]
	 * CANCEL - [BUTTON]
	 * NEXT - [BUTTON]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("PLAN NUMBER")) {
			return this.txtPlanNumber;
		}
		if (fieldName.trim().equalsIgnoreCase("CANCEL")) {
			return this.btnCancel;
		}
		if (fieldName.trim().equalsIgnoreCase("NEXT")) {
			return this.btnNextPlanNumber;
		}
		if (fieldName.trim().equalsIgnoreCase("BTNNEXT")) {
			return this.btnNext;
		}
		if (fieldName.trim().equalsIgnoreCase("SECURITY ANSWER")) {
			return this.txtFirstDropdownBox;
		}
		if (fieldName.trim().equalsIgnoreCase("USERNAME")) {
			return this.txtUsernameReg;
		}
		if (fieldName.trim().equalsIgnoreCase("SEARCH DEFAULT PLAN")) {
			return this.searchDefaultPlanfield;
		}
		if (fieldName.trim().equalsIgnoreCase("DEFAULT PLAN DROPDOWN")) {
			return this.defaultPlandrpdwn;
		}
		if (fieldName.trim().equalsIgnoreCase("SKIP")) {
			return this.btnSkipCreateUsername;
		}
		if (fieldName.trim().equalsIgnoreCase("DISMISS")) {
			return this.dismissErrorBox;
		}
		if (fieldName.trim().equalsIgnoreCase("ERRORMSG")) {
			return this.errorMsg;
		}
		
		if (fieldName.trim().equalsIgnoreCase("CANCEL PLANDEFAULT")) {
		    return this.btnCancelDefaultPlan;
		}
		if (fieldName.trim().equalsIgnoreCase("CANCEL")) {
            return this.btnCancel;
		}
		// Reporter.logEvent(
		// Status.WARNING,
		// "Get WebElement for field '" + fieldName + "'",
		// "No WebElement mapped for this field\nPage: <b> "
		// + this.getClass().getName() + "</b>",
		// false);
		return null;
	}

	/**
	 * <pre>
	 * This method validates the user input to any text field
	 * The error message box is displayed which contains a error text
	 * </pre>
	 * 
	 * @return
	 */
	public String getErrorMessageText() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.errorMsgBox);
		if (isElementPresent)
			return this.errorMsgBox.getText();
		else
			return "";
	}

	/**
	 * ; This method takes the user input for plan number when a new user logs
	 * in
	 */

	public void planNumberInput(String planNumber) {
		Web.setTextToTextBox(this.txtPlanNumber, planNumber);
		Web.clickOnElement(this.btnNextPlanNumber);
	}

	/**
	 * <pre>
	 * This method takes the user input for all the security questions
	 * </pre>
	 * 
	 * @param ansFirstDropdown
	 * @param ansSecondDropdown
	 * @param ansThirdDropdown
	 */
	public void answerSecurityQuestions(String ansFirstDropdown, String ansSecondDropdown, String ansThirdDropdown) {
		Web.setTextToTextBox(txtFirstDropdownBox, ansFirstDropdown);
		Web.setTextToTextBox(txtSecondDropdownBox, ansSecondDropdown);
		Web.setTextToTextBox(txtThirdDropdownBox, ansThirdDropdown);
		Web.clickOnElement(btnNext);
	}

	/**
	 * 
	 * <pre>
	 * This method is used to take the input for user name registration for a new user
	 * </pre>
	 * 
	 * @param userName
	 */

	public void registerUsername(String userName) {
		Web.isWebElementDisplayed(txtUsernameReg, true);
		Web.setTextToTextBox(txtUsernameReg, userName);
		Web.clickOnElement(btnNext);
	}

	/**
	 * <pre>
	 * This method verifies the userid displayed on screen with the entered userid
	 * </pre>
	 * 
	 * @param username
	 */
	public void verifyUserIdDisplayed(String username) {
		if((Web.VerifyText(username, this.displayedUserID.getText(), true))){
			Reporter.logEvent(Status.PASS, "Verify if the userid is displayed", "The user id is verified successfully", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if the userid is displayed", "The user id displayed on screen is not displayed correctly", true);
		}
	}

	/**
	 * <pre>
	 *  This method verifies the footerlinks across all the verification tabs while a new user logsin
	 * </pre>
	 * 
	 * @return the list of footerlinks as string
	 */
	public List<String> getFooterLinks() {
		footerlinksList = new LinkedList<String>();
		for (WebElement footer : footerLinks) {
			footerlinksList.add(footer.getText());
		}
		return footerlinksList;
	}

	/**
	 * <pre>
	 *  This method verifies the headerlinks across all the verification tabs while a new user logsin
	 * </pre>
	 * 
	 * @return the list of headerlinks as string
	 */

	public List<String> getHeaderLinks() {
		headerLinkslist = new LinkedList<String>();
		for (WebElement header : headerLinks) {
			headerLinkslist.add(header.getText());
		}
		return headerLinkslist;
	}

	/**
	 * <pre>
	 * The purpose of this method is to get the number of plans for an user
	 * </pre>
	 * 
	 * @param setDefaultPlanNullQuery
	 * @param username
	 * @throws Exception 
	 */
	public int getNumberOfplans(String[] getPlanNumberQuery, String username) throws Exception {
		int count = 0;
		resultset = DB.executeQuery(getPlanNumberQuery[0], getPlanNumberQuery[1], username);
		count = DB.getRecordSetCount(resultset);
		return count;
	}
/**
 * This method takes the user input for the new user flow in account verification screen
 * @param accountVerificationData
 */
	public void performVerification(String[] accountVerificationData) {
		String PlanNumber = accountVerificationData[0];
		String txtFirstDropdownAns = accountVerificationData[1];
		String txtSecondDropdownAns = accountVerificationData[2];
		String txtThirdDropdownAns = accountVerificationData[3];
		String txtRegUsername = accountVerificationData[4];

		Web.setTextToTextBox(this.txtPlanNumber, PlanNumber);
		Web.clickOnElement(this.btnNextPlanNumber);

		if (!Web.isWebElementDisplayed(errorMsgBox)) {
			Reporter.logEvent(Status.PASS, "Check the user input in plan number field",
					  "The user has input valid plan number", true);
			// Reporter.logEvent(Status.PASS,
			// "Check the user input in plan number field",
			// "The user has input valid plan number", true);
			Web.setTextToTextBox(txtFirstDropdownBox, txtFirstDropdownAns);
			Web.setTextToTextBox(txtSecondDropdownBox, txtSecondDropdownAns);
			Web.setTextToTextBox(txtThirdDropdownBox, txtThirdDropdownAns);
			Web.clickOnElement(btnNext);
		} else {
			throw new Error("User has made invalid input:" + PlanNumber);
		}
		if (!Web.isWebElementDisplayed(errorMsgBox)) {
			Reporter.logEvent(Status.PASS, "Validate the user input for security answer",
					"The user has used valid text for security answers field", true);
			// Reporter.logEvent(Status.PASS,
			// "Validate the user input for security answer",
			// "The user has used valid text for security answers field",
			// false);
			Web.setTextToTextBox(txtUsernameReg, txtRegUsername);
			Web.clickOnElement(btnNext);
		} else {
			throw new Error("The user has made invalid input for security questions:" + txtFirstDropdownAns + "Or"
					+ txtSecondDropdownAns + "Or" + txtThirdDropdownAns);
		}
		if (!Web.isWebElementDisplayed(errorMsgBox)) {
			Reporter.logEvent(Status.PASS,"Verify the user input for creating username",
					"The user has put valid username", true);
			// Reporter.logEvent(Status.PASS,
			// "Verify the user input for creating username",
			// "The user has put valid username", false);
		} else {
			throw new Error("Invalid input for username:" + txtRegUsername);
		}

	}

//	

	/**
	 * This method selects the unlocked plan for the user
	 */
	public void selectUnlockedPlan() {
		dropDownSelect = new Select(defaultPlandrpdwn);
		List<WebElement> dropdownList = dropDownSelect.getOptions();
		int iLoop = 0;
		while (dropdownList.iterator().hasNext()) {
			if (!StringUtils.containsIgnoreCase(dropdownList.get(iLoop).getText(), "LOCKED")) {
				dropDownSelect.selectByIndex(iLoop);
				break;
			}
			iLoop++;
		}
	}

	/**
	 * This method used to answer the security questions by selecting all the
	 * questions equal
	 */
	public void selectSameSecurityQuestions() {
		dropDownSelect = new Select(drpdownFirstSecurityQues);
		dropDownSelect.selectByIndex(2);
		dropDownSelect = new Select(drpdownSecondSecurityQues);
		dropDownSelect.selectByIndex(2);
		dropDownSelect = new Select(drpdownSecondSecurityQues);
		dropDownSelect.selectByIndex(2);
	}

	/**
	 * The method used to reset password when it is expired
	 */
//	public void resetPassword(String currentPwd, String newPwd, String confirmPwd) {
//		createNewPwdfields.get(iLoopCnt).click();
//		Web.setTextToTextBox(createNewPwdfields.get(iLoopCnt),currentPwd);
////	createNewPwdfields.get(1).click();
////	Web.setTextToTextBox(createNewPwdfields.get(1), newPwd);
////	createNewPwdfields.get(2).click();
////	Web.setTextToTextBox(createNewPwdfields.get(2), confirmPwd);
//		btnNext.click();	
//	}
	
	public void resetPassword(String... params) {
		 int iLoopCnt=0;
		 for(String param : params){
			 if(Web.isWebElementDisplayed(createNewPwdfields.get(iLoopCnt), true)){
			      createNewPwdfields.get(iLoopCnt).click();
				  Web.setTextToTextBox(createNewPwdfields.get(iLoopCnt),param);
			  }
			 iLoopCnt=iLoopCnt+1;
		 }		 
		 btnNext.click();	
	}

	/**
	 * This method recreates the existing users and returns the recently
	 * assigned planNumber
	 * 
	 * @param deleteFriendlyUsernameQuery
	 * @param updateAttributesQuery
	 * @param deleteSecurityQuestions
	 * @param username
	 * @throws Exception 
	 */

	public String recreateNewUser(String[] updateAttributesQuery, 
			String[] deleteSecurityQuestions, String[] deleteFriendlyUsernameQuery,String[] getRecentPlanQuery, String username) throws Exception {
		String recentPlan = "";
		DB.executeUpdate(updateAttributesQuery[0], updateAttributesQuery[1], username);
		DB.executeUpdate(deleteSecurityQuestions[0], deleteSecurityQuestions[1], username);
		DB.executeUpdate(deleteFriendlyUsernameQuery[0], deleteFriendlyUsernameQuery[1], username, username);
		
		resultset = DB.executeQuery(getRecentPlanQuery[0], getRecentPlanQuery[1], username);
		if (resultset != null) {
			while (resultset.next()) {
				recentPlan = resultset.getString("GA_ID");
			}
		}
		return recentPlan;
	}
	/**
	 * This method checks if the jump page is displayed for the users having access to multiple sites and skip it as required
	 */

	public void jumpPagedisplayed() {
		if (Web.isWebElementDisplayed(urlJumpPage))
			urlJumpPage.click();
	}

	/**
	 * This method adds a plan to an existing user
	 * @param addPlanQuery
	 * @param username
	 * @param planNumber
	 * @throws Exception
	 */
	public void addPlanNumber(String[] addPlanQuery, String username, String planNumber) throws Exception

	{

		DB.executeUpdate(addPlanQuery[0], addPlanQuery[1], "K_" + username, planNumber);

	}
	/**
	 * This methos returns the number of password rows in the isis_password table
	 * @param queryForisispassword
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int getNumberOfRows(String[] queryForisispassword,String username) throws Exception
	{
		resultset = DB.executeQuery(queryForisispassword[0], queryForisispassword[1], "K_"+username);
		return DB.getRecordSetCount(resultset);
	}
	/**
	 * This method deletes the old password rows keeping the latest one
	 * @param queryDeleteOldPassword
	 * @param username
	 * @throws Exception
	 */
	
	public void deleteOldPasswordrows(String[] queryDeleteOldPassword,String username) throws Exception
	{
		if(getNumberOfRows(Stock.getTestQuery("queryToNumberOfrows"),Stock.GetParameterValue("username")) > 1)
		{
		DB.executeUpdate(queryDeleteOldPassword[0], queryDeleteOldPassword[1],"K_"+username,"K_"+username);
		}
	}
	
	/**
	 * This method used to reset the password to user by a query which updates the effdate to effdate - 365
	 * @param updateUserEffdateQuery
	 * @param username
	 * @throws Exception
	 */
	public void resetPasswordQuery(String[] updateUserEffdateQuery,String username) throws Exception
	{
		DB.executeUpdate(updateUserEffdateQuery[0], updateUserEffdateQuery[1], "K_"+username);
		
	}
	/**
	 * This method sets the old password for the user by deleting the recently created row
	 * @param querytoDeleterecentRows
	 * @param queryResetOldPassword
	 * @param username
	 * @throws Exception
	 */
	public void setOldPassword(String[] querytoDeleterecentRows,String[] queryResetOldPassword,String username) throws Exception
	{
		DB.executeUpdate(querytoDeleterecentRows[0],querytoDeleterecentRows[1],"K_"+username,"K_"+username);
		
		Reporter.logEvent(Status.PASS, "Check if the query for deletion of the recent rows successful",
				"The query "+querytoDeleterecentRows[1]+"Executed successfully",false);
		
		DB.executeUpdate(queryResetOldPassword[0], queryResetOldPassword[1], "K_"+username);
		
		Reporter.logEvent(Status.PASS, "Check if the query for updating the old password successfull",
				"The query "+queryResetOldPassword[1]+"Executed successfully",false);
	}

	/**
	 * This methos will return the number of sites the user is having access to
	 * @param queryTofindAccesstoSites
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int getUseraccessForSites(String[] queryTofindAccesstoSites,String username) throws Exception
	{
	 	
		resultset =	DB.executeQuery(queryTofindAccesstoSites[0], queryTofindAccesstoSites[1], "K_"+username);
		return DB.getRecordSetCount(resultset);
		
	}
	/**
	 * This method will log out user from the application
	 * @throws InterruptedException
	 */
	public void logoutFromApplication() throws InterruptedException
	{
		if(Web.isWebElementDisplayed(linkLogout)){
			linkLogout.click();
		}
		else if(Web.isWebElementDisplayed(linkLogoutAccveri))
		{
			linkLogoutAccveri.click();
	
		}
		Thread.sleep(3000);
	}
	
	/**
	 * This method used to fetch the email address of the user
	 * @param getEmailQuery
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public String getEmailAddressOfuser(String[] getEmailQuery, String userid) throws Exception {
		String emailAddr = "";
		resultset = DB.executeQuery(getEmailQuery[0], getEmailQuery[1], "K_" + userid);
		if (resultset != null) {
			while (resultset.next()) {
				emailAddr = resultset.getString("EMAIL_ADDRESS");
			}
		}
		return emailAddr;
	}
	
	/**
	 * Method compares the actual error message with expected one
	 * @param actualErrorMessage
	 * @param expectedErrorMsg
	 */
	public void actualErrorValidationRptNegativeFlow(String actualErrorMessage, String expectedErrorMsg){
		if (!actualErrorMessage.trim().isEmpty()) {
			Reporter.logEvent(
					Status.PASS,
					"Verify error message "+actualErrorMessage+"is displayed for invalid user input",
					"Error message is displayed as expected for the invalid input",
					false);

			// Verify the displayed error message
			if ((Web.VerifyText(actualErrorMessage,expectedErrorMsg, true))) {
				Reporter.logEvent(
						Status.PASS,
						"Check if appropriate error message is displayed for invalid input",
						"Appropriate error message is successfully displayed as expected", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if appropriate error message is displayed for invalid input",
						"Appropriate error message is not displayed, Expected Message : "
						+expectedErrorMsg +" Actual : "+actualErrorMessage, true);
			}
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify error message "+actualErrorMessage+"is displayed for invalid user input",
					"Error message is not displayed for invalid input",
					true);
		}
	}
	
	/**
	 * Method checks if any error message is displayed for valid input and report appropriately
	 * @param actualErrorMessage
	 * @param scenarioDesc
	 */
	public void actualErrorValidationRptPositiveFlow(String actualErrorMessage, String scenarioDesc){
		if ((actualErrorMessage.trim().isEmpty())) {
			Reporter.logEvent(
					Status.PASS,
					"Check if the user has entered valid details for "+ scenarioDesc +" scenario",
					"The user is proceeed with Valid Input for "+ scenarioDesc +" scenario", false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Check if the user has entered valid details for "+ scenarioDesc +" scenario",
					"The user has failed to proceeed with provided input for "+ scenarioDesc +" scenario", true);
		}
	}
	/**
	 * It check the number of sites the user is having access to and check the fields accordingly
	 * @param userAccessToSite
	 * @param emailAddress
	 * @param recentPlan
	 * @throws Exception
	 */
	public void resetPassword(int userAccessToSite, String emailAddress, String recentPlan) throws Exception{
		if (userAccessToSite == 1) {
			resetPassword(
					Stock.GetParameterValue("CurrentPassword"),
					Stock.GetParameterValue("newPassword"),
					Stock.GetParameterValue("confirmPassword"), recentPlan);
		} else if (userAccessToSite > 1) {
			resetPassword(
					Stock.GetParameterValue("CurrentPassword"),
					Stock.GetParameterValue("newPassword"),
					Stock.GetParameterValue("confirmPassword"));
		}		
	}	
}
