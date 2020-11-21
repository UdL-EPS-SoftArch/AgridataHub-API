Feature: Delete DatasetRequest
  In order to delete DatasetRequest from the app
  As a User
  I want to delete my DatasetRequest

  Scenario: User deletes his/her own DatasetRequest by request
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I delete a DatasetRequest with status value "True" and associate request "description"
    Then The response code is 204
    And It does not exist the DatasetRequest with status value "True" and request "description".

  Scenario: User deletes his/her own DatasetRequest by dataset
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I delete a DatasetRequest with status value "True" and associate dataset "title" and "description"
    Then The response code is 204
    And It does not exist the DatasetRequest with status value "True" and dateset "title" and "description".

  Scenario: User deletes his/her own DatasetRequest by request when not authenticated
    Given I'm not logged in
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I delete a DatasetRequest with status value "True" and associate request "description"
    Then The response code is 401
    And It does not exist the DatasetRequest with status value "True" and request "description".

  Scenario: User deletes his/her own DatasetRequest by dataset when not authenticated
    Given I'm not logged in
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I delete a DatasetRequest with status value "True" and associate dataset "title" and "description"
    Then The response code is 401
    And It does not exist the DatasetRequest with status value "True" and dateset "title" and "description".