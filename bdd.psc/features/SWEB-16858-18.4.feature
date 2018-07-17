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
@SWEB-16858
Feature: Common Remitter plan suppression of elements

Scenario Outline: Verify the Plan Analytics section is suppressed for Common Remitter plans
Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user changes to "<CMREMIT>" plan
    Then "<CMREMIT>" plan home page is displayed
    And Plan Analytics are suppressed
    Examples: 
      | accuCode    | username | password | CMREMIT   |
      | PlanEmpower | 1INST    | testing1 | 194179-01 |


