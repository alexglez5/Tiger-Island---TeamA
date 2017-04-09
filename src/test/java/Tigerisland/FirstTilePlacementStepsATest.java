package Tigerisland;

import cucumber.api.java.en.*;

import java.lang.Error;

public class FirstTilePlacementStepsATest {
    Game game = new Game();
    AI ai = new AI();
    Tile tile;

    @Given("^the board is empty is given tile with terrains \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_board_is_empty_is_given_tile_with_terrains_and(String terrainLeft, String terrainRight) throws Error {
        game.resetGame();
        TerrainType terrainLeftParsed = ai.parseTerrain(terrainLeft);
        TerrainType terrainRightParsed = ai.parseTerrain(terrainRight);
        tile = new Tile(terrainLeftParsed, terrainRightParsed);
    }

    @When("^the game places the tile at \\((\\d+),(\\d+)\\) and orientation \"([^\"]*)\"$")
    public void the_game_places_the_tile_at_and_orientation(int coordinateX, int coordinateY, String orientation) throws Error {
        Coordinate coordinate = new Coordinate(coordinateX,coordinateY);
        Orientation orientation1 = ai.parseOrientation(orientation);
        game.placeTile(tile, coordinate, orientation1);
    }

    @Then("^the tile left hex should be placed at \\((-?\\d+),(-?\\d+)\\) with terrain \"([^\"]*)\"$")
    public void the_tile_left_hex_should_be_placed_at_with_terrain(int coordinateX, int coordinateY, String terrain) throws Error {
        TerrainType terrain1 = ai.parseTerrain(terrain);
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(coordinate);
        if(hex == null) {
            new Error("There was no hex at the coordinate");
        }
        if(hex.getTerrainType().equals(terrain1)){
            new Error("The terrain was not correct");
        }
    }

    @Then("^the tile right hex should be placed at \\((-?\\d+),(-?\\d+)\\) with terrain \"([^\"]*)\"$")
    public void the_tile_right_hex_should_be_placed_at_with_terrain(int coordinateX, int coordinateY, String terrain) throws Error {
        TerrainType terrain1 = ai.parseTerrain(terrain);
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(coordinate);
        if(hex == null) {
            new Error("There was no hex at the coordinate");
        }
        if(hex.getTerrainType().equals(terrain1)){
            new Error("The terrain was not correct");
        }
    }

    @Then("^the tile volcano hex should be placed at \\((-?\\d+),(-?\\d+)\\) with terrain \"([^\"]*)\"$")
    public void the_tile_volcano_hex_should_be_placed_at_with_terrain(int coordinateX, int coordinateY, String terrain) throws Error {
        TerrainType terrain1 = ai.parseTerrain(terrain);
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        Hex hex = game.getBoard().get(coordinate);
        if(hex == null) {
            new Error("There was no hex at the coordinate");
        }
        if(hex.getTerrainType().equals(terrain1)){
            new Error("The terrain was not correct");
        }
    }
}
