#Author: Matt and Remya
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
@SWEB-16823
Feature: InstSunTrust - System tray - System Requirements Pre-login and Post-Login system tray

  Scenario: Verify the system tray System Requirements link includes the correct content in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode     |
      | InstSunTrust |
    When user scroll down to footer section
    And clicks on the System Requirement link on login page
    Then the System Requirements content should be displayed
    And the following text should be displayed
      """
      	 System Requirements
      Web Browsers Supported
      Our website is intended to be used by all recent versions of popular web browsers. Other less popular or older versions of web browsers will generally work on this site, but be aware that some features will not be optimized or may not appear as intended.
      Note: Beta and release candidate software are NOT supported (this includes browsers and operating systems). If you have an issue with a beta version browser or operating system, please contact the manufacturer.
      For this site to function properly, JavaScript must be enabled.
      Browser Encryption
      Our Web-based account services utilize industry-standard security technologies.
      """

  Scenario: Verify the system tray System Requirements link includes the correct content in Post-Login page
    Given user is on the Login page of accuCode
      | accuCode     |
      | InstSunTrust |
    When user enters username and password
      | username |  | password |
      | 1IST     |  | testing1 |
    And user clicks on Sign In button
    And user is on home page
    And user scroll down to footer section
    And clicks on the System Requirement link
    Then the System Requirements content should be displayed in a pop-up window
    And the following text should be displayed
      """
         System Requirements
      Web Browsers Supported
      Our website is intended to be used by all recent versions of popular web browsers. Other less popular or older versions of web browsers will generally work on this site, but be aware that some features will not be optimized or may not appear as intended.
      Note: Beta and release candidate software are NOT supported (this includes browsers and operating systems). If you have an issue with a beta version browser or operating system, please contact the manufacturer.
      For this site to function properly, JavaScript must be enabled.
      Browser Encryption
      Our Web-based account services utilize industry-standard security technologies.
      """

  Scenario: Verify the system tray System Requirements link includes the correct content on Forgot password page
    Given user is on the Forgot Password page
    When user scroll down to footer section
    And clicks on the System Requirement link
    Then the System Requirements content should be displayed in a pop-up window
    And the following text should be displayed
      """
         System Requirements
      Web Browsers Supported
      Our website is intended to be used by all recent versions of popular web browsers. Other less popular or older versions of web browsers will generally work on this site, but be aware that some features will not be optimized or may not appear as intended.
      Note: Beta and release candidate software are NOT supported (this includes browsers and operating systems). If you have an issue with a beta version browser or operating system, please contact the manufacturer.
      For this site to function properly, JavaScript must be enabled.
      Browser Encryption
      Our Web-based account services utilize industry-standard security technologies.
      """
