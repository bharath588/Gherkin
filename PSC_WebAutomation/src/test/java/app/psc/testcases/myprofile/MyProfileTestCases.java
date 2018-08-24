/**
 * 
 */
package app.psc.testcases.myprofile;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import framework.util.CommonLib;
import pageobjects.accountverification.AccountVerificationPage;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.myProfile.MyProfilePage;
import pageobjects.userverification.UserVerificationPage;

/**
 * @author rvpndy
 *
 */
public class MyProfileTestCases {
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

	@Test(dataProvider = "setData")
	public void TC001_01_SIT_PSC_Update_My_profile_Default_Plan(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-10189");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"Verify updation of default GA Id from My Profile page",
					false);
			MyProfilePage myProfPage = new MyProfilePage();
			if (Stock.GetParameterValue("defaultGaId").equalsIgnoreCase("null")) {
				myProfPage.updateDefaultGaIdInDB();
				myProfPage.get();
				if (Web.returnElement(myProfPage, "Current Default Plan")
						.getText().equalsIgnoreCase("None")) {
					Reporter.logEvent(Status.PASS,
							"Verify default GA Id on my profile page",
							"Default GA ID is same as in database", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify default GA Id on my profile page",
							"Default GA ID is not same as in database", true);
				}
			} else {
				if (myProfPage.updateDefaultGaID(Stock
						.GetParameterValue("defaultGaId"))
						&& Web.returnElement(myProfPage, "Current Default Plan")
								.getText()
								.contains(myProfPage.getDefaultGaIdFromDb())) {
					Reporter.logEvent(
							Status.PASS,
							"Verify default plan is updated on my profile page",
							"Default plan is updated on my profile page", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify default plan is updated on my profile page",
							"Default plan is not updated on my profile page",
							true);
				}
			}
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC001_02_SIT_PSC_Update_My_profile_Default_Plan(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"This test case verifies that user is unable to update default plan on my profile page "
							+ "with locked or terminated plan");
			Reporter.logEvent(
					Status.INFO,
					"Testcase Description",
					"This test case verifies that user is unable to update default plan on my profile page "
							+ "with locked or terminated plan", false);
			MyProfilePage myProfPage = new MyProfilePage();
			myProfPage.get();
			if (!myProfPage.updateDefaultGaID(Stock
					.GetParameterValue("InvalidGaId"))) {
				Reporter.logEvent(
						Status.PASS,
						"Verify that user cannot update locked plan as default plan",
						"User is not able to update locked plan as default plan",
						true);
			} else
				Reporter.logEvent(
						Status.FAIL,
						"Verify that user cannot update locked plan as default plan",
						"User is able to update locked plan as default plan",
						true);
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC002_01_SIT_PSC_Update_Email_Address_Positive_Flow(int itr,
			Map<String, String> testData) {
		String prevEmailAddress = null;
		String updatedEmailAddress = null;
		boolean defaultEmailId= false;
		boolean isEmailUpdatedFromUI = false;
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"This test case "
							+ "verifies updation of email address from My Profile page.DDTC-1276 & "
							+ "DDTC-10188");
			Reporter.logEvent(Status.INFO, "Testcase description",
					"Email update from" + " my profile page. Positive flow",
					false);
			UserVerificationPage userVer = new UserVerificationPage();

			prevEmailAddress = userVer.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			MyProfilePage myProfPage = new MyProfilePage().get();
			isEmailUpdatedFromUI = myProfPage.updateEmailAddress();
			updatedEmailAddress = userVer.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			if (isEmailUpdatedFromUI
					&& (!prevEmailAddress.equals(updatedEmailAddress))) {
				defaultEmailId = myProfPage.updateDefaultEmailAddress();
				System.out.println(defaultEmailId + "Default email id");
				Reporter.logEvent(Status.PASS,
						"Email Id is updated in UI and DB",
						"Email ID is updated in UI and DB", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Email Id is updated in UI",
						"Email ID is not updated", true);
			}
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC002_02_SIT_PSC_Update_Email_Address_Negative_Flow(int itr,
			Map<String, String> testData) {
		String prevEmailAddress = null;
		String updatedEmailAddress = null;
		boolean isEmailUpdatedFromUI = false;
		try {
			Reporter.initializeReportForTC(
					itr,
					Globals.GC_MANUAL_TC_NAME,
					"This test case "
							+ "verifies email update button is diabled for invalid email format.DDTC-1276 & "
							+ "DDTC-10188");
			Reporter.logEvent(Status.INFO, "Testcase description",
					"Email update button"
							+ " disability for invalid email format", false);
			UserVerificationPage userVer = new UserVerificationPage();

			prevEmailAddress = userVer.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			MyProfilePage myProfPage = new MyProfilePage().get();
			isEmailUpdatedFromUI = myProfPage.updateEmailAddress(true);
			updatedEmailAddress = userVer.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));

