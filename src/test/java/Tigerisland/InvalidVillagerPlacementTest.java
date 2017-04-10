package Tigerisland;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InvalidVillagerPlacementTest {
    Game game = new Game();
    Tile tile;
    Coordinate cord;

    @Given("^the player chooses to \"([^\"]*)\"$")
    public void the_player_chooses_to(String choice) throws Throwable {
        tile = new Tile(TerrainType.GRASS,TerrainType.JUNGLE);
        cord = new Coordinate(0,0);
        game.placeTile(tile, cord, Orientation.FromBottom);
    }

    @When("^there is an open hex on level one$")
    public void there_is_an_open_hex_on_level_one() throws Throwable {
        if (game.getBoard()==null) {
            throw new Error("board is empty");
        }
    }

    @When("^the hex at \\((-?\\d+), (-?\\d+)\\) is of \"([^\"]*)\" terrain$")
    public void the_hex_at_is_of_terrain(int coordinateX, int coordinateY, String terrain) throws Throwable {
        TerrainType terrain1 = TerrainType.VOLCANO;
        cord = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(cord);
        if(hex == null) {
            new Error("There was no hex at the coordinate");
        }
        if(!hex.getTerrainType().equals(terrain1)){
            new Error("The terrain was not correct");
        }
    }

    @Then("^the player cannot place (\\d+) villager at \\((\\d+),(\\d+)\\)$")
    public void the_player_cannot_place_villager_at(int numberOfVillagers, int coordinateX, int coordinateY) throws Throwable {
        game.foundNewSettlement(cord);
        if(!game.settlementCanBeFound(cord)) {
            throw new Error("the hex has villagers despite incorrect terrain");
        }
    }
}