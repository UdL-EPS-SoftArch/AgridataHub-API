Feature: Register Reuser
  In order to get data
  As a reuser
  I want to register myself and get an account

  Scenario: Register new reuser
    Given There is no registered reuser with username "reuser"
    And I'm not logged in
    When I register a new reuser with username "reuser", email "reuser@sample.app" and password "password"
    Then The response code is 201
    And It has been created a reuser with username "reuser" and email "reuser@sample.app", the password is not returned

  Scenario: Register existing reuser username
    Given There is a registered reuser with username "reuser" and password "existing" and email "existing@example.com"
    And I'm not logged in
    When I register a new reuser with username "reuser", email "reuser@example.com" and password "password"
    Then The response code is 401
    And I cannot login with username "reuser" and password "password"