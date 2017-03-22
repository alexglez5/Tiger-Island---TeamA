Feature: Villager Placement
#add more description

  Scenario: Founding a settlement (valid)
    Given the player chooses to found a settlement
    When there is a open hex on level one
      And the tile is not of volcano terrain
      And the player has a villager left
    Then the player will place 1 villager on that hex

  Scenario: Founding a settlement (invalid)
    Given the player chooses to found a settlement
    When there is not an open hex on level one
      And the tile is not of volcano terrain
      And the player has a villagers left
    Then the player cannot place a villager

  Scenario: Founding a settlement (invalid)
    Given the player chooses to found a settlement
    When there is an open hex on level one
      And the tile is of volcano terrain
      And the player has a villager left
    Then the player cannot place a villager

  Scenario: Founding a settlement (invalid)
    Given the player chooses to found a settlement
    When there is an open hex on level one
      And the tile is not of volcano terrain
      And the player does not have a villager left
    Then the player cannot place a villager

  Scenario: Founding a settlement (invalid)
    Given the player chooses to found a settlement
    When there is an open hex on level one
      And the tile is not of volcano terrain
      And the player does have a villager left
    Then the player cannot place a villager
