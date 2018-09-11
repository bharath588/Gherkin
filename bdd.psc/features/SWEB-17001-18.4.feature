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
@SWEB-17001
Feature: InstMetLife - the Partnerlink landing page is not branded as Empower
  Narrative Description : As a MetLife client I want the Partnerlink landing page is not branded as Empower

  Scenario: Verify the Partnerlink landing page is not branded as Empower
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | InstMetLife | 2IML     | testing1 |
    Then the Partnerlink landing page is not branded as Empower
