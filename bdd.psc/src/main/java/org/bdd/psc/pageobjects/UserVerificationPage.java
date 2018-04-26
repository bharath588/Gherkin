/**
 * 
 */
package org.bdd.psc.pageobjects;

import java.util.LinkedHashMap;
import java.util.Map;

import lib.CommonLib;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import reporter.Reporter;

/**
 * @author rvpndy
 *
 */
public class UserVerificationPage extends LoadableComponent<UserVerificationPage> {
	
	@FindBy(css = "input[class *= 'emailBox']")
	private WebElement txtUserVerificationEmail;
	@FindBy(css = "input[class *='answerBox']")
	private WebElement txtUserVerificationSecAns;
	@FindBy(css = "button[class *= 'swap-prelogin-cancel']")
	private WebElement btnUserVerfificationCancel;
	@FindBy(css = "button[class *= 'btn-u next']")
	private WebElement btnUserVerificationNext;
	@FindBy(xpath = ".//*[@id='logo']/img")
	private WebElement imgEmpowerPsc;
	@FindBy(xpath = ".//label[contains(text(),'What')]")
	private WebElement txtSecurityQuestion;
	@FindBy(xpath = ".//*[contains(@id,'securityEmail')]")
	private WebElement heritageSecEmail;
	@FindBy(xpath = ".//*[contains(@id,'securityAnswer')]")
	private WebElement heritageSecAnswer;
	@FindBy(xpath = ".//*[contains(@id,'securityQuestion')]")
	private WebElement heritageSecQuestion;
	@FindBy(xpath=".//button/span[text()='DONE']")
	private WebElement heritageUserVerDoneButton;
	LoadableComponent<?> parent;
	public static WebDriver webDriver;
	CommonLib commonLib;
	Map<String,String> securityAnsMap=null;
	public UserVerificationPage() {
		this.parent = new LoginPage();
		PageFactory.initElements(Web.getDriver(), this);
	}
	public UserVerificationPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(Web.getDriver(), this);
	}
	public UserVerificationPage(WebDriver webDriver){
		UserVerificationPage.webDriver = webDriver;
		this.parent = new LoginPage(webDriver);
		commonLib = new CommonLib(webDriver);
		PageFactory.initElements(webDriver, this);
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		
		try {
			if(Web.isWebElementDisplayed(txtUserVerificationEmail,true)){
				Reporter.logEvent(Status.INFO,"Loading User Verification Page"
						                     ,"User verfication page loaded",false);
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		webDriver.switchTo().defaultContent();
		try {
			Assert.assertTrue(txtUserVerificationEmail.isDisplayed());
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("EMAIL ADDRESS")) {
			return this.txtUserVerificationEmail;
		}

		if (fieldName.trim().equalsIgnoreCase("SECURITY ANSWER")) {
			return this.txtUserVerificationSecAns;
		}
		if(fieldName.trim().equalsIgnoreCase("SECURITYQUESTION"))
		{
			return this.txtSecurityQuestion;
		}
		if(fieldName.trim().equalsIgnoreCase("HERITAGE SEC ANSWER")){
			return this.heritageSecAnswer;
		}
		if(fieldName.trim().equalsIgnoreCase("HERITAGE EMAIL")){
			return this.heritageSecEmail;
		}
		if(fieldName.trim().equalsIgnoreCase("HERITAGE SEC QUESTN")){
			return this.heritageSecQuestion;
		}
		return null;
	}
	
	public void performVerification(String email){
		try{
			Thread.sleep(10000);
			WebDriverWait wait = new WebDriverWait(Web.getDriver(),30);
			txtUserVerificationEmail.sendKeys(email);
			txtUserVerificationSecAns.sendKeys("test");
			btnUserVerificationNext.click();
			Thread.sleep(10000);
			wait.until(ExpectedConditions.visibilityOf(imgEmpowerPsc));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** This method Performs user verification based on the user input */
	public void performVerification(String[] userVerfiData) {
		//new UserVerificationPage();	
		commonLib = new CommonLib(Web.getDriver());
		if (Web.isWebElementDisplayed(txtUserVerificationEmail)) {			
			Web.setTextToTextBox(txtUserVerificationEmail, userVerfiData[0]);
			Web.setTextToTextBox(txtUserVerificationSecAns, userVerfiData[1]);
			btnUserVerificationNext.click();
			Web.waitForElement(imgEmpowerPsc);
		}		
		if (!Web.isWebElementDisplayed(imgEmpowerPsc)) {
				Reporter.logEvent(Status.INFO, "Verify if the user verification screen is loaded",
						"The user verification screen is not loaded", false);
			}else{
				Reporter.logEvent(Status.PASS, "Verify if the user verification screen is loaded",
						"The user verification screen is loaded", false);
			}
		}
	
	/** This method Performs user verification based on the user input for heritage page*/
	public void performVerificationHeritage(String[] userVerfiData) {
		//new UserVerificationPage();	
		if (Web.isWebElementDisplayed(heritageSecEmail)) {			
			Web.setTextToTextBox(heritageSecEmail, userVerfiData[0]);
			Web.setTextToTextBox(heritageSecAnswer, userVerfiData[1]);
			Web.clickOnElement(heritageUserVerDoneButton);
			Web.waitForElement(imgEmpowerPsc);
		}		
		if (!Web.isWebElementDisplayed(imgEmpowerPsc)) {
				Reporter.logEvent(Status.INFO, "Verify if the user verification screen is loaded",
						"The user verification screen is not loaded", false);
			}else{
				Reporter.logEvent(Status.PASS, "Verify if the user verification screen is loaded",
						"The user verification screen is loaded", false);
			}
		}
	
	public String getSecurityAnswer(String securityQuestion) {
		String securityAnswer = null;
		initSecurityAnsMap();
		for (Map.Entry<String, String> entry : securityAnsMap.entrySet()) {
			if (StringUtils.contains(securityQuestion, entry.getKey())) {
				securityAnswer = entry.getValue();
				break;
			}
		}
		return securityAnswer;
	}
	private void initSecurityAnsMap() {
		String secAns = Stock.getConfigParam("SecurityAns");
		if (securityAnsMap == null & !secAns.isEmpty()) {
			securityAnsMap = new LinkedHashMap<String, String>();
			for (String QA : secAns.split("\\|")) {
				securityAnsMap.put(QA.split(",")[0].trim(),
						QA.split(",")[1].trim());
			}
		}
	}

}
