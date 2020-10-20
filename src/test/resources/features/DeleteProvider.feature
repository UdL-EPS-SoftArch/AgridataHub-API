Feature: Delete Provider
  In order to unsubscribe from the app
  As a provider
  I want to delete my account

  Scenario: Provider deletes his/her own account
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I delete the provider with username "provider"
    Then The response code is 204
    And It does not exist a provider with username "provider"

  Scenario: Provider deletes a not owned account
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And There is a registered provider with username "provider2" and password "password" and email "prov2@gmail.com"
    And I login as "provider" with password "password"
    When I delete the provider with username "provider2"
    Then The response code is 403
    And It has not been deleted a provider with username "provider2"

  Scenario: Delete a provider when not authenticated
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    When I delete the provider with username "provider"
    Then The response code is 401
    And It has not been deleted a provider with username "provider"

  Scenario: Delete a provider that does not exist
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I delete the provider with username "unknown"
    Then The response code is 404

  Scenario: User deletes a provider
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "demo" with password "password"
    When I delete the provider with username "provider"
    Then The response code is 403
    And It has not been deleted a provider with username "provider"