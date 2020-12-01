Feature: Rule feature works

  Background:
    Given there is a server for rule steps

  Scenario: We can grab a key and use it
    Given I have a registration payload
    When I send a POST to the /applications endpoint
    Then I receive a 201 status code for rule steps

  Scenario: We can make a badge
    Given I have a badge payload
    When I send a POST to the /badges endpoint
    Then I receive a 201 status code for rule steps

  Scenario: We can make a point scale
    Given I have a point scale payload
    When I send a POST to the /pointscales endpoint
    Then I receive a 201 status code for rule steps
    
  Scenario: We can make a rule
    Given I have a rule payload
    When I send a POST to the /rule endpoint
    Then I receive a 201 status code for rule steps

  Scenario: I make an event to trigger the rule / I am awarded the badge /I am awarded the points
    Given I have an event payload
    When I send a POST to the /event endpoint
    Then I receive a 201 status code for rule steps
    When I send a GET to the /BadgeRewards endpoint
    Then I receive a 200 status code for rule steps
    Then I find my awarded badge
    When I send a GET to the /PointScaleRewards endpoint
    Then I receive a 200 status code for rule steps
    Then I find my awarded points
    Given I have an event payload
    When I send a POST to the /event endpoint
    Then I receive a 201 status code for rule steps
    When I send a GET to the /PointScaleRewards endpoint
    Then I receive a 200 status code for rule steps
    Then I find more awarded points
