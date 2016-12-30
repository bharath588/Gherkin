package pageobjects.enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.general.LeftNavigationBar;
import pageobjects.login.TwoStepVerification;

public class Enrollment extends LoadableComponent<Enrollment> {
	
//Declarations
	private LoadableComponent<?> parent;
	//private static boolean waitforLoad = false;
	
	@FindBy(xpath="//h1[contains(text(),'Enrollment')]") private WebElement hdrEnrollment;
	@FindBy(id="home") private WebElement lnkHome;
	@FindBy(id="btn-enroll submit") private WebElement btnEnrollSubmit;
	@FindBy(id="btn-enroll submit") private WebElement hdrEnrollmentDetails;
	@FindBy(xpath="//div[@class='row']//a[text()='Skip & View My Account']") private WebElement btnSkipAndViewAccount;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;

	/** Empty args constructor
     * 
     */
	 /** Empty args constructor
     * 
     */
    public Enrollment() {
    	this.parent = new TwoStepVerification();
    	PageFactory.initElements(lib.Web.getDriver(), this);
    }
    
    /** Constructor taking parent as input
     * 
     * @param parent
     */
	public Enrollment(LoadableComponent<?> parent){
		//this.driver = DriveSuite.webDriver;
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {

		Assert.assertTrue(Web.isWebElementDisplayed(this.hdrEnrollment),"Enrollment Page is Not Loaded");
		
		
	}

	@Override
	protected void load() {
		TwoStepVerification mfaScreen = (TwoStepVerification) this.parent;
		this.parent.get();
		
		if (!Web.isWebElementDisplayed(this.lnkHome)) {
			mfaScreen.selectCodeDeliveryOption("ALREADY_HAVE_CODE");
			try {
				mfaScreen.submitVerificationCode(Stock.getConfigParam("defaultActivationCode"), true, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
    
    public void quickEnroll(){
    	lib.Web.clickOnElement(btnEnrollSubmit);
//    	lib.Web.waitForElement(btnSkipAndViewAccount);
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(lib.Web.isWebElementDisplayed(btnSkipAndViewAccount));
    	lib.Web.clickOnElement(btnSkipAndViewAccount);
    }
}
