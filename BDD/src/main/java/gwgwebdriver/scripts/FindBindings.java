/**
 * 
 */
package gwgwebdriver.scripts;

/**
 * @author rvpndy
 *
 */
public final class FindBindings implements Script {

	/* (non-Javadoc)
	 * @see gwgwebdriver.scripts.Script#content()
	 */
	@Override
	public String content() {
		// TODO Auto-generated method stub
		return new Loader("binding").content();
	}

}
