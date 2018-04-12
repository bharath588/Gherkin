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
@SWEB-16913
Feature: InstMetLife - Footer - System Tray Requirements Pre-login and Post-Login Footer, social media icons and System tray
  Narrative Description : As a MetLife client I want the Pre-login and Post-Login Footer, social media icons and System tray to be displayed

  Scenario: Verify the Footer includes the correct content in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    Then the Partner specific disclosures and copyright content should be displayed
    And the following text should be displayed
      """
      Access to the website may be limited or unavailable during periods of peak demand, market volatility, systems upgrades/maintenance or other reasons.
      
      The Legal Notices posted on this website apply to our third party services providers that provide services, information and content in connection with this website, 
      and such services providers are deemed third party beneficiaries of such Legal Notices. 
      
      © ccyy MetLife Services and Solutions LLC
      """

  Scenario: Verify the Footer is reflecting current year in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    Then the copyright year should reflect current year

  Scenario: Verify the Footer includes the correct content in Post-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user enters username and password
      | username |  | password |
      | 1IML     |  | testing1 |
    And user scroll down to footer section
    Then the Partner specific disclosures and copyright content should be displayed
    And the following text should be displayed
      """
      Access to the website may be limited or unavailable during periods of peak demand, market volatility, systems upgrades/maintenance or other reasons.
      
      The Legal Notices posted on this website apply to our third party services providers that provide services, information and content in connection with this website, 
      and such services providers are deemed third party beneficiaries of such Legal Notices. 
      
      © ccyy MetLife Services and Solutions LLC
      """

  Scenario: Verify the Footer is reflecting current year in Post-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user enters username and password
      | username |  | password |
      | 1IML     |  | testing1 |
    And user scroll down to footer section
    Then the copyright year should reflect current year

  Scenario: Verify the System tray includes the correct content in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    Then the line Important Information | Privacy Policy  | Legal Notices should be displayed

  Scenario: Verify the Important Information includes the correct content in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    And clicks Important Information
    Then the same information is shown as on heritage site with the login inputs displayed

  Scenario: Verify the Privacy Policy includes the correct link in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    And clicks Privacy Policy
    Then will link to https://www.metlife.com/about/privacy-policy/online-privacy-policy/index.html

  Scenario: Verify the Legal Notices includes the correct link in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    And clicks Legal Notices
    Then will link to https://www.metlife.com/about/terms-and-conditions/index.html

  Scenario: Verify the System tray includes the correct content in Post-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user enters username and password
      | username |  | password |
      | 1IML     |  | testing1 |
    And user scroll down to footer section
    Then the line Important Information | Privacy Policy  | Legal Notices should be displayed --Scope of improvement
    Then following links should be displayed in the system tray
      | links                 |
      | Important Information |
      | Privacy Policy        |

  Scenario: Verify the Important Information includes the correct content in Post-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user enters username and password
      | username |  | password |
      | 1IML     |  | testing1 |
    And user scroll down to footer section
    And clicks Important Information
    Then the same information is shown as on heritage site with the login inputs displayed

  Scenario: Verify the Privacy Policy includes the correct link in Post-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user enters username and password
      | username |  | password |
      | 1IML     |  | testing1 |
    And user scroll down to footer section
    And clicks Privacy Policy
    Then will link to https://www.metlife.com/about/privacy-policy/online-privacy-policy/index.html

  Scenario: Verify the Legal Notices includes the correct link in Post-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user enters username and password
      | username |  | password |
      | 1IML     |  | testing1 |
    When user scroll down to footer section
    And clicks Legal Notices
    Then will link to https://www.metlife.com/about/terms-and-conditions/index.html

  Scenario Outline: Verify the Social Media icons are displayed with correct content and links in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    Then the Social Media "<icons>" are displayed

    Examples: 
      | icons    |
      | Facebook |
      | Twitter  |
      | Linkedin |
      | YouTube  |

  Scenario: Verify the Facebook icon includes the correct link in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    And clicks Facebook
    Then will link to https://www.facebook.com/metlife

  Scenario: Verify the Twitter icon includes the correct link in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    And clicks Twitter
    Then will link to https://twitter.com/metlife

  Scenario: Verify the Linkedin icon includes the correct link in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    And clicks Linkedin
    Then will link to https://www.linkedin.com/company/metlife

  Scenario: Verify the YouTube icon includes the correct link in Pre-Login page
    Given user is on the Login page of accuCode
      | accuCode    |
      | InstMetLife |
    When user scroll down to footer section
    And clicks YouTube
    Then will link to https://www.youtube.com/user/Metlife
