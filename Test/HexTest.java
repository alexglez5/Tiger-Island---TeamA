import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by NotKali on 3/14/2017.
 */
public class HexTest {
    private Hex.TerrainType terrain = Hex.TerrainType.VOLCANO;

    Hex firstTile = new Hex(terrain);
    @Test
    public void hasFeatures() throws Exception {
        assertTrue(firstTile.hasFeatures());
    }
}

