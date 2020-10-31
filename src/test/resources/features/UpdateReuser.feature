Feature: Update Reuser
  In order to update my profile account
  As a reuser
  I want to change my profile attributes

  Scenario: Reuser updates email
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    When I change the email of reuser "reuser" to "new@gmail.com"
    Then The response code is 200
    And It has been updated the email of reuser "reuser" to "new@gmail.com"

  Scenario: Reuser updates email to an invalid one
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    When I change the email of reuser "reuser" to "newemail"
    Then The response code is 400
    And The error message is "must be a well-formed email address"
    And Email of reuser "reuser" is still "reus@gmail.com"

  Scenario: Reuser updates email to a blank one
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    When I change the email of reuser "reuser" to ""
    Then The response code is 400
    And The error message is "must not be blank"
    And Email of reuser "reuser" is still "reus@gmail.com"

  Scenario: Update the email of a reuser that does not exist
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And I login as "reuser" with password "password"
    When I change the email of reuser "unknown" to "new@gmail.com"
    Then The response code is 404

  Scenario: Reuser updates email of a not owned account
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And There is a registered reuser with username "reuser2" and password "password" and email "reus2@gmail.com"
    And I login as "reuser" with password "password"
    When I change the email of reuser "reuser2" to "new@gmail.com"
    Then The response code is 403
    And Email of reuser "reuser2" is still "reus2@gmail.com"

