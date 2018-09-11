package webservices.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import bdd_core.framework.Globals;



/**<pre>
 * This class contains utilities to work with an XML document.</pre>
 * @author krsbhr
 *
 */
public class XmlUtils {
	private static DocumentBuilderFactory dbFactory =null;
	private static DocumentBuilder dBuilder = null;
	private static Document document=null;
	private static Element rootElement;
	
	
	/**<pre>
	 * Reads the xml file and returns the root node of the document with all the depth
	 * </pre>
	 * @param fileName
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void initDocumentFactory(String fileName) throws SAXException, IOException, ParserConfigurationException
	{
		 dbFactory = DocumentBuilderFactory.newInstance();
         dBuilder = dbFactory.newDocumentBuilder();
         document = dBuilder.parse(new File(fileName));
         document.getDocumentElement().normalize();
         rootElement = document.getDocumentElement();
	}
	
	/**<pre>
	 * Reads an XML string and returns the root node of the XML string with all the depth.
	 * @param xml
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void initDocumentFactoryForXmlString(String xml) throws SAXException, IOException, ParserConfigurationException
	{
		 dbFactory = DocumentBuilderFactory.newInstance();
         dBuilder = dbFactory.newDocumentBuilder();
         InputSource is = new InputSource(new StringReader(xml));
         document = dBuilder.parse(is);
         document.getDocumentElement().normalize();
         rootElement = document.getDocumentElement();
	}
	
	/**<pre>
	 * returns the value of the inputed tag name
	 * </pre>
	 * @param tagName
	 * @return
	 */
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
	
	/**<pre>
	 * Returns the parent node of the inputed tag name which has inputed node value.
	 * </pre> 
	 * @param fileName
	 * @param nodeValue
	 * @param tagName
	 * @return
	 */
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
	
	 /**<pre>
	  * Returns the node value of the inputed tag name. Traversal of the xml file is done<br>using inputed string xpath</br></pre>
	 * @param fileName
	 * @param xpath
	 * @param tagName
	 * @return
	 */
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

	 /**<pre>
	  * Returns the node value of the inputed tag name available in inputed file</pre>
	 * @param fileName
	 * @param tagName
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
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
	 
	 /**<pre>
	  * This method returns node value of a tag under parent node.</pre>
	 * @param fileName
	 * @param nodeValue
	 * @param tagName
	 * @param tagNameTobeFound
	 * @return
	 */
	public static String getNodeValueFromParentNode(String fileName,String nodeValue,
			 String tagName,String tagNameTobeFound)
	 {
		String value = "";
		Node parentNode = getParentNode(fileName,nodeValue,tagNameTobeFound);
		Element xmlElement = (Element) parentNode;
		value = xmlElement.getElementsByTagName(tagName).item(0).getTextContent();
		return value;
	 }
	 
	 
	 /**<pre>
	  * Evaluates the existence of an xml element</pre>
	 * @param fileName
	 * @param tagName
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static boolean isElementExists(String fileName,String tagName) 
			 throws SAXException, IOException, ParserConfigurationException {
		 initDocumentFactory(Globals.GC_REMOTE_OUTPUT_PATH+"\\\\"+fileName);
		    NodeList nodeList = document.getElementsByTagName(tagName);
		    return nodeList.getLength() > 0 ? true : false;
		}
	 
	 /**<pre> Evaluates existence of an element for inputed string xpath</pre>
	 * @param fileName
	 * @param xpath
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 */
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
	
	 /**<pre> This method returns the nodelist in xml file relative to given xpath</pre>
	 * @param fileName
	 * @param xpath
	 * @return
	 */
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
	
	/**<pre>
	 * This method returns the node list available at inputed xpath.</pre>
	 * @param xml
	 * @param xpath
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 */
	 
	 
	 public static NodeList getXmlTagValues(String xml,String xpath) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException
	 {
		NodeList nodeList = null;
		 XPathFactory xPathFact = XPathFactory.newInstance();
		 XPath objXPath = xPathFact.newXPath();
		 XPathExpression xPathExp;
		initDocumentFactoryForXmlString(xml);
		 xPathExp = objXPath.compile(xpath);
		 nodeList = (NodeList) xPathExp.evaluate(document, XPathConstants.NODESET);	
		 return nodeList;
	 }
	 
}


