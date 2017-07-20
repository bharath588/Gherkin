$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("MyApplication.feature");
formatter.feature({
  "line": 1,
  "name": "Test Facebook smoke scenario",
  "description": "",
  "id": "test-facebook-smoke-scenario",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 2,
  "name": "Test Login with valid Credentials",
  "description": "",
  "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials",
  "type": "scenario_outline",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 3,
  "name": "Open Chrome and start application",
  "keyword": "Given "
});
formatter.step({
  "line": 4,
  "name": "I enter valid \"\u003cusername\u003e\" and valid \"\u003cpassword\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 5,
  "name": "user should able to login successfully",
  "keyword": "Then "
});
formatter.examples({
  "line": 7,
  "name": "",
  "description": "",
  "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;",
  "rows": [
    {
      "cells": [
        "username",
        "password"
      ],
      "line": 8,
      "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;;1"
    },
    {
      "cells": [
        "bharath",
        "bharath"
      ],
      "line": 9,
      "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;;2"
    },
    {
      "cells": [
        "bharath1",
        "bharath1"
      ],
      "line": 10,
      "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;;3"
    },
    {
      "cells": [
        "bharath2",
        "bharath2"
      ],
      "line": 11,
      "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;;4"
    }
  ],
  "keyword": "Examples"
});
formatter.scenario({
  "line": 9,
  "name": "Test Login with valid Credentials",
  "description": "",
  "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;;2",
  "type": "scenario",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 3,
  "name": "Open Chrome and start application",
  "keyword": "Given "
});
formatter.step({
  "line": 4,
  "name": "I enter valid \"bharath\" and valid \"bharath\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 5,
  "name": "user should able to login successfully",
  "keyword": "Then "
});
formatter.match({
  "location": "SmokeTest.open_Chrome_and_start_application()"
});
formatter.result({
  "duration": 15754082979,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "bharath",
      "offset": 15
    },
    {
      "val": "bharath",
      "offset": 35
    }
  ],
  "location": "SmokeTest.i_enter_valid_and_valid(String,String)"
});
formatter.result({
  "duration": 7820288158,
  "status": "passed"
});
formatter.match({
  "location": "SmokeTest.user_should_able_to_login_successfully()"
});
formatter.result({
  "duration": 5935618951,
  "status": "passed"
});
formatter.scenario({
  "line": 10,
  "name": "Test Login with valid Credentials",
  "description": "",
  "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;;3",
  "type": "scenario",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 3,
  "name": "Open Chrome and start application",
  "keyword": "Given "
});
formatter.step({
  "line": 4,
  "name": "I enter valid \"bharath1\" and valid \"bharath1\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 5,
  "name": "user should able to login successfully",
  "keyword": "Then "
});
formatter.match({
  "location": "SmokeTest.open_Chrome_and_start_application()"
});
formatter.result({
  "duration": 19148717009,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "bharath1",
      "offset": 15
    },
    {
      "val": "bharath1",
      "offset": 36
    }
  ],
  "location": "SmokeTest.i_enter_valid_and_valid(String,String)"
});
formatter.result({
  "duration": 224185743,
  "status": "passed"
});
formatter.match({
  "location": "SmokeTest.user_should_able_to_login_successfully()"
});
formatter.result({
  "duration": 4127005863,
  "status": "passed"
});
formatter.scenario({
  "line": 11,
  "name": "Test Login with valid Credentials",
  "description": "",
  "id": "test-facebook-smoke-scenario;test-login-with-valid-credentials;;4",
  "type": "scenario",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 3,
  "name": "Open Chrome and start application",
  "keyword": "Given "
});
formatter.step({
  "line": 4,
  "name": "I enter valid \"bharath2\" and valid \"bharath2\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 5,
  "name": "user should able to login successfully",
  "keyword": "Then "
});
formatter.match({
  "location": "SmokeTest.open_Chrome_and_start_application()"
});
formatter.result({
  "duration": 22106022632,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "bharath2",
      "offset": 15
    },
    {
      "val": "bharath2",
      "offset": 36
    }
  ],
  "location": "SmokeTest.i_enter_valid_and_valid(String,String)"
});
formatter.result({
  "duration": 288821405,
  "status": "passed"
});
formatter.match({
  "location": "SmokeTest.user_should_able_to_login_successfully()"
});
formatter.result({
  "duration": 2440529815,
  "status": "passed"
});
});