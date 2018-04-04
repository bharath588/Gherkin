#Author: Connor
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
Feature: PSC social media icons

  Scenario: Social media icons display on PSC home page
    Given User is on Home Page of NextGen
    And User is connected to a plan
    When User clicks on the YouTube social media icon
    Then A new tab opens with https://www.youtube.com/channel/UCFPLlGp16vPjBb-G7SnUWhQ
    When User clicks on the Instagram social media icon
    Then A new tab opens with https://www.instagram.com/empowerretirement/
