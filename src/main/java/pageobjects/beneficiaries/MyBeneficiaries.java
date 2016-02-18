
package pageobjects.beneficiaries;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.DB;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.general.LeftNavigationBar;



public class MyBeneficiaries extends LoadableComponent<MyBeneficiaries> {

	//Declarations
	private LoadableComponent<?> parent;
	//private static boolean waitforLoad = false;
	
	@FindBy(xpath=".//*[text()='My Beneficiaries']") private WebElement lblMyBeneficiaries;
	@FindBy(id="btn-add-beneficiary") private WebElement btnAddAnotherBeneficiary;
//	@FindBy(id="btn-continue") private WebElement btnContinue;
	@FindBy(id="btn-continue submit") private WebElement btnContinue;
	@FindBy(xpath=".//span[text()='Contingent']") private WebElement btnContingent;
	@FindBy(xpath=".//span[text()='Primary']") private WebElement btnPrimary;
	@FindBy(id="btn-cancel") private WebElement btnCancel;
	@FindBy(id="btn-save") private WebElement btnSave;
	@FindBy(xpath=".//*[@class='page-title']") private WebElement lblAddBeneficiaryTitle;
	@FindBy(xpath=".//*[text()='Primary']") private WebElement btnPrimaryBenType;
	@FindBy(xpath=".//*[text()='Contingent']") private WebElement btnContingentBenType;
	@FindBy(id="beneficiaryType") private WebElement selMyBeneficiary;
	@FindBy(xpath=".//*[contains(@class,'bene-type-header')]") private WebElement lblBenSelectedHdr;
	@FindBy(id="beneficiaryFirstName") private WebElement txtFirstName;
	@FindBy(id="beneficiaryMiddleName") private WebElement txtMiddleName;
	@FindBy(id="beneficiaryLastName") private WebElement txtLastName;
	@FindBy(id="beneficiarySuffix") private WebElement txtSuffix;
	@FindBy(id="dateOfBirth") private WebElement txtDateOfBirth;
	@FindBy(id="socialSecurityNumber") private WebElement txtSSN;
	@FindBy(id="phoneNumber") private WebElement txtPhoneNumber;
	@FindBy(id="useAddressOnFile") private WebElement chkUseMyAddressForBen;
	@FindBy(id="address1") private WebElement txtAddressOne;
	@FindBy(id="address2") private WebElement txtAddressTwo;
	@FindBy(id="city") private WebElement txtCity;
	@FindBy(id="zipCode") private WebElement txtZipCode;
	@FindBy(id="state") private WebElement selState;
	@FindBy(id="country") private WebElement selCountry;
	@FindBy(id="removeBeneficiary") private WebElement chkDelBeneficiary;
	@FindBy(xpath=".//*[@id='account-details-container']/div/div/form/div[1]/div/table/tbody/tr/td[1]/a") private WebElement Linkclick;

	@FindBy(xpath="//h1[text()='Designate beneficiary']") private WebElement lblDesignateBeneficiary;
	@FindBy(xpath="//span[text()='Yes']") private WebElement btnMarried;
	@FindBy(xpath="//span[text()='No']") private WebElement btnUnmarried;
	@FindBy(xpath="//table[@class='beneficiaries primary-beneficiaries table']/tbody") private WebElement tblMyBeneficiaries;
	@FindBy(xpath="//button[text()='Delete']") private WebElement btnDelete;
	@FindBy(xpath="//table[@class='beneficiaries primary-beneficiaries table']//*[@class='beneficiary-name']/a") private List<WebElement> lstlnkPrimaryBeneficiaryName;
	@FindBy(xpath="//table[@class='beneficiaries contingent-beneficiaries table']//*[@class='beneficiary-name']/a") private List<WebElement> lstlnkContingentBeneficiaryName;
	@FindBy(xpath="//*[contains(@class,'contingent')]//*[@class='beneficiary-allocation']//input") private List<WebElement> lsttxtContingentAllocation;
	@FindBy(xpath="//*[contains(@class,'primary')]//*[@class='beneficiary-allocation']//input") private List<WebElement> lsttxtPrimaryAllocation;
	@FindBy(xpath="//div[@class='table-details ng-scope']/table") private List<WebElement> lstTablePrimaryBeneficiary;
	@FindBy(xpath="//div[@class='table-details']/table/tbody/tr") private List<WebElement> lstTblConfirmationDetails;
	@FindBy(xpath=".//*[text()='Confirmation #:']") private WebElement lblConfirmation;
	@FindBy(xpath="//input[@id='primaryBeneficiaryAllocationPercent0']") private WebElement txtAllocationBeneficiary1;
	@FindBy(xpath="//input[@id='primaryBeneficiaryAllocationPercent1']") private WebElement txtAllocationBeneficiary2;
	
