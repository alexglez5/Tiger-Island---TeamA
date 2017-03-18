Feature: Tile Placement
  In order to play a successful game
  As a player of the game
  I want a successful game board state

  Scenario: First tile placement
    Given the gameboard is empty
    When the player places a tile
    Then the volcano hex is placed at coordinate (0,0)