Feature: Delete Reuser
  In order to unsubscribe from the app
  As a reuser
  I want to delete my account

  Scenario: reuser deletes his/her own account
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    When I delete the reuser with username "reuser"
    Then The response code is 204
    And It does not exist a reuser with username "reuser"

  Scenario: reuser deletes a not owned account
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And There is a registered reuser with username "reuser2" and password "password" and email "reus2@gmail.com"
    And I login as "reuser" with password "password"
    When I delete the reuser with username "reuser2"
    Then The response code is 403
    And It has not been deleted a reuser with username "reuser2"

  Scenario: Delete a reuser when not authenticated
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    When I delete the reuser with username "reuser"
    Then The response code is 401
    And It has not been deleted a reuser with username "reuser"

  Scenario: Delete a reuser that does not exist
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    When I delete the reuser with username "unknown"
    Then The response code is 404

  Scenario: User deletes a reuser
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "demo" with password "password"
    When I delete the reuser with username "reuser"
    Then The response code is 403
    And It has not been deleted a reuser with username "reuser"