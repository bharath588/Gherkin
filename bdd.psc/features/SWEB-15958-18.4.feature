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
@SWEB-15958-18.4
Feature: On the Loan details page, the "Total Payment applied" column should be suppressed for internal and external loans

  Scenario Outline: Verify "Total Payment applied" column is suppressed for "<loan_type>" loans
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects plan "<plan no>"
    And User is on Employee Search Page
    When user search for an employee using "Participant ID" having loan_type loans with "<IND ID>"
    And clicks on the search result to open employee detail page
    And clicks on View button on Loan Section
    And Loan details page is displayed
    Then the Total Payment applied column should be suppressed

    Examples: 
      | accuCode    | username | password | plan_no   | loan_type      | IND ID  |
      | PlanEmpower | 1ISIS    | testing1 | 331156-01 | internal loans | 6340303 |
      | PlanEmpower | 1ISIS    | testing1 | 930126-01 | external loans | 1640201 |
