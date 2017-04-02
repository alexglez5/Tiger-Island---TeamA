package UnitTests;

import Tigerisland.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by nathanbarnavon on 4/1/17.
 */
public class CoordinateTest {

    Coordinate a;
    Coordinate b;

    @Before
    public void setup() throws Exception {
        Coordinate a = new Coordinate(1,1,1);
        Coordinate b = new Coordinate(1,1,1);

    }

    @Test
    public void testTwoCoordinatesAreConsideredEqual() throws Exception {
        Assert.assertEquals(a,b);
    }

    @Test
    public void testHashMapCanGetFromKey() throws Exception {
        HashMap<Coordinate, Integer> h = new HashMap<>();
        h.put(a,1);
        int test = h.get(a);
        Assert.assertNotNull(test);
    }
}
