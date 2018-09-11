#Author:
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
@SWEB-16399
Feature: Establish a TRS Flex Core market product and a TRS Flex Empower HSA Core market product through Plan Express

  Scenario: New Plan creation when user selects Partner drop down as TRS Flex Core
    Given user is on the PartnerLink Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 1pet     | test11   |
    When Pl user selects Implementation/Plan Express from the main nav
    And user selects 1 Plan Pipeline Data
    And User selects Partner drop down
    Then "TRS Flex Core (TRSCRE)" appear in drop down list

  Scenario: Product code and providing company is assigned for TRS Flex Core
    Given User selects "TRS Flex Core (TRSCRE)" from partner drop down on Add plan page 1
    When User selects TRS Flex Core from Partner drop down and selects Save
    Then Product code is stored as TRSFLEXCRE and providing company is stored as "TRSCRE" and plan range allowed is "1" to "999999" and state situs is all available states
      """
      select * from ISIS_REF_VALUES WHERE RV_table  = 'GROUP_CLIENT' and RV_TYPE ='TRSCRE'
      """

  Scenario: Plan type TRS is only available plan type
    Given User is on Edit Plan page 2 and selected TRS Flex Core as partner
    When User selects Plan type drop down
    Then "TRS" appears in drop down list as only option selectable

  Scenario: Plan type is assigned
    Given User selects TRS from plan type drop down on Edit Plan page 2
    When User selects TRS from plan type drop down and selects Save
    Then Plan type is stored as TRS

  Scenario: Data is saved also to Database
    When User selects Cancel on Edit Plan page 2
    Then Plan is created also in Database
      """
      SELECT * FROM prospective_plan WHERE ga_id =? 
      """

  Scenario: New questions and selection for TRS Flex Core market product
    Given User is on "Implementation Checklist" Page
    Then new page "320: TRS Flex and Empower HSA" is presented in Implementation Checklist

  Scenario: TRS or HSA Plan Types drop down values includes
    When User selects partner of TRS Flex Core
    Then new page 320 displays drop down values for TRS_HSA_Plan_Types
      | TRS_HSA_Plan_Types                              |
      | Defined Benefit Final Average Pay               |
      | Defined Benefit Cash Balance                    |
      | Non-Qualified Defined Benefit Final Average Pay |
      | Non-Qualified Defined Benefit Cash Balance      |
      | Non-Qualified Defined Contribution              |
      | Qualified Defined Contribution                  |
      | Stock Option - Computershare                    |
      | Restricted Stock - Computershare                |
      | Stock Purchase/ESPP - Computershare             |
      | Health Savings Plan                             |
      | Retirement Health &Welfare(Account-based)       |
      | Retirement Health &Welfare(Annuity-based)       |
    And new 320 page is presented and presents "Include TRS Account Balance on Statements?" option with Yes No radio button to select response
    And new 320 page is presented and presents "Include TRS or HSA Account Balance in Retirement Income Projections?" option with Yes No radio button to select response

  Scenario Outline:
  	Given user is on the PartnerLink Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 1pet     | test11   | 
    When Pl user selects Implementation/Plan Express from the main nav
    And selects option 2 Complete Plan Data
    And User clicks on new plan and selects "320: TRS Flex and Empower HSA" page
    And User selects "<TRS HSA Plan Type>" selects Save & Return
    And User provide Plan sponsor EIN in "2700: Plan Sponsor" page
    Then click on Create Forms and Update Recordkeeping and validate in DB using query
      """
      select * from ga_service  where ga_id = ?
      """ 
    And Group Level Service Rule is saved with data in DB
      """
       select * from std_service where subcode = 'ANNUITY'
       """ 
    Examples: 
      | TRS HSA Plan Type                               |
      | Defined Benefit Final Average Pay               |
      | Defined Benefit Cash Balance                    |
      | Non-Qualified Defined Benefit Final Average Pay |
      | Non-Qualified Defined Benefit Cash Balance      |
      | Non-Qualified Defined Contribution              |
      | Qualified Defined Contribution                  |
      | Stock Option - Computershare                    |
      | Restricted Stock - Computershare                |
      | Stock Purchase/ESPP - Computershare             |
      | Health Savings Plan                             |
      | Retirement Health & Welfare (Account-based)       |
      | Retirement Health & Welfare (Annuity-based)       |

  Scenario: New Plan creation when user selects Partner drop down as TRS Flex Empower HSA
  Given user is on the PartnerLink Home page of accuCode when user login with correct username and password
      | accuCode    | username | password |
      | PlanEmpower | 1pet     | test11   | 
    When Pl user selects Implementation/Plan Express from the main nav
    And user selects 1 Plan Pipeline Data
    And User selects Partner drop down
    Then "TRS Flex Empower HSA (TRSOPT)" appear in drop down list

  Scenario: Product code and providing company is assigned for TRS Flex Empower HSA
    Given User selects "TRS Flex Empower HSA (TRSOPT)" from partner drop down on Add plan page 1
    When User selects TRS Flex Empower HSA from Partner drop down and selects Save
    Then Product code is stored as TRSFLEXOPT and providing company is stored as "TRSOPT" and plan range allowed is "1" to "999999" and state situs is all available states
			"""
      select * from ISIS_REF_VALUES WHERE RV_table  = 'GROUP_CLIENT' and RV_TYPE ='TRSOPT'
      """
      
  Scenario: Plan type TRS is only available plan type
    Given User is on Edit Plan page 2 and selected TRS Flex Empower HSA as partner
    When User selects Plan type drop down
    Then "TRS" appears in drop down list as only option selectable

  Scenario: Plan type is assigned
    Given User selects TRS from plan type drop down on Edit Plan page 2
    When User selects TRS Flex from plan type drop down and selects Save
    Then Plan type is stored as TRS

  Scenario: Data is saved also to Database
    When User selects Cancel on Edit Plan page 2
    Then Plan is created also in Database
       """
      SELECT * FROM prospective_plan WHERE ga_id =? 
      """
      
  Scenario: New questions and selection for a TRS Flex Empower HSA Core market product
    When User is on "Implementation Checklist" Page
    Then new page "320: TRS Flex and Empower HSA" is presented in Implementation Checklist

  Scenario Outline: 
    When User selects "<TRS HSA Plan Type>" selects Save & Return
    Then User provide Plan sponsor EIN in "2700: Plan Sponsor" page
   Then click on Create Forms and Update Recordkeeping and validate in DB using query
      """
      select * from ga_service  where ga_id = ?
      """ 
    And Group Level Service Rule is saved with data in DB
      """
       select * from std_service where subcode = 'ANNUITY'
       """
    Examples: 
      | TRS HSA Plan Type    |
      | TRS Flex Empower HSA |
      
      
      
      
