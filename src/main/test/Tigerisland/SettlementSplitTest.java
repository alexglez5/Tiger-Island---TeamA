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
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottomRight);

        map.foundNewSettlement(new Coordinate(-1,1));
        map.foundNewSettlement(new Coordinate(0,1));
        map.foundNewSettlement(new Coordinate(1,1));
        map.foundNewSettlement(new Coordinate(2,0));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom);

        // since split is resolved automatically in placeTile, this test should say the settlement isnt split, since the
        // split settlement is already resolved into 2 settlements when it gets called.
        Assert.assertFalse(map.isSettlementSplit(map.getSettlements().get
                (map.getBoard().get(new Coordinate(-1,1)).getSettlementID())));
    }

    // tests if the isSplit() function correctly identifies if a settlement is split
    @Test
    public void testSettlementIsNotSplit() throws Exception{
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(0,1), Orientation.FromTopLeft);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(2,-2), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(1,-1));
        map.foundNewSettlement(new Coordinate(0,0));
        map.foundNewSettlement(new Coordinate(2,-1));
        map.foundNewSettlement(new Coordinate(2,0));
        map.foundNewSettlement(new Coordinate(1,1));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertFalse(map.isSettlementSplit(map.getSettlements().get
                (map.getBoard().get(new Coordinate(1,-1)).getSettlementID())));
    }

    // tests the resolution of the split
    @Test
    public void testSplitWhenRemainingSettlementsHaveSizeOne() {
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottomRight);

        map.foundNewSettlement(new Coordinate(-1,1));
        map.foundNewSettlement(new Coordinate(0,1));
        map.foundNewSettlement(new Coordinate(1,1));
        map.foundNewSettlement(new Coordinate(2,0));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertNotEquals(map.getBoard().get(new Coordinate(-1,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(2,0)).getSettlementID());
        Assert.assertEquals(map.getSettlements().size(),2);
    }

    @Test
    public void testSplitWhenRemainingSettlementsHaveSizeMoreThanOne() {
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(2,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(-2,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(-1,2), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(0,1));
        map.expandSettlement(new Coordinate(0,1), TerrainType.ROCK);
        Assert.assertEquals(map.getSettlements().get(map.getBoard().get(
                new Coordinate(0,1)).getSettlementID()).getSize(), 6);
        Assert.assertEquals(map.getSettlements().size(), 1);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
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
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(2,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(-1,1));
        map.expandSettlement(new Coordinate(-1,1), TerrainType.ROCK);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromTop);

        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.getSettlements().size(), 2);
    }

    @Test
    public void testSplitWithTwoPlayers() {
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(0,0), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(-1,2), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(1,1), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(2,-1), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(-1,0));
        map.foundNewSettlement(new Coordinate(-1,1));
        map.foundNewSettlement(new Coordinate(-2,2));

        map.switchPlayers();
        map.foundNewSettlement(new Coordinate(0,2));
        map.foundNewSettlement(new Coordinate(0,1));
        map.foundNewSettlement(new Coordinate(1,0));

        Assert.assertEquals(map.getSettlements().size(), 2);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(-1,2), Orientation.FromTop);

        Assert.assertEquals(map.getSettlements().size(), 4);

    }

    @Test
    public void integrationTestForSettlementManagement() {
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(-1,1));
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,1), Orientation.FromTop);
        map.foundNewSettlement(new Coordinate(2,0));
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,1), Orientation.FromTopLeft);
        map.expandSettlement(new Coordinate(2,0), TerrainType.GRASS);
        Assert.assertEquals(map.getSettlements().size(), 2);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE),
                new Coordinate(-2,1), Orientation.FromTop);
        map.foundNewSettlement(new Coordinate(-1, 0));
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE),
                new Coordinate(-2,2), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.GRASS),
                new Coordinate(-2,2), Orientation.FromTop);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1, 0).hashCode()).getSize(), 1);
        map.expandSettlement(new Coordinate(-1,0), TerrainType.GRASS);
        Assert.assertEquals(map.getSettlements().size(), 2);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1, 0).hashCode()).getSize(), 3);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE),
                new Coordinate(-1,2), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE),
                new Coordinate(0,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE),
                new Coordinate(0,2), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.LAKE),
                new Coordinate(-2, 2), Orientation.FromTopRight);
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,1)).getLevel(), 3);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1, 0).hashCode()).getSize(), 2);
        map.expandSettlement(new Coordinate(-2,1), TerrainType.JUNGLE);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1, 0).hashCode()).getSize(), 9);
        Assert.assertEquals(map.getSettlements().size(), 2);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.LAKE),
                new Coordinate(0,-1), Orientation.FromTop);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(0,0), Orientation.FromTopLeft);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1, 0).hashCode()).getSize(), 8);
        Assert.assertEquals(map.getSettlements().size(), 2);
        map.placeTotoro(new Coordinate(-1,0));
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1, 0).hashCode()).getSize(), 9);
        Assert.assertEquals(map.getSettlements().size(), 2);
        map.expandSettlement(new Coordinate(2,0), TerrainType.ROCK);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(2,0).hashCode()).getSize(), 3);
        Assert.assertEquals(map.getSettlements().size(), 2);
        map.placeTiger(new Coordinate(-1,1));
        Assert.assertEquals(map.getSettlements().size(), 1);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1,0).hashCode()).getSize(), 13);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.LAKE),
                new Coordinate(-4,3), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(-4,3), Orientation.FromTopRight);
        Assert.assertEquals(map.getSettlements().size(), 2);
        Assert.assertNotNull(map.getSettlements().get(new Coordinate(-1,0).hashCode()));
        Assert.assertEquals(map.getSettlements().get(new Coordinate(-1,0).hashCode()).getSize(), 7);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.LAKE),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.getSettlements().size(), 3);
    }

    @Test
    public void testExpandMergesWithSettlementOfOnlyTotoro() {
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE),
                new Coordinate(0,0), Orientation.FromTop);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.JUNGLE),
                new Coordinate(2,-1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(3,-2), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(0,-1));
        map.expandSettlement(new Coordinate(0,-1), TerrainType.JUNGLE);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(0,-1).hashCode()).getSize(), 5);
        map.placeTotoro(new Coordinate(4,-2));
        Assert.assertEquals(map.getSettlements().get(new Coordinate(0,-1).hashCode()).getSize(), 6);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.LAKE),
                new Coordinate(3,-2), Orientation.FromBottom);
        Assert.assertEquals(map.getSettlements().size(), 2);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(0,-1).hashCode()).getSize(), 4);
        map.expandSettlement(new Coordinate(1,0), TerrainType.JUNGLE);
        Assert.assertEquals(map.getSettlements().get(new Coordinate(0,-1).hashCode()).getSize(), 6);
        Assert.assertEquals(map.getSettlements().size(), 1);
    }


    @After
    public void teardown() {
        map.resetGame();
    }
}
