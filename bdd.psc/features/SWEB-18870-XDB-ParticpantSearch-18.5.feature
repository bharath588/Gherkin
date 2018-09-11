# Keywords Summary :															                                | Team lead: Silvio Nunes
# Feature: List of scenarios 													                            | DEV: Liyang Zhang
# Scenario: Business rule through list of steps with arguments 					          | BA: Connor Swatling
# Given: Some precondition step                                                   | QA: Bindu Subbireddy
# When: Some key actions                                                          | Application: PSC
# Then: To observe outcomes or validation                                         | User type:
# And, But: To enumerate more Given, When, Then steps                             | User category: Internal/External
# Scenario Outline: List of steps for data-driven as an Example and <placeholder> | Sites: Empower
# Background: List of steps run before each of the scenario                       |
# """ (Doc String)																                                |
# | (Data tables)                                                                 |
# @ (Tags/Labels): To group Scenarios											                        |
# <> (placeholder)																                                |
# ""                                                                              |
# # (Comments)																	                                  |
# Sample Feature Definition Template                                              |
#-----------------------------------------------------------------------------------------------------------------------
@SWEB-18870
Feature: As a xDB user, when I search for participants I want to see one instance of each participant

  Scenario Outline: Verify that unique results are returned in Employee Search
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 7KPKI    | testing1 |
    And User is on Employee Search Page
    When 'user' selects "<search criteria>" in Employee Search and clicks on 'Go'
    Then table should populate with employees
    And no duplicate results should be returned across all pages

    Examples: 
      | search criteria |
      | Name            |
      | SSN             |
      | Employee ID     |
      | Participant ID  |
      | Division        |

  Scenario Outline: Verify that unique results are returned in Employee Search
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 799S3    | testing1 |
    And User is on Employee Search Page
    When 'user' selects "<search criteria>" in Employee Search and clicks on 'Go'
    Then table should populate with employees
    And no duplicate results should be returned across all pages

    Examples: 
      | search criteria |
      | Name            |
      | SSN             |
      | Employee ID     |
      | Participant ID  |

  Scenario Outline: Verify that unique results are returned in Employee Search for division
    When user switches to "<plan_no>"
    And User is on Employee Search Page
    And 'user' selects "<search criteria>" in Employee Search and clicks on 'Go'
    Then table should populate with employees
    And no duplicate results should be returned across all pages

    Examples: 
      | search criteria | plan_no   |
      | Division        | 932777-01 |
