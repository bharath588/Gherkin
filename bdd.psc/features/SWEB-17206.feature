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
@SWEB-17206
Feature: My Profile - Error 500

  Scenario Outline: Verify the My Profile page is displayed for PL users before selecting a plan given "<accucode>"
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user selects to My Profile
    Then the My Profile page should be displayed

    Examples: 
      | accuCode     | username | password |
      | InstAF       | 2AF      | testing1 |
      | PlanEmpower  | 5inst    | testing1 |
      | InstJPM      | 2iJPM    | testing1 |
      | PlanJPM      | 2in02    | testing1 |
      | PlanMiss     | 2Miss    | testing1 |
      | PlanMN       | 2MN      | testing1 |
      | InstDMBA     | 2DMBA    | password |
      | InstSunTrust | 2IST     | testing1 |
      | InstFTB      | 2FTB     | testing1 |
      #Confirm date and accucode of InstMetlife
      | InstMetLife  | 2ML      | testing1 |

  Scenario Outline: Verify the My Profile page is displayed for PL users after selecting a plan given "<accucode>"
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user selects to My Profile
    Then the My Profile page should be displayed

    Examples: 
      | accuCode     | username | password | plan_no  |
      | InstAF       | 2AF      | testing1 | #####-01 |
      | PlanEmpower  | 5inst    | testing1 | #####-01 |
      | InstJPM      | 2iJPM    | password | #####-01 |
      | PlanJPM      | 2in02    | testing1 | #####-01 |
      | PlanMiss     | 2Miss    | testing1 | #####-01 |
      | PlanMN       | 2MN      | testing1 | #####-01 |
      | InstDMBA     | 2DMBA    | password | #####-01 |
      | InstSunTrust | 2IST     | testing1 | #####-01 |
      | InstFTB      | 2FTB     | testing1 | #####-01 |
      #Confirm date and accucode of InstMetlife
      | InstMetLife  | 2ML      | testing1 | #####-01 |

  Scenario Outline: Verify the My Profile page is displayed for PSC users before selecting a plan given "<accucode>"
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user selects to My Profile
    Then the My Profile page should be displayed

    Examples: 
      | accuCode     | username | password |
      | InstAF       | 1AF      | testing1 |
      | PlanEmpower  | 1inst    | testing1 |
      | InstJPM      | 1iJPM    | password |
      | PlanJPM      | 1in02    | testing1 |
      | PlanMiss     | 1Miss    | testing1 |
      | PlanMN       | 1MN      | testing1 |
      | InstDMBA     | 1DMBA    | password |
      | InstSunTrust | 1IST     | testing1 |
      | InstFTB      | 1FTB     | testing1 |
      #Confirm date, ID, and accucode of InstMetlife
      | InstMetLife  | 1ML      | testing1 |

  Scenario Outline: Verify the My Profile page is displayed for PSC users after selecting a plan given "<accucode>"
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user selects to My Profile
    Then the My Profile page should be displayed

    Examples: 
      | accuCode     | username | password |
      | InstAF       | 1AF      | testing1 |
      | PlanEmpower  | 1inst    | testing1 |
      | InstJPM      | 1iJPM    | password |
      | PlanJPM      | 1in02    | testing1 |
      | PlanMiss     | 1Miss    | testing1 |
      | PlanMN       | 1MN      | testing1 |
      | InstDMBA     | 1DMBA    | password |
      | InstSunTrust | 1IST     | testing1 |
      | InstFTB      | 1FTB     | testing1 |
      #Confirm date, ID, and accucode of InstMetlife
      | InstMetLife  | 1ML      | testing1 |
