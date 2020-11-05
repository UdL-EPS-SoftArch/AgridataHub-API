Feature: Grant Dataset Access
  In order to grant access to Dataset
  As a Provider
  I want to respond a DatasetRequest

  Scenario: Grant access to a new DatasetRequest
    Given There is a registered reuser with username "reuser" and password "password" and email "reus@gmail.com"
    And there is created a DatasetRequest with id 2
    Then The response code is 201
    And It has been granted access to DatasetRequest with id 2
