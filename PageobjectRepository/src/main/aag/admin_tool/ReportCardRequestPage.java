package pageobjects.admin_tool;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import shell.utils.ShellUtils;
import aag_lib.General;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class ReportCardRequestPage extends
		LoadableComponent<ReportCardRequestPage> {

	@FindBy(xpath = "//p[contains(text(),'New Request')]")
	private WebElement btnNewRequest;

	@FindBy(xpath = ".//*[@id='gaId']")
	private WebElement gaIdForReportCard;
	
	@FindBy(xpath = ".//*[@id='new']/p[2]")
	private WebElement btnContinue;
	
	@FindBy(xpath = ".//*[@id='newStep3']/table[3]//tr/td[1]/p")
	private WebElement btnContinueDateRange;
	
	LoadableComponent<?> parent;

	ResultSet queryResultSet;
	
	public ReportCardRequestPage() {
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = new HomePage();
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(btnNewRequest));
	}

	@Override
	protected void load() {
		this.parent.get();
		new HomePage().navigateToReportCardProcPage();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createNewReportCardRequest(String gaId)
	{
		Web.clickOnElement(btnNewRequest);
		Web.setTextToTextBox(gaIdForReportCard, gaId);
		Web.clickOnElement(btnContinue);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enterDateRange()
	{
		
	}
	
	public String verifyRowCreatedAfterNewRequest(String gaId) throws SQLException
	{
		String rcgt_id = null;
		
		queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("VerifyReportCardRequest")[1],gaId);
		
		if(DB.getRecordSetCount(queryResultSet) > 0)
		{
			Reporter.logEvent(Status.PASS, "Verify Setup/order request for report card only adds a record to group to do table with a NEW status", "A new request has been created in rept_card_group_todo", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify Setup/order request for report card only adds a record to group to do table with a NEW status", "A new request has not created in rept_card_group_todo", false);
		}
		
		while(queryResultSet.next())
		{
			rcgt_id = queryResultSet.getString("ID");
			break;
		}
		
		return rcgt_id;
	}
	
	public void runReportCardJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Run Report Card Job", "Report Card Processing", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./run_rept_card.ksh");
	}
	
	public void verifySuccessMessage()
	{
		
	}
}