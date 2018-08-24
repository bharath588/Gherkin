# Keywords Summary :															  | Team lead: Silvio Nunes
# Feature: List of scenarios 													  | DEV: Liyang Zhang
# Scenario: Business rule through list of steps with arguments 					  | BA: Connor Swatling
# Given: Some precondition step                                                   | QA: Bindu Subbireddy
# When: Some key actions                                                          | Application: PSC
# Then: To observe outcomes or validation                                         | User type:
# And, But: To enumerate more Given, When, Then steps                             | User category: Internal/External
# Scenario Outline: List of steps for data-driven as an Example and <placeholder> | Sites: Empower
# Background: List of steps run before each of the scenario                       |
# """ (Doc String)																  |
# | (Data tables)                                                                 |
# @ (Tags/Labels): To group Scenarios											  |
# <> (placeholder)																  |
# ""                                                                              |
# # (Comments)																	  |
# Sample Feature Definition Template                                              |
@SWEB-18879
Feature: As a xDB user, when I log in I should see the login date and time

  Scenario Outline: Verify that the login date and time match the date of the last user login
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And user has logged out of their account
    When user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    Then the Last login field should display the previous login time and in UI date and time should be displayed in Eastern time and LAST_LOGIN_DATE_TIME in USERS table should be saved in Mountain time across all databases with "<DBname>" and "<username>" the User has access to
      """
      select TO_CHAR(LAST_LOGIN_DATE_TIME, 'MM/DD/YYYY hh AM') as LASTDATE from Users where logon_id=? 
      """

    Examples: 
      | accuCode    | username | password | DB              | DBname                |
      # | PlanEmpower | 7HAC4    | testing1 | PSC - Single    | ISIS                  |
      | PlanEmpower | 7KPKI    | testing1 | PSC - Double    | ISIS, IN02            |
      | PlanEmpower | 799S3    | testing1 | PSC - Quadruple | ISIS, INST, IN02, PNP |
      #  | PlanEmpower | 7PKN4    | testing1 | PL - Single     | ISIS                 |
      | PlanEmpower | 7MV0M    | testing1 | PL - Double     | ISIS, IN02            |
      | PlanEmpower | 7BFIZ    | testing1 | PL - Quadruple  | ISIS, INST, IN02, PNP |
