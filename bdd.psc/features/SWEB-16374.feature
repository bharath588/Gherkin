#Author:
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
Feature: Label change in "Loan Information" page

  Background: 
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    Then the Home page should be displayed

  Scenario Outline: Verify the label change from “Loans allowed” to “Loans allowed after default” for "<plan_type>"
    Given user is on the Plan Provisions page of "<plan_no>"
    When user clicks Loan Information button on Plan Provision page
    Then the Loan Information page should be displayed
    And the selected "<plan_no>" should be displayed on the page
    And the expected_text label should be displayed
      | expected_text                          |
      | Restriction on number of loans allowed |
      | Minimum loan amount                    |
      | Loan interest rate                     |
      | Loan fee - origination                 |
      | Loan fee - maintenance                 |
      | Loan repayment method                  |
      | Loans allowed after default            |

    Examples: 
      | accucode     | username | password | plan_type     | plan_no    |
      | InstAF       | 1AF      | testing1 | part grouping | 1114020-01 |
      | InstJPM      | 1IJPM    | testing1 | part grouping | 1114020-01 |
      | InstSunTrust | 2IST     | testing1 | part grouping | 1114020-01 |
      | InstFTB      | 1FTB     | testing1 | part grouping | 1114020-01 |
      | PlanApple    | 3VFO3    | testing1 | part grouping | 1114020-01 |
      | PlanEmpower  | 1ISIS    | testing1 | part grouping | 1114020-01 |
      | PlanJPM      | 3JPM     | testing1 | part grouping | 1114020-01 |
      | PlanDMBA     | 1ISIS    | testing1 | part grouping | 1114020-01 |
      | PlanMiss     | 1MISS    | testing1 | part grouping | 1114020-01 |
      | PlanMN       | 1IN02    | testing1 | part grouping | 1114020-01 |

  Scenario Outline: Verify the label change from “Loans allowed” to “Loans allowed after default” for "<plan_type>"
    Given user is on the Plan Provisions page of "<plan_no>"
    When user clicks Loan Information button on Plan Provision page
    Then the Loan Information page should be displayed
    And the selected "<plan_no>" should be displayed on the page
    And the expected_text label should be displayed
      | expected_text                          |
      | Restriction on number of loans allowed |
      | Minimum loan amount                    |
      | Loan interest rate                     |
      | Loan fee - origination                 |
      | Loan fee - maintenance                 |
      | Loan repayment method                  |
      | Loans allowed after default            |

    Examples: 
      | accucode     | username | password | plan_type         | plan_no   |
      | InstAF       | 1AF      | testing1 | non part grouping | 384171-01 |
      | InstJPM      | 1IJPM    | testing1 | non part grouping | 384171-01 |
      | InstSunTrust | 2IST     | testing1 | non part grouping | 384171-01 |
      | InstFTB      | 1FTB     | testing1 | non part grouping | 384171-01 |
      | PlanApple    | 3VFO3    | testing1 | non part grouping | 384171-01 |
      | PlanEmpower  | 1ISIS    | testing1 | non part grouping | 384171-01 |
      | PlanJPM      | 3JPM     | testing1 | non part grouping | 384171-01 |
      | PlanDMBA     | 1ISIS    | testing1 | non part grouping | 384171-01 |
      | PlanMiss     | 1MISS    | testing1 | non part grouping | 384171-01 |
      | PlanMN       | 1IN02    | testing1 | non part grouping | 384171-01 |

  Scenario Outline: Verify "Restriction on number of loans allowed" is displaying "NA" if value returned is Null for "<plan_type>"
    Given user is on the Plan Provisions page of "<plan_no>"
    When user clicks Loan Information button on Plan Provision page
    And in DB "max_loans_allowed" column of table "GRP_LOAN_TERM" is Null
    Then the  "Restriction on number of loans allowed" should display "N/A"

    Examples: 
      | accucode     | username | password | plan_type     | plan_no    |
      | InstAF       | 1AF      | testing1 | part grouping | 1114020-01 |
      | InstJPM      | 1IJPM    | testing1 | part grouping | 1114020-01 |
      | InstSunTrust | 2IST     | testing1 | part grouping | 1114020-01 |
      | InstFTB      | 1FTB     | testing1 | part grouping | 1114020-01 |
      | PlanApple    | 3VFO3    | testing1 | part grouping | 1114020-01 |
      | PlanEmpower  | 1ISIS    | testing1 | part grouping | 1114020-01 |
      | PlanJPM      | 3JPM     | testing1 | part grouping | 1114020-01 |
      | PlanDMBA     | 1ISIS    | testing1 | part grouping | 1114020-01 |
      | PlanMiss     | 1MISS    | testing1 | part grouping | 1114020-01 |
      | PlanMN       | 1IN02    | testing1 | part grouping | 1114020-01 |

  Scenario Outline: Verify "Restriction on number of loans allowed" is displaying "NA" if value returned is Null for "<plan_type>"
    Given user is on the Plan Provisions page of "<plan_no>"
    When user clicks Loan Information button on Plan Provision page
    And in DB "max_loans_allowed" column of table "GRP_LOAN_TERM" is Null
    Then the  "Restriction on number of loans allowed" should display "N/A"

    Examples: 
      | accucode     | username | password | plan_type         | plan_no   |
      | InstAF       | 1AF      | testing1 | non part grouping | 384171-01 |
      | InstJPM      | 1IJPM    | testing1 | non part grouping | 384171-01 |
      | InstSunTrust | 2IST     | testing1 | non part grouping | 384171-01 |
      | InstFTB      | 1FTB     | testing1 | non part grouping | 384171-01 |
      | PlanApple    | 3VFO3    | testing1 | non part grouping | 384171-01 |
      | PlanEmpower  | 1ISIS    | testing1 | non part grouping | 384171-01 |
      | PlanJPM      | 3JPM     | testing1 | non part grouping | 384171-01 |
      | PlanDMBA     | 1ISIS    | testing1 | non part grouping | 384171-01 |
      | PlanMiss     | 1MISS    | testing1 | non part grouping | 384171-01 |
      | PlanMN       | 1IN02    | testing1 | non part grouping | 384171-01 |

  Scenario Outline: Verify "Restriction on number of loans allowed" is displaying correct value as in DB if value returned is not Null
    Given user is on the Plan Provisions page of "<plan_no>"
    When user clicks Loan Information button on Plan Provision page
    And in DB "max_loans_allowed" column of table "GRP_LOAN_TERM" is not Null
    Then the "Restriction on number of loans allowed" should display the corresponding value in DB

    Examples: 
      | accucode     | username | password | plan_type     | plan_no    |
      | InstAF       | 1AF      | testing1 | part grouping | 1114020-01 |
      | InstJPM      | 1IJPM    | testing1 | part grouping | 1114020-01 |
      | InstSunTrust | 2IST     | testing1 | part grouping | 1114020-01 |
      | InstFTB      | 1FTB     | testing1 | part grouping | 1114020-01 |
      | PlanApple    | 3VFO3    | testing1 | part grouping | 1114020-01 |
      | PlanEmpower  | 1ISIS    | testing1 | part grouping | 1114020-01 |
      | PlanJPM      | 3JPM     | testing1 | part grouping | 1114020-01 |
      | PlanDMBA     | 1ISIS    | testing1 | part grouping | 1114020-01 |
      | PlanMiss     | 1MISS    | testing1 | part grouping | 1114020-01 |
      | PlanMN       | 1IN02    | testing1 | part grouping | 1114020-01 |

  Scenario Outline: Verify "Restriction on number of loans allowed" is displaying correct value as in DB if value returned is not Null
    Given user is on the Plan Provisions page of "<plan_no>"
    When user clicks Loan Information button on Plan Provision page
    And in DB "max_loans_allowed" column of table "GRP_LOAN_TERM" is not Null
    Then the "Restriction on number of loans allowed" should display the corresponding value in DB

    Examples: 
      | accucode     | username | password | plan_type         | plan_no   |
      | InstAF       | 1AF      | testing1 | non part grouping | 384171-01 |
      | InstJPM      | 1IJPM    | testing1 | non part grouping | 384171-01 |
      | InstSunTrust | 2IST     | testing1 | non part grouping | 384171-01 |
      | InstFTB      | 1FTB     | testing1 | non part grouping | 384171-01 |
      | PlanApple    | 3VFO3    | testing1 | non part grouping | 384171-01 |
      | PlanEmpower  | 1ISIS    | testing1 | non part grouping | 384171-01 |
      | PlanJPM      | 3JPM     | testing1 | non part grouping | 384171-01 |
      | PlanDMBA     | 1ISIS    | testing1 | non part grouping | 384171-01 |
      | PlanMiss     | 1MISS    | testing1 | non part grouping | 384171-01 |
      | PlanMN       | 1IN02    | testing1 | non part grouping | 384171-01 |
