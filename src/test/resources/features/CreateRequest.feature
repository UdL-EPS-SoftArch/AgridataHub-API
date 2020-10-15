Feature: Create Request
  In order to obtain a Dataset
  As a Reuser
  I want to create requests in order to get datasets

  Scenario:  Create new Request as user
    Given I login as "demo" with password "password"
    When  I create a new request with description "primera request"
    Then The response code is 201
    And It has been created a new Request