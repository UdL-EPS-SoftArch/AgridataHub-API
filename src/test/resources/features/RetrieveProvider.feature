Feature: Retrieve Provider
  In order to see what providers are registered in the app
  As a user
  I want to list all the providers or see the details of one

  Scenario: List all providers
    Given There is a registered provider with username "provider1" and password "password" and email "prov1@gmail.com"
    And There is a registered provider with username "provider2" and password "password" and email "prov2@gmail.com"
    And I login as "provider1" with password "password"
    When I list all the existing providers in the app
    Then The response code is 200
    And There has been retrieved 2 providers

  Scenario: List empty providers list
    Given I login as "demo" with password "password"
    When I list all the existing providers in the app
    Then The response code is 200
    And There has been retrieved 0 providers
    
  Scenario: List providers list when not authenticated
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    When I list all the existing providers in the app
    Then The response code is 401
