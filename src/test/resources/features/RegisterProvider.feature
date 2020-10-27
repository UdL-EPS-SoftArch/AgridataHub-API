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

  Scenario: Register existing provider username
    Given There is a registered provider with username "provider" and password "existing" and email "existing@example.com"
    And I'm not logged in
    When I register a new provider with username "provider", email "provider@example.com" and password "password"
    Then The response code is 409
    And I cannot login with username "provider" and password "password"

  Scenario: Register provider when already authenticated
    Given I login as "demo" with password "password"
    When I register a new provider with username "provider", email "provider@example.com" and password "password"
    Then The response code is 403
    And It has not been created a provider with username "provider"

  Scenario: Register provider with empty password
    Given I'm not logged in
    When I register a new provider with username "provider", email "provider@example.com" and password ""
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a provider with username "provider"

  Scenario: Register provider with empty email
    Given I'm not logged in
    When I register a new provider with username "provider", email "" and password "password"
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created a provider with username "provider"

  Scenario: Register provider with invalid email
    Given I'm not logged in
    When I register a new provider with username "provider", email "providersample.app" and password "password"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And It has not been created a provider with username "provider"

  Scenario: Register provider with password shorter than 8 characters
    Given I'm not logged in
    When I register a new provider with username "provider", email "provider@example.com" and password "pass"
    Then The response code is 400
    And The error message is "length must be between 8 and 256"
    And It has not been created a user with username "provider"

  Scenario: Register provider with an existing email
    Given There is a registered provider with username "provider" and password "password" and email "provider@example.com"
    And I'm not logged in
    When I register a new provider with username "provider2", email "provider@example.com" and password "password2"
    Then The response code is 409
    And I can login with username "provider" and password "password"
    And I cannot login with username "provider2" and password "password2"