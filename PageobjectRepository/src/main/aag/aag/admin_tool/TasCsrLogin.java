package aag.admin_tool;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class TasCsrLogin extends LoadableComponent<TasCsrLogin>{
	
	//IndID field
	@FindBy(id = "userId") 
	private WebElement txtUserID;
	
	//GaID field
	@FindBy(id = "planId") 
	private WebElement txtPlanId;
	
	//Login button
	@FindBy(className = "button") 
	private WebElement btnLogin;
	
	//pagetitle
	@FindBy(id = "page-title") 
	private WebElement pageTitle;
	
	
	
	LoadableComponent<?> parent;
	public TasCsrLogin() {
		
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = new HomePage();
	}
	
	public TasCsrLogin(LoadableComponent<?> parent)
	{
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = parent;
	}
	
	

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(pageTitle));
		
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		this.parent.get();
		HomePage Homepage = new HomePage();
		Homepage.navigateToTasCsrPage();
				
		Reporter.logEvent(Status.PASS, "Verify if the user has been logged into the advisory services  application successfully",
				"The user has been logged in successfully and landed into advisory services Page", true);
		
	}

	
	public void logIntoAdvisoryServicesPage(String IndID,String GaID)
			{
			
			Web.setTextToTextBox(txtUserID, IndID);
			Web.setTextToTextBox(txtPlanId, GaID);
			Web.clickOnElement(btnLogin);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// Do Nothing
			}
		}
	
	
}
