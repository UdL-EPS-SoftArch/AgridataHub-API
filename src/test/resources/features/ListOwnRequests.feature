Feature: List own Requests
  In order to see all my created requests in the app
  As a reuser
  I want to list all the requests

  Background:
    Given I login as "reuserDemo" with password "password"
    And There is a registered reuser with username "reuser2" and password "password" and email "reusermail@gmial.com"
    And There exists a created request with description "descrip1"
    And There exists a created request with description "descrip2"


  Scenario: List all requests
    When I list all the existing requests in the app
    Then The response code is 200
    And There has been retrieved 2 requests

  Scenario: List all requests
    When I login as "reuser2" with password "password"
    And I list all the existing requests in the app
    Then The response code is 200
    And There has been retrieved 0 requests

  Scenario: Check if listed request is requested by Reuser
    When I login as "reuser2" with password "password"
    And There exists a created request with description "descrip2"
    When I list all the existing requests in the app
    Then The response code is 200
    And There has been retrieved 1 requests
    And It has been retrieved a Request by "reuser2"
