
package pageobject.PLMC;
import generallib.General;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lib.DB;
import lib.Reporter;
import lib.Stock;
import org.w3c.dom.Document;
import com.aventstack.extentreports.Status;
import core.framework.Globals;

/**
 * @author smykjn
 *
 */
public class PLMC_PLSM_Master_Cklist_Query {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/PLMC_PLSM_Master_Cklist_Query";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String gaid_responce;
	String gaid_db;
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	
	
	public void setServiceParameters() throws SQLException	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for PLMC_PLSM_Master_Cklist_Query service", this.queryString.replaceAll("&", "\n"), false);
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			URL obj = new URL(serviceURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			//print result
			System.out.println(response.toString());
			con.getContent();
			PrintWriter pw = new PrintWriter("response.txt");
			pw.println(response.toString().replaceAll("<></>", ""));
			pw.close();

			 BufferedReader br = new BufferedReader(new FileReader("response.txt"));
			 String line;
			 while((line = br.readLine()) != null)
			 {
			     System.out.println(line);
			 }
			 br.close();
			 
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File("response.txt"));
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run PLMC_PLSM_Master_Cklist_Query service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run PLMC_PLSM_Master_Cklist_Query service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			gaid_responce = doc.getElementsByTagName("GROUP_ACCOUNT_GA_ID_0").item(0).getTextContent();
			Reporter.logEvent(Status.INFO, "Values From Response: ", "GROUP_ACCOUNT_GA_ID_0: "+gaid_responce+
					"\nGROUP_ACCOUNT_PLAN_NAME_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_PLAN_NAME_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_RECEIVED_DATE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_RECEIVED_DATE_0").item(0).getTextContent()+
					"\nGROUP_INFO_MAILED_DATE_0: "+ doc.getElementsByTagName("GROUP_INFO_MAILED_DATE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_PSC_DATE_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_PSC_DATE_0").item(0).getTextContent()+
					"\nGROUP_INFO_CC_DATE_0: "+ doc.getElementsByTagName("GROUP_INFO_CC_DATE_0").item(0).getTextContent()+
					"\nSELECTION_IRS_CODE_0: "+ doc.getElementsByTagName("SELECTION_IRS_CODE_0").item(0).getTextContent()+
					"\nSELECTION_SPL_HANDLING_CODE_0: "+ doc.getElementsByTagName("SELECTION_SPL_HANDLING_CODE_0").item(0).getTextContent()+
					"\nGROUP_ACCOUNT_QA_USER_ID_0: "+ doc.getElementsByTagName("GROUP_ACCOUNT_QA_USER_ID_0").item(0).getTextContent()+
					"\nSELECTION_MEDIA_TYPE_0: "+ doc.getElementsByTagName("SELECTION_MEDIA_TYPE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("PLMC_PLSM_Master_Cklist_Query")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS,"Validate records exist in DB.","Records exist in DB.",false);
			while(queryResultSet.next()){
				gaid_db= queryResultSet.getString("ID");
				if(gaid_db.equals(gaid_responce)){
					Reporter.logEvent(Status.PASS,"Validate response from DB.","\nFrom response:"+gaid_responce+"\n"
							+ "From DB:"+gaid_db, false);
					break;
				}
			}
		}else{
			Reporter.logEvent(Status.FAIL,"Validate records exist in DB.","Records do not exist in DB.",false);
		}
	}
}
