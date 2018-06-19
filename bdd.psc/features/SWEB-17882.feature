
#Author: rvpndy
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

Feature: Plan Express  - Plan creation / setup
  Narrative Description : As a Plan Express user I want to start the plan creation process in Plan Express

  Scenario: PartnerLink home page is presented for user
    Given user is on the Login page of accuCode
      | accucode    |
      | PlanEmpower |
    When user enters username and password
      | username | password |
      | 1pet     | test11   |
    Then user is on PartnerLink home page

  Scenario: PlanExpress page is presented
    Given user is on the PartnerLink Home page of accuCode when user login with correct username and password
      | accucode    | username | password |
      | PlanEmpower | 1pet     | test11   |
    When user selects Implementation / Plan Express from the main nav
    Then user is presented Welcome to PlanExpress page

  Scenario: Plan Pipeline Data page 1 is presented
    Given user has logged into PlanExpress page
    When user selects 1 Plan Pipeline Data
    Then user is presented PlanExpress Add a New Plan page 1

  Scenario: Plan Pipeline Data page 2 is presented
    Given user is on PlanExpress Add a New Plan page 1
    When user selects partner as PartnerLink
      | partner                           |
      | Boon Group, Corp 401k, Emjay (23) |
    And enters a plan name
      | planName    |
      | SWEB-17882_ |
    And chooses internal Compliance service
      | internalCompliance |
      | Yes                |
    And clicks on Save & Continue
    Then user is presented PlanExpress Add a New Plan page 2

  Scenario: Plan Pipeline Next Generation experience page is presented
    Given user is on PlanExpress Add a New Plan page 2
    When user enters required data fields
    And enters a plan name in range for the PartnerLink
      """
        select * from ISIS_REF_VALUES WHERE RV_table  = 'GROUP_CLIENT';
      """
    And selects Save & Return
