/**
 * 
 */
package pageobjects.reports;
import framework.util.CommonLib;
import gwgwebdriver.How;
import gwgwebdriver.NextGenWebDriver;
import gwgwebdriver.pagefactory.Channel;
import gwgwebdriver.pagefactory.NextGenPageFactory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.homepage.HomePage;
import pageobjects.login.LoginPage;
import annotations.FindBy;

import com.aventstack.extentreports.Status;

/**
 * @author rvpndy
 *
 */
public class ReportsPage extends LoadableComponent<ReportsPage>{

	static ResultSet queryResultSet;
	public static String reportRequestNo = "";
	private int countRecurssion = 0;

	@FindBy(how =How.ID, using="framef")
	private WebElement reportsFrame;

	@FindBy(how=How.XPATH,using=".//*[@id='standardreportsTab']/a")
	private WebElement standardReports;

	@FindBy(how=How.XPATH,using=".//*[@id='myreportsTab']/a")
	private WebElement myReports;

	@FindBy(how=How.XPATH,using=".//*[@id='recurringTab']/a")
	private WebElement myRecurringReports;

	@FindBy(how=How.MODEL,using="report.selected")
	private WebElement selectMyReport;

	@FindBy(how=How.ANGULAR_ATTRIBUTE,using="click",text="openReportOrderForm")
	private WebElement openReportOrderForm;

	@FindBy(how=How.REPEATER,using="report in category.reports",exact=false)
	private List<WebElement> allReportsUnderTab;

	@FindBy(how = How.ID,using="report-order-form-container")
	private WebElement reportOrderForm;

	@FindBy(how = How.ID, using = "schedulingErrorMessage")
	private WebElement reportSchedulingError;

	@FindBy(how = How.ID,using = "planSelection")
	private WebElement planSelection;

	@FindBy(how = How.MODEL, using = "query")
	private WebElement planSearchBox;

	@FindBy(how = How.REPEATER, using = "option in options", exact=true)
	private List<WebElement> planSearchResult;

	@FindBy(how = How.MODEL,using = "scheduledReport.query")
	private WebElement myReportsFilter;

	@FindBy(how = How.ANGULAR_ATTRIBUTE,using = "click",text="submitOrderForm")
	private WebElement submitReport;

	@FindBy(how = How.ID,using = "progressMessageContainer")
	private WebElement reportSubmitProgress;

	@FindBy(how = How.MODEL, using="reportSearchCriteria")
	private WebElement reportSearchBox;

	@FindBy(how = How.REPEATER, using="result in category.reports", exact=false)
	private List<WebElement> reportSearchResult;

	@FindBy(how = How.ID, using="EMAIL_NOTIFICATION_IND1")
	private WebElement setEmailNotification;

	@FindBy(how = How.ID, using="EMAIL_NOTIFICATION_IND0")
	private WebElement noEmailNotification;

	@FindBy(how = How.ID, using = "USER_EMAIL_ADDR")
	private WebElement emailAddressBox;

	@FindBy(how = How.XPATH, using=".//span[contains(text(),'Request #')]")
	private WebElement rptRequestNo;

	@FindBy(how = How.XPATH, using=".//table[contains(@class,'myreport')]/tbody/tr")
	private WebElement myReportTable;

	@FindBy(how = How.REPEATER_ROW, using="report in scheduledReport.filteredReports", exact=true)
	private List<WebElement> myScheduledReports;
	
	@FindBy(how = How.XPATH, using=".//iframe[contains(@id,'page_1_iframe')]")
	private WebElement reportFrame;
	
	@FindBy(how = How.CSS_CONTAINING_TEXT, using="span.ng-binding.ng-scope",text="We are currently "
			+ "unable to retrieve your reports. Please try again later")
	private WebElement myReportsError;
	
	@FindBy(how = How.ANGULAR_ATTRIBUTE, using = "click" ,text="handleOk()")
	private WebElement reportDownloadButton;


