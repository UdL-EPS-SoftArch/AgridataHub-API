Feature: Register Reuser
  In order to get data
  As a Reuser
  I want to register myself and get an account

  Scenario: Register new Reuser
    Given There is no registered Reuser with username "Reuser"
    And I'm not logged in
    When I register a new user with username "user", email "user@sample.app" and password "password"
    Then The response code is 201
    And It has been created a Reuser with username "Reuser" and email "Reuser@sample.app", the password is not returned

