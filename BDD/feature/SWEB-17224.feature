#Author: Ravi
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
@SWEB-17224
Feature: Footer copyright date

  Scenario Outline: Verify the footer copyright date is updated to 2018 for the "<accucode>" site post login site
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user scroll down to footer section
    Then the copyright date should be "<Label>"

    Examples: 
      | accucode    | Label                                                                    | username | password |
      | PlanApple   | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. | 1IN02    | testing1 |
      | PlanEmpower | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. | 1ISIS    | testing1 |
      | PlanJPM     | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. | 1IN02    | testing1 |

  Scenario Outline: Verify the footer copyright date is updated to 2018 for the "<accucode>" site Pre login site
    Given user is on the Login page of "<accucode>"
    When user scroll down to footer section
    Then the copyright date should be "<Label>"

    Examples: 
      | accucode    | Label                                                                    |
      | PlanApple   | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. |
      | PlanEmpower | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. |
      | PlanJPM     | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. |

  Scenario Outline: Verify the footer copyright date is updated to 2018 for the "<accucode>" site on Forgot password page
    Given user is on the Forgot Password page
    When user scroll down to footer section
    Then the copyright date should be "<Label>"

    Examples: 
      | accucode    | Label                                                                    |
      | PlanApple   | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. |
      | PlanEmpower | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. |
      | PlanJPM     | 2018 Great-West Life & Annuity Insurance Company. All rights reserved. |
