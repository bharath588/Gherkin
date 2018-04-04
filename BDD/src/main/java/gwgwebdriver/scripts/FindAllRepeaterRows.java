/**
 * 
 */
package gwgwebdriver.scripts;

/**
 * @author rvpndy
 *
 */
public final class FindAllRepeaterRows implements Script {

	/* (non-Javadoc)
	 * @see gwgwebdriver.scripts.Script#content()
	 */
	@Override
	public String content() {
		// TODO Auto-generated method stub
		return new Loader("repeater").content();
	}

}
