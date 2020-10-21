Feature: Delete Request
  In order to discard a bad request
  As a user
  I want to delete a request

  Scenario: User deletes an existing Request
    Given There is a registered user with username "demo" and password "password" and email "email"
    And I login as "demo" with password "password"
    And Exists a Request with description "primera request"
    When I delete the previously created Request
    Then The response code is 204


  Scenario: Delete Request when when not authenticated
    Given There is a registered user with username "demo" and password "password" and email "email"
    And I login as "unknown" with password "unknown"
    And Exists a Request with description "primera request"
    When I delete the previously created Request
    Then The response code is 401