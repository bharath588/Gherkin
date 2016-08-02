package pageobjects.homepage;

import java.lang.reflect.Method;
import java.util.List;

import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import pageobjects.login.LoginPage;
import pageobjects.userverification.UserVerificationPage;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

public class HomePage extends LoadableComponent<HomePage>{
	//Object Declarations 

	@FindBy(css="div[id='footContainer'] ul[id='footBottomLinks'] li") private List<WebElement> wePostFooterLinkListLogin;
	@FindBy(css="div[id='greeting'] span[class='label']") private WebElement weGreeting;
	@FindBy(css="table[class='verificationTable']") private WebElement tabUserVerification;
	@FindBy(css="input[class*='emailBox']") private WebElement txtUserVerificationEmail;
	@FindBy(css="input[class*='answerBox']") private WebElement txtUserVerificationSecAns;
	@FindBy(css="button[class*='btn-u next ui-button']") private WebElement btnUserVerificationNxt;
	@FindBy(css="iframe[id='ifrmFooter']") private WebElement frmModalWindow;
	@FindBy(css="section[id='main'] > div[class='row'] h1[class='text-red flush-top']") private WebElement weModalWindowHeadertxt;
	@FindBy(css="div[class*='footerDialog ui-draggable'] a[class*='close']") private WebElement linkModalClose;
	@FindBy(css = "a[id = 'profileLink']") private WebElement myProfileLink;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(xpath=".//a[@id='logOutLink']") private WebElement logoutLink;

	/*-----------------------------------------------------------------*/
	private LoadableComponent<?> parent;
	private boolean ifUserOrAccntVerificationMandate = false; 
	private Method invokeMethod;
	private Method invokeMethodforUserVerification;
	private String[] userData;
	private String[] userVeriData;
	
	// userVeriData is optional for HomePage constructor
	public HomePage(LoadableComponent<?> parent,boolean performVerification,String... userData){
		this.parent = parent;
		this.ifUserOrAccntVerificationMandate = performVerification;
		this.userData = userData; 
		this.userVeriData = new String[2];		
		PageFactory.initElements(Web.webdriver, this);
	}
	
	public HomePage(){
		PageFactory.initElements(Web.webdriver, this);
	}
	
	
	@Override
	protected void isLoaded() throws Error {	
	    if(!Web.isWebElementDisplayed(weGreeting)){
	    	throw new AssertionError("Plan service center landing page not loaded.");
	    }else{
	    //	Reporter.logEvent(Status.PASS, "Check if Home page is loaded","Home page has loaded successfully",false);
	    }	
	}

	@Override
	protected void load() {		
		this.parent.get();
		UserVerificationPage userVeriPg = new UserVerificationPage();
		LoginPage loginObj =  new LoginPage();
		try{			
			if(ifUserOrAccntVerificationMandate) {	// Performs User or Account Verification 		
				invokeMethod = this.parent.getClass().getDeclaredMethod("performVerification", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),new Object[]{userData});			
			}else{
				//Performing Login
				Object login = this.parent.getClass().newInstance(); 				
				Web.webdriver.switchTo().frame(Web.returnElement(login,"LOGIN FRAME"));
				Web.waitForElement(login,"USERNAME");
				Web.waitForElement(login,"PASSWORD");
				Web.webdriver.switchTo().defaultContent();
				invokeMethod = this.parent.getClass().getDeclaredMethod("submitLoginCredentials", String[].class);
				invokeMethod.invoke(this.parent.getClass().newInstance(),new Object[]{userData});
				loginObj.waitForSuccessfulLogin();
				//Check if UserVerification Pages appears then performVerification
				if(Web.isWebElementDisplayed(Web.returnElement(userVeriPg,"EMAIL ADDRESS"))){
				   userVeriData[0] = userVeriPg.getEmailAddressOfuser(Stock.getTestQuery("getEmailaddressQuery"),
							Stock.GetParameterValue("username"));
				   userVeriData[1] = Stock.GetParameterValue("userVerificationAns");
				   invokeMethodforUserVerification = userVeriPg.getClass().getDeclaredMethod("performVerification",String[].class);
				   invokeMethodforUserVerification.invoke(userVeriPg,new Object[]{userVeriData});
				}else{
					//navigate to Home
				}
			}
			urlJumpPage.click();
			Web.waitForElement(weGreeting);
			Reporter.logEvent(Status.INFO, "Check if Login is successfull","Login for PSC is successfull",false);
		} catch (Exception e) {
			try {
				throw new Exception("Login to PSC failed , Error description : "+ e.getMessage());
			} catch (Exception t) {			
				ThrowException.Report(TYPE.EXCEPTION,t.getMessage());
			}
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

		if (fieldName.trim().equalsIgnoreCase("MY PROFILE")) {
			return this.myProfileLink;
		}
		if (fieldName.trim().equalsIgnoreCase("JUMP_PAGE_NXTGEN_LINK")) {
			return this.urlJumpPage;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGOUT_LINK")) {
			return this.logoutLink;
		}
		return null;
	}
		
