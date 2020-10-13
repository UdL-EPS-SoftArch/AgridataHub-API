Feature: List DataSets
  In order to get grouped DataSets
  As a reuser
  I want to get a list with all DataSets

  Scenario: List existing DataSets
    Given There is already a list called "list"
    When I create a new list called "list"
    Then The response code is 201
    And It asks if I want to overwrite the old list

  Scenario: List new DataSets
    Given There is no list
    When I create a new list called "list"
    Then The response code is 201
    And I cannot create new list with name "list"