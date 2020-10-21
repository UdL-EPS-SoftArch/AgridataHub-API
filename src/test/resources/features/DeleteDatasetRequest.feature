Feature: Delete DatasetRequest
  In order to delete DatasetRequest from the app
  As a User
  I want to delete my DatasetRequest

  Scenario: User deletes his/her own DatasetRequest
    Given I login as "demo" with password "password"
    And I create a new DatasetRequest with status value "False"
    When I delete a DatasetRequest
    Then The response code is 204
    And It does not exist the DatasetRequest.

  Scenario: User deletes a DatasetRequest when not authenticated
    Given I create a new DatasetRequest with status value "False"
    And I'm not logged in
    When I delete a DatasetRequest
    Then The response code is 403
    And It has not deleted the DatasetRequest.