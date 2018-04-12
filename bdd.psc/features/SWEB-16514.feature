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
@SWEB-16514
Feature: Asset allocation model label change for InstAF site

  Scenario Outline: Verify the label for the Asset Allocation Model tab on the Investment page is correct for the "<accucode>"
    Given User successfully navigated to "<accucode>"
    When user enters "<username>" and "<password>" and clicks on sign in button
    Then user is on Home page on the site "<accucode>"
    When user switches to "<plan_no>"
    And user selects the "<Investments>" option under the Plan Menu
    Then the tab label Asset Allocation Model should be "<Label>"

    Examples: 
      | plan_no   | accucode    | Investments               | Label                   | username | password |
      | 341587-01 | InstAF      | Investments & Results     | Model portfolios        | 1AF      | testing1 |
      | 194017-01 | PlanEmpower | Investments & Performance | Asset allocation models | 1inst    | testing1 |
