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
    Then the following below text should be displayed in "InstMetLife"
      """
      Access to the website may be limited or unavailable during periods of peak demand, market volatility, systems upgrades/maintenance or other reasons.
      The Legal Notices posted on this website apply to our third party services providers that provide services, information and content in connection with this website, and such services providers are deemed third party beneficiaries of such Legal Notices.
      © 2018 MetLife Services and Solutions LLC
      """

  Scenario Outline: Verify the Footer is reflecting current year in Pre-Login page
    Given user is on the Login page of "<accuCode>"
    When user scroll down to footer section
    Then the copyright date should be "<text>"

    Examples: 
      | accuCode    | text                                    |
      | InstMetLife | 2018 MetLife Services and Solutions LLC |

  Scenario: Verify the Footer includes the correct content in Post-Login page
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username |  | password |
      | InstMetLife | 1IML     |  | testing1 |
    And user scroll down to footer section
    Then the following below text should be displayed in "InstMetLife"
      """
      Access to the website may be limited or unavailable during periods of peak demand, market volatility, systems upgrades/maintenance or other reasons.
      The Legal Notices posted on this website apply to our third party services providers that provide services, information and content in connection with this website, and such services providers are deemed third party beneficiaries of such Legal Notices.
      © 2018 MetLife Services and Solutions LLC
      """

  Scenario Outline: Verify the Footer is reflecting current year in Post-Login page
    Given user is on the Login page of "<accuCode>"
    When user enters "<username>" and "<password>" and clicks on sign in button
    And user scroll down to footer section
    Then the copyright date should be "<text>"

    Examples: 
      | accuCode    | username | password | text                                    |
      | InstMetLife | 1IML     | testing1 | 2018 MetLife Services and Solutions LLC |

  Scenario Outline: Verify the System tray includes the correct content in Pre-Login page
    Given user is on the Login page of "<accuCode>"
    When user scroll down to footer section
    Then following "<links>" should be displayed in the system tray

    Examples: 
      | accuCode    | links                        |
      | InstMetLife | Privacy Policy,Legal Notices |

  Scenario Outline: Verify the Privacy Policy and Legal Notices includes the correct link in Pre-Login page
    Given user is on the Login page of "<accuCode>"
    When user scroll down to footer section
    And User clicks on the "<link>"
    Then A new tab opens with "<url>"

    Examples: 
      | accuCode    | link                         | url                                                                                                                                         |
      | InstMetLife | Privacy Policy,Legal Notices | https://www.metlife.com/about/privacy-policy/online-privacy-policy/index.html,https://www.metlife.com/about/terms-and-conditions/index.html |

  Scenario Outline: Verify the System tray includes the correct content in Post-Login page
    Given user is on the Login page of "<accuCode>"
    When user enters "<username>" and "<password>" and clicks on sign in button
    And user scroll down to footer section
    Then following "<links>" should be displayed in the system tray

    Examples: 
      | accuCode    | username | password | links                        |
      | InstMetLife | 1IML     | testing1 | Privacy Policy,Legal Notices |

  Scenario Outline: Verify the Privacy Policy and Legal Notices includes the correct link in Post-Login page
    Given user is on the Login page of "<accuCode>"
    When user enters "<username>" and "<password>" and clicks on sign in button
    And user scroll down to footer section
    And User clicks on the "<link>"
    Then A new tab opens with "<url>"

    Examples: 
      | accuCode    | username | password | link                         | url                                                                                                                                         |
      | InstMetLife | 1IML     | testing1 | Privacy Policy,Legal Notices | https://www.metlife.com/about/privacy-policy/online-privacy-policy/index.html,https://www.metlife.com/about/terms-and-conditions/index.html |

  Scenario Outline: Verify the Social Media icons are displayed with correct content and links in Pre-Login page
    Given user is on the Login page of "<accuCode>"
    When user scroll down to footer section
    Then the Social Media "<icons>" are displayed

    Examples: 
      | accuCode    | icons                             |
      | InstMetLife | Facebook,Twitter,Linkedin,YouTube |

  Scenario Outline: Verify the Social Media icons includes the correct link in Pre-Login page
    Given user is on the Login page of "<accuCode>"
    When user scroll down to footer section
    And User clicks on the "<icons>"
    Then A new tab opens with "<url>"

    Examples: 
      | accuCode    | icons                             | url                                                                                                                                         |
      | InstMetLife | Facebook,Twitter,Linkedin,YouTube | https://www.facebook.com/metlife/,https://twitter.com/MetLife,https://www.linkedin.com/company/metlife,https://www.youtube.com/user/Metlife |

    
  Scenario Outline: Verify the Social Media icons includes the correct link in Post-Login page
    Given user is on the Login page of "<accuCode>"
    When user enters "<username>" and "<password>" and clicks on sign in button
    And user scroll down to footer section
    And User clicks on the "<icons>"
    Then A new tab opens with "<url>"

    Examples: 
      | accuCode   |username | password | icons                             | url                                                                                                                                         |
      | InstMetLife| 1IML    | testing1 | Facebook,Twitter,Linkedin,YouTube | https://www.facebook.com/metlife/,https://twitter.com/MetLife,https://www.linkedin.com/company/metlife,https://www.youtube.com/user/Metlife |
      