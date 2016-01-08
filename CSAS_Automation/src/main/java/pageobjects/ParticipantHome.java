package pageobjects;

import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;

public class ParticipantHome extends LoadableComponent<ParticipantHome> {

	@FindBy(name = "username")
	private WebElement userNameField;

	@FindBy(name = "password")
	private WebElement passWordField;

	// Account Balance..
	
	@FindBy(css = "td.colTitle.balance")
	private WebElement tdParticipantBalance;

	@FindBy(css = "td.data.balance>a.floatingDiv")
	private WebElement lnkHoverablePlanBalance;

	@FindBy(css = "div#overDiv[style *= 'visible']>table")
	private WebElement lnkHoverablePlanBalanceAfterHover;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Vested Balance')]")
	private WebElement tbVestedBal;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Non-Vested Balance')]")
	private WebElement tbNonVestedBal;

	@FindBy(xpath = "//table[@class = 'compactDataTable']//td[contains(text(),'Current Balance')]")
	private WebElement tbCurrentBal;
	
	// Required List implementaions..

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(2)>td")
	private WebElement tbTotalVarBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(3)>td")
	private WebElement tbTotalFixBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(4)>td")
	private WebElement tbTotalExcludingLoans;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(5)>td")
	private WebElement tbTotalLoanBal;

	@FindBy(css = "td[valign='top'] table.compactDataTable tr:nth-of-type(6)>td")
	private WebElement tbTotalIncludingLoan;

	@FindBy(xpath = "//td[contains(text(),'Total Balance:')]")
	private WebElement participantTotalBalLabel;

	@FindBy(css = "td.total.balance")
	private WebElement participantTotalBalVal;

	// Personal Data...
	// Required list implementation..

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Name:')]")
	private WebElement participantNameLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(3)>td.data")
	private WebElement participantName;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'SSN')]")
	private WebElement participantSSNLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(4)>td.data")
	private WebElement participantSSN;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Date Of Birth')]")
	private WebElement participantDOBLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(5)>td.data")
	private WebElement participantDOB;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Gender')]")
	private WebElement participantGenderLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(6)>td.data")
	private WebElement participantGender;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Address')]")
	private WebElement participantAddLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(7)>td.data")
	private WebElement participantAdd;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Mail Hold')]")
	private WebElement participantMailLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(8)>td.data")
	private WebElement participantMail;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Cash Hold Status')]")
	private WebElement participantCHSLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(9)>td.data")
	private WebElement participantCHS;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Web Registration Status')]")
	private WebElement participantRegStatusLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(11)>td.data")
	private WebElement participantRegStatus;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'Managed Account Status')]")
	private WebElement participantMngAccStsLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(12)>td.data")
	private WebElement participantMngAccSts;

	@FindBy(xpath = "//table[@name = 'pptinfo']//td[contains(text(),'SecureFoundation Status')]")
	private WebElement participantSecFoundationStsLabel;

	@FindBy(css = "table[name = 'pptinfo'] tr:nth-of-type(13)>td.data")
	private WebElement participantSecFoundationSts;

//Order PIN..	
	
	@FindBy(css = "table.compactDataTable tr:nth-of-type(7) a:nth-of-type(2)")
	private WebElement lnkOrderPIN;

	@FindBy(css = "table#table_popupLayout tr:nth-of-type(2) span.titleShadow")
	private WebElement OrderPINTitle;
	
	@FindBy(css = "input[value = 'Mail Existing PIN']")
	private WebElement btnOPMailExistingPIN;
	
	@FindBy(css = "input[value = 'Order Temp PIN']")
	private WebElement btnOPOrderTempPin;
	
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(8)")
	private WebElement EmploymentStatusLabel;

//implement List...	
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(8)>a")
	private WebElement lnkEmploymentStatus;
	
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(9)")
	private WebElement PDILabel;

//implement List...	
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(9)")
	private WebElement PDIStatus;
	
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td[class = 'colTitle centered']:nth-of-type(10)")
	private WebElement InstanceLabel;

//implement List...	
	@FindBy(css = "div.dataContainerBody td:nth-of-type(2) td:nth-of-type(10)")
	private WebElement InstanceStatus;
	
	
	
	
	
	@Override
	protected void isLoaded() throws Error {

	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
	}

}
