/**
 * 
 */
package org.bdd.psc.pageobjects;

import gwgwebdriver.How;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.NextGenPageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import reporter.Reporter;
import annotations.FindBy;

/**
 * @author rvpndy
 *
 */
public class FundProspectus extends LoadableComponent<FundProspectus> {
	
	LoadableComponent<?> parent;
	@FindBy(how=How.XPATH,using=".//*[@id='main']/div[1]/div/ol/li[2]")
	private WebElement fundProspectusesBreadcrumb;
	@FindBy(how=How.REPEATER,using="fund in alphabetFunds.fundList",exact=true)
	private List<WebElement> fundInFunds;
	@FindBy(how=How.ID,using="fund_iframe")
	private WebElement fundsIframe;
	@FindBy(how=How.XPATH,using=".//*[@id='page-container']/div/div[3]/div")
	private WebElement loadingFundsSpinner;
	
	
	public FundProspectus(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(fundProspectusesBreadcrumb));
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		LoginPage login = (LoginPage)this.parent;
		login.get();
		login.clickOnFooterLink("Fund Prospectuses");
		Web.nextGenDriver.waitForAngular();
		
	}
	
	public boolean compareHrefValues(String expectedHref){
		try{
			//waitForFundsToLoad();
			Web.FrameSwitchONandOFF(true, fundsIframe);
			Web.nextGenDriver.waitForAngular();
			Reporter.logEvent(Status.INFO, "Total number of funds is:", 
					String.valueOf(fundInFunds.size()), false);
			if(fundInFunds.size()>0){
				for(WebElement ele : fundInFunds){
					if(ele.findElement(By.tagName("a")).getAttribute("href").contains(expectedHref))
						return true;
				}
				return false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	private void waitForFundsToLoad(){
		Web.FrameSwitchONandOFF(true, fundsIframe);
		do{
			System.out.println("Funds are loading. Please wait...");
		}
		while(Web.isWebElementDisplayed(loadingFundsSpinner, false));
		Web.FrameSwitchONandOFF(false, fundsIframe);
	}

	

}
