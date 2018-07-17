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
@SWEB-18056
Feature: Employee Overview / Employee Details / Edit Information - show_additional_fields flag
  Narrative Description : As a PSC user, the elements displayed on the edit screen of the Employee Details from the Employee details tab will vary based on the show_additional_fields property

  Scenario Outline: Verify Employee details elements are suppressed when the flag = False
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects plan "<plan_no>"
    When User is landing on Employee Search Page 
    And User search employee by "Participant ID"  and select an employee with "<IND_ID>"
    And I click on Employee detail tab
    Then the Employee Information section should not include "<Labels>"

    Examples: 
      | plan_no    | accuCode     | username | password | IND_ID   | Labels                                                                                                                                          |
      | 341587-01  | InstAF       | 1AF      | testing1 | 14836582 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 168004-01  | InstJPM      | 1IJPM    | testing1 | 12933870 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 250306-N1  | InstSunTrust | 1IST     | testing1 | 11032492 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 453669-01  | InstFTB      | 1FTB     | testing1 |  5371944 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 1005006-02 | InstMetLife  | 1IML     | testing1 |  3880557 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |

  Scenario Outline: Verify Employee details elements are displayed when the flag = True
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects plan "<plan no>"
    When User search employee by "Participant ID"  and select an employee with "<IND_ID>"
    And I click on Employee detail tab
    Then the Employee Information section should include "<Labels>"

    Examples: 
      | plan_no    | accuCode    | username | password  | IND_ID   | Labels                                                                                                                                          |
      | 150551-01  | PlanApple   | 3VFO3    | testing1  | 11758237 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 1200035-01 | PlanEmpower | 1INST    | testing1  | 14537719 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 150012-01  | PlanJPM     | 3JPM     | testing1  | 17681800 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 385076-P1  | PlanDMBA    | 1PDMBA   | Testing@1 | 11539629 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 98949-01   | PlanMiss    | 1MISS    | testing1  |  8257371 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 98945-01   | PlanMN      | 1MN      | testing1  |  8993304 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |

  Scenario Outline: Verify Edit Employee Information elements are suppressed when the flag = False
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects plan "<plan no>"
    When User search employee by "Participant ID"  and select an employee with "IND_ID"
    And I click on Employee detail tab
    And user click on Edit button of Employment information
    And user click on Details link under Employment history
    Then the 'Details' and 'Update employment information' section should not include "<Labels>"

    Examples: 
      | plan_no    | accucode     | username | password | IND_ID   | Labels                                                                                                                                          |
      | 341587-01  | InstAF       | 1AF      | testing1 | 14836582 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 168004-01  | InstJPM      | 1IJPM    | testing1 | 12933870 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 250306-N1  | InstSunTrust | 1IST     | testing1 | 11032492 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 453669-01  | InstFTB      | 1FTB     | testing1 |  5371944 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 1005006-02 | InstMetLife  | 1IML     | testing1 |  3880557 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |

  Scenario Outline: Verify Edit Employee Information elements are displayed when the flag = True
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And User search and selects plan "<plan no>"
    When User search employee by "Participant ID"  and select an employee with "IND_ID"
    And I click on Employee detail tab
    And user click on Edit button of Employment information
    And user click on Details link under Employment history
    Then the 'Details' and 'Update employment information' section should include "<Labels>"

    Examples: 
      | plan_no    | accucode    | username | password  | IND_ID   | Labels                                                                                                                                          |
      | 150551-01  | PlanApple   | 3VFO3    | testing1  | 11758237 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 1200035-01 | PlanEmpower | 1INST    | testing1  | 14537719 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 150012-01  | PlanJPM     | 3JPM     | testing1  | 17681800 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 385076-P1  | PlanDMBA    | 1PDMBA   | Testing@1 | 11539629 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 98949-01   | PlanMiss    | 1MISS    | testing1  |  8257371 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 98945-01   | PlanMN      | 1MN      | testing1  |  8993304 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
