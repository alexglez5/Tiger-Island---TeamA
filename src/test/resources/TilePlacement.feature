Feature: Tile Placement
  In order to play the game
  As a player of the game
  I need to place tiles correctly

  Scenario Outline: First tile placement
    When a player places a tile with tileID 1 and orientation <orient>
    Then a hex should occupy coordinate <main>
    And a hex should occupy coordinate <left>
    And a hex should occupy coordinate <right>

    Examples:
       | orient      | main   | left   | right   |
       |"FromBottom" | (0, 0) | (0, 1) | (-1, 1) |