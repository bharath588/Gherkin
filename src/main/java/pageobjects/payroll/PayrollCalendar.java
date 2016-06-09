package pageobjects.payroll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;






import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;






import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.balance.Balance;
import pageobjects.general.LeftNavigationBar;


public class PayrollCalendar extends LoadableComponent<PayrollCalendar> {
	//Declarations
		private LoadableComponent<?> parent;
		
		@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
		@FindBy(xpath="//h1[contains(text(),'Payroll Calendar')]") private WebElement lblPayrollCalendar;
		@FindBy(linkText="Log out") private WebElement lnkLogout;
		@FindBy(xpath="//table[@class='table']/thead/tr") private WebElement hdrPayrollTable;
		@FindBy(xpath="//table[@class='table']/tbody/tr") private List<WebElement> lstPayrollTableData;
		@FindBy(xpath = "//img[@class='site-logo']")
		private WebElement lblSponser;
		
		/** Empty args constructor
		 * 
		 */
		public PayrollCalendar() {
			this.parent = new LeftNavigationBar();
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		/** Constructor taking parent as input
		 * 
		 * @param parent
		 */
		public PayrollCalendar(LoadableComponent<?> parent) {
			this.parent = parent;
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		@Override
		protected void isLoaded() throws Error {
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
			String ssn = Stock.GetParameterValue("userName");
			String userFromDatasheet = null;
			if(Globals.GC_EXECUTION_ENVIRONMENT.equalsIgnoreCase("PROD"))
			{
				userFromDatasheet=Stock.GetParameterValue("lblUserName");
			}
			else{
			ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(
					0, ssn.length() - 3));

			
			try {
				userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
						+ strUserInfo.getString("LAST_NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			String userLogedIn = this.lblUserName.getText();
			String sponser = this.lblSponser.getAttribute("Alt");
			if (sponser.isEmpty()) {
				sponser = Common.GC_DEFAULT_SPONSER;
			}
			if (userFromDatasheet.equalsIgnoreCase(userLogedIn)
					) {
				Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
				Assert.assertTrue(lib.Web.isWebElementDisplayed(lblPayrollCalendar,true));
			} else {
				this.lnkLogout.click();
				Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
			}
		}

		@Override
		protected void load() {
			this.parent.get();
			
			((LeftNavigationBar) this.parent).clickNavigationLink("Payroll calendar");
			
		}
		
		public void verifyDataIsDiaplyed(){
			if(StringUtils.containsIgnoreCase(hdrPayrollTable.getText(), "CUT-OFF DATE EFFECTIVE PAY DATE REMAINING PAY PERIODS"))
				Reporter.logEvent(Status.PASS, "Verify table header is displayed","Expected Header: CUT-OFF DATE EFFECTIVE PAY DATE REMAINING PAY PERIODS \n Actual: "+hdrPayrollTable.getText(), true);
			else
				Reporter.logEvent(Status.FAIL, "Verify table header is displayed","Expected Header: CUT-OFF DATE EFFECTIVE PAY DATE REMAINING PAY PERIODS \n Actual: "+hdrPayrollTable.getText(),true);
			
			if(lstPayrollTableData.size()>=1)
				Reporter.logEvent(Status.PASS, "Verify table data is displayed","Data is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify table data is displayed","Data not displayed",true);
			
			
		}
}
