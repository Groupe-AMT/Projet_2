Feature: Registration working

  Background:
    Given there is a Fruits server

  Scenario: We can grab a key and use it
    When I send a GET to the /applications endpoint
    Then I receive a 401 status code
    When I send a POST to the /registration endpoint
    Then I receive a 201 status code
    When I send a GET to the /applications endpoint with API Key
    Then I receive a 200 status code
