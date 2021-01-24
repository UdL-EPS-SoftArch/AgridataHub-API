Feature: Retrieve Provider
  In order to see all my DatasetRequest
  As a user
  I want to list all my DatasetRequest

  Scenario: List all DatasetRequests as provider
    Given I login as "providerDemo" with password "password"
    And I create a new dataset with title "title1" and description "descr1"
    And I create a new dataset with title "title2" and description "descr2"
    And I login as "reuserDemo" with password "password"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title1" and "descr1" and Request with id "3" and status value "False"
    And I create a new DatasetRequest associate with Dataset "title2" and "descr2" and Request with id "3" and status value "False"
    And I login as "providerDemo" with password "password"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 2 DatasetRequest

  Scenario: List all DatasetRequests as user
    Given I login as "demo" with password "password"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 0 DatasetRequest

  Scenario: List all DatasetRequests as reuser
    Given I login as "providerDemo" with password "password"
    And I create a new dataset with title "title1" and description "descr1"
    And I create a new dataset with title "title2" and description "descr2"
    And I login as "reuserDemo" with password "password"
    And I create a new request with description "description"
    And I create a new DatasetRequest associate with Dataset "title1" and "descr1" and Request with id "3" and status value "False"
    And I create a new DatasetRequest associate with Dataset "title2" and "descr2" and Request with id "3" and status value "False"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 2 DatasetRequest

  Scenario: List empty DatasetRequests
    Given I login as "demo" with password "password"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 0 DatasetRequest

  Scenario: List empty DatasetRequests when not authenticated
    Given There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "True"
    And There is a created DatasetRequest associate with Dataset "title2" and "description2" and Request "description2" with status value "True"
    When I list all my DatasetRequests in the app.
    Then The response code is 401