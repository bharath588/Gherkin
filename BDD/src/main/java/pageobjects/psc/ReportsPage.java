/**
 * 
 */
package pageobjects.psc;

import java.util.List;
import java.util.concurrent.TimeUnit;

import gwgwebdriver.How;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.NextGenPageFactory;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import annotations.FindBy;

import com.aventstack.extentreports.Status;

import reporter.Reporter;

/**
 * @author rvpndy
 *
 */
public class ReportsPage extends LoadableComponent<ReportsPage> {

	static String[] folderName;
	static int recurssionCount = 0;
	public static String planNumberValue;
	public static String folderNameValue;
	public static String subfolderNameValue;
	public static String fileNameValue;

	@FindBy(how =How.ID, using="framef")
	private WebElement reportsFrame;

	@FindBy(how=How.XPATH,using=".//*[@id='standardreportsTab']/a")
	private WebElement standardReports;

	@FindBy(how=How.XPATH,using=".//*[@id='myreportsTab']/a")
	private WebElement myReports;

	@FindBy(how=How.LINK_TEXT,using="Share")
	private List<WebElement> shareableReports;

	@FindBy(how=How.LINK_TEXT,using="Reshare")
	private List<WebElement> reshareReports;

	@FindBy(how=How.ANGULAR_ATTRIBUTE,using="click",text="openFileSharingUploadModal")
	private List<WebElement> shareReports;

	@FindBy(how=How.ID,using="fileSharingModalDialog")
	private WebElement fileSharingModalDialog;

	@FindBy(how=How.OPTIONS,using="folder.label for folder in fileSharingData.folders")
	private List<WebElement> fileSharingFolder;

	@FindBy(how=How.OPTIONS,using="subfolder.label for subfolder in fileSharingData.subfolders")
	private List<WebElement> fileSharingSubFolder;

	@FindBy(how=How.OPTIONS,using="category.label for category in fileSharingData.categories")
	private List<WebElement> fileSharingCategory;
	
	@FindBy(how=How.OPTIONS,using="expirationYear.label for expirationYear in fileSharingData.expirationYears")
	private List<WebElement> fileSharingExpirationDates;
	
	@FindBy(how=How.MODEL,using="fileSharingData.upldAck")
	private WebElement confirmationCheckBox;
	
	@FindBy(how=How.XPATH,using=".//input[@value='Save']")
	private WebElement saveButton;
	
	@FindBy(how=How.XPATH,using=".//input[@value='Close']")
	private WebElement closeModal;
	
	@FindBy(how=How.XPATH,using=".//*[@id='fileSharingModalDialog']//span[contains(text(),'successfully')]")
	private WebElement saveSuccess;
	
	@FindBy(how=How.XPATH,using=".//*[@id='fileSharingModalDialog']//label[contains(text(),'Plan')]/following-sibling::p")
	private WebElement planNumber;
	
	@FindBy(how=How.XPATH,using=".//*[@id='fileSharingModalDialog']//label[contains(text(),'Folder')]/following-sibling::p")
	private WebElement folder_Name;
	
	@FindBy(how=How.XPATH,using=".//*[@id='fileSharingModalDialog']//label[contains(text(),'Subfolder')]/following-sibling::p")
	private WebElement subfolder_name;
	
	@FindBy(how=How.XPATH,using=".//*[@id='fileSharingModalDialog']//div/label[contains(text(),'File name')]/parent::div/following-sibling::div/p")
	private WebElement fileName;
	

	LoadableComponent<?> parent;

