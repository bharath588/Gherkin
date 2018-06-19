/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pscBDD.fileSharing.FileSharingPage;
import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;

import pscBDD.planPage.PlanDMBACustomSitePage;
import pscBDD.userVerification.UserVerificationPage;
import bdd_lib.CommonLib;
import bdd_lib.Web;

import com.aventstack.extentreports.Status;

import bdd_core.framework.Globals;
import gherkin.formatter.model.Scenario;
import bdd_reporter.Reporter;

import pscBDD.userVerification.UserVerificationPage;
import bdd_core.framework.Globals;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import gherkin.formatter.model.Scenario;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author rvpndy
 *
 */
public class HomeStepDefinitions {
	public static String featureName = null;
	public static Scenario scenario = null;

	
	private static List<String> newTabUrl=new ArrayList<String>();


	LoginPage login;
	UserVerificationPage userVerPage;
	JumpPage jumpPage;
	HomePage homePage;
	PlanDMBACustomSitePage planDmbaPage;
	static int pertinentPopups = 0;
	static int homePageVisits = 0;
	public static int forgotPwdPageVisits = 0;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try {
			featureName = scenario.getId().split(";")[0];
			Globals.scenarioName = scenario.getName();
			System.out.println(scenario.getId());
			homePage = new HomePage();
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(
					";")[scenario.getId().split(";").length - 1]) - 1;
			System.out.println("Current iteration is: "+Globals.currentIteration);
			Reporter.initializeModule(featureName);
		} catch (Exception e) {
			Globals.currentIteration = 1;
		}

	}

	@After
	public void after() throws Exception {
		Reporter.finalizeTCReport();
	}

	@Given("^user is on Home page on the site \"([^\"]*)\"$")
	public void user_is_on_Home_page_on_the_site(String accuCode)
			throws Throwable {
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		homePage = new HomePage();
		homePage.get();
		
		if (Web.getDriver().getCurrentUrl().contains(accuCode)) {
			Reporter.logEvent(Status.PASS, "User is on home page of "
					+ accuCode, "User is on home page of " + accuCode, false);
		} else {
			Reporter.logEvent(Status.FAIL, "User is on home page of "
					+ accuCode, "User is not on home page of " + accuCode,
					false);
		}
	}

	@When("^user switches to \"([^\"]*)\"$")
	public void user_switches_to(String planNumber) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Globals.planNumber = planNumber;
		homePage = new HomePage();
		homePage.switchPlan(planNumber);
	}

	@Then("^GWC connect page is displayed$")
	public void gwc_connect_page_is_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		homePage.get();
		if (homePage.isSalesForcePage()) {
			Reporter.logEvent(Status.PASS,
					"User has landed on GWC connect page",
					"User has landed on GWC connect page", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"User has landed on GWC connect page",
					"User has not landed on GWC connect page", true);
		}

	}
	@Then("^GWC connect page should displayed$")
	public void gwc_connect_page_should_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (homePage.isSalesForcePage()) {
			Reporter.logEvent(Status.PASS,
					"User has landed on GWC connect page",
					"User has landed on GWC connect page", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"User has landed on GWC connect page",
					"User has not landed on GWC connect page", true);
		}

	}
	
	@Then("^GWC DMBA connect page is displayed$")
	public void gwc_dmba_connect_page_is_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		planDmbaPage = new PlanDMBACustomSitePage();
		planDmbaPage.get();
		if (planDmbaPage.isPlanDMBACustomSitePage()) {
			Reporter.logEvent(Status.PASS,
					"User has landed on GWC DMBA connect page",
					"User has landed on GWC DMBA connect page", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"User has landed on GWC connect page",
					"User has not landed on GWC connect page", true);
		}

	}

	@Given("^I am on GWC connect page$")
	public void i_am_on_GWC_connect_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		homePage = new HomePage();
		if (homePage.isSalesForcePage()) {
			Reporter.logEvent(Status.PASS,
					"User has landed on GWC connect page",
					"User has landed on GWC connect page", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"User has landed on GWC connect page",
					"User has not landed on GWC connect page", true);
		}
	}

	@Then("^username is displayed on the top right hand corner of the site$")
	public void username_is_displayed_on_the_top_right_hand_corner_of_the_site()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.logEvent(Status.INFO, "This is unavailable for MN",
				"This is unavailable for MN", true);
	}

	@Given("^User is on Home Page of NextGen$")
	public void user_is_on_Home_Page_of_NextGen() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		throw new PendingException();
	}

	@Given("^User is connected to a plan$")
	public void user_is_connected_to_a_plan() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		throw new PendingException();
	}

	@When("^user is on home page$")
	public void user_is_on_home_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		homePage.get();
		Reporter.logEvent(Status.INFO, "User is on home page",
				"User is on home page", true);
	}

	@Then("^the link should open a PDF in a new window$")
	public void the_link_should_open_a_PDF_in_a_new_window() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		if (homePage.isTnCOpensInNewWindow()) {
			Reporter.logEvent(Status.PASS,
					"Terms and Conditions link opens in a new window",
					"Terms and Conditions link opens in a new window", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Terms and Conditions link opens in a new window",
					"Terms and Conditions link does not open in a new window",
					true);
		}
	}

	@When("^user clicks on Terms and Conditions link displayed in the footer on home page$")
	public void user_clicks_on_Terms_and_Conditions_link_displayed_in_the_footer_on_home_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		homePage.clickOnTermsAndConditionLink();
	}

	@Then("^the Terms and Conditions link should not be displayed in the footer system tray on home page$")
	public void the_Terms_and_Conditions_link_should_not_be_displayed_in_the_footer_system_tray_on_home_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		if (homePage.isTnCDisplayedInSysTray()) {
			Reporter.logEvent(Status.PASS,
					"Terms and conditions link does not display in system "
							+ "tray on home page",
					"Terms and conditions link does not display in system tray "
							+ "on home page", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Terms and conditions link does not display in system "
							+ "tray on home page",
					"Terms and conditions link displays in system tray "
							+ "on home page", true);
		}

	}

	@When("^clicks on the System Requirement link$")
	public void clicks_on_the_System_Requirement_link() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		homePage.clickOnFooterLink("System Requirements");

	}

	@Then("^the System Requirements content should be displayed in a pop-up window$")
	public void the_System_Requirements_content_should_be_displayed_in_a_pop_up_window()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		if (homePage.isSysReqDisplayed()) {
			Reporter.logEvent(Status.PASS,
					"System requirement contents displayed",
					"System requirement contents displayed", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"System requirement contents displayed",
					"System requirement contents not displayed", true);
		}
	}

	@Given("^user is on the Home page of accuCode when user login with correct username and password$")
	public void user_is_on_the_Home_page_of_accuCode_when_user_login_with_correct_username_and_password(
			DataTable accAndCreds) throws Throwable {
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		if (!accAndCreds.equals(Globals.creds)) {
			Globals.creds = accAndCreds;
			LoginPage.setURL(Web.rawValues(Globals.creds).get(0)
					.get("accuCode"));
			if (Web.isWebElementDisplayed(Web.returnWebElement(new LoginPage(),
					"Logout")))
				//Web.clickOnElement(Web.returnWebElement(new LoginPage(),"Logout"));
				Web.actionsClickOnElement(Web.returnWebElement(new LoginPage(),"Logout"));
			new HomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		} else {
			new HomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		}

	}
	
	@Given("^user is on the Home page of DMBA when user login with correct username and password$")
    public void user_is_on_the_home_page_of_dmba_when_user_login_with_correct_username_and_password(DataTable accAndCreds) throws Throwable {
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		if (!accAndCreds.equals(Globals.creds)) {
			Globals.creds = accAndCreds;
			LoginPage.setURL(Web.rawValues(Globals.creds).get(0)
					.get("accuCode"));
			if (Web.isWebElementDisplayed(Web.returnWebElement(new LoginPage(),
					"Logout")))
				Web.clickOnElement(Web.returnWebElement(new LoginPage(),
						"Logout"));
			new PlanDMBACustomSitePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		} else {
			new PlanDMBACustomSitePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		}
    }

	
	@When("^user clicks on filesharing tab$")
	public void user_clicks_on_filesharing_tab() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		new FileSharingPage().get();
	}

	@Given("^user is on the Home page of \"([^\"]*)\" when user login with correct \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_is_on_the_Home_page_of_when_user_login_with_correct_and(
			String accuCode, String username, String password) throws Throwable {
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		List<List<String>> accuWithCreds = Arrays.asList(
				Arrays.asList("accuCode", "username", "password"),
				Arrays.asList(accuCode, username, password));
		DataTable dataTable = DataTable.create(accuWithCreds);
		Globals.creds = dataTable;
		LoginPage.setURL(accuCode);
		new LoginPage().get();
		new HomePage(new LoginPage(), false,
				new String[] { username, password }).get();

	}

	@Then("^the copyright date should be \"([^\"]*)\"$")
	public void the_copyright_date_should_be(String copyrightText)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (new HomePage().isCurrentYearDisplayedInCopyright(copyrightText))
			Reporter.logEvent(Status.PASS,
					"Current year displays in copyright",
					"Current year displays in copyright", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Current year displays in copyright",
					"Current year does not display in copyright", true);
	}

	@Then("^new \"([^\"]*)\" are displayed in the footer at post login page$")
	public void new_are_displayed_in_the_footer_at_post_login_page(
			@Delimiter(",") List<String> links) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		int count = 0;

		for (String link : links) {
			if (homePage.isLinkDisplayed(link))
				count++;
		}
		if (count == links.size()) {
			Reporter.logEvent(Status.PASS,
					"New links display at footer of home page",
					"New links display at footer of home page", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"New links display at footer of home page",
					"New links do not display at footer of home page", true);
		}
	}

	@When("^user clicks on new links \"([^\"]*)\"$")
	public void user_clicks_on_new_links(@Delimiter(",") List<String> links)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// String[] newLinks = links.split(",");
		for (String link : links) {
			homePage.clickOnFooterLink(link);
			if (homePage.isPertinentPopUp(link)) {
				pertinentPopups++;
			}
			if (Globals.scenarioName.contains("post login page")) {
				if (homePage.isHomePagePostPopUpClose()) {
					homePageVisits++;
				}
			}
			if (Globals.scenarioName.contains("forgot password page")) {
				if (new ForgotPasswordPage().isForgotPwdPagePostPopUpClose()) {
					forgotPwdPageVisits++;
				}
			}
		}
	}

	@Then("^pertinent page pop up$")
	public void pertinent_page_pop_up() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (pertinentPopups == 3) {
			Reporter.logEvent(Status.PASS, "All pertinent pop ups open",
					"All pertinent pop ups open", true);
			pertinentPopups = 0;
		} else {
			Reporter.logEvent(Status.FAIL, "All pertinent pop ups open",
					"All pertinent pop ups do not open", true);
			pertinentPopups = 0;
		}
	}

	@Then("^clicking close on pop up returns to post login page$")
	public void clicking_close_on_pop_up_returns_to_post_login_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (homePageVisits == 3) {
			Reporter.logEvent(Status.PASS,
					"clicking close on pop up returns to post login page",
					"clicking close on pop up returns to post login page", true);
			homePageVisits = 0;
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"clicking close on pop up returns to post login page",
					"clicking close on pop up do not return to post login page",
					true);
			homePageVisits = 0;
		}
	}
	@When("^User clicks on the \"([^\"]*)\"$")
    public void clicks_something(@Delimiter(",") List<String> links) throws Throwable {
		/*if(!(newTabUrl.isEmpty()))
			newTabUrl.clear();*/
		for(String link:links){
			try{
				String linkUrl=new LoginPage().openAndCloseNewTab(link);
				newTabUrl.add(linkUrl);//should remove newtaburl object
			}
			catch(Exception e){
				
			}
			
		}
        
    }
	
	@Then("^A new tab opens with \"([^\"]*)\"$")
	public void a_new_tab_opens_with_something(@Delimiter(",") List<String> url)
			throws Throwable {
		try {
			Collections.sort(newTabUrl);
			String[] arr = new String[newTabUrl.size()];
			arr = newTabUrl.toArray(arr);
			for (String featureUrl : url) {
				if (CommonLib.binarySearch(arr, featureUrl, 0, arr.length) >= 0) {
					Reporter.logEvent(Status.PASS,
							"A new tab  open with " + featureUrl,
							"A new tab opens with " + featureUrl, true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"A new tab opens with " + featureUrl,
							"A new tab isn't opens with " + featureUrl, true);
				}
			}

		} 
		catch (Exception e) {

		}

	}
	
	@Then("^following \"([^\"]*)\" should be displayed in the system tray$")
	public void following_something_should_be_displayed_in_the_system_tray(
			@Delimiter(",") List<String> links) throws Throwable {
		for (String link : links) {
			if (Web.isWebElementDisplayed(new LoginPage(), link, true)) {
				Reporter.logEvent(Status.PASS, link
						+ " link should displays in Pre/post-Login page", link
						+ " link displays in Pre/post-Login page", true);
			} else {
				Reporter.logEvent(Status.FAIL, link
						+ " link should displays in Pre/post-Login page", link
						+ " link does not displays in Pre/post-Login page",
						true);
			}
		}
	}

	@Then("^the Social Media \"([^\"]*)\" are displayed$")
	public void the_social_media_something_are_displayed(
			@Delimiter(",") List<String> icons) throws Throwable {
		for (String icon : icons) {
			if (Web.isWebElementDisplayed(new LoginPage(), icon, true)) {
				Reporter.logEvent(Status.PASS, "the Social Media " + icon
						+ "  displays in Pre-Login page",
						"the Social Media " + icon
								+ " displays in Pre-Login page", true);
			} else {
				Reporter.logEvent(Status.FAIL, "the Social Media " + icon
						+ " displays in Pre-Login page",
						"the Social Media " + icon
								+ " does not displays in Pre-Login page", true);
			}
		}
	}

	@Then("^the Social Media \"([^\"]*)\" are displayed in correct order$")
	public void the_social_media_something_are_displayed_in_correct_order(
			@Delimiter(",") List<String> icons) throws Throwable {
		/*List<String> listObject = new ArrayList<String>();
		for (String icon : icons) {
			listObject.add(icon);
		}*/
		new LoginPage().checkCorrectOrderOfSocialIconObjects(icons);

	}
	
	@Then("^PSC Home screen is displayed$")
    public void psc_home_screen_is_displayed() throws Throwable {
		if (homePage.isHomePage()) {
			Reporter.logEvent(Status.PASS, "PSC Home screen should displayes",
					"PSC Home screen is displays", true);
		} else {
			Reporter.logEvent(Status.FAIL, "PSC Home screen should displayes",
					"PSC Home screen doesn't displays", true);
		}
    }
	@When("^user selects to My Profile$")
    public void user_selects_to_my_profile() throws Throwable {
        homePage.clickOnMyProfile();
        Web.nextGenDriver.waitForAngular();
        
    }
	@Then("^the My Profile page should be displayed$")
    public void the_my_profile_page_should_be_displayed() throws Throwable {
		if (homePage.isMyProfilePage()) {
			Reporter.logEvent(Status.PASS, "My Profile page should displayes",
					"My Profile page is displays", true);
		} else {
			Reporter.logEvent(Status.FAIL, "My Profile page should displayes",
					"My Profile page isn't displays", true);
		}
    }



}
