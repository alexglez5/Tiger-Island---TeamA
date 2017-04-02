package Tigerisland;

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

    @Test
    public void expandSettlementAndCheckScore(){
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky), new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0,1));
        System.out.println("Points: " + map.player.getPoints());
        Assert.assertEquals(map.player.getPoints(), 1);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.expandSettlement(new Coordinate(0,1), TerrainType.Rocky);
        System.out.println("Points: " + map.player.getPoints());
        Assert.assertEquals(map.player.getPoints(), 4);
    }
}
