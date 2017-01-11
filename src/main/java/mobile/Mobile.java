package mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lib.Log;
import lib.Log.Level;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.github.lalyos.jfiglet.FigletFont;

public class Mobile {
	
	public static boolean mobilePlatform = false;
	//public static AppiumDriver<MobileElement> appiumDriver;

	public static AppiumDriver getDriver() {
		return  (AppiumDriver) Web.getDriver() ;
	}

	public static MobileDriver getMobileDriver() {
		return  (MobileDriver) Web.getDriver() ;
	}
	public static void tapElement(String locator) {
	//	TouchAction act = new TouchAction(getDriver());
		IOSElement ele = findElement(locator);
		if (ele != null) {
			  TouchAction tap = new TouchAction(getDriver());
		      tap.press(ele).waitAction(2000).release();
			
		} else {
			System.out.println("Element not Present :" + locator);
		}

	}

	public static void tapWithPosition(int x, int y) {
		TouchAction tAction = new TouchAction(getDriver());
		tAction.tap(x, y).release().perform();

	}

	public static void wait(int inmillSec) throws InterruptedException {
		Thread.sleep(inmillSec);
	}

	public static void setEdit(String locator, CharSequence... sTextValue) {
		IOSElement ele = findElement(locator);
		if (ele != null) {
			ele.clear();
			ele.sendKeys(sTextValue);
		
		} else {
			System.out.println("Element not Present :" + locator);
		}

	}
	

	public static void setValue(String locator, String sTextValue) {
		try {
			IOSElement ele = findElement(locator);
			if (ele != null) {
				ele.setValue(sTextValue);

				Reporter.logEvent(Status.INFO, "Enter Value  : " + sTextValue,
						"", true);
			} else {
				System.out.println("Element not Present :" + locator);
			}
		} catch (WebDriverException e) {
			System.out.println("Not able to set Value");

		}
	}

	public static String getText(String locator) {
		String actText = "";
		IOSElement ele = findElement(locator);
		if (ele != null) {
			actText = ele.getText();

		} else {
			System.out.println("Element not Present :" + locator);
		}
		return actText;

	}

	public static void setSlider(String locator, String sText) {
		try {
			String sObj = "//*[@name='" + locator
					+ "']/following-sibling::XCUIElementTypeSlider";
			IOSElement ele = findElement(sObj);
			if (ele != null) {
			
				ele.sendKeys(sText);
			
			} else {
				System.out.println("Element not Present :" + locator);
			}
            
			
		} catch (WebDriverException e) {
			System.out.println("WebDriverException is thrown" + locator);
		}

	}
	public static void setSliderValue(String locator, String sText) {
		try {
			String sObj = "//*[@name='" + locator
					+ "']/following-sibling::XCUIElementTypeTextField";
			IOSElement ele = findElement(sObj);
			if (ele != null) {
			    ele.clear();
				ele.sendKeys(sText);
			
			} else {
				System.out.println("Element not Present :" + locator);
			}
            
			
		} catch (WebDriverException e) {
			System.out.println("WebDriverException is thrown" + locator);
		}

	}	
	
	public static void clickElement(String locator) {
		IOSElement ele = findElement(locator);

		if (ele != null) {
			ele.click();
			// Reporter.logEvent(Status.INFO,"Click Element  : "+locator,
			// "",true);
		}

	}

	// pressed x axis & y axis of seekbar and move seekbar till the end

	public static void slider(String locator, int toPercentage) {
		try {

			TouchAction act = new TouchAction((MobileDriver) getDriver());

			String sObj = "//*[@name='" + locator
					+ "']/following-sibling::XCUIElementTypeSlider";
			IOSElement ele = findElement(sObj);
			System.out.println(ele.getText());

			int xActualPer = Integer.parseInt(ele.getText().replace("%", ""));

			int xAxisStartPoint = ele.getLocation().getX();
			int xAxisEndPoint = xAxisStartPoint + ele.getSize().getWidth();
			int yAxis = ele.getLocation().getY();
			int xAxis = xAxisEndPoint - xAxisStartPoint;
			System.out.println(xAxis);

			int fromX = xAxis * xActualPer / 100 + xAxisStartPoint;

			int toX = xAxis * toPercentage / 100 + xAxisStartPoint;

			System.out.println(fromX);
			System.out.println(toX);

			// TouchAction act=new TouchAction(driver);

			act.press(fromX, yAxis).waitAction(500).moveTo(toX, yAxis)
					.release().perform();
		} catch (Exception e) {
			System.out.println("Not able to slide the slider");
		}

	}

