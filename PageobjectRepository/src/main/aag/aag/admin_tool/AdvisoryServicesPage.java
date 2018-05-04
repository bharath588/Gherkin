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

public class AdvisoryServicesPage extends LoadableComponent<AdvisoryServicesPage>

{
	
	//advisory services title
	@FindBy(className = "ibbotson") 
	private WebElement pageTitle;
	
	
	//Personal and income title
	@FindBy(xpath = ".//*[@class='pagetitle']") 
	private WebElement incomePage;
	
	//iframe
	@FindBy(xpath= "//iframe") 
	private WebElement iFrame;
	
	@FindBy(id="quickAdvice") 
	private WebElement viewButton;
	
	//overview page next button
	@FindBy(id="nextButton") 
	private WebElement nextButton;
	
	//get started button
	@FindBy(id="fullAdvice")
	private WebElement getStartButton;
	
	// next button on income page
	@FindBy(id="nextButton")
	private WebElement incNextButton;

	
	// salary text box on income page
	@FindBy(id="salary")
	private WebElement salTxtBox;

	
	//Personal and income title
	@FindBy(xpath = ".//*[@class='pagetitle']") 
	private WebElement accountsPage;
	
	//getAdvButton acounts
	@FindBy(id="getAdvButton")
	private WebElement getAdvButton;
	
	// strategy overview page
	@FindBy(id="acceptProposedButton")
	private WebElement strategyButton;
	
	// strategy overview page
	@FindBy(xpath = ".//*[@class='pagetitle']") 
	private WebElement overviewPage;
	
	//managed acc radio button
	@FindBy(id="statusRadioGroup0") 
	private WebElement maRadio;
					
	//managed acc radio button
	@FindBy(id="statusRadioGroup1") 
	private WebElement advRadio;
			
	//managed acc radio button
	@FindBy(id="statusRadioGroup2") 
	private WebElement guiRadio;
	
	//enroll stat next button
	@FindBy(id="implementButton") 
	private WebElement eSnextButton;
	
	//legal disc title
	@FindBy(xpath = ".//*[@class='pagetitle']") 
	private WebElement legalDiscPage;
	
	//accept and enroll button legal disc
	@FindBy(id="nextButton") 
	private WebElement acptEnrlLegal;
	
	//confirmation page title
	@FindBy(xpath = ".//*[@class='pagetitle']") 
	private WebElement confirmPage;
	
	//confirmation page implement changebutt
	@FindBy(id="nextButton") 
	private WebElement impChanges;
	
	//confirmation page exit
	@FindBy(id="nextButton") 
	private WebElement exit;
	
	
	//confirmation page title
	@FindBy(xpath = ".//*[@class='pagetitle']") 
	private WebElement exitPage;
	
	//Cancel Enrollment Link
	@FindBy(xpath = "//a[text()='Cancel Enrollment']") 
	private WebElement cancelEnrollment;
		
	//Cancel Enrollment Check Box
	@FindBy(id="cancelService") 
	private WebElement cbkCancelEnrollment;
	
	//Reason for uneroll radio button
	@FindBy(id="reasonRadioGroup0") 
	private WebElement reasonRadio;
	
	//Submit button for unenroll
	@FindBy(id="submitButton") 
	private WebElement unEnrollSubmit;
	
	//Enrollment Status
	@FindBy(xpath = "//td[@class='status']") 
	private WebElement enrollmentStatus;
	
	//OK button for unenroll
	@FindBy(id="OkButton") 
	private WebElement btnOK;
				
	LoadableComponent<?> parent;
	public  AdvisoryServicesPage() {
		// TODO Auto-generated constructor stu
		
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = new TasCsrLogin();
	}
	
	public AdvisoryServicesPage(LoadableComponent<?> parent)
	{
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = parent;
	}

	@Override
	protected void isLoaded() throws Error {
		Web.getDriver().switchTo().frame(iFrame);
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(pageTitle));
		Web.getDriver().switchTo().defaultContent();
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		this.parent.get();
		TasCsrLogin tas= new TasCsrLogin();
		
		tas.logIntoAdvisoryServicesPage(Stock.GetParameterValue("IndID"),Stock.GetParameterValue("SponsorId"));
		
