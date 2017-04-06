package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SettlementTest {
    Settlement s;

    @Before
    public void setup() {
        s = new Settlement(new Coordinate(0,0));
    }

    @Test
    public void shouldAddCoordinateToSettlement() {
        s.addToSettlement(new Coordinate(0,1));
        Assert.assertTrue(s.contains(new Coordinate(0,1)));
        Assert.assertEquals(s.getSize(), 2);
    }

    @Test
    public void shouldRemoveCoordinateFromSettlement() {
        s.addToSettlement(new Coordinate(0,1));
        s.removeFromSettlement(new Coordinate(0,0));
        Assert.assertFalse(s.contains(new Coordinate( 0,0)));
        Assert.assertEquals(s.getSize(), 1);
    }

    @Test
    public void shouldPassBFS() {
        s.addToSettlement(new Coordinate(0,1));
        s.addToSettlement(new Coordinate(0,2));
        s.addToSettlement(new Coordinate(0,3));
        int size = s.bfs().size();
        Assert.assertEquals(4, size);
    }

    @After
    public void teardown() {

    }

}