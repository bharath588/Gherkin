package framework.util;

import lib.Stock;
import lib.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CommonLib {

	public static void HighlightElement(WebElement ele, WebDriver driver) {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"color: yellow; border: 2px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"");
		}
	}
	
	public static void enterData(WebElement ele, String value) {
		String tagName = "";
		try {
			tagName = ele.getTagName();
			if (tagName.equals("input")) {
				if (ele.getAttribute("type").equals("text")) {
					Web.setTextToTextBox(ele, value);
				} else if (ele.getAttribute("type").equals("radio")) {
					Web.clickOnElement(ele);
				}
			}
			if (tagName.equals("select")) {
				Select select = new Select(ele);
				for (WebElement opt : select.getOptions()) {
					if (Web.VerifyPartialText(value, opt.getText(), true)) {
						opt.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fillForm(WebElement parentNode, String... coLNames) {
		String tagName = "";
		try {
			for (String colNm : coLNames) {
				tagName = parentNode.findElement(By.id(colNm)).getTagName();
				if (tagName.equals("input")) {
					if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("text")) {
						Thread.sleep(500);
						Web.setTextToTextBox(
								parentNode.findElement(By.id(colNm)),
								Stock.globalTestdata.get(colNm));
					} else if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("radio")) {
						Thread.sleep(500);
						Web.clickOnElement(parentNode.findElement(By.id(colNm)));
					}
				}
				if (tagName.equals("select")) {
					Select select = new Select(parentNode.findElement(By
							.id(colNm)));
					for (WebElement opt : select.getOptions()) {
						if (Web.VerifyPartialText(
								Stock.globalTestdata.get(colNm), opt.getText(),
								true)) {
							opt.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean VerifyText(String inExpectedText,
			String inActualText, boolean... ignoreCase) {
		boolean isMatching = false;

		if (ignoreCase.length > 0) {
			if (ignoreCase[0]) {
				if (inExpectedText.trim().equalsIgnoreCase(inActualText.trim())) {
					isMatching = true;
				}
			} else {
				if (inExpectedText.trim().equals(inActualText.trim())) {
					isMatching = true;
				}
			}
		} else {
			if (inExpectedText.trim().equals(inActualText.trim())) {
				isMatching = true;
			}
		}
		return isMatching;
	}

}
