#Author: Matt and Remya
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
@SWEB-16822
Feature: InstSunTrust - System tray - Terms & conditions Pre-login and Post-Login footer

  Scenario: Verify the Terms and Conditions link in the footer text opens in pdf format in pre-login page
    Given user is on the Login page of accuCode
      | accuCode     |
      | InstSunTrust |
    When user clicks on Terms and Conditions link displayed in the footer
    Then the link should open a PDF in a new tab

  Scenario: Verify the system tray does not include a Terms and Conditions link in pre-login page
    Given user is on the Login page of accuCode
      | accuCode     |
      | InstSunTrust |
    When user scroll down to footer section
    Then the Terms and Conditions link should not be displayed in the footer system tray

  Scenario: Verify the Terms and Conditions link in the footer text opens in pdf format in post-login page
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode     | username |  | password |
      | InstSunTrust | 1IST     |  | testing1 |
    When user clicks on Terms and Conditions link displayed in the footer on home page
    Then the link should open a PDF in a new window

  Scenario: Verify the system tray does not include a Terms and Conditions link in the post login page
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode     | username |  | password |
      | InstSunTrust | 1IST     |  | testing1 |
    When user scroll down to footer section
    Then the Terms and Conditions link should not be displayed in the footer system tray on home page

  Scenario: Verify the Terms and Conditions link in the footer text opens in pdf format in Forgot password page
    Given user is on the Forgot Password page
    When user scroll down to footer section
    And user clicks on Terms and Conditions link displayed in the footer on Forgot password page
    Then the link should open a PDF in a new window

  Scenario: Verify the system tray does not include a Terms and Conditions link in Forgot password page
    Given user is on the Forgot Password page
    When user scroll down to footer section
    Then the Terms and Conditions link should not be displayed in the footer system tray on Forgot password page
