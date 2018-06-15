#Author: Matteo
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
# (Comments)
#Sample Feature Definition Template
@SWEB-18520
Feature: InstMetLife UI changes for plan upgrade to Nextgen: MetLife SR# 04943713
  Narrative Description : As a PSC user, when I use the MetLife site, I should be I see the the following changes

  Scenario Outline: Verify pre-login Contact Us information provides the correct information
    Given user is on the Login page of "<accucode>"
    When user scroll down to footer section
    Then the "<Contact_Us>" section should be displayed in pre login

    Examples: 
      | accucode     | Contact_Us                                                                                                                            |
      | InstAF       | 'Contact us (877) 872-5159'                                                                                                           |
      | InstJPM      | 'Representatives are available weekdays from 8:30 am – 7:00 pm EST(855) 576-5465'                                                     |
      | InstSunTrust | 'To speak with a representative, please contact us between 8:30 a.m. and 7 p.m. ET.'                                                  |
      | InstFTB      | 'If you have any questions please call Website Support at (866) 827-2211. Website Support is available from 8:30 am until 8:00 pm ET. |
      | PlanApple    | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanEmpower  | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanJPM      | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanDMBA     | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanMiss     | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanMN       | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | InstMetLife  | 'Plan sponsors needing website support can call 1-800-856-7772, select option 1, Monday through Friday, 9:00 a.m. to 7:00 p.m. ET.'   |

  Scenario Outline: Verify post-login Contact Us information provides the correct information
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user scroll down to footer section
    Then the "<Contact_Us>" section should be displayed

    Examples: 
      | username | password | accucode     | Contact_Us                                                                                                                            |
      | 1AF      | testing1 | InstAF       | 'Contact us (877) 872-5159'                                                                                                           |
      | 1IJPM    | testing1 | InstJPM      | 'Representatives are available weekdays from 8:30 am – 7:00 pm EST. (855) 576-5465'                                                   |
      | 1IST     | testing1 | InstSunTrust | 'To speak with a representative, please contact us between 8:30 a.m. to 7:00 p.m., ET.'                                               |
      | 1FTB     | testing1 | InstFTB      | 'If you have any questions please call Website Support at (866) 827-2211. Website Support is available from 8:30 am until 8:00 pm ET. |
      | 3VFO3    | testing1 | PlanApple    | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | 1ISIS    | testing1 | PlanEmpower  | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | 3JPM     | testing1 | PlanJPM      | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | 1PDMBA   | testing1 | PlanDMBA     | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | 2MISS    | testing1 | PlanMiss     | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | 1MN      | testing1 | PlanMN       | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | 1IML     | testing1 | InstMetLife  | 'Plan sponsors needing website support can call 1-800-856-7772, select option 1, Monday through Friday, 9:00 a.m. to 7:00 p.m. ET.'   |

  Scenario Outline: Verify 'Forgot Password' page Contact Us information provides the correct information
    Given user is on the Forgot Password page of "<accucode>"
    When user scroll down to footer section
    Then the "<Contact_Us>" section should be displayed

    Examples: 
      | accucode     | Contact_Us                                                                                                                            |
      | InstAF       | 'Contact us (877) 872-5159'                                                                                                           |
      | InstJPM      | 'Representatives are available weekdays from 8:30 am – 7:00 pm EST. (855) 576-5465'                                                   |
      | InstSunTrust | 'To speak with a representative, please contact us between 8:30 a.m. to 7:00 p.m., ET.'                                               |
      | InstFTB      | 'If you have any questions please call Website Support at (866) 827-2211. Website Support is available from 8:30 am until 8:00 pm ET. |
      | PlanApple    | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanEmpower  | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanJPM      | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanDMBA     | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanMiss     | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | PlanMN       | 'To speak with a representative, please contact us between 8:30 a.m. and 8 p.m. ET.'                                                  |
      | InstMetLife  | 'Plan sponsors needing website support can call 1-800-856-7772, select option 1, Monday through Friday, 9:00 a.m. to 7:00 p.m. ET.'   |

  Scenario Outline: Verify post-login 'Fixed rate' tab has the correct label
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user is on "<Investments>" page
    Then the "<Fixed_Rate>" tab should be displayed

    Examples: 
      | plan_no    | accucode     | username | password | Fixed_Rate             | Investments               |
      | 344692-01  | InstAF       | 2AF      | testing1 | Fixed investment rates | Investments & Results     |
      | 168154-01  | InstJPM      | 1IJPM    | testing1 | Fixed investment rates | Investments & Performance |
      | 250366-01  | InstSunTrust | 1IST     | testing1 | Fixed investment rates | Investments & Performance |
      | 453000-07  | InstFTB      | 1FTB     | testing1 | Fixed investment rates | Investments & Performance |
      | 150550-01  | PlanApple    | 3VFO3    | testing1 | Fixed investment rates | Investments & Performance |
      | 932777-01  | PlanEmpower  | 1ISIS    | testing1 | Fixed investment rates | Investments & Performance |
      #| #######-01 | PlanJPM      | 3JPM     | testing1 | Fixed investment rates | Investments & Performance |
      | 385074-01  | PlanDMBA     | 1PDMBA   | testing1 | Fixed investment rates | Investments & Performance |
      | 98949-01   | PlanMiss     | 2MISS    | testing1 | Fixed investment rates | Investments & Performance |
      #| #######-01 | PlanMN       | 1MN      | testing1 | Fixed investment rates | Investments & Performance |
      | 1005120-01 | InstMetLife  | 1IML     | testing1 | Fixed Account Rates    | Investments & Performance |

  Scenario Outline: Verify post-login 'Fixed rate' tab has the correct label
    Given user is on the Home page of "<accucode>" when user login with correct "<username>" and "<password>"
    When user switches to "<plan_no>"
    And user is on "<Investments>" page
    And user is on "<Fixed_Rate>" tab
    Then the "<Interest_rate>" label should be displayed

    Examples: 
      | plan_no    | accucode    | username | password | Fixed_Rate             | Investments               | Interest_rate    |
      #| #######-01 | InstAF       | 1AF      | testing1 | Fixed investment rates | Investments & Results     | Interest Rate    |
      #| #######-01 | InstJPM      | 1IJPM    | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      #| #######-01 | InstSunTrust |          | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      #| #######-01 | InstFTB      |          | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      | 150550-01  | PlanApple   | 3VFO3    | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      | 932777-01  | PlanEmpower | 1ISIS    | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      #| #######-01 | PlanJPM      |          | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      #| #######-01 | PlanDMBA     |          | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      #| #######-01 | PlanMiss     |          | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      #| #######-01 | PlanMN       |          | testing1 | Fixed investment rates | Investments & Performance | Interest Rate    |
      | 1005120-01 | InstMetLife | 1IML     | testing1 | Fixed account rates    | Investments & Performance | Interest Rate *% |
