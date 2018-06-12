/**
 * 
 */
package bdd_gwgwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

/**
 * @author rvpndy
 *
 */
public class ByAngularModel extends ByAngular.BaseBy{
	public ByAngularModel(String rootSelector, String model) {
        super(rootSelector);
        this.model = model;
    }

    private String model;

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = '" + rootSelector + "';\n" +
                        "var model = '" + model + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findByModel")
                , context);
    }

    @Override
    public String toString() {
        return "model(" + model + ')';
    }
	

}
