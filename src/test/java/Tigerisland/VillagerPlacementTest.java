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
            app.givePlayerTile("Lake", "Grassland", app.currentTurnNumber);
            app.placeTile("FromBottom", 1,0, 0 );
            if(app.isEmptyBoard()){
                throw new Error("board is empty");
            }


        }

        @When("^there is a open hex on level one$")
        public void there_is_a_open_hex_on_level_one() throws Throwable {
            if(app.isEmptyBoard()){
                throw new Error("board is empty");
            }
        }

        @When("^the hex at \\((-?\\d+), (-?\\d+)\\) is not of \"([^\"]*)\" terrain$")
        public void the_hex_at_is_not_of_terrain(int coordinateX, int coordinateY, String volcano) throws Throwable {
            System.out.println(coordinateX + " " + coordinateY);
            if(app.isCorrectTerrain(coordinateX, coordinateY, volcano)){
                throw new Error("the terrain chosen was of type volcano");
            }
        }

        @When("^the player has a villager left$")
        public void the_player_has_a_villager_left() throws Throwable {
            if(app.player1.getNumberOfVillagersLeft()==0) {
                throw new Error("the player doesn't have any villagers left");
            }
        }

        @Then("^the player will place (\\d+) villager on that hex at \\((\\d+), (\\d+)\\)$")
        public void the_player_will_place_villager_on_that_hex_at(int numberOfVillagers, int coordinatex, int coordinatey) throws Throwable {
            app.buildVillager(coordinatex, coordinatey);
            if(!app.checkVillagers(coordinatex, coordinatey)) {
                throw new Error("the tile doesn't have any villagers placed");
            }
        }
}
