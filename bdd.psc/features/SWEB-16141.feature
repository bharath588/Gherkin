#Author: your.email@your.domain.com
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
@SWEB-16141
Feature: Plan DBMA NEE custom site

Scenario: User login to DMBA site with valid credentials and with transaction code PSCSF2 assigned to their access
Given user is on the Home page of DMBA when user login with correct username and password
      |accuCode| username| password|
      |PlanDMBA| 4qas0   | Testing1| 
Then GWC DMBA connect page is displayed
And username is displayed on the top right hand corner of the site

Scenario: User login to DMBA site with valid credentials and with NO transaction code PSCSF2 assigned to their access
Given user is on the Home page of accuCode when user login with correct username and password
      |accuCode| username| password|
      |PlanDMBA| 1pdmba   | testing1| 
Then PSC Home screen is displayed

Scenario: User login to DMBA site with invalid password
Given user is on the Login page of "PlanDMBA"
When I enter valid username and invalid password
| username               | password            |
| 4qas0                  | testing123          |
Then Error message is displayed

Scenario: User login to DMBA site with invalid username
Given user is on the Login page of "PlanDMBA"
When I enter invalid username and invalid password
| username               | password            |
| zdmba                  | zzzzzzzz            |
Then Error message is displayed


















