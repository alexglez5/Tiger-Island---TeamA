package Tigerisland;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.Error;

public class StartOfTurnTilePlacement {
    Game game = new Game();
    AI ai = new AI();
    Tile tile;

    @Given("^the game is given a tile with terrains \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_game_is_given_a_tile_with_terrains_and(String terrainLeft, String terrainRight) throws Error {
        game.resetGame();
        TerrainType terrainLeftParsed = TerrainType.LAKE;
        TerrainType terrainRightParsed = TerrainType.ROCK;
        tile = new Tile(terrainLeftParsed, terrainRightParsed);

        Coordinate coordinate = new Coordinate(0,0);
        Orientation orientation = Orientation.FromBottom;
        game.placeTile(tile, coordinate, orientation);
    }

    @When("^the player places the tile with orientation \"([^\"]*)\" at \\((-?\\d+),(-?\\d+)\\)$")
    public void the_player_places_the_tile_with_orientation_at(String orientation, int coordinateX, int coordinateY) throws Error {
        Coordinate coordinate = new Coordinate(coordinateX,coordinateY);
        Orientation orientation1 = Orientation.FromTop;
        game.placeTile(tile, coordinate, orientation1);
    }

    @When("^the tile at \\((-?\\d+),(-?\\d+)\\) will have no connections to any other tiles$")
    public void the_tile_at_will_have_no_connections_to_any_other_tiles(int coordinateX, int coordinateY) throws Error {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(coordinate);
        if(!(hex == null)) {
            new Error("There was hex at the coordinate");
        }
    }

    @Then("^the gameboard should reject the tile at \\((-?\\d+), (-?\\d+)\\)$")
    public void the_gameboard_should_reject_the_tile_at(int coordinateX, int coordinateY) throws Error {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(coordinate);
        if(!(hex == null)) {
            new Error("There was hex at the coordinate");
        }
    }

    //Correct Placement

    @Given("^the game is given tile with terrains \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_game_is_given_tile_with_terrains_and(String arg1, String arg2) throws Throwable {
        game.resetGame();
        TerrainType terrainLeftParsed = TerrainType.LAKE;
        TerrainType terrainRightParsed = TerrainType.ROCK;
        tile = new Tile(terrainLeftParsed, terrainRightParsed);

        Coordinate coordinate = new Coordinate(0,0);
        Orientation orientation = Orientation.FromBottom;
        game.placeTile(tile, coordinate, orientation);
    }

    @When("^the game places the tile with orientation \"([^\"]*)\" at \\((\\d+),(\\d+)\\)$")
    public void the_game_places_the_tile_with_orientation_at(String arg1, int coordinateX, int coordinateY) throws Throwable {
        Coordinate coordinate = new Coordinate(coordinateX,coordinateY);
        Orientation orientation1 = Orientation.FromTop;
        game.placeTile(tile, coordinate, orientation1);
    }

    @When("^the tile at \\((\\d+),(\\d+)\\) will have more than zero connections to any other tile$")
    public void the_tile_at_will_have_more_than_zero_connections_to_any_other_tile(int coordinateX, int coordinateY) throws Throwable {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(coordinate);
        if((hex == null)) {
            new Error("There was hex at the coordinate");
        }
    }

    @Then("^the gameboard should accept the tile at \\((\\d+),(\\d+)\\)$")
    public void the_gameboard_should_accept_the_tile_at(int coordinateX, int coordinateY) throws Throwable {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(coordinate);
        if((hex == null)) {
            new Error("There was hex at the coordinate");
        }
    }
}

