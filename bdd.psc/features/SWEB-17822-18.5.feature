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
@SWEB-17822
Feature: American Funds Plan Express to Differentiate Frozen money types
  Narrative Description : As an American Funds institutional Partner I want Plan Express Differentiate Frozen money types when the plan is loaded

  Scenario Outline: Verify GRP_DEF_MNTY.CLASSIFICATION_CODE is appended with 'FZ' when a frozen money type is selected for American funds plan set-up via Plan Express
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user selects PartnerLink>Implementation>Plan Express
    And selects option 2 Complete Plan Data
    And selects a plan "<GA_ID>" to complete from list provided
    And creates a Frozen money type on pages 5400-5430 using "<Money_Type>" , "<Descr>" and "<Type_Descr>"
    And selects Create forms and update recordkeeping system from Implementation Checklist for that plan
    Then PXIS runs in background and updates GRP_DEF_MNTY.CLASSIFICATION_CODE is appended with FZ for the money types marked as Frozen money type on pages 5400-5430 and can be verified with sql for "<GA_ID>","<Classification_Code>","<Descr>"
      """
      Select * from grp_def_mnty 
      where ga_id = ? and CLASSIFICATION_CODE = ? and DESCR = ?
      """

    Examples: 
      | accuCode | username | password | GA_ID      | Classification_Code  | Descr                          | Money_Type                   |Type_Descr|
      | InstAF   | 2af      | testing1 | 345320-01  | DISCRETNARY MATCH FZ | EMPLOYER MATCH 1               | EMPLOYER MATCH               |EMPLOYER MATCH 2|
      | InstAF   | 2af      | testing1 | 345320-01  | MONEYPURCHASE FZ     | EMPLOYER MONEY PURCHASE 1      | EMPLOYER MONEY PURCHASE      | EMPLOYER MONEY PURCHASE 2|
      | InstAF   | 2af      | testing1 | 345320-01  | PROFITSHARING FZ     | EMPLOYER PROFIT SHARING 1      | EMPLOYER PROFIT SHARING      |EMPLOYER PROFIT SHARING 2|
      | InstAF   | 2af      | testing1 | 345320-01  | SAFE HARBOR MATCH FZ | SAFE HARBOR MATCH 1            | SAFE HARBOR MATCH            |SAFE HARBOR MATCH 2|   
      | InstAF   | 2af      | testing1 | 1355138-01 | SAFEHRBR NON-ELEC FZ | SAFE HARBOR NON ELECTVE 1      | SAFE HARBOR NON-ELECTIVE      |SAFE HARBOR NON ELECTVE 2|
      | InstAF   | 2af      | testing1 | 1355138-01 | NONELECTIVE FZ       | QACA SAFE HARBOR NON ELECTVE 1 | QACA SAFE HARBOR NON ELECTIVE |QACA SAFE HARBOR NON ELECTVE 2|
      | InstAF   | 2af      | testing1 | 1355138-01 | MATCH FZ             | QACA SAFE HARBOR MATCH 1       | QACA SAFE HARBOR MATCH       |QACA SAFE HARBOR MATCH 2| 
