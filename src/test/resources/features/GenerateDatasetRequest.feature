Feature: Create DatasetRequest
  In order to have DatasetRequest
  As a user
  I want to create a new DatasetRequest associate with Dataset/Request

  Scenario: Create a new DatasetRequest with associate Dataset and Request as provider
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    When I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    Then The response code is 201
    And It has been created a new DatasetRequest

  Scenario: Create a new DatasetRequest with associate Dataset and Request as user
    Given I login as "demo" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    When I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    Then The response code is 201
    And It has been created a new DatasetRequest

  Scenario: Create a new DatasetRequest with associate Dataset and Request as reuser
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    When I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    Then The response code is 201
    And It has been created a new DatasetRequest

  Scenario: Create a new DatasetRequest with associate Dataset and Request when not authenticated
    Given I'm not logged in
    And I create a new dataset with title "title" and description "description"
    And I create a new request with description "description"
    When I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    Then The response code is 401
    And It has not been created any DatasetRequest