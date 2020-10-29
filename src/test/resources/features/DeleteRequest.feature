Feature: Delete Request
  In order to discard a bad request
  As a user
  I want to delete a request

  Scenario: Reuser creator deletes Request
    Given I login as "reuserDemo" with password "password"
    And I register a new request with description "descripcio"
    When I delete the previously created Request
    Then The response code is 204
    And It does not exist a request with description "descripcio"


  Scenario: Delete Request of other reuser
    Given I login as "reuserDemo" with password "password"
    And I register a new request with description "descripcio"
    When I login as "reuser2" with password "password"
    And I delete the previously created Request
    Then The response code is 403