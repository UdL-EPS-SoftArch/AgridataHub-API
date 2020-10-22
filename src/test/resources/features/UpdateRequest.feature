Feature: Update Request
  In order to modify an existing Request
  As a user
  I want to update the Request

  Scenario: User updates description
    Given There is a registered user with username "demo" and password "password" and email "email"
    And I login as "demo" with password "password"
    And Exists a created request with description "old description"
    When I change the description of the previous request to "new description"
    Then The response code is 200
    And It has been updated the request description to "new description"

    Scenario: User try to update creation date
      Given There is a registered user with username "demo" and password "password" and email "email"
      And I login as "demo" with password "password"
      And Exists a created request with description "description"
      When I change the date of the previous request to current date
      Then The response code is 400

