package webservices.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


@SuppressWarnings("deprecation")
public class WebserviceUtil {
	
	
	public HttpClient httpClient;
	public HttpPost postRequest;
	public HttpGet getRequest;
	public HttpResponse response;
	/**
	 * Method used to print the SOAP Response
	 * 
	 * @throws TransformerException
	 * @throws SOAPException
	 */
	@SuppressWarnings("unused")
	private static String printSOAPResponse(SOAPMessage soapResponse)
			throws TransformerException, SOAPException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.print("\nResponse SOAP Message = ");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
		StringWriter writer = new StringWriter();
		StreamResult result1 = new StreamResult(writer);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer1 = tFactory.newTransformer();
		transformer1.transform(sourceContent, result1);
		return writer.toString();

	}

	/**
	 * It will fetch the xml file and returns the SOAPMessage
	 * 
	 * @param xml
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 */

	public static SOAPMessage getSoapMessageFromString(String xml)
			throws SOAPException, IOException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory
				.createMessage(
						new MimeHeaders(),
						new ByteArrayInputStream(xml.getBytes(Charset
								.forName("UTF-8"))));
		return message;
	}

	/**
	 * This method executes the HttpGetrequest and returns the response as
	 * HttpResponse object
	 * 
	 * @param url
	 * @param jsonRequestString
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public HttpResponse getResponseasJsonforPostRequest(String url, String jsonRequestString,String...headers)
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(url);
		StringEntity input = new StringEntity(jsonRequestString);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		
		System.out.println(StringEscapeUtils.unescapeJson(jsonRequestString));
		System.out.println(jsonRequestString);
		HttpResponse response = httpClient.execute(postRequest);
		return response;
	}

	public HttpPost getPostRequest(String url,String jsonRequestString) throws UnsupportedEncodingException
	{
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(url);
		StringEntity input = new StringEntity(jsonRequestString);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		return postRequest;
	}
	
	public HttpResponse getResponse(HttpPost httpRequest) throws ClientProtocolException, IOException
	{
		httpClient = new DefaultHttpClient();
		return httpClient.execute(httpRequest);
	}

	public HttpResponse getResponseasJsonforGetRequest(String url, String jsonRequestString,String...headers)
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		getRequest = new HttpGet(url);
		response = httpClient.execute(postRequest);
		return response;
	}
	
	
	public String getHttpResponseAsString(HttpResponse response) {
		/*if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}*/
		String responseString = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String output;
		System.out.println("Output from Server .... \n");
		try {
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				responseString = responseString + output;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseString;
	}

	public static Object convertJava(String json, String name)
			throws JsonSyntaxException, ClassNotFoundException {
		Gson gson = new Gson();
		// Response response = (Response) gson.fromJson(json,
		// Class.forName(name));
		Object obj = gson.fromJson(json, Class.forName(name));
		return obj;

	}

	public HttpResponse getResponseasXml(String url, String xmlString)
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(url);
		// String xml = "<xml>xxxx</xml>";
		// StringEntity stringEntity = new StringEntity(xmlString);
		HttpEntity entity = new ByteArrayEntity(xmlString.getBytes("UTF-8"));
		postRequest.setEntity(entity);
		response = httpClient.execute(postRequest);
		String result = EntityUtils.toString(response.getEntity());
		return response;
	}
}
