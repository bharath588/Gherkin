package pscBDD.processCenter;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import bdd_annotations.FindBy;
import bdd_core.framework.Globals;
import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_gwgwebdriver.pagefactory.NextGenPageFactory;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;
import pscBDD.planPage.PlanProvisionsPage;

public class uploadPayrollFilePage extends LoadableComponent<PlanProvisionsPage> {
	LoadableComponent<?> parent;
	
	@FindBy(how=How.XPATH,using="//*[@id='newMenu']//a[text()='Process center']")
	private WebElement tabProcessCenter;
	@FindBy(how=How.XPATH,using="//*[contains(@class,'dropdown-submenu navProcessCenterTree')]//a[text()='Transmit your payroll file']")
	private WebElement transmitPayrollFileUnderProcessCenterMenu;
	@FindBy(how=How.XPATH,using="//a[text()='Upload payroll file']")
	private WebElement uploadPayrollFileLink;
	@FindBy(how=How.XPATH,using="//*[@class='breadcrumb']/i")
	private WebElement breadcrumbCommon;
	@FindBy(how=How.XPATH,using="//*[@id='tabForm:navProcessCenter']//div[@class='breadcrumb']/i")
	private WebElement breadcrumbProcessCenter;
	
	@FindBy(how=How.ID,using="framek")
	private WebElement uploadPayRollFileFrame;
	
	@FindBy(how=How.XPATH,using="//tr[@class='lightBgColor']/following-sibling::tr[1]//font")
	private WebElement fileNameFirstName;
	@FindBy(how=How.XPATH,using="//tr[@class='lightBgColor']/following-sibling::tr/td[1]")
	private List<WebElement> fileNameAll;
	
	@FindBy(how=How.ID,using="file_input_hidden")
	//@FindBy(how=How.XPATH,using="//*[contains(@id,'file_input_hidden') or contains(@name,'upfile') or contains(@class,'file_input_div')]")
	private WebElement browseButton;
	@FindBy(how=How.NAME,using="ESC_UPLOAD_SUBMIT")
	private WebElement uploadFileButton;
	@FindBy(how=How.NAME,using="ESC_UPLOAD_SUBMIT_CONTINUE")
	private WebElement uploadFileButtonContinue;
	@FindBy(how=How.XPATH,using="//*[contains(text(),'have been successfully')]")
	private WebElement uploadFileSuccessMessage;
	
	@FindBy(how=How.XPATH,using="//tr[@class='lightBgColor']/following-sibling::tr[1]//td[1]")
	private WebElement uploadedFileNameFirstName;
	@FindBy(how=How.XPATH,using="(//*[@class='important_note'])[2]")
	private WebElement errorMessageFileUpload;
	
	
	public String filePath="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\";
	public String fileNameToUpload=null;
	public String invalidFileNameToUpload="inavalid_dont_upload.csv";
	
	public uploadPayrollFilePage(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}

	@Override
	protected void load() {
		try {
			new HomePage(new LoginPage(), false, new String[] {
					Web.rawValues(Globals.creds).get(0).get("username"),
					Web.rawValues(Globals.creds).get(0).get("password") })
					.get();
			Reporter.logEvent(Status.PASS,
					"Check if the user has landed on homepage",
					"The user has landed on homepage", true);
			Web.actionsClickOnElement(tabProcessCenter);
			Web.clickOnElement(transmitPayrollFileUnderProcessCenterMenu);
			Web.clickOnElement(uploadPayrollFileLink);
			Web.nextGenDriver.waitForAngular();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	@Override
	protected void isLoaded() throws Error {
		/*Web.waitForElement(breadcrumbCommon);
		String text = breadcrumbCommon.getText();
		if (text.equals(""))
			text = breadcrumbProcessCenter.getText();
		Assert.assertTrue(text.trim().equals("Upload payroll file"));*/
		Assert.assertTrue(Web.isWebElementDisplayed(uploadPayRollFileFrame));
		
	}
	/**
	 * <pre>
	 * Method to get file name from file Name list in upload payroll file page
	 * </pre> 
	 * @return <b>String - fileName</b> if file exist on the page.
	 *         <b>NULL</b> otherwise
	 */
	public String getFileNameToUpload(){
		Web.waitForElement(uploadPayRollFileFrame);
		Web.FrameSwitchONandOFF(true, uploadPayRollFileFrame);
		
		if (Web.isWebElementDisplayed(fileNameFirstName, true))
			fileNameToUpload = fileNameFirstName.getText().trim();
		else
			Reporter.logEvent(Status.INFO, "File doesn't exist",
					"File doesn't exist", true);
		return fileNameToUpload;
		
	}
	/**
	 * <pre>
	 * Method to create file name in users download directory if given file doesn't exist
	 * </pre> 
	 * @return <b>boolean - true</b>if file exist or after created
	 *  * @return <b>boolean - false</b>otherwise
	 */
	
	public boolean createFileIfNotExist(String fileNameToCrate){
		try{
			if(fileNameToCrate.length()>0){
				File file=new File(filePath+fileNameToCrate);
				if(file.createNewFile()){
					 PrintWriter pw = new PrintWriter(file);
				     StringBuilder sb = new StringBuilder();
				        sb.append("id");
				        pw.write(sb.toString());
				        pw.close();
				}
				return true;
			}
			
		}catch(NullPointerException ex){
			ex.printStackTrace();
			Reporter.logEvent(Status.INFO, "file name can't be empty",
					"provide file name which you want to create", true);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return false;
	
	}
	public boolean checkUserHasFileToUpload(){
		if(this.createFileIfNotExist(this.getFileNameToUpload()))
			return true;
		return false;
		
	}
	public boolean checkUserHasInvalidFileToUpload(){
		this.getFileNameToUpload();
		if(this.createFileIfNotExist(invalidFileNameToUpload))
			return true;
		return false;
		
	}
	public void uploadFile(String pathOfFile){
		try{
			Web.waitForElement(browseButton);
			browseButton.sendKeys(pathOfFile);
			Web.clickOnElement(uploadFileButton);
			Web.nextGenDriver.waitForAngular();
			Thread.sleep(2000);
			if(Web.isWebElementDisplayed(uploadFileButtonContinue))
				Web.clickOnElement(uploadFileButtonContinue);
			Reporter.logEvent(Status.INFO, "file uplod succesfully",
					"file uplod succesfully", true);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public boolean verifyFileUploadMessage(String messageToVerify) {
		if (Web.isWebElementDisplayed(uploadFileSuccessMessage, true)) {
			if (uploadFileSuccessMessage.getText().trim()
					.equals(messageToVerify.trim()))
				return true;
		}
		return false;
	}

	public boolean verifyUploadedFileName() {
		if (Web.isWebElementDisplayed(uploadedFileNameFirstName, true)) {
			if (uploadedFileNameFirstName.getText().trim()
					.equals(fileNameToUpload.trim()))
				return true;
		}
		return false;

	}
	public boolean verifyFileUploadErrorMessage(String... errorMsg){
		if(errorMsg.length>0){
			String expMsg=errorMsg[0];
			
			expMsg=expMsg.trim().replace("<file name>", invalidFileNameToUpload);
			System.out.println(expMsg);
			
			if(Web.isWebElementDisplayed(errorMessageFileUpload, true)){
				String actualMsg=errorMessageFileUpload.getText().trim();
				System.out.println(actualMsg);
				if(actualMsg.contains(expMsg))
					return true;			
			}
		}
		else{
			if(Web.isWebElementDisplayed(errorMessageFileUpload, true))
				return true;
		}
		return false;
		
	}

}
