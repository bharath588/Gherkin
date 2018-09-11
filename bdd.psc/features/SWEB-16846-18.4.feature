#Author: Matt and Remya
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
#@SWEB-16858
@SWEB-16846
Feature: Empower H.S.A. Plans suppression of elements

  Scenario Outline: Verify the Plan Analytics section is suppressed for HSA plans
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user changes to "<HSA>" plan
    Then "<HSA>" plan home page is displayed
    And Plan Analytics are suppressed

    Examples: 
      | accuCode    | username | password | HSA       |
      | PlanEmpower | 1IN02    | testing1 | 150551-H1 |
      | PlanEmpower | 04IN02   | testing1 | 150551-H1 |

  Scenario Outline: Verify the Account Balance section is suppressed for HSA plans for external users
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user changes to "<HSA>" plan
    Then "<HSA>" plan home page is displayed
    When User is landing on Employee Search Page
    And User search employee by "<SearchBy>"  and select an employee with "<SearchValue>"
    Then the Employee Overview page is displayed
    And Employee account balance and view account history button is suppressed for "<HSA>" plan

    Examples: 
      | accuCode    | username | password | HSA       | SearchBy | SearchValue |
      | PlanEmpower | 04IN02   | testing1 | 150551-H1 | SSN      | 329-50-0691 |

  Scenario Outline: Verify the Account Balance section is suppressed for HSA plans for external users
    When User is landing on Employee Search Page
    And User search employee by "<SearchBy>"  and select an employee with "<SearchValue>"
    Then the Employee Overview page is displayed
    And Employee account balance and view account history button is suppressed for "<HSA>" plan

    Examples: 
      | SearchBy       | SearchValue |HSA      |
      | Participant ID |    11745064 |150551-H1|

  Scenario Outline: Verify the Account Balance section is displayed for HSA plans for internal users
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user changes to "<HSA>" plan
    Then "<HSA>" plan home page is displayed
    When User is landing on Employee Search Page
    And User search employee by "<SearchBy>"  and select an employee with "<SearchValue>"
    Then the Employee Overview page is displayed
    And Employee account balance and view account history button is displays for "<HSA>" plan

    Examples: 
      | accuCode    | username | password | HSA       | SearchBy | SearchValue |
      | PlanEmpower | 1IN02    | testing1 | 150551-H1 | SSN      | 329-50-0691 |

  Scenario Outline: Verify the Account Balance section is displayed for HSA plans for internal users
    When User is landing on Employee Search Page
    And User search employee by "<SearchBy>"  and select an employee with "<SearchValue>"
    Then the Employee Overview page is displayed
    And Employee account balance and view account history button is displays for "<HSA>" plan

    Examples: 
      | SearchBy       | SearchValue | HSA      |
      | Participant ID |    11745064 |150551-H1 |
