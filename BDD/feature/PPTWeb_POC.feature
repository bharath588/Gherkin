Feature: WithDrawal

Scenario: Test Withdrawal Page
	Given Open firefox and start ppt web application 
	When I enter valid username and password
	And Click on Request a Withdrawal link 
	Then User should be navigated to Withdrawals Page successfully
	
	Scenario: Scenario2
	Given Open firefox and start ppt web application 
	When I enter valid username and password
	And Click on Request a Withdrawal link 
	Then User should be navigated to Withdrawals Page successfully