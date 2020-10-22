Feature: Retrieve Dataset
  In order to see what datasets are available in the app
  As a user
  I want to list all the datasets or see the details of one

  Scenario: User lists all datasets
    Given There is a created dataset with title "title1" and description "description1"
    And There is a created dataset with title "title2" and description "description2"
    And I login as "demo" with password "password"
    When I list all the existing datasets in the app
    Then The response code is 200
    And There has been retrieved 2 datasets
    
  Scenario: Provider lists all datasets
    Given There is a created dataset with title "title1" and description "description1"
    And There is a created dataset with title "title2" and description "description2"
    And There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I list all the existing datasets in the app
    Then The response code is 200
    And There has been retrieved 2 datasets
    
  Scenario: Reuser lists all datasets
    Given There is a created dataset with title "title1" and description "description1"
    And There is a created dataset with title "title2" and description "description2"
    And There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    When I list all the existing datasets in the app
    Then The response code is 200
    And There has been retrieved 2 datasets
    
  Scenario: List empty datasets list
    Given I login as "demo" with password "password"
    When I list all the existing datasets in the app
    Then The response code is 200
    And There has been retrieved 0 datasets

  Scenario: List all datasets when not authenticated
    Given There is a created dataset with title "title1" and description "description1"
    And There is a created dataset with title "title2" and description "description2"
    When I list all the existing datasets in the app
    Then The response code is 401

  Scenario: Get an existing dataset
    Given There is a created dataset with title "title" and description "description"
    And I login as "demo" with password "password"
    When I request the dataset with title "title" and description "description"
    Then The response code is 200
    And It has been received the dataset with title "title" and description "description"

  Scenario: Get a non existing dataset
    Given I login as "demo" with password "password"
    When I request the dataset with title "title" and description "description"
    Then The response code is 404

  Scenario: Get an existing dataset when not authenticated
    Given There is a created dataset with title "title" and description "description"
    When I request the dataset with title "title" and description "description"
    Then The response code is 401