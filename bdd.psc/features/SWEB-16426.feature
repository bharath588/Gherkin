#Author: your.email@your.domain.com
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
#Iteration 1
@SWEB-16426
Feature: Plan Visualization Submenu Item

  Scenario: Verify Plan Visualizer submenu item displayed under PartnerLink menu option
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 2ISIPL    | testing1 |
    When User selects PartnerLink>Plan Visualizer menu tab
    Then Plan Visualizer page will open
    And breadcrumb will update to "PartnerLink/PlanVisualizer"

