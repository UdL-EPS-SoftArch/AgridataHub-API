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
    And I create a new DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "False"
    And It has been created a new DatasetRequest
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "False" to "True"
    Then The response code is 200
    And It has been updated the status value of DatasetRequest to "True"


  Scenario: Provider not authenticated tries to change DatasetRequest
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And There is a created dataset with title "title1" and description "description1"
    And There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "False"
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "False" to "True"
    Then The response code is 401


  Scenario: reuser tries to change DatasetRequest value
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And There is a created dataset with title "title1" and description "description1"
    And There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "False"
    And I login as "reuser" with password "passwd"
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "False" to "True"
    Then The response code is 401



  Scenario: provider tries to change DatasetRequest value
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    And I create a new dataset with title "title1" and description "description1"
    And I create a new DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "False"
    And There is a registered provider with username "provider1" and password "password1" and email "prov1@mail.com"
    And I login as "provider1" with password "password1"
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "False" to "True"
    Then The response code is 403

