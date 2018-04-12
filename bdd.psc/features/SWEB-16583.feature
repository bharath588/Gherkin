#Author:
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
@SWEB-16583
Feature: PSC social media icons

Scenario Outline: Verify the Social Media icons are displayed on PSC login page with correct content and order
Given user is on the Login page of "<accuCode>"
When user scroll down to footer section
Then the Social Media "<icons>" are displayed in correct order
And User clicks on the "<icons>"
Then A new tab opens with "<url>"

Examples: 
      | accuCode    | icons                                       |url                                                                                                                                                             |
      | PlanEmpower | Facebook,Twitter,Linkedin,Instagram,YouTube |https://www.facebook.com/empowerretirement,https://twitter.com/@empowertoday,https://www.linkedin.com/company/empower-retirement/,https://www.instagram.com/empowerretirement/,https://www.youtube.com/channel/UCFPLlGp16vPjBb-G7SnUWhQ|
 
Scenario Outline: Verify the Social Media icons are displayed on PSC home page with correct order and includes the correct link     
 Given user is on the Login page of "<accuCode>"
 When user enters "<username>" and "<password>" and clicks on sign in button
 And user scroll down to footer section
 Then the Social Media "<icons>" are displayed in correct order
 And User clicks on the "<icons>"
 Then A new tab opens with "<url>"

 Examples: 
      | accuCode   |username | password | icons                                       | url                                                                                                                                         |
      | PlanEmpower| 1isis   | testing1 | Facebook,Twitter,Linkedin,Instagram,YouTube |https://www.facebook.com/empowerretirement,https://twitter.com/empowertoday,https://www.linkedin.com/company/empower-retirement/,https://www.instagram.com/empowerretirement/,https://www.youtube.com/channel/UCFPLlGp16vPjBb-G7SnUWhQ|
      



