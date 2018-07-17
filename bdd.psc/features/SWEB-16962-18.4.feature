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
@SWEB-16962-18.4
Feature: InstMetLife - Display MetLife logo approved by client
Narrative Description : As a MetLife client I want to Display on pre and post login pages the MetLife logo approved by client

 	Scenario: Verify the Pre login page will Display MetLife logo approved by client 
    Given user is on the Login page of accuCode
      | accuCode     |
      | InstMetLife |
    When MetLife logo is displayed
    Then the logo is as approved by client, has Max-width of 300 pixels, Max-height of 70 pixels
	
	Scenario: Verify the Post login page will Display MetLife logo approved by client 
    Given user is on the Login page of accuCode
      | accuCode     |
      | InstMetLife |
     When user enters username and password
      | username |  | password |
      | 1IML     |  | testing1 |
	  And MetLife logo is displayed
    Then the logo is as approved by client, has Max-width of 300 pixels, Max-height of 70 pixels
	
	
	