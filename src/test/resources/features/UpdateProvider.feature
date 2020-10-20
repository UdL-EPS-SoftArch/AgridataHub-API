Feature: Update Provider
  In order to update my profile account
  As a provider
  I want to change my profile attributes

  Scenario: Provider updates email
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I change the email of provider "provider" to "new@gmail.com"
    Then The response code is 200
    And It has been updated the email of provider "provider" to "new@gmail.com"

  Scenario: Provider updates email to an invalid one
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I change the email of provider "provider" to "newemail"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And Email of provider "provider" is still "prov@gmail.com"

  Scenario: Provider updates email to a blank one
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I change the email of provider "provider" to ""
    Then The response code is 400
    And The error message is "must not be blank"
    And Email of provider "provider" is still "prov@gmail.com"

  Scenario: Update the email of a provider that does not exist
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I change the email of provider "unknown" to "new@gmail.com"
    Then The response code is 404

  Scenario: User updates a provider's email
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "demo" with password "password"
    When I change the email of provider "provider" to "new@gmail.com"
    Then The response code is 403
    And Email of provider "provider" is still "prov@gmail.com"

  Scenario: Provider updates email of a not owned account
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And There is a registered provider with username "provider2" and password "password" and email "prov2@gmail.com"
    And I login as "provider" with password "password"
    When I change the email of provider "provider2" to "new@gmail.com"
    Then The response code is 403
    And Email of provider "provider2" is still "prov2@gmail.com"