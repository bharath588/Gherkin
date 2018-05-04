package aag.admin_tool;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import aag.prospect_file.Prospect_Database_Validation;
import core.framework.Globals;
import shell.utils.ShellUtils;
import aag.aag_lib.General;
import aag.aag_lib.GlobalVar;

public class BatchProcessingPage extends LoadableComponent<BatchProcessingPage>{

	@FindBy(id = "oCMenu_top2") 
	private WebElement tabAdminstration;
	@FindBy(id = "popDialog")
	private WebElement btnCreateBatchRequest;
	@FindBy(css = "select[id = 'processCode']")
	private WebElement drpdwnProcessCode;
	@FindBy(xpath = ".//*[@id='adhocRequest']//span/input")
	private WebElement txtGcId;
	@FindBy(id = "indIdList")
	private WebElement txtIndidList;
//	@FindBy(id = "nextButton") -Id changed. 27-Sep-17
	@FindBy(id = "btnSubmit")
	private WebElement btnSubmit;
	@FindBy(xpath = "//ul[@id='ui-id-2']/li/a")
	private List<WebElement> gcIdElementsList;
	@FindBy(xpath=".//span[@class='custom-combobox']/a/span[1]")
	private WebElement btnGcIdDrpDwn;
	@FindBy(xpath = "html/body/div[9]/div[1]/button")
	private WebElement btnCloseErrorBox;
	@FindBy(xpath=".//*[@id='aPending']//tr/td[2]")
	private WebElement txtGroupClientValue;
	@FindBy(xpath="//*[@class='error-message']")
	private WebElement txtErrorMessage;
	//String RequestTableClientPath= ".//*[@id='aPending']/tbody/tr/td[contains(text(),'sponsorID')]";
	static String RequestTableProcessCodePath=".//*[@id='aPending']/tbody/tr/td[contains(text(),'sponsorID')]/../td[contains(text(),'processCode')]";
	@FindBy(id = "browseText")
	private WebElement btnBrowse;
	
	HomePage homePage;
	Alert alert;
	ResultSet queryResultSet;
	public static boolean isBatchRun;
	boolean isDropdownExist;
	public static boolean createAdhocBatch;
	
