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
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0,1));
        Assert.assertEquals(map.getPlayer().getNumberOfVillagersLeft(), 19);
        Assert.assertEquals(map.getPlayer().getPoints(), 1);
    }
}
