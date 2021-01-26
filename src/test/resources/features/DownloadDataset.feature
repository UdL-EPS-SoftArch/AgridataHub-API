Feature: Download Dataset
  In order to download my datasets
  As a reuser
  I want to download my datasets

  Background:
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "test.csv" and description "description"
    And It has been created a dataset with title "test.csv" and description "description" and is provided by "provider"
    And I upload a dataset with title "test.csv" and description "description" and contentType "UTF-8"
    And There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    And I create a new request with description "description"


  Scenario: Reuser download your datasets
    Given I create a new DatasetRequest associate with Dataset "title" and "description" and Request "description" with status value "True"
    When I download dataset file with title "test.csv" and description "description" and contentType "UTF-8"
    Then The response code is 201
    And It has been download dataset file with DatasetRequest associate with Dataset "test.csv" and "description" and Request "description" and status value "true"

  Scenario: Reuser can't to download your datasets
    Given It has not been created any DatasetRequest
    When I download dataset file with title "test.csv" and description "description" and contentType "UTF-8"
    Then The response code is 201
    And It has not been download dataset file
