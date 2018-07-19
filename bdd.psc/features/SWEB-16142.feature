
#Author: your.email@your.domain.com
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
@SWEB-16142
Feature: Plan MN NEE custom site

  Scenario: User login to MN site with valid credentials and user has transaction PSCSF2 assigned to their access
   #Given Open browser and launch PSC application
   Given user is on the Login page of "PlanMN"
    When I enter valid username and password
      | username | password |
      | 2B9DE    | testing1 |
    And click on Sign On button
    Then GWC connect page is displayed

  Scenario: username is displayed on the top right hand corner of GWC connect page
    Given I am on GWC connect page
    Then username is displayed on the top right hand corner of the site

  Scenario: User login to MN site with valid credentials and with NO transaction code PSCSF2
    #Given Open browser and launch PSC application
    Given user is on the Login page of "PlanMN"
    When I enter valid username and password
      | username | password |
      | 1MN      | testing1 |
    And click on Sign On button
    Then I should be navigated to home Page

  Scenario: User login to MN site with invalid password
    #Given Open browser and launch PSC application
    Given user is on the Login page of "PlanMN"
    When I enter valid username and invalid password
      | username | password |
      | 1mn      | testing  |
    And click on Sign On button
    Then Error message is displayed

  Scenario: User login to MN site with invalid username
    #Given Open browser and launch PSC application
    Given user is on the Login page of "PlanMN"
    When I enter invalid username and invalid password
      | username | password |
      | zminn    | zzzzzzzz |
    And click on Sign On button
    Then Error message is displayed

