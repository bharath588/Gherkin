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
@SWEB-18009
Feature: Update Calendar Logic
  
  Narrative: As a sponsor, I want my deferral requested dates to include holidays and weekends.
  
  
  

  Scenario Outline: Verify that the requested date listed on the deferral review page is the same date the user entered when future dating.
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user switched to "<plan_no>" and navigate to deferral page
    And user enters a "<future_date>" contribution for a "<deferral_type>"
    And user clicks continue on the deferral contribution screen
    Then the effective date listed on the deferral review page for a "<deferral_type>" is the "<future_date>" the user entered
    Examples: 
      | accuCode    | username | password | plan_no   | deferral_type | future_date     |
      | PlanEmpower | 1INST    | testing1 | 194193-01 | Before Tax    | current_date+21 |
      | PlanEmpower | 1INST    | testing1 | 194193-01 | Roth          | current_date+2  |

  Scenario Outline: Verify that the requested date listed on the deferral review page is the same date the user entered when not future dating.
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"     
    When user switched to "<plan_no>" and navigate to deferral page
    And user enters a contribution for a "<deferral_type>"
    And user clicks continue on the deferral contribution screen
    Then the effective date listed on the deferral review page is the current date for "<deferral_type>"
    Examples:
      | accuCode    | username | password | plan_no    | deferral_type |
      | PlanEmpower | 1INST    | testing1 |  194193-01 | Before Tax    |
      | PlanEmpower | 1INST    | testing1 |  194193-01 | Roth          |

  Scenario: Verify user can select a <weekend date> when scheduling a deferral contribution.
    Given plan is set up with sdsv subcode GENERIC.
      | accuCode    | username | password | deferral_type |
      | PlanEmpower | 1INST    | testing1 | Before Tax    |
    And participant has an ongoing deferral.
    When user adds a scheduled automatic increase.
    Then the dropdown list should populate with weekend dates.
    And user should be able to add a schedule.
    
    Scenario Outline: Verify that the requested date listed on the deferral review page is the first date of the following month when user creates a 457 contribution.
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user switched to "<plan_no>" and navigate to deferral page
    And user enters a contribution for a "<deferral_type>" of the given plan
    And user clicks continue on the deferral contribution screen      
    Then the effective date listed on the deferral review page for a "<deferral_type>" is the first date of the following month
    Examples:
      | accuCode    | username | password | plan_no  | deferral_type |
      | PlanEmpower | 2PNP     | testing1 | 98984-01 | Before Tax    |
      | PlanEmpower | 2PNP     | testing1 | 98984-01 | Roth          |

  