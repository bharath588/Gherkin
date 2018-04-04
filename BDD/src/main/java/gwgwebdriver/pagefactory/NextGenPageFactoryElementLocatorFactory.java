/**
 * 
 */
package gwgwebdriver.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import gwgwebdriver.pagefactory.Channel;

/**
 * @author rvpndy
 *
 */
public class NextGenPageFactoryElementLocatorFactory implements
		ElementLocatorFactory {
	
	private final SearchContext searchContext;
    private Channel channel;

	/* (non-Javadoc)
	 * @see org.openqa.selenium.support.pagefactory.ElementLocatorFactory#createLocator(java.lang.reflect.Field)
	 */
	@Override
	public ElementLocator createLocator(Field field) {
		// TODO Auto-generated method stub
		return new NextGenPageFactoryElementLocator(searchContext, new NextGenPageFactoryAnnotations(field,channel));
	}
	
	public NextGenPageFactoryElementLocatorFactory(SearchContext searchContext){
		this.searchContext = searchContext;
		this.channel = Channel.WEB;
	}
	
	public NextGenPageFactoryElementLocatorFactory(SearchContext searchContext, Channel channel){
		this.searchContext = searchContext;
		this.channel = channel;
	}

}
