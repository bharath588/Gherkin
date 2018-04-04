/**
 * 
 */
package gwgwebdriver;

import gwgwebdriver.scripts.Evaluate;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

/**
 * @author rvpndy
 *
 */
public class NextGenWebElement implements WebElement, WrapsElement {
	
	private final transient NextGenWebDriver driver;
	private final transient WebElement element;
	
	public NextGenWebElement(final NextGenWebDriver drv, final WebElement elm){
		this.driver = drv;
		this.element = elm;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)
	 */
	@Override
	public <X> X getScreenshotAs(OutputType<X> paramOutputType)
			throws WebDriverException {
		// TODO Auto-generated method stub
		return this.element.getScreenshotAs(paramOutputType);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.internal.WrapsElement#getWrappedElement()
	 */
	@Override
	public WebElement getWrappedElement() {
		// TODO Auto-generated method stub
		return this.element;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#click()
	 */
	@Override
	public void click() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		this.element.click();

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	@Override
	public void submit() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		this.element.submit();

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#sendKeys(java.lang.CharSequence[])
	 */
	@Override
	public void sendKeys(CharSequence... paramVarArgs) {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		this.element.sendKeys(paramVarArgs);

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		this.element.clear();

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.getTagName();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String paramString) {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.getAttribute(paramString);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.isSelected();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.isEnabled();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.getText();
	}
	
	public Object evaluate(final String expression) {
		this.driver.waitForAngular();
		final JavascriptExecutor executor = (JavascriptExecutor) this.driver
				.getWrappedDriver();
		return executor.executeScript(new Evaluate().content(), this.element,
				expression);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(By paramBy) {
		// TODO Auto-generated method stub
		if (paramBy instanceof JavaScriptBy) {
			((JavaScriptBy) paramBy).root = this.element;
		}
		final List<WebElement> returnElements = this.element.findElements(paramBy);
		this.driver.waitForAngular();
		return returnElements;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
	 */
	@Override
	public NextGenWebElement findElement(By by) {
		// TODO Auto-generated method stub
		if (by instanceof JavaScriptBy) {
			((JavaScriptBy) by).root = this.element;
		}
		this.driver.waitForAngular();
		return new NextGenWebElement(this.driver, this.element.findElement(by));
	}
	
	public List<NextGenWebElement> findNgElements(By by) {
		final List<WebElement> temp = findElements(by);
		final List<NextGenWebElement> elements = new ArrayList<NextGenWebElement>();
		for (final WebElement element : temp) {
			elements.add(new NextGenWebElement(this.driver, element));
		}
		return elements;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isDisplayed()
	 */
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.isDisplayed();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getLocation()
	 */
	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.getLocation();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getSize()
	 */
	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.getSize();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getRect()
	 */
	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return this.element.getRect();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue(String paramString) {
		// TODO Auto-generated method stub
		this.driver.waitForAngular();
		return this.element.getCssValue(paramString);
	}

}
