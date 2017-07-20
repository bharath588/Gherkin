Feature: Test Facebook smoke scenario
  Scenario Outline: Test open web page and login with valid details
    Given Open Chrome and start application using "<url>"
    When I enter valid "<accountnumber>" and valid "<password>"
    Then Click on login buttton user should able to login successfully

    Given user is on web account maintenance home page
    When profile button is visible
    Then Click on profile button

    Given User is on profile page
    When username input box appears
    Then Update the "<newusername>"

    Examples:
    | accountnumber | password |url|newusername |
    | 32626758  | Rahul1122  |  http://138.69.165.241/vector/account/home/accountLogin.do     | Abcd123|

