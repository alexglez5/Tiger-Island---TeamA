Feature: Tile Placement
  In order to play a successful game
  As a player of the game
  I want a successful game board state

  Scenario: First tile placement
    When the player places a tile with identifier 1
    Then the volcano hex of that tile is placed at coordinate (0,0)

  Scenario: First tile placement hexes exist
    Given the player chooses an orientation FromBottom
    When the player places the first tile
    Then the tile's left hex should be placed at (-1,1)
    And the tile's right hex should be placed at (0,1)

  #Scenario: Invalid subsequent tile placements
