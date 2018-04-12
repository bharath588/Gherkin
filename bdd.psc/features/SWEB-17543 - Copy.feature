#Author: rvpndy
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
@SWEB-175431
Feature: Plan Empower - Security Guarantee and Tips and System Requirements included in footer; Pre-login, Post-login and Forgot password pages
  Narrative Description : As a Plan Empower PSC user I want the Security Guarantee and Tips and System Requirements included in footer

  Scenario Outline: Verify new "<links>" are included in footer for the "<accucode>" site at forgot password page
    Given user is on the Forgot Password page of "<accucode>"
    When user scroll down to footer section
    Then new "<links>" are displayed in the footer at forgot password page

    Examples: 
      | accucode    | links                                                |
      | PlanEmpower | Security Guarantee,Security Tips,System Requirements |
      | PlanJPM     | Security Guarantee,Security Tips,System Requirements |
      | PlanApple   | Security Guarantee,Security Tips,System Requirements |
      | PlanMN      | Security Guarantee,Security Tips,System Requirements |
      | PlanMiss    | Security Guarantee,Security Tips,System Requirements |
      | PlanDMBA    | Security Guarantee,Security Tips,System Requirements |

  Scenario Outline: verify clicking on "<links>" at footer of forgot password page of "<accucode>" pops up correct page
    Given user is on the Forgot Password page of "<accucode>"
    When user scroll down to footer section
    And user clicks on new links "<links>"
    Then pertinent page pop up
    And clicking close on pop up returns to forgot password page

    Examples: 
      | accucode    | links                                                |
      | PlanEmpower | Security Guarantee,Security Tips,System Requirements |
      | PlanJPM     | Security Guarantee,Security Tips,System Requirements |
      | PlanApple   | Security Guarantee,Security Tips,System Requirements |
      | PlanMN      | Security Guarantee,Security Tips,System Requirements |
      | PlanMiss    | Security Guarantee,Security Tips,System Requirements |
      | PlanDMBA    | Security Guarantee,Security Tips,System Requirements |
