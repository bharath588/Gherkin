package pageobjects.uploaddocuments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;







import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.general.LeftNavigationBar;

public class UploadDocuments extends LoadableComponent<UploadDocuments> {

	//Declarations
	private LoadableComponent<?> parent;
	
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	//@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	@FindBy(xpath="//h1[text()='Upload document']") private WebElement lblUploadDocuments;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(id = "fileUploadCategory") private WebElement drpDocumentType;
	@FindBy(xpath = "//button[span[contains(text(),'Select file to upload')]]") private WebElement btnSelectFileToUpload;
	@FindBy(id = "fileUploadComments") private WebElement inpUploadComments;
	@FindBy(xpath = "//button[span[contains(text(),'Upload')]]") private WebElement btnUpload;
	@FindBy(xpath = "//button[contains(text(),' View supported file types')]") private WebElement lnkViewSupportedFiles;
	
	@FindBy(xpath = "//img[@class='site-logo']") private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	private String txtElement = "//*[contains(text(),'webElementText')]";
	
	/** Empty args constructor
	 * 
	 */
	public UploadDocuments() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public UploadDocuments(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");
		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblBalance,true),"Balance Page is Not Loaded\n");
		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = null;
		String userFromDatasheet = null;
		if(Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName").toString().trim();
				
		}
		else{
		 try {
			strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
					+ strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		String userLogedIn = this.lblUserName.getText();
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			if(Web.isWebElementDisplayed(lblUploadDocuments, true) )
			Assert.assertTrue(true,"Upload Documents Page is not Loadeed\n");
			else
				Assert.assertTrue(false,"Upload Documents Page is not Loadeed\n");
		} else {
			
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("UPLOAD DOCUMENTS");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.isWebElementDisplayed(lblUploadDocuments,true);	
		
	}
	
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("BUTTON UPLOAD")) {
			return this.btnUpload;
		}
		if (fieldName.trim().equalsIgnoreCase("DROP DOWN DOCUMENT TYPE")) {
			return this.drpDocumentType;
		}
		if (fieldName.trim().equalsIgnoreCase("BUTTON SELECT FILE TO UPLOAD")) {
			return this.btnSelectFileToUpload;
		}
		if (fieldName.trim().equalsIgnoreCase("INPUT OPTIONAL COMMENTS")) {
			return this.inpUploadComments;
		}
		if (fieldName.trim().equalsIgnoreCase("LINK VIEW SUPPORTED FILE TYPES")) {
			return this.lnkViewSupportedFiles;
		}
		return null;
	}
	public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed = false;
		WebElement txtWebElement = Web.getDriver().findElement(
				By.xpath(txtElement.replace("webElementText", fieldName)));

		isTextDisplayed = Web.isWebElementDisplayed(txtWebElement, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify '" + fieldName
					+ "' Text Field is Displayed", fieldName + " is Displayed", false);

		} else {
			lib.Reporter.logEvent(Status.FAIL, "Verify '" + fieldName
					+ "' Text Field is Displayed", fieldName + " is Not Displayed", false);
		}

		return isTextDisplayed;
	}
	/**
	 * <pre>
	 * Method to Verify WebElement is Displayed or Not
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyWebElementIsDisplayed(String fieldName) {

		WebElement element = getWebElement(fieldName);
		if (Web.isWebElementDisplayed(element,true)) {
			lib.Reporter.logEvent(Status.PASS, "Verify " + fieldName
					+ " is Displayed", fieldName + " is Displayed", false);

		} else {

			lib.Reporter.logEvent(Status.FAIL, "Verify " + fieldName
					+ " is Displayed", fieldName + " is Not Displayed", true);
			throw new Error(fieldName + " is not displayed");
		}

	}
	
	public void verifyUploadDocumentPage(){

		isTextFieldDisplayed("Document type");
		verifyWebElementIsDisplayed("DROP DOWN DOCUMENT TYPE");
		isTextFieldDisplayed("Documents");
		verifyWebElementIsDisplayed("BUTTON SELECT FILE TO UPLOAD");
		isTextFieldDisplayed("5MB max file size");
		verifyWebElementIsDisplayed("LINK VIEW SUPPORTED FILE TYPES");
		isTextFieldDisplayed("Optional comments");
		verifyWebElementIsDisplayed("INPUT OPTIONAL COMMENTS");
		verifyWebElementIsDisplayed("BUTTON UPLOAD");

	}
}
