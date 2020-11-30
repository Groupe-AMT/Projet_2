Feature: Base features is working

  Background:
    Given there is a server

  Scenario: We can register (grab a key and connect)
    When I send a GET to the /applications endpoint
    Then I receive a 403 status code
    Given I have a registration payload for tester
    When I send a POST to the /applications endpoint for registration
    Then I receive a 201 status code
    When I send a GET to the /applications endpoint with API Key
    Then I receive a 200 status code

  Scenario: Badges and PointScale are well inside their respective application
    Given I am in application testBasic1

    Given I have a badge payload for testBasic1
    When I create a badge, POST /badges
    Then I receive a 201 status code
    When I ask for badges
    Then I can find the created badge

    Given I have a point scale payload for testBasic1
    When I create a point scale, POST /pointscales
    Then I receive a 201 status code
    When I ask for point scale
    Then I can find the created point scale

    Given I have an other registration payload for tester
    When I send a POST to the /applications endpoint for registration
    Then I receive a 201 status code
    When I ask for badges
    Then I can not find the created badge
    When I ask for point scale
    Then I can not find the created point scale