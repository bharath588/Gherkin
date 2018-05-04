package aag_lib;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.framework.Globals;



public class XmlUtils {
	private static DocumentBuilderFactory dbFactory =null;
	private static DocumentBuilder dBuilder = null;
	private static Document document=null;
	private static Element rootElement;
	
	
	public static void initDocumentFactory(String fileName) throws SAXException, IOException, ParserConfigurationException
	{
		 dbFactory = DocumentBuilderFactory.newInstance();
         dBuilder = dbFactory.newDocumentBuilder();
         document = dBuilder.parse(new File(fileName));
         document.getDocumentElement().normalize();
         rootElement = document.getDocumentElement();
	}
	
	
	public static String getString(String tagName)
	{
		NodeList elementList = rootElement.getElementsByTagName(tagName);
		if (elementList != null && elementList.getLength() > 0) {
            NodeList subList = elementList.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue(); 
            }
        }
		return null;
	}
	
	public static Node getParentNode(String fileName,String nodeValue,String tagName)
	{
		
		Node parentNode = null;
		 try 
		 {
			 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
			 NodeList nList = document.getElementsByTagName(tagName);				   
			 for (int temp = 0; temp < nList.getLength();temp++) {	        	
				 if(nList.item(temp).getTextContent().equalsIgnoreCase(nodeValue))
				 {	        		 
					 parentNode = nList.item(temp).getParentNode();					
					 break;	        	 	
				 }	        	
			 }
		 }
		 catch(Exception e) { e.printStackTrace(); }
		return parentNode;
	}
	
	 public static String getParticipantFromXpath(String fileName,String xpath,String tagName)
	 {   
		 String value = "";
		 NodeList prospectHouseHoldLst=null;
		 XPathFactory xPathFact = XPathFactory.newInstance();
		 XPath objXPath = xPathFact.newXPath();
		 XPathExpression xPathExp;
		 try {
			 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
			 xPathExp = objXPath.compile(xpath);
			 prospectHouseHoldLst = (NodeList) xPathExp.evaluate(document, XPathConstants.NODESET);	
			 Element houseHoldIdElement=(Element) prospectHouseHoldLst.item(0);
			value = houseHoldIdElement.getElementsByTagName(tagName).item(0).getTextContent();
		 }catch(Exception e ) { e.printStackTrace();}
		 return value;
	 }		

	 public static String getNodeValueFromXmlFile(String fileName,String tagName) throws SAXException, IOException, ParserConfigurationException
	 {
		 String nodeValue = " ";
		 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
		 document.getElementById(tagName);
		 
		 Node sponsorNode = document.getElementsByTagName(
	             tagName).item(0);
		 nodeValue = sponsorNode.getTextContent();
		return nodeValue;
	 }
	 
	 public static String getNodeValueFromParentNode(String fileName,String nodeValue,String tagName,String tagNameTobeFound)
	 {
		String value = "";
		Node parentNode = getParentNode(fileName,nodeValue,tagNameTobeFound);
		Element xmlElement = (Element) parentNode;
		value = xmlElement.getElementsByTagName(tagName).item(0).getTextContent();
		return value;
	 }
	 
	 
	 public static boolean isElementExists(String fileName,String tagName) throws SAXException, IOException, ParserConfigurationException {
		 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
		    NodeList nodeList = document.getElementsByTagName(tagName);
		    return nodeList.getLength() > 0 ? true : false;
		}
	 
	 public static boolean isElementExistsforXpath(String fileName,String xpath) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		 NodeList prospectHouseHoldLst=null;
		 XPathFactory xPathFact = XPathFactory.newInstance();
		 XPath objXPath = xPathFact.newXPath();
		 XPathExpression xPathExp;
		 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
		 xPathExp = objXPath.compile(xpath);
		 prospectHouseHoldLst = (NodeList) xPathExp.evaluate(document, XPathConstants.NODESET);	
		    return prospectHouseHoldLst.getLength() > 0 ? true : false;
		}
	 public static NodeList getEligibleParticipantsNodeList(String fileName,String xpath)
	 {   
		 NodeList prospectHouseHoldLst=null;
		 XPathFactory xPathFact = XPathFactory.newInstance();
		 XPath objXPath = xPathFact.newXPath();
		 XPathExpression xPathExp;
		 try {
			 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
			 xPathExp = objXPath.compile(xpath);
			 prospectHouseHoldLst = (NodeList) xPathExp.evaluate(document, XPathConstants.NODESET);			
		 }catch(Exception e ) { e.printStackTrace();}
		 return prospectHouseHoldLst;
	 }
	 public static NodeList getEligibleNodesFromXpath(String fileName,String xpath)
	 {   
		 NodeList prospectHouseHoldLst=null;
		 XPathFactory xPathFact = XPathFactory.newInstance();
		 XPath objXPath = xPathFact.newXPath();
		 XPathExpression xPathExp;
		 try {
			 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
			 xPathExp = objXPath.compile(xpath);
			 prospectHouseHoldLst = (NodeList) xPathExp.evaluate(document, XPathConstants.NODESET);			
		 }catch(Exception e ) { e.printStackTrace();}
		 return prospectHouseHoldLst;
	 }
	 
	 public static boolean isNodeExist(Element nodeElement,int index,String nodeName)
	 {
		boolean isExist = true;
		@SuppressWarnings("unused")
		String nodeValue = "";
		try{
		nodeValue = nodeElement.getElementsByTagName(nodeName).item(index).getTextContent();	
		}catch(Exception e)
		{
			isExist = false;
		}
		return isExist;
	 }
}



