package Tigerisland;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InvalidVillagerPlacementTest {
    App app = new App();

    @Given("^the player chooses to \"([^\"]*)\"$")
    public void the_player_chooses_to(String choice) throws Throwable {
        app.updateCurrentTile("Lake", "Grassland");
        app.placeTile("FromBottom", 0, 0);
//        app.givePlayerChoice(choice);
//        if (!app.checkPlayerChoice(choice)) {
//            throw new Error("Wrong Player Choice");
//        }
    }

    @When("^there is an open hex on level one$")
    public void there_is_an_open_hex_on_level_one() throws Throwable {
        if (app.isEmptyBoard()) {
            throw new Error("board is empty");
        }
    }

    @When("^the hex at \\((-?\\d+), (-?\\d+)\\) is of \"([^\"]*)\" terrain$")
    public void the_hex_at_is_of_terrain(int coordinateX, int coordinateY, String terrain) throws Throwable {
        if (!app.isCorrectTerrain(coordinateX, coordinateY, terrain)) {
            throw new Error("the terrain chosen was valid");
        }
    }

    @Then("^the player cannot place (\\d+) villager at \\((\\d+),(\\d+)\\)$")
    public void the_player_cannot_place_villager_at(int numberOfVillagers, int coordinateX, int coordinateY) throws Throwable {
        app.buildVillager(coordinateX, coordinateY);
        if(app.checkVillagers(coordinateX, coordinateY)) {
            throw new Error("the hex has villagers despite incorrect terrain");
        }
    }
}