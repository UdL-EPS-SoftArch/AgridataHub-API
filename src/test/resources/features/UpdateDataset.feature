Feature: Update Dataset
  In order to modify my datasets
  As a provider
  I want to update a dataset

  Scenario: Provider updates title
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And There is a created dataset with title "title" and description "description"
    When I change the title of the dataset with title "title" and description "description" to "newtitle"
    Then The response code is 200
    And The previously updated dataset has now title "newtitle"

  Scenario: Provider updates description
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And There is a created dataset with title "title" and description "description"
    When I change the description of the dataset with title "title" and description "description" to "newDescription"
    Then The response code is 200
    And The previously updated dataset has now description "newDescription"
    
  Scenario: Provider updates title to a blank one
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And There is a created dataset with title "title" and description "description"
    When I change the title of the dataset with title "title" and description "description" to ""
    Then The response code is 400
    And The error message is "must not be blank"
    And The previously updated dataset has now title "title"
    
  Scenario: Update the title of a dataset that does not exist
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I change the title of the dataset with title "title" and description "description" to "newTitle"
    Then The response code is 404

  Scenario: User updates title
    Given I login as "demo" with password "password"
    And There is a created dataset with title "title" and description "description"
    When I change the title of the dataset with title "title" and description "description" to "newTitle"
    Then The response code is 403
    And The previously updated dataset has now title "title"

  Scenario: Reuser updates title
    Given There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    And There is a created dataset with title "title" and description "description"
    When I change the title of the dataset with title "title" and description "description" to "newTitle"
    Then The response code is 403
    And The previously updated dataset has now title "title"