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
    public void setUpTests(){
        map = new Game();
    }

    @Test
    public void foundSettlementAndCheckPlayerScoreAndVillagerUpdated(){
        map.player.setPlayerID(1);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky), new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0,1));
        Assert.assertEquals(map.player.getNumberOfVillagersLeft(), 19);
        Assert.assertEquals(map.player.getPoints(), 1);
        Assert.assertEquals(map.player.getPlayerID(),1);
    }

    @Test
    public void expandSettlementAndCheckScore(){
        map.player.setPlayerID(2);
        Assert.assertEquals(map.player.getPlayerID(),2);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0,1));
        System.out.println("Points: " + map.player.getPoints());
        Assert.assertEquals(map.player.getPoints(), 1);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.expandSettlement(new Coordinate(0,1), TerrainType.Rocky);
        System.out.println("Points: " + map.player.getPoints());
        Assert.assertEquals(map.player.getPoints(), 5);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Lake),
                new Coordinate(-2,1), Orientation.FromBottom);
        map.placeTotoro(new Coordinate(-1,1));

        Assert.assertEquals(map.player.getPoints(),205);
        System.out.println("Points: " + map.player.getPoints());
        Assert.assertEquals(map.player.getNumberOfTotoroLeft(),2);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getWhichPlayerID(), 2);
    }

    @Test
    public void addTigerPlaygrouundWithPoints(){
        map.player.setPlayerID(3);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,-1), Orientation.FromTop);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(2,-2), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,-1), Orientation.FromTopRight);
        map.foundNewSettlement(new Coordinate(2,-1));
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,-1), Orientation.FromBottomRight);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(2,-1)).getLevel(),1);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,-1)).getLevel(),3);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getLevel(),3);
        //System.out.println("Level: " + map.gameBoard.get(new Coordinate(0,-1)).getLevel());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(1,-1)).getTerrainType() == TerrainType.Volcano);
        Assert.assertTrue(map.gameBoard.get(new Coordinate(2,-1)).hasVillager());
        map.placeTiger(new Coordinate(1,-1));
        System.out.println("Points: " + map.player.getPoints());
        Assert.assertEquals(map.player.getNumberOfTigersLeft(), 1);
        Assert.assertEquals(map.player.getPoints(), 76);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,-1)).getWhichPlayerID(), 3);
    }

//    @Test
//    public void MergeSettlementTest(){
//        map.player.setPlayerID(1);
//        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky), new Coordinate(0,0), Orientation.FromBottom);
//        map.foundNewSettlement(new Coordinate(0,1));
//        map.foundNewSettlement(new Coordinate(-1,1));
//        //Assert.assertEquals(map.player.getNumberOfVillagersLeft(), 19);
//        //Assert.assertEquals(map.player.getPoints(), 2);
//        System.out.println("Settlement ID: " + map.gameBoard.get(new Coordinate(0,1)).getSettlementID());
//        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getSettlementID(),map.gameBoard.get(new Coordinate(0,1)).getSettlementID());
//    }

    @Test
    public void testSettlementsAreNotMergedWhenDifferentPlayerIDs() throws Exception{
        map.player.setPlayerID(4);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,3), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.Jungle, TerrainType.Rocky),
                new Coordinate(-2,2), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(1,1));
        Assert.assertEquals(map.settlements.size(),1);

        map.player.setPlayerID(5);
        map.foundNewSettlement(new Coordinate(-2,3));
        int oldId = map.gameBoard.get(new Coordinate(-2 ,3)).getSettlementID();
        map.expandSettlement(new Coordinate(-2,3), TerrainType.Rocky);
        map.placeTotoro(new Coordinate(0,2));
        System.out.println("Settlement ID: " + map.gameBoard.get(new Coordinate(1,1)).getSettlementID());
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,1)).getWhichPlayerID(), 4);
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1,1)).getWhichPlayerID() != map.gameBoard.get(new Coordinate(0,2)).getWhichPlayerID());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,2)).hasTotoro());
//        Assert.assertEquals(map.gameBoard.get(new Coordinate( -2,3)).getSettlementID(),
//                map.gameBoard.get(new Coordinate(1 ,1)).getSettlementID());
        int newId = map.gameBoard.get(new Coordinate(1 ,1)).getSettlementID();
        //Assert.assertNotEquals(oldId, newId);
        //Assert.assertEquals(map.settlements.get(newId).settlementCoordinates.size(), 6);
        //Assert.assertEquals(map.settlements.size(), 1);
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
        map.player.resetScoreAndInventory();
    }
}
