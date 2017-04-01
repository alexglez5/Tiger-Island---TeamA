Feature: Tile Placement
  In order to play the game
  As a player of the game
  I need to place tiles correctly

  Scenario Outline: First tile placement
    When a player places the first tile
    Then a hex should occupy location <x>,<y>,<z>

    Examples:
      | x  | y  | z  |
      | 0  | 0  | 0  |
      | -1 | 0  | 1  |
      | -1 | 1  | 0  |
      | 1  | -1 | 0  |
      | 1  | 0  | -1 |