package aag.member_file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;

import com.aventstack.extentreports.*;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;








import aag.enrollcancel_file.Generate_EnrollOrCancel_Input_XML_File;
import shell.utils.ShellUtils;
import aag.aag_lib.General;
import aag.aag_lib.GlobalVar;
import aag.aag_lib.XmlUtils;
import core.framework.Globals;

public class Member_XML_File_Validation {

	//XML Declarations
		private static DocumentBuilderFactory dbFactory =null;
		private static DocumentBuilder dBuilder = null;
		private static Document document=null;
		public static Map<String,String> tempMap;
		public ArrayList<String> indIdList;
	/**
	 *  This method initializes the document Builder Factory variables
	 */
	private static void memberDocumentBuilderFactoryInitialization()
	{
	 try {
		 	dbFactory = DocumentBuilderFactory.newInstance();
	        dBuilder = dbFactory.newDocumentBuilder();	        
	        document = dBuilder.parse(General.getInputFile(Globals.GC_MEMBER_FILE_NAMEPATTERN,Globals.GC_FILE_TYPE));
	        document.getDocumentElement().normalize();	
	 }
	 catch(Exception e) {e.printStackTrace();}	
	}

	/**
	 * This method retrieves the participant details based on House Hold Id from the member file
	 * @param houseHoldId
	 * @return
	 */
	public static Node getParentHouseholdIdNodes(String houseHoldId)
	{
		
		Node parentNode = null;
		 try 
		 {
			 memberDocumentBuilderFactoryInitialization();
			 NodeList nList = document.getElementsByTagName("HouseholdId");				   
			 for (int temp = 0; temp < nList.getLength();temp++) {	        	
				 if(nList.item(temp).getTextContent().equalsIgnoreCase(houseHoldId))
				 {	        		 
					 parentNode = nList.item(temp).getParentNode();					
					 break;	        	 	
				 }	        	
			 }
		 }
		 catch(Exception e) { e.printStackTrace(); }
		return parentNode;
	}
	
	/**
	 * This method validates whether the member is enrolled or not
	 * @param enrollCancelStatus
	 * @return
	 */

	public static boolean validateMemberFileMultiplePpt(String enrollCancelStatus)
	{
		List<String> planList = new LinkedList<>();
		Iterator<String> planListIterator ;
		int indexCount = 0;
		List<Node> parentNodeList= new ArrayList<Node>();
		 boolean participantEnrolled=false;
		for (Entry<String, Map<String, String>> entry : Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.entrySet())
		{
			tempMap = entry.getValue();
			String houseHoldId = tempMap.get(GlobalVar.houseHoldId);
			parentNodeList.add(Member_XML_File_Validation.getParentHouseholdIdNodes(houseHoldId));	
		}
		
		if(enrollCancelStatus.equalsIgnoreCase(Globals.GC_ENROLL_STATUS))
		{
		for(Node tempNode : parentNodeList)
		{
			participantEnrolled = false;
			if(tempNode!=null)
			  {
				 NodeList childNode=tempNode.getChildNodes();	
				 Element e = (Element)tempNode;
				 
				  String houseHoldId = childNode.item(0).getTextContent();
				  planList.add(Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.get(houseHoldId).get(GlobalVar.firstPlanId));
				  planList.add(Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.get(houseHoldId).get(GlobalVar.secondPlanId));
				  planListIterator = planList.iterator();
				  while(planListIterator.hasNext())
				  {
					  String planNumber = planListIterator.next();
					  if(planNumber!= null)
					  {
						  if(e.getElementsByTagName("PlanId").item(indexCount).getTextContent().equalsIgnoreCase(planNumber))
						  {
				  participantEnrolled=true;
				  Reporter.logEvent(Status.PASS, "Member File: Verify if Enrolled Participant is displayed",
						  "The participant has been enrolled and displayed in the Member File: \n"
						  + "\n"+"Participant HouseHold Id: " +houseHoldId
						  + "\n"+"Participant Individual Id: "+(Generate_EnrollOrCancel_Input_XML_File.multiplePptMap.get(houseHoldId)).get(GlobalVar.Individualid)
						  +"\n"+"and plan ID : "+planNumber, false);			  
			  }
					  }
					  indexCount++;
//			else
//			  {
//				  Reporter.logEvent(Status.FAIL, "Member File: Verify if Enrolled Participant is displayed",
//						  "The participant has been enrolled and not displayed in the Member File: \n", false);	 
//			  }
		}
				  planList.removeAll(planList);
				  indexCount=0;
		}
		}
		}else if(enrollCancelStatus.equalsIgnoreCase(Globals.GC_CANCEL_STATUS))
		{
			for(Node tempNode : parentNodeList)
			{
				if(tempNode == null)
				{
				 Reporter.logEvent(Status.PASS, "Member File: Verify if Cancelled participant details IS NOT displayed"
					  		+ "\n" + "in member file",
							  "The Cancelled Participant(s)\n "
					  		+Generate_EnrollOrCancel_Input_XML_File.getSpecifiedAttribute(GlobalVar.houseHoldId)+" is/are Unenrolled and NOT displayed in Member File\n", false);	
		}
			}
	}
		return participantEnrolled;
}
	
	public static void runMemberJob() throws IOException
	{
		Reporter.logEvent(Status.INFO, "Prospect File", "Member File Validation", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);                     
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);   
		General.threadSleepInvocation(5000); 
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000); 
		ShellUtils.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./member.ksh");
	}
	
}