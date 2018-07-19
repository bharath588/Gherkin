#Author: mchmdn
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
@SWEB-16967
Feature: InstMetLife - Hero Content
  Narrative Description : As a MetLife client I want the hero content to be displayed in Pre-Login page

  Scenario: Verify the hero content includes the correct content in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to Pre-Login Banner section
    Then the PreLogin Banner should be displayed
    And the following text should be displayed in Banner Section
      """
      New Look, New Features.
      We've added new resources to help your employees along their retirement journey.
      """

  Scenario Outline: Verify the hero content includes the correct content in Pre-Login Content Tiles
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to Pre-Login Content Tiles section
    Then the PreLogin Content Tiles pictures should be displayed
    And the "<following text>" should be displayed with each "<link to>" for that text

    Examples: 
      | following text                                                  | link to                                                                          |
      | Keep retirement planning top of mind with employees. LEARN MORE | metlife.com/retireready                                                          |
      | MetLife's 16th Annual Benefit Trends Study. READ REPORT           | benefittrends.metlife.com                                                        |
      | Maximum Benefits and Contribution Limits for 2018. VIEW LIMITS  | www.metlife.com/content/dam/metlifecom/us/homepage/metlife-resources-plan-sponsor/participant-materials/IRS-Annual-Contribution-Limits-Flyer|
      
      
      
      
      
	#	1) Keep retirement planning top of mind with employees. LEARN MORE [link to: metlife.com/retireready]
	#	2) MetLife's 15 Annual Benefit Trends Study. READ REPORT [link to: benefittrends.metlife.com]
	#	3) Maximum Benefits and Contribution Limits for 2018. VIEW LIMITS [link to: https://www.assets.metlife.com/RPP/public/pdf/IRS-Annual-Contribution-Limits.pdf]
