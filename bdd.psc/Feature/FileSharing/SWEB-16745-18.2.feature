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
@SWEB-16745
Feature: File Sharing File Movement

  Background: 
    Given user is on the File sharing page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 1ISIS    | testing1 |
    #When user clicks on filesharing tab
    Then user is on filesharing page
    
  
   
      
    Scenario: verify appropriate checkboxes are selected
    Given the user has files within a folder
    When User "select" the header row checkbox
    Then All checkboxes on screen should be "selected"
    And The checkboxes on other page should be "deselected"

  Scenario: verify appropriate checkboxes are deselected
    Given The header row checkbox is "selected"
    And All checkboxes on the screen should be "selected"
    When User "deselect" the header row checkbox
    Then All checkboxes on screen should be "deselected"

  Scenario: verify button row visibility
    Given the user has files within a folder
    When User selects a checkbox that is NOT the header row checkbox
    Then The Button Row becomes "visible"

  Scenario: verify selected checkboxes cleared after leaving page
    Given User has selected one or more checkboxes on a page
    When User navigates to another page
    Then Selected checkboxes should be "clear"
    And The Button Row becomes "invisible"

  Scenario: verify button row invisibility
    Given User has selected one or more checkboxes on a page
    When User unselects all selected checkboxes
    Then The Button Row becomes "invisible"

  Scenario: verify header row checkbox selection
    Given the user has files within a folder
    When All row-level checkboxes are selected
    Then The header-row checkbox should be "selected"

  Scenario: verify header row checkbox deselection
    Given the user has files within a folder
    When At least one row-level checkbox is not selected
    Then The header-row checkbox should be "deselected"

  Scenario Outline: verify "<Modal Box>" appears when user clicks on "<button>"
    Given The Button Row is "visible"
    When User clicks a "<button>"
    Then The corresponding "<Modal Box>" should appear

    Examples: 
      | button   | Modal Box        |
      | Delete   | Delete File(s)   |
      | Move     | Move File(s)     |
      | Download | Download File(s) |

  Scenario Outline: verify user returns to previously selected folder from "<Modal Box>"
    Given user has opened a "<Modal Box>"
    When User cancels actions on the "<Modal Box>"
    Then User returns to the previously selected folder
    And Previously selected checkboxes should remain checked
    And Button row should be "visible"

    Examples: 
      | Modal Box        |
      | Delete File(s)   |
      | Move File(s)     |
      | Download File(s) | 
      
   Scenario Outline: verify user returns to previously selected folder
    Given user has opened a "<Modal Box>"
    When User closes the "<Modal Box>"
    Then User returns to the previously selected folder
    And Previously selected checkboxes should remain checked
    And Button row should be "visible"

    Examples: 
      | Modal Box        |
      | Delete File(s)   |
      | Move File(s)     |
      | Download File(s) |

  Scenario: verify selected folder is default in Move File(s) modal box
    Given User at root-level folder
      | root folder |
      | Auditor     |
    And User has selected one or more checkboxes on a page
    When user clicks the "Move" button on the button Row
    Then User's root folders should populate in Parent Folder Name dropdown 
    
   Scenario: verify selected folder is default in Move File(s) modal box
    Given User at sub-level folder
      | sub folder |
      | Abc        |
    And User has selected one or more checkboxes on a page
    When user clicks the "Move" button on the button Row
    Then User's root folders should populate in Parent Folder Name dropdown
  
Scenario: verify "None" is displayed as default in Move File(s) modal box
    Given User at root-level folder
      | root folder |
      | Auditor     |
    And User has selected one or more checkboxes on a page
    When user clicks the "Move" button on the button Row
    And User clicks on the subfolder dropdown box
    Then The option "None" should be the default in the Subfolder Name dropdown
    And User's subfolders should populate in the Subfolder Name dropdown 
  

  Scenario Outline: verify Move File(s) Button Label becomes enabled for files moved from parent or sub folders to other parent or sub folders
    Given user has to select files to move from "<FromFolderName>" with "<FromFolderValue>"
    And User has selected one or more checkboxes on a page
    And clicked on "Move" button
    When User selects a "<ToFolderName>" folder with the "<ToFolderValue>" in dropdown box
    And User checks the Confirmation checkbox on the Move Files Modal Box
    But User does not select the "<FromFolderValue>" in "<ToFolderName>" folder
    Then The Move Files Button Label becomes "enabled"

    Examples: 
      | FromFolderValue | ToFolderValue |FromFolderName| ToFolderName|
      | Auditor         | Compliance    |parent        |parent       |
      | Auditor         | Abc           |parent        |sub          |
      | Abc             | Def           |sub           |sub          |
      | Abc             | Compliance    |sub           |parent       | 
   
  Scenario Outline: verify Move File(s) Button Label becomes disabled when user deselects Confirmation checkbox
    Given user has made a change to the "<folderName>" folder name dropdown box with "<values>"
    When User deselects the Confirmation checkbox on the Move Files Modal Box
    Then The Move Files Button Label becomes "disabled"
    
     Examples:
    |folderName|values     | 
    |parent    |Conversions| 
    |sub       |Newfolder  |




  Scenario Outline: verify Move File(s) Button Label becomes disabled when user reselects old folders
    Given user has selected the Confirmation checkbox
    When User selects either the default "<folderName>" folder in the destination dropbox with "<values>"
    Then The Move Files Button Label becomes "disabled"
    Examples:
    |folderName|values     | 
    |parent    |Compliance | 
    |sub       |--None--   | 



  Scenario: verify file count updates in Move File(s) modal box
    Given user has selected files
      |parentOrSubFolder| root folder|
      |parent           | Auditor    |
    And User has selected one or more checkboxes on a page
    When user clicks the "Move" button on the button Row
    Then the count of selected files should match the count that appears in the Move Files modal box

  Scenario: verify files moved from one folder to another succesfully
    Given user has selected files
     |parentOrSubFolder| root folder|
     |parent           | Auditor    |
    And User has selected one or more checkboxes on a page
    When user clicks the "Move" button on the button Row
    And User clicks Move Files on the modal box
    #Then User is navigated to the newly selected folder
    #And Previously selected files should no longer appear in former folder
    #And Previously selected files should appear in newly selected folder
    And Button row should be "invisible"

  