			if (isEmailUpdatedFromUI
					&& (prevEmailAddress.equals(updatedEmailAddress))) {
				Reporter.logEvent(Status.PASS,
						"Check Email Id is not updated in UI and DB",
						"Email ID is not updated in UI and DB", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check Email Id is not updated in UI and DB",
						"Email ID is updated in UI and DB", true);
			}
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC002_03_SIT_PSC_Update_Email_Address_Invalid_Password(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-10188");
			Reporter.logEvent(Status.INFO, "Testcase description",
					"User should be logged out after"
							+ " entering wrong pwd for 3 times.", false);

			UserVerificationPage userVer = new UserVerificationPage();
			String prevEmailAddress = userVer.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));
			MyProfilePage myProfPage = new MyProfilePage().get();
			/*if (Web.isWebElementDisplayed(myProfPage,
					"Update Current Email Btn", false))
				Web.clickOnElement(myProfPage, "Update Current Email Btn");
			Web.setTextToTextBox(Web.returnElement(myProfPage, "New Email"),
					Stock.GetParameterValue("newEmailID"));
			Web.setTextToTextBox(
					Web.returnElement(myProfPage, "Confirm Email"),
					Stock.GetParameterValue("newEmailID"));*/
			myProfPage.enterWrongPasswordToLogout(Stock
					.getCsvParameterValue("badPasswords"));
			String updatedEmailAddress = userVer.getEmailAddressOfuser(
					Stock.getTestQuery("getEmailaddressQuery"),
					Stock.GetParameterValue("username"));

			if (prevEmailAddress.equals(updatedEmailAddress)
					&& Web.isWebElementDisplayed(
							Web.returnElement(new LoginPage(), "LOGIN FRAME"),
							true)) {
				Reporter.logEvent(Status.PASS,
						"Check user is logged out of application after 3 "
								+ "invalid password try",
						"User is logged out of application", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check user is logged out of application after 3 "
								+ "invalid password try",
						"User is not logged out of application", true);
			}
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC003_01_SIT_PSC_Update_User_Name(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-1534");
			Reporter.logEvent(Status.INFO,
					"Update registered user name on my profile page",
					"Update registered user name on my profile page", false);
			MyProfilePage myProfPage = new MyProfilePage();
			myProfPage.get();
			if (Stock.GetParameterValue("NegativeVerify")
					.equalsIgnoreCase("No")) {
				if (myProfPage.VerifyUpdateRegisteredUserName(false)) {
					Reporter.logEvent(Status.PASS,
							"Verify user name is updated",
							"User name is updated successfully", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify user name is updated",
							"User name is not updated", true);
				}
			} else if (Stock.GetParameterValue("NegativeVerify")
					.equalsIgnoreCase("Yes")) {
				if (!myProfPage.VerifyUpdateRegisteredUserName(true)) {
					Reporter.logEvent(Status.PASS,
							"Verify user name is not updated",
							"User name is not updated", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify user name is not updated",
							"User name is updated", true);

				}
			}
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC004_01_SIT_PSC_Update_My_profile_Security_Questions(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-10185");
			Reporter.logEvent(Status.INFO,
					"Update security questions on my profile page",
					"Update security questions on my profile page", false);
			MyProfilePage myProfPage = new MyProfilePage();
			myProfPage.get();
			if (!Boolean.valueOf(Stock.GetParameterValue("sameAnswer"))
					&& !Boolean.valueOf(Stock
							.GetParameterValue("sameQuestions"))
					&& !Boolean.valueOf(Stock
							.GetParameterValue("NegativeVerify"))) {
				if (myProfPage.selectSecQuestnAndAnswers(Boolean.valueOf(Stock
						.GetParameterValue("sameQuestions")), Boolean
						.valueOf(Stock.GetParameterValue("sameAnswer")),
						Integer.valueOf(Stock
								.GetParameterValue("questionIndex")), Boolean
								.valueOf(Stock
										.GetParameterValue("NegativeVerify")))) {
					Reporter.logEvent(Status.PASS,
							"Update Security Questions and Answers",
							"Security questions and answers are updated", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Update Security Questions and Answers",
							"Security questions and answers are not updated",
							true);
				}
			}
			if (!Boolean.valueOf(Stock.GetParameterValue("sameAnswer"))
					&& Boolean
							.valueOf(Stock.GetParameterValue("sameQuestions"))
					&& !Boolean.valueOf(Stock
							.GetParameterValue("NegativeVerify"))) {
				if (!myProfPage.selectSecQuestnAndAnswers(Boolean.valueOf(Stock
						.GetParameterValue("sameQuestions")), Boolean
						.valueOf(Stock.GetParameterValue("sameAnswer")),
						Integer.valueOf(Stock
								.GetParameterValue("questionIndex")), Boolean
								.valueOf(Stock
										.GetParameterValue("NegativeVerify")))) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message for same questions",
							"Error message is\n"
									+ Web.returnElement(new MyProfilePage(),
											"Duplicate Question Error")
											.getText(), false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify error message for same questions",
							"Error message is not displayed", true);
				}
			}
			if (Boolean.valueOf(Stock.GetParameterValue("sameAnswer"))
					&& !Boolean.valueOf(Stock
							.GetParameterValue("sameQuestions"))
					&& !Boolean.valueOf(Stock
							.GetParameterValue("NegativeVerify"))) {
				if (!myProfPage.selectSecQuestnAndAnswers(Boolean.valueOf(Stock
						.GetParameterValue("sameQuestions")), Boolean
						.valueOf(Stock.GetParameterValue("sameAnswer")),
						Integer.valueOf(Stock
								.GetParameterValue("questionIndex")), Boolean
								.valueOf(Stock
										.GetParameterValue("NegativeVerify")))) {
					Reporter.logEvent(
							Status.PASS,
							"Verify error message for same answers",
							"Error message is\n"
									+ Web.returnElement(new MyProfilePage(),
											"Duplicate Answer Error").getText(),
							false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Verify error message for same answers",
							"Error message is not displayed", true);
				}
			}
			if (!Boolean.valueOf(Stock.GetParameterValue("sameAnswer"))
					&& !Boolean.valueOf(Stock
							.GetParameterValue("sameQuestions"))
					&& Boolean.valueOf(Stock
							.GetParameterValue("NegativeVerify"))) {
				if (!myProfPage.selectSecQuestnAndAnswers(Boolean.valueOf(Stock
						.GetParameterValue("sameQuestions")), Boolean
						.valueOf(Stock.GetParameterValue("sameAnswer")),
						Integer.valueOf(Stock
								.GetParameterValue("questionIndex")), Boolean
								.valueOf(Stock
										.GetParameterValue("NegativeVerify")))) {
					Reporter.logEvent(
							Status.PASS,
							"Verify user is logged out after 3 invalid passwords",
							"User is logged out", false);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify user is logged out after 3 invalid passwords",
							"User is not logged out", true);
				}
			}
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC005_01_SIT_PSC_Update_My_profile_Password(int itr,
			Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-10186");
			Reporter.logEvent(Status.INFO,
					"Update password on my profile page",
					"Update password on my profile page", false);
			MyProfilePage myProfPage = new MyProfilePage();
			myProfPage.get();
			if (!Boolean.valueOf(Stock.GetParameterValue("negativeVerify"))) {
				if (myProfPage.updateUserPassword(Boolean.valueOf(Stock
						.GetParameterValue("negativeVerify")), Stock
						.GetParameterValue("password"), Stock
						.GetParameterValue("newPassword"))) {
					Reporter.logEvent(Status.PASS, "Verify update password",
							"Update password is working as expected", true);

				} else {
					Reporter.logEvent(Status.FAIL, "Verify update password",
							"Update password is not working as expected", true);
				}
			} else {
				if (!myProfPage.updateUserPassword(Boolean.valueOf(Stock
						.GetParameterValue("negativeVerify")), Stock
						.GetParameterValue("password"), Stock
						.GetParameterValue("newPassword"))) {
					Reporter.logEvent(
							Status.PASS,
							"Verify user is logged out after entering wrong password thrice",
							"user is logged out after entering wrong password thrice",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify user is logged out after entering wrong password thrice",
							"user is not logged out after entering wrong password thrice",
							true);
				}
			}

		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void TC006_01_FAQ_DDTC_245(int itr, Map<String, String> testData) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME,
					"DDTC-245");
			Reporter.logEvent(Status.INFO, "Open FAQ", "Open FAQ", false);
			MyProfilePage myProfPage = new MyProfilePage();
			myProfPage.get();
			if (myProfPage.verifyFaqPageLoad()) {
				Reporter.logEvent(Status.PASS, "FAQ page is loaded",
						"FAQ page is loaded", true);
			} else {
				Reporter.logEvent(Status.FAIL, "FAQ page is loaded",
						"FAQ page is not loaded", true);
			}
		} catch (Exception e) {
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL,
					"Assertion Error Occured. Loading of home page/login page/My Profile Page "
							+ "could not be verified.", errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@AfterSuite
	public void DriverQuite() {
		Web.getDriver().close();
		Web.getDriver().quit();
		Web.removeWebDriverInstance();
	}
}
