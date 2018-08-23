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
@SWEB-16663
Feature: Subfolders in Notification Manager

  Background: User has access to the Notifications Manager
    Given user is on the File sharing page of accuCode when user login with correct username and password
      | accuCode    | username | password | 
      | PlanEmpower | 1INST    | testing1 | 
      And User has selected a plan
      | plan      | 
      | 194391-01 | 
     #And User has access to File Sharing
     When User navigates to the File Sharing tab
     Then User should see the "Manage folder notifications" link
  
  Scenario: Verify when the Plus Button is present
    Given User has access to a folder with subfolders
     When The User opens the Notifications Manager
     Then There should be a Plus Button next to the folder
     
   Scenario: Verify when the Plus Button converts to a Minus Button
    Given User has access to a folder with subfolders
      And User had opened the Notifications Manager
     When The User clicks on the Plus Button next to the folder
     Then The Plus Button should convert into a Minus Button
      And the Nested Subfolders section for that folder should expand
      And other Nested Subfolder sections should minimize
      And other Minus Buttons should convert into Plus Buttons