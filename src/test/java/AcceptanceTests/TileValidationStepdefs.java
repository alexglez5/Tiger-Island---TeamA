package AcceptanceTests;

import cucumber.api.PendingException;
import Tigerisland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by nathanbarnavon on 4/2/17.
 */
public class TileValidationStepdefs {

    Game app = new Game();
    Tile tile;

    @Given("^the first tile has been placed$")
    public void the_first_tile_has_been_placed() throws Throwable {
        app.placeFirstTile();
    }

    @Given("^the player has a tile of types \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_player_has_a_tile_of_types_and(String t1, String t2) throws Throwable {
        TerrainType a = app.stringToTerrain(t1);
        TerrainType b = app.stringToTerrain(t2);
        tile = new Tile(a, b);
    }

    @When("^the player places the tile at (-?\\d+),(-?\\d+),(-?\\d+) with orientation (\\d+)$")
    public void the_player_places_the_tile_at_with_orientation(int xCord, int yCord, int zCord, int o) throws Throwable {
        Coordinate target = new Coordinate(xCord,yCord,zCord);
        app.placeTile(tile, target, o);
    }

    @Then("^the game board should accept the tile placement$")
    public void the_game_board_should_accept_the_tile_placement() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
