package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;

public class MoneyTypeBalance extends LoadableComponent<MoneyTypeBalance>{

//Money Type Balance..(MTB)	
	@FindBy(css = "table[id = 'table_Money Type Balance'] td.pageMenuTitle")
	private WebElement MTBPageTitle ;
	
	@FindBy(css = "td input[name = 'date']")
	private WebElement MTBAsOfDate ;
	
	@FindBy(css = "select[name = 'planNumber']>option")
	private WebElement MTBForPlan ;
//Active Deposite Allocation(ADA)..
	
	@FindBy(xpath = "//span[contains(text(),'Active Deposit Allocation Information')]")
	private WebElement ADAInfoTitle ;
	
	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(1) td.dataStringColumnTitle")
	private WebElement ADAAssetModelInvstOpt ;
	
	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(1) td.dataNumberColumnTitle")
	private WebElement ADAAllocationPer ;

	//Total 'ld be 100%..
	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(1) td.dataNumberColumn")
	private WebElement ADAAllocationPerVal ;
	
//Variable Investment(VI)..	
	@FindBy(xpath = "//span[contains(text(),'Variable Investments')]")
	private WebElement VITitle ;
	
	@FindBy(css = "table#variableFunds th:nth-of-type(1)")
	private WebElement VIAssetModelInvstOpt ;

	@FindBy(css = "table#variableFunds th:nth-of-type(2)")
	private WebElement VIMoneyType ;

	@FindBy(css = "table#variableFunds th:nth-of-type(3)")
	private WebElement VIValueEffDate ;

	@FindBy(css = "table#variableFunds th:nth-of-type(4)")
	private WebElement VIUnitShareVal ;

	@FindBy(css = "table#variableFunds th:nth-of-type(5)")
	private WebElement VIUnitShareBal ;

	@FindBy(css = "table#variableFunds th:nth-of-type(6)")
	private WebElement VIVestingPercentage ;

	@FindBy(css = "table#variableFunds th:nth-of-type(7)")
	private WebElement VIVestedBal ;

	@FindBy(css = "table#variableFunds th:nth-of-type(8)")
	private WebElement VINonVestedBal ;

	@FindBy(css = "table#variableFunds th:nth-of-type(9)")
	private WebElement VICurrentBal ;
	
//Fixed Investment(FI)..
	@FindBy(xpath = "//span[contains(text(),'Fixed Investments')]")
	private WebElement FITitle ;
	
//Investment Total(IT)..
	@FindBy(xpath = "//span[contains(text(),'Investment Totals')]")
	private WebElement ITTitle ;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr>td[class = 'colTitle']:nth-of-type(3)")
	private WebElement ITVestedBalLable ;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr>td[class = 'colTitle']:nth-of-type(4)")
	private WebElement ITNonVestedBalLable ;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr>td[class = 'colTitle']:nth-of-type(5)")
	private WebElement ITCurrentBalLable ;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr:nth-of-type(2)>td[align = 'right']:nth-of-type(3)")
	private WebElement ITVestedBalVal ;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr:nth-of-type(2)>td[align = 'right']:nth-of-type(4)")
	private WebElement ITNonVestedBalVal ;

	@FindBy(css = "tbody>tr:nth-child(2)>td>table:nth-child(2)>tbody>tr:nth-child(5) tr:nth-of-type(2)>td[align = 'right']:nth-of-type(5)")
	private WebElement ITCurrentBalVal ;

//Secure Foundation Benefits Base Details..

	@FindBy(xpath = "//span[contains(text(),'SecureFoundation Benefit Base Details')]")
	private WebElement SecureFndtnBnftBsDtlsTitle ;
	
//Money Type Total(MTT)..
	
	@FindBy(xpath = "//span[contains(text(),'Money Type Totals')]")
	private WebElement MoneyTypeTotalTitle ;
	
	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(1)")
	private WebElement MTTMoneyType ;

	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(2)")
	private WebElement MTTCurrentBal ;
	
	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(3)")
	private WebElement MTTLoanPrinciapl ;
	
	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(4)")
	private WebElement MTTPriorWithdrawls ;
	
	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(5)")
	private WebElement MTTVestingPer ;
	
	@FindBy(css = "table#acctBalMntySummary th:nth-of-type(6)")
	private WebElement MTTVestedBal ;

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
