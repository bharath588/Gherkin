package aag.transaction_confirmation_file;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import lib.Reporter;
import com.aventstack.extentreports.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import aag.aag_lib.General;
import aag.aag_lib.GlobalVar;
import core.framework.Globals;

public class Transaction_Confirmation_XML_Validations {
	//XML Declarations
	private static DocumentBuilderFactory dbFactory =null;
	private static DocumentBuilder dBuilder = null;
	private static Document document=null;
	public static Map<String,String> tempAttrMap;
	public static LinkedHashMap<String, Map<String, String>> tempmultiplePptMap= new LinkedHashMap<String,Map<String,String>>();
	public static LinkedHashMap<String, Map<String, String>> multiplePptMap= new LinkedHashMap<String,Map<String,String>>();

	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
		   	FUNCTION:			txnconfDocumentBuilderFactoryInitialization()	
		    DESCRIPTION:	    This method initializes the document Builder Factory variables
		    PARAMETERS: 		None
		    RETURNS:		    None                	
		    REVISION HISTORY: 
		    ------------------------------------------------------------------------------------------------------------------------------------------------------------
		    Author : Janani     Date : 23-02-2016       
		    ------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */ 
	private static void txnconfDocumentBuilderFactoryInitialization()
	{
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();	        
			document = dBuilder.parse(General.getInputFile(Globals.GC_TRANSACTION_CONFIRMATION_FILE_NAMEPATTERN,Globals.GC_FILE_TYPE));
			document.getDocumentElement().normalize();	
		}
		catch(Exception e) {e.printStackTrace();}	
	}

	/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	   	FUNCTION:			verifyTransactionConfirmationStatus()	
	    DESCRIPTION:	    This method verifies the transaction confirmation status for future and rebalance allocation
	    PARAMETERS: 		None
	    RETURNS:		    None                	
	    REVISION HISTORY: 
	    ------------------------------------------------------------------------------------------------------------------------------------------------------------
	    Author : Janani     Date : 23-02-2016       
	    ------------------------------------------------------------------------------------------------------------------------------------------------------------
	 */ 
	static Map<String, String> planAttributesMap;
	static Map<String,Map<String, String>> tempPlanWiseFundAttrMap;
	public static boolean verifyTransactionConfirmationStatus()
	{	boolean status = true;
		txnconfDocumentBuilderFactoryInitialization();		
		try {
			XPathFactory xPathFact = XPathFactory.newInstance();
			XPath objXPath = xPathFact.newXPath();
			XPathExpression xPathExp;
			NodeList transactionNodeList=null;
			multiplePptMap=General.globalMultiPptMap;
			for (Entry<String, Map<String, String>> entry : multiplePptMap.entrySet())
			{
				tempAttrMap = entry.getValue();
				tempPlanWiseFundAttrMap = GlobalVar.planWiseFundAllocations.get(tempAttrMap.get(GlobalVar.Individualid));
				for(Entry<String,Map<String,String>> objEntryPlan : tempPlanWiseFundAttrMap.entrySet())
				{
					String planNumber = objEntryPlan.getKey();
				planAttributesMap = objEntryPlan.getValue();
				String transId = tempAttrMap.get(GlobalVar.transactionId);
				String futureFundAllocationOne = planAttributesMap.get(GlobalVar.futureFundallocationOne);
				String futureFundAllocationTwo = planAttributesMap.get(GlobalVar.futureFundallocationTwo);
				String rebalanceBuyFundId = planAttributesMap.get(GlobalVar.rebalBuyFundId);
				String rebalanceSellFundId = planAttributesMap.get(GlobalVar.rebalSellFundId);
				xPathExp = objXPath.compile("//Transaction[./TransactionId[text()='"+transId+"']]");
				transactionNodeList = (NodeList) xPathExp.evaluate(document, XPathConstants.NODESET);
				if(transactionNodeList.getLength()>0)
				{
					if(!futureFundAllocationOne.equals(null) && !futureFundAllocationTwo.equals(null))
					{
						NodeList futureTransactionEvent=null;
						xPathExp = objXPath.compile("//TransactionEvent[./FutureAllocation]/.//Status");
						futureTransactionEvent = (NodeList) xPathExp.evaluate(transactionNodeList.item(0), XPathConstants.NODESET);
						if(futureTransactionEvent.item(0).getTextContent().equalsIgnoreCase(Globals.GC_TRANS_CONF_SUCCESS_STATUS))			 
							Reporter.logEvent(Status.PASS, "Verify Transaction Confirmation Status for Future Allocation for participant \n " +
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									" and \n"+"plan id : "+planNumber+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid),
									"Future Allocation Transaction Confirmation Status for houseHold id "
									+tempAttrMap.get(GlobalVar.houseHoldId)+  " and " + "participant ind id " + tempAttrMap.get(GlobalVar.Individualid)+ " is: "+futureTransactionEvent.item(0).getTextContent(),false);
						else{
							Reporter.logEvent(Status.FAIL, "Verify Transaction Confirmation Status for Future Allocation for participant \n " +
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									" and \n"+"plan id : "+planNumber+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid),
									"Future Allocation Transaction Confirmation Status for houseHold id "
											+tempAttrMap.get(GlobalVar.houseHoldId)+  " and " + " participant ind id " + tempAttrMap.get(GlobalVar.Individualid)+ " is: "+futureTransactionEvent.item(0).getTextContent(),false);
							status=false;
						}
					}
					else{
						Reporter.logEvent(Status.FAIL, "Verify Transaction Confirmation Status for Future Allocation for participant \n " +
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									"For Plan Id :" + planNumber+ " and \n"+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid), 
								"There are NO Future Allocation Details available for Transaction Confirmation  for houseHold id "
											+tempAttrMap.get(GlobalVar.houseHoldId)+ " and " + "participant ind id " + tempAttrMap.get(GlobalVar.Individualid), false);
						status=false;
					}

					if(!rebalanceBuyFundId.equals(null) && !rebalanceSellFundId.equals(null))
					{
						NodeList rebalanceTransactionEvent=null;
						xPathExp = objXPath.compile("//TransactionEvent[./RebalanceAllocation]/.//Status");
						rebalanceTransactionEvent = (NodeList) xPathExp.evaluate(transactionNodeList.item(0), XPathConstants.NODESET);
						if(rebalanceTransactionEvent.item(0).getTextContent().equalsIgnoreCase(Globals.GC_TRANS_CONF_SUCCESS_STATUS))			 
							Reporter.logEvent(Status.PASS, "Verify Transaction Confirmation Status for Rebalance Allocation for participant \n " +
									"For Plan Id :" + planNumber+
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid),
									"Rebalance Allocation Transaction Confirmation Status for participant with \n"+
									"HouseHold Id "	+tempAttrMap.get(GlobalVar.houseHoldId)+  " and " +
									"Individual Id " + tempAttrMap.get(GlobalVar.Individualid)+ " is: "+rebalanceTransactionEvent.item(0).getTextContent(),false);
						else{
							Reporter.logEvent(Status.FAIL, "Verify Transaction Confirmation Status for Rebalance Allocation for participant \n " +
									"For Plan Id :" + planNumber+
									"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
									"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid),
									"Rebalance Allocation Transaction Confirmation Status for participant with HouseHold Id "
											+tempAttrMap.get(GlobalVar.houseHoldId)+  " and " + "Individual Id " + tempAttrMap.get(GlobalVar.Individualid)+ " is: "+rebalanceTransactionEvent.item(0).getTextContent(),false);
							status=false;
						}
					}
					else{
						Reporter.logEvent(Status.FAIL,"Verify Transaction Confirmation Status for Rebalance Allocation for participant \n " +
								"Household Id: "+tempAttrMap.get(GlobalVar.houseHoldId)+" and \n"+
								"For Plan Id :" + planNumber+
								"Individual Id: "+tempAttrMap.get(GlobalVar.Individualid), 
								"There are NO Rebalance Allocation Details available for Transaction Confirmation"
								+" for participant with HouseHold Id "
								+tempAttrMap.get(GlobalVar.houseHoldId)+ " and " + "Individual Id " + tempAttrMap.get(GlobalVar.Individualid), false);
						status=false;
					}

					NodeList transactionConfirmationStatus=null;
					xPathExp = objXPath.compile("//TransactionConfirmation/Status");
					transactionConfirmationStatus = (NodeList) xPathExp.evaluate(transactionNodeList.item(0), XPathConstants.NODESET);
					if(transactionConfirmationStatus.item(0).getTextContent().equalsIgnoreCase(Globals.GC_TRANS_CONF_SUCCESS_STATUS))			 
						Reporter.logEvent(Status.PASS, "Verify Participant Transaction Confirmation Status",
								"The Transaction Confirmation Status"
								+"for houseHold id "
								+tempAttrMap.get(GlobalVar.houseHoldId)+ " and " + " participant ind id " + tempAttrMap.get(GlobalVar.Individualid)
								+" For Plan Id :" + planNumber
								+ " is: "+transactionConfirmationStatus.item(0).getTextContent(),false);
					else{
						Reporter.logEvent(Status.FAIL, "Verify Participant Transaction Confirmation Status",
								"The Transaction Confirmation Status is: "+transactionConfirmationStatus.item(0).getTextContent(),false);	
						status=false;
					}
				}	
				else{
					Reporter.logEvent(Status.FAIL, "Verify Participant Transaction Confirmation Status",
							"The Transaction Confirmation Details are NOT available for Participant"
									+"for houseHold id "
									+tempAttrMap.get(GlobalVar.houseHoldId)+ " and " + 
									"participant ind id " + tempAttrMap.get(GlobalVar.Individualid)+
									"For Plan Id :" + planNumber,false);
					status=false;
				}

			}
		}}
		catch(Exception e ) { e.printStackTrace();} 
		return status;
	}


}
