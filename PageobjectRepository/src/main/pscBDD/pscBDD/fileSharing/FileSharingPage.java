/**
 * 
 */
package pscBDD.fileSharing;









import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


















import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;



import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pscBDD.homePage.HomePage;
import pscBDD.login.LoginPage;
import bdd_annotations.FindBy;
import bdd_core.framework.Globals;
import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenBy;
import bdd_gwgwebdriver.NextGenWebDriver;
import bdd_gwgwebdriver.pagefactory.NextGenPageFactory;
import bdd_lib.Web;
import bdd_lib.CommonLib;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;

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
	//@FindBy(how=How.REPEATER,using="folder in fileShareRepo.folders",exact=true)
	//private List<WebElement> fileSharingFolders;
	@FindBy(how=How.XPATH,using="//li[@ng-repeat='folder in fileShareRepo.folders']/div[1]")
	private List<WebElement> fileSharingFolders;
	@FindBy(how=How.REPEATER,using="subfolder in fileShareRepo.subfolders",exact=false)
	private List<WebElement> subfolderInFolders;
	@FindBy(how=How.REPEATER,using="document in fileShareRepo.view.filtered",exact=false)
	private List<WebElement> documentsInSubfolder;
	
	@FindBy(how=How.REPEATER,using="document in fileShareRepo.view.filtered",exact=false)
	private List<WebElement> documentsInSubfolders;
	@FindBy(how=How.ID,using="headercheckbox")
	private WebElement headerCheckbox;
	@FindBy(how=How.XPATH,using=".//*[@class='rowselector']")
	private List<WebElement> allCheckBoxes;
	@FindBy(how=How.XPATH,using=".//*[@ng-click='fileShareRepo.view.nextPage()']")
	private WebElement nextPage;
	@FindBy(how=How.XPATH,using=".//*[@ng-click='fileShareRepo.view.prevPage()']")
	private WebElement previousPage;
	@FindBy(how=How.XPATH,using="//button[@class='btn btn-default ng-binding']")
	private List<WebElement> buttonRow;
	@FindBy(how=How.XPATH,using="//div[@class='FidHeading pull-left ng-scope']/button[contains(text(),'Delete')]")
	private WebElement deleteButton;
	@FindBy(how=How.XPATH,using="//div[@class='FidHeading pull-left ng-scope']/button[contains(text(),'Move')]")
	private WebElement moveButton;
	@FindBy(how=How.XPATH,using="//h4[contains(text(),'Move file(s)')]")
	private WebElement moveFileModalBox;
	@FindBy(how=How.ID,using="deleteDocumentsLabel")
	private WebElement deleteFileModalBox;
	@FindBy(how=How.XPATH,using="//button[text()='Move file(s)']/following-sibling::button")
	private WebElement cancelButtonOfMovefileModalBox;
	@FindBy(how=How.BUTTON_TEXT,using="Move file(s)")
	private WebElement moveButtonOfMovefileModalBox;
	@FindBy(how=How.XPATH,using="//h4[text()='Move file(s)']/preceding-sibling::button")
	private WebElement closeButtonOfMovefileModalBox;
	@FindBy(how=How.XPATH,using="//button[text()='Delete file(s)']/following-sibling::button")
	private WebElement cancelButtonOfDeletefileModalBox;
	@FindBy(how=How.BUTTON_TEXT,using="Delete file(s)")
	private WebElement deleteButtonOfDeletefileModalBox;
	@FindBy(how=How.XPATH,using="//h4[@id='deleteDocumentsLabel']/preceding-sibling::button")
	private WebElement closeButtonOfDeletefileModalBox;
	@FindBy(how=How.MODEL,using="fileMoveModal.selectedFolder")
	private WebElement parentFolderDropdownOfMoveModalBox;
	@FindBy(how=How.MODEL,using="fileMoveModal.selectedSubfolder")
	private WebElement subFolderDropdownOfMoveModalBox;
	@FindBy(how=How.MODEL,using="fileMoveModal.confirmationCheck")
	private WebElement requiredCheckboxInMoveModalBox;
	@FindBy(how=How.XPATH,using="//*[*[@class='rowselector']]/following-sibling::td/div[1]/a")
	private List<WebElement> allFileNameInsideFolder;
	@FindBy(how=How.XPATH,using="//button[@id='manageFolderNotifications']")
	private WebElement manageFolderNotifications;
	
	@FindBy(how=How.XPATH,using="//div[@id='folderNotificationsModal']")
	private WebElement notificationFolderModelWindow;
	@FindBy(how=How.XPATH,using="//div[@id='folderNotificationsModal']//button[text()='Cancel']")
	private WebElement cancleButtonOnFolderNotificationsModal;
	
	
	
	private static final String folderRepeater = "folder in fileShareRepo.folders";
	public List<String> fileNamesOfSelectedCheckbox=new ArrayList<String>();

	@FindBy(how=How.XPATH,using="//li/div[contains(text(),'Auditor')]")
	private WebElement AuditorFolder;

	@FindBy(how=How.XPATH,using="//a/li[contains(text(),'Vault')]")
	private WebElement VaultFolder;
	
	@FindBy(how=How.XPATH,using="//*[@class='ng-binding ng-scope plussign folderlink']")
	private List<WebElement> plusButtonsInNotificationWindow;
	@FindBy(how=How.XPATH,using="//div[@ng-show='fileShareRepo.hasDailyDocuments']//ul//li//ul")
	private List<WebElement> subFoldersList;
	
	
	
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
		System.out.println(fileSharingFolders.size());
		if(fileSharingFolders.size()>0){
			for(WebElement ele : fileSharingFolders){
				System.out.println(ele.getText().trim());
				System.out.println(folderName.trim());
				if(ele.getText().trim().equalsIgnoreCase(folderName.trim())){
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
	public void selectOrDeselectTheHeaderRowCheckBox(String selectOrDeselect){
		try{
			if(selectOrDeselect.equalsIgnoreCase("select")||selectOrDeselect.equalsIgnoreCase("selected")){
				if(!(headerCheckbox.isSelected()))
					Web.clickOnElement(headerCheckbox);
			}
			else{
				if(headerCheckbox.isSelected())
					Web.clickOnElement(headerCheckbox);
			}
		}
		catch(Exception e){
			//throw new Error("Error getting when trying to check the checkbox : "+ e.getMessage());
		}		
	}
	
	public void clickOnMoveFiles(){
		if(Web.isWebElementDisplayed(requiredCheckboxInMoveModalBox, true)){
			if(!requiredCheckboxInMoveModalBox.isSelected()){
				Web.clickOnElement(requiredCheckboxInMoveModalBox);
				Web.clickOnElement(moveButtonOfMovefileModalBox);
			}
				
		}
			
	}
	
	public int countOfAllCheckBox(){
		return allCheckBoxes.size();
	}
	
	public int countOfNonSelectedCheckbox(){
		int i = 0;
		if (Web.isWebElementsDisplayed(allCheckBoxes, true)) {
			for (WebElement element : allCheckBoxes) {
				if (!(element.isSelected()))
					++i;
			}
		}
		return i;
	}
	
	public boolean isFileCountSameAsManyFileSelected() throws ParseException{
		WebElement pElement=requiredCheckboxInMoveModalBox.findElement(By.xpath("parent::*"));
		String text="";
		if(Web.isWebElementDisplayed(pElement, true))
			text=pElement.getText();
		int COUNT=0;		
		try{
			COUNT=((Number)NumberFormat.getInstance().parse(text)).intValue();
			System.out.println(COUNT);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(allCheckBoxes.size()-countOfNonSelectedCheckbox()==COUNT)
			return true;
		return false;
	}
	
	
	public void selectOneOrMoreNonHeaderRowCheckBox(int... n){
		if(!fileNamesOfSelectedCheckbox.isEmpty())
			fileNamesOfSelectedCheckbox.clear();
		int i = 0;int selectCount=0;
		int numberOfCheckboxToSelect = 0;
		if (Web.isWebElementsDisplayed(allCheckBoxes, true)) {
			if (n.length == 0)
				numberOfCheckboxToSelect = allCheckBoxes.size();
			else{
				//numberOfCheckboxToSelect = n.length;
				numberOfCheckboxToSelect = n[0];
			}

			for (WebElement element : allCheckBoxes) {
				if (!(element.isSelected())){
					Web.clickOnElement(element);
					}					
				++i;
				if (i == numberOfCheckboxToSelect)
					break;
			}
			while(!(selectCount==numberOfCheckboxToSelect)){
				fileNamesOfSelectedCheckbox.add(allFileNameInsideFolder.get(selectCount).getText().trim());
				selectCount++;
			}
			
		}

	}
	public void deselectOneOrMoreNonHeaderRowCheckBox(int... n){
		int i = 0;
		int numberOfCheckboxToDeSelect = 0;
			if(Web.isWebElementsDisplayed(allCheckBoxes, true)){
				if (n.length == 0)
					numberOfCheckboxToDeSelect = allCheckBoxes.size();
				else{
					//numberOfCheckboxToSelect = n.length;
					numberOfCheckboxToDeSelect = n[0];
				}
				
				for(WebElement element:allCheckBoxes){
					if(element.isSelected())
						Web.clickOnElement(element);
					++i;
					if (i == numberOfCheckboxToDeSelect)
						break;
				}
			}
		
	}
	
	public boolean isSelectedHeaderRowCheckBox(String selectedOrDeselected){
		if(selectedOrDeselected.equalsIgnoreCase("selected")){
			if(headerCheckbox.isSelected())
				return true;
		}
		else{
			if(!(headerCheckbox.isSelected()))
				return true;
		}
		return false;
	}
	
	public boolean isAllCheckBoxSelectedOrDeselected(String selectedOrDeselected) {
		boolean flag = false;
		try {
			if (selectedOrDeselected.equalsIgnoreCase("selected")) {
				for (WebElement checkBox : allCheckBoxes) {
					if (checkBox.isSelected())
						flag = true;
					else
						return false;
				}
			} else {
				for (WebElement checkBox : allCheckBoxes) {
					if (!(checkBox.isSelected()))
						flag = true;
					else
						return false;
				}
			}
		} catch (Exception e) {
			//throw new Error("Error getting when trying to check the checkbox selected or deselected : "+ e.getMessage());
			}
		return flag;
	}
	public boolean isButtonRowVisible(String visibleOrInvisible){
		
		if(visibleOrInvisible.equalsIgnoreCase("visible")){
			if(Web.isWebElementsDisplayed(buttonRow, true))
				return true;
		}
		else{
			if(!(Web.isWebElementsDisplayed(buttonRow, true)))
				return true;
		}
		return false;
	}
	
	public void clickOnButton(String buttonName){
		if(buttonName.trim().equalsIgnoreCase("Delete") || buttonName.trim().equalsIgnoreCase("Delete File(s)")){
				Web.clickOnElement(deleteButton);
		}
		else if(buttonName.trim().equalsIgnoreCase("Move") || buttonName.trim().equalsIgnoreCase("Move File(s)")){
				Web.clickOnElement(moveButton);
		}
		else{
			//click on download button
		}
	}
	
	public boolean isModalBoxPopupDisplayedOrNot(String popupName){
		if(popupName.trim().equalsIgnoreCase("Delete File(s)")){
			if(Web.isWebElementDisplayed(deleteFileModalBox, true))
				return true;
		}
		else if(popupName.trim().equalsIgnoreCase("Move File(s)")){
			if(Web.isWebElementDisplayed(moveFileModalBox, true))
				return true;
		}
		else{
			//check for download popup
		}
		return false;
	}
	
	public void cancelModalBoxPopup(String popupName){
		if(popupName.trim().equalsIgnoreCase("Delete File(s)")){
				Web.clickOnElement(cancelButtonOfDeletefileModalBox);
		}
		else if(popupName.trim().equalsIgnoreCase("Move File(s)")){
				Web.clickOnElement(cancelButtonOfMovefileModalBox);
		}
		else{
			//cancel for download popup
		}
	}
	
	public void closeModalBoxPopup(String popupName){
		if(popupName.trim().equalsIgnoreCase("Delete File(s)") || popupName.trim().equalsIgnoreCase("Delete")){
				Web.clickOnElement(closeButtonOfDeletefileModalBox);
		}
		else if(popupName.trim().equalsIgnoreCase("Move File(s)") ||popupName.trim().equalsIgnoreCase("Move")){
				Web.clickOnElement(closeButtonOfMovefileModalBox);
		}
		else{
			//close for download popup
		}
	}

	public void navigateToNextPage() {
		try {
			
				Web.clickOnElement(nextPage);
				Web.nextGenDriver.waitForAngular();

		} catch (Exception e) {
			//throw new Error("Error getting when trying click on next page : "+ e.getMessage());
		}
	}
	public void navigateToPreviousPage() {
		try {
			
			Web.clickOnElement(previousPage);
			Web.nextGenDriver.waitForAngular();
			

		} catch (Exception e) {
			//throw new Error("Error getting when trying click on next page : "+ e.getMessage());
		}
	}
	

	public String getDropDownSelectedText(String dropDownName) {
		WebElement element = null;
		String text = null;
		if (dropDownName.trim().equalsIgnoreCase("Parent Folder") ||dropDownName.trim().equalsIgnoreCase("Parent"))
			element = parentFolderDropdownOfMoveModalBox;
		if (dropDownName.trim().equalsIgnoreCase("Sub Folder")||dropDownName.trim().equalsIgnoreCase("Sub"))
			element = subFolderDropdownOfMoveModalBox;
		try {
			if (Web.isWebElementDisplayed(element, true))
			{
				//new Select(element).getOptions().get(0).click();
				text = new Select(element).getFirstSelectedOption().getText().trim();
			}
				
		} catch (Exception e) {
			Reporter.logEvent(Status.INFO, "dropdown not available",
					"dropdown not available", true);
		}
		/*finally{
			if(Web.isWebElementDisplayed(cancelButtonOfMovefileModalBox, true))
				cancelButtonOfMovefileModalBox.click();
			else
				cancelButtonOfDeletefileModalBox.click();
		}*/
		return text;
	}
	
	public int getTheCountOfDropDown(String dropDownName){
		WebElement element = null;
		if (dropDownName.trim().equalsIgnoreCase("Parent Folder") ||dropDownName.trim().equalsIgnoreCase("Parent"))
			element = parentFolderDropdownOfMoveModalBox;
		if (dropDownName.trim().equalsIgnoreCase("Sub Folder")||dropDownName.trim().equalsIgnoreCase("Sub"))
			element = subFolderDropdownOfMoveModalBox;
		try {
			if (Web.isWebElementDisplayed(element, true))
			{
				
				return new Select(element).getOptions().size();
			}
				
		} catch (Exception e) {
			Reporter.logEvent(Status.INFO, "dropdown not available",
					"dropdown not available", true);
		}
		return 0;
	}
	
	public void selectDropDownValue(String dropDownName,String valueToSelect){
		WebElement element = null;
		if (dropDownName.trim().equalsIgnoreCase("Parent"))
			element = parentFolderDropdownOfMoveModalBox;
		if (dropDownName.trim().equalsIgnoreCase("Sub"))
			element = subFolderDropdownOfMoveModalBox;
		if(Web.isWebElementDisplayed(element, true))
			Web.selectDropDownOption(element, valueToSelect);
	}
	
	public Boolean valueExistInselectDropDown(String dropDownName,String valueToCheck){
		WebElement element = null;
		if (dropDownName.trim().equalsIgnoreCase("Parent"))
			element = parentFolderDropdownOfMoveModalBox;
		if (dropDownName.trim().equalsIgnoreCase("Sub"))
			element = subFolderDropdownOfMoveModalBox;
		if(Web.isWebElementDisplayed(element, true))
		{
			if(Web.selectDropDownOption(element, valueToCheck))
			{
				return true;
			}
		}
		return false;
		
	}
	
	public void checkTheCheckboxInMoveFileModalBox(){
		if(!(requiredCheckboxInMoveModalBox.isSelected()))
			Web.clickOnElement(requiredCheckboxInMoveModalBox);
		
	}
	public void deSelectTheCheckboxInMoveFileModalBox(){
		if(requiredCheckboxInMoveModalBox.isSelected())
			Web.clickOnElement(requiredCheckboxInMoveModalBox);
		
	}
	public boolean isMoveFileButtonEnableOrDisabled(String enableOrDisabled){
		if (enableOrDisabled.trim().equalsIgnoreCase("enabled")) {
			if (moveButtonOfMovefileModalBox.isEnabled())
				return true;
			}
		if (enableOrDisabled.trim().equalsIgnoreCase("disabled")) {
			if (!(moveButtonOfMovefileModalBox.isEnabled()))
				return true;
			}
		return false;
	}
	
	public boolean isUserAllSubfoldersDisplayedInTheSubfolderNameDropdown(String rootFolderName){
		boolean flag=false;
		int i=0;
		List<String> subFolderNames=getSubfolderNameWithinFolder(rootFolderName);
		Collections.sort(subFolderNames);
		String[] arr = new String[subFolderNames.size()];
		arr=subFolderNames.toArray(arr);
		List<WebElement> dropDownList=new Select(subFolderDropdownOfMoveModalBox).getOptions();
		if(!(subFolderNames.size()==dropDownList.size()-1))
			return flag;
		for(WebElement elemnt:dropDownList){
			if(i>0){
				if(CommonLib.binarySearch(arr, elemnt.getText(), 0, arr.length)>=0)
					flag=true;
				else
					return false;
			}
			i++;
			
		}
		
		return flag;
	}
	
	public List<String> getSubfolderNameWithinFolder(String folderName){
		
		List<String> subFolderNames = new ArrayList<String>();
		List<WebElement> subfolders = Web.getDriver().findElement(NextGenBy.repeaterRows(folderRepeater, 0)).findElements(NextGenBy.repeater("subfolder in fileShareRepo.subfolders", false));
		System.out.println(subfolders.size());
		if (subfolders.size() > 0) {
			for (WebElement ele : subfolders) {
				String text = ele.getText().trim();
				if (ele.getText().trim().length() > 0) {
					subFolderNames.add(text);
				}

			}
		}
		return subFolderNames;
	}
	
	public boolean isFileExistAfterMoving() {
		Boolean flag=false;
		for (String input : fileNamesOfSelectedCheckbox) {
			flag = false;
			for (int i = 0; i < allFileNameInsideFolder.size(); i++) {
				if (input.trim().equals(
						allFileNameInsideFolder.get(i).getText().trim())) {
					flag = true;
					break;
				} else
					flag = false;

			}
		}
		return flag;

	}
	public boolean isFileExistInOldFolderAfterMoving() {
		Boolean flag=false;
		for (String input : fileNamesOfSelectedCheckbox) {
			flag = false;
			for (int i = 0; i < fileNamesOfSelectedCheckbox.size(); i++) {
				if (input.trim().equals(
						allFileNameInsideFolder.get(i).getText().trim())) {
					flag = true;
					break;
				} else
					flag = false;

			}
		}
		return flag;

	}

	public int getCountOfAllFiles()
	{
		
		try {
			if (Web.isWebElementsDisplayed(allFileNameInsideFolder, true))
			{
				return allFileNameInsideFolder.size();
			}
				
		} catch (Exception e) {
			Reporter.logEvent(Status.INFO, "Filenames Not Available in Auditor Folder",
					"Filenames Not Available in Auditor Folder", true);
		}
		return 0;
	}
	
	
	public void clickOnAuditorFolder()
	{
		try {
					if (Web.isWebElementDisplayed(AuditorFolder, true))
					{
						Web.clickOnElement(AuditorFolder);
						//return allFileNameInsideFolder.size();
					}
				
			} catch (Exception e) {
			Reporter.logEvent(Status.INFO, "Auditor Folder Does Not Exist",
					"Auditor Folder Does Not Exist", true);
			}
	}
	
	public void clickOnVaultFolder()
	{
		try {
			
					if (Web.isWebElementDisplayed(VaultFolder, true))
					{
						Web.clickOnElement(VaultFolder);
						//return allFileNameInsideFolder.size();
					}
				
			} catch (Exception e) {
			Reporter.logEvent(Status.INFO, "Vault Folder Does Not Exist",
					"Vault Folder Does Not Exist", true);
			}
	}
	
	public void displayAllFileNamesInAuditorFolder()
	{
		try {
			
			if (Web.isWebElementsDisplayed(allFileNameInsideFolder, true))
			{
				for(WebElement FileName:allFileNameInsideFolder)
				{
					String text = FileName.getText().trim();
					if (FileName.getText().trim().length() > 0) 
					{
						System.out.println(text);
					}
				}
				
				
				//return allFileNameInsideFolder.size();
			}
				
		} catch (Exception e) {
			Reporter.logEvent(Status.INFO, "Filenames Not Available in Auditor Folder",
					"Filenames Not Available in Auditor Folder", true);
		}
	}
	
	public Boolean checkFileNamesAreHavingGivenExtension(String extension)
	{
		
		try {
			
			if (Web.isWebElementsDisplayed(allFileNameInsideFolder, true))
			{
				Boolean selected=false;
				for(WebElement FileName:allFileNameInsideFolder)
				{
					String text = FileName.getText().trim();
					
					String FileExtenstion=FilenameUtils.getExtension(FileName.getText().trim());
					
					if (FileExtenstion.equalsIgnoreCase(extension)) 
					{
						selected=true;break;
			        }

				}
				if(selected)
				{
					Reporter.logEvent(Status.PASS,extension+" File Should Exist in Auditor Folder Files",
							extension+" File Exist in Auditor Folder Files",true);
		        }
		        else
		        {
		        	Reporter.logEvent(Status.FAIL,extension+" File Should Exist in Auditor Folder Files",
		        			extension+" File not Exist in Auditor Folder Files",true);
		        }
		
				//return allFileNameInsideFolder.size();
			}
				
		} catch (Exception e) {
			Reporter.logEvent(Status.INFO, "Filenames Not Available in Auditor Folder",
					"Filenames Not Available in Auditor Folder", true);
		}
		return false;
		
	}

	public void selectCheckBoxRelatedFileNames(List<String> FileNames)
	{
		
		int NumberOfFiles=getCountOfAllFiles();
		for(String file: FileNames)
		{
			Boolean selected=false;
			for(int i=0;i<NumberOfFiles;i++)
			{
				String ele=allFileNameInsideFolder.get(i).getText().trim();
				if(ele.equalsIgnoreCase(file))
				{
					Web.clickOnElement(allCheckBoxes.get(i));
					selected=true;
					break;
				}
			}
			if(selected)
			{
				Reporter.logEvent(Status.PASS,file+" File Should Exist and Related Check box Should Click in Auditor Folder Files ",
						file+" File Exist and Related Check box Clicked in Auditor Folder Files",true);
	        }
	        else
	        {
	        	Reporter.logEvent(Status.FAIL,file+" File Should Exist and Related Check box Should Click in Auditor Folder Files",
	        			file+" File not Exist and Related Check box is not Clicked in Auditor Folder Files",true);
	        }
		}
	}

	public void checkFilesAreExistInAuditorFolder(List<String> FileNames)
	{
		try{
			 closeModalBoxPopup("Move");
			 Thread.sleep(2000);
		 }
		 catch(Exception e){
			 Reporter.logEvent(Status.INFO, "No opened popup available", "No opened popup available", true);
		 }
		clickOnAuditorFolder();
		
		int NumberOfFiles=getCountOfAllFiles();
		for(String file: FileNames)
		{
			Boolean selected=false;
			for(int i=0;i<NumberOfFiles;i++)
			{
				String ele=allFileNameInsideFolder.get(i).getText().trim();
				if(ele.equalsIgnoreCase(file))
				{
					selected=true;
					break;
				}
			}
			if(selected)
			{
				Reporter.logEvent(Status.PASS,file+" File Should Exist in Auditor Folder Files ",
						file+" File Exist in Auditor Folder Files",true);
	        }
	        else
	        {
	        	Reporter.logEvent(Status.FAIL,file+" File Should Exist in Auditor Folder Files",
	        			file+" File not Exist in Auditor Folder Files",true);
	        }
		}
	}
	
	public void checkFilesAreExistInVaultFolder(List<String> FileNames)
	{
		
		try{
			 closeModalBoxPopup("Move");
			 Thread.sleep(2000);
		 }
		 catch(Exception e){
			 Reporter.logEvent(Status.INFO, "No opened popup available", "No opened popup available", true);
		 }
		clickOnVaultFolder();
				int NumberOfFiles=getCountOfAllFiles();
		for(String file: FileNames)
		{
			Boolean selected=false;
			for(int i=0;i<NumberOfFiles;i++)
			{
				String ele=allFileNameInsideFolder.get(i).getText().trim();
				if(ele.equalsIgnoreCase(file))
				{
					selected=true;
					break;
				}
			}
			if(selected)
			{
				Reporter.logEvent(Status.PASS,file+" File Should Exist in Vault Folder Files ",
						file+" File Exist in Vault Folder Files",true);
	        }
	        else
	        {
	        	Reporter.logEvent(Status.FAIL,file+" File Should Exist in Vault Folder Files",
	        			file+" File not Exist in Vault Folder Files",true);
	        }
		}
	}
	
	public void checkFilesAreDoesNotExistInAuditorFolder(List<String> FileNames)
	{
		try{
			 closeModalBoxPopup("Move");
			 Thread.sleep(2000);
		 }
		 catch(Exception e){
			 Reporter.logEvent(Status.INFO, "No opened popup available", "No opened popup available", true);
		 }
		clickOnAuditorFolder();
		
		int NumberOfFiles=getCountOfAllFiles();
		for(String file: FileNames)
		{
			Boolean selected=false;
			for(int i=0;i<NumberOfFiles;i++)
			{
				String ele=allFileNameInsideFolder.get(i).getText().trim();
				if(ele.equalsIgnoreCase(file))
				{
					selected=true;
					break;
				}
			}
			if(selected)
			{
				Reporter.logEvent(Status.FAIL,file+" File Should not Exist in Auditor Folder Files ",
						file+" File Exist in Auditor Folder Files",true);
	        }
	        else
	        {
	        	Reporter.logEvent(Status.PASS,file+" File Should not Exist in Auditor Folder Files",
	        			file+" File Exist in Auditor Folder Files",true);
	        }
		}
	}
	
	/*Navigating to File Sharing Tab after Plan Search */
	
	public void navigateToFileSharingTab() throws InterruptedException
	{
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
	}
	
	public boolean isManageFolderNotificationsButtonVisible()
	{
		if(Web.isWebElementDisplayed(manageFolderNotifications, true))
		{
			Reporter.logEvent(Status.PASS, "User should see the Manage folder notifications link", 
					"User able to see the Manage folder notifications link", true);
			return true;
		}
		else{
			Reporter.logEvent(Status.FAIL, "User should see the Manage folder notifications link", 
					"User not able to see the Manage folder notifications link", true);
			return false;
		}
	}
	
	public void openFolderNotificationManager()
	{
		try {
			
				if (Web.isWebElementDisplayed(manageFolderNotifications, true))
				{
					Web.clickOnElement(manageFolderNotifications);
					//return allFileNameInsideFolder.size();
					if(Web.isWebElementDisplayed(notificationFolderModelWindow, true))
					{
						Reporter.logEvent(Status.PASS, "Clicked on Manage folder notifications link ",
								"Notification manage Folder Window is  opened", true);
					}
				}
				
			} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Unable to Click Manage folder notifications link ",
					"Notification manage Folder is not opened", true);
			}
	}
	
	public boolean isPlusButtonPresentNexttoFolder()
	{
		try {
			
				if (Web.isWebElementsDisplayed(plusButtonsInNotificationWindow, true))
				{
					Reporter.logEvent(Status.PASS, "Plus Button Should Present next to the folder ",
								"Plus Button is displayed next to the folder ", true);
					
					if (Web.isWebElementDisplayed(cancleButtonOnFolderNotificationsModal, true))
						Web.clickOnElement(cancleButtonOnFolderNotificationsModal);
					
					return true;
					
				}
				return true;
				
			} catch (Exception e) {
			Reporter.logEvent(Status.FAIL, "Plus Button Should Present next to the folder ",
					"Plus Button is not displayed next to the folder ", true);
				return false;
			}
	}
	
	public boolean isSubFoldersPresent()
	{
		try {
			if (Web.isWebElementsDisplayed(subFoldersList, true))
		{
			Reporter.logEvent(Status.PASS, "User has access to a folder with subfolders", 
	    			"User is having  access to a folder with subfolders", true);
			return true;
			
		}
		return true;
		
	} catch (Exception e) {
	Reporter.logEvent(Status.FAIL, "User has access to a folder with subfolders", 
			"User is not having  access to a folder with subfolders", true);
		return false;
	}
		
	}

	public void clickOnPlusButtonNexttoFolder()
	{
		try {
				if (Web.isWebElementsDisplayed(plusButtonsInNotificationWindow, true))
				{
			
					Reporter.logEvent(Status.PASS, "Number of Folders is having Subfolders ",
							" "+plusButtonsInNotificationWindow.size(), false);
					
					String PlusFoldername=plusButtonsInNotificationWindow.get(0).getText();
					Web.clickOnElement(plusButtonsInNotificationWindow.get(0));
					Reporter.logEvent(Status.PASS, " Clicked on Plus Button Next to "+PlusFoldername +" Folder"," ", false);
					
					
					for(WebElement foldername: plusButtonsInNotificationWindow)
					{
						System.out.println(foldername.getText());
					}
					
										
				}
	
		
			} catch (Exception e) {
					Reporter.logEvent(Status.FAIL, "Plus Button Should Present next to the folder ",
								"Plus Button is not displayed next to the folder ", true);
	
			}
	}
}


