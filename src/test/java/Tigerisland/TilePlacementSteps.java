package Tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class TilePlacementSteps {

    App app = new App();

    @When("^the player places a tile with identifier (\\d+)$")
    public void the_player_places_a_tile_with_identifier(int firstTileID) throws Throwable {
        app.placeTile(firstTileID);
    }

    @Then("^the volcano hex of that tile is placed at coordinate \\((\\d+),(\\d+)\\)$")
    public void the_volcano_hex_of_that_tile_is_placed_at_coordinate(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
