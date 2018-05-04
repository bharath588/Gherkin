package aag.admin_tool;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import shell.utils.ShellUtils;
import aag.aag_lib.General;

import com.aventstack.extentreports.Status;
import com.mongodb.connection.QueryResult;

import core.framework.Globals;

public class RorProcessingPage extends LoadableComponent<RorProcessingPage>{
	
	
	
	@FindBy(xpath=".//h3[contains(text(),'ROR')]")
	private WebElement rorPageTitle;

	// new request button
	@FindBy(xpath=".//p[contains(text(),'New')]")
	private WebElement newRequestButton;
	
	//gaid text field
	@FindBy(id="gaId")
	private WebElement gaIdTextBox;
	
	//type dropdown
	@FindBy(id="processCode")
	private WebElement typeDropDown;
	
	//one off dropdown
	@FindBy(xpath=".//*[@value='AAG_ROR']")
	private WebElement oneOffoption;

	//continue button
	@FindBy(xpath=".//*[@id='new']/p[contains(text(),'Continue')]")
	private WebElement continueButton;
	//
	
	@FindBy(xpath=".//*[@id='newStep2']/p[2]")
	private WebElement histContinueButton;
	//continue button 
		@FindBy(xpath="(.//p[contains(text(),'Submit')])[1]")
		private WebElement submitButton;
		
		//success message
		@FindBy(xpath=".//*[@id='newSuccess']/h3")
		private WebElement successMessage;
	
	
		//variables
		int n=1;
		String tempRange=".//*[@id='range";
		String tempFromMonth="//*[@id='fromMonth";
		String tempFromYear="//*[@id='fromYear";
		String tempToMonth="//*[@id='toMonth";
		String tempToYear="//*[@id='toYear";
		
		String m= Integer.toString(n)+"']";
		
		String xpathRange= tempRange+m;
		String xpathFromMonth=tempFromMonth+m;
		String xpathFromYear=tempFromYear+m;
		String xpathToMonth=tempToMonth+m;
		String xpathToYear=tempToYear+m;
WebDriver web = Web.getDriver();
		
			/*WebElement range= Web.getDriver().findElement(By.xpath(xpathRange));
			WebElement fromMonth= Web.getDriver().findElement(By.xpath(xpathFromMonth));
			WebElement fromYear= Web.getDriver().findElement(By.xpath(xpathFromYear));
			WebElement toMonth= Web.getDriver().findElement(By.xpath(xpathToMonth));
			WebElement toYear= Web.getDriver().findElement(By.xpath(xpathToYear));*/
		
	
			
	LoadableComponent<?> parent;
	public  RorProcessingPage()
	 {
		
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = new HomePage();
	}
	

	public RorProcessingPage (LoadableComponent<?> parent)
	{
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = parent;
	}
	
	

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(rorPageTitle));
		
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		this.parent.get();
		HomePage Homepage = new HomePage();
		Homepage.navigateToRorProcessingPage();
				
		Reporter.logEvent(Status.PASS, "Verify if the user has been logged into the ROR processing page successfully",
				"The user has been logged in successfully and landed into ROR processing Page", true);
		
	}

public void newRorRequestOneOff(String sponsorId, int... numberOfMonths)
{
	
	
	try {
		Web.clickOnElement(newRequestButton);
		Thread.sleep(2000);
		Web.setTextToTextBox(gaIdTextBox, sponsorId);
		
		Reporter.logEvent(Status.PASS, "enter desired sponsor id for raising ROR request",
				"The sponsor has been entered plan sponsor: " +sponsorId, true);
		
	//	Web.clickOnElement(typeDropDown);
		Web.selectDropDownOption(typeDropDown, "One Off", true);
		
		//Thread.sleep(2000);
	//	Web.clickOnElement(oneOffoption);
		//Reporter.logEvent(Status.INFO, "Select desired option from type fropdown",
			//	"One Off has been selected from type option ", true);
		
		
		Web.clickOnElement(continueButton);
		
		
		Reporter.logEvent(Status.PASS, "Should land on  ROR request history page for the plan",
				"the userlands on ROR request history page ", true);
		Thread.sleep(2000);
		//Web.clickOnElement(histContinueButton);
//		Thread.sleep(2000);
//		Web.getDriver().switchTo().defaultContent();
		Web.clickOnElement(histContinueButton);
		Reporter.logEvent(Status.PASS, "Should land on page to set range for the ROR request",
				"the userlands on the range setting page ", true);
		Thread.sleep(2000);
		/*int n=1;
		String tempRange=".//*[@id='range";
		String tempFromMonth="//*[@id='fromMonth";
		String tempFromYear="//*[@id='fromYear";
		String tempToMonth="//*[@id='toMonth";
		String tempToYear="//*[@id='toYear";
		
		String m= Integer.toString(n)+"']";
		
		String xpathRange= tempRange+m;
		String xpathFromMonth=tempFromMonth+m;
		String xpathFromYear=tempFromYear+m;
		String xpathToMonth=tempToMonth+m;
		String xpathToYear=tempToYear+m;
		WebElement range= web.findElement(By.xpath(xpathRange));
		WebElement fromMonth= web.findElement(By.xpath(xpathFromMonth));
		WebElement fromYear= web.findElement(By.xpath(xpathFromYear));
		WebElement toMonth= web.findElement(By.xpath(xpathToMonth));
		WebElement toYear=web.findElement(By.xpath(xpathToYear));
		
		  String month="04";
		   int year=2008  ;
		   
		   
		   
		  
		   for(int i=1;i<=numberOfMonths.length;i++)
		   {
			   n=i;
			  Web.setTextToTextBox(range,(Integer.toString(numberOfMonths[i-1])));
			  Web.setTextToTextBox(fromMonth, month);
			  Web.setTextToTextBox(fromYear, Integer.toString(year));
			  Web.setTextToTextBox(toMonth, month);
			  int endYear=year+ ( numberOfMonths[i-1]/12);
			  Web.setTextToTextBox(toYear, Integer.toString(endYear));
			   
		   }*/
		  
		 
			   
		  Web.clickOnElement(submitButton);
		  Thread.sleep(2000);
		 
		 if(Web.isWebElementDisplayed(successMessage, true))
		  {Reporter.logEvent(Status.PASS, "Submit ROR request",
					"request has been submitted successfully", true);
		  }
		 else
		 {
			 
			 Reporter.logEvent(Status.FAIL, "Submit ROR request",
						"request has not been submitted successfully", true);
		 }
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
	public  void runRorBatchJob()
	{
		
		
		try {
			
			FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
			ShellUtils.executeShellCommand("cd ~");
			ShellUtils.executeShellCommand("bash");
			General.threadSleepInvocation(5000);                     
			ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
			General.threadSleepInvocation(5000); 
			ShellUtils.executeShellCommand(0);
			General.threadSleepInvocation(5000); 
			ShellUtils.runAndWriteShellCommandsToFile("/opt/isis/solaris/qa_test/solaris/fss_batch_java/managedAccounts/scripts/run_ror_grp_todo.csh 3 pnp");
			General.threadSleepInvocation(5000);
			Reporter.logEvent(Status.PASS, "Run RoR Batch Job", "ROR Batch Job is Completed", false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}




