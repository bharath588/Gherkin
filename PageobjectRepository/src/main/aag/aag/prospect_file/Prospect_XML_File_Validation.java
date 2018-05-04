package aag.prospect_file;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import lib.Stock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import aag.enrollcancel_file.EnrollOrCancel_Database_Validation;
import aag.aag_lib.General;
import aag.aag_lib.GlobalVar;
import aag.aag_lib.XmlUtils;
import core.framework.Globals;

public class Prospect_XML_File_Validation {
	//XML Declarations
	private static File folder=new File(Globals.GC_LOCAL_TEMP_DIRECTORY);
	private static DocumentBuilderFactory dbFactory =null;
	private static DocumentBuilder dBuilder = null;
	private static Document document=null;
	
	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			documentBuilderFactoryInitialization()	
    DESCRIPTION:	    This method initializes the document Builder Factory variables
    PARAMETERS: 		None
    RETURNS:		    None                	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Janani     Date : 07-01-2016       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/ public static void prospectDocumentBuilderFactoryInitialization()
	{
	 try {
		 	dbFactory = DocumentBuilderFactory.newInstance();
	        dBuilder = dbFactory.newDocumentBuilder();
	        document = dBuilder.parse(General.getInputFile(Globals.GC_PROSPECT_FILE_NAMEPATTERN,
	        		Stock.GetParameterValue("plan_SponsorId")+Globals.GC_FILE_TYPE));
	        document.getDocumentElement().normalize();	
	 }
	 catch(Exception e) {e.printStackTrace();}	
	}
	

	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
				FUNCTION:			getActiveProspectsNodeList()	
				DESCRIPTION:	    This method returns NodeList of Active / InActive Prospects i.e who does not have Termination date
				PARAMETERS: 		None
				RETURNS:			Node List of Active Prospects               	
				REVISION HISTORY: 
				------------------------------------------------------------------------------------------------------------------------------------------------------------
				Author : Janani     Date : 14-01-2016       
				------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */ public static NodeList getActiveProspectsNodeList(boolean... isForMultiplePlanOnly)
	 {   
		 NodeList prospectHouseHoldLst=null;
		 XPathFactory xPathFact = XPathFactory.newInstance();
		 XPath objXPath = xPathFact.newXPath();
		 XPathExpression xPathExp;
		 try {
			 prospectDocumentBuilderFactoryInitialization();
			 if(isForMultiplePlanOnly[0] == true)
			 {
			 xPathExp = objXPath.compile("//Household[./*//EligibilityStatus[not(text()='TERMINATED')]]//Client[count(Account) = 2]/..");
			 }else{
		//		xPathExp = objXPath.compile("(//Client[count(Account)=1])/..");
		 xPathExp = objXPath.compile("//Household[./*//EligibilityStatus[not(text()='TERMINATED')]]"); 
			 }
			 prospectHouseHoldLst = (NodeList) xPathExp.evaluate(document, XPathConstants.NODESET);			
		 }catch(Exception e ) {
			 e.printStackTrace();}
		 return prospectHouseHoldLst;
	 }
	 
	 


	/**
	 * <pre>This method stores the household id for multiple participants in a global map
	 * based on the number mentioned in configuration sheet</pre>
	 * 
	 * @return
	 */
	
	public static LinkedHashMap<String, Map<String, String>> getProspectHouseHoldID()
	{
		int numberOfppts;
		NodeList prospectHouseHoldLst=null;
		String houseHoldId=null;
		String planID;
		String sponsorID;
		String[] houseHoldDetailDB= new String[2];
		numberOfppts = General.getNumberOfParticipants();
		LinkedHashMap<String, Map<String, String>> participantMap = new LinkedHashMap<String, Map<String,String>>();
		LinkedHashMap<String,String> attributesMap = null;
		prospectHouseHoldLst = getActiveProspectsNodeList(General.isMultiplePlanOnly());
		if(prospectHouseHoldLst!=null && prospectHouseHoldLst.getLength()>0) {
			try {
				for(int iCount = 0 ; iCount < numberOfppts ; iCount++)
				{
					List<String> valueList = new ArrayList<String>();
					attributesMap = new LinkedHashMap<String,String>();
				Element houseHoldIdElement=(Element) prospectHouseHoldLst.item(iCount);	
				houseHoldId=houseHoldIdElement.getElementsByTagName("HouseholdId").item(0).getTextContent();
				attributesMap.put(GlobalVar.houseHoldId, houseHoldId);
				planID=houseHoldIdElement.getElementsByTagName("PlanId").item(0).getTextContent();
				
				attributesMap.put(GlobalVar.firstPlanId, planID);
				
				if(XmlUtils.isNodeExist(houseHoldIdElement, 1, "PlanId"))
				{
					planID=houseHoldIdElement.getElementsByTagName("PlanId").item(1).getTextContent();
					attributesMap.put(GlobalVar.secondPlanId, planID);
				}else{
					attributesMap.put(GlobalVar.secondPlanId, null);
				}
				sponsorID=houseHoldIdElement.getElementsByTagName("EmployerId").item(0).getTextContent();
				attributesMap.put(GlobalVar.sponsorId, sponsorID);
				houseHoldDetailDB = EnrollOrCancel_Database_Validation.getParticipantsDetailsFromHouseHoldId(houseHoldId);
				valueList.add(houseHoldDetailDB[0]);
				attributesMap.put(GlobalVar.Individualid, houseHoldDetailDB[0]);
				valueList.add(houseHoldDetailDB[1]);
				attributesMap.put(GlobalVar.dbInstance, houseHoldDetailDB[1]);
				participantMap.put(houseHoldId, attributesMap);
				Stock.setConfigParam("MemberParticipantDB", houseHoldDetailDB[1],true);
				}
				General.globalMultiPptMap = participantMap;
			} catch(Exception e )
			{ 
				e.printStackTrace();
			}
		}
		return participantMap;
	}		
	
	
	
	

		/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
		FUNCTION:			getProspectHouseHoldID()	
		DESCRIPTION:	    This method retrieves the HouseholdID from ProspectXML File
		PARAMETERS: 		None
		RETURNS:		    String - HouseHold ID                	
		REVISION HISTORY: 
		------------------------------------------------------------------------------------------------------------------------------------------------------------
		Author : Janani     Date : 01-22-2016       
		------------------------------------------------------------------------------------------------------------------------------------------------------------
		 */
}
