/**
 * 
 */
package gwgwebdriver;

import gwgwebdriver.scripts.FindAllRepeaterColumns;
import gwgwebdriver.scripts.FindAllRepeaterRows;
import gwgwebdriver.scripts.FindAngularAttribute;
import gwgwebdriver.scripts.FindBindings;
import gwgwebdriver.scripts.FindButtonText;
import gwgwebdriver.scripts.FindCssSelectorContainingText;
import gwgwebdriver.scripts.FindModel;
import gwgwebdriver.scripts.FindPartialButtonText;
import gwgwebdriver.scripts.FindRepeaterElements;
import gwgwebdriver.scripts.FindRepeaterRows;
import gwgwebdriver.scripts.FindSelectedOption;
import gwgwebdriver.scripts.FindSelectedRepeaterOption;
import gwgwebdriver.scripts.FindOptions;

import org.openqa.selenium.By;


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

