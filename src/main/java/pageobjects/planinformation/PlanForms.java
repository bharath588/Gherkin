package pageobjects.planinformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;




import java.util.Set;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;









import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.balance.Balance;
import pageobjects.general.LeftNavigationBar;

public class PlanForms extends LoadableComponent<PlanForms> {

	//Declarations
		private LoadableComponent<?> parent;
		
		@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
		@FindBy(xpath="//h1[text()='Plan forms']") private WebElement lblPlanForms;
		@FindBy(linkText="Log out") private WebElement lnkLogout;
		@FindBy(xpath="//table[@class='table ng-scope']") private WebElement tblPlanForms;
		@FindBy(xpath="//table[@class='table ng-scope']/tbody/tr") private List<WebElement> lstPlanforms;
		@FindBy(xpath="//table[@class='table ng-scope']/tbody//a") private List<WebElement> lstPlanformName;
		@FindBy(xpath = "//img[@class='site-logo']")
		private WebElement lblSponser;
		@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

		
		/** Empty args constructor
		 * 
		 */
		public PlanForms() {
			this.parent = new LeftNavigationBar();
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		/** Constructor taking parent as input
		 * 
		 * @param parent
		 */
		public PlanForms(LoadableComponent<?> parent) {
			this.parent = parent;
			PageFactory.initElements(lib.Web.webdriver, this);
		}
		
		@Override
		protected void isLoaded() throws Error {
			Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

			//Assert.assertTrue(Web.isWebElementDisplayed(this.lblPlanForms,true),"PlanForms Page is Not Loaded");
			String ssn = Stock.GetParameterValue("userName");
			String userFromDatasheet = null;
			if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
			{
				userFromDatasheet=Stock.GetParameterValue("lblUserName");
			}
			else{
			ResultSet strUserInfo=null;
			try {
				strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
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
				Assert.assertTrue(lib.Web.isWebElementDisplayed(lblPlanForms,true),"Planforms page is not loadeed\n");
			} else {
				this.lnkLogout.click();
				Assert.assertTrue(false,"Logging in with new User");
			}
		}

		@Override
		protected void load() {
			this.parent.get();
			
			((LeftNavigationBar) this.parent).clickNavigationLink("Plan forms");
			
		}
		
		public void clickOnForm(String planfrom) throws InterruptedException{
			Web.waitForElement(tblPlanForms);
			int noOfPlanForms=lstPlanformName.size();
			if(planfrom==null){
				lstPlanformName.get(1).click();
				Reporter.logEvent(Status.INFO, "Verify Plan form is clicked", lstPlanformName.get(1).getText()+" Form is clicked",true);
			}
			else{
				for(int i=1;i<noOfPlanForms;i++){
					if(lstPlanformName.get(i).getText().trim().equalsIgnoreCase(planfrom)){
						lstPlanformName.get(i).click();
						Reporter.logEvent(Status.INFO, "Verify Plan form is clicked", planfrom+" Form is clicked",true);
						break;
					}
				}
			}

		}
		
	public boolean verifyPlanFormIsOpened() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean windowFound = false;
		String parentWindow = Web.webdriver.getWindowHandle();
		Set<String> handles = Web.webdriver.getWindowHandles();

		for (String windowHandle : handles) {

			if (!windowHandle.equals(parentWindow)) {
				Web.webdriver.switchTo().window(windowHandle);

				windowFound = true;
				break;
			}
				}
		// closing child window
		Web.webdriver.close(); 
		//Switching to main window
		Web.webdriver.switchTo().window(parentWindow);
		return windowFound;
	}
}
