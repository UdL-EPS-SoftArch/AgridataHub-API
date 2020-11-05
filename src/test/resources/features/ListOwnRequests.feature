Feature: List own Requests
  In order to see all my created requests in the app
  As a reuser
  I want to list all the requests

  Scenario: List all requests
    Given I login as "reuserDemo" with password "password"
    And There exists a created request with description "descrip1"
    And There exists a created request with description "descrip2"
    When There is a registered reuser with username "reuser2" and password "password" and email "reusermail@gmial.com"
    And I login as "reuser2" with password "password"
    And I list all the existing requests in the app
    Then The response code is 200
    And There has been retrieved 0 requests