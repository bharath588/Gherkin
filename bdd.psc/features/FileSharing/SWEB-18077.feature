#Author: Connor Swatling, Bindu Subbireddy, Silvio Nunes
#Keywords Summary :
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
@SWEB-18077
Feature: SWEB-18077 - File Sharing - FileNet file movement
  
  Narrative Description : As a file sharing user, I want to move file(s) to the Vault.

  Scenario: Verify user has option to move files to the Vault
    Given user is on the File sharing page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 1ISIS    | testing1 |
    And user has upload and delete permissions and access to the Vault folder
    When user selects a file
    And clicks the "Move" button that appears
    Then the Move Files modal box should appear
    And the folder destination dropdown box should have the "Vault" option

  Scenario Outline: Verify <fileGroup> moved to Vault appears in Vault
    Given user has test.txt, test.png, and test.pdf in the Auditor folder
    When user moves <fileGroup> to Vault
    Then the moved <fileGroup> should appear in list of files
    And the moved <fileGroup> should not be in the Auditor folder

    Examples: 
      | fileGroup          |
      | test.txt, test.png |
      | test.png, test.pdf |
      | test.pdf           |
      | test.png           |
      | test.txt           |
