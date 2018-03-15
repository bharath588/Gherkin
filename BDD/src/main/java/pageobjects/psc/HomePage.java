/**
 * 
 */
package pageobjects.psc;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.Stock;
import lib.Web;

import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.aventstack.extentreports.Status;

import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;
import reporter.Reporter;

/**
 * @author rvpndy
 *
 */
public class HomePage extends LoadableComponent<HomePage> {
	
	@FindBy(css="div[id='greeting'] span[class='label']") 
	private WebElement weGreeting;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(xpath="//div[@id='logo']/img")
	private WebElement homePageLogo;
	@FindBy(xpath=".//input[@id='planSearchAc_input']")
	private WebElement planSearchBox;
	@FindBy(xpath=".//button[@id='planSearchAutocompleteButton']")
	private WebElement btnPlanSearchTxtGO;
	@FindBy(id="termsconditions")
	private WebElement termsAndConditions;
	@FindBy(xpath="//ul[@id='footBottomLinks']/li")
	private List<WebElement> footerLinks;
	@FindBy(id="ifrmFooter")
	private WebElement tNcIframe;
	@FindBy(xpath="//div[contains(@class,'footerDialog')]//a")
	private WebElement closeFooterDialog;
	@FindBy(xpath=".//*[@id='main']/div")
	private WebElement sysReqDiv;
	@FindBy(xpath=".//div[contains(@class,'CopyRight') or contains(@class,'copyright')]")
	private WebElement copyrightText;
	@FindBy(xpath="//*[@id='newMenu']//li/a[text()='Reports']")
	private WebElement reportsMenu;
	@FindBy(xpath="//*[@id='newMenu']//li/ul//li/a[text()='Standard reports']")
	private WebElement standardReportsSubMenu;
	
	private LoadableComponent<?> parent;
	public static WebDriver webDriver;
	private String[] userVeriData;
	private boolean ifUserOrAccntVerificationMandate = false; 
	private String[] userData;
	private Method invokeMethod;
	
	public HomePage(LoadableComponent<?> parent,boolean performVerification,String... userData){
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData; 
		this.userVeriData = new String[2];		
		PageFactory.initElements(Web.getDriver(), this);
	}
	
	public HomePage(){
		PageFactory.initElements(Web.getDriver(), this);
	}
	
	public HomePage(WebDriver webDriver){
		HomePage.webDriver = webDriver;
		this.parent = new LoginPage(webDriver);
		PageFactory.initElements(webDriver, this);
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		this.parent.get();
		UserVerificationPage userVeriPg = new UserVerificationPage();
		LoginPage loginObj =  new LoginPage();
		try{			
			if(ifUserOrAccntVerificationMandate) {	// Performs User or Account Verification 		
				invokeMethod = this.parent.getClass().
						getDeclaredMethod("performVerification", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),
						new Object[]{userData});			
			}else{
				//Performing Login
				Object login = this.parent.getClass().newInstance(); 				
				Web.getDriver().switchTo().frame(Web.returnElement(login,"LOGIN FRAME"));
				Web.waitForElement(login,"USERNAME");
				Web.waitForElement(login,"PASSWORD");
				Web.getDriver().switchTo().defaultContent();
				invokeMethod = this.parent.getClass().getDeclaredMethod("submitLoginCredentials", 
						String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),new Object[]{userData});
				loginObj.waitForSuccessfulLogin();
			if(Web.isWebElementDisplayed(Web.returnElement(userVeriPg,"EMAIL ADDRESS"))){
				userVeriData = new String[]{"discard@gwl.com",
						userVeriPg.getSecurityAnswer((Web.returnElement(userVeriPg, 
								"SECURITYQUESTION")).getText().trim())};
				//				   userVeriData[0] = Stock.GetParameterValue("userVerificationEmail");
				//				   userVeriData[1] = getSecurityAnswer((Web.returnElement(userVeriPg, "SECURITYQUESTION")).getText().trim());
				userVeriPg.performVerification(userVeriData);
				
			}
		} }catch (Exception e) {
			try {
				throw new Exception("Login to PSC failed , Error description : "+ e.getMessage());
			} catch (Exception t) {			
				ThrowException.Report(TYPE.EXCEPTION,t.getMessage());
			}
		}
		JumpPage jumpPage = new JumpPage();
		if(Web.isWebElementDisplayed(jumpPage, "JUMP PAGE URL", false))
			try {
				Web.clickOnElement(Web.returnElement(jumpPage, "JUMP PAGE URL"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		if(!Web.isWebElementDisplayed(weGreeting)){
			throw new AssertionError("Plan service center landing page not loaded.");
		}
		
		
	}
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 *  </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		
		if(fieldName.trim().equalsIgnoreCase("Reports Menu")){
			Web.getDriver().switchTo().defaultContent();
			return this.reportsMenu;
		}
		if(fieldName.trim().equalsIgnoreCase("Standard Reports")){
			return this.standardReportsSubMenu;
		}
		return null;
		
	}
	
	public void switchPlan(String planNumber){
		try{
		planSearchBox.sendKeys(planNumber);
		btnPlanSearchTxtGO.click();
		Reporter.logEvent(Status.INFO, "Switched to plan:", planNumber, true);
		Thread.sleep(5000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isSalesForcePage(){
		if(Web.getDriver().getCurrentUrl().contains("devthree-retirementpartner.cs8.force.com/partner/apex/PartnerPortalHome"))
			return true;
		return false;
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
			Reporter.logEvent(Status.INFO, "Footer links on home page are:", 
					footerLink, true);
			if(!footerLink.contains("Terms and Conditions"))
				return true;
		}
		return false;
	}
	
	public void clickOnTermsAndConditionLink(){
		Web.clickOnElement(termsAndConditions);
	}
	
	public void clickOnFooterLink(String footerLink){
		if(Web.isWebElementsDisplayed(footerLinks)){
			for (WebElement ele : footerLinks){
				if(ele.getText().contains(footerLink)){
					Web.clickOnElement(ele);
					break;
				}
			}
		}
	}
	
	public boolean isSysReqDisplayed(){
		try{
			Web.FrameSwitchONandOFF(true, tNcIframe);
			if(Web.isWebElementDisplayed(sysReqDiv, true)){
				Reporter.logEvent(Status.INFO, "System Requirement marking text:", 
						sysReqDiv.getText(), true);
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean isCurrentYearDisplayedInCopyright(String expCopyright){
		try{
			System.out.println("copy:"+copyrightText.getText()+"And exp:"+expCopyright);
			if(Web.isWebElementDisplayed(copyrightText, true)
					&&copyrightText.getText().contains(expCopyright)){
				int year = extractInt(copyrightText.getText());
				System.out.println("Extracted year is "+year);
				if(year==Calendar.getInstance().get(Calendar.YEAR))
					return true;
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static int extractInt(String str) {
        Matcher matcher = Pattern.compile("\\d+").matcher(str);

        if (!matcher.find())
            throw new NumberFormatException("For input string [" + str + "]");

        return Integer.parseInt(matcher.group());
    }

}
