/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import java.util.List;
import java.util.Map;

import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.fundProspectus.FundProspectus;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.userVerification.UserVerificationPage;

import bdd_lib.Web;

import com.aventstack.extentreports.Status;

import bdd_core.framework.Globals;



import bdd_reporter.Reporter;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author rvpndy
 *
 */
public class LoginStepDefinitions {
	public static String featureName = null;
	public static Scenario scenario = null;
	LoginPage login;
	UserVerificationPage userVerPage;
	JumpPage jumpPage;
	HomePage homePage;
	ForgotPasswordPage forgotPwdPage;
	static boolean suspended = true;
	static int correctPageVisit = 0;
	static int correctPreloginPageVisit = 0;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try {
			login = new LoginPage();
			homePage = new HomePage();
			forgotPwdPage = new ForgotPasswordPage();
			featureName = scenario.getId().split(";")[0];
			Globals.scenarioName = scenario.getName();
			Reporter.initializeModule(featureName);
			LoginStepDefinitions.scenario = scenario;
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(
					";")[scenario.getId().split(";").length - 1]) - 1;
			System.out.println("Current iteration is: "+Globals.currentIteration);
		} catch (Exception e) {
			Globals.currentIteration = 1;
		}
	}

	@After
	public void after() throws Exception {
		Reporter.finalizeTCReport();
	}

	@Given("^Open browser and launch PSC application$")
	public void openBrowserAndLaunchPSCApp() {
		try {
			Reporter.initializeReportForTC(1, Globals.scenarioName);
			// testWeb.getDriver().manage().deleteAllCookies();
			login = new LoginPage();
			login.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Given("^Open browser and launch PSC application for the given accuCode$")
	public void openBrowserAndLaunchPSCAppForTheAccuCode(DataTable accAndCreds) {
		try {
			Reporter.initializeReportForTC(1, Globals.scenarioName);
			if (!accAndCreds.equals(Globals.creds)) {
				Globals.creds = accAndCreds;
				LoginPage.setURL(Web.rawValues(Globals.creds).get(0)
						.get("accuCode"));
				login = new LoginPage();
				login.get();
				
			} 
			else {
				login = new LoginPage();
				login.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@When("^I enter valid username and password$")
	public void enterLoginCredentials(DataTable usercredentials) {

		System.out.println("Enter UserName and Password");
		List<Map<String, String>> data = usercredentials.asMaps(String.class,
				String.class);
		login = new LoginPage();
		login.submitLoginCredentials(data.get(0).get("username"), data.get(0)
				.get("password"));
		/*
		 * HomePage homePage = new HomePage(Web.getDriver()); homePage.get();
		 */

	}

	@And("^click on Sign On button$")
	public void clickOnSignOn() {
		Reporter.logEvent(Status.PASS, "Verify User is Logged in",
				"user is logged in", false);
	}

	@Then("^I should be navigated to user verification page$")
	public void navigationToUserVerificationPage() {
		userVerPage = new UserVerificationPage();
		userVerPage.get();
	}

	@When("^I enter email address in email box$")
	public void enterEmailOnUserVerificationPage(DataTable userVerData) {
		Reporter.logEvent(Status.PASS, "Enter email on user verification page",
				"Enter email on user verification page", false);
		List<String> emailIds = userVerData.asList(String.class);
		userVerPage = new UserVerificationPage();
		userVerPage.performVerification(emailIds.get(1));
	}

	@And("^I enter security question answer$")
	public void enterSecurityQuestionAnswer(DataTable secAnswer) {
		Reporter.logEvent(Status.PASS,
				"Enter security answer on user verification page",
				"Enter security answer on user verification page", false);
	}

	@And("^I click on Next button$")
	public void clickOnNextButton() {
		Reporter.logEvent(Status.PASS,
				"click on Next button on user verification page",
				"click on Next button on user verification page", false);
	}

	@Then("^I should be naviagted to jump page$")
	public void jumpPageLoad() {
		jumpPage = new JumpPage();
		jumpPage.get();
		Reporter.logEvent(Status.PASS, "jump page is loaded",
				"jump page is loaded", false);
	}

	@And("^I click on nextGen page link$")
	public void clickOnNextGenLink() {
		jumpPage = new JumpPage();
		jumpPage.clickOnJumpPageLink();
		Reporter.logEvent(Status.PASS, "click on Next Gen Link on jump page",
				"click on Next Gen Link on jump page", false);
	}

	@Then("^I should be navigated to home Page$")
	public void navigateToHomePage() {
		homePage = new HomePage();
		homePage.get();
		Reporter.logEvent(Status.PASS, "User is on home page",
				"User is on home page", false);
	}

	@Given("^I am on user verification page$")
	public void i_am_on_user_Verification_page() throws Throwable {
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		Reporter.logEvent(Status.INFO, "I am on user verification page",
				"I am on user verification page", false);
	}

	@Given("^I am on jump page$")
	public void i_am_on_jump_page() throws Throwable {
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		Reporter.logEvent(Status.INFO, "I am on jump page",
				"I am on jump page", false);
	}

	@Given("^User successfully navigated to \"([^\"]*)\"$")
	public void user_successfully_navigated_to(String accuCode)
			throws Throwable {
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		login = new LoginPage();
		LoginPage.setURL(accuCode);
		login.get();

	}

	@When("^user enters \"([^\"]*)\" and \"([^\"]*)\" and clicks on sign in button$")
	public void user_enters_and_and_clicks_on_sign_in_button(String username,
			String password) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		login.submitLoginCredentials(username, password);
	}

	@Then("^the Home page should be displayed$")
	public void the_Home_page_should_be_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		homePage = new HomePage();
		homePage.get();
		Reporter.logEvent(Status.PASS, "User is on home page",
				"User is on home page", false);
	}

	@When("^I enter valid username and invalid password$")
	public void i_enter_valid_username_and_invalid_password(
			DataTable usercredentials) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		List<Map<String, String>> data = usercredentials.asMaps(String.class,
				String.class);
		login = new LoginPage();
		login.submitLoginCredentials(data.get(0).get("username"), data.get(0)
				.get("password"));

	}

	@Then("^Error message is displayed$")
	public void error_message_is_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		if (login.isErrorDisplayed()) {
			Reporter.logEvent(Status.PASS,
					"Error message displays for invalid username or password",
					"Error message displays for invalid username or password",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Error message displays for invalid username or password",
					"Error message does not display for invalid username or password",
					true);
		}
	}

	@When("^I enter invalid username and invalid password$")
	public void i_enter_invalid_credentials(DataTable usercredentials)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		List<Map<String, String>> data = usercredentials.asMaps(String.class,
				String.class);
		login = new LoginPage();
		login.submitLoginCredentials(data.get(0).get("username"), data.get(0)
				.get("password"));
	}

	@Given("^user is on the Login page of accuCode$")
	public void user_is_on_the_Login_page_of_accuCode(DataTable accuCode)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		List<String> accu = accuCode.asList(String.class);
		login = new LoginPage();
		LoginPage.setURL(accu.get(1));
		// System.out.println(accu.get(1));
		login.get();
	}

	@When("^user clicks on Terms and Conditions link displayed in the footer$")
	public void user_clicks_on_Terms_and_Conditions_link_displayed_in_the_footer()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		login.clickOnTermsAndConditionsLink();
		Reporter.logEvent(Status.INFO, "Click on Terms and Conditions link",
				"Clicked on Terms and Conditions link", true);
	}

	@Then("^the link should open a PDF in a new tab$")
	public void the_link_should_open_a_PDF_in_a_new_tab() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		if (login.tNCOpensInNewTab()) {
			Reporter.logEvent(Status.PASS,
					"Terms and Conditions link opens in new tab",
					"Terms and Conditions link opens in new tab", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Terms and Conditions link opens in new tab",
					"Terms and Conditions link does not open in new tab", true);
		}
	}

	@When("^user scroll down to footer section$")
	public void user_scroll_down_to_footer_section() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Web.scrollDown();
		Thread.sleep(2000);
		Reporter.logEvent(Status.INFO, "Scroll down to footer section",
				"Scroll down to footer section", true);
	}
	@When("^user scroll down to Pre-Login Banner section$")
	public void user_scroll_down_to_banner_section() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Web.scrollDown();
		Thread.sleep(2000);
		Reporter.logEvent(Status.INFO, "Scroll down to Banner section",
				"Scroll down to Banner section", true);
	}
	@When("^user scroll down to Pre-Login Content Tiles section$")
	public void user_scroll_down_to_content_tiles_section() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Web.scrollDown();
		Thread.sleep(2000);
		Reporter.logEvent(Status.INFO, "Scroll down to Banner section",
				"Scroll down to Banner section", true);
	}

	@Then("^the Terms and Conditions link should not be displayed in the footer system tray$")
	public void the_Terms_and_Conditions_link_should_not_be_displayed_in_the_footer_system_tray()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		if (login.isTnCDisplayedInSysTray()) {
			Reporter.logEvent(
					Status.PASS,
					"Terms and conditions link does not display in system tray",
					"Terms and conditions link does not display in system tray",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Terms and conditions link does not display in system tray",
					"Terms and conditions link displays in system tray", true);
		}
	}

	@When("^user enters username and password$")
	public void user_enters_username_and_password(DataTable creds)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		login = new LoginPage();
		login.get();
		Globals.creds = creds;
		List<Map<String, String>> credentials = creds.asMaps(String.class,
				String.class);
		login.submitLoginCredentials(credentials.get(0).get("username"),
				credentials.get(0).get("password"));
	}

	@When("^user clicks on Sign In button$")
	public void user_clicks_on_Sign_In_button() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.logEvent(Status.INFO, "User clicks on Sign In button",
				"User clicks on Sign In button", true);
	}

	@When("^clicks on the System Requirement link on login page$")
	public void clicks_on_the_System_Requirement_link_on_login_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		login.clickOnFooterLink("System Requirements");
		Reporter.logEvent(Status.INFO,
				"Click on System Requirements link on login page",
				"Click on System Requirements link on login page", true);

	}

	@Then("^the System Requirements content should be displayed$")
	public void the_System_Requirements_content_should_be_displayed()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		if (login.isSysReqDisplayed()) {
			Reporter.logEvent(Status.PASS,
					"System requirement contents displayed",
					"System requirement contents displayed", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"System requirement contents displayed",
					"System requirement contents not displayed", true);
		}

	}

	@Then("^the following text should be displayed$")
	public void the_following_text_should_be_displayed(String sysReqDocString)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		homePage = new HomePage();
		forgotPwdPage = new ForgotPasswordPage();
		if (Globals.scenarioName.endsWith("Post-Login page")
				|| Globals.scenarioName.endsWith("Forgot password page")) {
			if (login.doTextMatches(sysReqDocString,
					Web.returnWebElement(forgotPwdPage, "Footer iFrame"))) {
				Reporter.logEvent(Status.PASS,
						"System requirement texts displayed correctly",
						"System requirement texts displayed correctly", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"System requirement texts displayed correctly",
						"System requirement texts not displayed correctly",
						true);
			}

		} else {
			if (login.doTextMatches(sysReqDocString)) {

				Reporter.logEvent(Status.PASS,
						"System requirement texts displayed correctly",
						"System requirement texts displayed correctly", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"System requirement texts displayed correctly",
						"System requirement texts not displayed correctly",
						true);
			}
		}
	}

	@Given("^user is on the Login page of \"([^\"]*)\"$")
	public void user_is_on_the_Login_page_of(String accuCode) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(Globals.currentIteration, Globals.scenarioName,
				accuCode);
		LoginPage.setURL(accuCode);
		new LoginPage().get();
	}

	@When("^clicks on the Fund Prospectuses link on login page$")
	public void clicks_on_the_Fund_Prospectuses_link_on_login_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		new LoginPage().clickOnFooterLink("Fund Prospectuses");
		Reporter.logEvent(Status.INFO,
				"Click on Fund Prospectuses link on login page",
				"Click on Fund Prospectuses link on login page", true);

	}

	@Then("^the Fund Prospectuses content should be displayed$")
	public void the_Fund_Prospectuses_content_should_be_displayed()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		new LoginPage().displayFundProspectusContent();

	}

	@Then("^breadcrumb should display Home / Fund prospectuses$")
	public void breadcrumb_should_display_Home_Fund_prospectuses()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (new LoginPage().isFundProspectusBreadCrumbDisplayed())
			Reporter.logEvent(Status.PASS,
					"Breadcrumb Home/Fund prospectuses displays",
					"Breadcrumb Home/Fund prospectuses displays", true);
		else
			Reporter.logEvent(Status.FAIL,
					"Breadcrumb Home/Fund prospectuses displays",
					"Breadcrumb Home/Fund prospectuses does not display", true);

	}

	@Then("^the updated \"([^\"]*)\" should be displayed$")
	public void the_updated_should_be_displayed(String link) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (new FundProspectus().compareHrefValues(link))
			Reporter.logEvent(Status.PASS, "Updated URL displays",
					"Updated url contains:" + "\n"
							+ "https://prospectus-express.broadridge.com/",
					true);
		else
			Reporter.logEvent(Status.FAIL, "Updated URL displays",
					"Updated URL does not display", true);
	}

	@When("^user views browser tab$")
	public void user_views_browser_tab() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (LoginPage.getBrowserTitle().length() > 0) {
			Reporter.logEvent(Status.PASS, "Browser tab has title",
					"Browser tab has title", true);
		} else {
			Reporter.logEvent(Status.FAIL, "Browser tab has title",
					"Browser tab does not have title", true);
		}
	}

	@Then("^browser tab presents text \"([^\"]*)\"$")
	public void browser_tab_presents_text(String title) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (LoginPage.getBrowserTitle().equalsIgnoreCase(title)) {
			Reporter.logEvent(
					Status.PASS,
					"Appropriate browser title displays",
					"Appropriate browser title displays\n"
							+ LoginPage.getBrowserTitle(), true);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Appropriate browser title displays",
					"Appropriate browser title does not display", true);
		}
	}

	@Then("^is branded for MetLife with an appropriate icon$")
	public void is_branded_for_MetLife_with_an_appropriate_icon()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.logEvent(Status.INFO, "Cannot verify browser tab icon",
				"Cannot verify browser tab icon", true);
	}

	@Then("^new \"([^\"]*)\" are displayed in the footer at pre-login page$")
	public void new_are_displayed_in_the_footer_at_pre_login_page(@Delimiter(",") List<String> links)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		int count = 0;

		//String[] newLinks = links.split(",");
		for (String link : links) {
			if (login.isLinkDisplayed(link))
				count++;
		}
		if (count == links.size()) {
			Reporter.logEvent(Status.PASS, "New links display at footer",
					"New links display at footer", true);
		} else {
			Reporter.logEvent(Status.FAIL, "New links display at footer",
					"New links do not display at footer", true);
		}
	}

	@When("^user clicks on link \"([^\"]*)\"$")
	public void user_clicks_on_link(@Delimiter(",") List<String> links) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		//String[] newLinks = links.split(",");
		for (String link : links) {
			login.clickOnFooterLink(link);
			if (login.isCorrectPage(link)) {
				correctPageVisit++;
			}

			login.clickOnHomeLink();
			if (login.isPreloginPage()) {
				correctPreloginPageVisit++;
			}

		}
	}

	@Then("^user is navigated to correct page$")
	public void user_is_navigated_to_correct_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (correctPageVisit == 3) {
			Reporter.logEvent(Status.PASS,
					"User navigates to correct page after footer link click",
					"User navigates to correct page after footer link click",
					true);
			correctPageVisit = 0;
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"User navigates to correct page after footer link click",
					"User do not navigates to correct page after footer link click",
					true);
		}
	}

	@Then("^clicking Home returns to site Pre-login page$")
	public void clicking_Home_returns_to_site_Pre_login_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (correctPreloginPageVisit == 3) {
			Reporter.logEvent(Status.PASS,
					"User naviagtes to home page when clicks Home link",
					"User naviagtes to home page when clicks Home link", true);
			correctPreloginPageVisit = 0;
		} else {
			Reporter.logEvent(Status.PASS,
					"User naviagtes to home page when clicks Home link",
					"User do not naviagtes to home page when clicks Home link",
					true);
		}
	}

	@Then("^the following below text should be displayed in \"([^\"]*)\"$")
	public void the_following_below_text_should_be_displayed_in_something(
			String accuCode, String expectedText) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
		if (Globals.scenarioName.endsWith("Post-Login page")
				|| Globals.scenarioName.endsWith("Forgot password page")) {
			if (accuCode.equalsIgnoreCase("InstMetLife")) {
				if (login.textFormatMatches(expectedText,
						Web.returnWebElement(login, "footerReqText"))) {
					Reporter.logEvent(Status.PASS,
							"System requirement texts displayed correctly",
							"System requirement texts displayed correctly",
							true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"System requirement texts displayed correctly",
							"System requirement texts not displayed correctly",
							true);
				}
			}

		} else {
			if (login.textFormatMatches(expectedText,
					Web.returnWebElement(login, "footerReqText"))) {

				Reporter.logEvent(Status.PASS,
						"System requirement texts displayed correctly",
						"System requirement texts displayed correctly", true);
			} else {
				Reporter.logEvent(Status.FAIL,
						"System requirement texts displayed correctly",
						"System requirement texts not displayed correctly",
						true);
			}
		}

	}
	
	@Given("^User opens \"([^\"]*)\"$")
	public void user_opens_source_url(String sourceUrl) throws Throwable {
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		//login.get();
		Web.getDriver().get(sourceUrl);
		Reporter.logEvent(Status.INFO,"user opens the source URL",sourceUrl, true);
	}

	@Then("^\"([^\"]*)\" page should be displayed$")
	public void nextGen_url_page_should_be_displayed(String nextGenUrl) throws Throwable {
		if (login.isNextGenUrlPage(nextGenUrl)) {
			Reporter.logEvent(Status.PASS," user should navigate to: "+nextGenUrl,"user is navigated to : "+nextGenUrl, true);
		} 
		else {
			Reporter.logEvent(Status.FAIL," user should navigate to: "+nextGenUrl,"user isn't navigated to : "+nextGenUrl, true);
			
			}	    
	}

	@Then("^the \"([^\"]*)\" section should be displayed in pre login$")
	public void the_contact_us_section_should_be_displayed_in_pre_login(
			String contactus) throws Throwable {
		if (login.isContactUsSectionDisplayCorrectTextInPreLogin(contactus)) {
			Reporter.logEvent(Status.PASS,
					" The contactUs section should displays : " + contactus,
					"The contactUs section is displays : " + contactus, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					" The contactUs section should displays: " + contactus,
					"The contactUs section isn't displays : " + contactus,
					true);
		}
	}

	@When("^clicks on the FAQ link on login page$")
	public void clicks_on_the_faq_link_on_login_page() throws Throwable {
		login.clickOnFAQLink();
	}

	@Then("^the FAQ content should be displayed$")
	public void the_faq_content_should_be_displayed() throws Throwable {
		if(login.isFAQpageDisplayes()){
			Reporter.logEvent(Status.PASS,
					" the FAQ content should displays",
					"the FAQ content displays", true);
		} else {
			Reporter.logEvent(Status.FAIL,
					" the FAQ content should displays",
					"the FAQ content isn't displays",
					true);
		}
			

	}

	@Then("^the following text should be displayed in FAQ content$")
	public void the_following_text_should_be_displayed_in_faq_content(
			String text) throws Throwable {
		if(login.isSameTextDisplaysInFAQ(text)){
			Reporter.logEvent(Status.PASS,
					" the following text should displays: "+text,
					"the text is displays: "+text, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					" the following text should displays: "+text,
					"the text isn't displays: "+text,
					true);
		}
	}

	@Then("^the PreLogin Banner should be displayed$")
	public void the_prelogin_banner_should_be_displayed() throws Throwable {
		if (login.isBannerSectionDisplays()) {
			Reporter.logEvent(Status.PASS, "PreLogin Banner section displays",
					"PreLogin Banner section displays", true);
		} else {
			Reporter.logEvent(Status.FAIL, "PreLogin Banner section displays",
					"PreLogin Banner section isn't displays", true);
		}
	}
	
	@Then("^the following text should be displayed in Banner Section$")
    public void the_following_text_should_be_displayed_in_banner_section(String text) throws Throwable {
		if(login.isSameTextDisplaysInBannerSection(text)){
			Reporter.logEvent(Status.PASS,
					" the following text  displays in banner section: "+text,
					"the text is displays: "+text, true);
		} else {
			Reporter.logEvent(Status.FAIL,
					" the following text  displays in banner section: "+text,
					"the text isn't displays: "+text,
					true);
		}
    }

	 @Then("^the PreLogin Content Tiles pictures should be displayed$")
	    public void the_prelogin_content_tiles_pictures_should_be_displayed() throws Throwable {
		 if (login.isPreLoginTilesPictureDisplays()) {
				Reporter.logEvent(Status.PASS, "PreLogin Content Tiles pictures displays",
						"PreLogin Content Tiles pictures displays", true);
			} else {
				Reporter.logEvent(Status.FAIL, "PreLogin Content Tiles pictures displays",
						"PreLogin Content Tiles pictures isn't displays", true);
			}
	    }
	 
	 @Then("^the \"([^\"]*)\" should be displayed with each \"([^\"]*)\" for that text$")
	    public void the_expected_text_should_be_displayed_with_each_link_to_for_that_text(String followingtext, String linkto) throws Throwable {
		 if (login.verifyingPreLoginContentTilesPicturesTextAndLink(followingtext, linkto)){
				Reporter.logEvent(Status.PASS, "PreLogin Content Tiles text displays with link",
						"PreLogin Content Tiles text displays with link", true);
			} else {
				Reporter.logEvent(Status.FAIL, "PreLogin Content Tiles text displays with link",
						"PreLogin Content Tiles text isn't displays with link", true);
			}
	    }
	 
	 @When("^user clicks on Pre-login banner$")
	    public void user_clicks_on_prelogin_banner() throws Throwable {
	       login.clickOnBannerSectionReadMoreLink();
	    }
	 
	 @Then("^a page like this link is displayed \"([^\"]*)\" and is branded for MetLife$")
	    public void a_page_like_this_link_is_displayed_and_is_branded_for_metlife(String expLink) throws Throwable {
		 if (login.verifyMetLifeLogoAndLink(expLink)) {
				Reporter.logEvent(Status.PASS, "navigated to "+expLink+" and can't verify metLife logo ",
						"navigated to "+expLink+" and metLife logo displays", true);
			} else {
				Reporter.logEvent(Status.FAIL, "navigated to "+expLink+" and metLife logo displays",
						"navigated to "+expLink+" and can't verify metLife logo ", true);
			} 
	    }
	 
	 @When("^MetLife logo is displayed$")
	    public void metlife_logo_is_displayed() throws Throwable {
	        if(!login.ismetLifeLogoDisplays())
	        	login.get();
	    }
	/* @Then("^the logo is as approved by client, has Max-width of 300 pixels, Max-height of 70 pixels$")
	    public void the_logo_is_as_approved_by_client_has_maxwidth_of_300_pixels_maxheight_of_70_pixels() throws Throwable {
	       login.ismetLifeLogoDisplays();
	    }*/
	
	 @Given("^is a first time login to the site$")
	    public void is_a_first_time_login_to_the_site() throws Throwable {
		 Reporter.initializeReportForTC(Globals.currentIteration,
					Globals.scenarioName);
	        login.clearCookies();
		 
	    }
	 
	
	 
	 
	 
	 
	 
	 
}
