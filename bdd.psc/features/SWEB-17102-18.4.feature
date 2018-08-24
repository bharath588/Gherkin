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
@SWEB-17102
Feature: InstMetLife - the Account verification flow include three security questions
  Narrative Description : As a MetLife client I want to be presented three security questions during Account verification for first time login

  Scenario: Verify first time login presents three security questions during Account verification
    Given is a first time login to the site
    And user is on the Account Verification page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | InstMetLife | 2FIIN    | testing1 |    
    Then three security questions are presented during Account verification

  Scenario: Verify first time login answers three security questions during Account verification to proceed
    Given user answers three security questions
    Then three security questions are saved and flow proceeds to post-login page
