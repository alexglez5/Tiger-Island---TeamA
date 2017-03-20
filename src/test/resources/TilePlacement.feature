Feature: Tile Placement
  In order to play a successful game
  As a player of the game
  I want a successful game board state

  Scenario: First tile placement
    When the player places a tile with identifier "1"
    Then the volcano hex of that tile is placed at coordinate (0,0)