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
@SWEB-17264
Feature: Mandatory deferral minimums
  
  Narrative: As a sponsor, I want to know when a plan I manage has a minimum deferral amount.

  Scenario Outline: verify value entered by user is greater than or equal to the plan mandatory minimum deferral rate
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    And a mandatory minimum deferral amount is in place
      | plan_no   | ind_id   | db_name | node_object | service_url                                                                     |
      | 194007-01 | 13857250 | inst    | minAmt      | ElectiveDeferralServices/rest/deferralServices/deferrals/participant/defrlType/ |
    When user switches to "<plan_no>"
    And User is on Employee Search Page
    And User search employee by "<search criteria>"  and select an employee with "<search value>"
    And click on paycheck contribution edit link
    Then deferral page will display
    When click on ongoing Edit button
    And user enters a contribution "dollar" amount lower than the minimum
    Then an error message should appear indicating that the "Non-zero election is less than the minimum"
    And the contribution should not be processed
    Given a mandatory minimum deferral amount is in place
      | plan_no   | ind_id   | db_name | node_object | service_url                                                                     |
      | 194007-01 | 13857250 | inst    | minPct      | ElectiveDeferralServices/rest/deferralServices/deferrals/participant/defrlType/ |
    When user enters a contribution "percent" amount lower than the minimum
    Then an error message should appear indicating that the "Non-zero election is less than the minimum"
    And the contribution should not be processed

    Examples: 
      | accucode    | username | password | plan_no   | search criteria | search value |
      | PlanEmpower | 1inst    | testing1 | 194007-01 | Participant ID  |     13857250 |

  Scenario: verify that putting a 'stop' on deferrals sets the deferral contribution rate to the minimum
    Given a mandatory minimum deferral amount is in place for a deferral type
      | deferral type        | plan_no   | ind_id   | db_name | node_object | service_url                                                                     |
      | BEFORE,AFTER| 194007-01 | 13857250 | inst    | minAmt      | ElectiveDeferralServices/rest/deferralServices/deferrals/participant/defrlType/ |
    And user back to deferral page
    When user clicks 'Edit' next to 'Stop all deferrals'
    And clicks 'confirm' on the following screen
    Then the deferral contribution rate is set to the mandatory minimum deferral amount for that deferral type