	private WebElement getReportByName(String reportName){
		WebElement reportElement = null;
		try{
			if(allReportsUnderTab.size()>0){
				for(WebElement ele : allReportsUnderTab){
					if(ele.isDisplayed()){
						String reportLabel =ele.findElement(By.xpath("./td[3]/span/a")).getText();
						if(reportLabel.equalsIgnoreCase(reportName))
							return ele;
					}


				}
			}
			else{
				throw new Exception("Selected tab does not have reports");//This is not possible
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return reportElement;
	}

	private WebElement getReportOption(String reportName){
		WebElement report = getReportByName(reportName);
		if(Web.isWebElementDisplayed(report)){
			return report.findElement(By.xpath("./td[5]/div/a"));}
		else
			throw new NoSuchElementException("Report element is not displayed");
	}


	/**All reports category tab under Standard reports
	 * @param tabName e.g. Assets & Investments, Cross Plan etc.
	 * @return report category tab WebElement
	 */
	private WebElement getStandardReportsTab(String tabName){
		return Web.getDriver().findElement(By.xpath("//ul[@id='investmentTabs']/li/a/span[text()='"+tabName+"']"));
	}

	LoadableComponent<?> parent;

	public ReportsPage(){
		Web.getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		Web.nextGenDriver = new NextGenWebDriver(Web.getDriver() );
		NextGenPageFactory.initWebElements(Web.getDriver(),this);
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		Assert.assertTrue(Web.isWebElementDisplayed(standardReports));
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		Web.getDriver().switchTo().defaultContent();
		HomePage homePage = (HomePage) this.parent;

		new HomePage(new LoginPage(), false, new String[] {
			Stock.GetParameterValue("username"),
			Stock.GetParameterValue("password") }).get();

		Reporter.logEvent(Status.PASS,
				"Check if the user has landed on homepage",
				"The user has landed on homepage", true);
		//homePage.logoutPSC(homePage.isDifferentUser());
		if(Stock.GetParameterValue("userType").contains("PL"))
			try {
				CommonLib.switchToDefaultPlanPartnerLinkUserAndNumberOfPlansLessThan25();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(Web.isWebElementDisplayed(new HomePage(), "Reports Menu", true)){
			Web.clickOnElement(new HomePage(), "Reports Menu");
			Web.nextGenDriver.waitForAngular();
			Web.clickOnElement(new HomePage(), "Standard Reports");
			Web.nextGenDriver.waitForAngular();
			Web.waitForElement(reportsFrame);
			Web.getDriver().switchTo().defaultContent();
			Web.getDriver().switchTo().frame(reportsFrame);
			Web.nextGenDriver.waitForAngular();
		}

	}

	private void clickOnMyReports(){
		if(Web.isWebElementDisplayed(myReports))
			Web.clickOnElement(myReports);
	}

	private void clickOnStandardReports(){
		if(Web.isWebElementDisplayed(standardReports))
			Web.clickOnElement(standardReports);
	}

	private void clickOnMyRecurringReports(){
		if(Web.isWebElementDisplayed(myRecurringReports)){
			Web.clickOnElement(myRecurringReports);
		}
	}

	private void clickOnStandardReportsTab(String tabName){
		if(Web.isWebElementDisplayed(getStandardReportsTab(tabName)))
			Web.clickOnElement(getStandardReportsTab(tabName));
	}

	private void clickOnReport(String reportName){
		Web.clickOnElement(getReportByName(reportName));
	}

	public boolean isReportOrderForm()
	{
		boolean isReportOrderForm = false;
		try{
			this.clickOnStandardReports();
			Web.nextGenDriver.waitForAngular();
			this.clickOnStandardReportsTab(Stock.GetParameterValue("tabName"));
			Web.nextGenDriver.waitForAngular();
			WebElement reportOption = this.getReportOption(Stock.GetParameterValue("reportName"));
			if(reportOption.isEnabled())
				reportOption.click();
			Web.nextGenDriver.waitForAngular();
			Web.clickOnElement(reportOption.findElement(By.xpath("./following-sibling::ul/li[1]/a")));
			Web.nextGenDriver.waitForAngular();
			if(isReportOrderFormOpen())
				isReportOrderForm = true;

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return isReportOrderForm;
	}

	private boolean isReportOrderFormOpen(){
		if(Web.isWebElementDisplayed(planSelection)){
			return true;
		}
		return false;
	}

	private void submitReport(){
		if(Web.isWebElementDisplayed(submitReport, false)){
			Web.clickOnElement(submitReport);
			Reporter.logEvent(Status.PASS, "Submit report order", "Report order is submitted", true);
		}
	}

	private void searchResultByTxnCode(String txnCode){
		if(Web.isWebElementDisplayed(reportSearchBox, false))
			Web.setTextToAngularTextBox(reportSearchBox, txnCode);
		Web.nextGenDriver.waitForAngular();
	}

	private boolean isSearchedReportDisplayed(){
		if(Web.isWebElementDisplayed(reportSearchResult.get(0), true))
			return true;
		return false;
	}

	private void clickOnSearchedReport(String txnCode){
		searchResultByTxnCode(txnCode);
		if(isSearchedReportDisplayed()){
			System.out.println(reportSearchResult.get(0).getText());
			Web.clickOnElement(reportSearchResult.get(0).findElement(By.tagName("a")));}
		Web.nextGenDriver.waitForAngular();
	}

	public boolean isReportOrderFormByTxnCodeSearch(String reportTxnCode){
		clickOnSearchedReport(reportTxnCode);
		//if(isReportOrderForm())
		if(Web.isWebElementDisplayed(planSelection, false))
			return true;
		return false;
	}

	private ResultSet getReportParams(){
		String[] query = Stock.getTestQuery("getReportOrderParams");
		return DB.executeQuery(query[0], query[1], 
				Stock.GetParameterValue("txnCode"));
	}

	private List<String> getReportOrderPageParams(){

		List<String> reportParams = new ArrayList<String>() ;
		int i = 0;
		try{
			queryResultSet = getReportParams();
			if(queryResultSet!=null){
				while(queryResultSet.next()){
					String paramName = queryResultSet.getString("PARM_NAME");
					System.out.println(paramName);
					reportParams.add(i,paramName);
					i++;

				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return reportParams;
	}

	private void fillReportOrderParam(){
		try{
			List<String> reportParams = getReportOrderPageParams();
			if(reportParams.size()>0){
				for(int i = 0;i<reportParams.size();i++){
					if(reportParams.get(i).toLowerCase().contains("ga_id")){
						fillPlanOnReportOrderPage();
					}
					else if(reportParams.get(i).toLowerCase().contains("date")&&
							!reportParams.get(i).toLowerCase().contains("update")){
						String tagName = Web.getDriver().findElement
								(By.id(reportParams.get(i))).getTagName();
						String label = Web.getDriver().findElement
								(By.xpath(".//*[@id='"+reportParams.get(i)+"']/../preceding-sibling::label")).getText();
						if(tagName.equalsIgnoreCase("select"))
							selectParamOnReportOrderPage(reportParams.get(i),label);
						else if(tagName.equalsIgnoreCase("input"))
							enterDateOnReportOrderPage(reportParams.get(i),label);
					}
					else if(reportParams.get(i).toLowerCase().contains("label")){
						Reporter.logEvent(Status.INFO, "There are instructions on page", 
								"There are instructions on page", true);
					}
					else{
						List<WebElement> reportOrderPageElements = Web.getDriver().findElements
								(By.xpath(".//*[contains(@id,'"+reportParams.get(i)+"')]"));
						if(reportOrderPageElements.size()>1){
							String fieldLabel = Web.getDriver().findElement
									(By.xpath(".//*[contains(@id,'"+reportParams.get(i)+"')]/../../preceding-sibling::label")).getText();
							if(fieldLabel.endsWith("?")||fieldLabel.endsWith("? *")){
								String fieldValue = Stock.GetParameterValue(fieldLabel);
								if(fieldValue.equalsIgnoreCase("Yes")){
									Web.clickOnElement(reportOrderPageElements.get(1));
									Reporter.logEvent(Status.INFO, "Set value for: "+fieldLabel, "Yes is set for: "+fieldLabel, true);
								}

								else if(fieldValue.equalsIgnoreCase("No")){
									Web.clickOnElement(reportOrderPageElements.get(0));
									Reporter.logEvent(Status.INFO, "Set value for: "+fieldLabel, "No is set for: "+fieldLabel, true);
								}

							}

						}
						else if(reportOrderPageElements.size()==1){
							String tagName = reportOrderPageElements.get(0).getTagName();
							String fieldLabel = Web.getDriver().findElement
									(By.xpath(".//*[@id='"+reportParams.get(i)+"']/../preceding-sibling::label")).getText();
							if(tagName.equalsIgnoreCase("select"))
								selectParamOnReportOrderPage(reportOrderPageElements.get(0).getAttribute("id"),fieldLabel);
						}
						else
							throw new Error("No elements in the param list");
					}
				}
			}
			else
				throw new Error("No report parameters found in database");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private void selectParamOnReportOrderPage(String selectId, String fieldLabel) {
		// TODO Auto-generated method stub
		Web.selectDropDownOption(Web.getDriver().findElement(By.id(selectId)), 
				Stock.GetParameterValue(fieldLabel));
	}

	private void enterDateOnReportOrderPage(String fieldId, String fieldLabel) {
		// TODO Auto-generated method stub
		Web.setTextToAngularTextBox(Web.getDriver().findElement(By.id(fieldId)), 
				Stock.GetParameterValue(fieldLabel));
		Reporter.logEvent(Status.INFO, "Enter date in:"+fieldLabel, "Date is entered.", true);

	}

	private void fillPlanOnReportOrderPage() {
		// TODO Auto-generated method stub
		if(planSelection.getText().contains(Stock.GetParameterValue("planId")))
			Reporter.logEvent(Status.INFO, "Enter plan on report order page", "Plan is already selected on order page", true);
		else{
			Web.setTextToAngularTextBox(planSearchBox, Stock.GetParameterValue("planId"));
			Web.nextGenDriver.waitForAngular();
			if(planSearchResult.size()>0){
				for(WebElement plan : planSearchResult){
					if(plan.getText().contains(Stock.GetParameterValue("planId"))){
						Web.clickOnElement(plan);
						Web.nextGenDriver.waitForAngular();
						Reporter.logEvent(Status.INFO,"Enter plan on report order page", plan.getText()+" is selected", true);
						break;
					}
				}
			}
			else{
				throw new Error("Plan is not found");
			}
		}

	}

	private void switchOnOffEmailNotification(){
		if(Stock.GetParameterValue("emailNotification").equalsIgnoreCase("Yes")){
			Web.setTextToAngularTextBox(emailAddressBox, Stock.GetParameterValue("emailAddress"));
			Reporter.logEvent(Status.INFO, "Enter email address on order page", "Email address is filled in address box:"+Stock.GetParameterValue("emailAddress"), true);
		}
		else{
			Web.clickOnElement(this.noEmailNotification);
			Reporter.logEvent(Status.INFO, "Set email notification to No", "Email notification is set to No", true);
		}
	}

	private String getReportTypeCode(){
		String typeCode = null;
		try{
			String[] getTypeCodeQuery = Stock.getTestQuery("getReportTypeCode");
			queryResultSet = DB.executeQuery(getTypeCodeQuery[0], getTypeCodeQuery[1], 
					Stock.GetParameterValue("txnCode"));
			if(queryResultSet!=null){
				while(queryResultSet.next()){
					typeCode = queryResultSet.getString("TYPE_CODE");
				}
			}
			else{
				throw new Error("getReportTypeCode query results null resultSet");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return typeCode;
	}

	public void orderReport(){
		this.fillReportOrderParam();
		this.switchOnOffEmailNotification();
		this.submitReport();
		Web.nextGenDriver.waitForAngular();
		if(isReportOrdered()){
			String reportTypeCode = getReportTypeCode();
			//multi-step reports
			if(reportTypeCode.equalsIgnoreCase("RPT_MULTI")||
					reportTypeCode.equalsIgnoreCase("RPT_PUB")||
					reportTypeCode.equalsIgnoreCase("RPT_ISIS")){
				if(verifyMultiStepReports()){
					Reporter.logEvent(Status.PASS, "Ordered report is verified", 
							"Ordered report is verified", true);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Ordered report is verified", 
							"Ordered report is not verified", true);
				}
			}
			//single step reports
			else if(reportTypeCode.equalsIgnoreCase("RPT_BO")||
					reportTypeCode.equalsIgnoreCase("RPT_WEBI")){
				if(verifySingleStepReports()){
					Reporter.logEvent(Status.PASS, "Ordered report is verified", 
							"Ordered report is verified", true);
				}
				else{
					Reporter.logEvent(Status.FAIL, "Ordered report is verified", 
							"Ordered report is not verified", true);
				}

			}
		}
		else{
			Reporter.logEvent(Status.FAIL, "Report ordered sucessfully", 
					"Report is not ordered", true);
		}
	}

	private boolean verifyMultiStepReports(){
		boolean isVerified = false;
		String primeWindow = "";
		try{
			Reporter.logEvent(Status.INFO, "Verifying report..", "Verifying report..", false);
			CommonLib.FrameSwitchONandOFF(true, reportsFrame);
			clickOnStandardReports();
			Web.nextGenDriver.waitForAngular();
			clickOnMyReports();
			Web.nextGenDriver.waitForAngular();
			if(Web.isWebElementDisplayed(myReportsError, false)){
				clickOnStandardReports();
				Web.nextGenDriver.waitForAngular();
				clickOnMyReports();
				Web.nextGenDriver.waitForAngular();
			}
			System.out.println("My scheduled reports size: "+myScheduledReports.size());
			if(myScheduledReports.size()>0){
				primeWindow = Web.getDriver().getWindowHandle();
				for(WebElement report : myScheduledReports){
					if(report.findElement(By.xpath("td[6]")).getText().trim()
							.equalsIgnoreCase(reportRequestNo)){
						WebElement status = report.findElement(By.xpath("td[7]"));
						System.out.println("Report : "+report.findElement(By.xpath("td[6]"))
								.getText()+"... status : "+status.getText());
						if(status.getText().trim().equalsIgnoreCase("View")){
							Web.clickOnElement(status.findElement(By
									.xpath("//a[contains(text(),'View')]")));
							Web.nextGenDriver.waitForAngular();
							if(Web.isWebElementDisplayed(reportDownloadButton, false))
							{
								Web.clickOnElement(reportDownloadButton);
								String fileName = CommonLib.getDownloadedDocumentName(Stock.getConfigParam("Download_Directory"),"pdf");
								Reporter.logEvent(Status.INFO, "Downloaded file name is: ", fileName, false);
								return true;
							}
							for(String window : Web.getDriver().getWindowHandles()){
								Web.getDriver().switchTo().window(window);
							}
							if(Web.isWebElementDisplayed(reportFrame,true)){
								Web.getDriver().switchTo().frame(reportFrame);
								if(verifyReportContent()){
									Reporter.logEvent(Status.INFO, 
											"Report opened and verified successfully", 
											"Report opened and verified successfully", true);
									Web.getDriver().close();
									System.out.println("BO window closed");
									Web.getDriver().switchTo().window(primeWindow);
									System.out.println("Switch to old window successfully");
									return true;
								}
							}
							else{
								Reporter.logEvent(Status.FAIL,"Generate Report",
										"Report generation failed",true);
								System.out.println("BO Report not displayed");
								if(Web.getDriver().getWindowHandles().size() > 1){
									Web.getDriver().close();
									Web.getDriver().switchTo().window(primeWindow);}
								break;
							}
						}
						else if(status.getText().trim().equalsIgnoreCase("Failed")){
							Reporter.logEvent(Status.FAIL, "Verify report", 
									"Report status is Failed in my report table", true);
						}
						else{
							//when report status is Pending, running
							if(countRecurssion==600) throw new 
							Error("Report is not getting generated after 10 mins");
							Thread.sleep(1000);
							clickOnMyReports();
							Web.nextGenDriver.waitForAngular();
							if(Web.isWebElementDisplayed(myReportsError, false)){
								clickOnStandardReports();
								Web.nextGenDriver.waitForAngular();
								clickOnMyReports();
								Web.nextGenDriver.waitForAngular();
							}
							System.out.println("Report page refresh count : "+ countRecurssion);
							countRecurssion++;
							if(verifyMultiStepReports())
								return true;
						}
					}
				}
			}


		}
		catch(Exception e){
			e.printStackTrace();
		}
		Reporter.logEvent(Status.FAIL, "Reporting Negative", "Reporting Negative", true);
		return isVerified;
	}

	private boolean verifyReportContent() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean verifySingleStepReports(){
		boolean isVerified = false;
		try{

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return isVerified;
	}

	private String getReportRequestNumber(){
		if(Web.isWebElementDisplayed(rptRequestNo, true)){
			String reportNumber = rptRequestNo.getText();
			Pattern p = Pattern.compile("(\\d+)");
			Matcher m = p.matcher(reportNumber);
			while(m.find()){
				reportRequestNo = reportRequestNo+m.group(1);
			}
			Reporter.logEvent(Status.INFO, "Ordered report number is: ", reportRequestNo, true);
		}
		return reportRequestNo;
	}

	private boolean isReportOrdered(){
		this.getReportRequestNumber();
		if(reportRequestNo!=null && StringUtils.isNumeric(reportRequestNo)){
			Reporter.logEvent(Status.PASS, "Report ordered successfully", "Report ordered successfully", true);
			return true;
		}
		return false;
	}





}
