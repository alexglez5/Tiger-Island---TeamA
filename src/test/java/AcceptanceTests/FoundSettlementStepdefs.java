package AcceptanceTests;

import Tigerisland.*;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sun.security.util.PendingException;

/**
 * Created by nathanbarnavon on 4/4/17.
 */
public class FoundSettlementStepdefs {

    @When("^the player (\\d+) chooses to \"([^\"]*)\" at (-?\\d+),(-?\\d+),(-?\\d+)$")
    public void the_player_chooses_to_at(int pid, String buildOpt, int x, int y, int z) throws Throwable {

    }

    @Then("^a new settlement is created with size (\\d+)$")
    public void a_new_settlement_is_created_with_size(int size) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