	@FindBy(xpath="//input[@id='beneficiaryName']") private WebElement txtBeneficiaryName;
	@FindBy(xpath="//input[@id='dateOfTrust']") private WebElement txtDateOfTrust;
	@FindBy(xpath="//input[@id='taxIdentificationNumber']") private WebElement txtTaxIdentificationNo;
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	
	/** Empty args constructor
	 * 
	 */
	public MyBeneficiaries() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public MyBeneficiaries(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(0, ssn.length()-3));
		
		String userFromDatasheet = null;
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		String userLogedIn = this.lblUserName.getText();
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			if(Stock.GetParameterValue("Add_Allocation").equalsIgnoreCase("No"))
				Assert.assertTrue(lib.Web.isWebElementDisplayed(lblDesignateBeneficiary));
			else
				Assert.assertTrue(lib.Web.isWebElementDisplayed(lblMyBeneficiaries));
		} else {
			this.lnkLogout.click();
		}

	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Beneficiaries");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private WebElement getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("Married")) {
			return this.btnMarried;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Unmarried")) {
			return this.btnUnmarried;
		}
		
		if (fieldName.trim().equalsIgnoreCase("DesignateBeneficiary")) {
			return this.lblDesignateBeneficiary;
		}
		
		if (fieldName.trim().equalsIgnoreCase("AddAnotherBeneficiary")) {
			return this.btnAddAnotherBeneficiary;
		}
		
		if (fieldName.trim().equalsIgnoreCase("ContinueAndConfirm")) {
			return this.btnContinue;
		}
		
		
		
		return null;
		
	}
	
//	public void addBeneficiary(){
//
//		this.Linkclick.click();
//		this.btnAddAnotherBeneficiary.click();
//
//		try {
//			WebActions.waitForElement(this.lblAddBeneficiaryTitle);
//		} catch (Exception e) {
//		}
//		
//		this.btnPrimary.click();
//		WebActions.selectDropDownOption(this.selMyBeneficiary, "Parent");
//		WebActions.isWebElementDisplayed(this.lblMyBeneficiaries);
//		this.txtFirstName.sendKeys("sandeep");
//		this.txtMiddleName.sendKeys("BS");
//		this.txtLastName.sendKeys("kamath");
//		this.txtSuffix.sendKeys("Sir");
//		this.txtDateOfBirth.sendKeys("08/22/1963");
//		this.txtSSN.sendKeys("123-12-3123");
//		this.txtPhoneNumber.sendKeys("(123) 123-1231");
//		this.txtAddressOne.sendKeys("addr1");
//		this.txtAddressTwo.sendKeys("addr2");
//		this.txtCity.sendKeys("bangalore");
//		this.txtZipCode.sendKeys("1234");
//		WebActions.selectDropDownOption(this.selCountry, "India");
//		this.btnCancel.click();
//		
//		
//	}
	
	/**<pre> Method to check if a particular element is present on the page.
	 *.</pre>
	 * @param fieldName - fieldname
	 * 
	 * @return - boolean
	 */
	public boolean isFieldDisplayed(String fieldName) {
		WebElement element = this.getWebElement(fieldName);
		
		if (element == null) {
			return false;
		} else {
			return Web.isWebElementDisplayed(element);
		}
	}
	
	/**<pre> Method to add a beneficiary.
	 *.</pre>
	 * @param maritalStatus - marital Status of the beneficiary
	 * @param useMyAddress - whether to use current address
	 * @param beneficiaryType - Beneficiary type can be primary or contingent
	 */
	public void addBeneficiary(String maritalStatus, String beneficiaryRelation, String useMyAddress, String beneficiaryType){
		WebElement maritalstatus = this.getWebElement(maritalStatus);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
		}
		if(Web.isWebElementDisplayed(lblDesignateBeneficiary))
			maritalstatus.click();
		
		else{
			Web.clickOnElement(this.btnAddAnotherBeneficiary);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				
			}
			if(beneficiaryType.equalsIgnoreCase("Primary"))
				
				Web.clickOnElement(this.btnPrimary);
			else
				Web.clickOnElement(this.btnContingent);
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		Web.selectDropDownOption(this.selMyBeneficiary, beneficiaryRelation);
		if(Stock.GetParameterValue("Beneficiary Relation").equalsIgnoreCase("A Trust"))
			this.enterEntityDetails();
		else{
			this.enterBeneficiaryDetails();
			if(useMyAddress.equalsIgnoreCase("Yes")){
				if(!this.chkUseMyAddressForBen.isSelected())
					this.chkUseMyAddressForBen.click();
			}
			else{
				if(this.chkUseMyAddressForBen.isSelected())
					this.chkUseMyAddressForBen.click();
				this.enterAddressDetails();
			}
		}
			
		Web.clickOnElement(this.btnSave);

		
		if(Stock.GetParameterValue("Add_Allocation").equalsIgnoreCase("Yes")){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				
			}
			this.txtAllocationBeneficiary1.clear();
			this.txtAllocationBeneficiary1.sendKeys(Stock.GetParameterValue("Allocation"));
			this.txtAllocationBeneficiary2.sendKeys(Stock.GetParameterValue("Allocation"));
		}
	}
	
	
