package pageobjects.login;

import java.util.ArrayList;
import java.util.LinkedList;
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
import org.testng.Assert;
import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;
import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

public class LoginPage extends LoadableComponent<LoginPage> {

	// Object Declarations

	@FindBy(id = "username")
	private WebElement txtUserName;
	@FindBy(id = "password")
	private WebElement txtPassword;
	@FindBy(id = "helpBlock")
	private WebElement weHelpBlock;
	@FindBy(xpath = ".//*[@href='#/loginHelp' and text()='Forgot Password?']")
	private WebElement lnkForgotPassword;
	@FindBy(css = "a[href*='register']")
	private WebElement btnRegister;
	@FindBy(css = "footer[class='page-footer'] div[class='nav-disclosure'] ul li")
	private List<WebElement> weFooterLinkListPreLogin;
	@FindBy(css = "div[class='col-sm-6 col-md-5 no-pad-right'] span[class='tagline text-uppercase']")
	private WebElement wePSCHeader;
	@FindBy(css = "div[class='breadcrumbs'] strong")
	private WebElement wePSCBreadCrum;
	@FindBy(css = "div[class='breadcrumbs'] a")
	private WebElement linkPSCBreadCrumHome;
	@FindBy(css = "iframe[id='mpwr_login']")
	private WebElement frmLogin;
	@FindBy(css = "button[id='login']")
	private WebElement btnLogin;
	@FindBy(css = "ul[class='list-inline'] li")
	private List<WebElement> weHeaderLinkListPreLogin;
	@FindBy(css = "site-header span[class='site-tagline ng-binding']")
	private WebElement weSiteTagLine;
	@FindBy(xpath = ".//*[@id='headNav']/li[2]/a")
	private WebElement linkLogOut;
	@FindBy(css = "input[class = 'chekbox']")
	private WebElement chkBoxrememberPassword;
	@FindBy(css = "div[id = 'errorMessage']")
	private WebElement wePreLoginError;
	@FindBy(css = "a[class = 'prelogin-pod']")
	private WebElement lnkPlanFeeds;
	@FindBy(css = "img[src *= 'redirect_logo.png']")
	private WebElement imgGWRS;
	@FindBy(css = "input[id = 'welcomeForm:username']")
	private WebElement txtForceLoginUsernameField;
	@FindBy(css = "img[alt = 'Empower Retirement']")
	private WebElement logoEmpower;
	@FindBy(xpath = ".//*[@id='loginSpinner']")
	private WebElement loginSpinner;
	@FindBy(xpath = ".//iframe[@id='fund_iframe']")
	private WebElement fundIFrame;
	@FindBy(xpath = ".//table[@id='alphabetTable']")
	private WebElement tabAlphabet;
	@FindBy(xpath = ".//div[@id='defaultErrorMessage']//span[contains(text(),'Invalid fund ID')]")
	private WebElement invalidFundErr;
	@FindBy(xpath = "html/body")
	private WebElement fundProspectusText;
	@FindBy(xpath = ".//a[starts-with(text(),'Prospectus')]")
	private WebElement fundNav_Prospectus;
	@FindBy(xpath = ".//a[starts-with(text(),'Annual Report')]")
	private WebElement fundNav_AnnualReport;
	@FindBy(xpath = ".//a[starts-with(text(),'Semi Annual Report')]")
	private WebElement fundNav_SemiAnnualReport;
	@FindBy(xpath = ".//a[starts-with(text(),'Statement of Additional Information')]")
	private WebElement fundNav_SOAI;
	@FindBy(xpath = ".//iframe[@id='documentContentFrame']")
	private WebElement docContentIFrame;
	@FindBy(xpath = ".//table[@class='fundProspectusTable']")
	private WebElement fundTable;
	
	

		
	LoadableComponent<?> parent;
	/*-----------------------------------------------------------------*/

	private List<String> getFooterLinkList = null;

