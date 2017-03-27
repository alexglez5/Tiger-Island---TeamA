package Tigerisland;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by vincentibarrola on 3/24/17.
 */
public class VillagerPlacementTest{
        App app = new App();

        @Given("^the player chooses to found a settlement at \\((\\d+),(\\d+)\\)$")
        public void the_player_chooses_to_found_a_settlement(int coordinatex, int coordinatey) throws Throwable {
            app.givePlayerTile("Lakes", "Grasslands", app.currentTurnNumber);
            app.placeTile("FromTop", 1,0, 0 );
            if(app.isEmptyBoard()){
                throw new Error("board is empty");
            }
            app.buildVillager(coordinatex, coordinatey);

        }

        @When("^there is a open hex on level one$")
        public void there_is_a_open_hex_on_level_one() throws Throwable {
            if(app.isEmptyBoard()){
                throw new Error("board is empty");
            }
        }

        @When("^the hex is not of volcano terrain$")
        public void the_tile_is_not_of_volcano_terrain() throws Throwable {

        }

        @When("^the player has a villager left$")
        public void the_player_has_a_villager_left() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Then("^the player will place (\\d+) villager on that hex$")
        public void the_player_will_place_villager_on_that_hex(int arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

}
