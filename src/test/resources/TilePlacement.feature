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

  Scenario: It is the start of a player's turns on turn 2
    Given the player is given a tile with terrains "Lakes" and "Rocky"
    When the player places the tile with next tile ID 2 and orientation "FromTop" at (2,1)
    And the tile at (2,1) will have no connections to any other tiles
    Then the gameboard should reject the tile at (2, 1)

  Scenario: It is the start of a player's turn on turn 2
    Given the player is given a tile with terrains "Lakes" and "Rocky"
    When the player places the tile with next tile ID 2 and orientaiton "FromTop" at (1,0)
    And the tile at (1,0) will have more than zero connections to any other tile
    Then the gameboard should accept the tile at (1,0)

    #not coded yet
  Scenario: A Settlement was placed at a chosen hex
    Given the player choose to create a new settlement
    When the player placed a tile
    And has to choose between expanding a settlement and creating a settlement
    Then the hex chosen should now have a villager of the player's color

  #Scenario: Invalid subsequent tile placements
