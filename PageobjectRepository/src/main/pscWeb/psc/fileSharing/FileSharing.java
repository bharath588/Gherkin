/**
 * 
 */
package psc.fileSharing;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.homepage.HomePage;
import psc.login.LoginPage;

import com.aventstack.extentreports.Status;
import gwgwebdriver.ByAngularButtonText;
import gwgwebdriver.ByAngularRepeater;
import gwgwebdriver.GwgWebDriver;

/**
 * @author rvpndy
 *
 */
public class FileSharing extends LoadableComponent<FileSharing>{
	private static final String rootSelector = "ng-app";

	@FindBy(id = "framel")
	private WebElement fileSharingFrame;
	ByAngularRepeater pagination = new ByAngularRepeater(rootSelector, "n in fileShareRepo.view.range()", true);
	ByAngularButtonText uploadNewDoc = new ByAngularButtonText(rootSelector, "Upload New Document");
	
	private WebElement uploadNewDocument()
	{
		try{
		Web.gwgWebDriver = new GwgWebDriver((JavascriptExecutor) Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(fileSharingFrame);
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		}
		catch(Exception e)
		{
			load();
		}
		return Web.getDriver().findElement(uploadNewDoc);
	}
	
	private List<WebElement> pagination()
	{
		Web.gwgWebDriver = new GwgWebDriver((JavascriptExecutor) Web.getDriver());
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(fileSharingFrame);
		Web.gwgWebDriver.waitForAngularRequestsToFinish();
		return Web.getDriver().findElements(pagination);
	}
	
	

	LoadableComponent<?> parent;
	public FileSharing()
	{
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		PageFactory.initElements(Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(uploadNewDocument()));

	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		HomePage homepage = (HomePage) this.parent;
		new HomePage(new LoginPage(), false, new String[] {
			Stock.GetParameterValue("username"),
			Stock.GetParameterValue("password") }).get();

		Reporter.logEvent(Status.PASS,
				"Check if the user has landed on homepage",
				"The user has landed on homepage", true);

		try {
			WebElement fileSharingMenu = Web.returnElement(new HomePage(), "File Sharing");
			if(fileSharingMenu.isDisplayed())
				Web.clickOnElement(fileSharingMenu);
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(fileSharingFrame);
		if(Web.isWebElementDisplayed(uploadNewDocument()))
		{
			Reporter.logEvent(Status.PASS, "Check if user has landed on File Sharing page",
					"User has landed on File Sharing page", false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Check if user has landed on File Sharing page",
					"User has not landed on File Sharing page", true);
		}

	}
	
	public boolean verifyPaginationOnFileSharingPage()
	{
		if(this.pagination().size()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
