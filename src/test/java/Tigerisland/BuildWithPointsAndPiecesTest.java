package Tigerisland;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/31/2017.
 */
public class BuildWithPointsAndPiecesTest {
//    Player player;
    GameBoard map;
    @Before
    public void setUpTests(){
        map = new GameBoard();
//        player = new Player();
    }

    @Test
    public void foundSettlementAndCheckPlayerScoreAndVillagerUpdated(){
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
=======
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0,1));
        Assert.assertEquals(map.getPlayer().getNumberOfVillagersLeft(), 19);
        Assert.assertEquals(map.getPlayer().getPoints(), 1);
    }
}
