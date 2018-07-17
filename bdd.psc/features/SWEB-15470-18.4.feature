#Author:
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
@SWEB-15470
Feature: Vesting Block for participants with a null balance

  Scenario Outline: 
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects the plan "<plan_no>" with Vesting Service level as "<Vesting Level>" in Plan Provisions page
    Then the Home page should be displayed
    ## Vesting historical data should be displayed for employees with no balance
    When User is landing on Employee Search Page
    And User search employee by "Participant ID"  and select an employee with "<Account Balance Null>"
    Then employee detail page is displayed
    When user clicks on Vesting link
    Then a new window opens with Vesting historical data
    ## Verify verbiage change on the vesting block for participants with a null balance
    When User is landing on Employee Search Page
    And User search employee by "Participant ID"  and select an employee with "<Account Balance Null>"
    Then employee detail page is displayed
    And information displayed on Vesting section should be "Vesting balance information is not available."
    ## Vesting data should be displayed for employees with balance
    When User is landing on Employee Search Page
    And User search employee by "Participant ID"  and select an employee with "<with Account Balance>"
    Then employee detail page is displayed
    And Vesting information should be available under Vesting section
    ## Vesting historical data should be displayed for employees with balance
    When User is landing on Employee Search Page
    And User search employee by "Participant ID"  and select an employee with "<with Account Balance>"
    Then employee detail page is displayed
    When user clicks on Vesting link
    Then a new window opens with Vesting historical data

    Examples: 
      | accuCode    | username | password | plan_no    | Vesting Level | Account Balance Null | with Account Balance |
      | PlanEmpower | 1ISIS    | testing1 | 331156-01  | Full Services |             12304061 |              6340280 |
      | PlanEmpower | 1ISIS    | testing1 | 1260013-01 | Informational |              9774987 |              2276869 |

  Scenario Outline: 
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects the plan "<plan_no>" with Vesting Service level as "<Vesting Level>" in Plan Provisions page
    Then the Home page should be displayed
    When User is landing on Employee Search Page
    And User search employee by "Participant ID"  and select an employee with "<Account Balance Null>"
    And employee detail page is displayed
    Then Vesting section should be suppressed
    When User search employee by "Participant ID"  and select an employee with "<with Account Balance>"
    And employee detail page is displayed
    Then Vesting section should be suppressed

    Examples: 
      | accuCode    | username | password | plan_no   | Vesting Level | Account Balance Null | with Account Balance |
      | PlanEmpower | 1ISIS    | testing1 | 932777-01 | NONE          |              8440405 |             11987953 |
      | PlanEmpower | 1ISIS    | testing1 | 336300-01 | BENE          |             12309313 |             10630117 |
