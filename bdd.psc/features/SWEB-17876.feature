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
@SWEB-17876
Feature: Plan / Plan Provisions / Angular page load
  Narrative Description : As a PSC user, the Plan provision details should be displayed when clicking on the button

  Scenario Outline: When a user in on the Plan Provisions page in NextGen and clicks on one of the details buttons, the new Angular page should be rendered for non-part grouped plans
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    And user is on the Plan Provisions page of "<planNo>"
    When user click to "<buttonName>"
    Then the "<buttonPage>" page should be displayed

    Examples: 
      | planNo    | accucode     | username | password | buttonName                                                             | buttonPage                                                                     |
      #| 341587-01 | InstAF       | 1AF      | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 168004-01 | InstJPM      | 1IJPM    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      | 280053-01 | InstSunTrust | 2IST     | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 453000-01 | InstFTB      | 1FTB     | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 150004-01 | PlanApple    | 3VFO3    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 331011-01 | PlanEmpower  | 1ISIS    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 150012-01 | PlanJPM      | 3JPM     | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 385076-P1 | PlanDMBA     | 1ISIS    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 98949-01  | PlanMiss     | 1MISS    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |

  #| 932777-01 | PlanMN      | 1IN02    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
  Scenario Outline: When a user in on the Plan Provisions page in NextGen and clicks on one of the details buttons, the new Angular page should be rendered for part grouped plans
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    And user is on the Plan Provisions page of "<planNo>"
    When user click to "<buttonName>"
    Then the "<buttonPage>" page should be displayed

    Examples: 
      | planNo    | accucode    | username | password | buttonName                                                             | buttonPage                                                                     |
      #| 341009-01 | InstAF      | 4AF      | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 168069-01 | InstJPM     | 2IJPM    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      | 280053-01 | InstSunTrust| 2IST     | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 453008-01 | InstFTB     | 1FTB     | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 150550-01 | PlanApple   | 3VFO3    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 384012-02 | PlanEmpower | 1ISIS    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 150012-01 | PlanJPM      | 3JPM     | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 385076-P1 | PlanDMBA     | 1ISIS    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 932777-01 | PlanMiss     | 1IN02    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
      #| 932777-01 | PlanMN       | 1IN02    | testing1 | Automatic enrollment,Deferrals,Plan match,Eligibility,Loan information | Automatic enrollment,Allowed Deferrals,Plan match,Eligibility,Loan information |
