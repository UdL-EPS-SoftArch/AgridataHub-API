Feature: Create Dataset
  In order to share data
  As a provider
  I want to create a dataset and make it available

  Scenario: Create a dataset description without data
    Given I login as "provider" with password "password"
    And There are 0 datasets created
    When I create a dataset with title "my own data"
    Then The new dataset has title "my own data"
    And There are 1 datasets created

  Scenario: Create a dataset but wrong password
    Given I login as "provider" with password "wrongpassword"
    And There are 0 datasets created
    When I create a dataset with title "my own data"
    Then The response code is 401
    And The error message is "Bad credentials"
    And There are 0 datasets created

  Scenario: Create a dataset but empty title
    Given I login as "provider" with password "password"
    When I create a dataset with title ""
    Then The response code is 400
    And The error message is "may not be empty"
    And There are 0 datasets created