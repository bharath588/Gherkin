#Author: Ravi Shankar Pandey
#Keywords Summary :username, password, email, securityAnswer
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
@Login
Feature: User login

  Scenario: user login with correct credentials
    Given Open browser and launch PSC application
    When I enter valid username and password
      | username | password |
      | 1ISIS    | testing1 |
    And click on Sign On button
    Then I should be navigated to user verification page

  Scenario: User Verification flow
    Given I am on user verification page
    When I enter email address in email box
      | email           |
      | discard@gwl.com |
    And I enter security question answer
      | securityAnswer |
      | test           |
    And I click on Next button
    Then I should be naviagted to jump page

  Scenario: Site selection from jump page
    Given I am on jump page
    When I click on nextGen page link
    Then I should be navigated to home Page
