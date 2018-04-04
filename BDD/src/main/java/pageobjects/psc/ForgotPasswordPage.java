/**
 * 
 */
package pageobjects.psc;

import java.util.List;

import lib.Web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.aventstack.extentreports.Status;

import reporter.Reporter;

/**
 * @author rvpndy
 *
 */
public class ForgotPasswordPage extends LoadableComponent<ForgotPasswordPage> {

	@FindBy(id="termsconditions")
	private WebElement termsAndConditions;
	@FindBy(xpath="//ul[@id='footBottomLinks']/li")
	private List<WebElement> footerLinks;
	@FindBy(id="mpwr_login")
	private WebElement loginIframe;
	@FindBy(id="ifrmFooter")
	private WebElement tNcIframe;
	@FindBy(xpath="//div[contains(@class,'footerDialog')]//a")
	private WebElement closeFooterDialog;
	@FindBy(xpath=".//*[@id='main']/div")
	private WebElement sysReqDiv;

	public static WebDriver webDriver;
	LoadableComponent<?> parent;



	public ForgotPasswordPage() {
		PageFactory.initElements(Web.getDriver(), this);
		this.parent = new LoginPage();

	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Web.getDriver().getCurrentUrl().contains("forgotPassword.xhtml"))
			Reporter.logEvent(Status.INFO, "Forgot password page is loaded", 
					"Forgot password page is loaded", true);
		else
			throw new AssertionError("Forgot password page is not loaded");
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		try{
			this.parent.get();
			Web.FrameSwitchONandOFF(true, loginIframe);
			if(Web.isWebElementDisplayed(Web.returnElement(this.parent, "Forgot Password"), true))
				Web.clickOnElement(Web.returnElement(this.parent, "Forgot Password"));
			Web.FrameSwitchONandOFF(false);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isTnCOpensInNewWindow(){
		
		if(Web.isWebElementDisplayed(tNcIframe, true)){
			Web.clickOnElement(closeFooterDialog);
			return true;
		}
		return false;
	}
	
	public boolean isTnCDisplayedInSysTray(){
		String footerLink="";
		if(Web.isWebElementsDisplayed(footerLinks)){
			for (WebElement ele : footerLinks){
				footerLink+=ele.getText()+" ";
			}
			Reporter.logEvent(Status.INFO, "Footer links on forgot password page are:", 
					footerLink, true);
			if(!footerLink.contains("Terms and Conditions"))
				return true;
		}
		return false;
	}

	public void clickOnTnCLink() {
		// TODO Auto-generated method stub
		Web.clickOnElement(termsAndConditions);
	}
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName){
		if (fieldName.trim().equalsIgnoreCase("System Requirement Window")) {
			return this.sysReqDiv;
		}
		if(fieldName.trim().equalsIgnoreCase("Footer iFrame"))
			return this.tNcIframe;
		return null;
	}
	
	public boolean isLinkDisplayed(String footerLinkName){
		try {
			//Thread.sleep(1000);
			for(WebElement link : footerLinks){
				System.out.println(link.getText());
				if(link.getText().trim().contains(footerLinkName.trim()))
					return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean isForgotPwdPagePostPopUpClose(){
		try {
			Web.FrameSwitchONandOFF(false, tNcIframe);
			Web.clickOnElement(closeFooterDialog);
			Thread.sleep(1000);
			if(Web.getDriver().getCurrentUrl().contains("forgotPassword.xhtml"))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

}
