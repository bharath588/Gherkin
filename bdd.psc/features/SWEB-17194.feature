#Author: Remya
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
@SWEB-17194
Feature: Prospectus links on pre-login footer section

  Scenario Outline: Verify the Prospectus links includes the correct URL
    Given user is on the Login page of "<accuCode>"
    When user scroll down to footer section
    And clicks on the Fund Prospectuses link on login page
    Then the Fund Prospectuses content should be displayed
    And breadcrumb should display Home / Fund prospectuses
    And the updated "<links>" should be displayed

    Examples: 
      | accuCode    | links                                      |
      | PlanEmpower | https://prospectus-express.broadridge.com/ |
      | PlanDMBA    | https://prospectus-express.broadridge.com/ |
      | PlanMN      | https://prospectus-express.broadridge.com/ |
      | PlanMiss    | https://prospectus-express.broadridge.com/ |
      | PlanJPM     | https://prospectus-express.broadridge.com/ |
