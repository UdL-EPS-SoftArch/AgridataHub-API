Feature: Retrieve Provider
  In order to see what providers are registered in the app
  As a user
  I want to list all the providers or see the details of one

  Scenario: List all providers
    Given There is a registered provider with username "provider1" and password "password" and email "prov1@gmail.com"
    And There is a registered provider with username "provider2" and password "password" and email "prov2@gmail.com"
    And I login as "demo" with password "password"
    When I list all the existing providers in the app
    Then The response code is 200
    And There has been retrieved 3 providers

  Scenario: List empty providers list
    Given I login as "demo" with password "password"
    When I list all the existing providers in the app
    Then The response code is 200
    And There has been retrieved 1 providers
    
  Scenario: List providers list when not authenticated
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    When I list all the existing providers in the app
    Then The response code is 401

  Scenario: Get an existing provider
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "demo" with password "password"
    When I request the provider with username "provider"
    Then The response code is 200
    And It has been received the provider with username "provider"

  Scenario: Get a non existing provider
    Given I login as "demo" with password "password"
    When I request the provider with username "provider"
    Then The response code is 404

  Scenario: Get an existing provider when not authenticated
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    When I request the provider with username "provider"
    Then The response code is 401
