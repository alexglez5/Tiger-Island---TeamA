//package Tigerisland;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by Alexander Gonzalez on 4/2/2017.
// */
//public class SettlementTest {
//    Game map;
//
//    @Before
//    public void initializeGameBoard() throws Exception{
//        map = new Game();
//        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
//                new Coordinate(0,0), Orientation.FromBottom);
//    }
//
//    @Test
//    public void testSettlementsAreMergedWhenTotoroIsPlacedBetweenThem() throws Exception{
//        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
//                new Coordinate(1,0), Orientation.FromBottomRight);
//        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
//                new Coordinate(-1,2), Orientation.FromBottomRight);
//        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
//                new Coordinate(2,1), Orientation.FromBottom);
//        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
//                new Coordinate(1,3), Orientation.FromBottomLeft);
//        map.placeTile(new Tile(TerrainType.Jungle, TerrainType.Rocky),
//                new Coordinate(-2,2), Orientation.FromBottom);
//
//        map.foundNewSettlement(new Coordinate(1,1));
//        map.foundNewSettlement(new Coordinate(-2,3));
//        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
//        map.gameBoard.get(new Coordinate(0,3)).placeVillagers();
//        map.gameBoard.get(new Coordinate(0,3)).
//                setSettlementID(map.gameBoard.get(new Coordinate(1,1)).getSettlementID());
//        map.placeTotoro(new Coordinate(-1,3));
//
//        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());
//        Assert.assertEquals(map.gameBoard.get(new Coordinate( -2,3)).getSettlementID(),
//                map.gameBoard.get(new Coordinate(1 ,1)).getSettlementID());
//    }
//
//    @After
//    public void deallocateHexesInMap() throws Exception{
//        map.gameBoard.clear();
//        map.player.resetScoreAndInventory();
//    }
//}
