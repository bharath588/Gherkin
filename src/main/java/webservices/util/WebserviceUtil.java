package webservices.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import lib.DB;
import lib.Stock;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;


import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;




//import org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


/**<pre>
 * This class contains utilities to work with Web service request and response.</pre>
 * @author krsbhr
 *
 */

public class WebserviceUtil {


	public HttpClient httpClient;
	public HttpPost postRequest;
	public HttpGet getRequest;
	public HttpResponse response;
	
	public String proj1AuthURL="http://fss-dapps1:8516/security/authenticate/participant";
	public String proj2AuthURL="http://fss-dapps1:8517/security/authenticate/participant";
	public String proj3AuthURL="http://fss-dapps1:8618/security/authenticate/participant";
	public String proj6AuthURL="http://fss-dapps1:8611/security/authenticate/participant";
	public String proj8AuthURL="http://fss-dapps1:8613/security/authenticate/participant";
	public String proj9AuthURL="http://fss-dapps1:8614/security/authenticate/participant";
	public String proj10AuthURL="http://fss-dapps1:8615/security/authenticate/participant";
	public String qabAuthURL="http://fss-dapps1:8617/security/authenticate/participant";
	
	public String proj1URL="http://fss-dapps1:8516/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";
	public String proj2URL="http://fss-dapps1:8517/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";
	public String proj3URL="http://fss-dapps1:8518/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";
	public String proj6URL="http://fss-dapps1:8611/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";
	public String proj8URL="http://fss-dapps1:8613/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";
	public String proj9URL="http://fss-dapps1:8614/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";
	public String proj10URL="http://fss-dapps1:8615/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";
	public String qabURL="http://143.199.232.101:8006/advisoryServicesComponents/rest/advisoryServices/enrollIntoMA";

	public String getAuthURL()
	{
		switch(Stock.getConfigParam("TEST_ENV"))
		{
		case "PROJ_1": return proj1AuthURL;
		case "PROJ_2": return proj2AuthURL;
		case "PROJ_3": return proj3AuthURL;
		case "PROJ_6": return proj6AuthURL;
		case "PROJ_8": return proj8AuthURL;
		case "PROJ_9": return proj9AuthURL;
		case "PROJ_10": return proj10AuthURL;
		case "QAB": return qabAuthURL;
		}
		return "";
	}
	
	public String getEnrlURL()
	{
		switch(Stock.getConfigParam("TEST_ENV"))
		{
		case "PROJ_1": return proj1URL;
		case "PROJ_2": return proj2URL;
		case "PROJ_3": return proj3URL;
		case "PROJ_6": return proj6URL;
		case "PROJ_8": return proj8URL;
		case "PROJ_9": return proj9URL;
		case "PROJ_10": return proj10URL;
		case "QAB": return qabURL;
		}
		return "";
	}

	public List<String> getSalDetails(String ind_Id, String ga_Id, String dbName) throws Exception
	{
		List<String> salDetail = new ArrayList<String>();
		ResultSet queryResultSet=DB.executeQuery(dbName, Stock.getTestQuery("getSalDetails")[1], ga_Id.split("-")[0].toString(), ind_Id, ga_Id);
		if(queryResultSet.next())
		{
			salDetail.add(queryResultSet.getString("sal_amt"));
			salDetail.add(queryResultSet.getString("encrypted_sal_amt"));
			salDetail.add(queryResultSet.getString("dft_mngd_accts_income_amt"));
		}
		System.out.println("Salary details : "+salDetail.get(0)+", "+salDetail.get(1)+", "+salDetail.get(2));
		return salDetail;
	}

	public void doSalSetup(String ind_Id, String ga_Id, String salAmt, String encryptSal, String defaultSalAmt, String dbName) throws Exception
	{
		DB.executeUpdate(dbName, Stock.getTestQuery("updateSalAmt")[1], salAmt, ga_Id.split("-")[0].toString(), ind_Id);
		DB.executeUpdate(dbName, Stock.getTestQuery("updateEncryptSalAmt")[1], encryptSal, ga_Id.split("-")[0].toString(), ind_Id);
		DB.executeUpdate(dbName, Stock.getTestQuery("doSalAmtSetup")[1], defaultSalAmt, ga_Id);
	}
	
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
		httpClient = HttpClientBuilder.create().build();
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
		//httpClient = new DefaultHttpClient();
		httpClient = HttpClientBuilder.create().build();
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
		//httpClient = new DefaultHttpClient();
				httpClient = HttpClientBuilder.create().build();
		postRequest = new HttpPost(url);
		StringEntity input = new StringEntity(jsonRequestString);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		return postRequest;
	}

	/**<pre>
	 * This method takes request url and Credentials as input
	 * and returns the post request.
	 * </pre>
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public HttpResponse getPostRequestWithCredentials(String url, String uName, String passwd) throws ClientProtocolException, IOException
	{
		HttpHost targetHost = new HttpHost("api.feitest.com",-1,"https");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(uName, passwd));

		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		// Add AuthCache to the execution context
		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		context.setAttribute("preemptive-auth", authCache);
		
		HttpClient client = HttpClientBuilder.create().build();
		response = client.execute(new HttpPost(url), context);
		
		return response;
	}
	
	public HttpResponse getPostRequestWithOAuth(String url, String oAuthAccessCode, String jsonRequestString) throws ClientProtocolException, IOException
	{
		HttpHost targetHost = new HttpHost("api.feitest.com",-1,"https");

		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		final HttpClientContext context = HttpClientContext.create();
		context.setAuthCache(authCache);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost httpReq=new HttpPost(url);
		StringEntity input = new StringEntity(jsonRequestString);
		input.setContentType("application/json");
		httpReq.setEntity(input);
		httpReq.addHeader("Authorization", "Bearer "+oAuthAccessCode);
		response = client.execute(httpReq, context);
		return response;
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
		//httpClient = new DefaultHttpClient();
				httpClient = HttpClientBuilder.create().build();
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
		//httpClient = new DefaultHttpClient();
				httpClient = HttpClientBuilder.create().build();
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
		//httpClient = new DefaultHttpClient();
				httpClient = HttpClientBuilder.create().build();
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
		//httpClient = new DefaultHttpClient();
				httpClient = HttpClientBuilder.create().build();
	     response = httpClient.execute(getReq);
	     return response;
	}


}