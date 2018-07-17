# Keywords Summary :															                                | Team lead: Silvio Nunes
# Feature: List of scenarios 													                            | DEV: Liyang Zhang
# Scenario: Business rule through list of steps with arguments 					          | BA: Connor Swatling
# Given: Some precondition step                                                   | QA: Bindu Subbireddy
# When: Some key actions                                                          | Application: PSC
# Then: To observe outcomes or validation                                         | User type:
# And, But: To enumerate more Given, When, Then steps                             | User category: Internal/External
# Scenario Outline: List of steps for data-driven as an Example and <placeholder> | Sites: Empower
# Background: List of steps run before each of the scenario                       |
# """ (Doc String)																                                |
# | (Data tables)                                                                 |
# @ (Tags/Labels): To group Scenarios											                        |
# <> (placeholder)																                                |
# ""                                                                              |
# # (Comments)																	                                  |
# Sample Feature Definition Template                                              |
#-----------------------------------------------------------------------------------------------------------------------
@SWEB-18872
Feature: As a xDB user, when I upload Employee Data File, I should see a message

  Scenario Outline: Verify that confirmation message appears when uploading a file as a xDB user
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And navigated to Process center/Transmit your payroll file/Upload payroll file
    And has a file with a file name and format as one of the authorized file names listed
    When user uploads an Employee data file
    Then confirmation message should appear: "The following file(s) have been successfully uploaded:"
    And the file name should match the name of the file that was uploaded

    Examples: 
      | accuCode    | username | password |
      | PlanEmpower | 7KPKI    | testing1 |
      #| PlanEmpower | 7QT7C    | testing1 |

  Scenario Outline: Verify that uploaded file is rejected if it does not match authorized File Name list
    Given user is on the Home page of "<accuCode>" when user login with correct "<username>" and "<password>"
    And navigated to Process center/Transmit your payroll file/Upload payroll file
    And has a file with a file name and format that does NOT match one of the authorized file names listed
    When user uploads an invalid Employee data file
    Then the upload should not proceed
    And a message should be displayed: "You are trying to upload a file, <file name> , which is not on the authorized list."

    Examples: 
      | accuCode    | username | password |
      | PlanEmpower | 7KPKI    | testing1 |
      #| PlanEmpower | 7QT7C    | testing1 |
