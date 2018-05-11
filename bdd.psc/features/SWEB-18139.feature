#Author: rvpndy
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
@SWEB-18139
Feature: PSC - Employee overview page
  Narrative Description : As PSC product owner: correct Production issue so that in Employee overview page while toggling between plans, page will display details from the current plan

  Scenario Outline: Verify data under <labels> reflects data for the plan displayed in header on employee overview page
    Given user is on Employee Search page of "<accuCode>" when login with correct "<username>" and "<password>"
    When user selects search employee by "SSN - all plans"
    And enters "<ssn>"
    And hits Go to search
    And clicks on last name link
    And clicks on View button for a "<plannumber>"
    And selects Return to employee overview
    Then data under "<labels>" reflects data for the plan displayed in header on employee overview page

    Examples: 
      | accuCode    | username | password | plannumber | ssn       | labels                                      |
      | PlanEmpower | 1isis    | testing1 | 931372-01  | 211111122 | Account detail, Employee detail, Statements |
      | PlanEmpower | 1isis    | testing1 | 1114009-01 | 211111122 | Account detail, Employee detail, Statements |
