Feature: Retrieve Provider
  In order to see all my DatasetRequest
  As a user
  I want to list all my DatasetRequest

  Scenario: List all DatasetRequests
    Given I login as "demo" with password "password"
    And I create a new DatasetRequest with status value "False"
    And I create a new DatasetRequest with status value "true"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 2 DatasetRequest

  Scenario: List empty DatasetRequests
    Given I login as "demo" with password "password"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 0 DatasetRequest