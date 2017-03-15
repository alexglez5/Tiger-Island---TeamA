import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by NotKali on 3/14/2017.
 */
public class HexTest {
    private Hex.Feature features = Hex.Feature.VOLCANO;

    Hex firstTile = new Hex(features);

    @Test
    public void testFeatures() throws Exception {
        assertTrue(firstTile.hasFeatures());
    }
}

