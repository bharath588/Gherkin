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
@SWEB-17184
Feature: InstMetLife - the Pre login FAQ content contains the current approved verbiage
  Narrative Description : As a MetLife client I want the Pre login FAQ content contain the current approved verbiage

  Scenario Outline: Verify the Pre login FAQ content contains the current approved verbiage
    Given User successfully navigated to "<accuCode>"
    When clicks on the FAQ link on login page
    Then the FAQ content should be displayed
    And the following text should be displayed in FAQ content
      """
      How many characters must my password contain?
      Passwords must be between eight and 64 characters in length and contain at least three of the following character sets: lowercase letters, uppercase letters, numbers and/or special characters @ $ ! # % * ? & + . _ - =
      """
    Examples: 
      | accuCode    |
      | InstMetLife |

Scenario Outline: Return to Home page from Pre login FAQ page
    Given User successfully navigated to "<accuCode>"
    When clicks on the FAQ link on login page
    And user clicks on Home link on top nav
    Then is returned to Plan Service Center Pre login page
     Examples: 
      | accuCode    |
      | InstMetLife |
  Scenario: Return to Home page from Pre login FAQ page
    Given user has clicked FAQ link from Pre login page
    When the Pre login FAQ content is presented
    And user clicks on Home link on top nav
    Then is returned to Plan Service Center Pre login page