//	public String enterAllocationPercentage(){
//		System.out.println("inside enter allocations");
//		int noOfBeneficiaries = this.lsttxtPrimaryAllocation.size();
//		allocationPercentage = Integer.toString(100/noOfBeneficiaries);
//		System.out.println(allocationPercentage);
//		for(int i=0;i<noOfBeneficiaries;i++){
//			System.out.println(i);
//			lsttxtPrimaryAllocation.get(i).clear();
//			lsttxtPrimaryAllocation.get(i).sendKeys(allocationPercentage);
//		}
//		
//		return allocationPercentage;
//	}
	

//	public boolean verifyBeneficiaryPresentInBeneficiaryTable(){
//		boolean isSuccess = false;
//		String beneficiaryName = TestDataContainer.GetParameterValue("FirstName")+" "+TestDataContainer.GetParameterValue("MiddleName")+" "+TestDataContainer.GetParameterValue("LastName");
//		if(this.tblMyBeneficiaries.getText().contains(beneficiaryName))
//			isSuccess = true;
//		this.btnContinue.click();
//		System.out.println("button clisked");
//		return isSuccess;
//	}
//	
	
//	public boolean clickOnBeneficiaryFromTable(String beneficiaryType){
//		boolean isSuccess = true;
//		List<WebElement> names = null;
//		String beneficiaryName = TestDataContainer.GetParameterValue("FirstName")+" "+TestDataContainer.GetParameterValue("MiddleName")+" "+TestDataContainer.GetParameterValue("LastName");
//		
//		if(beneficiaryType.equalsIgnoreCase("Primary"))
//			names= this.lstlnkPrimaryBeneficiaryName;
//		else
//			names = this.lstlnkContingentBeneficiaryName;
//		
//		if(this.verifyBeneficiaryPresentInBeneficiaryTable()){
//			int noOfbeneficiaries = names.size();
//			for(int i=1; i<=noOfbeneficiaries;i++){
//				if(names.get(i).getText().contains(beneficiaryName))
//					names.get(i).click();
//			}
//		}
//		return isSuccess;
//	}
	
	/**<pre> Method to verify the details after adding a beneficiary.
	 *.</pre>
	 * @param attribute - beneficiary attribute to be verified
	 * 
	 * @return - boolean
	 */
	public boolean verifyBeneficiaryDetails(String attribute){
		
		
		boolean isSuccess = false;
		String beneficiaryName = Stock.GetParameterValue("FirstName")+" "+Stock.GetParameterValue("MiddleName")+" "+Stock.GetParameterValue("LastName");
		try {
			Thread.sleep(10000);
			Web.waitForElement(this.lblConfirmation);
		} catch (Exception e) {
			
		}
		
		if(Web.isWebElementDisplayed(this.lblConfirmation)){
			
				if(attribute.equalsIgnoreCase("Confirmation number"))
					if(lstTblConfirmationDetails.get(0).getText().split(":")[1] != null)
						return isSuccess;
				if(attribute.equalsIgnoreCase("Marital status"))
					return Web.VerifyText("Marital Status: "+Stock.GetParameterValue("Marital Status"), lstTblConfirmationDetails.get(1).getText(), true);
			
			
			for(int i=0;i<lstTablePrimaryBeneficiary.size();i++){
				if(lstTablePrimaryBeneficiary.get(i).getText().contains(beneficiaryName)){
						
					if(attribute.equalsIgnoreCase("Name"))
						return Web.VerifyText("Name: "+beneficiaryName+", "+Stock.GetParameterValue("Prefix"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[0], true);
					
					if(attribute.equalsIgnoreCase("Allocation"))	
						return Web.VerifyText("Allocation: "+Stock.GetParameterValue("Allocation")+"%", lstTablePrimaryBeneficiary.get(i).getText().split("\n")[1], true);
					
					if(attribute.equalsIgnoreCase("Relationship"))	
						return Web.VerifyText("Relationship: "+Stock.GetParameterValue("Beneficiary Relation"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[2], true);
					
					if(attribute.equalsIgnoreCase("SSN")){
//						return Web.VerifyText("SSN (LAST FOUR): "+Stock.GetParameterValue("ssn").split("-")[2], lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
						return Web.VerifyText("SSN (LAST FOUR): "+Stock.GetParameterValue("ssn").substring(Stock.GetParameterValue("ssn").length()-4), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
					}
					if(attribute.equalsIgnoreCase("DOB")){	
						return Web.VerifyText("DATE OF BIRTH: "+Stock.GetParameterValue("DOB"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[4], true);
					}
					if(attribute.equalsIgnoreCase("Phone Number"))	
						return Web.VerifyText("PHONE NUMBER: "+Stock.GetParameterValue("PhoneNumber"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[5], true);
					
					if(attribute.equalsIgnoreCase("Address")){
						String address= Stock.GetParameterValue("AddressOne")+" "+Stock.GetParameterValue("AddressTwo")+" "+Stock.GetParameterValue("City")+", "+Stock.GetParameterValue("Country")+", "+Stock.GetParameterValue("Zipcode").split("\\.")[0];
						System.out.println(address);
						System.out.println(lstTablePrimaryBeneficiary.get(i).getText().split("\n")[6]+" "+lstTablePrimaryBeneficiary.get(i).getText().split("\n")[7]+" "+lstTablePrimaryBeneficiary.get(i).getText().split("\n")[8]);
						return Web.VerifyText("Address: "+address, lstTablePrimaryBeneficiary.get(i).getText().split("\n")[6]+" "+lstTablePrimaryBeneficiary.get(i).getText().split("\n")[7]+" "+lstTablePrimaryBeneficiary.get(i).getText().split("\n")[8] ,true);
					}
				}
				
			}
		}
		
		return false;
	}
	
	public boolean verifyEntityDetails(String attribute){

		boolean isSuccess = false;
		try {
			Thread.sleep(5000);
			Web.waitForElement(this.lblConfirmation);
		} catch (Exception e) {
			
		}
		
		for(int i=0;i<lstTablePrimaryBeneficiary.size();i++){
			if(lstTablePrimaryBeneficiary.get(i).getText().contains(Stock.GetParameterValue("BeneficiaryName"))){
				if(attribute.equalsIgnoreCase("Name"))
					return Web.VerifyText("Name: "+Stock.GetParameterValue("BeneficiaryName"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[0], true);
				
				if(attribute.equalsIgnoreCase("Allocation"))	
					return Web.VerifyText("Allocation: "+Stock.GetParameterValue("Allocation")+"%", lstTablePrimaryBeneficiary.get(i).getText().split("\n")[1], true);
				
				if(attribute.equalsIgnoreCase("Relationship"))	
					return Web.VerifyText("Type: "+Stock.GetParameterValue("Beneficiary Relation"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[2], true);
				
				if(attribute.equalsIgnoreCase("TIN")){
//					return Web.VerifyText("SSN (LAST FOUR): "+Stock.GetParameterValue("ssn").split("-")[2], lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
					return Web.VerifyText("TIN (last four): "+Stock.GetParameterValue("Tax_Identification_No").substring(Stock.GetParameterValue("Tax_Identification_No").length()-4), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
				}
				if(attribute.equalsIgnoreCase("DOT")){	
					return Web.VerifyText("DATE OF TRUST: "+Stock.GetParameterValue("Date_of_Trust"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[4], true);
				}
			}
		}
		return false;
	}
	

	/**<pre> Method to enter the beneficiary details.
	 *.</pre>
	 */
	public void enterBeneficiaryDetails(){
		this.txtFirstName.sendKeys(Stock.GetParameterValue("FirstName"));
		this.txtMiddleName.sendKeys(Stock.GetParameterValue("MiddleName"));
		this.txtLastName.sendKeys(Stock.GetParameterValue("LastName"));
		this.txtSuffix.sendKeys(Stock.GetParameterValue("Prefix"));
		this.txtDateOfBirth.sendKeys(Stock.GetParameterValue("DOB"));
		System.out.println("SSn : "+Stock.GetParameterValue("SSN"));
//		this.txtSSN.sendKeys(Stock.GetParameterValue("SSN"));
		lib.Web.setTextToTextBox(txtSSN, Stock.GetParameterValue("SSN"));
		this.txtPhoneNumber.sendKeys(Stock.GetParameterValue("PhoneNumber"));
	
	}
	

	/**<pre> Method to enter the address details of a beneficiary.
	 *.</pre>
	 */
	public void enterAddressDetails(){
		this.txtAddressOne.clear();
		this.txtAddressOne.sendKeys(Stock.GetParameterValue("AddressOne"));
		this.txtAddressTwo.clear();
		this.txtAddressTwo.sendKeys(Stock.GetParameterValue("AddressTwo"));
		this.txtCity.clear();
		this.txtCity.sendKeys(Stock.GetParameterValue("City"));
		Web.selectDropDownOption(this.selState,Stock.GetParameterValue("State"));
		this.txtZipCode.clear();
		this.txtZipCode.sendKeys(Stock.GetParameterValue("Zipcode").split("\\.")[0]);
		Web.selectDropDownOption(this.selCountry,Stock.GetParameterValue("Country"));
	}
	
	public void enterEntityDetails(){
		lib.Web.setTextToTextBox(this.txtBeneficiaryName, Stock.GetParameterValue("BeneficiaryName"));
		lib.Web.setTextToTextBox(this.txtDateOfTrust, Stock.GetParameterValue("Date_of_Trust"));
		lib.Web.setTextToTextBox(this.txtTaxIdentificationNo, Stock.GetParameterValue("Tax_Identification_No"));
	}
	/**<pre> Method to delete beneficiaries from DB.
	 *.</pre>
	 * @param ssn - Partcipant ssn
	 * @param firstName - Participant First name
	 * 
	 * @return - boolean
	 * @throws Exception 
	 */
	public void deleteBeneficiariesFromDB(String ssn, String firstName) throws Exception{
		String[] sqlQuery;
		String[] sqlQuery_commit;
		sqlQuery = Stock.getTestQuery("deleteBeneficiaries");
		sqlQuery_commit = Stock.getTestQuery("deleteBeneficiaries_commit");
		
		DB.executeUpdate(sqlQuery[0], sqlQuery[1], ssn, firstName);
		DB.executeUpdate(sqlQuery_commit[0], sqlQuery_commit[1]);
		
	}
}
