Feature: Tile Placement
  In order to play the game
  As a player of the game
  I need to place tiles correctly

  Scenario: First tile placement
    Given the game board is empty
    When a player places a tile with orientation "FromBottom"
    Then A hex should occupy coordinate (0, 0)
    And A hex should occupy coordinate (0, 1)
    And A hex should occupy coordinate (-1, 1)