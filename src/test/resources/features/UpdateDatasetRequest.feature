Feature: Update DatasetRequest
  In order to update DatasetRequest
  As a user
  I want to change a DatasetRequest attributes

  Scenario: User update your DatasetRequest
    Given I login as "demo" with password "password"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I change the status value of DatasetRequest to "True"
    Then The response code is 200
    And It has been updated the status value of DatasetRequest to "False"

  Scenario: User update your DatasetRequest
    Given I login as "demo" with password "password"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I change the status value of DatasetRequest to "False"
    Then The response code is 200
    And It has been updated the status value of DatasetRequest to "True"