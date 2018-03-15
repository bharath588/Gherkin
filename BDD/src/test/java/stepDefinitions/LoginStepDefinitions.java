/**
 * 
 */
package stepDefinitions;

import java.util.List;
import java.util.Map;





import lib.Web;





import com.aventstack.extentreports.Status;

import core.framework.Globals;
import pageobjects.psc.ForgotPasswordPage;
import pageobjects.psc.FundProspectus;
import pageobjects.psc.HomePage;
import pageobjects.psc.JumpPage;
import pageobjects.psc.LoginPage;
import pageobjects.psc.UserVerificationPage;
import reporter.Reporter;
import cucumber.api.DataTable;
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
	public static String featureName=null;
	public static Scenario scenario=null;
	public static Integer currentIteration = null;

	LoginPage login;
	UserVerificationPage userVerPage;
	JumpPage jumpPage;
	HomePage homePage;
	ForgotPasswordPage forgotPwdPage;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try{
		featureName=scenario.getId().split(";")[0];
		Globals.scenarioName=scenario.getName();
		Reporter.initializeModule(featureName);
		LoginStepDefinitions.scenario =scenario;
		System.out.println(scenario.getId());
		currentIteration=Integer.valueOf(scenario.getId().split(";")[3])-1;
		}
		catch(Exception e){
			currentIteration=1;
		}
	}

	@After
	public void after() throws Exception{
		Reporter.finalizeTCReport();
	}



	@Given("^Open browser and launch PSC application$")	
	public void openBrowserAndLaunchPSCApp(){
		try{
			Reporter.initializeReportForTC(1,Globals.scenarioName);
			//testWeb.getDriver().manage().deleteAllCookies();
			login = new LoginPage(Web.getDriver());
			login.get();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@When("^I enter valid username and password$")
	public void enterLoginCredentials(DataTable usercredentials){

		System.out.println("Enter UserName and Password");	  
		List<Map<String, String>> data = usercredentials.asMaps(String.class,
				String.class);
		login = new LoginPage(Web.getDriver());
		login.submitLoginCredentials(data.get(0).get("username"), data.get(0)
				.get("password"));
		/*HomePage homePage = new HomePage(Web.getDriver());
	    homePage.get();*/

	}

	@And("^click on Sign On button$")
	public void clickOnSignOn(){
		Reporter.logEvent(Status.PASS, "Verify User is Logged in", 
				"user is logged in", false);
	}

	@Then("^I should be navigated to user verification page$")
	public void navigationToUserVerificationPage(){
		userVerPage = new UserVerificationPage(Web.getDriver());
		userVerPage.get();
	}

	@When("^I enter email address in email box$")
	public void enterEmailOnUserVerificationPage(DataTable userVerData){
		Reporter.logEvent(Status.PASS, "Enter email on user verification page",
				"Enter email on user verification page",false);
		List<String> emailIds =  userVerData.asList(String.class);
		userVerPage = new UserVerificationPage(Web.getDriver());
		userVerPage.performVerification(emailIds.get(1));
	}

	@And("^I enter security question answer$")
	public void enterSecurityQuestionAnswer(DataTable secAnswer){
		Reporter.logEvent(Status.PASS, "Enter security answer on user verification page",
				"Enter security answer on user verification page",false);
	}

	@And("^I click on Next button$")
	public void clickOnNextButton(){
		Reporter.logEvent(Status.PASS, "click on Next button on user verification page",
				"click on Next button on user verification page",false);
	}

	@Then("^I should be naviagted to jump page$")
	public void jumpPageLoad(){
		jumpPage = new JumpPage(Web.getDriver());
		jumpPage.get();
		Reporter.logEvent(Status.PASS, "jump page is loaded",
				"jump page is loaded",false);
	}

	@And("^I click on nextGen page link$")
	public void clickOnNextGenLink(){
		jumpPage = new JumpPage(Web.getDriver());
		jumpPage.clickOnJumpPageLink();
		Reporter.logEvent(Status.PASS, "click on Next Gen Link on jump page",
				"click on Next Gen Link on jump page",false);
	}

	@Then("^I should be navigated to home Page$")
	public void navigateToHomePage(){
		homePage = new HomePage(Web.getDriver());
		homePage.get();
		Reporter.logEvent(Status.PASS, "User is on home page",
				"User is on home page",false);
	}

	@Given("^I am on user verification page$")
	public void i_am_on_user_Verification_page() throws Throwable{
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		Reporter.logEvent(Status.INFO, "I am on user verification page", 
				"I am on user verification page", false);
	}

	@Given("^I am on jump page$")
	public void i_am_on_jump_page() throws Throwable{
		Reporter.initializeReportForTC(1, Globals.scenarioName);
		Reporter.logEvent(Status.INFO, "I am on jump page", "I am on jump page", false);
	}

	@Given("^User successfully navigated to \"([^\"]*)\"$")
	public void user_successfully_navigated_to(String arg1) throws Throwable{
		Reporter.initializeReportForTC(1,Globals.scenarioName);
		login = new LoginPage();
		LoginPage.setURL(arg1);
		login.get();

	}
	@When("^user enters \"([^\"]*)\" and \"([^\"]*)\" and clicks on sign in button$")
	public void user_enters_and_and_clicks_on_sign_in_button(String username, String password) throws Throwable {
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
				"User is on home page",false);
	}

	@When("^I enter valid username and invalid password$")
	public void i_enter_valid_username_and_invalid_password(DataTable usercredentials) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		List<Map<String, String>> data = usercredentials.asMaps(String.class,
				String.class);
		login = new LoginPage(Web.getDriver());
		login.submitLoginCredentials(data.get(0).get("username"), data.get(0)
				.get("password"));

	}

	@Then("^Error message is displayed$")
	public void error_message_is_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage(Web.getDriver());
		if(login.isErrorDisplayed()){
			Reporter.logEvent(Status.PASS, "Error message displays for invalid username or password", 
					"Error message displays for invalid username or password", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Error message displays for invalid username or password", 
					"Error message does not display for invalid username or password", true);
		}
	}

	@When("^I enter invalid username and invalid password$")
	public void i_enter_invalid_credentials(DataTable usercredentials) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		List<Map<String, String>> data = usercredentials.asMaps(String.class,
				String.class);
		login = new LoginPage(Web.getDriver());
		login.submitLoginCredentials(data.get(0).get("username"), data.get(0)
				.get("password"));
	}

	@Given("^user is on the Login page of accuCode$")
	public void user_is_on_the_Login_page_of_accuCode(DataTable accuCode) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		Reporter.initializeReportForTC(1,Globals.scenarioName);
		List<String> accu = accuCode.asList(String.class);
		login = new LoginPage();
		login.setURL(accu.get(1));
		//System.out.println(accu.get(1));
		login.get();
	}

	@When("^user clicks on Terms and Conditions link displayed in the footer$")
	public void user_clicks_on_Terms_and_Conditions_link_displayed_in_the_footer() throws Throwable {
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
		if(login.tNCOpensInNewTab()){
			Reporter.logEvent(Status.PASS, "Terms and Conditions link opens in new tab",
					"Terms and Conditions link opens in new tab", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Terms and Conditions link opens in new tab",
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
	@Then("^the Terms and Conditions link should not be displayed in the footer system tray$")
	public void the_Terms_and_Conditions_link_should_not_be_displayed_in_the_footer_system_tray() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		if(login.isTnCDisplayedInSysTray()){
			Reporter.logEvent(Status.PASS, "Terms and conditions link does not display in system tray", 
					"Terms and conditions link does not display in system tray", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "Terms and conditions link does not display in system tray",
					"Terms and conditions link displays in system tray", true);
		}
	}
	@When("^user enters username and password$")
	public void user_enters_username_and_password(DataTable creds) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		login = new LoginPage();
		login.get();
		List<Map<String,String>> credentials = creds.asMaps(String.class, String.class);
		login.submitLoginCredentials(credentials.get(0).get("username"), credentials.get(0)
				.get("password"));
	}

	@When("^user clicks on Sign In button$")
	public void user_clicks_on_Sign_In_button() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.logEvent(Status.INFO, "User clicks on Sign In button", "User clicks on Sign In button", true);
	}

	@When("^clicks on the System Requirement link on login page$")
	public void clicks_on_the_System_Requirement_link_on_login_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		login.clickOnFooterLink("System Requirements");
		Reporter.logEvent(Status.INFO, "Click on System Requirements link on login page", "Click on System Requirements link on login page", true);

	}

	@Then("^the System Requirements content should be displayed$")
	public void the_System_Requirements_content_should_be_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		if(login.isSysReqDisplayed()){
			Reporter.logEvent(Status.PASS, "System requirement contents displayed", 
					"System requirement contents displayed", true);
		}
		else{
			Reporter.logEvent(Status.FAIL, "System requirement contents displayed", 
					"System requirement contents not displayed", true);
		}

	}

	@Then("^the following text should be displayed$")
	public void the_following_text_should_be_displayed(String sysReqDocString) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		login = new LoginPage();
		homePage = new HomePage();
		forgotPwdPage = new ForgotPasswordPage();
		if(Globals.scenarioName.endsWith("Post-Login page")||Globals.scenarioName.endsWith("Forgot password page")){
			if(login.doTextMatches(sysReqDocString,  
					Web.returnWebElement(forgotPwdPage, "Footer iFrame")))
			{
				Reporter.logEvent(Status.PASS, "System requirement texts displayed correctly", 
						"System requirement texts displayed correctly", true);
			}
			else{
				Reporter.logEvent(Status.FAIL, "System requirement texts displayed correctly", 
						"System requirement texts not displayed correctly", true);
			}

		}
		else{
			if(login.doTextMatches(sysReqDocString)){

				Reporter.logEvent(Status.PASS, "System requirement texts displayed correctly", 
						"System requirement texts displayed correctly", true);
			}
			else{
				Reporter.logEvent(Status.FAIL, "System requirement texts displayed correctly", 
						"System requirement texts not displayed correctly", true);
			}
		}
	}

	@Given("^user is on the Login page of \"([^\"]*)\"$")
	public void user_is_on_the_Login_page_of(String accuCode) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(currentIteration,
				Globals.scenarioName,accuCode);
		LoginPage.setURL(accuCode);
		new LoginPage().get();
	}

	@When("^clicks on the Fund Prospectuses link on login page$")
	public void clicks_on_the_Fund_Prospectuses_link_on_login_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		new LoginPage().clickOnFooterLink("Fund Prospectuses");
		Reporter.logEvent(Status.INFO, "Click on Fund Prospectuses link on login page", 
				"Click on Fund Prospectuses link on login page", true);

	}

	@Then("^the Fund Prospectuses content should be displayed$")
	public void the_Fund_Prospectuses_content_should_be_displayed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		new LoginPage().displayFundProspectusContent();

	}

	@Then("^breadcrumb should display Home / Fund prospectuses$")
	public void breadcrumb_should_display_Home_Fund_prospectuses() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if(new LoginPage().isFundProspectusBreadCrumbDisplayed())
			Reporter.logEvent(Status.PASS, "Breadcrumb Home/Fund prospectuses displays", 
					"Breadcrumb Home/Fund prospectuses displays", true);
		else
			Reporter.logEvent(Status.FAIL, "Breadcrumb Home/Fund prospectuses displays", 
					"Breadcrumb Home/Fund prospectuses does not display", true);

	}

	@Then("^the updated \"([^\"]*)\" should be displayed$")
	public void the_updated_should_be_displayed(String link) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if(new FundProspectus().compareHrefValues(link))
			Reporter.logEvent(Status.PASS, "Updated URL displays", 
					"Updated url contains:"+"\n" +"https://prospectus-express.broadridge.com/", 
					true);
		else
			Reporter.logEvent(Status.FAIL, "Updated URL displays", 
					"Updated URL does not display", true);
	}
	
	@When("^user views browser tab$")
	public void user_views_browser_tab() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    if(LoginPage.getBrowserTitle().length()>0){
	    	Reporter.logEvent(Status.PASS, "Browser tab has title", "Browser tab has title", true);
	    }
	    else{
	    	Reporter.logEvent(Status.FAIL, "Browser tab has title", "Browser tab does not have title", true);
	    }
	}
	
	@Then("^browser tab presents text \"([^\"]*)\"$")
	public void browser_tab_presents_text(String title) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    if(LoginPage.getBrowserTitle().equalsIgnoreCase(title)){
	    	Reporter.logEvent(Status.PASS, "Appropriate browser title displays", 
	    			"Appropriate browser title displays\n"+LoginPage.getBrowserTitle(), 
	    			true);
	    }
	    else{
	    	Reporter.logEvent(Status.FAIL, "Appropriate browser title displays", 
	    			"Appropriate browser title does not display", 
	    			true);
	    }
	}
	
	@Then("^is branded for MetLife with an appropriate icon$")
	public void is_branded_for_MetLife_with_an_appropriate_icon() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    Reporter.logEvent(Status.INFO, "Cannot verify browser tab icon", 
	    		"Cannot verify browser tab icon", true);
	}

}
