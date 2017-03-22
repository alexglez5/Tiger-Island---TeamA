package Tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import java.lang.Error;
/**
 * Created by NotKali on 3/21/2017.
 */



public class FirstTilePlacementStepsATest {

    App app = new App();

    @Given("^the board is empty$")
    public void the_board_is_empty() throws Throwable {    // Write code here that turns the phrase above into concrete actions
        if(!app.isEmptyBoard()){
            throw new Error("didn't work");
        }
    }

    @When("^the player places the number (\\d+) tile at \\((\\d+),(\\d+)\\) and orientation \"([^\"]*)\"$")
    public void the_player_places_the_number_tile_at_and_orientation(int ID, int xcoordinate, int ycoordinate, String orientation) throws Throwable {
        app.placeTile(orientation, ID,xcoordinate, ycoordinate );
    }

    @Then("^the tile left hex should be placed at \\((-?\\d+),(\\d+)\\)$")
    public void the_tile_left_hex_should_be_placed_at(int coordinate1x, int coordinate1y) throws Throwable {
        if(!app.doesTileExist(coordinate1x, coordinate1y)){
            throw new Error("didn't work");
        }
    }

    @Then("^the tile right hex should be placed at \\((\\d+),(\\d+)\\)$")
    public void the_tile_right_hex_should_be_placed_at(int coordinate2x, int coordinate2y) throws Throwable {
        if(!app.doesTileExist(coordinate2x, coordinate2y)){
            throw new Error("didn't work");
        }
    }

    @Then("^the tile volcano hex should be placed at \\((\\d+),(\\d+)\\)$")
    public void the_tile_volcano_hex_should_be_placed_at(int coordinate3x, int coordinate3y) throws Throwable {
        if(!app.doesTileExist(coordinate3x, coordinate3y)){
            throw new Error("didn't work");
        }
    }

    //Next Acceptance Test

}
