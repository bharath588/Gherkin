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

@SWEB-16996
Feature: InstMetLife - In the Plan /Contacts menu, ensure that we do not have an "Empower Contacts" menu item
  Narrative Description : As a MetLife client I want In the Plan /Contacts menu, to ensure that we do not have an "Empower Contacts" menu item

  Scenario: Verify the Empower Contacts are not displayed under Contacts Menu.
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | password   |  
      | InstMetLife | 1IML     | testing1   |          
    When user selects Plan /Contacts menu
    Then No "Empower Contacts" menu item is displayed
