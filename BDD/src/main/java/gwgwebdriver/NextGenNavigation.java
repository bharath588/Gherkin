/**
 * 
 */
package gwgwebdriver;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriverException;

/**
 * @author rvpndy
 *
 */
public final class NextGenNavigation implements Navigation {
	
	private final WebDriver.Navigation nav;
	
	public NextGenNavigation(final WebDriver.Navigation navigation) {
		// TODO Auto-generated constructor stub
		this.nav = navigation;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver.Navigation#back()
	 */
	@Override
	public void back() {
		// TODO Auto-generated method stub
		this.nav.back();

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver.Navigation#forward()
	 */
	@Override
	public void forward() {
		// TODO Auto-generated method stub
		this.nav.forward();

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.lang.String)
	 */
	@Override
	public void to(final String url) {
		// TODO Auto-generated method stub
		if(url==null){
			throw new WebDriverException ("URL cannot be null");
		}
		this.nav.to(url);

	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.net.URL)
	 */
	@Override
	public void to(URL paramURL) {
		// TODO Auto-generated method stub
		if (paramURL == null) {
			throw new WebDriverException("URL cannot be null.");
		}
		this.to(paramURL.toString());
	}


	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver.Navigation#refresh()
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		this.nav.refresh();

	}

}
