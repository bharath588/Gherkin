#Author: Remya
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
@SWEB-17454
Feature: Case Management - Additional Excel elements

  Scenario Outline: Verify Additional Excel elements are added to the Excel file
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user clicks on 'Case Management' option under the Plan Menu
    And user clicks on 'View all case history' button
    And user clicks on 'Excel' link
    Then the Excel file columns should include "<Labels>"

    Examples: 
      | plan_no   | accucode    | username | password | Labels                                                                                                        |
      | 932777-01 | PlanEmpower | 1ISIS    | testing1 | PARTICIPANT FIRST NAME,PARTICIPANT LAST NAME,CLOSED DATE,DESCRIPTION,PLAN AGING DAYS,RECORDKEEPING AGING DAYS |
      | 150012-01 | PlanJPM     | 1IN02    | testing1 | PARTICIPANT FIRST NAME,PARTICIPANT LAST NAME,CLOSED DATE,DESCRIPTION,PLAN AGING DAYS,RECORDKEEPING AGING DAYS |
