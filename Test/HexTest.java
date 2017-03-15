import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by NotKali on 3/14/2017.
 */
public class HexTest {
    private Hex.Feature features = Hex.Feature.VOLCANO;
    int [] sidesConnected = {0,0,0,0,0,1};

    Hex firstTile = new Hex(sidesConnected, features);

    @Test
    public void getSidesConnected() throws Exception {
        assertArrayEquals(sidesConnected,firstTile.getSidesConnected());
    }

    @Test
    public void testConnected() throws Exception {
        assertTrue(firstTile.isConnected());
    }

    @Test
    public void testFeatures() throws Exception {
        assertTrue(firstTile.hasFeatures());
    }
}

