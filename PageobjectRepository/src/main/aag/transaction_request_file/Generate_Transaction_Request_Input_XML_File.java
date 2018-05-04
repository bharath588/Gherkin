package pageobjects.transaction_request_file;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lib.DB;
import lib.Reporter;
import com.aventstack.extentreports.*;
import lib.Stock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import core.framework.Globals;
import aag_lib.General;
import aag_lib.GlobalVar;

public class Generate_Transaction_Request_Input_XML_File {

	//XML Declarations
	private static DocumentBuilderFactory dbFactory =null;
	private static DocumentBuilder dBuilder = null;
	private static Document document=null;
	//private static File transactionRequestTemplateFile=new File("C:\\temp\\ma_fei_txnpr_template1.xml");
	private static File transactionRequestTemplateFile=new File(System.getProperty("user.dir")+"\\Resource\\ma_fei_txnpr_template.xml");			
	public static String transactionRequestInputFileName = "C:\\temp\\ma_fei_txnpr_"+General.getCurrentSystemDate(Globals.GC_RUN_DATE_TIMESTAMP)+Globals.GC_FILE_TYPE;
	public static File transactionRequestInputFile = new File(transactionRequestInputFileName);
	private static int fundsCount=0;
	String tcName;
	static Map<String,Map<String,String>> planWiseFundMap;
	
	//Future Allocation Variables
	public static boolean futureAllocationAvail=false;
	private static List<String> futureFundList = null;