		Reporter.logEvent(Status.PASS, "Verify if the user has been logged into the application successfully",
				"The user has been logged in successfully and landed into Advisory services Page", true);
		
	}

	public void viewYourStrategy()
	{
		Web.getDriver().switchTo().frame(iFrame);
		if(Web.isWebElementDisplayed(viewButton, true))
		{
			Web.clickOnElement(viewButton);
			Web.waitForElement(strategyButton);
			Reporter.logEvent(Status.PASS, "Verify view your strategy  button is displayed on the Advisory services page",
					"View your strategy button is displayed", true);
		}
	}
	
	public void getStarted()
	{
		Web.getDriver().switchTo().defaultContent();
		Web.waitForElement(iFrame);
		Web.getDriver().switchTo().frame(iFrame);
		if(Web.isWebElementDisplayed(getStartButton, true))
		{
			Reporter.logEvent(Status.PASS, "Verify Get started button is displayed on the Advisory services page",
					"Get Started button is displayed", true);
			
		}
		
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify Get started button is displayed on the Advisory services page",
					"The user has been logged in successfully and landed into Advisory services Page", true);
		}
			Web.clickOnElement(getStartButton);
			Web.waitForElement(incNextButton);
			
		/*	if(Web.VerifyText("Step 1: Information > Personal and Income", incomePage.getText(), true))
					{*/
				Reporter.logEvent(Status.PASS, "Verify user has landed on Personal and Income Page",
						" user has landed on Personal and Income Page", true);
				
		/*	}
			
			else
			{
				Reporter.logEvent(Status.FAIL, "Verify user has landed on Personal and Income Page",
						"The  user has not landed on Personal and Income Page", true);
			}*/
			
			Web.getDriver().switchTo().defaultContent();
		
	}
	
	public void nextFromincomePage()
	{
		Web.getDriver().switchTo().frame(iFrame);
		Web.setTextToTextBox(salTxtBox, "100000");
		Web.clickOnElement(nextButton);
		Web.waitForElement(getAdvButton);
		if(Web.VerifyText("Step 1: Information > Accounts", accountsPage.getText(), true))
		{
			Reporter.logEvent(Status.PASS, "Verify user has landed on Accounts Page",
					" user has landed on Accounts Page", true);
		}
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify user has landed on  Accounts Page",
					"The  user has not landed on  Accounts Page", true);
		}
		Web.getDriver().switchTo().defaultContent();
	}
		
	public void getAdvice()
	
	{
		
		Web.getDriver().switchTo().frame(iFrame);
		Web.clickOnElement(getAdvButton);
		
	/*	
		if(Web.isWebElementDisplayed(strategyButton,true))
		{*/
		Reporter.logEvent(Status.PASS, "Verify user has landed on Personal and Income Page",
				" user has landed on Personal and Income Page", true);
		
	//}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}											
/*else
{
	Reporter.logEvent(Status.FAIL, "Verify user has landed on Personal and Income Page",
			"The  user has not landed on Personal and Income Page", true);
}
*/
	
		
		
	}
	
	public void continueWithStrat1()
	{
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(iFrame);
		Web.clickOnElement(strategyButton);
		Web.waitForElement(maRadio);
		if(Web.isWebElementDisplayed(maRadio, false))
		{
			Reporter.logEvent(Status.PASS, "Verify user has landed on Enrollment status Page",
					" user has landed on Enrollment status Page", true);
		}
		
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify user has landed on  Enrollment status Page",
					"The  user has not landed on  Enrollment status", true);
		}
	
		Web.getDriver().switchTo().defaultContent();
	}

	
	public void enrollMA()
	{
		Web.getDriver().switchTo().frame(iFrame);
		Web.clickOnElement(maRadio);
	
		if(maRadio.isSelected())
		{
			Reporter.logEvent(Status.PASS, "Verify user is able to click on/choose the plan options from MA, guidance and advice",
					"user is able to click on/choose the plan options", true);
		}
		
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify user is able to click on/choose the plan options MA, guidance and advice",
					"user is able to click on/choose the plan options", true);
		}
	
		Web.getDriver().switchTo().defaultContent();
		
	}
	
	public void enrollAdvice()
	{
		Web.getDriver().switchTo().frame(iFrame);
		Web.clickOnElement(advRadio);
	
		if(advRadio.isSelected())
		{
			Reporter.logEvent(Status.PASS, "Verify user is able to click on/choose the plan options from MA, guidance and advice",
					"user is able to click on/choose the plan options", true);
	
		}
		
		else
		{
			Reporter.logEvent(Status.FAIL, "Verify user is able to click on/choose the plan options MA, guidance and advice",
					"user is able to click on/choose the plan options", true);
			
		}
	
		Web.getDriver().switchTo().defaultContent();
		
	}
	
	public void enrollNext()
	{
		Web.getDriver().switchTo().frame(iFrame);
		Web.clickOnElement(eSnextButton);
	
//	if(Web.VerifyText("Step 3: Service Options > Legal Disclaimer", legalDiscPage.getText(), true))
	//{
		Reporter.logEvent(Status.PASS, "Verify user has landed onLegal disclaimer Page",
			" user has landed onLegal disclaimer", true);

//}

/*else
{
Reporter.logEvent(Status.FAIL, "Verify user has landed on Personal and Income Page",
		"The user has not landed onLegal disclaimer page", true);
}	*/
		Web.getDriver().switchTo().defaultContent();
	}
	
	public void legalNext()
	{
		Web.getDriver().switchTo().frame(iFrame);
		Web.clickOnElement(acptEnrlLegal);
	
	/*if(Web.VerifyText("Step 4: Confirmation", confirmPage.getText(), true))
	{*/
		Reporter.logEvent(Status.PASS, "Verify user has landed on Confirmation Page",
				"The user has landed on Confirmation Page", true);

//}

/*else
{
Reporter.logEvent(Status.FAIL, "Verify user has landed on Confirmation Page",
		"The  user has not landed on Confirmation Page", true);
}	*/
		Web.getDriver().switchTo().defaultContent();
	}
	
	
	public void implementChanges()
	{
		Web.getDriver().switchTo().frame(iFrame);
		Web.clickOnElement(impChanges);
	
	/*if(Web.VerifyText("Step 4: Confirmation", confirmPage.getText(), true))
	{*/
	/*Reporter.logEvent(Status.PASS, "Verify user has landed on Congratulations page",
			" user has landed on Congratulations page", true);*/
/*}

else
{
Reporter.logEvent(Status.FAIL, "Verify user has landed on Personal and Income Page",
		"The  user has not landed on Personal and Income Page", true);
}	*/
		Web.getDriver().switchTo().defaultContent();
	}
	


	public void exit()
	{
		Web.getDriver().switchTo().frame(iFrame);
		/*
		if(Web.VerifyText("Congratulations, your changes were successful ", confirmPage.getText(), true))
		{*/
		Reporter.logEvent(Status.PASS, "Verify user has landed on Congratulations page",
				" user has landed on Congratulations page", true);
/*
	}

	else
	{
	Reporter.logEvent(Status.FAIL, "Verify user has landed on Congratulations page",
			"The  user has not landed on Congratulations page", true);
	}	*/
		
		Web.clickOnElement(exit);
		Reporter.logEvent(Status.PASS, "Verify user has successfully exited congratulations page",
				" user has successfully exited congratulations Page", true);
		Web.getDriver().switchTo().defaultContent();
	}
	
	//Cleanup step-This function is to Uneroll the Enrolled participants
	public void unEnroll(String Ind_Id, String sponsorId)
	{
		TasCsrLogin tasCsrLogin=new TasCsrLogin().get();
		tasCsrLogin.logIntoAdvisoryServicesPage(Ind_Id, sponsorId);
		
		Web.getDriver().switchTo().frame(iFrame);
		
		if(Web.isWebElementDisplayed(cancelEnrollment, true))
		{
			Web.clickOnElement(cancelEnrollment);
			Web.clickOnElement(cbkCancelEnrollment);
			Web.clickOnElement(reasonRadio);
			Web.clickOnElement(unEnrollSubmit);
			Web.clickOnElement(btnOK);
			Reporter.logEvent(Status.PASS, "UnEnroll the participant", "Participant is UnEnrolled successfully", false);
		}
		else
			Reporter.logEvent(Status.INFO, "UnEnroll the participant", "Participant is not Enrolled", false);
		
		Web.getDriver().switchTo().defaultContent();
	}
	
	//This function is to check the participants's enrollment status
	public void checkEnrollStatus(String Ind_Id, String sponsorId, String status)
	{
		TasCsrLogin tasCsrLogin=new TasCsrLogin().get();
		tasCsrLogin.logIntoAdvisoryServicesPage(Ind_Id, sponsorId);
		
		Web.getDriver().switchTo().frame(iFrame);
		
		if(Web.isWebElementDisplayed(enrollmentStatus, true))
		{
			if(Web.getWebElementText(enrollmentStatus).equals(status))
				Reporter.logEvent(Status.PASS, "Check the participant enrollment status", "The participant enrollment status is "+status, false);
			else
				Reporter.logEvent(Status.FAIL, "Check the participant enrollment status", "The participant enrollment status is NOT "+status, true);
		}
		else
			Reporter.logEvent(Status.FAIL, "TAS opened", "TAS is not logged in", true);
		
		Web.getDriver().switchTo().defaultContent();
	}	
	
}



	
	
	

