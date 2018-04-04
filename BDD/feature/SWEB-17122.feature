#Author: Bindu Subbireddy, Connor Swatling, Silvio Nunes, Marty Rodriguez
# Keywords Summary :
# Feature: List of scenarios.
# Scenario: Business rule through list of steps with arguments.
# Given: Some precondition step
# When: Some key actions
# Then: To observe outcomes or validation
# And,But: To enumerate more Given,When,Then steps
# Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
# Examples: Container for s table
# Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@SWEB-17122
Feature: My Reports Subfolders Sharing
  Assigned Developer: Susmitha
  Assigned Shadower: Marty

  Background: Report Sharing modal box is open
    Given User has a shareable report in My Reports
    When User clicks the 'Share' or 'Reshare' link
    Then Report Sharing modal box opens

  Scenario: Verify the Subfolder Dropdown populates with appropriate subfolders in File Sharing page when user clicks on the Share link
    Given User has access to File Sharing
    When User selects a folder from the Folder dropdown
    Then The Subfolder dropdown should populate with the folder's subfolders' names
    And the dropdown should display the default option 'Select subfolder'

  Scenario: Verify when a folder has no subfolders and the modal box Subfolder dropdown indicates this
    Given User has access to File Sharing
    When User selects a folder from the Folder dropdown
    Then No subfolders should populate in the Subfolder dropdown
    And the dropdown should display the default option 'Select subfolder'
    And the dropdown box should appear disabled

  Scenario: Verify reports are shared to the selected folder/subfolder
    Given User has access to File Sharing
    When User selects a folder from the Folder dropdown
    And has made a selection for the subfolder field
    And has made a selection for the Category field
    And has made a selection for the Expiration Date field
    And has selected the Confirmation Box
    And User clicks "Save"
    Then The selected report should appear in the selected subfolder
    And the content of the file should match the selected report
