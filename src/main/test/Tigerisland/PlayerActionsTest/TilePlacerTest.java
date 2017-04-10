package Tigerisland.PlayerActionsTest;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TilePlacerTest {
    Game map;

    @Before
    public void initializeGameBoard() throws Exception{
        map = new Game();
    }

    @Test
    public void testPlacementFromBottomRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,1)));
    }

    @Test
    public void testPlacementFromBottomRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,0)));
    }

    @Test
    public void testPlacementFromTopRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromTopRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,-1)));
    }

    @Test
    public void testPlacementFromTopRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromTop);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,-1)));
    }

    @Test
    public void testPlacementFromTopLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,0)));
    }

    @Test
    public void testPlacementFromBottomLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottomLeft);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,1)));
    }

    @Test
    public void testAtLeastOneEdgeTouchesEdgeOfPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.JUNGLE),
                new Coordinate(1,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(2,0)));

        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,-1), Orientation.FromTopRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(2,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(2,-2)));

        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.GRASS),
                new Coordinate(-1,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,-1)));
    }

    @Test
    public void testTileIsNotPlacedIfNoEdgeTouchesPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(0,-2), Orientation.FromBottomLeft);
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-1, -2)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-1,-1)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-2,0)));

        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.JUNGLE),
                new Coordinate(3,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-1,3)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(3,0)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(2,0)));
    }

    @Test
    public void testNukeIncreasesHexLevel() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(),2);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getLevel(),2);
        Assert.assertEquals(map.getBoard().get(new Coordinate(1,0)).getLevel(),2);
    }

    @Test
    public void testNukeOverwritesTerrainTypeOfHexes() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.ROCK);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.VOLCANO);
        Assert.assertEquals(map.getBoard().get(new Coordinate(1,0)).getTerrainType(),
                TerrainType.GRASS);
    }

    @Test
    public void testVolcanoIsPlacedOnTopOfAnotherVolcano() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,1), Orientation.FromTop);
        Assert.assertFalse(map.tileCanNukeOtherTiles(new Tile(TerrainType.JUNGLE, TerrainType.LAKE),
                new Coordinate(1,0), Orientation.FromBottom));
    }

    @Test
    public void testTileIsNotPerfectlyOnTopOfOtherTile() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertFalse(map.tileCanNukeOtherTiles(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom));
    }

    @Test
    public void testTileCompletelyCoversHexesBeneathIt() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);

        Assert.assertFalse(map.tileCanNukeOtherTiles(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottomRight));
    }

    @Test
    public void testNukingIsNotDoneIfItReducesAnySettlementSizeToZero() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(0,1));

        Assert.assertFalse(map.tileCanNukeOtherTiles(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom));
    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTotoro() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.getBoard().get(new Coordinate(0,1)).placeTotoro();
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertFalse(map.tileCanNukeOtherTiles(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom));

    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTiger() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.getBoard().get(new Coordinate(0,1)).placeTiger();
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(), 1);
    }

    @Test
    public void testResolvedSplitSettlement() {

    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.resetGame();
    }
}