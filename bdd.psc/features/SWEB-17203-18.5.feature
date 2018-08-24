#Author: Bindu Subbireddy, Lydia Bauer, Connor Swatling, Silvio Nunes, Giri Kasarla
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
#developed by: Soumya Ranjan Lenka
@SWEB-17203
Feature: Future Dated Deferrals One-time
  
  Narrative: As a sponsor, I need to be able to schedule one-time deferrals for my participants.

  Scenario Outline: Verify clicking 'Edit' next to One-time on the payroll contributions page brings the user to the One-time deferral change page
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user switched to "<plan_no>" and navigate to deferral page
    And user clicks 'Edit' next to One-time on the payroll contributions page
    Then user is sent to "One-time" deferral election screen

    Examples: 
      | accuCode    | username | password | plan_no   |
      | PlanEmpower | 1INST    | testing1 | 194193-01 |

  Scenario: verify dropdown dates match payroll dates
    Given user is on the One-time deferral election screen
    When user clicks date dropdown box
    Then dropdown should show user's payroll dates for the next 18 months
    And the dates shown should not have already occurred

  Scenario: Verify that date dropdown box is required before proceeding to confirmation page
    Given user is on the One-time deferral election screen
    When user clicks date dropdown box and makes a selection
    Then the 'Continue button should be "enabled"

  Scenario Outline: Verify that dollar election information is required before proceeding to confirmation page
    Given user is on the One-time deferral election screen
    And has selected the "Dollar" button
    When user enters "<dollar>"
    And user clicks date dropdown box and makes a selection
    Then the 'Continue button should be "enabled"

    Examples: 
      | dollar |
      |   1.00 |
      |  15000 |
      |   1.33 |
      |  20.01 |

  Scenario Outline: Verify that percent election information is required before proceeding to confirmation page
    Given user is on the One-time deferral election screen
    And has selected the "Percent" button
    When user enters "<percent>"
    And user clicks date dropdown box and makes a selection
    Then the 'Continue button should be "enabled"

    Examples: 
      | percent |
      |       1 |
      |      15 |
      |     100 |

  Scenario Outline: Verify that deferral appears in 'Current and Pending Deferrals' list
    Given user has set a one-time deferral amount "<dollar>" and selected a date from the date dropdown box
    When user confirms deferral
    Then deferral amount "<dollar>" and description should appear within 'Current and Pending Deferrals' list
    And the selected date should be listed as the effective date
   Examples: 
      | dollar |
      |   5    |
  Scenario: Verify that outstanding one-time deferrals can only be cancelled if the sponsor has not been notified
    Given user has set up a one-time deferral amount for the future
    And sponsor notification ('payroll cutoff date') has not yet been issued
    When user goes to cancel the deferral by clicking 'Cancel' on the Current and Pending deferrals screen
    Then the option to cancel the deferral should still be available

  Scenario Outline: Verify that outstanding one-time deferrals can be cancelled
    #Given user has set a one-time deferral amount for a date
    #And date is a future date
    Given user has set a one-time deferral amount "<dollar>" and selected a date from the date dropdown box
    When user cancels deferral
    Then deferral should no longer appear in list of 'Ongoing and pending deferrals'
    And deferral should no longer be applied to corresponding payroll
   Examples: 
      | dollar |
      |   5    |
  Scenario: Verify that a scheduled one-time deferral overrides a scheduled ongoing contribution
    Given user has scheduled a one-time deferral for X date
    And user has scheduled an ongoing deferral rate change for the same date and money type
    When X date occurs
    Then the one-time deferral rate should override the scheduled ongoing contribution

  Scenario: Verify that stopping all deferrals cancels existing one-time deferrals
    Given user has set a one-time deferral
    When user clicks 'Edit' next to the 'Stop all deferrals' option on the Current and Pending deferrals screen
    Then existing one-time deferral should be cancelled
    But not if the notification batch ('payroll cutoff date') has gone out
