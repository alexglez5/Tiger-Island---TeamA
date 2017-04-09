package Tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.Error;

public class ATLevel2Placement {
    Game game = new Game();
    Tile tile;

    @Given("^that the board has at least (\\d+) tiles$")
    public void that_the_board_has_at_least_tiles(int arg1) throws Error {
        game.resetGame();
        tile = new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
        game.placeTile(tile,new Coordinate(0,0), Orientation.FromBottom);
        if(!game.getBoard().containsKey(new Coordinate(0,0))){
            throw new Error("couldn't place tile at (0, 0)");
        }
        tile = new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
        game.placeTile(tile,new Coordinate(1,-2), Orientation.FromBottom);
        if(!game.getBoard().containsKey(new Coordinate(1,-2))){
            throw new Error("couldn't place tile at (1, -2)");
        }
    }

    @Given("^a valid level two placement option exists at \\((-?\\d+),(-?\\d+)\\)$")
    public void a_valid_level_two_placement_option_exists_at(int arg1, int arg2) throws Throwable {
        if(true) {}; // need to implement return valid options
    }

    @When("^the player places a tile on level (\\d+) at a valid level two location$")
    public void the_player_places_a_tile_on_level_at_a_valid_level_two_location(int arg1) throws Throwable {
        tile = new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
    }

    @When("^the level two tile's origin will be at \\((-?\\d+),(-?\\d+)\\) with orientation \"([^\"]*)\"$")
    public void the_level_two_tile_s_origin_will_be_at_with_orientation(int coordinateX, int coordinateY, String orientation) throws Throwable {
        game.placeTile(tile, new Coordinate(coordinateX,coordinateY) ,Orientation.FromBottom );
    }

    @Then("^the gameboard should accept the tile at \\((-?\\d+),(-?\\d+)\\) at level (\\d+)$")
    public void the_gameboard_should_accept_the_tile_at_at_level(int coordinateX, int coordinateY, int level) throws Throwable {
//        if(!game.getBoard().get(new Coordinate(coordinateX,coordinateY)).equals(tile.getMainTerrain())){
//            throw new Error("Tile is not on correct level");
//        }
        throw new PendingException();
    }

    //Illegal


    @Given("^that the board has at least (\\d+) tiles from game start$")
    public void that_the_board_has_at_least_tiles_from_game_start(int arg1) throws Throwable {
        game.resetGame();
        tile = new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
        game.placeTile(tile,new Coordinate(0,0), Orientation.FromBottom);
        if(!game.getBoard().containsKey(new Coordinate(0,0))){
            throw new Error("couldn't place tile at (0, 0)");
        }
        tile = new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
        game.placeTile(tile,new Coordinate(1,-2), Orientation.FromBottom);
        if(!game.getBoard().containsKey(new Coordinate(1,-2))){
            throw new Error("couldn't place tile at (1, -2)");
        }
    }
    @When("^the player places a tile on level (\\d+) at a invalid level two location$")
    public void the_player_places_a_tile_on_level_at_a_invalid_level_two_location(int arg1) throws Throwable {
        tile = new Tile(TerrainType.GRASSLANDS, TerrainType.GRASSLANDS);
    }

    @When("^the level two tile's origin will be at an incorrect coordinate \\((\\d+),-(\\d+)\\) with orientation \"([^\"]*)\"$")
    public void the_level_two_tile_s_origin_will_be_at_an_incorrect_coordinate_with_orientation(int coordinateX, int coordinateY, String orientation) throws Throwable {
        game.placeTile(tile, new Coordinate(coordinateX,coordinateY) ,Orientation.FromBottom );

    }

    @Then("^the gameboard should not accept the tile at \\((\\d+),-(\\d+)\\) at level (\\d+)$")public void the_gameboard_should_not_accept_the_tile_at_at_level(int arg1, int arg2, int arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
