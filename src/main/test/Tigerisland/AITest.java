//package Tigerisland;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//public class AITest {
//
//
//    AI ai;
//
//    @Before
//    public void setup() {
//        ai = new AI();
//    }
//
//    @Test
//    public void testCorrectParseOfTile() {
//        String message = new String("JUNGLE ROCK 1 0 FromBottomRight found 2 0");
//        ai.parseOtherPlayerMessage(message);
//        Assert.assertNotNull(ai.currentTile);
//    }
//
//    @Test
//    public void testCorrectParseOfTileCoordinate() {
//        String message = new String("JUNGLE ROCK 1 0 FromBottomRight found 2 0");
//        ai.parseOtherPlayerMessage(message);
//        Assert.assertEquals(ai.tileCord, new Coordinate(1,0));
//    }
//
//    @Test
//    public void testCorrectParseOfOrientation() {
//        String message = new String("JUNGLE ROCK 1 0 FromBottomRight found 2 0");
//        ai.parseOtherPlayerMessage(message);
//        Assert.assertEquals(ai.orient, Orientation.FromBottomRight);
//    }
//
//    @Test
//    public void testCorrectParseOfBuildChoice() {
//        String message = new String("JUNGLE ROCK 1 0 FromBottomRight found 2 0");
//        ai.parseOtherPlayerMessage(message);
//        Assert.assertEquals(Choice.FOUNDED, ai.buildChoice);
//    }
//
//    @Test
//    public void testCorrectParseOfBuildCoordinate() {
//        String message = new String("JUNGLE ROCK 1 0 FromBottomRight found 2 0");
//        ai.parseOtherPlayerMessage(message);
//        Assert.assertEquals(new Coordinate(2,0), ai.buildCord);
//    }
//
//    @Test
//    public void testCorrectParseOfExpandArea() {
//        String message = new String("JUNGLE ROCK 1 0 FromBottomRight expand 2 0 ROCK");
//        ai.parseOtherPlayerMessage(message);
//        Assert.assertEquals(TerrainType.ROCKY, ai.expandArea);
//    }
//
//    @Test
//    public void testDoNotParseExpandArea() {
//        String message = new String("JUNGLE ROCK 1 0 FromBottomRight found 2 0");
//        ai.parseOtherPlayerMessage(message);
//        Assert.assertNull(ai.expandArea);
//    }
//
//    @After
//    public void teardown() {
//
//    }
//
//
//}
