/**
 * 
 */
package bdd_gwgwebdriver.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import bdd_annotations.FindAll;
import bdd_annotations.FindBy;
import bdd_annotations.FindBys;
import bdd_gwgwebdriver.How;
import bdd_gwgwebdriver.NextGenBy;

/**
 * @author rvpndy
 *
 */
public class NextGenPageFactoryAnnotations extends Annotations{
	private boolean isWebChannel;

	public NextGenPageFactoryAnnotations(Field field, Channel channel) {
		super(field);
		this.isWebChannel = Channel.WEB.equals(channel);
		// TODO Auto-generated constructor stub
	}
	
	public By buildBy(){
		this.assertValidAnnotations();
		By ans = null;
		FindBys findBys = (FindBys) this.getField().getAnnotation(FindBys.class);
		if (findBys != null) {
			ans = this.buildByFromFindBys(findBys);
		}

		FindAll findAll = (FindAll) this.getField().getAnnotation(FindAll.class);
		if (ans == null && findAll != null) {
			ans = this.buildBysFromFindByOneOf(findAll);
		}

		FindBy findBy = (FindBy) this.getField().getAnnotation(FindBy.class);
		if (ans == null && findBy != null) {
			ans = this.buildByFromFindBy(findBy);
		}
		return ans;
	}
	
	protected By buildByFromFindBys(FindBys findBys) {
		FindBy[] findByArray = findBys.value();
		By[] byArray = new By[findByArray.length];

		for (int i = 0; i < findByArray.length; ++i) {
			byArray[i] = this.buildByFromFindBy(findByArray[i]);
		}
		return new ByChained(byArray);
	}

	protected By buildBysFromFindByOneOf(FindAll findBys) {
		FindBy[] findByArray = findBys.value();
		By[] byArray = new By[findByArray.length];

		for (int i = 0; i < findByArray.length; ++i) {
			byArray[i] = this.buildByFromFindBy(findByArray[i]);
		}
		return new ByAll(byArray);
	}
	
	protected By buildByFromFindBy(FindBy findBy) {
		How how = getHowDefinition(findBy);
		String using = getUsingDefinition(findBy);
		String text = getTextDefinition(findBy);
		String column = getColumnDefinition(findBy);
		Integer row = getRowDefinition(findBy);
		String angularAttribute = getAngularAttributeDefinition(findBy);
		String attributeValue = getAttributeValueDefinition(findBy);
		boolean exact = getExactDefinition(findBy);

		if (using.isEmpty()) {
			return null;
		}

		switch (how) {
		case CLASS_NAME:
			return By.className(using);

		case CSS:
			return By.cssSelector(using);

		case ID:
			return By.id(using);
		case UNSET:
			return By.id(using);

		case ID_OR_NAME:
			return new ByIdOrName(using);

		case LINK_TEXT:
			return By.linkText(using);

		case NAME:
			return By.name(using);

		case PARTIAL_LINK_TEXT:
			return By.partialLinkText(using);

		case TAG_NAME:
			return By.tagName(using);

		case XPATH:
			return By.xpath(using);

		case BINDING:
			return NextGenBy.binding(using);

		case BUTTON_TEXT:
			return NextGenBy.buttonText(using);

		case PARTIAL_BUTTON_TEXT:
			return NextGenBy.partialButtonText(using);

		case MODEL:
			return NextGenBy.model(using);

		case OPTIONS:
			return NextGenBy.options(using);

		case REPEATER:
			return NextGenBy.repeater(using,exact);
			
		case REPEATER_SELECTED_OPTION:
			return NextGenBy.selectedRepeaterOption(using);

		case REPEATER_ELEMENT:
			return NextGenBy.repeaterElement(using, row, column);

		case REPEATER_COLUMN:
			return NextGenBy.repeaterColumn(using, column);

		case REPEATER_ROW:
			return NextGenBy.repeaterRows(using, row);

		case SELECTED_OPTION:
			return NextGenBy.selectedOption(using);

		case ANGULAR_ATTRIBUTE:
			return NextGenBy.angularAttribute(using,text);

		case CSS_CONTAINING_TEXT:
			return NextGenBy.cssContainingText(using, text);

		default:
			// Note that this shouldn't happen (eg, the above matches all
			// possible values for the How enum)
			throw new IllegalArgumentException(
					"Cannot determine how to locate element ");
		}
	}

	private boolean getExactDefinition(FindBy findBy) {
		// TODO Auto-generated method stub
		boolean exact = findBy.exact();
		return exact;
	}

	private String getAttributeValueDefinition(FindBy findBy) {
		// TODO Auto-generated method stub
		String attriButeValue = findBy.attributeValue();
		return attriButeValue;
	}

	private String getAngularAttributeDefinition(FindBy findBy) {
		// TODO Auto-generated method stub
		String angularAttribute = findBy.angularAttribute();
		return angularAttribute;
	}

	private String getColumnDefinition(FindBy findBy) {
		String column = findBy.column();
		return column;
	}

	private String getTextDefinition(FindBy findBy) {
		String text = findBy.text();
		return text;
	}

	private int getRowDefinition(FindBy findBy) {
		int row = findBy.row();
		return row;
	}

	private String getUsingDefinition(FindBy findBy) {
		String using = findBy.using();
		if (using.isEmpty()) {
			if (isWebChannel) {
				using = findBy.usingWeb();
			} else {
				using = findBy.usingMobile();
			}
		} else {
			if (!findBy.usingWeb().isEmpty() || !findBy.usingMobile().isEmpty()) {
				throw new IllegalArgumentException(
						"If you use 'using' attribute, you must not also use 'usingWeb' and 'usingMobile' attributes");
			}
		}
		return using;
	}

	private How getHowDefinition(FindBy findBy) {
		How how = findBy.how();
		if (how.equals(How.UNSET)) {
			if (isWebChannel) {
				how = findBy.howWeb();
			} else {
				how = findBy.howMobile();
			}
		} else {
			if (!findBy.howWeb().equals(How.UNSET)
					|| !findBy.howMobile().equals(How.UNSET)) {
				throw new IllegalArgumentException(
						"If you use 'using' attribute, you must not also use 'usingWeb' and 'usingMobile' attributes");
			}
		}
		return how;
	}

	protected void assertValidAnnotations() {
		FindBys findBys = (FindBys) this.getField().getAnnotation(FindBys.class);
		FindAll findAll = (FindAll) this.getField().getAnnotation(FindAll.class);
		FindBy findBy = (FindBy) this.getField().getAnnotation(FindBy.class);
		if (findBys != null && findBy != null) {
			throw new IllegalArgumentException(
					"If you use a \'@FindBys\' annotation, you must not also use a \'@FindBy\' annotation");
		} else if (findAll != null && findBy != null) {
			throw new IllegalArgumentException(
					"If you use a \'@FindAll\' annotation, you must not also use a \'@FindBy\' annotation");
		} else if (findAll != null && findBys != null) {
			throw new IllegalArgumentException(
					"If you use a \'@FindAll\' annotation, you must not also use a \'@FindBys\' annotation");
		}
	}
}


