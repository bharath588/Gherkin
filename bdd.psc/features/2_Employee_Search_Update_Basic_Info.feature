#Author: Ravi Shankar Pandey
#Keywords Summary : SSN
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
@EmployeeSearchAndUpdateInfo
Feature: Employee Search and Update Basic Info

  Scenario: Navigation to Employee Search
    Given I am on Home Page
    When I click on Employee menu
    Then I should be navigated to Employee Search page

  Scenario: Employee Search
    Given I am on Employee Search Page
    When I select SSN from search filter drop down down
    And I enter correct SSN number in search text box
      | SSN       |
      | 135785475 |
    And I click on Go button
    Then Employee Search result displays

  Scenario: Navigation to employee overview page
    Given I am on employee search result page
    When I click on employee name link
    Then Employee overview page opens

  Scenario: Update employee info and verify
    Given I am on Employee overview page
    When I click on Employee detail tab
    And I click on Edit button of Basic information
    And I update the last name
    And I update the first name
    And I update the middle name
    And I click on Save button
    Then Updated information is saved
    And I can verify the updated information in contact information section

  Scenario: Close error box
    Given I am on error page
    When I close the error page
    Then I am on Employee Detail page
