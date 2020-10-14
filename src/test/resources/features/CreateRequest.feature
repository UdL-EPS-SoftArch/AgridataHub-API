Feature: Create Request
  In order to obtain a Dataset
  As a Reuser
  I want to create requests in order to get datasets

  Scenario:  Create new Request
    Given There is no created requests by user with username "reuser"
    And There is a registered user with username "reuser"
    When  I create a new request for a Dataset
    Then The response code is 201