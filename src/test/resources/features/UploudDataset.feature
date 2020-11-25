Feature: Uploud Dataset
  In order to share my datasets
  As a provider
  I want to uploud my datasets

  Background:
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "test.csv" and description "description"
    And It has been created a dataset with title "test.csv" and description "description" and is provided by "provider"

  Scenario: Provider uploud your datasets
    Given I login as "provider" with password "password"
    When I upload a dataset with title "test.csv" and description "description" and contentType ","
    Then The dataset contains a file with title "test.csv"
    And The dataset content contains "test.csv" content
    And The dataset contentType is ","


