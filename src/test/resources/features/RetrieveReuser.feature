Feature: Retrieve Reuser
  In order to see what reusers are registered in the app
  As a user
  I want to list all the reusers or see the details of one

  Scenario: List all reusers
    Given There is a registered reuser with username "reuser1" and password "password" and email "reus1@gmail.com"
    And There is a registered reuser with username "reuser2" and password "password" and email "reus2@gmail.com"
    And I login as "demo" with password "password"
    When I list all the existing reusers in the app
    Then The response code is 200
    And There has been retrieved 2 reusers

  Scenario: List empty reusers list
    Given I login as "demo" with password "password"
    When I list all the existing reusers in the app
    Then The response code is 200
    And There has been retrieved 0 reusers

  Scenario: List reusers list when not authenticated
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    When I list all the existing reusers in the app
    Then The response code is 401

  Scenario: Get an existing reuser
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "demo" with password "password"
    When I request the reuser with username "reuser"
    Then The response code is 200
    And It has been received the reuser with username "reuser"

  Scenario: Get a non existing reuser
    Given I login as "demo" with password "password"
    When I request the reuser with username "reuser"
    Then The response code is 404

  Scenario: Get an existing reuser when not authenticated
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    When I request the reuser with username "reuser"
    Then The response code is 401
