 #Author:
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
@SWEB-16745-Extended
Feature: File Sharing File Movement

Background: 
    Given user is on the File sharing page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 4HRJW    | testing1 |
    Then user is on filesharing page

 Scenario: verify "None" is displayed as default in Move File(s) modal box
 
 Given User at root-level folder
      | root folder |
      | Trustee     |
    And User has selected one or more checkboxes on a page
    When user clicks the "Move" button on the button Row
    And User clicks on the subfolder dropdown box
    Then The option "None" should be the default in the Subfolder Name dropdown
    And User has no subfolders in the previously selected root-level folder