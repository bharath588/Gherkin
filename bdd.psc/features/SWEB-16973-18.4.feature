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
@SWEB-16973
Feature: InstMetLife - Pre-login static page 
Narrative Description : As a MetLife client I want the Pre-login static page to be displayed with correct content

  Scenario: Verify the Pre-login static page is displayed with correct content in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode     |
      | InstMetLife |
    When user clicks on Pre-login banner
    Then a page like this link is displayed "https://plan.empower-retirement.com/static/PlanEmpower/sponsor-plananalytics.html" and is branded for MetLife
   