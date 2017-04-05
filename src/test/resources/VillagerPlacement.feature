Feature: Villager Placement
  In order to grow my settlements
  As a player of the game
  I need to place villagers

  Scenario: Found a settlement
    Given the first tile has been played
    When the player 1 chooses to "found" at 1,0,-1
    Then a new settlement is created with size 1