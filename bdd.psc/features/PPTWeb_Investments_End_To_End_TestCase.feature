Feature: Verify Future Allocations After Changing Investment Options

  @Login
  Scenario: Login in to Application
    Given Launch PPTWEB application
    When I enter valid username and password
    And click on Sign On button
    And I Enter Verification Code
    Then I should be naviagted to Home page

  @LeftNav
  Scenario: Navigate To My Investments Page
    Given I am on Home Page
    When I click on MyAccount Tab
    And I click on View/Managemyinvestments link
    Then Application navigates to My Investments Page

  @ChooseIndividualFunds
  Scenario: Change Future Allocations
    Given I am on My Investments page
    When I click on ChangeMyInvestments link
    And I select Change how my future contributions will be invested Option
    And I select Choose Individual Funds
    And Submit the Changes
    Then I Navigate to Confirmation Page
    And I can Verify the Investment Submittion Time and Cofirmation No
    

  @VerifyUpdatedIndividualFunds
  Scenario: Verify Future Allocations
   Given I am on My Investments page
   Then I can verify the updated Investment Options in the Future Allocation Section

    @ChooseTargerDateFunds
  Scenario: Change Future Allocations
    Given I am on My Investments page
    When I click on ChangeMyInvestments link
    And I select Change how my future contributions will be invested Option
    And I select Target Date Funds
    And Submit the Changes
    Then I Navigate to Confirmation Page
    And I can Verify the Investment Submittion Time and Cofirmation No

  @VerifyUpdatedTargetDateFunds
  Scenario: Verify Future Allocations
    Given I am on My Investments page
    Then I can verify the updated Investment Options in the Future Allocation Section
   
    