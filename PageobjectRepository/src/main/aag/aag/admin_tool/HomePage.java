package aag.admin_tool;

import lib.Reporter;

import com.aventstack.extentreports.*;

import core.framework.Globals;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

public class HomePage extends LoadableComponent<HomePage>{

	//Webelement declarations
	@FindBy(id = "oCMenu_top0")
	private WebElement tabHome;
	
	@FindBy(id = "oCMenu_top2") 
	private WebElement tabAdminstration;
	
	@FindBy(id = "oCMenu_sub23")
	private WebElement linkBatchManagement;
	
	@FindBy(id = "oCMenu_sub21")
	private WebElement linkAAGManagement;
	
	@FindBy(id = "oCMenu_sub215")
	private WebElement linkAdhocBatchProcessing;
	

	//ror processing tab
	@FindBy(id = "oCMenu_sub213")
	private WebElement linkRorProcessing;
	@FindBy(id = "oCMenu_sub211")
	private WebElement linkRequestBatchProcessing;
	
	@FindBy(id = "oCMenu_sub211")
	private WebElement linkDuplicateTsr;
	
	@FindBy(id = "oCMenu_sub212")
	private WebElement linkReportCardProcessing;
	
	@FindBy(id = "oCMenu_sub200")
	private WebElement linkThreeStrike;
	
	@FindBy(id = "oCMenu_sub20")
    private WebElement linkTasCsrAdmin;

    @FindBy(id = "oCMenu_sub200")
    private WebElement linkTasCsrApp;


	
	//Object declarations
	LoginPage loginPage;
	
	
	
	public HomePage()
	{
		PageFactory.initElements(Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(tabHome));
	}

	@Override
	protected void load() {
		loginPage = new LoginPage().get();
		loginPage.performLogin(Stock.getConfigParam(Globals.GC_COLNAME_USERID),Stock.getConfigParam(Globals.GC_COLNAME_SYSPASSWORD));
		Reporter.logEvent(Status.PASS, "Verify if the user has been logged into the application successfully",
				"The user has been logged in successfully and landed into Admin Tool Home Page", true);
	}

	public void navigateToBatchProcessingPage()
	{
	try {		
	Web.mouseHover(tabAdminstration);
	Thread.sleep(2000);
	Web.mouseHover(linkBatchManagement);
	Thread.sleep(2000);
	Web.mouseHover(linkAdhocBatchProcessing);
	Thread.sleep(2000);
	if(Web.clickOnElement(linkAdhocBatchProcessing))
		Reporter.logEvent(Status.PASS,"Click on Adhoc Batch Processing link from Administration -> Batch Management tab",
			"The user has clicked Adhoc Batch Processing option",true);
	else
		Reporter.logEvent(Status.FAIL,"Click on Adhoc Batch Processing link from Administration -> Batch Management tab",
		"The user has NOT clicked Adhoc Batch Processing option",true);
		}catch(Exception  e)
		{
			e.printStackTrace();
		}
	}
	
	public void navigateToRorProcessingPage()
	{
	try {		
		Web.mouseHover(tabAdminstration);
		Thread.sleep(2000);
		Web.mouseHover(linkBatchManagement);
		Thread.sleep(2000);
		Web.mouseHover(linkRorProcessing);
		Thread.sleep(2000);

	if(Web.clickOnElement(linkRorProcessing))
		Reporter.logEvent(Status.PASS,"Click on ROR processing link from Administration -> Batch management tab",
			"The user has clicked ROR processing option",true);
	else
		Reporter.logEvent(Status.FAIL,"Click on ROR processing link from Administration -> Batch management tab",
		"The user has NOT clicked on ROR processing option",true);
}catch(Exception  e)
	{
	e.printStackTrace();
}
}


