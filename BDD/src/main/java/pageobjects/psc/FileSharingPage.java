/**
 * 
 */
package pageobjects.psc;

import gwgwebdriver.How;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.NextGenPageFactory;





import java.util.List;
import java.util.concurrent.TimeUnit;

import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import annotations.FindBy;

import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
import reporter.Reporter;
/**
 * @author rvpndy
 *
 */
public class FileSharingPage extends LoadableComponent<FileSharingPage> {

	LoadableComponent<?> parent;
	Actions actions;

	@FindBy(how=How.XPATH,using=".//*[@id='docSearchText']")
	private WebElement filterSearch;
	@FindBy(how=How.XPATH,using=".//*[@id='newMenu']//*[contains(text(),'File sharing')]")
	private WebElement tabFileSharing;
	@FindBy(how=How.XPATH,using=".//*[@id='framel']")
	private WebElement fileSharingFrame;
	@FindBy(how=How.XPATH,using=".//div[@class='breadcrumb']/i[contains(text(),'File Sharing']")
	private WebElement fileSharingTag;
	@FindBy(how=How.REPEATER,using="folder in fileShareRepo.folders",exact=true)
	private List<WebElement> fileSharingFolders;
	@FindBy(how=How.REPEATER,using="subfolder in fileShareRepo.subfolders",exact=false)
	private List<WebElement> subfolderInFolders;
	@FindBy(how=How.REPEATER,using="document in fileShareRepo.view.filtered",exact=false)
	private List<WebElement> documentsInSubfolder;



	public FileSharingPage(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(filterSearch));
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		try {
			HomePage homepage = (HomePage) this.parent;
			// LoginPage login = new LoginPage();
			new HomePage(new LoginPage(), false, new String[] {
				Web.rawValues(Globals.creds).get(0).get("username"),
				Web.rawValues(Globals.creds).get(0).get("password") }).get();
			Reporter.logEvent(Status.PASS, 
					"Check if the user has landed on homepage",
					"The user has landed on homepage", true);
			Web.waitForPageToLoad(Web.getDriver());
			//CommonLib.waitForProgressBar();
			Web.waitForElement(tabFileSharing);
			//Web.clickOnElement(tabEmployees);
			//Web.waitForElement(drpdwnSearchEmployee);
			actions = new Actions(Web.getDriver());
			actions.moveToElement(tabFileSharing).click().build().perform();
			Web.waitForPageToLoad(Web.getDriver());
			Web.getDriver().switchTo().frame(fileSharingFrame);
			if(!Web.isWebElementDisplayed(fileSharingTag, true))
			{
				Reporter.logEvent(Status.PASS, "File sharing page loaded", 
						"File Sharing page loaded", true);
			}
			else{
				Reporter.logEvent(Status.FAIL, "File sharing page loaded", 
						"File Sharing page not loaded", true);
			}
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 *  </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName){
		if(fieldName.equalsIgnoreCase("File Sharing Tab"))
			return this.tabFileSharing;
		if(fieldName.equalsIgnoreCase("File Sharing Frame"))
			return this.fileSharingFrame;
		return null;
	}

	public void openFolder(String folderName){
		if(fileSharingFolders.size()>0){
			for(WebElement ele : fileSharingFolders){
				if(ele.getText().equalsIgnoreCase(folderName)){
					Web.clickOnElement(ele);
					Web.nextGenDriver.waitForAngular();
					break;
				}
			}
		}
		else{
			Reporter.logEvent(Status.INFO, "No folders available", "No folders available", true);
		}
	}
	
	public void openSubFolder(String SubfolderName){
		if(subfolderInFolders.size()>0){
			for(WebElement ele : subfolderInFolders){
				System.out.println("Sub folder name "+ele.getText());
				if(ele.getText().contains(SubfolderName)){
					
					Web.clickOnElement(ele);
					Web.nextGenDriver.waitForAngular();
					break;
				}
			}
		}
	}
	
	public boolean isDocumentAvailable(String docName){
		if(documentsInSubfolder.size()>0){
			for(WebElement ele : documentsInSubfolder){
				System.out.println("document name "+ele.getText());
				if(ele.getText().contains(docName))
					return true;
			}
		}
		return false;
	}
}


