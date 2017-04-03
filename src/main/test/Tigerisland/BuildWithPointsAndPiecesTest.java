package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/31/2017.
 */
public class BuildWithPointsAndPiecesTest {
    GameBoard map;
    @Before
    public void setUpTests(){
        map = new GameBoard();
    }

//    @Test
//    public void foundSettlementAndCheckPlayerScoreAndVillagerUpdated(){
//        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky), new Coordinate(0,0), Orientation.FromBottom);
//        map.foundNewSettlement(new Coordinate(0,1));
//        Assert.assertEquals(map.player.getNumberOfVillagersLeft(), 18);
//        Assert.assertEquals(map.player.getPoints(), 2);
//    }

//    @Test
//    public void expandSettlementAndCheckScore(){
//        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky), new Coordinate(0,0), Orientation.FromBottom);
//        map.foundNewSettlement(new Coordinate(0,1));
//        System.out.println("Points: " + map.player.getPoints());
//        Assert.assertEquals(map.player.getPoints(), 1);
//        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
//                new Coordinate(1,0), Orientation.FromBottomRight);
//        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
//                new Coordinate(-1,2), Orientation.FromBottomRight);
//        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
//                new Coordinate(2,1), Orientation.FromBottom);
//        map.expandSettlement(new Coordinate(0,1), TerrainType.Rocky);
//        System.out.println("Points: " + map.player.getPoints());
//        Assert.assertEquals(map.player.getPoints(), 5);
//        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Lake),
//                new Coordinate(-2,1), Orientation.FromBottom);
//        map.placeTotoro(new Coordinate(2,0));
//        Assert.assertEquals(map.player.getPoints(),205);
//        System.out.println("Points: " + map.player.getPoints());
//        Assert.assertEquals(map.player.getNumberOfTotoroLeft(),2);
//    }

    @Test
    public void addTigerPlaygrouundWithPoints(){
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
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,-1)).getLevel(),3);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,-1)).getLevel(),3);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getLevel(),3);
        Assert.assertFalse(map.gameBoard.get(new Coordinate(1,-1)).getTerrainType() == TerrainType.Volcano);
        Assert.assertTrue(map.gameBoard.get(new Coordinate(2,-1)).hasVillager());
       //map.placeTiger(new Coordinate(1,-1));
        //Assert.assertEquals(map.player.getPoints(), 76);

    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
        map.player.resetScoreAndInventory();
        map.settlements.clear();
    }
}
