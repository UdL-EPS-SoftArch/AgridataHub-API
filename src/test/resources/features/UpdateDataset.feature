Feature: Update Dataset
  In order to modify my datasets
  As a provider
  I want to update a dataset

  Background:
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "title" and description "description"

  Scenario: Provider updates title
    Given I login as "provider" with password "password"
    When I change the title of the dataset with title "title" and description "description" to "newtitle"
    Then The response code is 200
    And The previously updated dataset has now title "newtitle"

  Scenario: Provider updates description
    Given I login as "provider" with password "password"
    When I change the description of the dataset with title "title" and description "description" to "newDescription"
    Then The response code is 200
    And The previously updated dataset has now description "newDescription"
    
  Scenario: Provider updates title to a blank one
    Given I login as "provider" with password "password"
    When I change the title of the dataset with title "title" and description "description" to ""
    Then The response code is 400
    And The error message is "must not be blank"
    And The previously updated dataset has now title "title"

  Scenario: Provider updates a not owned dataset
    Given There is a registered provider with username "provider2" and password "password" and email "prov2@gmail.com"
    And I login as "provider2" with password "password"
    When I change the title of the dataset with title "title" and description "description" to "newtitle"
    Then The response code is 403
    And The previously updated dataset has now title "title"
    
  Scenario: Update the title of a dataset that does not exist
    And I login as "provider" with password "password"
    When I change the title of the dataset with title "unknown" and description "description" to "newTitle"
    Then The response code is 404

  Scenario: Update dataset when not authenticated
    Given I'm not logged in
    When I change the title of the dataset with title "title" and description "description" to "newTitle"
    Then The response code is 401
    And The previously updated dataset has now title "title"

  Scenario: User updates title
    Given I login as "demo" with password "password"
    When I change the title of the dataset with title "title" and description "description" to "newTitle"
    Then The response code is 403
    And The previously updated dataset has now title "title"

  Scenario: Reuser updates title
    Given There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    When I change the title of the dataset with title "title" and description "description" to "newTitle"
    Then The response code is 403
    And The previously updated dataset has now title "title"