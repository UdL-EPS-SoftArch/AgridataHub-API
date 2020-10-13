Feature: Create DatasetRequest
  In order to have DatasetRequest
  As a user
  I want to create a new DatasetRequest

  Scenario: Create a new DatasetRequest as user
    Given I login as "user" with password "password"
    When I create a new DatasetRequest with value "test" and boolean "False"
    Then The response code is 201
    And It has been created a new DatasetRequest with value "test"