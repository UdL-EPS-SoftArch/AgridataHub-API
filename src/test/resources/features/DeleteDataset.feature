Feature: Delete Dataset
  In order to manage my datasets
  As a provider
  I want to delete my datasets

  Scenario: Provider deletes his/her dataset
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And There is a created dataset with title "title" and description "description"
    And I login as "provider" with password "password"
    When I delete the dataset with title "title" and description "description"
    Then The response code is 204
    And It does not exist a dataset with title "title" and description "description"

  Scenario: Delete a dataset when not authenticated
    Given There is a created dataset with title "title" and description "description"
    When I delete the dataset with title "title" and description "description"
    Then The response code is 401
    And It has not been deleted a dataset with title "title" and description "description"

  Scenario: Delete a dataset that does not exist
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I delete the dataset with title "title" and description "description"
    Then The response code is 404

  Scenario: Reuser deletes a dataset
    Given There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    And There is a created dataset with title "title" and description "description"
    When I delete the dataset with title "title" and description "description"
    Then The response code is 403
    And It has not been deleted a dataset with title "title" and description "description"
    
  Scenario: User deletes a dataset
    Given I login as "demo" with password "password"
    And There is a created dataset with title "title" and description "description"
    When I delete the dataset with title "title" and description "description"
    Then The response code is 403
    And It has not been deleted a dataset with title "title" and description "description"
