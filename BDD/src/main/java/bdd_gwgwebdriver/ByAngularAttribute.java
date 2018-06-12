package bdd_gwgwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

/**
 * @author rvpndy
 *
 */
public class ByAngularAttribute extends ByAngular.BaseBy{

	public ByAngularAttribute(String rootSelector, String angularAttribute, 
			String attributeValue){
		super(rootSelector);
		this.angularAttribute = angularAttribute;
		this.attributeValue = attributeValue;

	}
	private String angularAttribute;
	private String attributeValue;
	@Override
	protected Object getObject(SearchContext context,
			JavascriptExecutor javascriptExecutor) {
		// TODO Auto-generated method stub
		String script = "var angularAttribute = '" + angularAttribute + "';\n" +
				"var attributeValue = '" + attributeValue + "';\n" +
				"var using = arguments[0] || document;\n" +
				"var rows = [];\n" +
				"var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-'];\n" +
				"for (var p = 0; p < prefixes.length; ++p) {\n" +
				"  var attr = prefixes[p] + angularAttribute;\n" +
				"  var clickElems = using.querySelectorAll('[' + attr + ']');\n" +
				"  attr = attr.replace(/\\\\/g, '');\n" +
				"  for (var i = 0; i < clickElems.length; ++i) {\n" +
				"    if (clickElems[i].getAttribute(attr).indexOf(attributeValue) != -1) {\n" +
				"      rows.push(clickElems[i]);\n" +
				"    }\n" +
				"  }\n" +
				"}\n" +
				"return rows;";
		//System.out.println(script);
		return javascriptExecutor.executeScript(script, context);


	}
	
	 @Override
	    public String toString() {
	        return "angularAttribute(" +angularAttribute + attributeValue + ')';
	    }
}
