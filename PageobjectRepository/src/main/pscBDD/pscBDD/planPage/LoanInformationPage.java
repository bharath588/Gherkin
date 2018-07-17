package pscBDD.planPage;

import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenBy;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_gwgwebdriver.pagefactory.NextGenPageFactory;
import bdd_annotations.FindBy;
import bdd_lib.DB;
import bdd_lib.Web;
import bdd_core.framework.Globals;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bdd_lib.CommonLib;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;
import bdd_reporter.Reporter;







import com.aventstack.extentreports.Status;



public class LoanInformationPage extends LoadableComponent<LoanInformationPage> {
	@FindBy(how=How.XPATH,using="//*[span[text()='navPlan']]//h3[@id='headerInfo_xhtml']")
	private WebElement planNumberOnPageHeader;
	
	@FindBy(how=How.XPATH,using="//h3[text()='Loan Information']")
	private WebElement loanInformationPageHeaderName;
	@FindBy(how=How.XPATH,using="//table[@ng-repeat='dto in dtoList'][1]//tr/td[1]")
	private List<WebElement> loanReasonGeneralSectionLabel;
	@FindBy(how=How.XPATH,using="//table[@ng-repeat='dto in dtoList'][1]//tr[1]/td[2]")
	private WebElement generalRestrictionNumberLoansAllowedValue;
	@FindBy(how=How.XPATH,using="//*[contains(text(),'Loans required prior to hardship')]")
	private WebElement labelLoansRequiredPriorToHardship;
	@FindBy(how=How.XPATH,using="//td[contains(text(),'Loan restrictions to hardship')]/following-sibling::td")
	private List<WebElement> loanlRestrictionHardshipReasonsValue;
	
	
	
	
	LoadableComponent<?> parent;
	CommonLib commonLib;
	PlanProvisionsPage planProvPage;

	public LoanInformationPage(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
		planProvPage=new PlanProvisionsPage();
		
	}	

	@Override
	protected void load() {
		try {
			planProvPage.get();
			Web.FrameSwitchONandOFF(true, planProvPage.getWebElement("Plan Frame"));
			Web.clickOnElement(planProvPage, "Loan Information");
			Web.nextGenDriver.waitForAngular();
			Web.FrameSwitchONandOFF(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void isLoaded() throws Error {
		// if (Web.isWebElementDisplayed(planProvisionsBreadcrumb, true))
		// Web.actionsClickOnElement(planProvPage.getWebElement("Breadcrumb1"));
		String text = planProvPage.getWebElement("Breadcrumb1").getText();
		if (text.equals(""))
			text = planProvPage.getWebElement("Breadcrumb2").getText();
		Assert.assertTrue(text.trim().equalsIgnoreCase("Loan information"));
	}
	
	public boolean isLoanInformationPage(){
		Web.FrameSwitchONandOFF(true,planProvPage.getWebElement("Plan Frame"));
		if(Web.isWebElementDisplayed(loanInformationPageHeaderName, true))
			return true;
		return false;
		
	}
	
	public String get_max_loans_allowed(String query, String ga_id,String... db_name) {
		String maxLoanAllowed = "";
		String db_Name="D_INST";
		if(db_name.length>0)
			db_Name=db_name[0];
		try {
			ResultSet resultSet = DB
					.executeQuery(db_Name, query, ga_id);
			while (resultSet.next()) {
				maxLoanAllowed = resultSet.getString(1);
				break;
			}
			if (maxLoanAllowed.length() > 0)
				maxLoanAllowed = resultSet.getString(1);
			else
				Reporter.logEvent(Status.INFO,
						"No record founds in the given query",
						"No record founds in the given query", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxLoanAllowed;

	}
	
	public boolean isLabelDisplaysInLoanInformationPage(List<String> label) {
		try {
			Web.FrameSwitchONandOFF(true,planProvPage.getWebElement("Plan Frame"));
			System.out.println(label);
			List<String> labelText = new ArrayList<String>();
			loanReasonGeneralSectionLabel.remove(loanReasonGeneralSectionLabel
					.size()-1);
			loanReasonGeneralSectionLabel.forEach(ele -> labelText.add(ele
					.getText().trim()));
			System.out.println(labelText);
			if (labelText.containsAll(label))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}
	
	public boolean verifyRestrictionNumberOfLoansAllowedValue(String expValue) {
		Web.FrameSwitchONandOFF(false);
		Web.FrameSwitchONandOFF(true, planProvPage.getWebElement("Plan Frame"));
		if (Web.isWebElementDisplayed(
				generalRestrictionNumberLoansAllowedValue, true)) {
			if (generalRestrictionNumberLoansAllowedValue.getText().trim()
					.equals(expValue.trim()))
				return true;
		}
		return false;

	}

	public boolean verifyLoanRequiredPriorToHardshipValue(String expValue) {
		if (Web.isWebElementDisplayed(labelLoansRequiredPriorToHardship, true)) {
			String text = labelLoansRequiredPriorToHardship.getText().trim();
			if (text.substring(text.length() - 1).equals(expValue.trim()))
				return true;
		}
		return false;

	}

	public boolean verifyLoanRestrictionHardshipReasonsValue(String expValue) {
		boolean flag = false;
		if (Web.isWebElementsDisplayed(loanlRestrictionHardshipReasonsValue,
				true)) {
			for (WebElement element : loanlRestrictionHardshipReasonsValue) {
				if (element.getText().trim().equals(expValue.trim()))
					flag = true;
			}
		}
		return flag;

	}
	
	
	
	

}
