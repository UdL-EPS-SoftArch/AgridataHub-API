Feature: Delete Dataset
  In order to manage my datasets
  As a provider
  I want to delete my datasets

  Background:
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "title" and description "description"

  Scenario: Provider deletes his/her dataset
    Given I login as "provider" with password "password"
    When I delete the dataset with id "1"
    Then The response code is 204
    
  Scenario: Provider deletes a not owned dataset
    Given I login as "provider" with password "password"
    And There is a registered provider with username "provider2" and password "password" and email "prov2@gmail.com"
    And I login as "provider2" with password "password"
    When I delete the dataset with id "1"
    Then The response code is 403
    And I login as "provider" with password "password"
    And I request the dataset with id "1"
    And The response code is 200

  Scenario: Delete a dataset when not authenticated
    Given I'm not logged in
    When I delete the dataset with id "1"
    Then The response code is 401
    And I login as "provider" with password "password"
    And I request the dataset with id "1"
    And The response code is 200

  Scenario: Delete a dataset that does not exist
    And I login as "provider" with password "password"
    When I delete the dataset with id "99"
    Then The response code is 404

  Scenario: Reuser deletes a dataset
    Given There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    When I delete the dataset with id "1"
    Then The response code is 403
    And I login as "provider" with password "password"
    And I request the dataset with id "1"
    And The response code is 200
    
  Scenario: User deletes a dataset
    Given I login as "demo" with password "password"
    When I delete the dataset with id "1"
    Then The response code is 403
    And I login as "provider" with password "password"
    And I request the dataset with id "1"
    And The response code is 200
