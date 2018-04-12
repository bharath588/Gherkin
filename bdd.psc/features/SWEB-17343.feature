#Author: mchmdn
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
@SWEB-17343
Feature: Reports Catalog Link - available on  Standard Reports page in PSC
  Narrative Description : As a PSC user I want a Reports Catalog Link available on Standard Reports page in PSC

  Scenario: Reports Catalog Link is available on Standard Reports page in PSC-Empower
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username |  | password |
      | PlanEmpower | 1isis    |  | testing1 |
    When user clicks on "Standard reports" under Reports tab
    Then the Reports catalog link is displayed next to the Search entry text box

  Scenario: Reports Catalog Link links to reports catalog pdf file of Reports
    Given user is on the "Standard reports" page
    When user clicks Reports Catalog Link
    Then link downloads the Reports Catalog excel file
