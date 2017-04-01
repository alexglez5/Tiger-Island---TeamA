package Tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by NotKali on 3/31/2017.
 */
public class ExpandSettlementAtMultipleHexes {
    App app = new App();

    @Given("^the player has the desire to \"([^\"]*)\"$")
    public void the_player_has_the_desire_to(String choice) throws Throwable {
        app.givePlayerTile("Lake", "Grassland", app.currentTurnNumber);
        app.placeTile("FromBottom", app.currentTurnNumber, 0, 0);
        app.givePlayerTile("Grassland", "Grassland", app.currentTurnNumber);
        app.placeTile("FromTop", app.currentTurnNumber, 1, 1);
        app.givePlayerChoice(choice);
        if (!app.checkPlayerChoice(choice)) {
            throw new Error("Wrong Player Choice");
        }
    }

    @Given("^more than one hex from \\((\\d+), (\\d+)\\) is available of that terrain type to expand into$")
    public void more_than_one_hex_from_is_available_of_that_terrain_type_to_expand_into(int coordinateX, int coordinateY) throws Throwable {
        if(!app.existsMultipleHexesOfTerrainTypeThatAreAdjacent(coordinateX, coordinateY)){
            throw new Error("Does not exist multiple hexes of terrain type that are adjacent");
        }
    }

    @When("^there is a settlement at \\((\\d+) , (\\d+)\\) that can be expanded$")
    public void there_is_a_settlement_at_that_can_be_expanded(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the hex at \\((\\d+) , (\\d+)\\) is of \"([^\"]*)\" and has a settlement$")
    public void the_hex_at_is_of_and_has_a_settlement(int arg1, int arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
