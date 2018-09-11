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
@SWEB-18852
Feature: Plan Web Rewrite AF bundled and TPA pre-login Contact Us modal boxes
  Narrative Description : As a user of the new Plan Web application 
  I want Plan Web to present the AF bundled and TPA role pre-login Contact Us modal boxes

  Scenario Outline: 
    #Given user is in Plan Web using https://proj6-plan.empower-retirement.com/planweb/?accu=InstAFCR#/login
    Given user is on the Login page of "<accuCode>"
    When user is on pre-login page for <accucode>
    And user clicks on Contact Us link
    Then Contact Us modal box is displayed

    Examples: 
      | accucode      | username | password |
      | Inst-AF_React | 1SAFT    | testing1 |
      | Inst-AF_React | 1AAF     | testing1 |

  Scenario Outline: 
    Given user is in Plan Web using https://proj6-plan.empower-retirement.com/planweb/?accu=InstAFCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Contact Us link
    Then Contact Us modal box is displayed

    Examples: 
      | accucode | PSC User Login Credentials |  | PL User Login Credentials |
      | Inst-AF  | 1SAFT& testing1            |  | 1AAF & testing1           |

  Scenario Outline: 
    Given user is in Plan Web using https://proj6-plan.empower-retirement.com/planweb/?accu=InstAFCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Systems Requirements link
    Then Systems Requirements modal box is displayed

    Examples: 
      | accucode | PSC User Login Credentials |  | PL User Login Credentials |
      | Inst-AF  | 1SAFT& testing1            |  | 1AAF & testing1           |

  Scenario Outline: 
    Given user is in Plan Web using https://proj6-plan.empower-retirement.com/planweb/?accu=InstAFCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Security link
    Then Security modal box is displayed

    Examples: 
      | accucode | PSC User Login Credentials |  | PL User Login Credentials |
      | Inst-AF  | 1SAFT& testing1            |  | 1AAF & testing1           |

  Scenario Outline: 
    Given user is in Plan Web using https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Contact Us link
    Then Contact Us modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on FAQ link
    Then FAQ  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Security Guarantee link
    Then Security Guarantee  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Security Tips link
    Then Security Tips  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on System Requirements link
    Then System Requirements  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Privacy link
    Then Privacy  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Terms and Conditions link
    Then Terms and Conditions  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Business Continuity Plan link
    Then Business Continuity Plan  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Broker Check Notification link
    Then Broker Check Notification  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Market Timing and Excessive Trading Policies link
    Then Market Timing and Excessive Trading Policies  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |

  Scenario Outline: 
    Given user is in Plan Web using  https://proj6-plan.empower-retirement.com/planweb/?accu=PlanEmpowerCR#/login
    When user is on pre-login page for <accucode>
    And user clicks on Fund Prospectuses link
    Then Fund Prospectuses  modal box is displayed

    Examples: 
      | accucode     | PSC User Login Credentials |
      | Plan-Empower | 2ser & testing1            |
      | Plan-Apple   | 1SAFT& testing1            |
