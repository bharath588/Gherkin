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
@tag
Feature: InstAF - Rename Investment and Performance to Investment Results
  In the Plan Menu and breadcrumb, rename them to Investment Results for InstAF only.

  Background: 
    Given Open browser and launch PSC application
    When I enter valid username and password
      | username |  | password |
      | 1AF      |  | testing1 |
    And click on Sign On button
    Then I should be navigated to home Page

  Scenario: Rename Investment and Performance to Investment Results in Plan menu
    Given user is on AF bundled nextgen home page
    When user clicks on Plan menu
    Then Investment and Performance is renamed to Investment Results

  Scenario: Rename Investment and Performance to Investment Results in breadcrumb
    Given user is on AF bundled nextgen home page
    When user clicks on Investment Results under Plan menu
    Then Investment Results page is loaded
    And breadcrumb displays Investment Results
