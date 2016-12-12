package appUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.bcel.generic.RETURN;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import pageobjects.general.LeftNavigationBar;
import core.framework.Globals;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

public class Common {
	
	/**
	 * <pre>
	 * Method to return the no of plans associated to a user from db
	 * 
	 * @return noOfPlans
	 */
	// For Sponsor
	public static final String GC_DEFAULT_SPONSER = "Empower";
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	public static WebElement lblUserName;
	@FindBy(name="legacyFeatureIframe") public static WebElement iframeLegacyFeature;
	private static String userName = "";
	static String userFromDatasheet = null;
	public static ResultSet getParticipantInfoFromDB(String ssn) {

		// query to get the no of plans
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("getParticipantInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				ssn);

		if (DB.getRecordSetCount(participantInfo) > 0) {
			try {
				participantInfo.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant Info from DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		}
		return participantInfo;
	}

	public static ResultSet getParticipantInfoFromDataBase(String ssn)
			throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		try {
			sqlQuery = Stock.getTestQuery("getParticipantInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlQuery[0] = getParticipantDBName(ssn) + "DB_"+checkEnv(Stock.getConfigParam("TEST_ENV"));
		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],
				ssn.substring(0, 9));

		if (DB.getRecordSetCount(participantInfo) > 0) {
			try {
				participantInfo.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant Info from DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		}
		return participantInfo;
	}

	public static String getParticipantDBName(String userName) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;

		ResultSet participantDB = null;

		try {
			sqlQuery = Stock.getTestQuery("getPartcipantDBInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantDB = DB.executeQuery(sqlQuery[0], sqlQuery[1],userName);
		if (DB.getRecordSetCount(participantDB) > 0) {
			try {
				participantDB.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}

		}
		System.out.println("DATA BASE Name"
				+ participantDB.getString("database_instance"));
		return participantDB.getString("database_instance");
	}

	public static String getParticipantID(String ssn) throws SQLException {

		// query to get the no of plans
		String[] sqlQuery = null;
		ResultSet participantID = null;

		try {
			sqlQuery = Stock.getTestQuery("getParticipantID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

		if (DB.getRecordSetCount(participantID) > 0) {
			try {
				participantID.last();
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(
						Status.WARNING,
						"Query Participant DB:" + sqlQuery[0],
						"The Query did not return any results. Please check participant test data as the appropriate data base.",
						false);
			}
		} else {
			try {
				sqlQuery = Stock.getTestQuery("getParticipantIDfromDiffDBISIS");
			} catch (Exception e) {
				e.printStackTrace();
			}

			participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

			if (DB.getRecordSetCount(participantID) > 0) {
				try {
					participantID.last();
				} catch (SQLException e) {
					e.printStackTrace();
					Reporter.logEvent(
							Status.WARNING,
							"Query Participant DB:" + sqlQuery[0],
							"The Query did not return any results. Please check participant test data as the appropriate data base.",
							false);
				}
			} else {
				try {
					sqlQuery = Stock.getTestQuery("getParticipantIDfromDiffDB");
				} catch (Exception e) {
					e.printStackTrace();
				}

				participantID = DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);

				if (DB.getRecordSetCount(participantID) > 0) {
					try {
						participantID.last();
					} catch (SQLException e) {
						e.printStackTrace();
						Reporter.logEvent(
								Status.WARNING,
								"Query Participant DB:" + sqlQuery[0],
								"The Query did not return any results. Please check participant test data as the appropriate data base.",
								false);
					}
				}
			}
		}
		System.out.println("ID is " + participantID.getString("ID"));
		return participantID.getString("ID");
	}

	public static boolean isCurrentSponser(String sponserName) {
		boolean status = false;

		if (lib.Stock.globalTestdata.containsKey("ACCUCODE")
				&& lib.Stock.GetParameterValue("AccuCode") != null) {
			if (sponserName
					.equalsIgnoreCase("Southwest Airlines Retirement Plans")) {
				sponserName = "SWA";
			}
			if (sponserName.equalsIgnoreCase("Empower Retirement")) {
				sponserName = "Apple";
			}
			if (lib.Stock.GetParameterValue("AccuCode").equalsIgnoreCase(
					sponserName)) {
				status = true;
			} else {
				status = false;
			}
		}

		else {
			if (GC_DEFAULT_SPONSER.equalsIgnoreCase(sponserName))
				status = true;
			else
				status = false;
		}
		return status;
	}

	public static String getSponser() {

		String sponser = null;
		if (lib.Stock.globalTestdata.containsKey("ACCUCODE")) {
			if (lib.Stock.GetParameterValue("AccuCode") != null) {
				sponser = lib.Stock.GetParameterValue("AccuCode");
			} else {
				sponser = GC_DEFAULT_SPONSER;

			}
		} else {
			sponser = GC_DEFAULT_SPONSER;

		}
		return sponser;
	}

	public static String getUserNameFromDB() {

		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = null;
		try {
			strUserInfo = Common.getParticipantInfoFromDataBase(ssn.substring(
					0, ssn.length() - 3));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String userFromDatasheet = null;
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userFromDatasheet;
	}

	public static boolean verifyStringIsInNumberFormat(String value) {
		int number;
		boolean lblDisplayed;
		try {

			number = Integer.parseInt(value);
			lblDisplayed = true;
		} catch (NumberFormatException ex) {
			lblDisplayed = false;
		}
		return lblDisplayed;
	}
	public static String checkEnv(String envName) {
		if (envName.contains("PROJ")) {
			return Globals.DB_TYPE.get("PROJ");
		}
		if (envName.contains("QA")) {
			return Globals.DB_TYPE.get("QA");
		}
		if (envName.contains("PROD")) {
			return Globals.DB_TYPE.get("PROD");
		}
		return null;
	}
	public static boolean verifyLoggedInUserIsSame() {
		PageFactory.initElements(lib.Web.webdriver, Common.class);
		boolean lblDisplayed=false;
		String userLogedIn="";
		ResultSet strUserInfo=null;
	String ssn = Stock.GetParameterValue("userName");
	
	
	if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
	{
		userFromDatasheet=Stock.GetParameterValue("lblUserName");
	}
	else if (!userName.equalsIgnoreCase(ssn.trim())){
	
	try {
		strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	
	try {
		userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
				+ strUserInfo.getString("LAST_NAME");
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	if(Web.isWebElementDisplayed(lblUserName)){
	 userLogedIn = lblUserName.getText();
	}
	/*String sponser = this.lblSponser.getAttribute("Alt");
	if (sponser.isEmpty()) {
		sponser = Common.GC_DEFAULT_SPONSER;
	}*/
	if (!userName.equalsIgnoreCase(ssn.trim())){
		 if (userFromDatasheet.equalsIgnoreCase(userLogedIn)){
		lblDisplayed=true;
		 }
		 userName = ssn.trim();
	}else{
		 if (userFromDatasheet.equalsIgnoreCase(userLogedIn)){
		lblDisplayed=true;
		 }
	}
	
	return lblDisplayed;
	}
	

    private static String progressBar ="//*[@class='loader']";
/*
    * @Author :- Siddartha 
     * @Date  :- 17 - Oct -2016
    * @ Method:- 
     */
    
      public static void waitForProgressBar(){
    	  int iTimeInSecond=100;
           try{
                  int iCount = 0;
                  while (FindElement(progressBar).isDisplayed()){
                         if(iCount ==iTimeInSecond){
                               break;
                         }   
                         
                         System.out.println("Progress Bar displaying..");
                         Thread.sleep(1000);                       
                         iCount++;
                  }
                  
           }catch(Exception e){
                  
           }
           
        }
        
        public static WebElement FindElement(String sElement){
           return lib.Web.webdriver.findElement(By.xpath(sElement));
        }
        public static boolean isAlerPresent(){
            try{
                   Web.webdriver.switchTo().alert();
                   return true;
            }catch(NoAlertPresentException ex){
                   return false;
            }
            
      }
      public  static void HandlePopAlert(){
            try{
                   Alert alert = Web.webdriver.switchTo().alert();
                   String sPopText = alert.getText();
                   System.out.println(sPopText);
                   alert.accept();
            }catch(Exception e){
            
            }
            
      }

      public  static void handlePageToLoad(String pageName){
          try{
                 LeftNavigationBar lftbar=new LeftNavigationBar();
                 lftbar.clickNavigationLink("Rate Of Return");
                 waitForProgressBar();
                 Web.waitForPageToLoad(Web.webdriver);
                 lftbar.clickNavigationLink(pageName);
                 waitForProgressBar();
                 Web.waitForPageToLoad(Web.webdriver);
          }catch(Exception e){
          
          }
          
    }
      public static boolean switchToLegacyFutureFrame(){
          try{
        	  Web.webdriver.switchTo().defaultContent();
        	  Web.waitForElement(iframeLegacyFeature);
        	  if(Web.isWebElementDisplayed(iframeLegacyFeature)){
        	  Web.webdriver.switchTo().frame(iframeLegacyFeature);
        	         	  }
        	  return true;
          }  
          catch(Exception e){
                 return false;
          }
          
    }
}
