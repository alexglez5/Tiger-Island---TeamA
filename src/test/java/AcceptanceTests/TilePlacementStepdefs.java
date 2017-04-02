package AcceptanceTests;

import Tigerisland.Coordinate;
import Tigerisland.Game;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * Created by nathanbarnavon on 4/1/17.
 */
public class TilePlacementStepdefs {

    Game app;

    @When("^a player places the first tile$")
    public void a_player_places_the_first_tile() throws Throwable {
        app = new Game();
        app.placeFirstTile();
    }

    @Then("^a hex should occupy location (-?\\d+),(-?\\d+),(-?\\d+)$")
    public void a_hex_should_occupy_location(int xCord, int yCord, int zCord) throws Throwable {
        Coordinate cord = new Coordinate(xCord, yCord, zCord);
        Assert.assertTrue(app.checkHexLocation(cord));
    }

}