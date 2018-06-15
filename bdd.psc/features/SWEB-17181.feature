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
@SWEB-17181
Feature: Password changes - Update FAQ

  Scenario Outline: Verify the FAQ link includes the correct content in Pre-Login page
    Given User successfully navigated to "<accuCode>"
    When clicks on the FAQ link on login page
    Then the FAQ content should be displayed
    And the following text should be displayed in FAQ content
      """
      How many characters must my password contain?
      Passwords must be between eight and 64 characters in length and contain at least three of the following character sets: lowercase letters, uppercase letters, numbers and/or special characters @ $ ! # % * ? & + . _ - =
      """

    Examples: 
      | accuCode |
      | PlanMiss |
      | PlanDMBA |
      | PlanMN   |
      | PlanApple|