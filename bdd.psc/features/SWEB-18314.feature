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
@SWEB-18314
Feature: MetLife redirect
  Narrative Description : As a PSC user, when I enter the heritage PSC login page URL, I should be redirected to the new site login page

  Scenario Outline: When a user enters the Hertiage URL for MetLife PSC site, the NextGen logon page should be rendered
    Given User opens "<Source_URL>"
    Then "<NextGen_URL>" page should be displayed

    Examples: 
      | Source_URL                                                  | NextGen_URL                                                         |
      #| https://proj1-plan.retirementpartner.com/psc/?accu=PscMetWR | https://plan.empower-retirement.com/static/InstMetLife/welcome.html |
      | https://proj1-plan.retirementpartner.com/psc/?accu=PscMetWR | https://plan-metlife.retirementpartner.com/static/InstMetLife/welcome.html|