	public ReportsPage(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		Web.getDriver().switchTo().defaultContent();
		HomePage homePage = (HomePage) this.parent;

		new HomePage(new LoginPage(), false, new String[] {
			"1ISIS",
		"testing1" }).get();

		Reporter.logEvent(Status.PASS,
				"Check if the user has landed on homepage",
				"The user has landed on homepage", true);
		//homePage.logoutPSC(homePage.isDifferentUser());
		/*if(Stock.GetParameterValue("userType").contains("PL"))
					try {
						CommonLib.switchToDefaultPlanPartnerLinkUserAndNumberOfPlansLessThan25();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
		if(Web.isWebElementDisplayed(new HomePage(), "Reports Menu", true)){
			Web.clickOnElement(new HomePage(), "Reports Menu");
			Web.nextGenDriver.waitForAngular();
			Web.clickOnElement(new HomePage(), "Standard Reports");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Web.waitForElement(reportsFrame);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(reportsFrame);
			Web.nextGenDriver.waitForAngular();

		}

	}


	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(standardReports));
	}

	public void openMyReports(){
		Web.FrameSwitchONandOFF(true, reportsFrame);
		Web.clickOnElement(myReports);
		Web.nextGenDriver.waitForAngular();
	}

	public void clickOnShareOrReShare(){
		if(isShareOrReshareAvailable()){
			for(WebElement ele : shareReports){
				Web.clickOnElement(ele);
				Reporter.logEvent(Status.INFO, "Click on link", "Click on link "+ele.getText(), true);
				break;
			}
			Web.nextGenDriver.waitForAngular();
		}

		else{
			Reporter.logEvent(Status.INFO, "There are no reports with Share or Reshare link", 
					"There are no reports with Share or Reshare link", true);
		}
	}

	public boolean isShareOrReshareAvailable(){
		if(isFileSharingModel()){
			//Web.clickOnElement(closeModal);
			System.out.println("Close modal attrib"+closeModal.getAttribute("Value"));
			closeModal.click();
			Web.nextGenDriver.waitForAngular();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		openMyReports();
		if(shareReports.size()>0){
			Reporter.logEvent(Status.INFO, "User has shareable report in My Reports", 
					"User has shareable report in My Reports", true);
			return true;
		}
		return false;
	}

	public boolean isFileSharingModel(){
		if(Web.isWebElementDisplayed(fileSharingModalDialog)){
			folderName = new String[fileSharingFolder.size()-1];
			return true;
		}
		return false;
	}

	public boolean isFileSharingUser(){
		if(fileSharingFolder.size()>0){
			return true;
		}
		return false;
	}

	public void selectAFolder(){
		if(isFileSharingUser()){
			for(WebElement ele : fileSharingFolder){
				if(!ele.getText().equalsIgnoreCase("Select folder")){
					Web.clickOnElement(ele);
					Web.nextGenDriver.waitForAngular();
					Reporter.logEvent(Status.INFO, "Select a  Folder", 
							"Select a folder "+ele.getText(), true);
					break;
				}
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "User does not have access to File Sharing", "User does not have access to File Sharing", true);
		}
	}

	public boolean hasSubFolders(){
		if(fileSharingSubFolder.size()>1){
			return true;
		}
		return false;
	}

	public String getSubFolderDefaultOption(){
		if(fileSharingSubFolder.size()>0){
			for(WebElement ele : fileSharingSubFolder){
				if(ele.getText().equalsIgnoreCase("Select subfolder"))
					return ele.getText();
			}
		}
		return "No Subfolders";
	}

	public boolean selectFolderWithSubFolder(){
		try{
			if(fileSharingFolder.size()>1){
				for(WebElement ele : fileSharingFolder){
					if(!ele.getText().equalsIgnoreCase("Select folder")){
						for(String str : folderName){
							if(str!=null &&!str.equalsIgnoreCase(ele.getText())){
								folderName[recurssionCount] = ele.getText();
								Web.clickOnElement(ele);
								Web.nextGenDriver.waitForAngular();
								break;
							}
						}

					}
				}
				if(fileSharingSubFolder.size()>1){
					for(WebElement ele : fileSharingSubFolder){
						if(!ele.getText().equalsIgnoreCase("Select subfolder")){
							Web.clickOnElement(ele);
							Web.nextGenDriver.waitForAngular();
							return true;
						}
					}
				}
				else{
					recurssionCount++;
					selectFolderWithSubFolder();
				}
			}
			else{
				if(recurssionCount==0||recurssionCount>=fileSharingFolder.size()-1)
					return false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void selectAcategory(){
		try{
			if(fileSharingCategory.size()>1){
				for(WebElement ele : fileSharingCategory){
					if(!ele.getText().equalsIgnoreCase("Select category")){
						Web.clickOnElement(ele);
						break;
					}
				}
			}
			else{
				Reporter.logEvent(Status.INFO, "No category available", 
						"No category available", true);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void selectExpirationDate(){
		try{
			if(fileSharingExpirationDates.size()>1){
				for(WebElement ele : fileSharingExpirationDates){
					Web.clickOnElement(ele);
					break;
				}
			}
			else{
				Reporter.logEvent(Status.INFO, "No expiration date available", 
						"No expiration date available", true);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void clickOnConfirmationCheckBox(){
		Web.clickOnElement(confirmationCheckBox);
		Web.nextGenDriver.waitForAngular();
	}
	
	public void clickOnSave(){
		planNumberValue = planNumber.getText();
		folderNameValue = folder_Name.getText();
		subfolderNameValue=subfolder_name.getText();
		Web.clickOnElement(saveButton);
		Web.nextGenDriver.waitForAngular();
		if(Web.isWebElementDisplayed(saveSuccess)){
			Reporter.logEvent(Status.PASS, "Save Successful", 
					"Save Successful to "+folderNameValue+" and subfolder "+subfolderNameValue,
					true);
			fileNameValue = fileName.getText();
			Web.clickOnElement(closeModal);
			Web.nextGenDriver.waitForAngular();
		}
		else{
			Reporter.logEvent(Status.FAIL, "Save Successful", "Save failed", true);
		}
		
	}
	
	public void setFolderName(String folderName){
		if(isFileSharingUser()){
			for(WebElement ele : fileSharingFolder){
				if(!ele.getText().equalsIgnoreCase("Select folder")
						&& ele.getText().equalsIgnoreCase(folderName)){
					Web.clickOnElement(ele);
					Web.nextGenDriver.waitForAngular();
					Reporter.logEvent(Status.INFO, "Select a  Folder", 
							"Select a folder "+ele.getText(), true);
					break;
				}
			}
		}
	}





}
