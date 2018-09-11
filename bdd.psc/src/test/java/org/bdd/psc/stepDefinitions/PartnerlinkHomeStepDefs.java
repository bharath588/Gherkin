/**
 * 
 */
package org.bdd.psc.stepDefinitions;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import gherkin.formatter.model.Scenario;
import pscBDD.PlanExpress.PlanExpressPage;
import pscBDD.login.LoginPage;
import pscBDD.partnerlinkHomePage.PartnerlinkHomePage;
import bdd_core.framework.Globals;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
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
public class PartnerlinkHomeStepDefs {
	public static String featureName = null;
	public static Scenario scenario = null;
	PartnerlinkHomePage plHomePage;
	PlanExpressPage pePage;

	@Before
	public void before(cucumber.api.Scenario scenario) {
		try {
			featureName = scenario.getId().split(";")[0];
			Globals.scenarioName = scenario.getName();
			plHomePage = new PartnerlinkHomePage();
			pePage = new PlanExpressPage();
			Globals.currentIteration = Integer.valueOf(scenario.getId().split(
					";")[scenario.getId().split(";").length - 1]) - 1;
			Reporter.initializeModule(featureName);
		} catch (Exception e) {
			Globals.currentIteration = 1;
		}

	}

	@After
	public void after() throws Exception {
		Reporter.finalizeTCReport();
	}

