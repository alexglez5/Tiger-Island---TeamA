Feature: Tile Placement
  In order to play a successful game
  As a player of the game
  I want a successful game board state
  
  Scenario: First tile placement hexes exist
    Given the board is empty and player "red" is given tile with terrains "Lake" and "Rocky"
    When the player "red" places the tile at (0,0) and orientation "FromBottom"
    Then the tile left hex should be placed at (-1,1) with terrain "Lake"
    And the tile right hex should be placed at (0,1) with terrain "Rocky"
    And the tile volcano hex should be placed at (0,0) with terrain "Volcano"

  Scenario: It is the start of a player's turns on turn 2
    Given the player "red" is given a tile with terrains "Lake" and "Rocky"
    When the player places the tile with orientation "FromTop" at (2,1)
    And the tile at (2,1) will have no connections to any other tiles
    Then the gameboard should reject the tile at (2, 1)

  Scenario: It is the start of a player's turn on turn 2
    Given the player "red" is given a tile with terrains "Lake" and "Rocky"
    When the player places the tile with next tile ID 2 and orientaiton "FromTop" at (1,0)
    And the tile at (1,0) will have more than zero connections to any other tile
    Then the gameboard should accept the tile at (1,0)

  Scenario: The player wants to place a tile on level 2 thus erupting a volcano
    Given that the board has at least 2 tiles
    And a valid level two placement option exists at (0,0)
    When the player places a tile on level 2 at a valid level two location
    And the level two tile's origin will be at (0,0) with orientation "FromTop"
    Then the gameboard should accept the tile at (0,0) at level 2

  Scenario: The player wants to place a tile on level 2 thus erupting a volcano
    Given that the board has at least 2 tiles from game start
    When the player places a tile on level 2 at a invalid level two location
    And the level two tile's origin will be at an incorrect coordinate (0,-1) with orientation "FromTopLeft"
    Then the gameboard should not accept the tile at (0,-1) at level 2

