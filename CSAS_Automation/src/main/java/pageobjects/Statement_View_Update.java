package pageobjects;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.aventstack.extentreports.Status;

import framework.util.CommonLib;

public class Statement_View_Update extends LoadableComponent<Statement_View_Update> {
	LoadableComponent<?> parent;
	
	Actions action = new Actions(Web.getDriver());
	
	public Statement_View_Update()
	{
		PageFactory.initElements(Web.getDriver(), this);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(name="selectedStmtOnDemand")
	WebElement statement_on_demand_order_checkbox;
	
	@FindBy(xpath="//input[@value='fax']")
		WebElement fax_radio_button;
	
	
	@FindBy(xpath = "//*[@class='pageMenuTitle' and text()='Statement View/Update']")
	 WebElement statementPageTitle;
	
	
	//@FindBy(xpath=".//*[@id='partStatementForm']/table/tbody/tr[3]/td/div/div[2]/table[1]/tbody/tr/td[3]/input")
	//WebElement standard_mail_radio_button;
	//@FindBy(xpath=".//*[@id='partStatementForm']/table/tbody/tr[3]/td/div/div[2]/table[2]/tbody/tr[1]/td[2]/input")
	//WebElement first_name_text_box;
	@FindBy(name ="faxNumberPart1")
	WebElement fax_number_part1;
	
	@FindBy(name ="faxNumberPart2")
	WebElement fax_number_part2;
	@FindBy(name ="faxNumberPart3")
	WebElement fax_number_part3;
	@FindBy(xpath=".//input[@value='stdMail']")
	WebElement standard_mail_radio_button;
	@FindBy(name ="firstName")
	WebElement first_name;
	
	@FindBy(name="lastName")
	WebElement last_name;
	
	@FindBy(name ="firstLineMailing")
	WebElement first_line_mailing;
	
	@FindBy(name ="scndLineMailing")
	WebElement second_line_mailing;
	
	@FindBy(name ="city")
	WebElement city;
	@FindBy(name ="stateCode")
	WebElement state_code;
	@FindBy(name ="zipCode")
	WebElement zip_code;
	@FindBy(name="action")
	WebElement continue_button;
	
	@FindBy(xpath="//*[@value ='Submit']")
	WebElement submit;
	
	@FindBy(xpath="//*[@class='messageContent']")
	WebElement confirmation_number;
	@FindBy(xpath=".//*[@id='oCMenu_319']")
	WebElement search;
	@FindBy(name = "searchPartId")
	WebElement partId;
	@FindBy(xpath=".//input[@id='submitPpt']")
	WebElement submit_button;
	@FindBy(xpath=".//*[@id='oCMenu_315']")
	WebElement participant_info;
	@FindBy(xpath=".//*[@id='oCMenu_15098']")
	WebElement view_order_statement;
	@FindBy(xpath = ".//*[@id='oCMenu_319']")
	WebElement search_menu;
	
	
	/*@FindBy(xpath=".//*[@id='partStatementForm']/table/tbody/tr[4]/td/table/tbody/tr/td/input")
	WebElement continue_button;*/
	
	

	

	public void selecting_participant_info()
	{
		Web.waitForElement(participant_info);
		Web.clickOnElement(participant_info);
		Web.waitForElement(view_order_statement);
		Web.clickOnElement(view_order_statement);
		
		
	}
	
	
	
	
	public void verifying_if_fax_is_selected()
	{
		try{
		Web.waitForElement(fax_radio_button);
		if(fax_radio_button.isSelected())
		{
			Reporter.logEvent(Status.PASS, "Veryfing if fax radio button is selected","Fax radio button is selected",false);
		}
		else
		{
			Reporter.logEvent(Status.FAIL,"Verifying if fax radio button is selected","Fax radio button is not selected",false);
		}
		 
	}catch(Exception e)
		{
		e.printStackTrace();
		}
	}
	public void checking_fax()
	{ 
	//	Web.waitForElement(statement_on_demand_order_checkbox);
		Web.clickOnElement(statement_on_demand_order_checkbox);
		Web.clickOnElement(fax_number_part1);
		Web.setTextToTextBox(fax_number_part1,Stock.GetParameterValue("Fax part 1"));
		Web.clickOnElement(fax_number_part2);		
		Web.setTextToTextBox(fax_number_part2,Stock.GetParameterValue("Fax part 2"));
		Web.clickOnElement(fax_number_part3);
		Web.setTextToTextBox(fax_number_part3,Stock.GetParameterValue("Fax part 3"));
		Reporter.logEvent(Status.INFO,"Checking if the fax field is working","Fax field is accepting fax number", false);
		/*continue_button.click();
		Reporter.logEvent(Status.PASS,"We are now clicking continue to move onto Statement Order Confirmation Screen","Moved onto Statement Order Confirmation Screen", false);*/
	}
	
	
	//after moving onto confirmation page
	public void updating_delivery_information()
	{
		Web.waitForElement(statement_on_demand_order_checkbox);
		Web.clickOnElement(standard_mail_radio_button);
		Web.waitForElement(first_name);
		Web.setTextToTextBox(first_name,Stock.GetParameterValue("First Name"));
		Reporter.logEvent(Status.INFO,"Entering first name" ,"First name entered",false);
		Web.waitForElement(last_name);
		Web.setTextToTextBox(last_name,Stock.GetParameterValue("Last Name"));
		Reporter.logEvent(Status.INFO,"Entering last name","Last name entered", false);
		Web.waitForElement(first_line_mailing);
		Web.setTextToTextBox(first_line_mailing, Stock.GetParameterValue("First Line Mailing"));
		Reporter.logEvent(Status.INFO,"Entering first line mailing","First line mailing entered", false);
		Web.waitForElement(second_line_mailing);
		Web.setTextToTextBox(second_line_mailing, Stock.GetParameterValue("Second Line Mailing"));
		Reporter.logEvent(Status.INFO,"Entering second line mailing","Second line mailing entered",false);
		Web.waitForElement(city);
		Web.setTextToTextBox(city, Stock.GetParameterValue("City"));
		Reporter.logEvent(Status.INFO,"Entering city name","City name entered",false);
		Web.waitForElement(state_code);
		Web.setTextToTextBox(state_code, Stock.GetParameterValue("State Code"));
		Reporter.logEvent(Status.INFO,"Entering state code","State code entered",false);
		Web.waitForElement(zip_code);
		Web.setTextToTextBox(zip_code, Stock.GetParameterValue("Zip Code"));
		Reporter.logEvent(Status.INFO,"Entering zip code" ,"Zip code entered",false);
		Web.clickOnElement(continue_button);
		Reporter.logEvent(Status.INFO,"We are now clicking continue to move onto Statement Order Confirmation Screen","Moved onto Statement Order Confirmation Screen", false);	
	}
	//moving to confirmation page
	public void confirm_delivery_information()
	{
		Web.waitForElement(submit);
		Web.clickOnElement(submit);
	}
	public void return_confirmation_number()
	{
		try{
		Web.waitForElement(confirmation_number);
		if(confirmation_number != null ){
		String confir_number=confirmation_number.getText();
		System.out.println(confir_number);
		Reporter.logEvent(Status.PASS,"Checking for confirmation page in resultant page" ,"The confirmation number is:"+confir_number,false);
		}
		else{
			Reporter.logEvent(Status.FAIL,"Checking for confirmation page in resultant page" ,"The confirmation number is not displayed",true);
		}
		
		//return confir_number;
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	@Override
	protected void load() {
		
		this.parent= new LoanRequest().get();
            try{            	
            	Web.clickOnElement(participant_info);
            	Web.waitForElement(view_order_statement);
            	Web.clickOnElement(view_order_statement);
            	Web.waitForElement(fax_radio_button);
            }catch(Exception e)
            {
            	e.printStackTrace();
            }
            
            
        
	} 
	@Override
	protected void isLoaded() throws Error {
		
		// TODO Auto-generated method stub		
		Assert.assertTrue(Web.isWebElementDisplayed(statementPageTitle) && CommonLib.checkForPpt());
		Reporter.logEvent(Status.INFO,"Checking if Statement Update Page is loaded","Statement Update Page is loaded successfully" , false);
	}

	
	
}
