package pageobjects.admin_tool;

import lib.Reporter;
import com.aventstack.extentreports.*;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

public class LoginPage extends LoadableComponent<LoginPage> {

	//Variable Declaration
	@FindBy(id = "username") 
	private WebElement txtUsername;
	
	@FindBy(id = "password") 
	private WebElement txtPassword;
	
	@FindBy(className= "button") 
	private WebElement btnLogin;
	
   public LoginPage() {
	PageFactory.initElements(Web.getDriver(), this);	
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(txtUsername));
		
	}


	@Override
	protected void load() {
		Web.getDriver().get(Stock.getConfigParam("AppURL" + "_" + Stock.getConfigParam("TEST_ENV")));
		Reporter.logEvent(Status.INFO, "Verify if AAG Admin Tool has been launched", "AAG Admin Tool application has been launched", true);
	}

	
	public void performLogin(String username,String password)
	{
		Web.setTextToTextBox(txtUsername, username);
		Web.setTextToTextBox(txtPassword, password);
		Web.clickOnElement(btnLogin);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// Do Nothing
		}
	}
}
