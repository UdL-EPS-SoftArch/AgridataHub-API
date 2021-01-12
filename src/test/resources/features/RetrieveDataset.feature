Feature: Retrieve Dataset
  In order to see what datasets are available in the app
  As a user
  I want to list all the datasets or see the details of one

  Scenario: User lists all datasets
    Given There is a created dataset with title "title1" and description "description1" and provided by "providerDemo"
    And There is a created dataset with title "title2" and description "description2" and provided by "providerDemo"
    And I login as "demo" with password "password"
    When I list all the existing datasets in the app
    Then The response code is 200
    And There has been retrieved 2 datasets
    
  Scenario: Provider lists all datasets
    Given There is a created dataset with title "title1" and description "description1" and provided by "providerDemo"
    And There is a created dataset with title "title2" and description "description2" and provided by "providerDemo"
    And There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I list all the existing datasets in the app
    Then The response code is 200
    And There has been retrieved 2 datasets
    
  Scenario: Reuser lists all datasets
    Given There is a created dataset with title "title1" and description "description1" and provided by "providerDemo"
    And There is a created dataset with title "title2" and description "description2" and provided by "providerDemo"
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
    Given There is a created dataset with title "title1" and description "description1" and provided by "providerDemo"
    And There is a created dataset with title "title2" and description "description2" and provided by "providerDemo"
    When I list all the existing datasets in the app
    Then The response code is 401

  Scenario: Get a non existing dataset
    Given There is a registered provider with username "providerA" and password "password" and email "provA@gmail.com"
    And I login as "providerA" with password "password"
    When I request the dataset with id "99"
    Then The response code is 404

  Scenario: Get an existing dataset when not authenticated
    Given There is a registered provider with username "providerA" and password "password" and email "provA@gmail.com"
    And I login as "providerA" with password "password"
    And I create a new dataset with title "title" and description "description"
    And I'm not logged in
    When I request the dataset with id "1"
    Then The response code is 401

  Scenario: Reuser searches all datasets by text in title or description
    Given There is a created dataset with title "title" and description "description" and provided by "providerDemo"
    And There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    When I search all the existing datasets in the app containing text "title" in title or containing text "pepito" in description
    And There has been retrieved 1 datasets
    Then The response code is 200

  Scenario: Search datasets containing in title or description
    Given There is a created dataset with title "title" and description "description" and provided by "providerDemo"
    And There is a created dataset with title "title2" and description "description2" and provided by "providerDemo"
    And There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    When I search all the existing datasets in the app containing text "title" in title or containing text "description" in description
    Then The response code is 200
    And There has been retrieved 2 datasets

  Scenario: Search a dataset that does not exist
    Given There is a created dataset with title "title" and description "description" and provided by "providerDemo"
    And There is a created dataset with title "title2" and description "description2" and provided by "providerDemo"
    And There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    When I search all the existing datasets in the app containing text "robert" in title or containing text "munne" in description
    Then The response code is 200
    And There has been retrieved 0 datasets

  Scenario: Search a dataset that does not exist
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    When I search all the existing datasets in the app containing text "TitleNotFound" in title or containing text "DescriptionNotFound" in description
    Then The response code is 200
    And There has been retrieved 0 datasets

  Scenario: Provider lists own datasets
    Given There is a registered provider with username "providerA" and password "password" and email "provA@gmail.com"
    And There is a registered provider with username "providerB" and password "password" and email "provB@gmail.com"
    And I login as "providerA" with password "password"
    And I create a new dataset with title "titleA" and description "descriptionA"
    And I login as "providerB" with password "password"
    And I create a new dataset with title "titleB1" and description "descriptionB1"
    And I create a new dataset with title "titleB2" and description "descriptionB2"
    When I list all my datasets
    Then The response code is 200
    And There has been retrieved 2 datasets

  Scenario: Provider gets a dataset that owns
    Given There is a registered provider with username "providerA" and password "password" and email "provA@gmail.com"
    And I login as "providerA" with password "password"
    And I create a new dataset with title "titleA" and description "descriptionA"
    When I request the dataset with id "1"
    Then The response code is 200

  Scenario: Provider gets a dataset that does not own
    Given There is a registered provider with username "providerA" and password "password" and email "provA@gmail.com"
    And There is a registered provider with username "providerB" and password "password" and email "provB@gmail.com"
    And I login as "providerA" with password "password"
    And I create a new dataset with title "titleA" and description "descriptionA"
    And I login as "providerB" with password "password"
    When I request the dataset with id "1"
    Then The response code is 403
