Feature: Testing Population API Endpoint

  @smoke
  Scenario: Retrieve Population Data for the United States
    Given I send a GET request to "data"
    And the response body should contain population data for the "United States"

  @smoke
  Scenario: Retrieve Population Data for the United States - FAIL
    Given I send a GET request to "data"
    And the response body should contain population data for the "Canada"