	public static boolean isElementPresent(String locator) {
		IOSElement ele = findElement(locator);
		Boolean flag = false;
		if (ele != null) {
			if (ele.isDisplayed()) {
				flag = true;
			} else {
				System.out.println("Not Display :" + locator);
				flag = false;
			}
		} 
		return flag;
	}
	
	public static boolean assertElementPresent(By link) {
		Boolean flag = false;

		try{
		getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		IOSElement ele = (IOSElement) getDriver().findElement(link);

		if (ele != null) {
			if (ele.isDisplayed()) {
				flag = true;
			} else {
				System.out.println("Not Display :" );
				flag = false;
			}
		} 
		
	}  catch(Exception e){
		System.out.println("Not Display :" );
	}
		return flag;
	}
	

	public static void waitForPageToLoad(String locator) {
		try {
			IOSElement id = findElement(locator);
			WebDriverWait wait = new WebDriverWait(getDriver(),
					Integer.parseInt(Stock.getConfigParam("objectSyncTimeout")));
			wait.until(ExpectedConditions.elementToBeClickable(id));
		} catch (Exception e) {
			System.out.println("Not able to  load the Page waitForPageToLoad");
		}
	}

	public void waitForElementToDisAppear(String id) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 15);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
	}

	public static IOSElement findElement(String locator) {
		getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		IOSElement element = null;
		for (int i = 0; i < 5; i++) {
			try {

				try {
					// System.out.println("Name");
					element = (IOSElement) getDriver().findElementByAccessibilityId(
							locator);

				} catch (Exception e1) {
					try {
						// System.out.println("Accessibility");
						element = (IOSElement) getDriver()
								.findElementByName(locator);

					} catch (Exception e2) {
						try {
							// System.out.println("xpath");
							element = (IOSElement) getDriver()
									.findElementByXPath(locator);

						} catch (Exception e3) {

							try {
								// System.out.println("ID");
								element = (IOSElement) getDriver()
										.findElementById(locator);

							} catch (Exception e4) {

								element = null;

							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Element Not fount at FindElement");

			}

			if (element != null) {
				while (!element.isDisplayed()) {
				
                //element.swipe(SwipeElementDirection.UP, 1000);
					scrollTo(element);					

				}
				getDriver().manage().timeouts()
						.implicitlyWait(10, TimeUnit.SECONDS);
				return element;
			}
		}

		return element;

	}

	public static void hideKeyboard() {
		getDriver().getKeyboard().pressKey("\n");
	}

	/**
	 * <pre>
	 * Method to return iOS web element
	 * </pre>
	 * 
	 * @param ele
	 * @return
	 */
	public static IOSElement getIOSElement(By by) {
		int waitTime = 5;
		// try {
		// FluentWait<?> wait = (FluentWait<?>) new WebDriverWait(driver, 30)
		// .pollingEvery(10,TimeUnit.SECONDS)
		// .ignoring(NoSuchElementException.class)
		// .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ele)));
		//
		// return ((IOSElement) driver.findElementByXPath(ele));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return null;

		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return (IOSElement) getDriver().findElement(by);
		} catch (org.openqa.selenium.TimeoutException e) {
			return null;
		}

	}

	private static void onTap(WebElement element) {
		try {

			TouchAction tAction = new TouchAction((MobileDriver) getDriver());

			Point loc = element.getLocation();
			int x = loc.getX();
			int y = loc.getY();
			// driver.tap(x, element, y);
			tAction.tap(element).release().perform();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to click");
		}
	}

	public static void scrollTo(IOSElement scrollToElement) {

		Dimension dimensions = getDriver().manage().window().getSize();
		Double screenHeightStart = dimensions.getHeight() * 0.6;
		int scrollStart = screenHeightStart.intValue();
		Double screenHeightEnd = dimensions.getHeight() * 0.2;
		int scrollEnd = screenHeightEnd.intValue();
//		TouchAction tAction = new TouchAction((MobileDriver) getDriver());
	//	tAction.press(0, scrollStart).waitAction().moveTo(0,scrollEnd).release().perform();
		getDriver().swipe(15, scrollStart, 15, scrollEnd, 1000);
	}

	public void swipeLeftUntilTextExists(String expected) {
		do {
			swipeLeft();
		} while (!getDriver().getPageSource().contains(expected));
	}

	public static void swipeRightUntilLogOutScreen() {
		do {
			swipeRight();
		} while (!isElementPresent(""));
	}

	public static void swipeRight() {
		Dimension size = getDriver().manage().window().getSize();
		int startx = (int) (size.width * 0.9);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		getDriver().swipe(startx, starty, endx, starty, 5000);
	}

	public static void swipeLeft() {
		Dimension size = getDriver().manage().window().getSize();
		int startx = (int) (size.width * 0.8);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		getDriver().swipe(startx, starty, endx, starty, 1000);
	}

	/**
	 * method to set the context to required view.
	 *
	 * @param context
	 *            view to be set
	 */
	public static void setContext(String context) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<String> contextNames = getDriver().getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); // prints out something like
												// NATIVE_APP \n WEBVIEW_1
		}
		getDriver().context((String) contextNames.toArray()[1]); // set context
																	// to
																	// WEBVIEW_1

		Log.Report(Level.INFO, "Current context" + getDriver().getContext());
	}

	public static void sendKey(String locator, CharSequence... sTextValue) {
		IOSElement ele = findElement(locator);
		if (ele != null) {

			ele.sendKeys(sTextValue);
		}
		Reporter.logEvent(Status.INFO, "Enter Value  : " + sTextValue, "", true);
	}

	public static void DataPicker(String locator, String sTextValue) {

		String value0 = sTextValue;
		String temp0 = "";
		while (!temp0.equals(value0)) {
			try {
				sendKey(locator, sTextValue);

			} catch (Exception e) {
				// Do Nothing
			} finally {
				temp0 = getText(locator);
				System.out.println("temp0" + temp0);
			}
		}
	}

	
	

	

	public static void clickDeleteImage(String sLabel) {
		String sObj = "//*[@name='" + sLabel
				+ "']/preceding-sibling::XCUIElementTypeButton";
		clickElement(sObj);

	}

	public static void clickDeleteButton(String sLabel) {
		String sObj = "//*[@name='" + sLabel
				+ "']/following-sibling::XCUIElementTypeButton[@name='DELETE']";
		clickElement(sObj);

	}

	

	public static void AllFund(String sLabel, Boolean bValue) {
		String MONTH_RADIO = "//*[@name='" + sLabel
				+ "']/preceding-sibling::XCUIElementTypeCell";
		String isSelected = "//*[@name='"
				+ sLabel
				+ "']/preceding-sibling::XCUIElementTypeImage[@name='select-on']";
		try {
			IOSElement eleCheckBoxEle = findElement(MONTH_RADIO);
			IOSElement isSelectObjEle = findElement(isSelected);
			boolean sChecked = false;
			if (isSelectObjEle != null) {
				sChecked = isSelectObjEle.isDisplayed();
			}
			if (bValue == true) {
				if (!sChecked == true) {
					eleCheckBoxEle.click();
				}
			} else {
				if (sChecked == true) {
					eleCheckBoxEle.click();
				}
			}
		} catch (Exception e) {
			System.out.println("Not able to Select ");
		}

	}

	public static void checkItem(String sLabel, Boolean bValue) {
		String MONTH_RADIO = "//*[@name='" + sLabel
				+ "']/preceding-sibling::XCUIElementTypeImage";
		String isSelected = "//*[@name='"
				+ sLabel
				+ "']/preceding-sibling::XCUIElementTypeImage[@name='select-on']";
		try {
			IOSElement eleCheckBoxEle = findElement(MONTH_RADIO);
			IOSElement isSelectObjEle = findElement(isSelected);
			boolean sChecked = false;
			if (isSelectObjEle != null) {
				sChecked = isSelectObjEle.isDisplayed();
			}
			if (bValue == true) {
				if (!sChecked == true) {
					eleCheckBoxEle.click();
				}
			} else {
				if (sChecked == true) {
					eleCheckBoxEle.click();
				}
			}
		} catch (Exception e) {
			System.out.println("Not able to Select ");
		}

	}
	 public static void figlet(String text) {
	        String asciiArt1 = null;
	        try {
	            asciiArt1 = FigletFont.convertOneLine(text);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println(asciiArt1);
	    }
	
}
