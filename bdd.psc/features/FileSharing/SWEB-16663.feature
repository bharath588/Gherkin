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
      | PlanEmpower | 1INSPS   | testing1 | 
   #   And User has selected a plan
   #   | plan      | 
   #   | 194391-01 | 
     #And User has access to File Sharing
     When User navigates to the File Sharing tab
     Then User should see the "Manage folder notifications" link
  
 # Scenario: Verify when the Plus Button is present
 #   Given User has access to a folder with subfolders
 #    When The User opens the Notifications Manager
  #   Then There should be a Plus Button next to the folder
     
 #  Scenario: Verify when the Plus Button converts to a Minus Button
#    Given User has access to a folder with subfolders
 #    And User had opened the Notifications Manager
 #   When The User clicks on the Plus Button next to the folder
 #   Then The Plus Button should convert into a Minus Button
 #    And the Nested Subfolders section for that folder should expand
 #    And other Nested Subfolder sections should minimize
 #    And other Minus Buttons should convert into Plus Buttons
      
 # Scenario: Verify when the Minus Button converts to a Plus Button
 #   Given User has access to a folder with subfolders
 #     And there is a Minus Button on the Notification Manager
 #    When The User clicks on the Minus Button
 #    Then The Minus Button should convert into a Plus Button
 #     And the Nested Subfolders section for that folder should minimize
      
 # Scenario: Verify changes to preferences in the Notification Manager are preserved
 #   Given User has made a change to their subfolder notification preferences
 #     And User has minimized the Nested Subfolders
 #    When User reopens the Nested Subfolders
 #    Then User's changes should be preserved
     
 # Scenario: Verify changes to preferences in the Notifications Manager are NOT preserved when User exits
 #   Given User has made a change to their subfolder notification preferences
 #    When User closes the Notifications Manager without clicking 'Update'
 #    Then User's changes should be NOT be preserved
     
    Scenario: Verify all subfolders within a folder are selected when User selects 'Select all subfolders'
    Given User has expanded a folder's nested subfolders
     When User selects the 'Select all subfolders' option
     Then All subfolder options for that folder should be selected
     
    Scenario: Verify all subfolders within a folder are deselected when User deselects 'Select all subfolders'	
    Given User has expanded a folder's nested subfolders
     When User deselects the 'Select all subfolders' option
     Then All subfolder options for that folder should be deselected
     
     
   Scenario: Verify 'Select all subfolders' is deselected when at least one subfolder is not selected
    Given User has expanded a folder's nested subfolders	
      And User has selected 'Select all subfolders'
     When User deselects a selected subfolder
     Then The 'Select all subfolders' option should be deselected
     
   Scenario: Verify 'Select all subfolders' is selected when all of the subfolders are selected
    Given User has expanded a folder's nested subfolders
     When User manually selects all subfolders
      But does not select 'Select all subfolders'
     Then The 'Select all subfolders' option should be selected
     
    Scenario: Verify 'Select all' button selects all subfolders and folders
    Given User has access to folders and subfolders in the Notifications Manager
     When User clicks 'Select all' button
     Then All folders and subfolders within the Notifications Manager are selected