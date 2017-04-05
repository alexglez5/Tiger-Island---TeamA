Feature: Tile Placement
  In order to play the game
  As a player of the game
  I need to be able to place tiles

  Scenario Outline: First tile placement
    When a player places the first tile
    Then a hex should occupy location <x>,<y>,<z>

    Examples:
      | x  | y  | z  |
      | 0  | 0  | 0  |
      | -1 | 0  | 1  |
      | 0  | 1  | -1 |
      | 0  | -1 | 1  |
      | 1  | 0  | -1 |

  Scenario: Level one tile placement is connected
    Given a tile with types "ROCK" and "JUNGLE"
    And the first tile has been played
    When the player places the tile at 1,-1,0 with orientation 2
    Then a hex should occupy location 2,-2,0

  Scenario: Level one tile placement is not connected
    Given a tile with types "ROCK" and "JUNGLE"
    And the first tile has been played
    When the player places the tile at 2,-2,0 with orientation 2
    Then a hex should not occupy location 2,-2,0

  Scenario: Level two tile placement is valid
    Given the first tile has been played
    And a tile has been played at 1,-1,0 with orientation 2
    And a tile with types "ROCK" and "JUNGLE"
    When the player places the tile at 0,0,0 with orientation 2
    Then the hex at 0,0,0 should have level 2

  Scenario: Level two tile placement on one tile
    Given the first tile has been played
    And a tile with types "ROCK" and "JUNGLE"
    When the player places the tile at 0,0,0 with orientation 1
    Then the hex at 0,0,0 should have level 1

  Scenario: Level two tile placement not on volcano
    Given the first tile has been played
    And a tile with types "ROCK" and "JUNGLE"
    When the player places the tile at 0,1,-1 with orientation 3
    Then the hex at 0,1,-1 should have level 1