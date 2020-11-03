Feature: Create Dataset
  In order to share data
  As a provider
  I want to create a dataset and make it available
  
  Scenario: Create new dataset
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I create a new dataset with title "title" and description "description"
    Then The response code is 201
    And It has been created a dataset with title "title" and description "description" and is provided by "provider"

  Scenario: Create new dataset with empty description
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I create a new dataset with title "title" and description ""
    Then The response code is 201
    And It has been created a dataset with title "title" and description "" and is provided by "provider"

  Scenario: Create new dataset with empty title
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I create a new dataset with title "" and description "description"
    Then The response code is 400
    And The error message is "must not be blank"
    And It has not been created any dataset

  Scenario: Create new dataset when not authenticated
    Given I'm not logged in
    When I create a new dataset with title "title" and description "description"
    Then The response code is 401
    And It has not been created any dataset

  Scenario: User creates new dataset
    Given I login as "demo" with password "password"
    When I create a new dataset with title "title" and description "description"
    Then The response code is 403
    And It has not been created any dataset

  Scenario: Reuser creates new dataset
    Given I register a new reuser with username "reuser", email "reuser@gmail.com" and password "password"
    And I login as "reuser" with password "password"
    When I create a new dataset with title "title" and description "description"
    Then The response code is 401
    And It has not been created any dataset
