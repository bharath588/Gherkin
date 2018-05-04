package aag.admin_tool;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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









import shell.utils.ShellUtils;
import aag.aag_lib.General;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class RequestBatchProcessing extends LoadableComponent<RequestBatchProcessing>{

	@FindBy(xpath = "//input[@value = 'Request Offer Letter']")
	private WebElement btnRequestOfferLetter;
	
	@FindBy(xpath = "//input[@value = 'Request Plan Sponsor Report Card']")
	private WebElement btnReportCard;
	
	@FindBy(id = "indId")
	private WebElement txtGcId;
	
	@FindBy(id = "database")
	private WebElement drpdwnGcId;
	
	LoadableComponent<?> parent;
	ResultSet queryResultSet;
	
	
	 public RequestBatchProcessing() {
		 PageFactory.initElements(Web.getDriver(), this);
		 this.parent = new HomePage();
	}
	
	 @Override
		protected void isLoaded() throws Error {
			Assert.assertTrue(Web.isWebElementDisplayed(btnRequestOfferLetter));
			Reporter.logEvent(Status.INFO, "Verify if the user landed in Adhoc Batch Management Page",
					"The user landed in Adhoc Batch Management Page",false);
		}
	
	@Override
	protected void load() {
		this.parent.get();
		new HomePage().navigateToRequestBatchPage();
	}

	
	/**
	 * <pre>This method used to create a offer letter request</pre>
	 * @param sponsorId
	 * @throws SQLException 
	 */
	public void createOfferLetterRequest(String sponsorId) throws SQLException
	{
		Web.selectDropDownOption(drpdwnGcId, getDBInstance(sponsorId));
		Web.setTextToTextBox(txtGcId,sponsorId);
		Web.clickOnElement(btnRequestOfferLetter);
	}

	public void deleteExistingNewReqinAma(String sponsorId) throws Exception
	{
		DB.executeUpdate("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")), Stock.getTestQuery("deletePendingRequest")[1]);
	}
	public String getDBInstance(String sponsorId) throws SQLException
	{
	String dbInstance = null;
	 queryResultSet = DB.executeQuery("AMADB_"+General.checkEnv(Stock.getConfigParam("TEST_ENV")),
				Stock.getTestQuery("queryToGetDBInstance")[1],
				sponsorId + "%");
		if (queryResultSet != null) {
			while (queryResultSet.next()) {
				dbInstance = 
						 queryResultSet.getString("database_instance")
								.toUpperCase();
				break;
			}
		}
		return dbInstance;
}

	public void verifyOfferLetterRequest(String sponsorId)
	{
		queryResultSet = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("verifyOffrLetterRequestInAMA")[1], sponsorId,Stock.getConfigParam(Globals.GC_COLNAME_USERID));
		
		if(DB.getRecordSetCount(queryResultSet) > 0)
		{
			Reporter.logEvent(Status.PASS, "Verify if an offer letter request has been created in database", "An offer letter request has created in database", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if an offer letter request has been created in database", "An offer letter request has created in database", false);
		}
		
	}
	
	public String selectIbbotsonPlan() throws SQLException
	{
		String planId = null;
		queryResultSet = DB.executeQuery("PNP", Stock.getTestQuery("selectIbbotsonPptQuery")[1]);
		while(queryResultSet.next())
		{
			planId = queryResultSet.getString("GA_ID");
			break;
		}
		return planId.split("-")[0];
	}
	
	public void runOfferLetterJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Offer Letter Process", "Offer Letter Process", false);
		//FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./offrlttr.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run OfferLetter Job", "Offer Letter Job is Completed", false);
	}
	
}