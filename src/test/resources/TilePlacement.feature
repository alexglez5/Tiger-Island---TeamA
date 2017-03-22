Feature: Tile Placement
  In order to play a successful game
  As a player of the game
  I want a successful game board state
  
  Scenario: First tile placement hexes exist
    Given the board is empty
    When the player places the number 1 tile at (0,0) and orientation "FromBottom"
    Then the tile left hex should be placed at (-1,1)
    And the tile right hex should be placed at (0,1)
    And the tile volcano hex should be placed at (0,0)

  Scenario: The player places the tile that doesn't touch another tile
    Given the player is given a tile with terrains "Lakes" and "Rocky"
    When the player places the tile with next tile ID 2 and orientation "FromTop" at (2,0)
    And the tile at (2,0) will have no connections to any other tiles
    Then the gameboard should reject the tile at (2, 0)

  Scenario: The player places the tile and touches another tile
    Given the player is given a tile with terrains "Lakes" and "Rocky"
    When the player places the tile with next tile ID 2 and orientation "FromTop" at (1,0)
    And the tile at (1, 0) will have a connection to another tile
    Then the gameboard should accept the tile at (1, 0)

  #Scenario: Invalid subsequent tile placements
