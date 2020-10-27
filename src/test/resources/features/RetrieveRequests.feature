Feature: Retrieve Request
  In order to see all created requests in the app
  As a user
  I want to list all the requests or see the details of one

  Scenario: List all requests
    Given There exists a created request with description "descrip1"
    And There exists a created request with description "descrip2"
    And I login as "demo" with password "password"
    When I list all the existing requests in the app
    Then The response code is 200
    And There has been retrieved 2 requests

    Scenario: List without any requests
      Given I login as "demo" with password "password"
      When I list all the existing requests in the app
      Then The response code is 200
      And There has been retrieved 0 requests


    Scenario: Retrieve requests when not authenticated
      Given There exists a created request with description "prova"
      When I list all the existing requests in the app
      Then The response code is 401

