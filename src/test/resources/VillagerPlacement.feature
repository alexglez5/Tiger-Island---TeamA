Feature: Villager Placement

  Scenario: Founding a settlement (valid)
    Given the player chooses to "Found Settlement" at (0,0)
    When there is a open hex on level one
      And the hex at (0, 1) is not of "Volcano" terrain
      And the player has a villager left
    Then the player will place 1 villager on that hex at (0, 1)

  Scenario: Founding a settlement (invalid)
    Given the player chooses to "Found Settlement"
    When there is an open hex on level one
      And the hex at (0, 0) is of "volcano" terrain
    Then the player cannot place 1 villager at (0,0)

  Scenario: Expand a settlement (valid)
    Given the player wants to "Expand Settlement"
    When there is a settlement at (0, 1) that can be expanded
      And the hex at (0, 1) is of "lake" and has a settlement
      And the hex at (-1, 1) is of "grasslands" and has no settlement
    Then the player will place 1 villager at (-1, 1) with terrain type "grasslands"

  Scenario: Expand a settlement to multiple hexes (valid)
    Given the player has the desire to "Expand Settlement"
      And more than one hex from (0, 1) is available of that terrain type to expand into
    When there is a settlement at (0 , 1) that can be expanded
      And the hex at (0 , 1) is of "lakes" and has a settlement