Feature: Tile Validation
  In order to play a successful game
  As a player of the game
  I need to place tiles correctly

  Scenario: Correct validation of level one tile placement
    Given the first tile has been placed
    And the player has a tile of types "ROCK" and "GRASS"
    When the player places the tile at 1,-1,0 with orientation 3
    Then a hex should occupy location 1,-1,0