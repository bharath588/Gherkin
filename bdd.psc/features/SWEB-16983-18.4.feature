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
@SWEB-16983
Feature: InstMetLife - present non-Empower branded information in the PDF links in the Plan analytics selection
  Narrative Description : As a MetLife client I want to be presented non-Empower branded information in the PDF links in the Plan analytics section

  Scenario: Verify the Post-login pages present the Plan analytics selection
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username |  | password |
      | InstMetLife | 1IML     |  | testing1 |
    #And selects a plan
    Then main tab displays Plan Analytics section
    When user clicks on Important information disclosure link under the Lifetime income score chart
    Then new tab opens a .pdf file of Important information for the "Lifetime income score" and is not branded for Empower
    When user clicks on About investment strategies link under the investment strategies balances
    Then new tab opens a .pdf file of "About investment strategies" and is not branded for Empower