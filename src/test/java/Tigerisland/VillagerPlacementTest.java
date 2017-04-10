package Tigerisland;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VillagerPlacementTest{
        Game game = new Game();
        AI ai = new AI();
        Tile tile;
        Coordinate coordinate;

        @Given("^the player \"([^\"]*)\" chooses to \"([^\"]*)\" at \\((-?\\d+),(-?\\d+)\\)$")
        public void the_player_chooses_to_at(String playerID, String choice, int coordinateX, int coordinateY) throws Throwable {
            ai.setServerMessage(choice);
            tile = new Tile(TerrainType.GRASS, TerrainType.LAKE);
            Coordinate cord = new Coordinate(coordinateX,coordinateY);
            game.placeTile(tile, cord, Orientation.FromBottom );
        }

        @When("^there is a open hex on level one$")
        public void there_is_a_open_hex_on_level_one() throws Throwable {
            if(game.getBoard() == null){
                throw new Error("board is empty");
            }
        }

        @When("^the hex at \\((-?\\d+), (-?\\d+)\\) is not of \"([^\"]*)\" terrain$")
        public void the_hex_at_is_not_of_terrain(int coordinateX, int coordinateY, String terrain) throws Throwable {
            TerrainType terrain1 = TerrainType.GRASS;
            coordinate = new Coordinate(coordinateX, coordinateY);
            Hex hex = game.getBoard().get(coordinate);
            if(!hex.getTerrainType().equals(terrain1)){
                new Error("The terrain was not correct");
            }
        }

        @When("^the player has a villager left$")
        public void the_player_has_a_villager_left() throws Throwable {
            if(!game.settlementCanBeFound(coordinate)) {
                throw new Error("cannot build village");
            }
        }

        @Then("^the player will place (\\d+) villager on that hex at \\((\\d+), (\\d+)\\)$")
        public void the_player_will_place_villager_on_that_hex_at(int numberOfVillagers, int coordinatex, int coordinatey) throws Throwable {
            game.foundNewSettlement(new Coordinate(coordinatex,coordinatey));
            if(game.getSettlements().isEmpty()) {
                throw new Error("the hex doesn't have any villagers placed");
            }
        }
}
