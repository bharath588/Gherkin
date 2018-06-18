/**
 * 
 */
package bdd_gwgwebdriver;

import org.openqa.selenium.WebDriverException;

/**
 * @author rvpndy
 *
 */
public class VariableNotInScopeException extends WebDriverException{
	public VariableNotInScopeException(String msg) {
        super(msg);
    }

}
