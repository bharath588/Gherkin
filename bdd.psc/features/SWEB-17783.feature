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
@SWEB-17783
Feature: American Funds PSC Enhanced Enrollment - tabs renamed to "Enrollment" and "Employer Contributions"

  Narrative Description : As an American Funds PSC user I want the tabs renamed to "Enrollment" and "Employer Contributions" on Plan Provisions page

  Scenario Outline: Verify "<tabs>" are included as tabs for the "<accucode>" site at Plan Provisions page
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user navigates to Plan and then "Overview" tabs and click on "Plan Provisions" page 
    Then "<tabs>" are displayed as the tabs

    Examples: 
      | accucode    | username |  password | tabs                                                                     |
      | InstAF      | 1af     |  testing1   | Enrollment,Deferrals,Employer Contributions,Eligibility,Loan information |
      

  