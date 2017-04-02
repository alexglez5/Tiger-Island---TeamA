package UnitTests;

import Tigerisland.*;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/2/17.
 */
public class TileValidatorTest {

    Game game = new Game();
    TileValidator validator = new TileValidator();

    @Test
    public void testPlacementOnLevelOne() throws Exception {
        game.placeFirstTile();
    }



}
