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

  Scenario: Reuser download your datasets
    Given There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "reuser" with password "password"
    When I download dataset file with title "test.csv" and description "description" and contentType "UTF-8"
    Then The response code is 200
    And It has been download dataset file with title "test.csv" and description "description" and contentType "UTF-8"


