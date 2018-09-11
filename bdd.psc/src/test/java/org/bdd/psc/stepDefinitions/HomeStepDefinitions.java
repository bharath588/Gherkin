/**
 * 
 */
package org.bdd.psc.stepDefinitions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;





import pscBDD.fileSharing.FileSharingPage;
import pscBDD.forgotPassword.ForgotPasswordPage;
import pscBDD.homePage.HomePage;
import pscBDD.jumpPage.JumpPage;
import pscBDD.login.LoginPage;
import pscBDD.partnerlinkHomePage.PartnerlinkHomePage;
import pscBDD.planPage.LoanInformationPage;
import pscBDD.planPage.PlanDMBACustomSitePage;
import pscBDD.planPage.PlanPage;
import pscBDD.userVerification.UserVerificationPage;
import bdd_lib.CommonLib;
import bdd_lib.Web;

import com.aventstack.extentreports.Status;

import bdd_core.framework.Globals;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import bdd_reporter.Reporter;
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
	PlanPage planPage;
	PartnerlinkHomePage partnerHomePage;
	
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
			planPage=new PlanPage();
			partnerHomePage= new PartnerlinkHomePage();
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(
					";")[scenario.getId().split(";").length - 1]) - 1;
			System.out.println("Current iteration is: "+Globals.currentIteration);
			Reporter.initializeModule(featureName);
		} catch (Exception e) {
			Globals.currentIteration = 1;
		}

	}

	@After
	public void after(cucumber.api.Scenario scenario) throws Exception {
		Reporter.logAfterExceptionEvent(scenario);			
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
	
	@When("^user switch to plan number$")
	public void user_switches_to_Plan(DataTable planNumber) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Globals.gaId = planNumber;
		String planNo=Web.rawValues(Globals.gaId).get(0).get("plan_no");
		homePage = new HomePage();
		homePage.switchPlan(planNo);
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
	
	@When("^User clicks on the YouTube social media icon$")
	public void user_clicks_on_the_YouTube_social_media_icon() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^A new tab opens with https://www\\.youtube\\.com/channel/UCFPLlGp(\\d+)vPjBb-G(\\d+)SnUWhQ$")
	public void a_new_tab_opens_with_https_www_youtube_com_channel_UCFPLlGp_vPjBb_G_SnUWhQ(
			int arg1, int arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^User clicks on the Instagram social media icon$")
	public void user_clicks_on_the_Instagram_social_media_icon()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^A new tab opens with https://www\\.instagram\\.com/empowerretirement/$")
	public void a_new_tab_opens_with_https_www_instagram_com_empowerretirement()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
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
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
		Date date = new Date();
		Globals.localDateTimeIST= dateFormat.format(date);
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
	
	@When("^user changes to \"([^\"]*)\" plan$")
    public void user_changes_to_given_plan_number(String nonpartgroupPlanNo) throws Throwable {
		homePage.switchPlan(nonpartgroupPlanNo);
    }
	 @Then("^\"([^\"]*)\" plan home page is displayed$")
	    public void given_plan_home_page_is_displayed(String planNo) throws Throwable {
		 if(homePage.getPlanNumberFromPageHeader().equals(planNo.trim())){
				Reporter.logEvent(Status.PASS, planNo+" plan home page displays",planNo+" plan home page displays", true);
			}else{		
				Reporter.logEvent(Status.FAIL, planNo+" plan home page displays", planNo+" plan home page isn't displays", true);
			}
	    }
	 
	 
	@When("^user selects Plan /Contacts menu$")
	public void user_selects_plan_contacts_menu() throws Throwable {
		planPage.clickOnContactsUnderPlanTab();
	}

	@Then("^No \"([^\"]*)\" menu item is displayed$")
	public void no_Empower_Contacts_menu_item_is_displayed(String menuOption)
			throws Throwable {
		if (planPage.isEmpowerContactsMenu(menuOption)) {
			Reporter.logEvent(
					Status.PASS,
					"Empower Contacts menu option not displayed for Plan/Contacts menu",
					"Empower Contacts menu option not displayed for Plan/Contacts menu",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Empower Contacts menu option not displayed for Plan/Contacts menu",
					"Empower Contacts menu option displayed for Plan/Contacts menu",
					true);
		}

	}

	@Then("^the Partnerlink landing page is not branded as Empower$")
    public void the_partnerlink_landing_page_is_not_branded_as_empower() throws Throwable {
       if(partnerHomePage.isMetLifeLogo()){
			Reporter.logEvent(
					Status.PASS,
					"PartnerLink landing page is not branded with Empower",
					"PartnerLink landing page is not branded with Empower",
					true);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"PartnerLink landing page is not branded with Empower",
					"PartnerLink landing page is branded with Empower",
					true);
		}

    }
	
	
	@Then("^three security questions are saved and flow proceeds to post-login page$")
    public void three_security_questions_are_saved_and_flow_proceeds_to_postlogin_page() throws Throwable {
		if (homePage.isHomePage()){
			Reporter.logEvent(
					Status.PASS,
					"Verify User proceeds to Homepage after providing security questions",
					"HomePage displayed",
					false);
		} else {
			Reporter.logEvent(
					Status.FAIL,
					"Verify User proceeds to Homepage after providing security questions",
					"HomePage not displayed",
					true);
		}	
    }
	
	 @Given("^user has logged out of their account$")
	    public void user_has_logged_out_of_their_account() throws Throwable {
	        homePage.clickLogout();
	        Reporter.logEvent(
					Status.INFO,
					"User logged out successfully",
					"User logged out successfully",
					false);
	    }

	 @When("^user clicks 'More' on the Plan Analytics dashboard.$")
	    public void user_clicks_more_on_the_plan_analytics_dashboard() throws Throwable {		
	      planPage.clickOnMOre();		 
	    }
	 
	 

	    @Then("^the MAX Enrollment High Chart will be visible.$")
	    public void the_max_enrollment_high_chart_will_be_visible() throws Throwable {
	    	if (planPage.isMAXEnrollmentHighChart()){
				Reporter.logEvent(
						Status.PASS,
						"MAX Enrollment High Chart displayed",
						"MAX Enrollment High Chart displayed",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"MAX Enrollment High Chart not displayed",
						"MAX Enrollment High Chart not displayed",
						true);
			} 
	    }
	   
	    @Then("^the MAX Enrollment High Chart will not be visible.$")
	    public void the_max_enrollment_high_chart_will_not_be_visible() throws Throwable {	    	
	    	if (planPage.verifyHighChart()){
				Reporter.logEvent(
						Status.PASS,
						"MAX Enrollment High Chart not displayed",
						"MAX Enrollment High Chart not  displayed",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"MAX Enrollment High Chart displayed",
						"MAX Enrollment High Chart displayed",
						true);
			} 
	    }

	  	   
	   
	    @Given ("^User search and selects \"([^\"]*)\" offers MAX$")
	    public void user_search_and_selects_something_offers_max(String plan) throws Throwable {
	    	 homePage.switchPlan(plan);
	    }
	    
	   
	    @Given("^User search and selects \"([^\"]*)\" does not offers MAX.$")
	    public void user_search_and_selects_something_does_not_offers_max(String plan) throws Throwable {
	    	 homePage.switchPlan(plan);
	    }
	    

	    @Then("^the Last login field should display the previous login time and in UI date and time should be displayed in Eastern time and LAST_LOGIN_DATE_TIME in USERS table should be saved in Mountain time across all databases with (.+) and (.+) the User has access to$")
	    public void the_last_login_field_should_display_the_previous_login_time_and_in_ui_date_and_time_should_be_displayed_in_eastern_time_and_lastlogindatetime_in_users_table_should_be_saved_in_mountain_time_across_all_databases_with_and_the_user_has_access_to(@Delimiter(",") List<String> DBname,String username,String query) throws Throwable {
	    	if (homePage.verifyLastLoginDateEquality(DBname,username,query)) {
	    		Reporter.logEvent(
						Status.PASS,
						"Validate last login date and time from application and database ",
						"Time displayed on application home page is equal to time stored in database",
						false);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Validate last login date and time values from application and database ",
						"Time displayed on application home page is not equal to time stored in database",
						true);
				  }
	    }
	    
	    @When("^user is on the Home page of \"([^\"]*)\" when user again login with correct \"([^\"]*)\" and \"([^\"]*)\"$")
	    public void user_is_on_the_home_page_of_something_when_user_again_login_with_correct_something_and_something(String accuCode, String username, String password) throws Throwable {    	
	    	List<List<String>> accuWithCreds = Arrays.asList(
					Arrays.asList("accuCode", "username", "password"),
					Arrays.asList(accuCode, username, password));
			DataTable dataTable = DataTable.create(accuWithCreds);
			Globals.creds = dataTable;
			LoginPage.setURL(accuCode);
			new LoginPage().get();
			new HomePage(new LoginPage(), false,
					new String[] { username, password }).get(); 
			Reporter.logEvent(
					Status.INFO,
					"User opens URl and again logged into PlanEmpower",
					"User is on Homepage",
					true);
	    }
	    
	    
}
