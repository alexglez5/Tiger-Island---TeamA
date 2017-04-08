package Tigerisland;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.Error;

public class StartOfTurnTilePlacement {
    App app = new App();

    @Given("^the player \"([^\"]*)\" is given a tile with terrains \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_player_is_given_a_tile_with_terrains_and(String playerID, String firstTerrain, String secondTerrain ) throws Throwable {
        app.map.getBoard().clear();
        app.updateCurrentTile(firstTerrain, secondTerrain);
        app.placeTile("FromBottom", 0, 0 );
        if(app.isEmptyBoard()){
            throw new Error("Empty Board");
        }
        app.updateCurrentTile(firstTerrain, secondTerrain);
    }

    @When("^the player places the tile with orientation \"([^\"]*)\" at \\((\\d+),(\\d+)\\)$")
    public void the_player_places_the_tile_with_next_tile_ID_and_orientation_at(String orientation, int coordinate1, int coordinate2) throws Throwable {
        app.placeTile(orientation, coordinate1, coordinate2 );
    }

    @When("^the tile at \\((\\d+),(\\d+)\\) will have no connections to any other tiles$")
    public void the_tile_at_will_have_no_connections_to_any_other_tiles(int coordinate1, int coordinate2) throws Throwable {
        if(app.doesTileExist(coordinate1, coordinate2)){
            throw new Error("tile does have connections");
        };
    }

    @Then("^the gameboard should reject the tile at \\((\\d+), (\\d+)\\)$")
    public void the_gameboard_should_reject_the_tile_at(int coordinate1, int coordinate2) throws Throwable {
        if(app.doesTileExist(coordinate1, coordinate2)){
            throw new Error("tile doesn't have connections");
        };
    }

    //next Test given same senario

    @When("^the player places the tile with next tile ID (\\d+) and orientaiton \"([^\"]*)\" at \\((\\d+),(\\d+)\\)$")
    public void the_player_places_the_tile_with_next_tile_ID_and_orientaiton_at(int id, String orientation, int coordinate1, int coordinate2) throws Throwable {
        app.placeTile(orientation, coordinate1, coordinate2);
    }

    @When("^the tile at \\((\\d+),(\\d+)\\) will have more than zero connections to any other tile$")
    public void the_tile_at_will_have_more_than_zero_connections_to_any_other_tile(int coordinate1, int coordinate2) throws Throwable {
        if(!app.doesTileExist(coordinate1, coordinate2)){
            throw new Error("tile did not have connections");
        };
    }

    @Then("^the gameboard should accept the tile at \\((\\d+),(\\d+)\\)$")
    public void the_gameboard_should_accept_the_tile_at(int coordinate1, int coordinate2) throws Throwable {
        if(!app.doesTileExist(coordinate1, coordinate2)){
            throw new Error("tile did not have connections");
        };
    }

}

