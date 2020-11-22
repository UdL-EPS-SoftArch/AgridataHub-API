Feature: Grant Dataset Access
  In order to grant access to Dataset
  As a Provider
  I want to respond a DatasetRequest


  Scenario: Change DatasetRequest value
    Given There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "False"
    And There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "False" to "True"
    Then The response code is 200


  Scenario: Provider tries to change DatasetRequest
    Given There is a created DatasetRequest associate with Dataset "title1" and "description1" and Request "description1" with status value "True"
    And There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider2" with password "password"
    When I change the status value of DatasetRequest associate with Dataset "title1" and "description1" and "True" to "False"
    Then The response code is 401




