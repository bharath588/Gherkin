/**
 * 
 */
package pscBDD.forgotPassword;

import java.util.List;






import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pscBDD.login.LoginPage;
import bdd_lib.Web;
import bdd_reporter.Reporter;

import com.aventstack.extentreports.Status;



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
	
	@FindBy(id="footRight")
	private WebElement contactUs;
	//@FindBy(xpath="//*[@id='footRight']//p")
	//private WebElement contactUsInstJpmInstFtb;
	@FindBy(xpath="//*[@id='footRight']//*[contains(text(),'Contact') or @class='contactNumber']")
	private List<WebElement> contactUsInstAF;

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
	
	public boolean isContactUsSectionDisplayedCorrectMessage(
			String contactUsText) {
		try {
			WebElement element = null;
			String appText = "";
			String[] replacements = { "Need help?", "TPA Resource Center",
					"Facebook", "Twitter", "LinkedIn", "Instagram", "YouTube",
					"You Tube" };

			if (LoginPage.accucode.equals("InstAF")) {
				List<WebElement> elementContactUs = contactUsInstAF;
				System.out.println(elementContactUs.size());
				appText = elementContactUs.get(0).getText() + " "
						+ elementContactUs.get(1).getText();
				appText = appText.replace("\n", " ").replace("\r", "").trim();
			} else {
				element = contactUs;
				appText = element.getText().replace("\n", " ")
						.replace("\r", "").trim();
			}
			for (String replacement : replacements) {
				appText = appText.replace(replacement, "").trim();
			}
			System.out.println(appText.replace("  ", " "));
			System.out.println(contactUsText.replace("'", "").trim());
			if (appText.replace("  ", " ").equalsIgnoreCase(
					contactUsText.replace("'", "").trim()))
				return true;

		} catch (NoSuchElementException ele) {
			ele.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	

}
