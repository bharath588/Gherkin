#Author: Matt and Remya
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@SWEB-16843
Feature: Plan - Plan Provisions - Loans Add Hardship values ( LoansRequiredPriorToHardship & RestrictionstoHardshipReasons )

  Scenario Outline: Verify the hardship fields are displayed for non-PartGroup plans
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And user changes to "<non-PartGroup>" plan
    Then "<non-PartGroup>" plan home page is displayed
    When user clicks Plan, Plan Provisions
    Then the Plan Provision page is displayed
    When user clicks Loans button
    Then Loan provision page is displayed
    And The "<LoansRequiredPriorToHardship>" value is displayed on header
    And The "<RestrictionstoHardshipReasons>" value is displayed

    Examples: 
      | accuCode    | username | password | non-PartGroup | LoansRequiredPriorToHardship | RestrictionstoHardshipReasons |
      | PlanEmpower | 1INST    | testing1 | 1210480-01    | N                            | N                             |

  Scenario Outline: Verify the hardship fields are displayed for PartGroup plans
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And user changes to "<PartGroup>" plan
    Then "<PartGroup>" plan home page is displayed
    When user clicks Plan, Plan Provisions
    Then the Plan Provision page is displayed
    When user clicks Loans button
    Then Loan provision page is displayed
    And The "<LoansRequiredPriorToHardship>" value is displayed on header
    And The "<RestrictionstoHardshipReasons>" value is displayed

    Examples: 
      | accuCode    | username | password | PartGroup     | LoansRequiredPriorToHardship | RestrictionstoHardshipReasons |
      | PlanEmpower | 1ISIS    | testing1 | 1114020-01    | Y                            | N                             |
