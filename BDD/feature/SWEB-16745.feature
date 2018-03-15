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
Feature: File Sharing File Movement

  Background: 
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | Password |
      | PlanEmpower | 1ISIS    | testing1 |
    When user clicks on filesharing tab
    Then user is on filesharing page

  Scenario: verify appropriate checkboxes are selected
    Given the user has files within a folder
    When User selects the header row checkbox
    Then All checkboxes on that page should be selected
    And The checkboxes on other page(s) should NOT be selected

  Scenario: verify appropriate checkboxes are deselected
    Given The header row checkbox is selected
    And All checkboxes on the page are selected
    When User deselects the header row checkbox
    Then All checkboxes on screen should be deselected

  Scenario: verify button row visibility
    Given the user has files within a folder
    When User selects a checkbox that is NOT the header row checkbox
    Then The Button Row becomes visible

  Scenario: verify selected checkboxes cleared after leaving page
    Given User has selected one or more checkboxes on a page
    When User navigates to another page
    Then Selected checkboxes should clear
    And The Button Row should no longer be visible

  Scenario: verify button row invisibility
    Given User has selected one or more checkboxes on a page
    When User unselects all selected checkboxes
    Then The Button Row should no longer be visible

  Scenario: verify header row checkbox selection
    Given the user has files within a folder
    When All row-level checkboxes are selected
    Then The header-row checkbox should be selected

  Scenario: verify header row checkbox deselection
    Given the user has files within a folder
    When At least one row-level checkbox is not selected
    Then The header-row checkbox should not be selected

  Scenario Outline: verify "<Modal Box>" appears when user clicks on "<button>"
    Given The Button Row is visible
    When User clicks a "<button>"
    Then The corresponding "<Modal Box>" should appear

    Examples: 
      | button   | Modal Box        |
      | Delete   | Delete File(s)   |
      | Move     | Move File(s)     |
      | Download | Download File(s) |

  Scenario: verify user returns to previously selected folder
    Given user has opened a Modal Box
    When User cancels actions on the modal box
    Then User returns to the previously selected folder
    And Previously selected checkboxes should remain checked
    And Button row should be visible

  Scenario: verify user returns to previously selected folder
    Given user has opened a Modal Box
    When User closes the modal box
    Then User returns to the previously selected folder
    And Previously selected checkboxes should remain checked
    And Button row should be visible

  Scenario: verify selected folder is default in Move File(s) modal box
    Given User was previously in a root-level folder
    When User clicks on the root folder dropdown box in the Move File(s) modal box
    Then The previously selected root folder should be the default
    And User's root folders should populate in Parent Folder Name dropdown

  Scenario: verify selected folder is default in Move File(s) modal box
    Given User was previously in a subfolder
    When User clicks on the root folder dropdown box in the Move File(s) modal box
    Then The subfolder's parent folder should be the default
    And User's root folders should populate in Parent Folder Name dropdown

  Scenario: verify "None" is displayed as default in Move File(s) modal box
    Given User opened Move File(s) modal box from a root-level folder
    When User clicks on the subfolder dropdown box
    Then The option "None" should be the default in the Subfolder Name dropdown
    And User's subfolders should populate in the Subfolder Name dropdown

  Scenario: verify "None" is displayed as default in Move File(s) modal box
    Given User opened Move File(s) modal box from a root-level folder
    And User has no subfolders in the previously selected root-level folder
    When User clicks on the subfolder dropdown box
    Then The option "None" should be the only option in the Subfolder Name dropdown

  Scenario: verify Move File(s) Button Label becomes enabled for files moved from parent folders to other parent folders
    Given user has selected files to move from a parent folder
    And clicked on the "Move" button
    When User selects a folder in the Parent Folder Name dropdown box
    And User checks the Confirmation checkbox on the Move File(s) Modal Box
    But User does not select the same folder
    Then The Move File(s) Button Label becomes enabled

  Scenario: verify Move File(s) Button Label becomes enabled for files moved from parent folders or subfolders to other subfolders
    Given user has selected files to move
    And clicked on the "Move" button
    When User selects a subfolder in the Subfolder Name dropdown box
    And User checks the Confirmation checkbox on the Move File(s) Modal Box
    But User does not select the same subfolder
    Then The Move File(s) Button Label becomes enabled

  Scenario: verify Move File(s) Button Label becomes enabled for files moved from parent folders or subfolders to other parent folders or subfolder
    Given user has selected files to move
    And clicked on the "Move" button
    When User selects a folder in the Parent Folder Name dropdown box
    And User selects a subfolder in the Subfolder Name dropdown box
    And User checks the Confirmation checkbox on the Move File(s) Modal Box
    But User does not select the same folder
    Then The Move File(s) Button Label becomes enabled

  Scenario: verify Move File(s) Button Label becomes disabled when user deselects Confirmation checkbox
    Given user has made a change to the parent folder name or subfolder name dropdown box
    When User deselects the Confirmation checkbox on the Move File(s) Modal Box
    Then Move File(s) Button Label should be disabled

  Scenario: verify Move File(s) Button Label becomes disabled when user reselects old folders
    Given user has selected the Confirmation checkbox
    When User selects either the default parent folder or subfolder in the destination dropbox
    Then Move File(s) Button Label should be disabled

  Scenario: verify file count updates in Move File(s) modal box
    Given user has selected files
    When user clicks the "Move" button on the Button Row
    Then the count of selected files should match the count that appears in the Move File(s) modal box

  Scenario: verify files moved from one folder to another succesfully
    Given user has selected files
    And user has clicked "Move" in the Button Row
    When User clicks "Move File(s) on the modal box
    Then User is navigated to the newly selected folder
    And Previously selected files should no longer appear in former folder
    And Previously selected files should appear in newly selected folder
    And Button row should NOT be visible
