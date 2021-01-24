Feature: Retrieve DatasetRequests of own Datasets
  In order to accept or decline DatasetRequests
  As a Provider or Reuser
  I want to be able to see the DatasetRequests of my Datasets

  Background:
    Given There is a registered provider with username "provider1" and password "password" and email "prov1@gmail.com"
    And There is a registered provider with username "provider2" and password "password" and email "prov2@gmail.com"
    And There is a registered reuser with username "reuser" and password "password" and email "reuser@gmail.com"
    And I login as "provider1" with password "password"
    And I create a new dataset with title "title1" and description "description1"
    And I create a new dataset with title "title2" and description "description2"
    And I login as "provider2" with password "password"
    And I create a new dataset with title "title3" and description "description3"
    And I login as "reuser" with password "password"
    And I create a new request with description "request1"
    And I create a new DatasetRequest associate with Dataset "title1" and "description1" and Request with id "4" and status value "False"
    And I create a new DatasetRequest associate with Dataset "title2" and "description2" and Request with id "4" and status value "False"
    And I create a new DatasetRequest associate with Dataset "title3" and "description3" and Request with id "4" and status value "False"

  Scenario: List DatasetRequests of own datasets as provider1
    Given I login as "provider1" with password "password"
    When I list all DatasetRequests of my own Datasets
    Then The response code is 200
    And There has been retrieved 2 DatasetRequest

  Scenario: List DatasetRequests of own datasets as provider2
    Given I login as "provider2" with password "password"
    When I list all DatasetRequests of my own Datasets
    Then The response code is 200
    And There has been retrieved 1 DatasetRequest

  Scenario: List empty list of DatasetRequests of own datasets as provider3
    Given There is a registered provider with username "provider3" and password "password" and email "prov3@gmail.com"
    And I login as "provider3" with password "password"
    When I list all DatasetRequests of my own Datasets
    Then The response code is 200
    And There has been retrieved 0 DatasetRequest

  Scenario: List DatasetRequests of own requests as reuser
    Given I login as "reuser" with password "password"
    When I list all DatasetRequests of my own Datasets
    Then The response code is 200
    And There has been retrieved 3 DatasetRequest