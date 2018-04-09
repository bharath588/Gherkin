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
@SWEB-17816
Feature: Employee Overview / Employee Details - show_additional_fields flag
  Narrative Description : As a PSC user, the elements displayed on the Employee details  tab will vary based on the show_additional_fields property

  Scenario Outline: Verify Employee details elements are suppressed when the flag = False
    Given User is on Employee Search page for "<plan_no>" in "<accucode>" site when user login with correct "<username>" and "<password>"
    When user searches employee by participant id using search text "<IND_ID>"
    And clicks on first name link in search result
    And user clicks on 'Employee Details' tab on overview page
    Then user is on employee detail page
    And Employee Information section should not include "<Labels>"

    Examples: 
      | plan_no   | accucode     | username | password | IND_ID   | Labels                                                                                                                                          |
      | 932777-01 | PlanEmpower  | 1ISIS    | testing1 | 10246343 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 150012-01 | PlanJPM      | 1IN02    | testing1 | 17681594 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 385075-01 | PlanDMBA     | 1PDMBA   | testing1 | 11529764 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 98949-01  | PlanMiss     | 2MISS    | testing1 |  8257298 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 341587-01 | InstAF       | 1AF      | testing1 |  4855509 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 168004-01 | InstJPM      | 1IJPM    | testing1 | 12933870 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 250366-01 | InstSunTrust | 1IST     | testing1 |  3494943 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
      | 453000-01 | InstFTB      | 1FTB     | testing1 | 14684673 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |

  Scenario Outline: Verify Employee details elements are displayed when the flag = True
    Given User is on Employee Search page for "<plan_no>" in "<accucode>" site when user login with correct "<username>" and "<password>"
    When user searches employee by participant id using search text "<IND_ID>"
    And clicks on first name link in search result
    And user clicks on 'Employee Details' tab on overview page
    Then user is on employee detail page
    And the Employee Information section should include <Labels>

    Examples: 
      | plan_no   | accucode  | username | password | IND_ID   | Labels                                                                                                                                          |
      | 150550-01 | PlanApple | 3VFO3    | testing1 | 11839592 | Union, Insider, Super Officer, FT/PT Employee, Job description, Employment type, Overseas employee, Overseas date, Employer classification code |
