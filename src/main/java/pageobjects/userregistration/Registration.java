
package pageobjects.userregistration;

import java.util.List;
import lib.Reporter;
import lib.Web;
import com.aventstack.extentreports.*;
import com.sun.org.apache.bcel.internal.generic.Select;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;






import org.testng.Assert;

import appUtils.Common;
import core.framework.Globals;
import pageobjects.login.LoginPage;

public class Registration extends LoadableComponent<Registration> {

	//Declarations
	private LoadableComponent<?> parent;
	
	//Objects and locators declaration
	
	@FindBy(xpath=".//*[text()[normalize-space()='Registration']]") private WebElement lblRegistrationHeader;
	
	//Provide personal information
	@FindBy(id="firstNameInput") private WebElement txtfirstName;
	@FindBy(id="middleNameInput") private WebElement txtMiddleName;
	@FindBy(id="lastNameInput") private WebElement txtLastname;
	@FindBy(id="dateOfBirth") private WebElement txtDob;
	@FindBy(id="genderInput") private WebElement selGender;
    @FindBy(id="ssnInput") private WebElement txtSSN;
	@FindBy(id="maritalStatusInput") private WebElement selMaritalStatus;
	
	//Empoyment information
	@FindBy(id="currentAnnualIncomeInput") private WebElement txtAnnualIncome;
	@FindBy(id="dateOfHire") private WebElement txtDateOfHire;
	
	//Provide mailing address
	@FindBy(id="addressLine1Input") private WebElement txtAddressLine1;
	@FindBy(id="addressLine2Input") private WebElement txtAddressLine2;
	@FindBy(id="cityInput") private WebElement txtCity;
	@FindBy(id="stateInput") private WebElement selState;
	@FindBy(id="zipcodeInput") private WebElement txtZipcode;
	@FindBy(id="countryInput") private WebElement selCountry;
	@FindBy(id="submit") private WebElement btnSubmit;
	
	
	/** Empty args constructor
	 * 
	 */
	
	public Registration() {
		this.parent = new LoginPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	
	public Registration(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblRegistrationHeader),"Registration page is Not Loaded");
		/*String sponser = this.lblSponser.getAttribute("Alt");
		if(sponser.isEmpty())
		{
			sponser=Common.GC_DEFAULT_SPONSER;
		}
		if (!Common.isCurrentSponser(sponser)) {
			Assert.assertTrue(Web.isWebElementDisplayed(txtPassword,true));

		} */
	}

	@Override
	protected void load() {
		
		this.parent.get();
		((LoginPage) this.parent).clickRegister();
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
	}
	
	
	
	/**Method to set details under 'Registration -Provide personal information
	 * 
	 */
	
	public void providePersonalInformation (String firstName,
												String middleName,
												String lastName,
												String dob,
												String gender,
												String sSN,
												String maritalStatus){
						
		Web.setTextToTextBox(txtfirstName, firstName);
		Web.setTextToTextBox(txtMiddleName, middleName);
		Web.setTextToTextBox(txtLastname, lastName);
		Web.setTextToTextBox(txtDob, dob);
		Web.selectDropDownOption(selGender, gender);
		Web.setTextToTextBox(txtSSN, sSN);
		Web.setTextToTextBox(selMaritalStatus, maritalStatus);

	}
	
	/**Method to set details under 'Registration -Employment information
	 * 
	 */
	
	public void provideEmploymentInformation(String annualIncome,
												String dateOfHire){
		
		Web.setTextToTextBox(txtAnnualIncome, annualIncome);
		Web.setTextToTextBox(txtDateOfHire, dateOfHire);
		
	}
	
	/**Method to set details under 'Registration -Provide mailing address
	 * 
	 */
	
	public void provideMailingAddress(String addressLine1,
										String addressLine2,
										String city,
										String state,
										String zip,
										String country){
		
		Web.setTextToTextBox(txtAddressLine1, addressLine1);
		Web.setTextToTextBox(txtAddressLine2, addressLine2);
		Web.setTextToTextBox(txtCity, city);
		Web.selectDropDownOption(selState, state);
		Web.setTextToTextBox(txtZipcode, zip);
		Web.selectDropDownOption(selCountry, country);
		Web.clickOnElement(btnSubmit);
				
	}
	
	
		
	


 
}
