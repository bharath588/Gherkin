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
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


/**<pre>
 * This class contains utilities to work with Web service request and response.</pre>
 * @author krsbhr
 *
 */
@SuppressWarnings("deprecation")
public class WebserviceUtil {


	public HttpClient httpClient;
	public HttpPost postRequest;
	public HttpGet getRequest;
	public HttpResponse response;
	
	/**<pre>
	 * Method used to print the SOAP Response
	 * </pre>
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

	/**<pre>
	 * It will fetch the xml file and returns the SOAPMessage
	 * </pre>
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

	/**<pre>
	 * This method executes the HttpGetrequest and returns the response as
	 * HttpResponse object
	 * </pre>
	 * @param url
	 * @param jsonRequestString
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse getResponseasJsonforPostRequest(String url, String jsonRequestString,String...headers)
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(url);
		postRequest.addHeader("Authorization", headers[0]);
		StringEntity input = new StringEntity(jsonRequestString);
		input.setContentType("application/json");
		postRequest.setEntity(input);

		System.out.println(StringEscapeUtils.unescapeJson(jsonRequestString));
		System.out.println(jsonRequestString);
		HttpResponse response = httpClient.execute(postRequest);
		return response;
	}
	
	/**<pre>
	 * This method executes the HttpGetrequest and returns the http response in
	 * json format
	 * </pre>
	 * @param url
	 * @param jsonRequestString
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse getResponseasJsonforPostRequest(String url, String jsonRequestString)
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

	/**<pre>
	 * This method takes request url and body of the post request as input
	 * and returns the post request.
	 * </pre>
	 * @param url
	 * @param jsonRequestString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public HttpPost getPostRequest(String url,String jsonRequestString) throws UnsupportedEncodingException
	{
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(url);
		StringEntity input = new StringEntity(jsonRequestString);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		return postRequest;
	}

	/**<pre>
	 * This method executes the HttpGetrequest and returns the response as
	 * HttpResponse object
	 * </pre>
	 * @param httpRequest
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse getResponse(HttpPost httpRequest) throws ClientProtocolException, IOException
	{
		httpClient = new DefaultHttpClient();
		return httpClient.execute(httpRequest);
	}

	/**<pre>
	 * This method reads the HttpResponse and returns the response as
	 * string
	 * </pre>
	 * </pre>
	 * @param response
	 * @return string response
	 */
	public String getHttpResponseAsString(HttpResponse response) {
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

	/**<pre>
	 * converts json to java object.
	 * </pre>
	 * @param json
	 * @param name
	 * @return
	 * @throws JsonSyntaxException
	 * @throws ClassNotFoundException
	 */
	public static Object convertJava(String json, String name)
			throws JsonSyntaxException, ClassNotFoundException {
		Gson gson = new Gson();
		Object obj = gson.fromJson(json, Class.forName(name));
		return obj;

	}

	/**<pre>
	 * This method executes the HttpGetrequest and returns the http response in
	 * xml format
	 * </pre>
	 * @param url
	 * @param xmlString
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse getResponseasXml(String url, String xmlString)
			throws ClientProtocolException, IOException {
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(url);
		HttpEntity entity = new ByteArrayEntity(xmlString.getBytes("UTF-8"));
		postRequest.setEntity(entity);
		response = httpClient.execute(postRequest);
		//String result = EntityUtils.toString(response.getEntity());
		return response;
	}

	/**<pre>
	 * This method executes the HttpGetrequest and returns the http response in
	 * json format
	 * </pre>
	 * @param url
	 * @param jsonRequestString
	 * @param headers
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse getResponseasJsonforGetRequest(String url, String jsonRequestString,String...headers)
            throws ClientProtocolException, IOException {
     httpClient = new DefaultHttpClient();
     getRequest = new HttpGet(url);
     response = httpClient.execute(postRequest);
     return response;
}

	/**<pre>
	 * This method executes the HttpGetrequest and returns the http response in
	 * json format
	 * </pre>
	 * @param getReq
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse getResponseasJsonforGet(HttpGet getReq) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		 httpClient = new DefaultHttpClient();
	     response = httpClient.execute(getReq);
	     return response;
	}


}