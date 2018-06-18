package bdd_gwgwebdriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

import bdd_gwgwebdriver.scripts.Script;

public class JavaScriptBy extends By {
	
	/**
	 * Script to use.
	 */
	private final transient Script script;
	/**
	 * Root element.
	 *
	 * FIXME this should not be like that.
	 */
	public WebElement root;
	/**
	 * Script arguments.
	 */
	private final transient Object[] args;
	
	/**
	 * 
	 * @param scrpt Script to use.
	//     * @param elem Root WebElement.
	 * @param jsargs Script args.
	 */
	public JavaScriptBy(
			// final WebElement elem,
			final Script scrpt, final Object... jsargs) {
		super();
		this.script = scrpt;
		// this.root = elem;
		this.args = Arrays.copyOf(jsargs, jsargs.length);
	}
	
	

	@Override
	public List<WebElement> findElements(final SearchContext paramSearchContext) {
		// TODO Auto-generated method stub
		final Object[] scriptargs = new Object[this.args.length + 1];
		scriptargs[0] = this.root;
		System.arraycopy(this.args, 0, scriptargs, 1, this.args.length);
		return this.elements(paramSearchContext, scriptargs);
	}
	
	private List<WebElement> elements(final SearchContext context,
			final Object[] jsargs) {
		@SuppressWarnings("unchecked")
		List<WebElement> elements = (List<WebElement>) this.executor(context)
				.executeScript(this.script.content(), jsargs);
		if (elements == null) {
			elements = new ArrayList<WebElement>();
		}
		return elements;
	}
	
	private JavascriptExecutor executor(final SearchContext context) {
		JavascriptExecutor jsexecutor = null;
		if (!(context instanceof WebElement)) {
			jsexecutor = (JavascriptExecutor) context;
		}
		if (jsexecutor == null) {
			final WrapsDriver driver = (WrapsDriver) context;
			if (driver != null) {
				jsexecutor = (JavascriptExecutor) driver.getWrappedDriver();
			}
		}
		if (jsexecutor == null) {
			throw new WebDriverException(
					"Could not get an JavaScriptExecutor instance from the context.");
		}
		return jsexecutor;
	}

}
