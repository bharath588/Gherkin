#Author: Lydia Bauer, Connor Swatling, Silvio Nunes, Amir Abdullaev
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
Feature: FDD Consistency
  
  Narrative: As a user, I want a consistent datepicker experience based on what dates I have available in my 401(k) plan

  Scenario Outline: As a user, I want a consistent datepicker experience based on dropdown datepicker for "<deferral_type>"
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And plan is set up with "<sdsv subcode>" in GA_Service table
      """
      select ga_id from ga_service where SDSV_SUBCODE=?
      """
    When user switched to "<plan_no>" and navigate to Deferral Contribution screen for Future Dated Ongoing Deferrals
    Then user should see a "dropdown" datepicker for "<deferral_type>"

    Examples: 
      | accuCode    | username | password | sdsv subcode       | plan_no   | deferral_type |
      | PlanEmpower | 1INST    | testing1 | ADJRUN_PAYROLLDATE | 194000-01 | Before Tax    |
     # | PlanEmpower | 1INST    | testing1 | ADJRUNDATE         | 194000-01 | Before Tax    |
      #| PlanEmpower | 1INST    | testing1 | GENERIC            | 194000-01 | Before Tax    |