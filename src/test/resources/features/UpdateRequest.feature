Feature: Update Request
  In order to modify an existing Request
  As a user
  I want to update the Request

  Scenario: User updates description
    And I login as "reuserDemo" with password "password"
    And Exists a created request with description "old description"
    When I change the description of the previous request to "new description"
    Then The response code is 200
    And It has been updated the request description to "new description"

    Scenario: User try to update creation date
      Given I login as "reuserDemo" with password "password"
      And Exists a created request with description "description"
      When I change the date of the previous request to current date
      Then The response code is 400

    Scenario: Update Request description of other reuser
      Given I login as "reuserDemo" with password "password"
      And Exists a created request with description "description"
      And There is a registered reuser with username "reuser2" and password "password" and email "emailreuser@gmail.com"
      When I login as "reuser2" with password "password"
      And I change the description of the previous request to "new description"
      Then The response code is 403