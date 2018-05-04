package pageobjects.admin_tool;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.xml.sax.SAXException;

import shell.utils.SftpUtils;
import shell.utils.ShellUtils;
import aag_lib.General;
import aag_lib.XmlUtils;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class DuplicateTasStatementPage extends LoadableComponent<DuplicateTasStatementPage>{

	@FindBy(css = "input[id = 'userId']")
	private WebElement txtIndId;
	
	@FindBy(css = "input[id = 'planId']")
	private WebElement txtGaId;
	
	@FindBy(css = "input[id = 'WELKIT']")
	private WebElement radioBtnWelcome;
	
	@FindBy(css = "input[id = 'ANLSTMT']")
	private WebElement radioBtnAnnual;
	
	@FindBy(xpath = ".//*[@value='Request Statement']")
	private WebElement btnReqStmt;
	
	
	ResultSet queryResultSet;
	
	public DuplicateTasStatementPage()
	{
		PageFactory.initElements(Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(btnReqStmt));
	}


	@Override
	protected void load() {
		// TODO Auto-generated method stub
		new HomePage().get();
		new HomePage().navigateToDuplicateTsr();
	}
	
	public void createDuplicateTasStatement(String indId,String gaid,String typeOfStmt) throws InterruptedException
	{
		Web.setTextToTextBox(txtIndId, indId);
		Web.setTextToTextBox(txtGaId, gaid);
		if(typeOfStmt.equalsIgnoreCase("Annual"))
		{
			radioBtnAnnual.click();
		}else{
			radioBtnWelcome.click();
		}
		Web.clickOnElement(btnReqStmt);
		Thread.sleep(2000);
	}
	
	
	public void verifyRequestCreatedInDB(String gaId,String indId) throws SQLException
	{
		queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("verifyDuplicateTsr")[1], gaId,indId);
		Date creationDate=null;
		if(DB.getRecordSetCount(queryResultSet) > 0)
		{
			Reporter.logEvent(Status.PASS, "Verify the isis_feed_batch_todo table for the participant after the request submission for duplicate tas csr", "Record got created for : "+ gaId +" "+indId, false);
			while(queryResultSet.next())
			{
				creationDate = queryResultSet.getDate("CREATION_DPDATE_TIME");
				break;
			}
		}
		
		if(creationDate.equals(Calendar.getInstance().getTime()))
		{
			Reporter.logEvent(Status.PASS, "Verify if the date created is displaying the current date", "The cureent date is displayed" + creationDate, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if the date created is displaying the current date", "The cureent date is not displayed" + creationDate, false);
		}
		
	}
	
	public void runAnnualKitJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Run Annual kit Job", "Run Annual Kit Batch", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./annual_statement_batch.ksh");
	}
	static String annualKitFileName = null;
	
	public void verifySuccessFulAnnualKitJob(String gaId,String indId)
	{
 annualKitFileName = verifyAnnualKitXmlGenerated();
		
		
		queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("verifyDuplicateTsr")[1], gaId,indId);
		
		if(DB.getRecordSetCount(queryResultSet) == 0)
		{
			Reporter.logEvent(Status.PASS, "Verify the isis_feed_batch_todo table for the participant after the batch is executed", "The isis_feed_batch_todo record is not present", false);
			
		}else{
			Reporter.logEvent(Status.FAIL, "Verify the isis_feed_batch_todo table for the participant after the batch is executed", "The isis_feed_batch_todo record is still present after the batch has executed", false);
		}
	}
	
	public String verifyAnnualKitXmlGenerated()
	{
		File downloadedFile = null;
		/*if (General.verifyLog(Globals.GC_REMOTE_OUTPUT_PATH,
				Globals.GC_LOG_FILE_VALIDATION_MESSAGE,
				"runAnnualStatement_",
				Globals.GC_LOG_FILE_TYPE)) {*/
			// Download Prospect File to Local Directory
			downloadedFile = SftpUtils.download_File(
					Globals.GC_REMOTE_OUTPUT_DIRECTORY,
					Globals.GC_REMOTE_OUTPUT_PATH,
					"ANNUALSTMT.",
							 Globals.GC_FILE_TYPE,
					Globals.GC_LOCAL_TEMP_DIRECTORY);

			if (General.getInputFile(
					"ANNUALSTMT.",Globals.GC_FILE_TYPE).exists())
				Reporter.logEvent(
						Status.PASS,
						"VerifyAnnual Kit xml generated successfully",
						" Annual Kit geneerated successfully",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"VerifyAnnual Kit xml generated successfully",
						" Annual Kit not geneerated successfully",
						false);
		//}
		return downloadedFile.getName();
	}
	
	public  void runWelcomeKitJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Run Welcome kit Job", "Run Welcome Kit Batch", false);
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./welcome_kit_batch.ksh");
	}
	
	
	public  String verifySuccessFulWelcomeKitJob()
	{
		File downloadedFile = null;
		if (General.verifyLog(Globals.GC_REMOTE_OUTPUT_PATH,
				Globals.GC_LOG_FILE_VALIDATION_MESSAGE,
				"runAnnualStatement_",
				Globals.GC_LOG_FILE_TYPE)) {
			// Download Prospect File to Local Directory
			downloadedFile = SftpUtils.download_File(
					Globals.GC_REMOTE_OUTPUT_DIRECTORY,
					Globals.GC_REMOTE_OUTPUT_PATH,
					"WELCOMEKIT.",
							 Globals.GC_FILE_TYPE,
					Globals.GC_LOCAL_TEMP_DIRECTORY);

			if (General.getInputFile(
					"WELCOMEKIT.",Globals.GC_FILE_TYPE).exists())
				Reporter.logEvent(
						Status.PASS,
						"Verify Welcome Kit xml generated successfully",
						" Welcome Kit geneerated successfully",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify Welcome Kit xml generated successfully",
						" Annual Kit not geneerated successfully",
						false);
		}
		return downloadedFile.getName();
	}
	
	public void verifyAnnualKitXmlSchema() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException
	{
		if(XmlUtils.isElementExistsforXpath(annualKitFileName, "//RetirementNeed[@InflationType = 'Now']"))
		{
			Reporter.logEvent(Status.PASS, "Verify the UserCase Investor RetirementNeed element details in the welcomekit xml", "The investor retirement need details are displayed", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify the UserCase Investor RetirementNeed element details in the welcomekit xml", "The investor retirement need details are not displayed", false);
		}
	}


	
}
