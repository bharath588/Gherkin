/**
 * 
 */
package gwgwebdriver;

import gwgwebdriver.scripts.WaitForAngular;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * @author rvpndy
 *
 */
public class NextGenWebDriver implements WebDriver, WrapsDriver {
	
	private final WebDriver webDriver;
	private final JavascriptExecutor jsExecutor;
	private final String rootElement;
	public boolean ignoreSynchronization;
	
	public NextGenWebDriver(final WebDriver webDriver){
		this(webDriver,"body");
	}
	
	public NextGenWebDriver(final WebDriver webDriver, final String rootElement){
		if(!(webDriver instanceof JavascriptExecutor)){
			throw new WebDriverException ("The WebDriver instance must implement JavaScriptExecutor interface.");
		}
		this.webDriver = webDriver;
		this.jsExecutor = (JavascriptExecutor) webDriver;
		this.rootElement = rootElement;
	}
	
	/**
	 * 
	 * @param webDriver Parent Web Driver.
	 * @param ignoreSynchronization flag indicating non-Angular sites
	 */
	public NextGenWebDriver(final WebDriver webDriver,
			final boolean ignoreSynchronization) {
		this(webDriver);
		this.ignoreSynchronization = ignoreSynchronization;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.internal.WrapsDriver#getWrappedDriver()
	 */
	@Override
	public WebDriver getWrappedDriver() {
		// TODO Auto-generated method stub
		return this.webDriver;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	@Override
	public void get(String paramString) {
		// TODO Auto-generated method stub
		this.waitForAngular();
		this.webDriver.get(paramString);

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl() {
		// TODO Auto-generated method stub
		return this.webDriver.getCurrentUrl();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		this.waitForAngular();
		return this.webDriver.getTitle();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(By paramBy) {
		// TODO Auto-generated method stub
		return this.webDriver.findElements(paramBy);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
	 */
	@Override
	public NextGenWebElement findElement(By paramBy) {
		// TODO Auto-generated method stub
		this.waitForAngular();
		return new NextGenWebElement(this, this.webDriver.findElement(paramBy));
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	@Override
	public String getPageSource() {
		// TODO Auto-generated method stub
		this.waitForAngular();
		return this.webDriver.getPageSource();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.webDriver.close();

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	@Override
	public void quit() {
		// TODO Auto-generated method stub
		this.webDriver.quit();

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	@Override
	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return this.webDriver.getWindowHandles();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	@Override
	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return this.webDriver.getWindowHandle();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return this.webDriver.switchTo();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	@Override
	public WebDriver.Navigation navigate() {
		// TODO Auto-generated method stub
		return new NextGenNavigation(this.webDriver.navigate());
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	@Override
	public Options manage() {
		// TODO Auto-generated method stub
		return this.webDriver.manage();
	}
	
	public void waitForAngular() {
		if (!this.ignoreSynchronization) {
			this.jsExecutor.executeAsyncScript(new WaitForAngular().content(),
					this.rootElement);
		}
	}

}
