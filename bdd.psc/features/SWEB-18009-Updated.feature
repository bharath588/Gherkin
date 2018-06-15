# Author: Lydia Bauer, Connor Swatling, Silvio Nunes, Amir Abdullaev
# Keywords Summary :
# Feature: List of scenarios.
# Scenario: Business rule through list of steps with arguments.
# Given: Some precondition step
# When: Some key actions
# Then: To observe outcomes or validation
# And,But: To enumerate more Given,When,Then steps
# Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
# Examples: Container for s table
# Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Update Calendar Logic

Narrative: As a sponsor, I want my deferral requested dates to include holidays and weekends.

	Scenario: Verify that the requested date listed on the deferral review page is the same date the user entered when future dating.
	Given user enters a <future date> contribution for a <deferral type>.
	|accuCode|username|password|plan_no|deferral_type|future_date|
	|PlanEmpower|1INST|testing1|194193-01|Before Tax|current_date + 21|
	|PlanEmpower|1INST|testing1|194193-01|Roth|current_date + 2|
	When user clicks 'continue' on the deferral contribution screen.
	Then the effective date listed on the deferral review page is the <future date> the user entered.

	Scenario: Verify that the requested date listed on the deferral review page is the same date the user entered when not future dating.
	Given user enters a contribution for a <deferral type>.
	|accuCode|username|password|plan_no|deferral_type|
	|PlanEmpower|1INST|testing1|194193-01|Before Tax|
	|PlanEmpower|1INST|testing1|194193-01|Roth|
	When user clicks 'continue' on the deferral contribution screen.
	Then the effective date listed on the deferral review page is the current date.
	
	
	
	Scenario: Verify user can select a <weekend date> when scheduling a deferral contribution.
    Given plan is set up with <sdsv subcode> 
      | accuCode    | username | password | deferral_type |sdsv subcode|weekend date|
      | PlanEmpower | 1INST    | testing1 | Before Tax    |GENERIC|     
	  | PlanEmpower | 1INST    | testing1 | Before Tax    |ADJRUNDATE|
    And participant has an ongoing deferral.
    When user adds a scheduled automatic increase.
    Then the dropdown list should populate with list of dates including weekend dates.
    And user should be able to add a schedule.
	
	  Scenario: Verify user can select a <weekend date> when scheduling a deferral contribution.
    Given plan is not set up in GA_SERVICE table 
      | accuCode    | username | password | deferral_type |sdsv subcode|weekend date|
      | PlanEmpower | 1INST    | testing1 | Before Tax    |null|
	     And participant has an ongoing deferral.
    When user adds a scheduled automatic increase.
    Then the dropdown list should populate with list of dates including weekend dates.
    And user should be able to add a schedule.

	  Scenario: Verify user can select a <date> when scheduling a deferral contribution.
    Given plan is set up with <sdsv subcode>  
      | accuCode    | username | password | deferral_type |sdsv subcode      |date|
      | PlanEmpower | 1INST    | testing1 | Before Tax    |ADJRUN_PAYROLLDATE|    |
	     And participant has an ongoing deferral.
    When user adds a scheduled automatic increase.
    Then the dropdown list should populate with list of dates based on Payroll calendar setup
    And user should be able to add a schedule.
    
    
    
		
	Scenario: Verify that the requested date listed on the deferral review page is the next month date when user creates a 457 contribution.
	Given user enters a contribution for a <deferral type>.
	|accuCode|username|password|plan_no|deferral_type|
	|PlanEmpower|2PNP|testing1|98960-02|Before Tax|
	|PlanEmpower|2PNP|testing1|98960-02|Roth|
	When user clicks 'continue' on the deferral contribution screen.
	Then the effective date listed on the deferral review page is the next month date
	
