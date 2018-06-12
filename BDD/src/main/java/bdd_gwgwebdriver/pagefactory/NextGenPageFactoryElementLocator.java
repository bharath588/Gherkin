/**
 * 
 */
package bdd_gwgwebdriver.pagefactory;

import java.lang.reflect.Field;
import java.util.List;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;

import bdd_gwgwebdriver.NextGenWebElement;

/**
 * @author rvpndy
 *
 */
public class NextGenPageFactoryElementLocator extends DefaultElementLocator{

	public NextGenPageFactoryElementLocator(SearchContext searchContext,
			AbstractAnnotations annotations) {
		super(searchContext, annotations);
		// TODO Auto-generated constructor stub
	}
	
	public NextGenPageFactoryElementLocator(SearchContext searchContext,
			Field field) {
		super(searchContext, field);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public WebElement findElement() {

        WebElement element = super.findElement();
        if (element instanceof NextGenWebElement) {
            return ((NextGenWebElement) element).getWrappedElement();
        }
        return element;
    }

    @Override
    public List<WebElement> findElements() {
        List<WebElement> elements = super.findElements();
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            if (element instanceof NextGenWebElement) {
                elements.add(i, ((NextGenWebElement) element).getWrappedElement());
            }
        }
        return elements;
    }

}
