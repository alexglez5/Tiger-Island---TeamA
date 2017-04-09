package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/8/17.
 */
public class SettlementSplitTest {
    Game map;

    @Before
    public void setup() {
        map = new Game();
    }

    // tests if the isSplit() function correctly identifies if a settlement is split
    @Test
    public void testSettlementIsSplit() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);

        map.foundNewSettlement(new Coordinate(-1,1));
        map.foundNewSettlement(new Coordinate(0,1));
        map.foundNewSettlement(new Coordinate(1,1));
        map.foundNewSettlement(new Coordinate(2,0));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        // since split is resolved automatically in placeTile, this test should say the settlement isnt split, since the
        // split settlement is already resolved into 2 settlements when it gets called.
        Assert.assertFalse(map.isSettlementSplit(map.getSettlements().get
                (map.getBoard().get(new Coordinate(-1,1)).getSettlementID())));
    }

    // tests if the isSplit() function correctly identifies if a settlement is split
    @Test
    public void testSettlementIsNotSplit() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,1), Orientation.FromTopLeft);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(2,-2), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(1,-1));
        map.foundNewSettlement(new Coordinate(0,0));
        map.foundNewSettlement(new Coordinate(2,-1));
        map.foundNewSettlement(new Coordinate(2,0));
        map.foundNewSettlement(new Coordinate(1,1));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertFalse(map.isSettlementSplit(map.getSettlements().get
                (map.getBoard().get(new Coordinate(1,-1)).getSettlementID())));
    }

    // tests the resolution of the split
    @Test
    public void testSplitWhenRemainingSettlementsHaveSizeOne() {
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);

        map.foundNewSettlement(new Coordinate(-1,1));
        map.foundNewSettlement(new Coordinate(0,1));
        map.foundNewSettlement(new Coordinate(1,1));
        map.foundNewSettlement(new Coordinate(2,0));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertNotEquals(map.getBoard().get(new Coordinate(-1,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(2,0)).getSettlementID());
        Assert.assertEquals(map.getSettlements().size(),2);
    }

    @Test
    public void testSplitWhenRemainingSettlementsHaveSizeMoreThanOne() {
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(2,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(-2,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(0,1));
        map.expandSettlement(new Coordinate(0,1), TerrainType.Rocky);
        Assert.assertEquals(map.getSettlements().get(map.getBoard().get(
                new Coordinate(0,1)).getSettlementID()).getSize(), 6);
        Assert.assertEquals(map.getSettlements().size(), 1);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromTop);

        Assert.assertNotEquals(map.getBoard().get(new Coordinate(-2,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(2,1)).getSettlementID());
        Assert.assertEquals(map.getSettlements().get(map.getBoard().get(new Coordinate(-2,1)).getSettlementID()).getSize()
                , 2);
        Assert.assertEquals(map.getSettlements().get(map.getBoard().get(new Coordinate(2,1)).getSettlementID()).getSize()
                , 2);
    }

    @Test
    public void testIfItWorksWhenOriginalFounderIsTheOneToSplit() {
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(2,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(-1,1));
        map.expandSettlement(new Coordinate(-1,1), TerrainType.Rocky);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromTop);

        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.getSettlements().size(), 2);
    }

    @After
    public void teardown() {
        map.resetGame();
    }
}
