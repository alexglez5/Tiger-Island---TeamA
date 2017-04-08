package Tigerisland;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ExpandSettlementTest {
    App app = new App();

    @Given("^the player wants to \"([^\"]*)\"$")
    public void the_player_wants_to(String choice) throws Throwable {
        app.updateCurrentTile("LAKE", "Grassland");
        app.placeTile("FromBottom",0, 0);
//        if (!app.checkPlayerChoice(choice)) {
//            throw new Error("Wrong Player Choice");
//        }
    }

    @When("^there is a settlement at \\((\\d+), (\\d+)\\) that can be expanded$")
    public void there_is_a_settlement_at_that_can_be_expanded(int coordinateX, int coordinateY) throws Throwable {
        app.buildVillager(coordinateX, coordinateY);
    }


    @When("^the hex at \\((\\d+), (\\d+)\\) is of \"([^\"]*)\" and has a settlement$")
    public void the_hex_at_is_of_and_has_a_settlement(int coordinateX, int coordinateY, String terrain) throws Throwable {
        if(!app.checkVillagers(coordinateX, coordinateY)) {
            throw new Error("the hex doesn't have any villagers placed");
        }
        if(app.isCorrectTerrain(coordinateX, coordinateY, terrain)){
            throw new Error("the terrain chosen was of type volcano");
        }
    }

    @When("^the hex at \\((-?\\d+), (-?\\d+)\\) is of \"([^\"]*)\" and has no settlement$")
    public void the_hex_at_is_of_and_has_no_settlement(int coordinateX, int coordinateY, String terrain) throws Throwable {
        if(app.checkVillagers(coordinateX, coordinateY)) {
            throw new Error("the hex has villagers placed");
        }
        if(app.isCorrectTerrain(coordinateX, coordinateY, terrain)){
            throw new Error("the terrain chosen was of type volcano");
        }
    }

    @Then("^the player will place (\\d+) villager at \\((-?\\d+), (-?\\d+)\\) with terrain type \"([^\"]*)\"$")
    public void the_player_will_place_villager_at_with_terrain_type(int numberOfVillagers, int coordinateX, int coordinateY, String terrain) throws Throwable {
        app.expandSettlementAt(coordinateX, coordinateY, terrain);
        if(app.checkVillagers(coordinateX,coordinateY)){
            throw new Error("the hex doesn't have any villagers placed");
        }
    }
}
