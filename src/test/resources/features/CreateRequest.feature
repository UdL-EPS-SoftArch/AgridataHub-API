Feature: Create Request
  In order to obtain a Dataset
  As a Reuser
  I want to create requests in order to get datasets

  Scenario:  Create new Request
    Given There is no created requests by user with username "reuser"
    And There is a registered user with username "reuser"
    When  I create a new request for a Dataset
    Then the response code is 201

    Scenario:Create a Request for an non-existent DatasetRequest
      Given There is a registered user with username "reuser"
      And No DatasetRequests with id 1
      When I create a Request for a datasetRequest with id 1
      Then the response code is 404