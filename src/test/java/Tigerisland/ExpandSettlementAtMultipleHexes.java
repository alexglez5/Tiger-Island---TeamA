package Tigerisland;

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
        app.updateCurrentTile("Lake", "Grassland");
        app.placeTile("FromBottom", 0, 0);
        app.updateCurrentTile("Grassland", "Grassland");

        app.placeTile("FromTop", 1, 1);
//        app.givePlayerChoice(choice);
//        if (!app.checkPlayerChoice(choice)) {
//            throw new Error("Wrong Player Choice");
//        }
    }

    @When("^there is a settlement at \\((-?\\d+) , (-?\\d+)\\) that can be expanded$")
    public void there_is_a_settlement_at_that_can_be_expanded(int coordinateX, int coordinateY) throws Throwable {
        app.buildVillager(coordinateX, coordinateY);
        if(!app.checkVillagers(coordinateX, coordinateY)) {
            throw new Error("the hex didn't have villagers");
        }
    }

    @Then("^the hexes at \\((-?\\d+), (-?\\d+)\\) and \\((-?\\d+), (-?\\d+)\\) have settlements$")
    public void the_hexes_at_and_have_settlements(int coordinateX, int coordinateY, int coordinateX1, int coordinateY1) throws Throwable {
        app.buildVillager(coordinateX, coordinateY);
        if(!app.checkVillagers(coordinateX, coordinateY)) {
            throw new Error("the hex didn't have villagers");
        }
        if(!app.checkVillagers(coordinateX1, coordinateY1)) {
            throw new Error("the hex didn't have villagers");
        }
    }

}
