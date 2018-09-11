# Keywords Summary :															 															  | Team lead: Silvio Nunes
# Feature: List of scenarios 													 													  | DEV:
# Scenario: Business rule through list of steps with arguments 					 				  | BA: Connor Swatling
# Given: Some precondition step                                                   | QA: Bindu Subbireddy
# When: Some key actions                                                          | Application: PSC
# Then: To observe outcomes or validation                                         | User type:
# And, But: To enumerate more Given, When, Then steps                             | User category: Internal/External
# Scenario Outline: List of steps for data-driven as an Example and <placeholder> | Sites: Empower
# Background: List of steps run before each of the scenario                       |
# """ (Doc String)																 																|
# | (Data tables)                                                                 |
# @ (Tags/Labels): To group Scenarios											  											|
# <> (placeholder)																 																|
# ""                                                                              |
# # (Comments)																																		|
# Sample Feature Definition Template                                              |
#-----------------------------------------------------------------------------------------------------------------------
@SWEB-18868
Feature: As a xDB user, I need my menu choices to change according to my plan selection

  Scenario Outline: Verify that menu choices do NOT change when plan is changed
    Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 7KPKI    | testing1 |
    And User is on Employee Search Page
    When "<user>" has retrived "<plan>" from "<DB>" and selected from the top right drop-down list
    Then "<user>" related "<menu_items>" should display

    Examples: 
      | user  | plan|menu_items                             | DB     |
      | 7KPKI | select GA_ID from user_data_access where user_logon_id='K_7KPKI'|Employees,Search employee,Add employee | D_ISIS |
      | 7KPKI | select GA_ID from user_data_access where user_logon_id='K_7KPKI'|Employees,Search employee,Add employee | D_IN02 | 
