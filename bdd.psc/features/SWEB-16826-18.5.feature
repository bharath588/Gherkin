#Author: mchmdn
# Keywords Summary : Plan Express
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
@SWEB-16826
Feature: Add “MIN DISTR” and “GRACE MDR” Disb Reasons to plan load logic in Plan Express
  Narrative Description : As a Plan Express user I want “MIN DISTR” and “GRACE MDR” Disb Reasons to load to EASY based on questions answered in Plan Express

  Background: user has logged into Plan Express for Core to create a new plan, has progressed through these pages
    Given user is on the PartnerLink Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 1pet     | test11   |
    When Pl user selects Implementation/Plan Express from the main nav
    And selects option 2 Complete Plan Data

  Scenario Outline: assign crit 120 or 129 when Age 59.5 is No and Inservice retirement age is Yes and Inservice is Yes
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is No and Inservice retirement age is Yes and Inservice is Yes
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "120" or "129" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | N       | Y       | Y                             |

  Scenario Outline: assign crit 121 or 133 when Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is Yes
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is Yes
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "121" or "133" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | Y       | Y       | Y                             |

  Scenario Outline: assign crit 122 or 130 when Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is No
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is No
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "122" or "130" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | Y       | Y       | N                             |

  Scenario Outline: assign crit 123 or 124 when Age 59.5 is No and Inservice retirement age is Yes and Inservice is No
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is No and Inservice retirement age is Yes and Inservice is No
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "123" or "124" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | N       | Y       | N                             |

  Scenario Outline: assign crit 125 or 126 when Age 59.5 is No and Inservice retirement age is No and Inservice is No
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is No and Inservice retirement age is No and Inservice is No
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "125" or "126" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | N       | N       | N                             |

  Scenario Outline: assign crit 127 or 131 when Age 59.5 is No and Inservice retirement age is No and Inservice is Yes
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is No and Inservice retirement age is No and Inservice is Yes
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "127" or "131" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | N       | N       | Y                             |

  Scenario Outline: assign crit 119 or 132 when Age 59.5 is Yes and Inservice retirement age is No and Inservice is No
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is Yes and Inservice retirement age is No and Inservice is No
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "119" or "132" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | Y       | N       | N                             |

  Scenario Outline: assign crit 128 or 134 when Age 59.5 is Yes and Inservice retirement age is No and Inservice is Yes
    Given selects a plan "<GAID>" to complete from list provided
    When questions are answered on pages "<7100/10>" and "<7160/10>" and "<7095/10 or 7180/10 or 7195/10>" as
    And Age 59.5 is Yes and Inservice retirement age is No and Inservice is Yes
    When selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then crit "128" or "134" applies to web initiation rule in rule_sel table in database.
      """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
      """

    Examples: 
      | GAID       | 7100/10 | 7160/10 | 7095/10 or 7180/10 or 7195/10 |
      | 1114958-01 | Y       | N       | Y                             |
