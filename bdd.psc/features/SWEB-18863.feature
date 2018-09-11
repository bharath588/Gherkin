# Keywords Summary :															  															| Team lead: Silvio Nunes
# Feature: List of scenarios 													 												 		| DEV: 
# Scenario: Business rule through list of steps with arguments 					 					| BA: Connor Swatling
# Given: Some precondition step                                                   | QA: Bindu Subbireddy
# When: Some key actions                                                          | Application: PSC
# Then: To observe outcomes or validation                                         | User type: 
# And, But: To enumerate more Given, When, Then steps                             | User category: Internal/External
# Scenario Outline: List of steps for data-driven as an Example and <placeholder> | Sites: Empower
# Background: List of steps run before each of the scenario                       |       
# """ (Doc String)																															  |
# | (Data tables)                                                                 |
# @ (Tags/Labels): To group Scenarios											  											|
# <> (placeholder)																  															|
# ""                                                                              |
# # (Comments)																	  																|
# Sample Feature Definition Template                                              |
#-----------------------------------------------------------------------------------------------------------------------
@SWEB-18863
Feature: As a PSC xDB user, I need to see plans from all databases when choosing my default plan

  Scenario Outline: Verify all of a PSC users plans, across all databases the user has access to, appear when selecting a default plan from My Profile
   Given user is on the Home page of accuCode when user login with correct username and password
      | accuCode    | username | password | 
      | PlanEmpower | 7KPKI    | testing1 | 
     
     And "<user>" has "<plans>" on multiple "<databases>"
     When "<user>" navigates to the plan list in My Profile
     Then "<plans>" on multiple "<databases>" will be available for selection
  
    Examples: 
      | user  | plans                                                            | databases | 
      | 7KPKI | select GA_ID from user_data_access where user_logon_id='K_7KPKI' | D_ISIS    | 
   #   | 7KPKI | select GA_ID from user_data_access where user_logon_id='K_7KPKI' | D_IN02    | 
  