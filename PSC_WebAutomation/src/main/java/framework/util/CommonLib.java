package framework.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pageobjects.homepage.HomePage;

import com.aventstack.extentreports.Status;

import core.framework.Globals;


public class CommonLib {
	static ResultSet queryResultSet;
	static HomePage homePage;
	public static void HighlightElement(WebElement ele, WebDriver driver) {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"color: yellow; border: 2px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"");
		}
	}
	
	public static void enterData(WebElement ele, String value) {
		String tagName = "";
		try {
			tagName = ele.getTagName();
			if (tagName.equals("input")) {
				if (ele.getAttribute("type").equals("text")) {
					Web.setTextToTextBox(ele, value);
				} else if (ele.getAttribute("type").equals("radio")) {
					Web.clickOnElement(ele);
				}
			}
			if (tagName.equals("select")) {
				Select select = new Select(ele);
				for (WebElement opt : select.getOptions()) {
					if (Web.VerifyPartialText(value, opt.getText(), true)) {
						opt.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fillForm(WebElement parentNode, String... coLNames) {
		String tagName = "";
		try {
			for (String colNm : coLNames) {
				tagName = parentNode.findElement(By.id(colNm)).getTagName();
				if (tagName.equals("input")) {
					if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("text")) {
						Thread.sleep(500);
						Web.setTextToTextBox(
								parentNode.findElement(By.id(colNm)),
								Stock.GetParameterValue(colNm));
					} else if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("radio")) {
						Thread.sleep(500);
						Web.clickOnElement(parentNode.findElement(By.id(colNm)));
					}
				}
				if (tagName.equals("select")) {
					Select select = new Select(parentNode.findElement(By
							.id(colNm)));
					for (WebElement opt : select.getOptions()) {
						if (Web.VerifyPartialText(
								Stock.GetParameterValue(colNm), opt.getText(),
								true)) {
							opt.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean VerifyText(String inExpectedText,
			String inActualText, boolean... ignoreCase) {
		boolean isMatching = false;

		if (ignoreCase.length > 0) {
			if (ignoreCase[0]) {
				if (inExpectedText.trim().equalsIgnoreCase(inActualText.trim())) {
					isMatching = true;
				}
			} else {
				if (inExpectedText.trim().equals(inActualText.trim())) {
					isMatching = true;
				}
			}
		} else {
			if (inExpectedText.trim().equals(inActualText.trim())) {
				isMatching = true;
			}
		}
		return isMatching;
	}
	
	public static ResultSet getParticipantInfoFromDataBase(String userName)
            throws SQLException {

     String[] sqlQuery = null;
     try {
            sqlQuery = Stock.getTestQuery("getParticipantInfo");
     } catch (Exception e) {
            e.printStackTrace();
     }
     sqlQuery[0] = getUserDBName(userName) + "DB_"+checkEnv(Stock.getConfigParam("TEST_ENV"));
     ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],
                  userName.substring(0, 9));

     if (DB.getRecordSetCount(participantInfo) > 0) {
            try {
                  participantInfo.last();
            } catch (SQLException e) {
                  e.printStackTrace();
                  Reporter.logEvent(
                                com.aventstack.extentreports.Status.WARNING,
                                "Query Participant Info from DB:" + sqlQuery[0],
                                "The Query did not return any results. Please check participant test data as the appropriate data base.",
                                false);
            }
     }
     return participantInfo;
}

public static String getUserDBName(String userName) throws SQLException {

     // query to get the no of plans
     String[] sqlQuery = null;
     ResultSet participantDB = null;
     try {
            sqlQuery = Stock.getTestQuery("getPartcipantDBInfo");
     } catch (Exception e) {
            e.printStackTrace();
     }
     
     participantDB = DB.executeQuery(sqlQuery[0], sqlQuery[1],"K_"+userName);
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
     System.out.println("DATA BASE Name"+ participantDB.getString("DATABASE_INSTANCE"));
     String db = participantDB.getString("DATABASE_INSTANCE");
     if(db.equals("QASK"))
     {
    	 db="ISIS";
     }
     return db;
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

	
	
/**
 * @author smykjn<br>
 * This method returns true if xpath exist else false.
 */

public static boolean isElementExistByXpath(String xpath)
{
	boolean xpathExist = false;
	List<WebElement> webElements = Web.getDriver().findElements(By.xpath(xpath));
	if(webElements.size()==0)
	{
		xpathExist = false;
	}
	else
	{
		WebElement element = Web.getDriver().findElement(By.xpath(xpath));
		if(element.isDisplayed())
			xpathExist=true;
		else
			xpathExist=false;
	}
	return xpathExist;
}	
	
	

private static String progressBar =".//*[@id='pscSpinnerId']";    
public static void waitForProgressBar(){
         int iTimeInSecond=100;
           try{
        	   
        	   WebElement ele = Web.getDriver().findElement(By.xpath(progressBar));
        	   Web.waitForElement(ele);
                  int iCount = 0;
                  while (ele.isDisplayed()){
                     
                         if(iCount ==iTimeInSecond){
                               break;
                         }   
                         
                         System.out.println("Progress Bar displaying..");
                         Thread.sleep(1000);                       
                         iCount++;
                  }
                  
                  
           }catch(Exception e){
                  e.getMessage();
           }
           
        }

	
/**	
 * @author smykjn
 * @Objective This method switches to default plan page
 * @throws SQLException
 * @throws Exception
 */
public static void switchToDefaultPlan() throws SQLException,Exception
{
	String defaultPlan = null;
	homePage = new HomePage();
	queryResultSet = DB.executeQuery(Stock.getTestQuery("selectDefaultPlanQuery")[0],Stock.getTestQuery("selectDefaultPlanQuery")[1],
			"K_"+Stock.GetParameterValue("username"));
	while(queryResultSet.next())
	{
		defaultPlan = queryResultSet.getString("DEFAULT_GA_ID");
	}
	Web.getDriver().switchTo().defaultContent();
	homePage.searchPlanWithIdOrName(defaultPlan);
}
	
	
/**
 * @author smykjn
 * @Objective This method returns true of list is sorted in ascending order
 * @return boolean
 */

public static boolean isSortedByDescOrder(List<Double> list)
{
    boolean sorted = false; 
    if(list.size()==1)
	{
		sorted = true;
		Reporter.logEvent(Status.INFO, "Only one allocation is found so sorting validation is not required.", "", true);
	}
    else{
    for (int i = 1; i < list.size(); i++) {
    	
        if (list.get(i-1).compareTo(list.get(i)) > 0) {
        	sorted = true;}
        else{
        	sorted = false;
        	break;
        	}
    	}
    }

    return sorted;
}
	

/**
 * @author smykjn
 * @param actHeaders
 * <pre>this parameter represents List of header WebElements captured from Xpath or any locators.</pre>
 * @param expHeaders
 * <pre>This parameter represents List of expected headers that can be taken from test data source ex. Excel,XML.</pre>
 * @return boolean
 * <pre>This method returns true if all actual headers are present in exppcted header list.
 * if any of the header is missing from expHeaders the returns false.</pre>
 * @throws Exception
 * @Date 2nd-May-2017
 */
public static boolean isAllHeadersDisplayed(List<WebElement> actHeaders,List<String> expHeaders) throws Exception
{
	boolean isdisplayed = false;
	for(WebElement header : actHeaders){
		System.out.println("Actual Header is"+header.getText().replaceAll("\\s+", " ").trim());
		if(expHeaders.contains(header.getText().replaceAll(":", "").replaceAll("\\s+", " ").trim()))
		{isdisplayed = true;}
		else
		{isdisplayed = false;break;}
	}	
	return isdisplayed;
}
	
	
	
	
	
	
	
	
	

}
