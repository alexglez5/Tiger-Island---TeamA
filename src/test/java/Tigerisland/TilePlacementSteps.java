package Tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import java.lang.Error;

import javax.management.RuntimeErrorException;

public class TilePlacementSteps {

    App app = new App();

    @When("^the player places a tile with identifier (\\d+)$")
    public void the_player_places_a_tile_with_identifier(int firstTileID) throws Throwable {
        app.placeTile(firstTileID);
    }

    @Then("^the volcano hex of that tile is placed at coordinate \\((\\d+),(\\d+)\\)$")
    public void the_volcano_hex_of_that_tile_is_placed_at_coordinate(int coordinate1, int coordinate2) throws Throwable {
        if(!app.checkTile(coordinate1, coordinate2)){
            throw new Error("didn't work");
        }
    }
}
