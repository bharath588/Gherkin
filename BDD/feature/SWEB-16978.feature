#Author: mchmdn
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
@SWEB-16978
Feature: InstMetLife - present the correct icon and title for a browser tab on pre- and post- login
  Narrative Description : As a MetLife client I want the correct icon and title for a browser tab presented on pre- and post- login pages

  Scenario: Verify the Pre-login pages present the correct icon and title for a browser tab
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user views browser tab
    Then browser tab presents text "MetLife - Plan Service Center"
    And is branded for MetLife with an appropriate icon

  Scenario: Verify the Post-login pages present the correct icon and title for a browser tab for a PSC user
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username |  | password |
      | InstMetLife | 1IML     |  | testing1 |
    When user views browser tab
    Then browser tab presents text "MetLife - Plan Service Center"
    And is branded for MetLife with an appropriate icon

  Scenario: Verify the Post-login pages present the correct icon and title for a browser tab for a Partner Link user
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username |  | password |
      | InstMetLife | 2IML     |  | testing1 |
    When user views browser tab
    Then browser tab presents text "MetLife - PartnerLink"
    And is branded for MetLife with an appropriate icon
