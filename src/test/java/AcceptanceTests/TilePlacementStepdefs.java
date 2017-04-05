package AcceptanceTests;

import Tigerisland.Coordinate;
import Tigerisland.Game;
import Tigerisland.TerrainType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * Created by nathanbarnavon on 4/1/17.
 */
public class TilePlacementStepdefs {

    Game app = new Game();

    @Given("^a tile with types \"([^\"]*)\" and \"([^\"]*)\"$")
    public void a_tile_with_types_and(String ta, String tb) throws Throwable {
        TerrainType a = app.stringToTerrain(ta);
        TerrainType b = app.stringToTerrain(tb);
        app.setCurrentTile(a, b);
    }

    @Given("^the first tile has been played$")
    public void the_first_tile_has_been_played() throws Throwable {
        app.placeFirstTile();
    }

    @Given("^a tile has been played at (-?\\d+),(-?\\d+),(-?\\d+) with orientation (\\d+)$")
    public void a_tile_has_been_played_at_with_orientation(int x, int y, int z, int o) throws Throwable {
        Coordinate c = new Coordinate(x,y,z);
        app.setCurrentTile(TerrainType.GRASS, TerrainType.LAKE);
        app.checkAndPlaceTile(app.getCurrentTile(), c, o);
    }

    @When("^the player places the tile at (-?\\d+),(-?\\d+),(-?\\d+) with orientation (\\d+)$")
    public void the_player_places_the_tile_at_with_orientation(int x, int y, int z, int o) throws Throwable {
        Coordinate c = new Coordinate(x,y,z);
        app.checkAndPlaceTile(app.getCurrentTile(), c, o);
    }

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

    @Then("^a hex should not occupy location (-?\\d+),(-?\\d+),(-?\\d+)$")
    public void a_hex_should_not_occupy_location(int x, int y, int z) throws Throwable {
        Coordinate cord = new Coordinate(x,y,z);
        Assert.assertFalse(app.checkHexLocation(cord));
    }

    @Then("^the hex at (-?\\d+),(-?\\d+),(-?\\d+) should have level (\\d+)$")
    public void the_hex_at_should_have_level(int x, int y, int z, int l) throws Throwable {
        Coordinate c = new Coordinate(x,y,z);
        app.checkAndPlaceTile(app.getCurrentTile(), c, l);
        Assert.assertEquals(app.checkLevel(c), l);
    }
}