package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/31/2017.
 */
public class BuildWithPointsAndPiecesTest {
    Game map;

    @Before
    public void setUpTests() {
        map = new Game();
    }

    @Test
    public void foundSettlementAndCheckPlayerScoreAndVillagerUpdated() {
        map.getPlayer().setPlayerID(1);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCKY), new Coordinate(0, 0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0, 1));
        Assert.assertEquals(map.getPlayer().getNumberOfVillagersLeft(), 19);
        Assert.assertEquals(map.getPlayer().getPoints(), 1);
        Assert.assertEquals(map.getPlayer().getPlayerID(), 1);
    }

    @Test
    public void expandSettlementAndCheckScore() {
        map.getPlayer().setPlayerID(2);
        Assert.assertEquals(map.getPlayer().getPlayerID(), 2);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCKY),
                new Coordinate(0, 0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0, 1));
        Assert.assertEquals(map.getPlayer().getPoints(), 1);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.ROCKY),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);
        map.expandSettlement(new Coordinate(0, 1), TerrainType.ROCKY);
        Assert.assertEquals(map.getPlayer().getPoints(), 5);
        map.placeTile(new Tile(TerrainType.GRASSLANDS, TerrainType.LAKE),
                new Coordinate(-2, 1), Orientation.FromBottom);
        map.placeTotoro(new Coordinate(-1, 1));

        Assert.assertEquals(map.getPlayer().getPoints(), 205);
        Assert.assertEquals(map.getPlayer().getNumberOfTotoroLeft(), 2);
    }

    @Test
    public void addTigerPlaygrouundWithPoints() {
        map.getPlayer().setPlayerID(3);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCKY),
                new Coordinate(0, 0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS),
                new Coordinate(0, 0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.ROCKY),
                new Coordinate(0, -1), Orientation.FromTop);
        map.placeTile(new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY),
                new Coordinate(2, -2), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS),
                new Coordinate(0, -1), Orientation.FromTopRight);
        map.foundNewSettlement(new Coordinate(2, -1));
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS),
                new Coordinate(0, -1), Orientation.FromBottomRight);
        Assert.assertEquals(map.getBoard().get(new Coordinate(2, -1)).getLevel(), 1);
        Assert.assertEquals(map.getBoard().get(new Coordinate(1, -1)).getLevel(), 3);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0, 0)).getLevel(), 3);
        Assert.assertFalse(map.getBoard().get(new Coordinate(1, -1)).getTerrainType() == TerrainType.VOLCANO);
        Assert.assertTrue(map.getBoard().get(new Coordinate(2, -1)).hasVillager());
        map.placeTiger(new Coordinate(1, -1));
        Assert.assertEquals(map.getPlayer().getNumberOfTigersLeft(), 1);
        Assert.assertEquals(map.getPlayer().getPoints(), 76);
    }

    @Test
    public void MergeSettlementTest() {
        map.getPlayer().setPlayerID(1);
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCKY), new Coordinate(0, 0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0, 1));
        map.foundNewSettlement(new Coordinate(-1, 1));
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1, 1)).getSettlementID(), map.getBoard().get(new Coordinate(0, 1)).getSettlementID());
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1, 1)).getPlayerID(), map.getBoard().get(new Coordinate(0, 1)).getPlayerID());
    }

    @Test
    public void testSettlementsAreNotMergedWhenDifferentPlayerIDs() throws Exception {
        map.getPlayer().setPlayerID(4);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.LAKE),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.ROCKY),
                new Coordinate(2, 1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS),
                new Coordinate(1, 3), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.ROCKY),
                new Coordinate(-2, 2), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(1, 1));

        map.getPlayer().setPlayerID(5);
        map.foundNewSettlement(new Coordinate(-2, 3));
        Assert.assertTrue(map.getBoard().get(new Coordinate(-2, 3)).hasVillager());
       Assert.assertTrue(map.getBoard().get(new Coordinate(1, 1)).hasVillager());
        Assert.assertTrue(map.getBoard().get(new Coordinate(-2, 3)).getPlayerID() !=
                map.getBoard().get(new Coordinate(1, 1)).getPlayerID());
        map.expandSettlement(new Coordinate(-2, 3), TerrainType.ROCKY);
        Assert.assertTrue(map.getBoard().get(new Coordinate(-1, 3)).hasVillager() && map.getBoard().get(new Coordinate(-1, 3)).getTerrainType() == TerrainType.ROCKY);
        Assert.assertTrue(map.getBoard().get(new Coordinate(2, 2)).hasVillager() && map.getBoard().get(new Coordinate(2, 2)).getTerrainType() == TerrainType.ROCKY);
        Assert.assertTrue(map.getBoard().get(new Coordinate(1, 2)).hasVillager() && map.getBoard().get(new Coordinate(1, 2)).getTerrainType() == TerrainType.ROCKY);
        Assert.assertTrue(map.getBoard().get(new Coordinate(0, 3)).hasVillager() && map.getBoard().get(new Coordinate(0, 3)).getTerrainType() == TerrainType.ROCKY);
        Assert.assertFalse(map.getBoard().get(new Coordinate(0, 2)).hasVillager() && map.getBoard().get(new Coordinate(0, 2)).getTerrainType() == TerrainType.ROCKY);
        Assert.assertFalse(map.getBoard().get(new Coordinate(0, 2)).hasVillager());
        Assert.assertFalse(map.getBoard().get(new Coordinate(0, 3)).hasTotoro());
        map.placeTotoro(new Coordinate(0, 2));
        Assert.assertEquals(map.getBoard().get(new Coordinate(1, 1)).getPlayerID(), 4);
        Assert.assertTrue(map.getBoard().get(new Coordinate(1, 1)).getPlayerID() != map.getBoard().get(new Coordinate(0, 2)).getPlayerID());
        Assert.assertTrue(map.getBoard().get(new Coordinate(0, 2)).hasTotoro());
        Assert.assertEquals(map.getBoard().get(new Coordinate(-2, 3)).getSettlementID(),
                map.getBoard().get(new Coordinate(0, 2)).getSettlementID());
        Assert.assertTrue(map.getBoard().get(new Coordinate(1, 1)).getSettlementID() !=
                map.getBoard().get(new Coordinate(0, 2)).getSettlementID());
    }

    @After
    public void deallocateHexesInMap() throws Exception {
        map.resetGame();
    }
}