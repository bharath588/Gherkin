package app.psc.testcases.accountverification;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.myProfile.MyProfilePage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.Globals;

public class accountverification_existingusr {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	AccountVerificationPage accountverification;
	LoginPage login;
	UserVerificationPage userverification;
	HomePage home;

	@BeforeClass
	public void InitTest() throws Exception {
		Reporter.initializeModule(this.getClass().getName());
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage()
				.getName(), testCase.getName());
	}


	/**
	 * <pre>Verify the expired password scenario for invalid user input</pre>
	 * @param itr
	 * @param testdata
	 * @throws SQLException
	 * @throws Exception
	 */

	@Test(dataProvider = "setData")
	public void TC004_01_Verify_Changepasword_When_Password_Expired_Negative_Scenario(
			int itr, Map<String, String> testdata) throws SQLException,
			Exception {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the expired password scenario for negative user input", false);
			userverification = new UserVerificationPage();
			accountverification = new AccountVerificationPage();
			accountverification.logoutFromApplication();
			String actualErrorMessage;
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			if (itr == 1) {
				userverification.performVerification(new String[] {
						(userverification.getEmailAddressOfuser(
								Stock.getTestQuery("getEmailaddressQuery"),
								Stock.GetParameterValue("username"))).trim(),
								Stock.GetParameterValue("UserSecondaryAns") });
			}
			accountverification.resetPassword(
					Stock.GetParameterValue("CurrentPassword"),
					Stock.GetParameterValue("newPassword"),
					Stock.GetParameterValue("confirmPassword"));			
			//actualErrorMessage = accountverification.getErrorMessageText();			

			if (Web.isWebElementDisplayed(accountverification, "ERRORMSG")) {				
				Web.clickOnElement(accountverification, "ERRORMSG");
				Web.clickOnElement(accountverification, "DISMISS");
			}
			/*accountverification.actualErrorValidationRptNegativeFlow(actualErrorMessage, 
					Stock.GetParameterValue("expectedErrorMessage"));*/
			if(!Web.returnElement(accountverification, "BTNNEXT").isEnabled())
				Reporter.logEvent(Status.PASS,"Enter invalid inputs.","Next button is disabled.", false);
			else
				Reporter.logEvent(Status.FAIL,"Enter invalid inputs.","Next button is not disabled.", true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>Verify the expired password scenario for valid user input</pre>
	 * @param itr
	 * @param testdata
	 * @throws Exception
	 */
	@Test(dataProvider = "setData")
	public void TC004_02_Verify_Changepasword_When_Password_Expired_Positive_Flow(
			int itr, Map<String, String> testdata) throws Exception {
		String actualErrorMessage = "";
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the expired password scenario for valid user input", false);			
			userverification = new UserVerificationPage();
			accountverification = new AccountVerificationPage();
			accountverification.deleteOldPasswordrows(
					Stock.getTestQuery("deleteOldPasswordRows"),
					Stock.GetParameterValue("username"));
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();
			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
							Stock.GetParameterValue("UserSecondaryAns") });
			if (Web.isWebElementDisplayed(accountverification, "ERRORMSG")) {
				Web.clickOnElement(accountverification, "ERRORMSG");
				Web.clickOnElement(accountverification, "DISMISS");
			}
			Thread.sleep(2000);
			accountverification.resetPassword(
					Stock.GetParameterValue("CurrentPassword"),
					Stock.GetParameterValue("newPassword"),
					Stock.GetParameterValue("confirmPassword"));

			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));

			actualErrorMessage = accountverification.getErrorMessageText();
			accountverification.actualErrorValidationRptPositiveFlow(actualErrorMessage,"Changed Password");
		} catch (Exception e) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}catch (Error ae) {
			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <pre>
	 * Testcase: <b><u>SIT_PSC_Login_01_TC019_Default plan access</u></b>
	 *  
	 *  Application: <b>PSC</b>
	 *  Functionality: <b>Login</b>
	 *  Sub-functionality: <b>Default Planprompt</b>
	 *  Test Type: <b>Positive validation</b>
	 *  
	 *  <b>Description:</b>
	 *             A random user will be required to go through default plan prompting if default ga_id = NULL
	 *  
	 *  <u><b>Test data:</b></u>
	 *  <u><b>Test data:</b></u> <b>txtUsername-</b> Username required to login
	 *  <b>txtPassword-</b> Password required to login 
	 *  <b>PlanNumber -</b> Input for plan number
	 *  <b>setDefaultplanNullQuery  -</b> Query to set default plan id to NULL
	 * <b>getPlanNUmberQuery -</b> Query to fetch the total number of plans for the user 
	 * 
	 * <b>Number of iterations -</b> 3
	 * @author krsbhr
	 * 
	 */

	@Test(dataProvider = "setData")
	public void TC019_Verify_Default_Plan_Prompting(int itr,
			Map<String, String> testdata) throws SQLException, Exception {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify the Default plan prompting sceanrio for an user according to the number of plans"
							+ "The user has access to", false);
			int PlanCount = 0;
			
			String[] setDeafultPlanNullQuery;
			accountverification = new AccountVerificationPage();
			userverification = new UserVerificationPage();
			accountverification.logoutFromApplication();
			setDeafultPlanNullQuery = Stock
					.getTestQuery("setDefaultPlanNullQuery");
			DB.executeUpdate(setDeafultPlanNullQuery[0],
					setDeafultPlanNullQuery[1],
					"K_" + Stock.GetParameterValue("username"));
			PlanCount = accountverification.getNumberOfplans(
					Stock.getTestQuery("getNumberOfplansQuery"),
					"K_" + Stock.GetParameterValue("username"));
			System.out.println("The number of plans the user has:" + PlanCount);
			//userverification.get();
			login = new LoginPage().get();
			login.submitLoginCredentials(new String[]{Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password")});
			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
					Stock.GetParameterValue("UserSecondaryAns")});
			if(Web.isWebElementDisplayed(userverification,"DEFAULT_PLAN_INPUT_FIELD",true)){
				userverification.enterPlanWhenDefaultPlanIsNull();
			}
			if (PlanCount > 25) {

				if ((Web.isWebElementDisplayed(accountverification,
						"SEARCH DEFAULT PLAN"))) {
					Reporter.logEvent(
							Status.PASS,
							"Check if search box displayed for default plan prompting displayed for user having more than 25 plans",
							"Search box is displayed for users having more than 25 plans",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if search box displayed for default plan prompting displayed for user having more than 25 plans",
							"Search box is not displayed for users having more than 25 plans",
							true);
				}
			}
			if (PlanCount > 1 && PlanCount < 25) {
				if ((Web.isWebElementDisplayed(accountverification,
						"DEFAULT PLAN DROPDOWN"))) {
					Reporter.logEvent(
							Status.PASS,
							"Check if dropdown box is displayed for the number of plans less than 25",
							"The dropdown box is displayed for the user having plan number 1 to 25",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if dropdown box is displayed for the number of plans less than 25",
							"The dropdown box is not displayed for user having plan number 1 to 25",
							true);
				}
			}
			if (PlanCount == 1) {
				accountverification.get();
				if ((!Web.isWebElementDisplayed(accountverification,
						"SEARCH DEFAULT PLAN"))
						&& !(Web.isWebElementDisplayed(accountverification,
								"DEFAULT PLAN DROPDOWN"))) {
					Reporter.logEvent(
							Status.PASS,
							"Check if default plan prompt page is displayed for user having one plan",
							"The system doesnot prompt user to enter default plan if it is having only one plan",
							false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Check if default plan prompt page is displayed for user having one plan",
							"Default plan page is displayed for the users having one plan",
							true);
				}
			}
			Web.clickOnElement(accountverification, "BTNNEXT");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}


	/**
	 * <pre>
	 * Testcase: <b><u>SIT_PSC_Login_01_TC018_Existing User_Security questions validation</u></b>
	 *  
	 *  Application: <b>PSC</b>
	 *  Functionality: <b>Login</b>
	 *  Sub-functionality: <b>Security Questions</b>
	 *  Test Type: <b>Positive validation</b>
	 *  
	 *  <b>Description:</b>
	 *  When the security questions are deleted for the user should be asked to re-enter them 
	 *  
	 *  <u><b>Test data:</b></u>
	 *   <b>txtUserName       -</b> Valid user name
	 *             <b>txtPassword               -</b> Valid password
	 *             <b>deleteSecurityQuesQuery -</b> Query to delete security questions
	 *  <b>firstDrpdwnAns -</b> Answer for the first dropdown 
	 *  <b>secondDrpdwnAns -</b> Answer for the second dropdown 
	 *  <b>thirdDrpdwnAns -</b> Answer for the third fropdown
	 * <b>Number of iterations = </b> 1
	 * @author krsbhr
	 * 
	 */
	@Test(dataProvider = "setData")
	public void TC018_Security_Question_Validation_Existing_User(int itr,
			Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates if the user is navigated to security answers page when the security answers are"
							+ "deleted in database", false);
			accountverification = new AccountVerificationPage();
			String[] queryDeleteSecurityAnswers;
			accountverification.logoutFromApplication();
			// Step-1 : Delete the security questions for the user from DB
			queryDeleteSecurityAnswers = Stock
					.getTestQuery("deleteSecurityAnsQuery");

			DB.executeUpdate(queryDeleteSecurityAnswers[0],
					queryDeleteSecurityAnswers[1],
					"K_" + Stock.GetParameterValue("username"));

			// Step-2 : Check if the user is prompted to answer security
			// questions
			accountverification.get();

			if ((Web.isWebElementDisplayed(accountverification,
					"SECURITY ANSWER"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify if the user is prompted to security question on deletion",
						"The user is prompted for security question", false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify if the user is prompted to security question on deletion",
						"The security questions are not displayed", true);
			}

			// Step-2 : Enter all the answers and click next
			accountverification.answerSecurityQuestions(
					Stock.GetParameterValue("firstDrpdwnAns"),
					Stock.GetParameterValue("secondDrpdwnAns"),
					Stock.GetParameterValue("thirdDrpdwnAns"));

			accountverification.actualErrorValidationRptPositiveFlow(accountverification.getErrorMessageText(),"Security Questions");

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		}  finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC005_01_Verify_PSC_Users_Are_Unbale_To_Reuse_Last_Ten_Passwords(int itr,Map<String,String> testData)
	{
		String actualErrorMessage = "";
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify user is unable to use last 10 passwords", false);			
			userverification = new UserVerificationPage();
			accountverification = new AccountVerificationPage();
			accountverification.logoutFromApplication();
			accountverification.resetPasswordQuery(
					Stock.getTestQuery("updateUserEffdateQuery"),
					Stock.GetParameterValue("username"));
			accountverification.get();

			userverification.performVerification(new String[] {
					(userverification.getEmailAddressOfuser(
							Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"))).trim(),
							Stock.GetParameterValue("UserSecondaryAns") });

			if (Web.isWebElementDisplayed(accountverification, "ERRORMSG")) {
				Web.clickOnElement(accountverification, "ERRORMSG");
				Web.clickOnElement(accountverification, "DISMISS");
			}
			Thread.sleep(2000);
			accountverification.resetPassword(
					Stock.GetParameterValue("CurrentPassword"),
					Stock.GetParameterValue("newPassword"),
					Stock.GetParameterValue("confirmPassword"));
			
			if(!Web.returnElement(accountverification, "BTNNEXT").isEnabled())
				Reporter.logEvent(Status.PASS,"Enter any of the last 10 passwords.","Next button is disabled and"
						+ " hence user can't use that password.", false);
			else
				Reporter.logEvent(Status.FAIL,"Enter any of the last 10 passwords.","Next button is not disabled and"
						+ " user can use that password.", true);

			accountverification.setOldPassword(
					Stock.getTestQuery("deleteRecentPasswordRows"),
					Stock.getTestQuery("queryTosetOldPassword"),
					Stock.GetParameterValue("username"));

			/*actualErrorMessage = accountverification.getErrorMessageText();
			System.out.println(actualErrorMessage);
			accountverification.actualErrorValidationRptNegativeFlow(actualErrorMessage,
					Stock.GetParameterValue("expectedErrorMessage"));*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
					errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void TC006_01_Verify_Display_Of_Notification_On_HomePage(int itr,Map<String,String> testData)
	{
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify 'Notification' button is available and working as expected", false);
			accountverification = new AccountVerificationPage();
			if(Stock.GetParameterValue("infoType").equalsIgnoreCase("NonAvailability"))
			{
				HomePage homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
				if(!Web.isWebElementDisplayed(homePage, "todoLink", false) &&(!Web.isWebElementDisplayed(homePage, "notificationLink", false))){
					Reporter.logEvent(Status.PASS, "Verify Notification button is not available and working as expected if txn code is not given to user", 
							"Notification and To Do hyperlinks are not available on home page", false);
					homePage.logoutPSC();

				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify Notification button is not available and working as expected if txn code is not given to user", 
							"Notification and To Do hyperlinks are available on home page", true);
					homePage.logoutPSC();
				}
			}
			else if(Stock.GetParameterValue("infoType").equalsIgnoreCase("Availability"))
			{
				accountverification.addTransactionCode(Stock.getTestQuery("addTransactionCode"), Stock.GetParameterValue("TxnCode"), 
						"K_"+Stock.GetParameterValue("username"));
				HomePage homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
				if(Web.isWebElementDisplayed(homePage, "todoLink", false) &&(Web.isWebElementDisplayed(homePage, "notificationLink", false))){
					Reporter.logEvent(Status.PASS, "Verify Notification button is available and working as expected if txn code is given to user", 
							"Notification and To Do hyperlinks are available on home page", false);
					homePage.logoutPSC();

				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify Notification button is available and working as expected if txn code is given to user", 
							"Notification and To Do hyperlinks are not available on home page", true);
					homePage.logoutPSC();
				}

				accountverification.deleteTransactionCode(Stock.getTestQuery("deleteTransactionCode"), Stock.GetParameterValue("TxnCode"), 
						"K_"+Stock.GetParameterValue("username"));
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
					errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC006_01_Verify_Display_Of_Compliance_On_HomePage(int itr,Map<String,String> testData)
	{
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify 'Compliance' button is available and working as expected", false);
			accountverification = new AccountVerificationPage();
			if(Stock.GetParameterValue("infoType").equalsIgnoreCase("NonAvailability"))
			{
				HomePage homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
				if(!Web.isWebElementDisplayed(homePage, "complianceLink", false)){
					Reporter.logEvent(Status.PASS, "Verify Compliance hyperlink is not available and working as expected if txn code is not given to user", 
							"Compliance hyperlink is not available on home page", false);
					homePage.logoutPSC();

				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify Compliance button is not available and working as expected if txn code is not given to user", 
							"Compliance hyperlink is available on home page", true);
					homePage.logoutPSC();
				}
			}
			else if(Stock.GetParameterValue("infoType").equalsIgnoreCase("Availability"))
			{
				accountverification.addTransactionCode(Stock.getTestQuery("addTransactionCode"), Stock.GetParameterValue("TxnCode"), 
						Stock.GetParameterValue("username"));
				HomePage homePage = new HomePage(new LoginPage(), false, new String[] {
					Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password") }).get();
				if(Web.isWebElementDisplayed(homePage, "complianceLink", false)){
					Reporter.logEvent(Status.PASS, "Verify Compliance button is available and working as expected if txn code is given to user", 
							"Compliance hyperlink is available on home page", false);
					homePage.logoutPSC();

				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify Compliance button is available and working as expected if txn code is given to user", 
							"Compliance hyperlink is not available on home page", true);
					homePage.logoutPSC();
				}

				accountverification.deleteTransactionCode(Stock.getTestQuery("deleteTransactionCode"), Stock.GetParameterValue("TxnCode"), 
						Stock.GetParameterValue("username"));
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page or login page could not be verified.",
					errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC009_01_Verify_Multifactoring(int itr, Map<String,String> testData)
	{
		String prevEmailAddress = null;
		String updatedEmailAddress = null;
		boolean isEmailUpdatedFromUI = false;
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify Multifactoring", false);
			UserVerificationPage userVer = new UserVerificationPage();

			prevEmailAddress = userVer.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"), 
					Stock.GetParameterValue("username"));
			MyProfilePage myProfPage = new MyProfilePage().get();
			isEmailUpdatedFromUI = myProfPage.updateEmailAddress();
			updatedEmailAddress = userVer.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"), 
					Stock.GetParameterValue("username"));
			if(isEmailUpdatedFromUI && (!prevEmailAddress.equals(updatedEmailAddress)))
			{
				Reporter.logEvent(Status.PASS, "Email Id is updated in UI and DB", "Email ID is updated in UI and DB", false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL, "Email Id is updated in UI", "Email ID is not updated", true);
			}


		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page/login page/My Profile Page could not be verified.",
					errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider="setData")
	public void TC012_SIT_PSC_HomePage_02_TC003_Compliance_Security(int itr, Map<String,String>testData)
	{
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME, 
					"Compliance security: Cheking group/plan in database whether it has "
							+ "permission to access compliance button in UI");
			HomePage homepage = new HomePage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			AccountVerificationPage actVerPage = new AccountVerificationPage();
			if(Stock.GetParameterValue("complianceEnable").equalsIgnoreCase("true")){
				Reporter.logEvent(Status.INFO, "Check compliance option enablity for a plan", 
						"Checking compliance option enablity",
						false);
				if(actVerPage.fetchCompanyCodeForPlan(Stock.getTestQuery("complianceSecurity"), 
						Stock.GetParameterValue("PlanNumber"))){


					if(homepage.searchPlan())
					{
						if(Web.isWebElementDisplayed(new HomePage(), "Compliance Menu", true)&&
								!Web.returnElement(new HomePage(), 
										"Compliance Menu").getAttribute("class").contains("ui-state-disabled")){
							Reporter.logEvent(Status.PASS, "Verify compliance option is available and enabled",
									"compliance option is available and enabled", false);
						}
					}
					else
					{
						Reporter.logEvent(Status.FAIL, "Verify switching to plan with compliance option availability",
								"Plan is not switched", true);
					}
				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify Compliance option is available for the given plan number", 
							"Compliance option is not available for the given plan", true);
				}


			}
			if(Stock.GetParameterValue("complianceEnable").equalsIgnoreCase("false")){

				Reporter.logEvent(Status.INFO, "Check compliance option disability for a plan", 
						"Checking compliance option disability for a plan",
						false);
				if(!actVerPage.fetchCompanyCodeForPlan(Stock.getTestQuery("complianceSecurity"), 
						Stock.GetParameterValue("PlanNumber"))){


					if(homepage.searchPlan())
					{
						if(Web.returnElement(new HomePage(), 
								"Compliance Menu").getAttribute("class").contains("ui-state-disabled")){
							Reporter.logEvent(Status.PASS, "Verify compliance option is available and disabled",
									"compliance option is available and disabled on home page", false);
						}
						else
						{
							Reporter.logEvent(Status.FAIL, "Verify compliance option is available and disabled",
									"compliance option disablility could not be verified on home page", true);
						}
					}
					else
					{
						Reporter.logEvent(Status.PASS, "Verify switching to plan with compliance option availability",
								"Plan is not switched", true);
					}
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page/login page/My Profile Page could not be verified.",
					errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider="setData")
	public void TC013_SIT_PSC_HomePage_01_TC010_Regression_Testing_New_Pages(int itr, Map<String, String>testData)
	{
		try
		{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME, 
					"Regression testing after after getting keystores");
			HomePage homePage = new HomePage();
			new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
			if(Stock.GetParameterValue("menuType").equalsIgnoreCase("menu")){
				if(homePage.navigatedToMenuPage(Stock.GetParameterValue("menuLabel"),
						Stock.GetParameterValue("breadCrumb")))
				{
					Reporter.logEvent(Status.PASS, "Verify page visits", "Required page visit completed", false);
				}
				else
				{
					Reporter.logEvent(Status.FAIL, "Verify page visits", "Required page visit failed", true);
				}
			}
			else if(Stock.GetParameterValue("menuType").equalsIgnoreCase("actionButton"))
			{
				if(homePage.navigatedFromActionButton(Stock.GetParameterValue("menuLabel"),
						Stock.GetParameterValue("breadCrumb")))
					Reporter.logEvent(Status.PASS, "Verfy page visit from home page action button",
							"Page visit verified", false);
				else
					Reporter.logEvent(Status.FAIL, "Verfy page visit from home page action button",
							"Page visit could not be verified", true);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		}
		catch(Error ae)
		{
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, 
					"Assertion Error Occured. Loading of home page/login page/My Profile Page could not be verified.",
					errorMsg, true);
		}
		finally{
			try
			{
				Reporter.finalizeTCReport();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}

	}


	@AfterSuite
	public void DriverQuite() {
		Web.getDriver().quit();
	}

}