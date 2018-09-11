#Author: mchmdn
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
@SWEB-16829

Feature: 1.	Add “MIN DISTR” and “GRACE MDR” Disb Reasons to plan load logic in Plan Express
Narrative Description : As a Plan Express user I want “MIN DISTR” and “GRACE MDR” Disb Reasons to load to EASY based on questions answered in Plan Express 

Background: user has logged into Plan Express for AF to create a new plan, has progressed through these pages: 
Given user is on the PartnerLink Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 1ape      | test11   |
When Pl user selects Implementation/Plan Express from the main nav
And selects option 2 Complete Plan Data


Scenario Outline: assign crit 91 or 94 when Age 59.5 is No and Inservice retirement age is Yes and Inservice is Yes
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is No and Inservice retirement age is Yes and Inservice is Yes
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 91 or 94 applies to web initiation rule
  """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
 Examples: 
  | GAID      | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| N       | Y       |           Y                   |

Scenario Outline: assign crit 92 or 105 when Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is Yes
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is Yes 
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 92 or 105 applies to web initiation rule
 """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
Examples: 
 | GAID  | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| Y       | Y       |           Y                   |

Scenario Outline: assign crit 95 or 103 when Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is No
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is Yes and Inservice retirement age is Yes and Inservice is No
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 95 or 103 applies to web initiation rule
 """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
Examples: 
 | GAID | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| Y       | Y       |           N                   |

Scenario Outline: assign crit 96 or 97 when Age 59.5 is No and Inservice retirement age is No and Inservice is Yes
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is No and Inservice retirement age is No and Inservice is Yes
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 96 or 97 applies to web initiation rule
 """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
  Examples: 
 | GAID   | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| N       | N       |           Y                   |

Scenario Outline: assign crit 98 or 99 when Age 59.5 is No and Inservice retirement age is No and Inservice is No
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is No and Inservice retirement age is No and Inservice is No
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 98 or 99 applies to web initiation rule
 """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
  Examples: 
 | GAID  | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| N       | N       |           N                   |
 
Scenario Outline: assign crit 100 or 101 when Age 59.5 is No and Inservice retirement age is No and Inservice is Yes
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is No and Inservice retirement age is No and Inservice is Yes
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 100 or 101 applies to web initiation rule
 """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
 Examples:
  | GAID | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| N       | N       |           Y                   |
 
Scenario Outline: assign crit 93 or 102 when Age 59.5 is Yes and Inservice retirement age is No and Inservice is No
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is Yes and Inservice retirement age is No and Inservice is No
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 93 or 102 applies to web initiation rule
 """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
  Examples:
 | GAID | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| Y       | N       |           N                   |
  
Scenario Outline: assign crit 86 or 104 when Age 59.5 is Yes and Inservice retirement age is No and Inservice is Yes
Given selects a plan "<GAID>" to complete from list provided
When questions are answered on pages "<5700/10>" and "<5760/10>" and "<5780/10 or 5770/10 or 5698/10>" as
And Age 59.5 is Yes and Inservice retirement age is No and Inservice is Yes
When User clicks Create Forms and Update Recordkeeping System button (PXIS load)
Then crit 86 or 104 applies to web initiation rule
 """
      Select * from rule_sel where ga_id = ? and std_rl_id = 'WEB_INITIATION'
  """
  Examples:
   | GAID | 5700/10 | 5760/10 | 5698/10 or 5770/10 or 5780/10 | 
|1355121-01| Y       | N       |           Y                   |
