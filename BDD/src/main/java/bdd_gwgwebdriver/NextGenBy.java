/**
 * 
 */
package bdd_gwgwebdriver;

import org.openqa.selenium.By;

import bdd_gwgwebdriver.scripts.FindAllRepeaterColumns;
import bdd_gwgwebdriver.scripts.FindAllRepeaterRows;
import bdd_gwgwebdriver.scripts.FindAngularAttribute;
import bdd_gwgwebdriver.scripts.FindBindings;
import bdd_gwgwebdriver.scripts.FindButtonText;
import bdd_gwgwebdriver.scripts.FindCssSelectorContainingText;
import bdd_gwgwebdriver.scripts.FindModel;
import bdd_gwgwebdriver.scripts.FindOptions;
import bdd_gwgwebdriver.scripts.FindPartialButtonText;
import bdd_gwgwebdriver.scripts.FindRepeaterElements;
import bdd_gwgwebdriver.scripts.FindRepeaterRows;
import bdd_gwgwebdriver.scripts.FindSelectedOption;
import bdd_gwgwebdriver.scripts.FindSelectedRepeaterOption;


/**
 * @author rvpndy
 *
 */
public final class NextGenBy {

	private NextGenBy(){

	}

	public static By binding(final String binding) {
		return new JavaScriptBy(new FindBindings(), binding);
	}

	public static By binding(final String binding, String rootSelector) {
		return new JavaScriptBy(new FindBindings(), binding, rootSelector);
	}

	public static By buttonText(final String text) {
		return new JavaScriptBy(new FindButtonText(), text);
	}

	public static By cssContainingText(final String cssSelector, String text) {
		return new JavaScriptBy(new FindCssSelectorContainingText(), cssSelector,
				text);
	}

	public static By input(final String input) {
		return new JavaScriptBy(new FindModel(), input);
	}

	public static By model(final String model) {
		return new JavaScriptBy(new FindModel(), model);
	}

	public static By model(final String model, String rootSelector) {
		return new JavaScriptBy(new FindModel(), model, rootSelector);
	}

	public static By partialButtonText(final String text) {
		return new JavaScriptBy(new FindPartialButtonText(), text);
	}

	public static By repeater(final String repeat,boolean exact) {
		return new JavaScriptBy(new FindAllRepeaterRows(), repeat,exact);
	}

	public static By repeaterColumn(final String repeat, String binding) {
		return new JavaScriptBy(new FindAllRepeaterColumns(), repeat, binding);
	}

	public static By repeaterElement(final String repeat, Integer index,
			String binding) {
		return new JavaScriptBy(new FindRepeaterElements(), repeat, index, binding);
	}

	public static By repeaterRows(final String repeat, Integer index) {
		return new JavaScriptBy(new FindRepeaterRows(), repeat, index);
	}

	public static By options(final String options) {
		return new JavaScriptBy(new FindOptions(), options);
	}

	public static By selectedOption(final String model) {
		return new JavaScriptBy(new FindSelectedOption(), model);
	}

	public static By selectedRepeaterOption(final String repeat) {
		return new JavaScriptBy(new FindSelectedRepeaterOption(), repeat);
	}
	
	public static By angularAttribute(final String angularAttribute, final String attributeValue){
		return new JavaScriptBy(new FindAngularAttribute(),angularAttribute,attributeValue);
	}
}

