Feature: Update DatasetRequest
  In order to update DatasetRequest
  As a user
  I want to change a DatasetRequest attributes

  Scenario: User update your DatasetRequest
    Given I login as "demo" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "False"
    When I change the status value of DatasetRequest associate with Dataset "title" and "description" and "False" to "True"
    Then The response code is 200
    And It has been updated the status value of DatasetRequest to "True"

  Scenario: User update your DatasetRequest
    Given I login as "demo" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I change the status value of DatasetRequest associate with Dataset "title" and "description" and "True" to "False"
    Then The response code is 200
    And It has been updated the status value of DatasetRequest to "False"