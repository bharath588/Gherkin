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

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class CommonLib {

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

     // query to get the no of plans
     String[] sqlQuery = null;
     try {
            sqlQuery = Stock.getTestQuery("getParticipantInfo");
     } catch (Exception e) {
            e.printStackTrace();
     }
     sqlQuery[0] = getParticipantDBName(userName) + "DB_"+checkEnv(Stock.getConfigParam("TEST_ENV"));
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

	
	
/*
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
		xpathExist = true;
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
