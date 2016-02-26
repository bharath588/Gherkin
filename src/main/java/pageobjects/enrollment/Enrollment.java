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
	@FindBy(id="//a[text()='Skip & View My Account']") private WebElement btnSkipAndViewAccount;
	/** Empty args constructor
     * 
     */
    public Enrollment() {
    	this.parent = new TwoStepVerification();
    	PageFactory.initElements(lib.Web.webdriver, this);
    }
    
    public Enrollment(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
    
    @Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(lib.Web.isWebElementDisplayed(hdrEnrollment));

	}
    
    @Override
	protected void load() {
		this.parent.get();
		TwoStepVerification mfaScreen = (TwoStepVerification) this.parent;
		
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
    	lib.Web.waitForElement(btnSkipAndViewAccount);
    	lib.Web.clickOnElement(btnSkipAndViewAccount);
    }
}
