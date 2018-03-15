package pageobjects.login;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
	
	@FindBy(xpath = ".//button[text()[normalize-space()='Dismiss']]")
	private WebElement btnDismiss;
	@FindBy(id = "usernameInput")
	private WebElement txtUserName;
	@FindBy(id = "passwordInput")
	private WebElement txtPassword;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]")
	private WebElement btnLogin;
	private String username;
	private String password;
	private String url = "https://proj2.retirementpartner.com/participant/#/login?accu=Empower";
	public static WebDriver webDriver;
	
	public Login() {
		// this.parent = parent;
		webDriver = new FirefoxDriver();
		PageFactory.initElements(webDriver, this);
	}
	public void openBrowserAndNavigateToApplication() throws InterruptedException
	{
	
		//PageFactory.initElements(webDriver, this);
		webDriver.get(url);
		webDriver.manage().window().maximize();
		submitLoginCredentials("452277889ABC", "Test@1234");
		
	}
	
	public void submitLoginCredentials(String userName, String password) throws InterruptedException 
	{
	
		if(isWebElementDisplayed(btnDismiss))
			btnDismiss.click();
		
		if (isWebElementDisplayed(txtUserName)) {
			txtUserName.clear();			
			txtUserName.sendKeys(userName);
			txtPassword.clear();			
			txtPassword.sendKeys(password);
			btnLogin.click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
 }
	public static boolean isWebElementDisplayed(WebElement element) throws InterruptedException {
		boolean blnElementDisplayed = false;
		try {		
			Thread.sleep(4000);
			blnElementDisplayed = element.isDisplayed();
		} catch (NoSuchElementException e) {
			blnElementDisplayed = false;
		}
		return blnElementDisplayed;
	}

}
