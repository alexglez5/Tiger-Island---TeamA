Feature: Tile Placement
  In order to play a successful game
  As a player of the game
  I want a successful game board state

  Scenario: First tile placement
    When the player places a tile with identifier 1
    Then the volcano hex of that tile is placed at coordinate (0,0)

    #needs acceptance tests to reflect unit tests
  Scenario: First tile placement hexes exist
    Given the player chooses an orientation FromBottom
    When the player places the first tile
    Then the tile's left hex should be placed at (-1,1)
    And the tile's right hex should be placed at (0,1)

    #not coded yet
  Scenario: A Settlement was placed at a chosen hex
    Given the player choose to create a new settlement
    When the player placed a tile
    And has to choose between expanding a settlement and creating a settlement
    Then the hex chosen should now have a villager of the player's color

  #Scenario: Invalid subsequent tile placements
