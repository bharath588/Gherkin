/**
 * 
 */
package pageobjects.psc;

import java.util.List;

import lib.Web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * @author rvpndy
 *
 */
public class JumpPage extends LoadableComponent<JumpPage> {
	
	@FindBy(id="jumpPage")
	private WebElement jumpPage;
	@FindBy(xpath=".//*[@class='headerBlock']/h1")
	private WebElement jumpPageHeader;
	@FindBy(xpath="//*[@id='jumpPageInput']")
	private WebElement jumpPageSearchBox;
	@FindBy(xpath=".//*[@id='jumpPageTable_data']//a")
	private List<WebElement> jumpPageSiteLinks;
	@FindBy(xpath="//span[@class='jumpPageText']")
	private List<WebElement> jumpPageText;
	@FindBy(xpath="//*[@id='jumpPageList']/option")
	private WebElement jumpPageList;
	@FindBy(css = "a[id = 'jumpPageTable:0:j_idt48']")
	private WebElement urlJumpPage;
	@FindBy(css="div[id='greeting'] span[class='label']") private WebElement weGreeting;
	private LoadableComponent<?> parent;
	public static WebDriver webDriver;
	
	public JumpPage(){
		this.parent = new UserVerificationPage();
		PageFactory.initElements(Web.getDriver(), this);
	}
	
	public JumpPage(WebDriver webDriver){
		JumpPage.webDriver = webDriver;
		this.parent = new UserVerificationPage(webDriver);
		PageFactory.initElements(webDriver, this);
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		this.parent.get();
		UserVerificationPage userVeriPg = new UserVerificationPage();
		LoginPage loginObj =  new LoginPage();
		loginObj.get();
		userVeriPg.get();
		
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(jumpPageHeader.isDisplayed());
		
	}
	
	public void clickOnJumpPageLink()
	{
		WebDriverWait wait = new WebDriverWait(Web.getDriver(),30);
		urlJumpPage.click();
		wait.until(ExpectedConditions.visibilityOf(weGreeting));
	}
	
	private WebElement getWebElement(String fieldName){
		if(fieldName.equalsIgnoreCase("JUMP PAGE URL"))
			return urlJumpPage;
		return null;
	}

}
