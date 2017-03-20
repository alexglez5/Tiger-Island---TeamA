//package Tigerisland;
//
//import cucumber.api.PendingException;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//
///**
// * Created by nathanbarnavon on 3/18/17.
// */
//public class TilePlacementSteps {
//
//    private GameBoard map = new GameBoard();
//    private Tile newTile = new Tile(TerrainType.Grasslands, TerrainType.Jungle);
//
//    @When("^the player places a tile with identifier \"([^\"]*)\"$")
//    public void the_player_places_a_tile_with_identifier(String firstTileID) throws Throwable {
//        newTile.setTileID(firstTileID);
//        map.placeTile(newTile, Orientation.FromBottom);
//    }
//
//    @Then("^the volcano hex is placed at coordinate \\((\\d+),(\\d+)\\)$")
//    public void the_volcano_hex_is_placed_at_coordinate(int xCord, int yCord) throws Throwable {
//
//    }
//}
