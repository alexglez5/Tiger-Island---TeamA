package Tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import java.lang.Error;
/**
 * Created by NotKali on 3/22/2017.
 */
public class StartOfTurnTilePlacementStepdefTest {
    App app = new App();

    @Given("^the player is given a tile with terrains \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_player_is_given_a_tile_with_terrains_and(String firstTerrain, String secondTerrain) throws Throwable {
        app.givePlayerTile(firstTerrain, secondTerrain);
        app.placeTile("FromBottom", 1,0, 0 );
        app.givePlayerTile(firstTerrain, secondTerrain);
    }

    @When("^the player places the tile with next tile ID (\\d+) and orientation \"([^\"]*)\" at \\((\\d+),(\\d+)\\)$")
    public void the_player_places_the_tile_with_next_tile_ID_and_orientation_at(int tileID, String orientation, int coordinate1, int coordinate2) throws Throwable {
        app.placeTile(orientation, tileID, coordinate1, coordinate2);
    }

    @When("^the tile at \\((\\d+),(\\d+)\\) will have no connections to any other tiles$")
    public void the_tile_at_will_have_no_connections_to_any_other_tiles(int coordinate1, int coordinate2) throws Throwable {
        if(app.doesTileExist(coordinate1, coordinate2)){
            throw new Error("tile had connections");
        };
    }

    @Then("^the gameboard should reject the tile at \\((\\d+), (\\d+)\\)$")
    public void the_gameboard_should_reject_the_tile_at(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}

