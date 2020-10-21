Feature: Update DatasetRequest
  In order to update DatasetRequest
  As a user
  I want to change my DatasetRequest attributes

  Scenario: User update your DatasetRequest
    Given I login as "demo" with password "password"
    And I create a new DatasetRequest with status value "False"
    When I change the status value of DatasetRequest to "True"
    Then The response code is 200
    And It has been updated the status value of DatasetRequest to "True"

  Scenario: User update your DatasetRequest
    Given I login as "demo" with password "password"
    And I create a new DatasetRequest with status value "True"
    When I change the status value of DatasetRequest to "False"
    Then The response code is 200
    And It has been updated the status value of DatasetRequest to "False"