	public void navigateToRequestBatchPage()
	{
	try {		
	Web.mouseHover(tabAdminstration);
	Thread.sleep(2000);
	Web.mouseHover(linkBatchManagement);
	Thread.sleep(2000);
	Web.mouseHover(linkRequestBatchProcessing);
	Thread.sleep(2000);
	if(Web.clickOnElement(linkRequestBatchProcessing))
		Reporter.logEvent(Status.PASS,"Click on Adhoc Batch Processing link from Administration -> Batch Management tab",
			"The user has clicked Request Batch Processing option",true);
	else
		Reporter.logEvent(Status.FAIL,"Click on Adhoc Batch Processing link from Administration -> Batch Management tab",
		"The user has NOT clicked Request Batch Processing option",true);
		}catch(Exception  e)
		{
			e.printStackTrace();
		}
	}
	
	public void navigateToReportCardProcPage()
	{
	try {		
	Web.mouseHover(tabAdminstration);
	Thread.sleep(2000);
	Web.mouseHover(linkBatchManagement);
	Thread.sleep(2000);
	Web.mouseHover(linkReportCardProcessing);
	Thread.sleep(2000);
	if(Web.clickOnElement(linkReportCardProcessing))
		Reporter.logEvent(Status.PASS,"Click on Adhoc Batch Processing link from Administration -> Report Card processing",
			"The user has clicked Request Batch Processing option",true);
	else
		Reporter.logEvent(Status.FAIL,"Click on Adhoc Batch Processing link from Administration ->Report Card processing",
		"The user has NOT clicked Request Batch Processing option",true);
		}catch(Exception  e)
		{
			e.printStackTrace();
		}
	}
	
	public void navigateToDuplicateTsr()
	{
		try {		
			Web.mouseHover(tabAdminstration);
			Thread.sleep(2000);
			Web.mouseHover(linkAAGManagement);
			Thread.sleep(2000);
			Web.mouseHover(linkDuplicateTsr);
			Thread.sleep(2000);
			if(Web.clickOnElement(linkDuplicateTsr))
				Reporter.logEvent(Status.PASS,"Click on Adhoc Batch Processing link from Administration -> DuplicateTsr",
					"The user has clicked Request Batch Processing option",true);
			else
				Reporter.logEvent(Status.FAIL,"Click on Adhoc Batch Processing link from Administration ->DuplicateTsr",
				"The user has NOT clicked Request Batch Processing option",true);
				}catch(Exception  e)
				{
					e.printStackTrace();
				}
	}
	
	public void navigateToThreeStrikePage()
	{
		try {		
			Web.mouseHover(tabAdminstration);
			Thread.sleep(2000);
			Web.mouseHover(linkBatchManagement);
			Thread.sleep(2000);
			Web.mouseHover(linkThreeStrike);
			Thread.sleep(2000);
			if(Web.clickOnElement(linkThreeStrike))
				Reporter.logEvent(Status.PASS,"Click on Adhoc Batch Processing link from Administration -> ThreeStrikeManagment",
					"The user has clicked Request Batch Processing option",true);
			else
				Reporter.logEvent(Status.FAIL,"Click on Adhoc Batch Processing link from Administration ->ThreeStrikeManagment",
				"The user has NOT clicked Request Batch Processing option",true);
				}catch(Exception  e)
				{
					e.printStackTrace();
				}
	}
	
	public void navigateToTasCsrPage()

	{
		try {
			Web.mouseHover(tabAdminstration);
			Thread.sleep(2000);

			Web.mouseHover(linkTasCsrAdmin);
			Thread.sleep(2000);

			Web.mouseHover(linkTasCsrApp);
			Thread.sleep(2000);

			if (Web.clickOnElement(linkTasCsrApp))
				Reporter.logEvent(Status.PASS, "Click on TAS CSR link from Administration -> TAS CSR Administration", "The user has clicked TAS CSR Application option", true);
			else
				Reporter.logEvent(Status.FAIL, "Click on TAS CSR link from Administration -> TAS CSR Administration", "The user has NOT clicked TAS CSR Application option", true);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}
