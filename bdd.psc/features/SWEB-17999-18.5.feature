# Author: Lydia Bauer, Connor Swatling, Silvio Nunes, Amir Abdullaev
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
@SWEB-17999
Feature: High Chart for MAX Enrollment visualization
  
  Narrative: As a sponsor with a plan that offers Managed Accounts ("MAX"), I want to see High Charts that apply to me.

  Scenario Outline: Verify that the MAX Enrollment High Chart is only visible when user's plan or providing company offers MAX.
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
   And User search and selects "<plan>" offers MAX
    When user clicks 'More' on the Plan Analytics dashboard.
    Then the MAX Enrollment High Chart will be visible.

    Examples: 
      | accuCode    | username | password | plan      |
      | PlanEmpower | 1in02    | testing1 | 150558-01 |

  Scenario Outline: Verify that the MAX Enrollment High Chart is not visible when user's plan or providing company offers MAX but does not have the High Chart TXN code.
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects "<plan>" offers MAX
    When user clicks 'More' on the Plan Analytics dashboard.
    Then the MAX Enrollment High Chart will not be visible.

    Examples: 
      | accuCode    | username | password | plan      |
      | PlanEmpower | 1in02    | testing1 | 1008831-01 |

  Scenario Outline: Verify that the MAX Enrollment High Chart is not visible when user's plan or providing company does not offer MAX.
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects "<plan>" does not offers MAX.
    When user clicks 'More' on the Plan Analytics dashboard.
    Then the MAX Enrollment High Chart will not be visible.

    Examples: 
      | accuCode    | username | password | plan      |
      | PlanEmpower | 1in02    | testing1 | 150002-01 |
