Feature: Grant Dataset Access
  In order to grant access to Dataset
  As a Provider
  I want to respond a DatasetRequest


  Scenario: Change DatasetRequest value
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "title1" and description "description1"
    And There is a registered reuser with username "reuser" and password "passwd" and email "reus@gmail.com"
    And I login as "reuser" with password "passwd"
    And I create a new request with description "description1"
    And There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "True"
    And It has been created a new DatasetRequest
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "True" to "False"
    Then The response code is 200


  Scenario: Provider tries to change DatasetRequest
    Given There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "True"
    And There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider2" with password "password"
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "True" to "False"
    Then The response code is 401