	//Rebalance allocation Variables		
	public static String buyFundId=null;
	public static String sellFundId=null;
	public static boolean rebalanceAllocationAvail=false;
	public static Map<String,String> tempAttrMap;
	public static Map<String,Map<String,String>> tempTransMap;
	public static Map<String,Map<String,String>> rebalanaceFundAllocationMap = new LinkedHashMap<String, Map<String,String>>(); 
	public static Map<String,Map<String,String>> futureFundAllocationMap = new LinkedHashMap<String, Map<String,String>>();
	public static Map<String,String> tempRebalMap;
	public static Map<String,String> tempFutureFundMap;
	
	
	/**
	 *    This method initializes the document Builder Factory variables
	 */
	private static void xmlDocumentBuilderFactoryInitialization()
	{
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(transactionRequestTemplateFile);
			document.getDocumentElement().normalize();	
		}
		catch (ParserConfigurationException pce) { 
			pce.printStackTrace(); 
			Globals.exception=pce;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}	
		catch (IOException ioe) { 
			ioe.printStackTrace();
			Globals.exception=ioe;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}
		catch (SAXException sae) { 
			sae.printStackTrace();
			Globals.exception=sae;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}

	}

	
	/**
	 * This method creates the transaction request xml file by taking the values from the global map
	 */
	public static boolean writeDataToTransactionRequestXML()
	{
		try {
			xmlDocumentBuilderFactoryInitialization();
			tempTransMap = General.globalMultiPptMap;
			//Enter Sponsor ID from Member File
			Node sponsorNode=document.getElementsByTagName("fei_batch:Sponsor_ID").item(0);
			sponsorNode.setTextContent(Stock.GetParameterValue("plan_SponsorId"));
			List<String> planList = null;
			//Enter Batch Run Date from General Date(Previous Business Day)
			Node batchRunDateNode=document.getElementsByTagName("fei_batch:Run_Date").item(0);
			batchRunDateNode.setTextContent(General.getCurrentSystemDate(Globals.GC_BATCH_RUN_DATE_FORMAT));
			
			//create loop for enrolled participants
			for (Entry<String, Map<String, String>> entry : tempTransMap.entrySet())
			{
				planList = new LinkedList<>();
				tempAttrMap = entry.getValue();
				planList.add(tempAttrMap.get(GlobalVar.firstPlanId));
				planList.add(tempAttrMap.get(GlobalVar.secondPlanId));
				Iterator<String> planListIterator = planList.iterator();
				
				
				Node transactionPostNode =document.getElementsByTagName("fei_batch:Txn_Post").item(0);
				Element transactionNode=document.createElement("Transaction");
				Element transactionIdNode=document.createElement("TransactionId");
				//				 transactionIdNode.setTextContent(Long.toString(Transaction_Request_Database_Validation.getTransactionId()));
				transactionIdNode.setTextContent(Transaction_Request_Database_Validation.getTransactionId());
				Element rkUserIdNode = document.createElement("RkUserId");
				rkUserIdNode.setTextContent(tempAttrMap.get(GlobalVar.houseHoldId));

				transactionPostNode.appendChild(transactionNode);
				transactionNode.appendChild(transactionIdNode);
				transactionNode.appendChild(rkUserIdNode);
				Stock.setConfigParam("MemberParticipantDB", tempAttrMap.get(GlobalVar.dbInstance));
				planWiseFundMap =  new LinkedHashMap<String, Map<String,String>>();
				while(planListIterator.hasNext())
				{
				String planNumber = planListIterator.next();	
				if(planNumber != null)
				{
				futureAllocationSection(transactionNode,tempAttrMap.get(GlobalVar.houseHoldId),planNumber
						,tempAttrMap.get(GlobalVar.Individualid),tempAttrMap.get(GlobalVar.dbInstance));
				Stock.setConfigParam("MemberParticipantDB", tempAttrMap.get(GlobalVar.dbInstance));
				rebalanceAllocationSection(transactionNode,tempAttrMap.get(GlobalVar.houseHoldId),planNumber
						,tempAttrMap.get(GlobalVar.Individualid),tempAttrMap.get(GlobalVar.dbInstance));
				}

			}
				GlobalVar.planWiseFundAllocations.put(tempAttrMap.get(GlobalVar.Individualid), planWiseFundMap);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "1");				 
			DOMSource source = new DOMSource(document);
			if(futureAllocationAvail || rebalanceAllocationAvail)
			{
			StreamResult result = new StreamResult(transactionRequestInputFile);
			transformer.transform(source, result);
			return true;
			}
		}
		catch (Exception tfe) { 
			tfe.printStackTrace(); 
			Globals.exception=tfe;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}	
		return false;
	}


	/**
	 * This method writes data to create future allocation section in transaction request input file
	 * @param futureTransactionNode
	 * @param houseHoldId
	 * @param planId
	 * @param indId
	 * @param dbInst
	 */
	private static void futureAllocationSection(Node futureTransactionNode,String houseHoldId,String planId,String indId,String dbInst)
	{
		try {
			Map<String,String> futureFundMap = new LinkedHashMap<>();
			
			tempFutureFundMap = new LinkedHashMap<String, String>();
			Element futuretransactionEvent=document.createElement("TransactionEvent");
			Element futureRkAccountId=document.createElement("RkAccountId");	
			futureRkAccountId.setTextContent(planId+houseHoldId);
			Element futureAllocation = document.createElement("FutureAllocation");
			futureTransactionNode.appendChild(futuretransactionEvent);
			futuretransactionEvent.appendChild(futureRkAccountId);
			futuretransactionEvent.appendChild(futureAllocation);
			ResultSet getEligibleFundsRs = Transaction_Request_Database_Validation.getParticipantEligibleFunds(planId,indId);
			int noOfEligibleFunds=DB.getRecordSetCount(getEligibleFundsRs);				
			futureFundList= new ArrayList<String>();
			getEligibleFundsRs.first();
			System.out.println("Eligible funds: "+ noOfEligibleFunds);
			if(noOfEligibleFunds >0)
			{
			while(getEligibleFundsRs.next())
			{
				if(fundsCount==2) {
					fundsCount=0;
					break;  
				} else {

					Element futureFundAllocation = document.createElement("FundAllocation");
					Element futureFund = document.createElement("Fund");
					Element futureFundId = document.createElement("FundId");
					Element futureFundType = document.createElement("Type");
					Element futureFundName = document.createElement("Name");
					Element futureFundAllocationType = document.createElement("Allocation");
					Element futureAllocValue = document.createElement("Value");
					Element futureAllocType = document.createElement("Type");
					if(fundsCount ==0)
					{
						tempFutureFundMap.put(GlobalVar.futureFundallocationOne, getEligibleFundsRs.getString("SDIO_ID")+" Fund Percentage:  "+String.valueOf(100 - Integer.valueOf(Stock.GetParameterValue("percentageofFundAllocation"))));
						futureFundAllocationMap.put(houseHoldId, tempFutureFundMap);
						futureFundMap.put(GlobalVar.futureFundallocationOne, getEligibleFundsRs.getString("SDIO_ID"));
					}else
					{
						tempFutureFundMap.put(GlobalVar.futureFundallocationTwo, getEligibleFundsRs.getString("SDIO_ID") + " Fund Percentage:  " + Stock.GetParameterValue("percentageofFundAllocation"));
						futureFundAllocationMap.put(houseHoldId, tempFutureFundMap);
						futureFundMap.put(GlobalVar.futureFundallocationTwo, getEligibleFundsRs.getString("SDIO_ID"));
					}

					futureAllocation.appendChild(futureFundAllocation);
					futureFundAllocation.appendChild(futureFund);
					futureFund.appendChild(futureFundId);
					futureFund.appendChild(futureFundType);
					futureFund.appendChild(futureFundName);
					futureRkAccountId.setTextContent(planId+houseHoldId);
					futureFundList.add(fundsCount, getEligibleFundsRs.getString("SDIO_ID"));					
					futureFundId.setTextContent(futureFundList.get(fundsCount));
					futureFundType.setTextContent("PRIVATE");
					futureFundName.setTextContent(getEligibleFundsRs.getString("LEGAL_NAME"));		
					futureFundAllocation.appendChild(futureFundAllocationType);
					futureFundAllocationType.appendChild(futureAllocType);
					futureFundAllocationType.appendChild(futureAllocValue);
					futureAllocType.setTextContent("PERCENT");
					if(fundsCount==1)
						futureAllocValue.setTextContent(Stock.GetParameterValue("percentageofFundAllocation"));
					else
						futureAllocValue.setTextContent(String.valueOf(100 - Integer.valueOf(Stock.GetParameterValue("percentageofFundAllocation"))));					  
					//futureAllocValue.setTextContent("50");
					fundsCount++;
					futureAllocationAvail=true;
				}
			}
			planWiseFundMap.put(planId, futureFundMap);
			}
			else
				futureAllocationAvail=false;
		}catch(SQLException sqle) {
			sqle.printStackTrace(); 
			Globals.exception=sqle;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}	
		catch(Exception sqle) {
			sqle.printStackTrace(); 
			Globals.exception=sqle;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}
	}

	
	/**
	 *  This method writes data to create Rebalance Allocation Section in transaction request input file
	 * @param rebalTransactionNode
	 * @param houseHoldId
	 * @param planId
	 * @param indId
	 * @param dbInst
	 */
	private static void rebalanceAllocationSection(Node rebalTransactionNode,String houseHoldId,String planId,String indId,String dbInst)
	{
		try { 	
			Map<String,String> rebalFundMap = new LinkedHashMap<>();
			tempRebalMap = new HashMap<String,String>();
			ResultSet getEligibleFundsRs = Transaction_Request_Database_Validation.getParticipantEligibleFunds(planId,indId);
			ResultSet getExistingFundsRs = Transaction_Request_Database_Validation.getParticipantExistingFunds(indId);
			int noOfExistingFunds=DB.getRecordSetCount(getExistingFundsRs);
			int noOfEligibleFunds = DB.getRecordSetCount(getEligibleFundsRs);
			System.out.println("Existing funds: "+noOfExistingFunds);
			if(noOfEligibleFunds>0 && noOfExistingFunds>0)
			{
			while(getExistingFundsRs.first() && getEligibleFundsRs.next())		
			{
				if(fundsCount==1)
				{
					fundsCount=0;
					break;
				} else {			
					sellFundId=getExistingFundsRs.getString("SDIO_ID");
					buyFundId=getEligibleFundsRs.getString("SDIO_ID");	
					tempRebalMap.put(GlobalVar.rebalBuyFundId, buyFundId);
					tempRebalMap.put(GlobalVar.rebalSellFundId, sellFundId);
					rebalFundMap.put(GlobalVar.rebalBuyFundId, buyFundId);
					rebalFundMap.put(GlobalVar.rebalSellFundId, sellFundId);
					
					rebalanaceFundAllocationMap.put(houseHoldId, tempRebalMap);
					Element rebaltransactionEvent=document.createElement("TransactionEvent");
					Element rebalRkAccountId=document.createElement("RkAccountId");	
					Element rebalanceAllocation = document.createElement("RebalanceAllocation");
					Element exchangeRebalance =document.createElement("ExchangeRebalance");
					Element exchange=document.createElement("Exchange");
					Element sell = document.createElement("Sell");
					Element rebalSellFund =document.createElement("Fund");		  
					Element rebalSellFundId = document.createElement("FundId");
					Element rebalSellFundType = document.createElement("Type");
					Element rebalSellFundName = document.createElement("Name");
					Element rebalSellAmount=document.createElement("SellAmount");
					Element rebalSellType = document.createElement("Type");
					Element rebalSellValue = document.createElement("Value");
					Element buy = document.createElement("Buy");
					Element rebalBuyFund =document.createElement("Fund");		  
					Element rebalBuyFundId = document.createElement("FundId");
					Element rebalBuyFundType = document.createElement("Type");
					Element rebalBuyFundName = document.createElement("Name");
					Element rebalAllocationFromProceed=document.createElement("AllocationFromProceed");
					Element rebalBuyType = document.createElement("Type");
					Element rebalBuyValue = document.createElement("Value");
					rebalTransactionNode.appendChild(rebaltransactionEvent);
					rebaltransactionEvent.appendChild(rebalRkAccountId);
					rebaltransactionEvent.appendChild(rebalanceAllocation);
					rebalanceAllocation.appendChild(exchangeRebalance);
					exchangeRebalance.appendChild(exchange);
					exchange.appendChild(sell);
					sell.appendChild(rebalSellFund);
					rebalSellFund.appendChild(rebalSellFundId);
					rebalSellFund.appendChild(rebalSellFundType);
					rebalSellFund.appendChild(rebalSellFundName);
					sell.appendChild(rebalSellAmount);
					rebalSellAmount.appendChild(rebalSellType);
					rebalSellAmount.appendChild(rebalSellValue);
					exchange.appendChild(buy);
					buy.appendChild(rebalBuyFund);
					rebalBuyFund.appendChild(rebalBuyFundId);
					rebalBuyFund.appendChild(rebalBuyFundType);
					rebalBuyFund.appendChild(rebalBuyFundName);
					buy.appendChild(rebalAllocationFromProceed);
					rebalAllocationFromProceed.appendChild(rebalBuyType);
					rebalAllocationFromProceed.appendChild(rebalBuyValue);

					rebalRkAccountId.setTextContent(planId+houseHoldId);			  
					rebalSellFundId.setTextContent(sellFundId);
					rebalSellFundType.setTextContent("PRIVATE");
					rebalSellFundName.setTextContent(getExistingFundsRs.getString("NAME"));
					rebalSellType.setTextContent("PERCENT");
					rebalSellValue.setTextContent("100");			
					rebalBuyFundId.setTextContent(buyFundId);
					rebalBuyFundType.setTextContent("PRIVATE");
					rebalBuyFundName.setTextContent(getEligibleFundsRs.getString("LEGAL_NAME"));
					rebalBuyType.setTextContent("PERCENT");
					rebalBuyValue.setTextContent("100");	
					fundsCount++;
					rebalanceAllocationAvail=true;
				} 
			}
			planWiseFundMap.put(planId, rebalFundMap);
			}
			else
				rebalanceAllocationAvail=false;

		} catch(SQLException sqle) {
			sqle.printStackTrace(); 
			Globals.exception=sqle;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}
		catch(Exception sqle) {
			sqle.printStackTrace(); 
			Globals.exception=sqle;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}
	} 
	public static boolean writeDataToTransactionRequestXML_Transaction_Module(String planId, String houseHoldId, String IndId, String El_Fund, String El_Fund_Name)
	{
		try {
			xmlDocumentBuilderFactoryInitialization();
//			tempTransMap = General.globalMultiPptMap;
			//Enter Sponsor ID from Member File
			Node sponsorNode=document.getElementsByTagName("fei_batch:Sponsor_ID").item(0);
			sponsorNode.setTextContent((planId.split("-"))[0]);
			List<String> planList = null;
			//Enter Batch Run Date from General Date(Previous Business Day)
			Node batchRunDateNode=document.getElementsByTagName("fei_batch:Run_Date").item(0);
			batchRunDateNode.setTextContent(General.getCurrentSystemDate(Globals.GC_BATCH_RUN_DATE_FORMAT));
			//create loop for enrolled participants
//			for (Entry<String, Map<String, String>> entry : tempTransMap.entrySet())
//			{
				planList = new LinkedList<>();
//				tempAttrMap = entry.getValue();
				planList.add(planId);
//				planList.add(tempAttrMap.get(GlobalVar.secondPlanId));
				Iterator<String> planListIterator = planList.iterator();
				
				
				Node transactionPostNode =document.getElementsByTagName("fei_batch:Txn_Post").item(0);
				Element transactionNode=document.createElement("Transaction");
				Element transactionIdNode=document.createElement("TransactionId");
				//				 transactionIdNode.setTextContent(Long.toString(Transaction_Request_Database_Validation.getTransactionId()));
				transactionIdNode.setTextContent(Transaction_Request_Database_Validation.getTransactionId());
				Element rkUserIdNode = document.createElement("RkUserId");
				rkUserIdNode.setTextContent(houseHoldId);

				transactionPostNode.appendChild(transactionNode);
				transactionNode.appendChild(transactionIdNode);
				transactionNode.appendChild(rkUserIdNode);
				Stock.setConfigParam("MemberParticipantDB", "AumDB");
				planWiseFundMap =  new LinkedHashMap<String, Map<String,String>>();
				while(planListIterator.hasNext())
				{
				String planNumber = planListIterator.next();	
				if(planNumber != null)
				{
//				futureAllocationSection(transactionNode,tempAttrMap.get(houseHoldId),planNumber
//						,tempAttrMap.get(GlobalVar.Individualid),tempAttrMap.get("AumDB"));
				Stock.setConfigParam("MemberParticipantDB", "D_INST");
				rebalanceAllocationSection_Transaction_Module(transactionNode,houseHoldId,planNumber
						,IndId, "AumDB", El_Fund, El_Fund_Name);
				}

//			}
				GlobalVar.planWiseFundAllocations.put(IndId, planWiseFundMap);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "1");				 
			DOMSource source = new DOMSource(document);
			if(futureAllocationAvail || rebalanceAllocationAvail)
			{
				StreamResult result = new StreamResult(transactionRequestInputFile);
				transformer.transform(source, result);
				return true;
			}
		}
		catch (Exception tfe) { 
			tfe.printStackTrace(); 
			Globals.exception=tfe;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}	
		return false;
	}
	private static void rebalanceAllocationSection_Transaction_Module(Node rebalTransactionNode,String houseHoldId,String planId,String indId,String dbInst, String El_Fund, String El_Fund_Name)
	{
		try { 	
			Map<String,String> rebalFundMap = new LinkedHashMap<>();
			tempRebalMap = new HashMap<String,String>();
//			ResultSet getEligibleFundsRs = Transaction_Request_Database_Validation.getParticipantEligibleFunds(planId,indId);
			ResultSet getExistingFundsRs = Transaction_Request_Database_Validation.getParticipantExistingFunds(indId);
			int noOfExistingFunds=DB.getRecordSetCount(getExistingFundsRs);
//			int noOfEligibleFunds = DB.getRecordSetCount(getEligibleFundsRs);
			System.out.println("Existing funds: "+noOfExistingFunds);
			if(noOfExistingFunds>0)
			{
			if(getExistingFundsRs.next())		
			{
//				if(fundsCount==1)
//				{
//					fundsCount=0;
//					break;
//				} else {			
					sellFundId=getExistingFundsRs.getString("SDIO_ID");
					buyFundId=El_Fund;	
					tempRebalMap.put(GlobalVar.rebalBuyFundId, buyFundId);
					tempRebalMap.put(GlobalVar.rebalSellFundId, sellFundId);
					rebalFundMap.put(GlobalVar.rebalBuyFundId, buyFundId);
					rebalFundMap.put(GlobalVar.rebalSellFundId, sellFundId);
					
					rebalanaceFundAllocationMap.put(houseHoldId, tempRebalMap);
					Element rebaltransactionEvent=document.createElement("TransactionEvent");
					Element rebalRkAccountId=document.createElement("RkAccountId");	
					Element rebalanceAllocation = document.createElement("RebalanceAllocation");
					Element exchangeRebalance =document.createElement("ExchangeRebalance");
					Element exchange=document.createElement("Exchange");
					Element sell = document.createElement("Sell");
					Element rebalSellFund =document.createElement("Fund");		  
					Element rebalSellFundId = document.createElement("FundId");
					Element rebalSellFundType = document.createElement("Type");
					Element rebalSellFundName = document.createElement("Name");
					Element rebalSellAmount=document.createElement("SellAmount");
					Element rebalSellType = document.createElement("Type");
					Element rebalSellValue = document.createElement("Value");
					Element buy = document.createElement("Buy");
					Element rebalBuyFund =document.createElement("Fund");		  
					Element rebalBuyFundId = document.createElement("FundId");
					Element rebalBuyFundType = document.createElement("Type");
					Element rebalBuyFundName = document.createElement("Name");
					Element rebalAllocationFromProceed=document.createElement("AllocationFromProceed");
					Element rebalBuyType = document.createElement("Type");
					Element rebalBuyValue = document.createElement("Value");
					rebalTransactionNode.appendChild(rebaltransactionEvent);
					rebaltransactionEvent.appendChild(rebalRkAccountId);
					rebaltransactionEvent.appendChild(rebalanceAllocation);
					rebalanceAllocation.appendChild(exchangeRebalance);
					exchangeRebalance.appendChild(exchange);
					exchange.appendChild(sell);
					sell.appendChild(rebalSellFund);
					rebalSellFund.appendChild(rebalSellFundId);
					rebalSellFund.appendChild(rebalSellFundType);
					rebalSellFund.appendChild(rebalSellFundName);
					sell.appendChild(rebalSellAmount);
					rebalSellAmount.appendChild(rebalSellType);
					rebalSellAmount.appendChild(rebalSellValue);
					exchange.appendChild(buy);
					buy.appendChild(rebalBuyFund);
					rebalBuyFund.appendChild(rebalBuyFundId);
					rebalBuyFund.appendChild(rebalBuyFundType);
					rebalBuyFund.appendChild(rebalBuyFundName);
					buy.appendChild(rebalAllocationFromProceed);
					rebalAllocationFromProceed.appendChild(rebalBuyType);
					rebalAllocationFromProceed.appendChild(rebalBuyValue);

					rebalRkAccountId.setTextContent(planId+houseHoldId);			  
					rebalSellFundId.setTextContent(sellFundId);
					rebalSellFundType.setTextContent("PRIVATE");
					rebalSellFundName.setTextContent(getExistingFundsRs.getString("NAME"));
					rebalSellType.setTextContent("PERCENT");
					rebalSellValue.setTextContent("100");			
					rebalBuyFundId.setTextContent(buyFundId);
					rebalBuyFundType.setTextContent("PRIVATE");
					rebalBuyFundName.setTextContent(El_Fund_Name);
					rebalBuyType.setTextContent("PERCENT");
					rebalBuyValue.setTextContent("100");	
					fundsCount++;
					rebalanceAllocationAvail=true;
//				} 
			}
			planWiseFundMap.put(planId, rebalFundMap);
			}
			else
				rebalanceAllocationAvail=false;

		} catch(SQLException sqle) {
			sqle.printStackTrace(); 
			Globals.exception=sqle;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}
		catch(Exception sqle) {
			sqle.printStackTrace(); 
			Globals.exception=sqle;
			Reporter.logEvent(Status.FAIL, "Verify Transaction Input File is created", "Tranaction Input file is not created",false); 		
		}
	}
}
