package pageobjects.admin_tool;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.openqa.selenium.support.ui.Select;

import shell.utils.ShellUtils;
import aag_lib.General;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class ThreeStrikePage extends LoadableComponent<ThreeStrikePage>{

    @FindBy(css = "input[value = 'Order New Strike']")
    private WebElement btnOrderNewStrike;
    
    @FindBy(xpath=".//input[@name='groupingContextId']")
    private WebElement inputGaId;
    
    
    @FindBy(xpath=".//td[@class='attention']/span")
    private WebElement viewStrikeProcessing;
    
    @FindBy(xpath=".//select[@name='selectedGroupIndex']")
    private WebElement manageGroup;
    

    @FindBy(xpath=".//*[@value='Manage Batch']")
    private WebElement btnManageBatch;
    

    @FindBy(xpath=".//*[@id='strike1DatePicker']")
    private WebElement inputDatePicker;
    
    @FindBy(xpath=".//*[@value='Create Baseline']")
    private WebElement btnCreateBaseline;
 
    @FindBy(xpath=".//*[@value='Submit']")
    private WebElement btnSubmit;
    
    
    @FindBy(xpath=" .//*[@value='Recalculate Dates']")
    private WebElement btnRecalcDates;
   
    
    @FindBy(xpath=".//*[@id='grpOption']//tr[@class='odd']/td[1]")
    private WebElement strikeId;
  
    @FindBy(xpath=".//*[@class='attention']/a")
    private WebElement lnkRefresh;
    
   
    
    @FindBy(xpath=".//*[@value='Continue']")
    private WebElement btnContinue;
    
    LoadableComponent<?> parent;
    ResultSet queryResultSet;
    String IndId;
    
    public ThreeStrikePage() {
         
           PageFactory.initElements(Web.getDriver(), this);
           this.parent = new HomePage();
    }
    
    public ThreeStrikePage(LoadableComponent<?> parent)
    {
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = parent;
		//PageFactory.initElements(Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		
		Assert.assertTrue(Web.isWebElementDisplayed(btnOrderNewStrike));
	}

    @Override
    protected void load() {
    	
    
    	
    	this.parent.get();
		HomePage Homepage = new HomePage();
		Homepage.navigateToThreeStrikePage();
    	 
           
    }

    
    public void orderNewStrike(String GaId)
    {
    	Web.setTextToTextBox(inputGaId, GaId);
    	Web.clickOnElement(btnOrderNewStrike);
    	
    	//verify strike is submitted
    }
    
    public void runBaselineBatch()
    {
    	
    		
    		
    		try {
    			
    			Reporter.logEvent(Status.INFO, "Baseline batch", "Run baseline batch", false);
    			FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
    			ShellUtils.executeShellCommand("cd ~");
    			ShellUtils.executeShellCommand("bash");
    			General.threadSleepInvocation(5000);                     
    			ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
    			General.threadSleepInvocation(5000); 
    			ShellUtils.executeShellCommand(0);
    			General.threadSleepInvocation(5000); 
    			ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
    			ShellUtils.runAndWriteShellCommandsToFile("./strikebaseline.ksh");
    			General.threadSleepInvocation(5000);
    			Reporter.logEvent(Status.PASS, "Run Strike Baseline Batch Job", "Strike Baseline Batch Job is Completed", false);
    			
    			//Refresh page
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     		
    }
    
    public void runStrike1Batch()
    {
    	
    		
    		
    		try {
    			
    			Reporter.logEvent(Status.INFO, "Strike 1 batch", "Run Strike1 batch", false);
    			FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
    			ShellUtils.executeShellCommand("cd ~");
    			ShellUtils.executeShellCommand("bash");
    			General.threadSleepInvocation(5000);                     
    			ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
    			General.threadSleepInvocation(5000); 
    			ShellUtils.executeShellCommand(0);
    			General.threadSleepInvocation(5000); 
    			ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
    			ShellUtils.runAndWriteShellCommandsToFile("./strike1.ksh");
    			General.threadSleepInvocation(5000);
    			Reporter.logEvent(Status.PASS, "Run Strike1 Batch Job", "Strike1 Batch Job is Completed", false);
    			
    			//Refresh page
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     		
    }
    
    public void runStrike2Batch()
    {
    	
    		
    		
    		try {
    			
    			Reporter.logEvent(Status.INFO, "Strike 1 batch", "Run Strike1 batch", false);
    			FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
    			ShellUtils.executeShellCommand("cd ~");
    			ShellUtils.executeShellCommand("bash");
    			General.threadSleepInvocation(5000);                     
    			ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
    			General.threadSleepInvocation(5000); 
    			ShellUtils.executeShellCommand(0);
    			General.threadSleepInvocation(5000); 
    			ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
    			ShellUtils.runAndWriteShellCommandsToFile("./strike2.ksh");
    			General.threadSleepInvocation(5000);
    			Reporter.logEvent(Status.PASS, "Run Strike1 Batch Job", "Strike1 Batch Job is Completed", false);
    			
    			//Refresh page
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     		
    }
    
    
    public void runEnrolBatch()
    {
    	
    		
    		
    		try {
    			
    			Reporter.logEvent(Status.INFO, "Strike 1 batch", "Run Strike1 batch", false);
    			FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
    			ShellUtils.executeShellCommand("cd ~");
    			ShellUtils.executeShellCommand("bash");
    			General.threadSleepInvocation(5000);                     
    			ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
    			General.threadSleepInvocation(5000); 
    			ShellUtils.executeShellCommand(0);
    			General.threadSleepInvocation(5000); 
    			ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
    			ShellUtils.runAndWriteShellCommandsToFile("./strikeenroll.ksh");
    			General.threadSleepInvocation(5000);
    			Reporter.logEvent(Status.PASS, "Run Strike Enroll Batch Job", "Strike Enroll Batch Job is Completed", false);
    			
    			//Refresh page
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     		
    }
    
    public void runStrike3Batch()
    {
    
    			try {
    			
    			Reporter.logEvent(Status.INFO, "Strike 1 batch", "Run Strike1 batch", false);
    			FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
    			ShellUtils.executeShellCommand("cd ~");
    			ShellUtils.executeShellCommand("bash");
    			General.threadSleepInvocation(5000);                     
    			ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
    			General.threadSleepInvocation(5000); 
    			ShellUtils.executeShellCommand(0);
    			General.threadSleepInvocation(5000); 
    			ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
    			ShellUtils.runAndWriteShellCommandsToFile("./strike3.ksh");
    			General.threadSleepInvocation(5000);
    			Reporter.logEvent(Status.PASS, "Run Strike Enroll Batch Job", "Strike Enroll Batch Job is Completed", false);
    			
    			//Refresh page
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     		
    }
    
	public void selectManageGroup()
	{
		
		Web.selectDropnDownOptionAsIndex(manageGroup, "0");
		Web.clickOnElement(btnManageBatch);
		
		
		
	}
	
	public void createBaseline()
	{
		Web.waitForElement(inputDatePicker);
		
		Web.clickOnElement(btnRecalcDates);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Web.waitForElement(btnCreateBaseline);
		//String dateString= inputDatePicker.getAttribute("value");
		
		Web.clickOnElement(btnCreateBaseline);	
		
		//report
	
	}
	
	public void submit()
	{
		Web.waitForElement(btnSubmit);
		Web.clickOnElement(btnSubmit);
		Web.waitForElement(btnContinue);
		Web.clickOnElement(btnContinue);
	}
	
	public void Refresh()
	{
		Web.clickOnElement(lnkRefresh);
	}
	
	public String verifyStrikeId()
	{
		Web.waitForElement(lnkRefresh);
		Web.clickOnElement(viewStrikeProcessing);
		
		String strkId= strikeId.getText();
		
		
		return strkId;
		//report
		
	}
	
	public void udpateEffdates(String strikeId)
	{
		try {
			DB.executeUpdate("AMADB", Stock.getTestQuery("updateEffdates")[1],strikeId);
			  Reporter.logEvent(Status.INFO, "update strike effdates",
                      "the strike effdates is set to current date", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public boolean checkDpdateStrike1(String strikeId) {
		
		 
         queryResultSet = DB.executeQuery("AMADB",
                                         Stock.getTestQuery("checkDpdateStrike1")[1], strikeId);
         if (DB.getRecordSetCount(queryResultSet) > 0) {
                         return true;
         } else {
                         return false;
         }
}
	 
		
	 public boolean checkDpdateStrike2(String strikeId) {
		
		 
         queryResultSet = DB.executeQuery("AMADB",
                                         Stock.getTestQuery("checkDpdateStrike2")[1], strikeId);
         if (DB.getRecordSetCount(queryResultSet) > 0) {
                         return true;
         } else {
                         return false;
         }
}
	 
	 
		
	 public boolean checkDpdateStrikeEnroll(String strikeId) {
		
		 
      queryResultSet = DB.executeQuery("AMADB",
                                      Stock.getTestQuery("checkDpdateStrikeEnroll")[1], strikeId);
      if (DB.getRecordSetCount(queryResultSet) > 0) {
                      return true;
      } else {
                      return false;
      }
}
	 
	 public boolean checkDpdateStrike3(String strikeId) {
			
		 
	      queryResultSet = DB.executeQuery("AMADB",
	                                      Stock.getTestQuery("checkDpdateStrike3")[1], strikeId);
	      if (DB.getRecordSetCount(queryResultSet) > 0) {
	                      return true;
	      } else {
	                      return false;
	      }
	}
	 
	 public boolean checkPartServStrike(String gaId) {
			
		 
	      queryResultSet = DB.executeQuery(Globals.dbNameAlias,
	                                      Stock.getTestQuery("checkPartServStrike")[1],gaId);
	      if (DB.getRecordSetCount(queryResultSet) > 0) {
	    	  
	                      return true;
	      } else {
	                      return false;
	      }
	}
	 
	 public String getIndId(String gaId) throws SQLException {
         queryResultSet = DB.executeQuery(Globals.dbNameAlias,
                                         Stock.getTestQuery("checkPartServStrike")[1], gaId);
        
         while (queryResultSet.next()) {
                                      IndId = queryResultSet.getString("IND_ID");
                         break;
         }
		return IndId;
	 }
	 
	 public boolean checkMngdEvent(String IndId) {
			
		 
	      queryResultSet = DB.executeQuery(Globals.dbNameAlias,
	                                      Stock.getTestQuery("checkMngdEvent")[1],IndId);
	      if (DB.getRecordSetCount(queryResultSet) > 0) {
	    	  
	                      return true;
	      } else {
	                      return false;
	      }
	}
	 
	 public boolean checkEventTable(String IndId) {
			
		 
	      queryResultSet = DB.executeQuery(Globals.dbNameAlias,
	                                      Stock.getTestQuery("checkEventTable")[1],IndId);
	      if (DB.getRecordSetCount(queryResultSet) > 0) {
	    	  
	                      return true;
	      } else {
	                      return false;
	      }
	}
	 
	 public boolean checkIsisFeed(String IndId) {
			
		 
	      queryResultSet = DB.executeQuery(Globals.dbNameAlias,
	                                      Stock.getTestQuery("checkIsisFeed")[1],IndId);
	      if (DB.getRecordSetCount(queryResultSet) > 0) {
	    	  
	                      return true;
	      } else {
	                      return false;
	      }
	}
	 
	 public boolean checkInvoptAlloc(String IndId) {
			
		 
	      queryResultSet = DB.executeQuery(Globals.dbNameAlias,
	                                      Stock.getTestQuery("checkInvoptAlloc")[1],IndId);
	      if (DB.getRecordSetCount(queryResultSet) > 0) {
	    	  
	                      return false;
	      } else {
	                      return true;
	      }
	}
	 
	 public boolean checkTrfTable(String IndId) {
			
		 
	      queryResultSet = DB.executeQuery(Globals.dbNameAlias,
	                                      Stock.getTestQuery("checkTrfTable")[1],IndId);
	      if (DB.getRecordSetCount(queryResultSet) > 0) {
	    	  
	                      return false;
	      } else {
	                      return true;
	      }
	}
}

