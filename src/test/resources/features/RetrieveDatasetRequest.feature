Feature: Retrieve Provider
  In order to see all my DatasetRequest
  As a user
  I want to list all my DatasetRequest

  Scenario: List all DatasetRequests as provider
    Given There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "True"
    And There is a created DatasetRequest associate with Dataset "title2" and "description2" and Request "description2" with status value "True"
    And There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 2 DatasetRequest

  Scenario: List all DatasetRequests as user
    Given There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "True"
    And There is a created DatasetRequest associate with Dataset "title2" and "description2" and Request "description2" with status value "True"
    And I login as "demo" with password "password"
    When I list all my DatasetRequests in the app.
    Then The response code is 200
    And There has been retrieved 2 DatasetRequest

  Scenario: List all DatasetRequests as reuser
    Given There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "True"
    And There is a created DatasetRequest associate with Dataset "title2" and "description2" and Request "description2" with status value "True"
    And There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
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