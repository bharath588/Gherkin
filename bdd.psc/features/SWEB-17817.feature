#Author: rvpndy
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
@SWEB-17817
Feature: American Funds Plan Express create BENE IN BOOK GA service rule

  #Narrative Description: As an American Funds institutional Partner I want Plan Express to capture data so the New daily XML file feed can report Beneficiary docs to be included with enrolment kit
  Scenario Outline: 
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user selects Implementation / Plan Express from the main nav
    And selects option 2 Complete Plan Data
    And selects a "<GA_ID>" to complete from list provided
    And on page 3600: Enrollment Kits, answers Should a beneficiary form be included in the enrollment books?: as "Yes"
    And selects Save & Return
    And selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then created plan data can be verified with sql below
      """
      Select * from GA_SERVICE 
      where ga_id = ?
      and SDSV_CODE = 'BENE_IN_BOOK'
      and SDSV_SUBCODE = 'AMERICAN FUNDS'
      and EFFDATE < system date
      and TERMDATE is null
      """

    Examples: 
      | accuCode | username | password | GA_ID     |
      | InstAF   | 2af      | testing1 | 345320-01 |
