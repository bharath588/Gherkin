#Author: your.email@your.domain.com
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
@SWEB-16426
Feature: Plan Visualization Submenu Item
Given User is on PartnerLink
And User has transaction codes related to Relationship Managers, Advisors, or Third-Party Managers
When User clicks on the Resource Center menu tab	
Then The submenu will populate with the Plan Visualizer link
When User clicks on the Plan Visualizer link
Then Plan Visualizer page will open
And breadcrumb will update to "PartnerLink / Plan Visualizer"