	/**Method to open each footer link after login and validate if the 
	 * respective page is opening
	 * 
	 * @throws Error 
	 */	
	public void checkFooterLinkPostLogin(List<String> FooterLinks) {				
		List<WebElement> footerLinks = wePostFooterLinkListLogin;
		WebElement footerlink; 
		boolean textMatch = false;
		String footerLinkText =null ;
		String testData = null;
		String modalWindowHeaderText = null;
		try{
			/*if(FooterLinks.size()!=footerLinks.size()){
				throw new Exception("Post login footer links doesnt match");
			}*/
			for(int iLoopCnt=0;iLoopCnt<=footerLinks.size()-1;iLoopCnt++){
				Web.waitForElement(footerLinks.get(iLoopCnt).findElement(By.cssSelector("a")));				
				footerlink =  footerLinks.get(iLoopCnt).findElement(By.cssSelector("a"));
				footerLinkText = footerlink.getText();
				
					if(FooterLinks.get(iLoopCnt).equals(footerlink.getText())){	
						Web.clickOnElement(footerlink);		
						//Thread.sleep(2000);
											
						if(Web.isWebElementDisplayed(frmModalWindow,true)){
							Web.webdriver.switchTo().frame(frmModalWindow);
							//testData = TestDataContainer.GetParameterValue("link_Footer"+(iLoopCnt+1)); 
							testData=Stock.GetParameterValue("link_Footer"+(iLoopCnt+1));
							
							if(testData.equalsIgnoreCase("PSCUserAuthorizationForm")){
								Web.webdriver.switchTo().defaultContent();	
								textMatch = Web.VerifyPartialText(testData, frmModalWindow.getAttribute("src"),true);
								modalWindowHeaderText = "PSC User Authorization Form";
							}else{
								textMatch = Web.VerifyPartialText(testData, weModalWindowHeadertxt.getText(),true);
								modalWindowHeaderText = weModalWindowHeadertxt.getText();
								Web.webdriver.switchTo().defaultContent();								
							}							
							linkModalClose.click();						
						}else{
							Reporter.logEvent(Status.FAIL,"Verify if respective modal window opens from footer link", 
							"No such modal window opended for " + footerLinkText + " link", true);
						}
					}
				if(textMatch)Reporter.logEvent(Status.PASS,"Verify if footer link opens respective modal window", 
			    "Modal window " +  modalWindowHeaderText+ " opended from footer link : " + footerLinkText, false);	
				
				if(!textMatch)Reporter.logEvent(Status.FAIL,"Verify if footer link opens respective modal window", 
				"No Modal window from footer link : " + footerLinkText, true);
				textMatch = false;
			}
		}catch(Exception e){
			Reporter.logEvent(Status.FAIL,"Verify if footer link opens respective modal window", 
					"Exception Occurred while checking footer links : "+e.getMessage() , true);
		}
	}
	
    public void validate_if_homepage_loaded(String ifSingleSiteUser) throws Exception {
        if(ifSingleSiteUser.equalsIgnoreCase("false")){
               if(Web.isWebElementDisplayed(urlJumpPage, true)){
                     urlJumpPage.click();
                     Web.waitForElement(weGreeting);
                     isLoaded();
               }else{
                     throw new Exception("Expected Jump page not loaded");
               }                                 
        }else if (ifSingleSiteUser.equalsIgnoreCase("true")){
               Web.waitForElement(weGreeting);
               isLoaded();
        }
 }
 
 public void logoutPSC(){
        if(Web.isWebElementDisplayed(logoutLink,true)){
               logoutLink.click();
               Reporter.logEvent(Status.PASS,"Perform logout of PSC","Logged out of PSC successfully",false);
        }else{
               Reporter.logEvent(Status.FAIL,"Perform logout of PSC","Unable to log out of PSC application",true);
        }
 }

}
