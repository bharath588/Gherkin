package stepDefination;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gherkin.formatter.model.Scenario;
import lib.CommonLib;
import lib.Stock;
import lib.Web;
import pageobject.pptweb.LandingPage;
import pageobject.pptweb.LeftNavigationBar;
import pageobject.pptweb.LoginPage;
import pageobject.pptweb.ManageMyInvestment;
import pageobject.pptweb.TwoStepVerification;
import reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ManageMyInvestments {
	
	public static String featureName=null;
	public static String scenarioName=null;
	public static Scenario scenario=null;
	public Map<String, String> mapInvestmentOptionsReviewPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsConfirmPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptions = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsRebalance = new HashMap<String, String>();
	public List<String> ConfirmationNos = new ArrayList<String>();
	LoginPage login;
	TwoStepVerification mfaPage;
	LandingPage homePage;
	LeftNavigationBar leftNav;
	ManageMyInvestment investments;
	@Before()
	public void before(cucumber.api.Scenario scenario) {
		
			featureName=scenario.getId().split(";")[0];
			scenarioName=scenario.getName();
			System.out.println(scenario.getId());
			Reporter.initializeModule(featureName);
		}
	
	
	@Given("^Launch PPTWEB application$")
	public void launch_PPTWEB_application() throws Throwable {
		Reporter.initializeReportForTC(1,scenarioName);
		login = new LoginPage();
		
	   
	}

	@When("^I enter valid username and password$")
	public void i_enter_username_and_password() throws Throwable {
	   mfaPage =new TwoStepVerification();
	  
	}

	@And("^click on Sign On button$")
	public void click_on_Sign_On_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	   
	}

	

	@And("^I Enter Verification Code$")
	public void i_enter_Verification_Code() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	   
	}

	

	@Then("^I should be naviagted to Home page$")
	public void i_should_be_naviagted_to_Home_page() throws Throwable {
	  login=new LoginPage();
	  mfaPage =new TwoStepVerification(login);
	  homePage= new LandingPage(mfaPage);
	  homePage.get();
	
	   
	}

	@Given("^I am on Home Page$")
	public void i_am_on_Home_Page() throws Throwable {
		Reporter.initializeReportForTC(1,scenarioName);
		 login=new LoginPage();
		  mfaPage =new TwoStepVerification(login);
		  homePage= new LandingPage(mfaPage);
		  homePage.get();
	   
	}

	@When("^I click on MyAccount Tab$")
	public void i_click_on_MyAccount_Tab() throws Throwable {
		login=new LoginPage();
		  mfaPage =new TwoStepVerification(login);
		  homePage= new LandingPage(mfaPage);
		  leftNav=new LeftNavigationBar(homePage);
		  leftNav.get();
		 
	    
	}
	
	@When("^I click on View/Managemyinvestments link$")
	public void i_click_on_View_Managemyinvestments_link() throws Throwable {
			login=new LoginPage();
		  mfaPage =new TwoStepVerification(login);
		  homePage= new LandingPage(mfaPage);
		  leftNav=new LeftNavigationBar(homePage);
		  investments= new ManageMyInvestment(leftNav);
		  investments.get();
	   
	}

	@Then("^Application navigates to My Investments Page$")
	public void application_navigates_to_My_Investments_Page() throws Throwable {
		  investments= new ManageMyInvestment();
	   investments.verifyPageHeaderIsDisplayed("Header My Investments");
	   
	}

	@Given("^I am on My Investments page$")
	public void i_am_on_My_Investments_page() throws Throwable {
		Reporter.initializeReportForTC(1,scenarioName);
		investments= new ManageMyInvestment();
	   
	}

	@When("^I click on ChangeMyInvestments link$")
	public void i_click_on_ChangeMyInvestments_link() throws Throwable {
		 investments= new ManageMyInvestment();
		 investments.clickChangeMyInvestmentButton();
	    
	}

	@Then("^I select Change how my future contributions will be invested Option$")
	public void i_select_Change_how_my_future_contributions_will_be_invested_Option() throws Throwable {
		 investments= new ManageMyInvestment();
		 investments.choseInvestmentOption("Change Future Contribution");
			Web.clickOnElement(investments, "Continue Button");
			CommonLib.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(investments, "Header How Would You Like To Invest");
			investments.verifyPageHeaderIsDisplayed("Header How Would You Like To Invest");
	    
	}

	
	@When("^I select Choose Individual Funds$")
	public void i_select_Choose_Individual_Funds() throws Throwable {
		 investments= new ManageMyInvestment();
		Web.clickOnElement(investments,"Choose Individual Funds");
		Web.waitForElement(investments, "Header Build Your Own Portfolio");
		investments.verifyPageHeaderIsDisplayed("Header Build Your Own Portfolio");
		investments.verifyWebElementDisplayed("Link Add/View All Funds");
		
		Web.clickOnElement(investments, "Link Add/View All Funds");
		
		Web.waitForPageToLoad(Web.getDriver());
		
		if (Web.isWebElementDisplayed(investments,"Table Select Funds")) {
			Reporter.logEvent(Status.PASS,
					"Verify Investment Allocation table is displayed",
					"Investment Allocation table is displayed ", true);
		}
		else{
			Reporter.logEvent(Status.FAIL,
					"Verify Investment Allocation table is displayed",
					"Investment Allocation table is not displayed ", true);
		}
	
		String[] percentage={"50","50"};
		mapInvestmentOptions=investments.addInvestments(2,percentage);
			investments.verifyPageHeaderIsDisplayed("Header Review Your Changes");
	
		 mapInvestmentOptionsReviewPage=investments.getCurrentFunds();
		
		
	   
	}

	@When("^Submit the Changes$")
	public void submit_the_Changes() throws Throwable {
		 investments= new ManageMyInvestment();
		Web.clickOnElement(investments, "Button Confirm");
		CommonLib.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Thread.sleep(25000);
		
		
	
	   
	}
	
	
	@Then("^I Navigate to Confirmation Page$")
	public void i_Navigate_to_Confirmation_Page() throws Throwable {
		investments= new ManageMyInvestment();
		investments.verifyPageHeaderIsDisplayed("Header Confirmation");
	    
	}

	@And("^I can Verify the Investment Submittion Time and Cofirmation No$")
	public void VerifyCofirmationPage() throws Throwable {
		investments.verifyConfirmationMessageForChangeFutureFlow();
		String confirmationNumber=investments.getConfirmationNoChangeFutureFlow();
		investments.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromEventTable", Stock.getConfigParam("PPTWEBusername"), confirmationNumber, "Event");
		investments.verifyConfirmationNoUpdatedInDB("getConfirmationNoFromStepTable", Stock.getConfigParam("PPTWEBusername"), confirmationNumber, "Step");
		investments.verifyConfirmationNoUpdatedInDB("getConfirmationNoFrominvopt_allocTable", Stock.getConfigParam("PPTWEBusername"), confirmationNumber, "Invopt_alloc");
	    
	}

	
	

	@Then("^I can verify the updated Investment Options in the Future Allocation Section$")
	public void i_can_verify_the_updated_Investment_Options_in_the_Future_Allocation_Section() throws Throwable {
	  Reporter.logEvent(Status.INFO, "Verify Future Investments", "Verifying Future Investment section is Updated with new Funds", true);
	  
	}
	

@When("^I select Target Date Funds$")
public void i_select_Target_Date_Funds() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
   
}


}
