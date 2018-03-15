package gwgwebdriver.pagefactory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;

public class NextGenPageFactory {
	
	public static void initElements(SearchContext searchContext, Channel channel, Object page) {
        PageFactory.initElements(new NextGenPageFactoryFieldDecorator(new NextGenPageFactoryElementLocatorFactory(searchContext, channel)), page);
    }

    public static void initWebElements(SearchContext searchContext, Object page) {
        initElements(searchContext, Channel.WEB, page);
    }

    public static void initMobileElements(SearchContext searchContext, Object page) {
        initElements(searchContext, Channel.MOBILE, page);
    }

}
