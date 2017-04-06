package Tigerisland;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.Error;

public class ATLevel2Placement {
    App app = new App();

    @Given("^that the board has at least (\\d+) tiles$")
    public void that_the_board_has_at_least_tiles(int arg1) throws Throwable {
        app.map.gameBoard.clear();
        if(!app.isEmptyBoard()){
            throw new Error("The Board already has tiles");
        }
        app.updateCurrentTile("Grassland", "Grassland");
        app.placeTile("FromBottom",0,0);
        if(!app.doesTileExist(0, 0)){
            throw new Error("couldn't place tile at (0, 0)");
        }
        app.updateCurrentTile("Grassland", "Grassland");
        app.placeTile("FromBottom", 1,-2);
        if(!app.doesTileExist(1, -2)){
            throw new Error("couldn't place tile at (1, -2)");
        }
    }

    @Given("^a valid level two placement option exists at \\((-?\\d+),(-?\\d+)\\)$")
    public void a_valid_level_two_placement_option_exists_at(int arg1, int arg2) throws Throwable {
        if(true) {}; // need to implement return valid options
    }

    @When("^the player places a tile on level (\\d+) at a valid level two location$")
    public void the_player_places_a_tile_on_level_at_a_valid_level_two_location(int arg1) throws Throwable {
        if(true) {}; // need to implement player choices
        app.updateCurrentTile("Lake", "Grassland");
    }

    @When("^the level two tile's origin will be at \\((-?\\d+),(-?\\d+)\\) with orientation \"([^\"]*)\"$")
    public void the_level_two_tile_s_origin_will_be_at_with_orientation(int x, int y, String orientation) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        app.placeTile(orientation,  x, y );
    }

    @Then("^the gameboard should accept the tile at \\((-?\\d+),(-?\\d+)\\) at level (\\d+)$")
    public void the_gameboard_should_accept_the_tile_at_at_level(int coordinateX, int coordinateY, int level) throws Throwable {
        if(!(app.returnLevel(coordinateX,coordinateY)==level)){
            throw new Error("Tile is not on correct level");
        }
    }
}
