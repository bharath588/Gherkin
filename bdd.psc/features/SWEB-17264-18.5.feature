#Author: Bindu Subbireddy, Connor Swatling, Silvio Nunes, Giri Kasarla
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
Feature: Mandatory deferral minimums
  
  Narrative: As a sponsor, I want to know when a plan I manage has a minimum deferral amount.

  Scenario: verify value entered by user is greater than or equal to the plan mandatory minimum deferral rate
    Given a mandatory minimum deferral amount is in place
    When user enters a contribution amount lower than the minimum
    Then an error message should appear
    And the contribution should not be processed

  Scenario: verify that message displayed when user enters dollar value that is less than plan minimum
    Given a mandatory minimum deferral amount is in place
    When user enters a contribution dollar amount lower than the minimum
    Then an error message should appear indicating the user's dollar amount is less than the plan minimum

  Scenario: verify that message displayed when user enters percentage value that is less than plan minimum
    Given a mandatory minimum deferral amount is in place
    When user enters a contribution percentage lower than the minimum
    Then an error message should appear indicating that the user's percentage value is less than the plan minimum

  Scenario: verify that putting a 'stop' on deferrals sets the deferral contribution rate to the minimum
    Given a "<mandatory minimum deferral>" amount is in place for a "<deferral type>"
      | mandatory minimum deferral | deferral type   |
      | 5%                         | Roth Before Tax |
      | 2%                         | Roth After Tax  |
      | 20%                        | Roth After Tax  |
    When user clicks 'Edit' next to 'Stop all deferrals'
    And clicks 'confirm' on the following screen
    Then the deferral contribution rate is set to the "mandatory minimum deferral" amount for that "deferral type"
