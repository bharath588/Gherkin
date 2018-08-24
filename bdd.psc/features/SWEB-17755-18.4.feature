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
@SWEB-17755
Feature: American Funds Plan Express Implementation checklist buttons rename
  Narrative Description : As an American Funds institutional Partner I want 2 buttons renamed on Implementation Checklist page

  Scenario Outline: on Implementation Checklist page Buttons are renamed: “Create Enrollment Book and Plan Installation Kit” to “Create Plan Installation Kit”, 
   “Create Forms and Update Recordkeeping System” to “Create Enrollment Materials / Forms and Update Recordkeeping System”

    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    When user selects PartnerLink>Implementation>Plan Express
    And selects option 2 Complete Plan Data
    And selects a plan "<GA_ID>" to complete from list provided 
    Then user is presented Implementation Checklist page
		And below page 6200 line item on that page the "<tabs>" are presented
		
    Examples: 
      | accuCode    | username | password |  GA_ID        | tabs                                                                                                                                              |
      | InstAF      | 2af      | testing1 |   345328-01   | Create Draft Plan Installation Kit,Create Plan Installation Kit,Create Enrollment Materials / Forms and Update Recordkeeping System |
      