	public BatchProcessingPage() {
	PageFactory.initElements(Web.getDriver(), this);
	}
	
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(btnCreateBatchRequest));
		Reporter.logEvent(Status.INFO, "Verify if the user landed in Adhoc Batch Management Page",
				"The user landed in Adhoc Batch Management Page",false);
	}


	
	@Override
	protected void load() {
	homePage=new HomePage().get();
	homePage.navigateToBatchProcessingPage();	
	Reporter.logEvent(Status.INFO, "Verify if the user has been redirected to Adhoc Batch Management page", 
			"The user has been redirected Adhoc Batch Management Page", false);
	}
	
	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * PLAN NUMBER - [TEXT BOX]
	 * CANCEL - [BUTTON]
	 * NEXT - [BUTTON]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("PROSPECT DROPDOWN")) {
			return this.drpdwnProcessCode;
		}
		
		if (fieldName.trim().equalsIgnoreCase("BTN ADHOC REQUEST")) {
			return this.btnCreateBatchRequest;
		}
		
		return null;
	}

	public void createAdhocBatchRequest(String sponsorId,boolean... isIndIdMandatory) throws SQLException, InterruptedException
	{
 		Reporter.logEvent(Status.INFO, "User Creates a New Adhoc Batch Request", 
				"Create a Adhoc Batch request with sponsor Id:"+sponsorId , true);
      
 		//lib.Web.getDriver().navigate().refresh();
 		Prospect_Database_Validation.selectSponsorforRunningProspectJob();
             	if(Web.clickOnElement(btnCreateBatchRequest))
        		Reporter.logEvent(Status.PASS,"Click on 'Create Adhoc Batch Request' button" ,
        				"The user has clicked 'Create Adhoc Batch Request' button",false);
        	else
        		Reporter.logEvent(Status.FAIL,"Click on 'Create Adhoc Batch Request' button" ,
        					"The user has NOT clicked 'Create Adhoc Batch Request' button",false);
        Thread.sleep(2000);
        
        Web.clickOnElement(btnGcIdDrpDwn);
        for(WebElement webelement : gcIdElementsList)
        {
               if(webelement.getText().equalsIgnoreCase(sponsorId))
                     webelement.click();
        }        
//      Web.setTextToTextBox(txtGcId, sponsorId);
      Thread.sleep(3000);
        Web.clickOnElement(txtIndidList);
        if(isIndIdMandatory.length > 0)
	        if(isIndIdMandatory[0] == true)
	        {     
	//	        String indId = getIndIdfromGcId(sponsorId+"%", Stock.getTestQuery("getIndIdFromGcIdQuery"));
	        	Stock.setConfigParam("No_Of_Participants", Stock.GetParameterValue("noOfParticipants").trim().toString(), true);
	        	System.out.println("No_Of_Participants "+General.getNumberOfParticipants());
	        	String indId = Stock.GetParameterValue("participants");
		        Web.setTextToTextBox(txtIndidList, indId);
	        }
	        else
	        	txtIndidList.clear();
        if(Web.clickOnElement(btnSubmit))
			Reporter.logEvent(Status.PASS, "Click on Submit button after selecting the Sponsor",
					"User clicked the submit button after selecting the sponsor from GC ID drop down", true);
		else
			Reporter.logEvent(Status.FAIL, "Click on Submit button after selecting the Sponsor",
					"User did NOT Click the submit button after selecting the sponsor from GC ID drop down", true);
        if(Web.isWebElementDisplayed(btnCloseErrorBox)){
               btnCloseErrorBox.click();
        }
// 
        }
	

	public void createManualAdhocBatchRequest(String sponsorId, String IndId,boolean... isIndIdMandatory) throws SQLException, InterruptedException
	{
 		Reporter.logEvent(Status.INFO, "User Creates a New Adhoc Batch Request", 
				"Create a Adhoc Batch request with sponsor Id:"+sponsorId , true);
 		Prospect_Database_Validation.selectSponsorforRunningProspectJob();
		lib.Web.getDriver().navigate().refresh();

             	if(Web.clickOnElement(btnCreateBatchRequest))
        		Reporter.logEvent(Status.PASS,"Click on 'Create Adhoc Batch Request' button" ,
        				"The user has clicked 'Create Adhoc Batch Request' button",false);
        	else
        		Reporter.logEvent(Status.FAIL,"Click on 'Create Adhoc Batch Request' button" ,
        					"The user has NOT clicked 'Create Adhoc Batch Request' button",false);
        Thread.sleep(2000);
        
//        Web.clickOnElement(btnGcIdDrpDwn);
//        for(WebElement webelement : gcIdElementsList)
//        {
//               if(webelement.getText().equalsIgnoreCase(sponsorId))
//                     webelement.click();
//        }        
   Web.setTextToTextBox(txtGcId, sponsorId);
      Thread.sleep(3000);
        Web.clickOnElement(txtIndidList);
        if(isIndIdMandatory.length > 0)
        if(isIndIdMandatory[0] == true)
        {      
           	Web.setTextToTextBox(txtIndidList, IndId);
        }
        else
        	txtIndidList.clear();
        if(Web.clickOnElement(btnSubmit))
			Reporter.logEvent(Status.PASS, "Click on Submit button after selecting the Sponsor",
					"User clicked the submit button after selecting the sponsor from GC ID drop down", true);
		else
			Reporter.logEvent(Status.FAIL, "Click on Submit button after selecting the Sponsor",
					"User did NOT Click the submit button after selecting the sponsor from GC ID drop down", true);
        if(Web.isWebElementDisplayed(btnCloseErrorBox)){
               btnCloseErrorBox.click();
        }
// 
        }
	
	public String getIndIdfromGcId(String gcId,String[] getIndIdFromGcIdQuery) throws SQLException
	{
		
		String indId = "";
		queryResultSet = DB.executeQuery(GlobalVar.participantDB, getIndIdFromGcIdQuery[1], gcId+"%");
		if(queryResultSet != null)
		{
			while(queryResultSet.next())
			{
				indId = queryResultSet.getString("IND_ID");
				break;
			}
				
		}
		return indId;
	}
	
	public boolean runProspectJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Prospect File", "Prospect File Validation", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./prospect.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run Prospect Batch Job", "Prospect Batch Job is Completed", false);
		if(General.verifyLog(Globals.GC_REMOTE_OUTPUT_PATH, Globals.GC_LOG_FILE_VALIDATION_MESSAGE, Globals.GC_PROSPECT_LOG_FILE_PATTERN, Globals.GC_LOG_FILE_TYPE))
		{
			Reporter.logEvent(Status.PASS, "Prospect File: Verify Prospect File has been generated", 
					"Prospect file has been generated successfully" , false);
			return true;
		}
		else
			{
				Reporter.logEvent(Status.WARNING, "Prospect File: Verify Prospect File has been generated", 
				"Prospect Files are NOT generated successfully", false);
				return false;
			}
	
	}
	
	public boolean runNegativeProspectJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Prospect File", "Prospect File Validation", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./prospect.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run Prospect Batch Job", "Prospect Batch Job is Completed", false);
		if(General.verifyLog(Globals.GC_REMOTE_OUTPUT_PATH, Globals.GC_LOG_FILE_VALIDATION_MESSAGE, Globals.GC_PROSPECT_LOG_FILE_PATTERN, Globals.GC_LOG_FILE_TYPE))
		{
			Reporter.logEvent(Status.FAIL, "Prospect File: Verify Prospect File has been generated", 
					"Prospect file has been generated successfully" , false);
			return true;
		}
		else
			{
				Reporter.logEvent(Status.PASS, "Prospect File: Verify Prospect File has been generated", 
				"Prospect Files are NOT generated successfully", false);
				return false;
			}
	
	}
	
	public void createAdhocMemberBatchRequest(String sponsorId,String... indId) throws SQLException, InterruptedException
    {
          Reporter.logEvent(Status.INFO, "User Creates a New Adhoc Member Batch Request", 
                        "Create a Adhoc Member Batch request with sponsor Id:"+sponsorId , true);
          Prospect_Database_Validation.selectSponsorforRunningProspectJob();
        //lib.Web.getDriver().navigate().refresh();

     Thread.sleep(2000);
 	if(Web.clickOnElement(btnCreateBatchRequest))
		Reporter.logEvent(Status.PASS,"Click on 'Create Adhoc Batch Request' button" ,
				"The user has clicked 'Create Adhoc Batch Request' button",false);
	else
		Reporter.logEvent(Status.FAIL,"Click on 'Create Adhoc Batch Request' button" ,
					"The user has NOT clicked 'Create Adhoc Batch Request' button",false);
 	Thread.sleep(3000);
     Web.selectDropDownOption(drpdwnProcessCode, "FEMEMBER");
     Thread.sleep(3000);
     Web.clickOnElement(btnGcIdDrpDwn);
     for(WebElement webelement : gcIdElementsList)
     {
            if(webelement.getText().equalsIgnoreCase(sponsorId))
                  webelement.click();
     }        
//   Web.setTextToTextBox(txtGcId, sponsorId);
   Thread.sleep(3000);
     
     if(Web.clickOnElement(btnSubmit))
                  Reporter.logEvent(Status.PASS, "Click on Submit button after selecting the Sponsor",
                               "User clicked the submit button after selecting the sponsor from GC ID drop down", true);
           else
                  Reporter.logEvent(Status.FAIL, "Click on Submit button after selecting the Sponsor",
                               "User did NOT Click the submit button after selecting the sponsor from GC ID drop down", true);
     if(Web.isWebElementDisplayed(btnCloseErrorBox)){
            btnCloseErrorBox.click();
     }

     }
	
	public static boolean checkRequestRaised(String sponsorID, String processCode)
	{
		
		WebElement AdhocRequestTable=Web.getDriver().findElement
               (By.xpath(RequestTableProcessCodePath.replace("sponsorID",

                               sponsorID).replaceAll("processCode", processCode)));
		
		if(Web.isWebElementDisplayed(AdhocRequestTable))
		return true;
		else 
		return false;
			

	}
	
	public boolean isErrorMessageDisplayed()
	{
		
		if(Web.isWebElementDisplayed(txtErrorMessage))
		return true;
		else 
		return false;
			
	}
	public void createOptoutRequest(String sponsorId) throws InterruptedException
	{
		Reporter.logEvent(Status.INFO, "User Creates a New Adhoc Batch Request for optout", 
				"Create a Adhoc Batch request with sponsor Id:"+sponsorId , true);

             	if(Web.clickOnElement(btnCreateBatchRequest))
        		Reporter.logEvent(Status.PASS,"Click on 'Create Adhoc Batch Request' button" ,
        				"The user has clicked 'Create Adhoc Batch Request' button",false);
        	else
        		Reporter.logEvent(Status.FAIL,"Click on 'Create Adhoc Batch Request' button" ,
        					"The user has NOT clicked 'Create Adhoc Batch Request' button",false);
        Thread.sleep(2000);
        Web.selectDropDownOption(drpdwnProcessCode, "FEOPTOUT");  
        Web.setTextToTextBox(txtGcId, sponsorId);
        btnBrowse.sendKeys("C:\\optout.txt");
        
        Web.clickOnElement(btnSubmit);
        if(Web.isWebElementDisplayed(btnCloseErrorBox)){
            btnCloseErrorBox.click();
     }
	}

	public boolean runOptoutJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Prospect File", "Prospect File Validation", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./optout.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run Optout Batch Job", "Optout Batch Job is Completed", false);
		if(General.verifyLog(Globals.GC_REMOTE_OUTPUT_PATH, Globals.GC_LOG_FILE_VALIDATION_MESSAGE, Globals.GC_OPTOUT_LOG_FILE_PATTERN, Globals.GC_LOG_FILE_TYPE))
		{
			Reporter.logEvent(Status.PASS, "Optout File: Verify Optout File has been generated", 
					"Prospect file has been generated successfully" , false);
			return true;
		}
		else
			{
				Reporter.logEvent(Status.FAIL, "Optout File: Verify Optout File has been generated", 
				"Prospect Files are NOT generated successfully", false);
				return false;
			}
	
	}
	
	public void createOptoutRequest(String sponsorId,String filePath) throws InterruptedException
	{
		Reporter.logEvent(Status.INFO, "User Creates a New Adhoc Batch Request for optout", 
				"Create a Adhoc Batch request with sponsor Id:"+sponsorId , true);

             	if(Web.clickOnElement(btnCreateBatchRequest))
        		Reporter.logEvent(Status.PASS,"Click on 'Create Adhoc Batch Request' button" ,
        				"The user has clicked 'Create Adhoc Batch Request' button",false);
        	else
        		Reporter.logEvent(Status.FAIL,"Click on 'Create Adhoc Batch Request' button" ,
        					"The user has NOT clicked 'Create Adhoc Batch Request' button",false);
        Thread.sleep(2000);
        Web.selectDropDownOption(drpdwnProcessCode, "FEOPTOUT");  
        Web.clickOnElement(btnGcIdDrpDwn);
        for(WebElement webelement : gcIdElementsList)
        {
               if(webelement.getText().equalsIgnoreCase(sponsorId))
                     webelement.click();
        } 
        btnBrowse.sendKeys(filePath);
        
        Web.clickOnElement(btnSubmit);
        
        if(Web.isWebElementDisplayed(btnCloseErrorBox)){
            btnCloseErrorBox.click();
     }
        
	}
}