	public LoginPage() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertEquals(Web.webdriver.getTitle(), "Empower Retirement - Plan Service Center");
	}

	@Override
	protected void load() {
		Web.webdriver.get(Stock.getConfigParam("AppURL"+"_"+Stock.getConfigParam("TEST_ENV")));	
	}

	/**
	 * <pre>
	 * Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	USERNAME - [TEXTBOX]
	 * 	PASSWORD - [TEXTBOX]
	 *  SIGN IN -[BUTTON]
	 *  REGISTER - [BUTTON]
	 *  FORGOT PASSWORD - [LINK]
	 * </pre>
	 * 
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		// Username
		if (fieldName.trim().equalsIgnoreCase("USERNAME")) {
			return this.txtUserName;
		}

		if (fieldName.trim().equalsIgnoreCase("PASSWORD")) {
			return this.txtPassword;
		}

		if (fieldName.trim().equalsIgnoreCase("SIGN IN")) {
			return this.btnLogin;
		}

		if (fieldName.trim().equalsIgnoreCase("REGISTER")) {
			return this.btnRegister;
		}

		if (fieldName.trim().equalsIgnoreCase("FORGOT PASSWORD")) {
			return this.lnkForgotPassword;
		}

		if (fieldName.trim().equalsIgnoreCase("FORCELOGIN USERNAME")) {
			return this.txtForceLoginUsernameField;
		}

		if (fieldName.trim().equalsIgnoreCase("GWRS IMAGE")) {
			return this.imgGWRS;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGIN FRAME")) {
			return this.frmLogin;
		}
		if (fieldName.trim().equalsIgnoreCase("LOGO EMPOWER")) {
			return this.logoEmpower;
		}

		// Reporter.logEvent(
		// Status.WARNING,
		// "Get WebElement for field '" + fieldName + "'",
		// "No WebElement mapped for this field\nPage: <b>
		// "+this.getClass().getName()+"</b>"
		// ,true);
		return null;

	}

	/**
	 * Method to enter user credentials and click on Sign In button
	 * 
	 * @param userName
	 * @param password
	 * @throws InterruptedException
	 */
	public void submitLoginCredentials(String[] loginData) {
		Web.webdriver.switchTo().frame(frmLogin);
		// common.webDriver.switchTo().frame(frmLogin);
		Web.setTextToTextBox(this.txtUserName, loginData[0]);
		Web.setTextToTextBox(this.txtPassword, loginData[1]);
		Web.clickOnElement(this.btnLogin);
		Web.webdriver.switchTo().defaultContent();
		// driver.switchTo().defaultContent();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			ThrowException.Report(TYPE.INTERRUPTED, "Exception occurred for thread sleep");
		}
	}

	/**
	 * <pre>
	 * Method to validate if entered login credentials are invalid and an error message is displayed
	 * Returns the error message displayed if an error block is displayed
	 * Returns empty string if no error block is displayed
	 * </pre>
	 * 
	 * @return String - Displayed error message
	 */
	public String getErrorMessageText() {
		boolean isElementPresent = Web.isWebElementDisplayed(this.weHelpBlock);
		if (isElementPresent)
			return this.weHelpBlock.getText();
		else
			return "";
	}

	/**
	 * Method to open each footer link before login and validate if the
	 * respective page is opening
	 * 
	 * @throws Exception
	 */
	public void checkFooterLinkPreLogin() throws Exception {
		checkHeaderFooterLink(weFooterLinkListPreLogin, "link_Footer");
	}

	/**
	 * Method to open each header link before login and validate if the
	 * respective page is opening
	 */
	public void checkHeaderLinkPreLogin() {
		checkHeaderFooterLink(weHeaderLinkListPreLogin, "link_Header");
		Web.webdriver.navigate().back();
	}

	/**
	 * Method to get link text for each footer link before link
	 */
	private void getPreLoginFooterList() {
		List<String> footerLinkText = new LinkedList<String>();
		for (int iLoopCnt = 0; iLoopCnt <= weFooterLinkListPreLogin.size() - 1; iLoopCnt++) {
			footerLinkText.add(weFooterLinkListPreLogin.get(iLoopCnt).findElement(By.cssSelector("a")).getText());
		}
		setPreLoginFooterLinkList(footerLinkText);
	}

	/**
	 * This method verifies the error messages pre login for invalid credentials
	 * @param errorMsg
	 */
	public void verifyErrorforRespectiveLogin(String errorMsg) {
		boolean textMatch = false;
		try {
			new LoginPage();
			Web.webdriver.switchTo().frame(frmLogin);			
			textMatch = Web.VerifyPartialText(errorMsg, wePreLoginError.getText().replace("\n", ""), true);
		
			if(textMatch){
				Reporter.logEvent(Status.PASS, "Check if the appropiate error displayed",
						"Expected Error message displayed", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Check if the appropiate error displayed",
						"Failed to display expected error message", true);
			}		
			Web.webdriver.switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for getFooterLinkList
	 */
	public List<String> getPreLoginFooterLinkList() {
		getPreLoginFooterList();
		return getFooterLinkList;
	}

	/**
	 * Setter for getFooterLinkList
	 */
	public void setPreLoginFooterLinkList(List<String> getFooterLinkList) {
		this.getFooterLinkList = getFooterLinkList;
	}

	/**
	 * Method to click on each Header and Footer links and validate if the
	 * redirection is correct
	 * 
	 * @param linkObjList
	 *            : WebElement list
	 * @param testDataColNm
	 *            : Test Data column name
	 * 
	 */
	private void checkHeaderFooterLink(List<WebElement> linkObjList, String testDataColNm) {
		String currentURL = "";
		String testData;
		int linkObjSize = linkObjList.size();
		List<WebElement> fundsList = null;
		WebElement fundLink = null;
		String[] charList = null;
		int fundLinkSize = 0;
		String charItem;
		try{
			 //Looping through the list of Header/Footer links
			for (int iLoopCnt = 0; iLoopCnt <= linkObjSize - 1; iLoopCnt++) {
				new LoginPage();
				
				//try-catch : To handle scenario - where only a specific link needs to be tested as specified in  test data
				try{					
					testData = Stock.GetParameterValue(testDataColNm + (iLoopCnt + 1));
				}catch(Error e){
					continue;
				}				
				Thread.sleep(500);
				if (Web.isWebElementDisplayed(linkObjList.get(iLoopCnt).findElement(By.cssSelector("a")), true)) {					
					if(linkObjList.get(iLoopCnt)
							       .findElement(By.cssSelector("a"))
							       .getAttribute("href")
							       .contains(testData)){
					  Web.clickOnElement((linkObjList.get(iLoopCnt).findElement(By.cssSelector("a"))));					  
					}					
				}
				
				if ((testDataColNm + (iLoopCnt + 1)).equals("link_Footer7")) {
					String winHandleBefore = Web.webdriver.getWindowHandle();
					for (String winHandle : Web.webdriver.getWindowHandles()) {
						Web.webdriver.switchTo().window(winHandle);
						currentURL = Web.webdriver.getCurrentUrl();
					}
					Web.webdriver.close();
					Web.webdriver.switchTo().window(winHandleBefore);
				} else {
					currentURL = Web.webdriver.getCurrentUrl();
				}

				// Verification against redirected URLs				
				if((Web.VerifyPartialText(testData, currentURL, false))){
					Reporter.logEvent(Status.PASS,"Check if the respective page is loaded",
							 "Verified that the respective page :" + testData + " is loaded successfully", false);
				}else{
					Reporter.logEvent(Status.FAIL,"Check if the respective page is loaded",
							"Respective page :" + testData + " is not loaded", true);
				}
								
				if (!(testDataColNm + (iLoopCnt + 1)).equals("link_Footer7")
						&& !(testDataColNm + (iLoopCnt + 1)).equals("link_Header2")) {
					
					//Handle sponsor fund prospectus footer link	
					if(testData.equalsIgnoreCase("sponsor-fund-prospectus")){
					   Web.isWebElementDisplayed(fundIFrame,true);
				       Web.webdriver.switchTo().frame(fundIFrame);
					   if(Web.isWebElementDisplayed(tabAlphabet,true)){ 
						   if(Stock.GetParameterValue("fundProspectus_Characters").contains(",")){
							   charList = Stock.GetParameterValue("fundProspectus_Characters").split(",");
						   }else{
							   charList = new String[1];
							   charList[0] =  Stock.GetParameterValue("fundProspectus_Characters");
						   }
						   
						   for(int iCharLoop=0;iCharLoop<=(charList.length)-1;iCharLoop++){
							   charItem = charList[iCharLoop];							   
							   String charLink = ".//th/a[@class='alphabetLetter ng-binding' and text() ='"+charItem+"']";
							   
							   Web.clickOnElement(Web.webdriver.findElement(By.xpath(charLink)));Thread.sleep(1000);
							   Reporter.logEvent(Status.INFO,"Selecting characters to list Sponsor Funds",
									   "Character '"+charItem+"' selected to list relevant funds",false);
							   			
							   fundsList = fundTable.findElements(By.xpath("thead//th[text()='"+charItem+"']/../../following-sibling::tbody/tr")); 							  
							   fundLinkSize = fundsList.size();							   

							   if(fundLinkSize == 0){
								  Reporter.logEvent(Status.INFO,"Funds Propectus list unavailable",
							              "Funds list not available for Character '"+charList[iCharLoop]+"'",true);
								  break;
							   }							   
							   for(int iFundLoop=0;iFundLoop<=fundLinkSize-1;iFundLoop++){
								   fundLink = fundsList.get(iFundLoop).findElement(By.xpath("td/div/a"));								   								   
								   String linkText = fundLink.getText();								   								  
								   fundLink.click();Thread.sleep(2000);
								   
								   String oldTab = Web.webdriver.getWindowHandle();
								   ArrayList<String> newTab = new ArrayList<String>(Web.webdriver.getWindowHandles());
								   Web.webdriver.switchTo().window(newTab.get(1));
								   if(Web.isWebElementDisplayed(invalidFundErr,false) 	
										   
								   ||fundProspectusText.getText().length()<100){ //ignoring fund prospectus details less than 100 chars
									  Web.webdriver.close();Web.webdriver.switchTo().window(oldTab);
									  Web.webdriver.switchTo().frame(fundIFrame);
									  continue;									   
								   }else{													 										   
									 if(Web.isWebElementDisplayed(fundNav_Prospectus,false)
									    && Web.isWebElementDisplayed(fundNav_AnnualReport,false)
									    && Web.isWebElementDisplayed(fundNav_SemiAnnualReport,false)
									    && Web.isWebElementDisplayed(fundNav_SOAI,false)){
										Web.webdriver.switchTo().frame(docContentIFrame);
										if(fundProspectusText.getText().length()>200){//validating if the prospectus text contains more that 200 chars
											Reporter.logEvent(Status.PASS,"Link "+linkText+" validate fund prospectus details",
								    		          "Fund prospectus details validated successfully",false);
										}else{
											Reporter.logEvent(Status.FAIL,"Link "+linkText+" validate fund prospectus details",
								    		          "Fund prospectus details validation failed",true);
										}	
										Web.webdriver.switchTo().defaultContent();
									 }else{
										 Reporter.logEvent(Status.FAIL,"Link "+linkText+" validate fund prospectus details",
							    		          "Fund prospectus details validation failed",true);
									 }
									 Web.webdriver.close();Web.webdriver.switchTo().window(oldTab);
									 Web.webdriver.switchTo().frame(fundIFrame);
									 break;
								  }
							   }							   
						   }												 
					   }else{
						   Reporter.logEvent(Status.FAIL,"Fund Prospectuses alphabet table not displayed",
								             "alphabet table not loaded",true);
					   }
					   Web.webdriver.switchTo().defaultContent();
					}					
					Web.waitForElement(linkPSCBreadCrumHome);
					linkPSCBreadCrumHome.click();
				}
			}
		}catch(Exception e){
			 throw new Error("Error occurred while checking " +
			 testDataColNm.split("link_")[1] +
			 " links , Exception:"+e.getMessage());
		}
	}
}
