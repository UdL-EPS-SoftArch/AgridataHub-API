Feature: Create Request
  In order to obtain a Dataset
  As a Reuser
  I want to create requests in order to get datasets

  Scenario:  Create new Request as reuser
    Given I login as "reuserDemo" with password "password"
    And There is no registered request with description "primera request"
    When  I create a new request with description "primera request"
    Then The response code is 201
    And It has been created a new Request with description "primera request"

  Scenario: Create a new Request without authentication
    Given I'm not logged in
    When I create a new request with description "no autenticat"
    Then The response code is 401
    And I cannot create a request with description "no autenticat"