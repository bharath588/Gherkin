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
@SWEB-18057
Feature: Case Management - Participant label mapping
  
  Narrative Description : As a PSC Case management user, I want to see the EMP ID and PART ID displayed with the right labels.
 
  Scenario Outline: Issue 1a: Participant column has the data assigned to the correct label on the dashboard.
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user clicks on 'Case Management' option under the Plan Menu
    Then the "<Part_ID>" and "<Emp_ID>" fields should be displayed correctly for "<Case_Number>"

    Examples: 
      | plan_no   | accucode    | username | password  | Part_ID  | Emp_ID    | Case_Number |
      | 932777-01 | PlanEmpower | 1ISIS    | testing1  |  9802725 | 123456789 |        1737 |
      | 150012-01 | PlanJPM     | 3JPM     | Testing@1 | 17681126 |    931256 |        1736 |

  Scenario Outline: Issue 1b: Participant column has the data assigned to the wrong label.
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user clicks on 'Case Management' option under the Plan Menu
    And user clicks on 'View all case history' button
    Then the "<Part_ID>" and "<Emp_ID>" fields should be displayed correctly for "<Case_Number>"

    Examples: 
      | plan_no   | accucode    | username | password  | Part_ID  | Emp_ID       | Case_Number |
      | 932777-01 | PlanEmpower | 1ISIS    | testing1  | 11987947 | 555555587569 |        1704 |
      | 150012-01 | PlanJPM     | 3JPM     | Testing@1 | 17681126 |       931256 |        1736 |

  Scenario Outline: Issue 1c: Participant column has the data assigned to the wrong label.
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user clicks on 'Case Management' option under the Plan Menu
    And user selects a legend link in 'Participant by category' with related to "<Case_Number>"
    Then the "<Part_ID>" and "<Emp_ID>" fields should be displayed correctly for "<Case_Number>" in the pop-up

    Examples: 
      | plan_no   | accucode    | username | password  | Part_ID  | Emp_ID    | Case_Number |
      | 932777-01 | PlanEmpower | 1ISIS    | testing1  |  9802725 | 123456789 |        1737 |
      | 150012-01 | PlanJPM     | 3JPM     | Testing@1 | 17681126 |    931256 |        1736 |

  Scenario Outline: Issue 2: The participant selection box on the new case creation has the wrong labels.
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user clicks on 'Case Management' option under the Plan Menu
    And user selects the 'New Case' option
    And user selects "Participant case" in case type and searches for a Participant using "<Part_ID>" and "<Emp_ID>"
    Then the "<Part_ID>" and "<Emp_ID>" fields should be displayed correctly for the "<plan_no>" in search results

    Examples: 
      | plan_no   | accucode    | username | password  | Part_ID  | Emp_ID    |
      | 932777-01 | PlanEmpower | 1ISIS    | testing1  |  9802725 | 123456789 |
      | 150012-01 | PlanJPM     | 3JPM     | Testing@1 | 17681126 |    931256 |

  Scenario Outline: Issue 3: The Case details page has the wrong label for the EMP ID.
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user clicks on 'Case Management' option under the Plan Menu
    And user clicks on 'View all case history' button
    And user select "<Case_Number>"
    Then 'Case details' page is displayed
    And the "<Label>" should be displayed

    Examples: 
      | plan_no   | accucode    | username | password  | Part_ID  | Emp_ID    | Case_Number | Label       |
      | 932777-01 | PlanEmpower | 1ISIS    | testing1  |  9802725 | 123456789 |        1737 | Employee ID |
      | 150012-01 | PlanJPM     | 3JPM     | Testing@1 | 17681126 |    931256 |        1736 | Employee ID |