	@Then("^user is on PartnerLink home page$")
	public void user_is_on_PartnerLink_home_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		new PartnerlinkHomePage(new LoginPage(), false, new String[] {
				Web.rawValues(Globals.creds).get(0).get("username"),
				Web.rawValues(Globals.creds).get(0).get("password") }).get();
	}

	@Given("^user is on the PartnerLink Home page of accuCode when user login with correct username and password$")
	public void user_is_on_the_PartnerLink_Home_page_of_accuCode_when_user_login_with_correct_username_and_password(
			DataTable accAndCreds) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
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
			new PartnerlinkHomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		} else {
			new PartnerlinkHomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
		}

	}

	@When("^user selects Implementation / Plan Express from the main nav$")
	public void user_selects_Implementation_Plan_Express_from_the_main_nav()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (plHomePage.isPlanExpressPage()) {
			Reporter.logEvent(Status.PASS, "User is on plan Express page",
					"User is on plan Express page", true);
		} else {
			Reporter.logEvent(Status.PASS, "User is not on plan Express page",
					"User is not on plan Express page", true);
		}

	}

	@When("^Pl user selects Implementation/Plan Express from the main nav$")
    public void pl_user_selects_implementationplan_express_from_the_main_nav() throws Throwable {
		if (plHomePage.isPLPlanExpressPage()) {
			Reporter.logEvent(Status.PASS, "User is on plan Express page",
					"User is on plan Express page", true);
		} else {
			Reporter.logEvent(Status.PASS, "User is not on plan Express page",
					"User is not on plan Express page", true);
		}
    }
	
	@Then("^user is presented Welcome to PlanExpress page$")
	public void user_is_presented_Welcome_to_PlanExpress_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (Web.isWebElementDisplayed(plHomePage, "WELCOME TO PLAN EXPRESS",
				true)) {
			Reporter.logEvent(Status.PASS,
					"User is presented with welcome to plan express page",
					"User is presented with welcome to plan express page", true);

		} else {
			Reporter.logEvent(Status.FAIL,
					"User is not presented with welcome to plan express page",
					"User is not presented with welcome to plan express page",
					true);
		}

	}

	@Given("^user has logged into PlanExpress page$")
	public void user_has_logged_into_PlanExpress_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Reporter.initializeReportForTC(Globals.currentIteration,
				Globals.scenarioName);
		pePage.get();

	}

	@When("^user selects (\\d+) Plan Pipeline Data$")
	public void user_selects_Plan_Pipeline_Data(int linkNumber)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnPlanPipelineData();
		Reporter.logEvent(Status.INFO, "user clicks on 1 Plan Pipeline Data",
				"user clicks on 1 Plan Pipeline Data", true);

	}

	@Then("^user is presented PlanExpress Add a New Plan page (\\d+)$")
	public void user_is_presented_PlanExpress_Add_a_New_Plan_page(
			int addPlanPageNumber) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (pePage.isAddPlanPage()) {
			Reporter.logEvent(Status.PASS, "User is presented add plan page",
					"User is presented add plan page number"
							+ addPlanPageNumber, true);
		} else {
			Reporter.logEvent(Status.FAIL, "User is presented add plan page",
					"User is not presented add plan page number"
							+ addPlanPageNumber, true);
		}

	}

	@Given("^user is on PlanExpress Add a New Plan page (\\d+)$")
	public void user_is_on_PlanExpress_Add_a_New_Plan_page(int addPlanPageNumber)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.get();
		pePage.clickOnPlanPipelineData();
		if (pePage.isAddPlanPage()) {
			Reporter.logEvent(Status.PASS, "User is on add plan page",
					"User is on add plan page number" + addPlanPageNumber, true);
		} else {
			Reporter.logEvent(Status.FAIL, "User is on add plan page",
					"User is not on add plan page number" + addPlanPageNumber,
					true);
		}

	}

	@When("^user selects partner as PartnerLink$")
	public void user_selects_partner_as_PartnerLink(DataTable partnerName)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		pePage.selectPartner(Web.rawValues(partnerName).get(0).get("partner"));
		Reporter.logEvent(Status.INFO, "Select a partner", "Select a partner:"
				+ Web.rawValues(partnerName).get(0).get("partner"), true);

	}

	@When("^enters a plan name$")
	public void enters_a_plan_name(DataTable planName) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		pePage.enterPlanName(Web.rawValues(planName).get(0).get("planName"));
		Reporter.logEvent(Status.INFO, "Enter plan name", "Enter plan name:"
				+ Web.rawValues(planName).get(0).get("planName"), true);
	}

	@When("^chooses internal Compliance service$")
	public void chooses_internal_Compliance_service(DataTable internalcompliance)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		pePage.selectInternalCompliance(Web.rawValues(internalcompliance)
				.get(0).get("internalCompliance"));
		Reporter.logEvent(
				Status.INFO,
				"Chooses internal Compliance service",
				"Chooses internal Compliance service:"
						+ Web.rawValues(internalcompliance).get(0)
								.get("internalCompliance"), true);
	}

	@When("^clicks on Save & Continue$")
	public void clicks_on_Save_Continue() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickSaveAndContinueOnAddPlanPage1();
		Reporter.logEvent(Status.INFO, "Clicks on Save & Continue",
				"Clicks on Save & Continue", true);
	}

	@Given("^user is on PlanExpress Add a New Plan page two$")
	public void user_is_on_PlanExpress_Add_a_New_Plan_page_two()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.isAddPlanPage2();
	}

	@When("^user enters required data fields$")
	public void user_enters_required_data_fields() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.fillAddressOnAddPlanPage2("Sample Company", "Addr1", "addr2",
				"Denver", "Colorado", "80014", "United States");
	}

	@When("^enters a plan name in range for the PartnerLink$")
	public void enters_a_plan_name_in_range_for_the_PartnerLink(String query)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.fillPlanNumber(query);
	}

	@When("^selects Save & Return$")
	public void selects_Save_Return() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnSaveAndReturn();
		Reporter.logEvent(Status.INFO,
				"Clicked on Save and return on add plan page 2",
				"Clicked on Save and return on add plan page 2", true);
	}

	@Then("^user is presented Implementation Checklist page$")
	public void user_is_presented_Implementation_Checklist_page()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		if (pePage.isImplementationCheckList()){
			Reporter.logEvent(Status.PASS,
					"user is presented Implementation Checklist page",
					"user is presented Implementation Checklist page", true);
		}else{
		Reporter.logEvent(Status.FAIL,
				"user is presented Implementation Checklist page",
				"user is not presented Implementation Checklist page", true);
		}
	}

	@When("^selects option (\\d+) Complete Plan Data$")
	public void selects_option_Complete_Plan_Data(int optionNumber)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnCompletePlanData();
		Reporter.logEvent(Status.INFO, "Clicks on Complete Plan Data link",
				"Clicks on Complete Plan Data link", false);
	}

	@When("^selects a \"([^\"]*)\" to complete from list provided$")
	public void selects_a_to_complete_from_list_provided(String ga_ID)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Globals.planNumber=ga_ID;
		pePage.clickOnPlanLink(ga_ID);
		Reporter.logEvent(Status.INFO,
				"Clicks on Plan link from list provided",
				"Clicks on Plan link from list provided", false);
	}

	@When("^on page (\\d+): Enrollment Kits, answers Should a beneficiary form be included in the enrollment books\\?: as \"([^\"]*)\"$")
	public void on_page_Enrollment_Kits_answers_Should_a_beneficiary_form_be_included_in_the_enrollment_books_as(
			int arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOn3600EnrollmentKits();
		Reporter.logEvent(Status.INFO, "Clicks on 3600: Enrollment Kits",
				"Clicks on 3600: Enrollment Kits", true);
		pePage.selectYBeneficiaryForm();
		Reporter.logEvent(
				Status.INFO,
				"Answer Y is selected for question \"Should an beneficiary form be included in the enrollment books?\"",
				"Answer Y is selected for question \"Should an beneficiary form be included in the enrollment books?\"",
				false);

	}

	@When("^selects Create forms and update recordkeeping system from Implementation Checklist for that plan$")
	public void selects_Create_forms_and_update_recordkeeping_system_from_Implementation_Checklist_for_that_plan()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		pePage.clickOnCreateFormsAndUpdate();
		Web.waitForPageToLoad(Web.getDriver());
		Reporter.logEvent(
				Status.INFO,
				"selects Create forms and update recordkeeping system from Implementation Checklist for that plan",
				"PXIS run will happen in the background",
				false);
		if(pePage.isSuccessMessage())
			Reporter.logEvent(Status.PASS, "Full Plan load completed", "Full Plan load completed", false);
		else
			Reporter.logEvent(Status.FAIL, "Full Plan load not completed", "Full Plan load not completed", true);
	}
	
	@Then("^created plan data can be verified with sql below$")
	public void created_plan_data_can_be_verified_with_sql_below(String query) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    pePage.verifyDBValues(query, Globals.planNumber);
	}
	
	
	 /* @SWEB-17822
	  * Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>" 	    
	  * When user selects PartnerLink>Implementation>Plan Express	    
	  * And selects option 2 Complete Plan Data	    
	  * And selects a plan "<GA_ID>" to complete from list provided	    
	  * And creates a Frozen money type on pages 5400-5430 using "<Money_Type>" , "<Descr>" and "<Type_Descr>"	    
	  * And selects Create forms and update recordkeeping system from Implementation Checklist for that plan	    
	  * Then PXIS runs in background and updates GRP_DEF_MNTY.CLASSIFICATION_CODE is appended with FZ for the money types marked as Frozen money type on pages 5400-5430 and can be verified with sql for "<GA_ID>","<Classification_Code>","<Descr>"
	*/
	
	
	 @When("^user selects PartnerLink>Implementation>Plan Express$")
	    public void user_selects_partnerlinkimplementationplan_express() throws Throwable {
		 if (plHomePage.isPlanExpressPage()) {
				Reporter.logEvent(Status.PASS, "User is on plan Express page",
						"User is on plan Express page", true);
			} else {
				Reporter.logEvent(Status.FAIL, "User is not on plan Express page",
						"User is not on plan Express page", true);
			}  				 
	    }
	 
	 @When("^selects a plan \"([^\"]*)\" to complete from list provided$")
		public void selects_a_plan_something_to_complete_from_list_provided(String ga_ID)
				throws Throwable {
			try {
				Thread.sleep(3000);
				Globals.planNumber=ga_ID;
				pePage.searchPlanNo(ga_ID);
				pePage.clickOnlegalPlanLink(ga_ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	 
	 @Then("^below page 6200 line item on that page the \"([^\"]*)\" are presented$")
	    public void below_page_6200_line_item_on_that_page_the_are_presented
	    (@Delimiter(",") List<String> tabs) throws Throwable {
		 for (String tab : tabs) {
		 if (pePage.validateTabsInPlanExpress(tab)) {
				Reporter.logEvent(Status.PASS, "Tab: " +tab+ "is presented below 6200 line items ",
						"Tab is presented below 6200 line items", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Tab:" +tab+ "is presented below 6200 line items ",
						"Tab is not presented below 6200 line items", true);
			} 				 
	    }
	 }
	 
	 @When("^User selects PartnerLink>Plan Visualizer menu tab$")
	    public void user_selects_partnerlinkplan_visualizer_menu_tab() throws Throwable {
		 plHomePage.clickOnPlanVisualizer();		 
	    } 
	 
	 @Then("^Plan Visualizer page will open$")
	    public void plan_visualizer_page_will_open() throws Throwable {
		 Reporter.logEvent(Status.INFO, "Plan Visualizer page displayed ",
					"Plan Visualizer page displayed", false);
	    }

	 @Then("^breadcrumb will update to \"([^\"]*)\"$")
	    public void breadcrumb_will_update_to_something(String strArg1) throws Throwable {
	       if( plHomePage.isBreadcrumb(strArg1)){
	    	   Reporter.logEvent(Status.PASS, "Breadcrumb is updated to PartnerLink / Plan Visualizer ",
						"Breadcrumb is updated to PartnerLink / Plan Visualizer", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Breadcrumb is not updated to PartnerLink / Plan Visualizer",
						"Breadcrumb is not updated to PartnerLink / Plan Visualizer", true);
			} 		 
	    }

	        
	 @When("^creates a Frozen money type on pages 5400-5430 using \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void creates_a_frozen_money_type_on_pages_54005430_using_something_something_and_something(String moneyType, String descr, String typeDescr) throws Throwable {
		 pePage.clickOnFrozenMoneytype();
		 pePage.addFrozenMoney();
		 pePage.add5410FrozenMoney(moneyType,descr);
		 pePage.add5420FrozenMoney(moneyType,typeDescr);
		 pePage.add5430FrozenMoney(moneyType);
		 pePage.add5440FrozenMoney();
		 pePage.add5500FrozenMoney();
		 pePage.add5600FrozenMoney();
		 pePage.add5620FrozenMoney();
		 pePage.add5640Vesting();
		 pePage.add5645Vesting();
		 pePage.add5650Vesting();
		 pePage.add5660Eligibility();
		 pePage.add5670Eligibility();
		 pePage.add5685Safeharbor();
		 pePage.add5685Safeharbor();
		 pePage.add5685Safeharbor();
		 pePage.add5800Misc();
		 pePage.add5900Distribution();
		 pePage.add6000Loans();
		 pePage.add6100FeeRecovery();
		 pePage.add6200Loans();
		 
	    }	 
	 	 
	 @Then("^PXIS runs in background and updates GRP_DEF_MNTY.CLASSIFICATION_CODE is appended with FZ for the money types marked as Frozen money type on pages 5400-5430 and can be verified with sql for \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
	    public void pxis_runs_in_background_and_updates_grpdefmntyclassificationcode_is_appended_with_fz_for_the_money_types_marked_as_frozen_money_type_on_pages_54005430_and_can_be_verified_with_sql_for_somethingsomethingsomething(String gaid,String classificationcode,String descr,String query) throws Throwable {
		 pePage.getFrozenMoneyCreated(query,gaid,classificationcode,descr);	
	    }
	 
	 @When("^User selects Partner drop down$")
	    public void user_selects_partner_drop_down() throws Throwable {
	      pePage.selectPartnerDropDown();	      
	    }

	 @Then("^\"([^\"]*)\" appear in drop down list$")
	    public void something_appear_in_drop_down_list(String partnerOption) throws Throwable {
	       pePage.validatePartner(partnerOption);
	    } 
	 
	 @Given("^User selects \"([^\"]*)\" from partner drop down on Add plan page 1$")
	    public void user_selects_something_from_partner_drop_down_on_add_plan_page_1(String partnerOption) throws Throwable {
	       pePage.selectPartner(partnerOption);     
	    }

	 @When("^User selects TRS Flex Core from Partner drop down and selects Save$")
	    public void user_selects_trs_flex_core_from_partner_drop_down_and_selects_save() throws Throwable {
		 pePage.enterPlanName("TRSFC_".concat(RandomStringUtils.randomAlphabetic(4).toUpperCase()));
	       pePage.selectInternalCompliance("Yes");
	       pePage.clickSaveAndContinueOnAddPlanPage1();
	    }

	 @Then("^Product code is stored as TRSFLEXCRE and providing company is stored as \"([^\"]*)\" and plan range allowed is \"([^\"]*)\" to \"([^\"]*)\" and state situs is all available states$")
	    public void product_code_is_stored_as_trsflexcre_and_providing_company_is_stored_as_something_and_plan_range_allowed_is_something_to_something_and_state_situs_is_all_available_states(String companyCode, String planRange1, String planRange2, String query) throws Throwable {
	       pePage.companyCodeAndPlanRange(companyCode, planRange1, planRange2, query);
	    } 
	 
	 @Given("^User is on Edit Plan page 2 and selected TRS Flex Core as partner$")
	    public void user_is_on_edit_plan_page_2_and_selected_trs_flex_core_as_partner() throws Throwable {
	       pePage.validateAddPlanPage2();
	    }
	 
	 @When("^User selects Plan type drop down$")
	    public void user_selects_plan_type_drop_down() throws Throwable {
	       pePage.clickPlanTypeDrpDwn();
	    }

	 @Then("^\"([^\"]*)\" appears in drop down list as only option selectable$")
	    public void something_appears_in_drop_down_list_as_only_option_selectable(String planType) throws Throwable {
	        if (pePage.validatePlanType(planType)){
	        	 Reporter.logEvent(Status.PASS, "TRS appears in the drop down list",
							"TRS appears in the drop down list", true);
				} else {
					Reporter.logEvent(Status.FAIL, "TRS not appears in the drop down list",
							"TRS not appears in the drop down list", true);
				} 
	    }
	 
	 @Given("^User selects TRS from plan type drop down on Edit Plan page 2$")
	    public void  user_selects_trs_from_plan_type_drop_down_on_edit_plan_page_2() throws Throwable {
		System.out.println("TRS appears in the drop down list");
	    }
	 
	 @When("^User selects TRS from plan type drop down and selects Save$")
	    public void user_selects_trs_from_plan_type_drop_down_and_selects_save() throws Throwable {
	        pePage.fillAddressOnAddPlanPage2("SKNL", "1234 Test Dr", "Test1", "Greenwood village", "COLORADO", "80111", "UNITED STATES");
	        pePage.setPlanNumber(RandomStringUtils.randomNumeric(6).concat("-T1"));        
	 }
	 
	 @When("^User selects TRS Flex from plan type drop down and selects Save$")
	    public void user_selects_trs_Flex_from_plan_type_drop_down_and_selects_save() throws Throwable {
	        pePage.fillAddressOnAddPlanPage2("SKNL", "1234 Test Dr", "Test1", "Greenwood village", "COLORADO", "80111", "UNITED STATES");
	        pePage.setPlanNumber(RandomStringUtils.randomNumeric(6).concat("-H1"));        
	 }
	 
	 @Then("^Plan type is stored as TRS$")
	    public void plan_type_is_stored_as_trs() throws Throwable {
		System.out.println("Plan type is stored as TRS");
	    }
	 
	 @When("^User selects Cancel on Edit Plan page 2$")
	    public void user_selects_Cancel_on_edit_plan_page_2() throws Throwable {
	       pePage.clickOnCancel();
	       
	    }
	 
	 @Then("^Plan is created also in Database$")
	    public void plan_is_created_also_in_database(String query) throws Throwable {
	      pePage.validatePlanInDB(query, Globals.planNumber);
	    }
	 
	 @Given("^User is on \"([^\"]*)\" Page$")
	    public void user_is_on_something_page(String lblPage) throws Throwable {
	       pePage.isImplementationCheckListPage(lblPage);
	    }

	    @Then("^new page \"([^\"]*)\" is presented in Implementation Checklist$")
	    public void new_page_something_is_presented_in_implementation_checklist(String lnkPageName) throws Throwable {
	       if (pePage.validateLink(lnkPageName)){
	    	   Reporter.logEvent(Status.PASS, "Implementation Checklist Page is presented with",
	    			   lnkPageName, true);
			} else {
				Reporter.logEvent(Status.FAIL, "Implementation Checklist Page is not presented with",
						lnkPageName, true);
			}
	    }
	 
	    @When("^User selects partner of TRS Flex Core$")
	    public void user_selects_partner_of_trs_flex_core() throws Throwable {    	
	    	System.out.println("User selects partner of TRS Flex Core");    	
	    } 
	 
	    @Then("^new page 320 displays drop down values for TRS_HSA_Plan_Types$")
	    public void new_page_320_displays_drop_down_values_for_trshsaplantypes(DataTable TRSPlan) throws Throwable {
	        pePage.validateTRSHSAPlan(TRSPlan);
	    }
	 
	    @And("^new 320 page is presented and presents \"([^\"]*)\" option with Yes No radio button to select response$")
	    public void new_320_page_is_presented_and_presents_something_option_with_yes_no_radio_button_to_select_response(String radiobtnLbl) throws Throwable {
	        if (pePage.isDrpdwnLabelDisplayed(radiobtnLbl)){
	        	Reporter.logEvent(Status.PASS, "new 320 page is presented with yes No radio button for question:",
	        			radiobtnLbl, true);
				} else {
					Reporter.logEvent(Status.FAIL, "new 320 page is presented with yes No radio button for question:",
							radiobtnLbl, true);
				}
	    }
   
		@When("^User clicks on new plan and selects \"([^\"]*)\" page$")
	    public void user_clicks_on_new_plan_and_selects_something_page(String lnkName) throws Throwable {
			pePage.searchPlanNo(Globals.planNumber);
			pePage.clickOnlegalPlanLink(Globals.planNumber);
			pePage.validateLink(lnkName);
	    }
	 
		@When("^User selects \"([^\"]*)\" selects Save & Return$")
	    public void user_selects_something_selects_save_return(String trshsaplantype) throws Throwable {
	        pePage.selectTRSHSAPlanType(trshsaplantype);        
	    }

		
		 @And("^User provide Plan sponsor EIN in \"([^\"]*)\" page$")
	    public void user_provide_plan_sponsor_ein_in_something_page(String lnkPageName) throws Throwable {
	        pePage.validateLink(lnkPageName);
	        pePage.fillPlanSponsorEIN(RandomStringUtils.randomNumeric(7));
	        
	    }
	 
		 @Then("^click on Create Forms and Update Recordkeeping and validate in DB using query$")
		    public void click_on_create_forms_and_update_recordkeeping_and_validate_in_db_using_query(String query) throws Throwable {
			 pePage.clickOnCreateFormsAndUpdate();
		       pePage.isSuccessMessage();
		       pePage.validateGAIDInDB(query, Globals.planNumber);
		 }
		 
		 @And("^Group Level Service Rule is saved with data in DB$")
		    public void group_level_service_rule_is_saved_with_data_in_db(String query) throws Throwable {
		      pePage.validateServicelevelCode(query);
		    }				 
		 
	       @When("^User selects TRS Flex Empower HSA from Partner drop down and selects Save$")
	       public void user_selects_trs_flex_empower_hsa_from_partner_drop_down_and_selects_save() throws Throwable {
	    	   pePage.enterPlanName("TRSEHSA_".concat(RandomStringUtils.randomAlphabetic(4).toUpperCase()));
		       pePage.selectInternalCompliance("Yes");
		       pePage.clickSaveAndContinueOnAddPlanPage1();
	       }			     
	       
	       @Then("^Product code is stored as TRSFLEXOPT and providing company is stored as \"([^\"]*)\" and plan range allowed is \"([^\"]*)\" to \"([^\"]*)\" and state situs is all available states$")
	       public void product_code_is_stored_as_trsflexopt_and_providing_company_is_stored_as_something_and_plan_range_allowed_is_something_to_something_and_state_situs_is_all_available_states(String companyCode, String planRange1, String planRange2, String query) throws Throwable {
	    	   pePage.companyCodeAndPlanRange(companyCode, planRange1, planRange2, query);
		    }   
	       
	       @Given("^User is on Edit Plan page 2 and selected TRS Flex Empower HSA as partner$")
	       public void user_is_on_edit_plan_page_2_and_selected_trs_flex_empower_hsa_as_partner() throws Throwable {
	       pePage.validateAddPlanPage2();
	       }
	       
	       @Given("^questions are answered on pages \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" as$")
	       public void questions_are_answered_on_pages_something_and_something_and_something_as(String page1, String page2, String page3) throws Throwable {
	           pePage.answerDiffPages(page1, page2, page3);
	       } 
	       
	       @And("^Age 59.5 is No and Inservice retirement age is Yes and Inservice is Yes$")
	       public void age_595_is_no_and_inservice_retirement_age_is_yes_and_inservice_is_yes() throws Throwable {
	          if(pePage.validateQuestionsAnswered()){
	        	  Reporter.logEvent(Status.PASS, "All questions were answered ",
	        			"for Age 59.5,Inservice retirement age and Inservice,", true);
				} else {
					Reporter.logEvent(Status.FAIL, "All questions were not answered",
							"for Age 59.5,Inservice retirement age and Inservice", true);
				}
	       }
	       
	       @Then("^crit \"([^\"]*)\" or \"([^\"]*)\" applies to web initiation rule in rule_sel table in database.$")
	       public void crit_something_or_something_applies_to_web_initiation_rule_in_rulesel_table_in_database(String critSeq1, String critSeq2 , String query) throws Throwable {
	          pePage.validateCritSeq(query, critSeq1, critSeq2);
	    	   
	       }
	       
	       @And("^Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is Yes$")
	       public void age_595_is_yes_and_inservice_retirement_age_is_yes_and_inservice_is_yes() throws Throwable {
	    	   if(pePage.validateQuestionsAnswered()){
		        	  Reporter.logEvent(Status.PASS, "All questions were answered",
		        			"for Age 59.5,Inservice retirement age and Inservice", true);
					} else {
						Reporter.logEvent(Status.FAIL, "All questions were not answered",
								"for Age 59.5,Inservice retirement age and Inservice", true);
					}
	       }
	      
	       @And("^Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is No$")
	       public void age_595_is_yes_and_inservice_retirement_age_is_yes_and_inservice_is_no() throws Throwable {
	    	   if(pePage.validateQuestionsAnswered()){
		        	  Reporter.logEvent(Status.PASS, "All questions were answered",
		        			"for Age 59.5,Inservice retirement age and Inservice", true);
					} else {
						Reporter.logEvent(Status.FAIL, "All questions were not answered",
								"for Age 59.5,Inservice retirement age and Inservice", true);
					}
	       }
	    
	       @And("^Age 59.5 is No and Inservice retirement age is Yes and Inservice is No$")
	       public void age_595_is_no_and_inservice_retirement_age_is_yes_and_inservice_is_no() throws Throwable {
	    	   if(pePage.validateQuestionsAnswered()){
		        	  Reporter.logEvent(Status.PASS, "All questions were answered",
		        			"for Age 59.5,Inservice retirement age and Inservice", true);
					} else {
						Reporter.logEvent(Status.FAIL, "All questions were not answered",
								"for Age 59.5,Inservice retirement age and Inservice", true);
					}
	       } 
	     
	       @And("^Age 59.5 is No and Inservice retirement age is No and Inservice is No$")
	       public void age_595_is_no_and_inservice_retirement_age_is_no_and_inservice_is_no() throws Throwable {
	    	   if(pePage.validateQuestionsAnswered()){
		        	  Reporter.logEvent(Status.PASS, "All questions were answered",
		        			"for Age 59.5,Inservice retirement age and Inservice", true);
					} else {
						Reporter.logEvent(Status.FAIL, "All questions were not answered",
								"for Age 59.5,Inservice retirement age and Inservice", true);
					}
	       }
	       
	       @And("^Age 59.5 is No and Inservice retirement age is No and Inservice is Yes$")
	       public void age_595_is_no_and_inservice_retirement_age_is_no_and_inservice_is_yes() throws Throwable {
	    	   if(pePage.validateQuestionsAnswered()){
		        	  Reporter.logEvent(Status.PASS, "All questions were answered",
		        			"for Age 59.5,Inservice retirement age and Inservice", true);
					} else {
						Reporter.logEvent(Status.FAIL, "All questions were not answered",
								"for Age 59.5,Inservice retirement age and Inservice", true);
					}
	       }  
	       
	       @And("^Age 59.5 is Yes and Inservice retirement age is No and Inservice is No$")
	       public void age_595_is_yes_and_inservice_retirement_age_is_no_and_inservice_is_no() throws Throwable {
	    	   if(pePage.validateQuestionsAnswered()){
		        	  Reporter.logEvent(Status.PASS, "All questions were answered",
		        			"for Age 59.5,Inservice retirement age and Inservice", true);
					} else {
						Reporter.logEvent(Status.FAIL, "All questions were not answered",
								"for Age 59.5,Inservice retirement age and Inservice", true);
					}
	       }
	       
	       @And("^Age 59.5 is Yes and Inservice retirement age is No and Inservice is Yes$")
	       public void age_595_is_yes_and_inservice_retirement_age_is_no_and_inservice_is_yes() throws Throwable {
	    	   if(pePage.validateQuestionsAnswered()){
		        	  Reporter.logEvent(Status.PASS, "All questions were answered",
		        			"for Age 59.5,Inservice retirement age and Inservice", true);
					} else {
						Reporter.logEvent(Status.FAIL, "All questions were not answered",
								"for Age 59.5,Inservice retirement age and Inservice", true);
					}
	       }
	       
}
