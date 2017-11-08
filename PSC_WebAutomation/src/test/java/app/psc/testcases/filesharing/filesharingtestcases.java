/*package app.psc.testcases.filesharing;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import pageobjects.fileSharing.FileSharing;
import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;

public class filesharingtestcases {
	
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	public FileSharing filesharing;
	HomePage homePage;
	
	
	
	
	@BeforeClass
	public void ReportInit() {
		Reporter.initializeModule(this.getClass().getName());
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage()
								.getName(), testCase.getName());
	}

	
	*//** This testcase validates the refresh functionality of the refresh element
	 * @throws Exception 
	 *//*
	@Test(dataProvider = "setData")
	public void TC001_FileSharing_Validate_the_refresh_button(int itr,Map<String, String> testdata) throws Exception
	{				
		try {
			Reporter.initializeReportForTC(itr, "Verify users with correct access verifies refresh view","DDTC-286");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the refresh functionality of the refresh element" + ":" +"Positive flow", false);
			filesharing = new FileSharing();
			filesharing.get();
			filesharing.validating_refresh_element();
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		} finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void TC002_FileSharing_Doc_Search(int itr,Map<String, String> testdata) throws Exception
	{				
		try {
			Reporter.initializeReportForTC(itr, "Verify users with correct access can search documents","DDTC-282");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the document search functionality" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			filesharing.doc_search_functionality();
			
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		} finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}	
	@Test(dataProvider = "setData")
	public void TC003_SIT_PSC_FileSharing_06_TC052_Upload_UI_Updates_Upload_File_Page(int itr,Map<String, String> testdata) throws Exception
	{				
		try {
			Reporter.initializeReportForTC(itr, "Display asterisks next to required fields and Disable upload button until all required fields are completed ","DDTC-396");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the fields in uplaod new document" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			filesharing.click_on_upload();
			filesharing.upload_mandatory_field_check();
			filesharing.enable_of_upload_btn();
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
      }
	
	@Test(dataProvider = "setData")
	public void TC004_SIT_TC001_Check_virus_in_files_during_upload(int itr,Map<String, String> testdata) throws Exception
	{				
		try {
			Reporter.initializeReportForTC(itr, "Checking for error message on trying to upload a virus file","DDTC-292");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the error message on trying to upload a virus file" + ":" +"Negative flow", false);
			filesharing = new FileSharing().get();
			filesharing.checkForVirusOnUpload();
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	} 
	
	@Test(dataProvider="setData")
	public void TC005_SIT_PSC_FileSharing_06_TC049_UploadNew(int itr,Map<String, String> testdata) throws Exception
	{
		try
		{
			Reporter.initializeReportForTC(itr, "Validation while uploading new document","DDTC-392");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates uploading of a new file" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			filesharing.refreshOnCancel();
			if(filesharing.uploadNewDoc_Validation())
			{
				Reporter.logEvent(Status.PASS,"Checking for various validations on upload of document","Successfull validation and upload of file",false);
			}
			else
			{
				Reporter.logEvent(Status.FAIL,"Checking for various validations on upload of document","Unsuccessfull validation and upload of file",false);
			}
			//filesharing.enable_of_upload_btn();
			//filesharing.refreshOnCancel();
		    filesharing.validationAfterUpload();
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
}


	
	@Test(dataProvider="setData")
	public void TC006_SIT_PSC_FileSharing_06_TC002_UploadLargeFiles(int itr,Map<String, String> testdata) throws Exception
	{
		try
		{
			Reporter.initializeReportForTC(itr, "Uploading large files","DDTC-346");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates uploading of large files" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			filesharing.uploadingLargeFiles();
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
	@Test(dataProvider="setData")
	public void TC007_Screen_element_filter_and_more(int itr,Map<String, String> testdata) throws Exception
	{
		try
		{
			Reporter.initializeReportForTC(itr, "Screen element filter and more filter option","DDTC-315");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates different filter options available" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			filesharing.moreFilterOptions();
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	@Test(dataProvider="setData")
	public void TC009_viewAndDeleteAccessCheck(int itr,Map<String, String> testdata) throws Exception
	{
		try
		{
			Reporter.initializeReportForTC(itr, "View and delete access","DDTC-239");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates different types of acess for view and delete functionality" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			homePage = new HomePage();
			LoginPage loginpage = new LoginPage();
			//filesharing.checkingAccessInAuditorFolder();
			String folderName = Stock.GetParameterValue("FolderName");
			String action = Stock.GetParameterValue("Action");
			boolean isAccess = filesharing.validateFolderActions(folderName,action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is not granted",false);
			
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username2"),
							Stock.GetParameterValue("password2")});
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.PASS,"Checking if access is given", "Access is not given",false);
			homePage.logoutPSC();
				
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider="setData")
	public void TC008_AccessCheck(int itr,Map<String, String> testdata) throws Exception
	{
		try
		{
			Reporter.initializeReportForTC(itr, "Checking for Screen element Folders- View upload delete access","DDTC-387");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the different actions against different folders" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			//filesharing.checkingAccessInAuditorFolder();
			String folderName = Stock.GetParameterValue("FolderName");
			String action = Stock.GetParameterValue("Action");
			filesharing.validateFolderActions(folderName,action);
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider="setData")
	public void TC010_uploadAccessCheck(int itr,Map<String, String> testdata) throws Exception
	{
		try
		{
			Reporter.initializeReportForTC(itr, "Checking for the upload access","DDTC-278");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates different upload access" + ":" +"Positive flow", false);
			filesharing = new FileSharing().get();
			homePage = new HomePage();
			LoginPage loginpage = new LoginPage();
			//filesharing.checkingAccessInAuditorFolder();
			String folderName = Stock.GetParameterValue("FolderName");
			String action = Stock.GetParameterValue("Action");
			boolean isAccess = filesharing.validateFolderActions(folderName,action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is not granted",false);
			
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username2"),
							Stock.GetParameterValue("password2")});
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.PASS,"Checking if access is given", "Access is not given",false);
			homePage.logoutPSC();
				
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider="setData")
    public void TC011_viewDocInfo(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for the view document info action with users of different access","DDTC-323");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the view document info action for users with different types of access" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
         homePage = new HomePage();
			LoginPage loginpage = new LoginPage();
			//filesharing.checkingAccessInAuditorFolder();
			String folderName = Stock.GetParameterValue("FolderName");
			String action = Stock.GetParameterValue("Action");
			boolean isAccess = filesharing.validateFolderActions(folderName,action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is not granted",false);
			
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username2"),
							Stock.GetParameterValue("password2")});
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if access is given", "Access is not given",false);
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username3"),
							Stock.GetParameterValue("password3")});
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if access is given", "Access is not given",false);
			homePage.logoutPSC();
				
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider="setData")
    public void TC012_screenElementSortBy(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for different filter types","DDTC-382");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the different filter types" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
         filesharing.checkingSorting(Web.returnElements(filesharing, "CATEGORY_LIST"),
        		 Web.returnElement(filesharing,"CATEGORY_HEADER"));
         filesharing.checkingSorting(Web.returnElements(filesharing, "DOCUMENT_NAME_LIST"),
        		 Web.returnElement(filesharing,"DOCUMENT_NAME_HEADER"));
         filesharing.checkingSorting(Web.returnElements(filesharing, "TYPE_LIST"),
        		 Web.returnElement(filesharing,"TYPE_HEADER"));
         filesharing.checkingSorting(Web.returnElements(filesharing, "MODIFIED_BY_LIST"),
        		 Web.returnElement(filesharing,"MODIFIED_BY_HEADER"));
         filesharing.checkingSorting(Web.returnElements(filesharing, "MODIFIED_DATE_LIST"),
        		 Web.returnElement(filesharing,"MODIFIED_DATE_HEADER"));
         filesharing.checkingSorting(Web.returnElements(filesharing, "DIVISION_LIST"),
        		 Web.returnElement(filesharing,"DIVISION_HEADER"));
        }
      catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
    }

	@Test(dataProvider="setData")
    public void TC013_deleteDocWithDiffAccess(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for the delete document action with users of different access","DDTC-175");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the delete document action for users with different types of view access" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
         homePage = new HomePage();
			LoginPage loginpage = new LoginPage();
			//filesharing.checkingAccessInAuditorFolder();
			String folderName = Stock.GetParameterValue("FolderName");
			String action = Stock.GetParameterValue("Action");
			boolean isAccess = filesharing.validateFolderActions(folderName,action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is not granted",false);
			
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username2"),
							Stock.GetParameterValue("password2")});
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.PASS,"Checking if access is given", "Access is not given",false);
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username3"),
							Stock.GetParameterValue("password3")});
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.FAIL,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.PASS,"Checking if access is given", "Access is not given",false);
			homePage.logoutPSC();
				
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider="setData")
	public void TC014_CheckFolderWithoutAccess(int itr,Map<String, String> testdata) throws Exception
	{
		try
		{
			Reporter.initializeReportForTC(itr, "Screen element folder without access","DDTC-264");
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates access to different folder if there or not" + ":" +"Negative flow", false);
			filesharing = new FileSharing().get();
			homePage = new HomePage();
			homePage = new HomePage();
			LoginPage loginpage = new LoginPage();
			filesharing.checkingAccessInAuditorFolder();
			String folderName = Stock.GetParameterValue("FolderName");
			String action = Stock.GetParameterValue("Action");
			boolean isAccess = filesharing.validateFolderActions(folderName,action);
			if(isAccess)
				Reporter.logEvent(Status.FAIL,"Checking if access is granted and able to view the folder",
						"Access is granted and able to view the folder",true);
			else
				Reporter.logEvent(Status.PASS,"Checking if access is granted and able to view the folder",""
						+ "Access is not granted and not able to view the folder",false);
			homePage.logoutPSC();
				
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
 				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider="setData")
    public void TC015_uploadnewversionAccessCheck(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for the upload new version action with users of different access","DDTC-6");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the upload new version action for users with different types of access" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
         homePage = new HomePage();
         //UserVerificationPage userVerificationPage = new UserVerificationPage();
			LoginPage loginpage = new LoginPage();
			//filesharing.checkingAccessInAuditorFolder();
			String folderName = Stock.GetParameterValue("FolderName");
			String action = Stock.GetParameterValue("Action");
			boolean isAccess = filesharing.validateFolderActions(folderName,action);
			if(isAccess)
				Reporter.logEvent(Status.FAIL,"Checking if upload access is granted","Upload access is granted",false);
			else
				Reporter.logEvent(Status.PASS,"Checking if upload access is granted","Upload access is not granted",false);
			
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username2"),
							Stock.GetParameterValue("password2")});
			//userVerificationPage.performVerification(Stock.GetParameterValue("userVerificationEmail"));
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if upload access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if upload access is given", "Access is not given",false);
			homePage.logoutPSC();
			loginpage.submitLoginCredentials(
					new String[]{Stock.GetParameterValue("username3"),
							Stock.GetParameterValue("password3")});
			homePage.navigateToProvidedPage("File sharing","","");
			isAccess = filesharing.validateFolderActions(folderName, action);
			if(isAccess)
				Reporter.logEvent(Status.PASS,"Checking if access is granted","Access is granted",false);
			else
				Reporter.logEvent(Status.FAIL,"Checking if access is given", "Access is not given",false);
			homePage.logoutPSC();
				
		}
		catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
}
	
	@Test(dataProvider="setData")
    public void TC016_browserCompatibilityCheck(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for compatibility with different browsers","DDTC-56");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the browser compatibility with different available browsers" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
         filesharing.differentFolderSearch();
        }
      catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
}
	
	@Test(dataProvider="setData")
    public void TC017_pagination(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for compatibility with different browsers","DDTC-56");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the browser compatibility with different available browsers" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
         filesharing.pagingValidation();
        }
      catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
}
	
	
	@Test(dataProvider="setData")
    public void TC018_accessCheckForFileSharing(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for compatibility with different browsers","DDTC-56");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the browser compatibility with different available browsers" + ":" +"Positive flow", false);
         LoginPage login =new LoginPage();
        homePage = new HomePage(new LoginPage(), false, new String[] {
				Stock.GetParameterValue("username"),
				Stock.GetParameterValue("password") }).get();
        homePage.get();
        String[] queryData=Stock.getTestQuery("getTransactionCodeAvailability");
        if(homePage.isTxnCode(queryData,"1ISIS","EMFIAR"))
        {
        Reporter.logEvent(Status.INFO,"if the user has the transaction code enabled.","Transaction code is enabled.",false);
        }
        }
      catch (Error ae)
      {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
		}
		catch(Exception e)
      {
			e.printStackTrace();
		}
		finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
     }
	
	@Test(dataProvider="setData")
    public void TC020_SIT_PSC_FileSharing_07_TC058_Check_Menu_Activity_User_File_Sharing_uploadDocumnet(int itr,Map<String, String> testdata) throws Exception
    {
		ResultSet tempResultset;
     try
        {
         Reporter.initializeReportForTC(itr,"Checking for row count on successfull upload","DDTC-214");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the row count on upload new action" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
	     tempResultset = DB.executeQuery(Stock.getTestQuery("deleteCount")[0],Stock.getTestQuery("deleteCount")[1]);
	     int count = DB.getRecordSetCount(tempResultset);
	     System.out.println("count before:"+count);
	     filesharing.click_on_upload();
	     filesharing.sending_file_to_browse();
	     filesharing.uploading_new_file();
	     tempResultset = DB.executeQuery(Stock.getTestQuery("deleteCount")[0],Stock.getTestQuery("deleteCount")[1]);
	     int count1 = DB.getRecordSetCount(tempResultset);
	     System.out.println("count after"+count1);
	     if (count1>count) 
	     {
			Reporter.logEvent(Status.PASS,"Checking if row count increased on uploading new file","Row count increased on uploading new file",false);
		 }
	     else
	     {
	    	 Reporter.logEvent(Status.FAIL,"Checking if row count increased on uploading new file","Row count not increased on uploading new file",true);
	     }
        }
     catch(Exception e)
     {
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				Reporter.finalizeTCReport();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}

	}
	
	
	@Test(dataProvider="setData")
    public void TC019_deletionOfFile(int itr,Map<String, String> testdata) throws Exception
    {
		ResultSet tempResultset;
     try
        {
         Reporter.initializeReportForTC(itr,"Checking for row count on successfull delete","DDTC-394");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the row count on delete action" + ":" +"Positive flow", false);
         filesharing = new FileSharing().get();
	     tempResultset = DB.executeQuery(Stock.getTestQuery("deleteCount")[0],Stock.getTestQuery("deleteCount")[1]);
	     int count = DB.getRecordSetCount(tempResultset);
	     System.out.println("count before:"+count);
	     filesharing.deleteSuccessfully(Stock.GetParameterValue("FolderName"));
	     tempResultset = DB.executeQuery(Stock.getTestQuery("deleteCount")[0],Stock.getTestQuery("deleteCount")[1]);
	     int count1 = DB.getRecordSetCount(tempResultset);
	     System.out.println("count after"+count1);
	     if ((count1-count)>0) 
	     {
			Reporter.logEvent(Status.PASS,"Checking if row count reduced on delete","Row count reduced",false);
		 }
	     else
	     {
	    	 Reporter.logEvent(Status.FAIL,"Checking if row count reduced on delete","Row count not reduced",false);
	     }
        }
     catch(Exception e)
     {
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				Reporter.finalizeTCReport();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}

	}
     
	
	@Test(dataProvider="setData")
    public void TC021_SIT_PSC_FileSharing_04_TC026_Screen_element_Expiration_date(int itr,Map<String, String> testdata) throws Exception
    {
      try
        {
         Reporter.initializeReportForTC(itr,"Checking for expiration date option in vault folder","DDTC-89");
         Reporter.logEvent(Status.INFO, "Testcase Description","This testcase validates the expiration date menu in vault folder"+"Negative flow", false);
         filesharing = new FileSharing().get();
         filesharing.checkForExpDateInVault();
        }
      catch(Exception e)
      {
 			e.printStackTrace();
 		}
 		finally 
 		{
 			try 
 			{
 				Reporter.finalizeTCReport();
 			} 
 			catch (Exception e1)
 			{
 				e1.printStackTrace();
 			}
 		}
	
    }
}*/