Feature: Register Provider
  In order to start sharing my data
  As a provider
  I want to register myself and get an account

  Scenario: Register new provider
    Given There is no registered provider with username "provider"
    And I'm not logged in
    When I register a new provider with username "provider", email "provider@example.com" and password "password"
    Then The response code is 201
    And It has been created a provider with username "provider" and email "provider@example.com", the password is not returned
    And I can login with username "provider" and password "password"