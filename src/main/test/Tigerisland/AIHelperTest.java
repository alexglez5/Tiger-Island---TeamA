package Tigerisland;

import Tigerisland.AIHelpers.AIHelper;
import Tigerisland.AIHelpers.TileParameters;
import Tigerisland.AIHelpers.ExpandingParameters;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AIHelperTest{
    AIHelper helper;

    @Before
    public void initializeGameBoard() throws Exception {
        helper = new AIHelper();
        helper.map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0, 0), Orientation.FromBottom);
    }

    @Test
    public void shouldReturnCoordinateWhereTotoroCanBePlaced() throws Exception {
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(1, 3), Orientation.FromBottomLeft);

        helper.map.foundNewSettlement(new Coordinate(1, 1));
        helper.map.expandSettlement(new Coordinate(1, 1), TerrainType.ROCK);
        helper.findCoordinateWhereTotoroCanBePlaced();
        Assert.assertTrue(helper.map.totoroCanBePlaced(helper.getPlaceWhereTotoroCanBePlaced()));
    }

    // test also checks that other player settlement is not taken into account
    @Test
    public void shouldCoordinateWhereTigerCanBePlaced() throws Exception {
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);

        helper.map.foundNewSettlement(new Coordinate(1,1));
        helper.map.getBoard().get(new Coordinate(2,0)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(1,0)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(0,0)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(0,1)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(-1,1)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(-1,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(0,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(-1,3)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(1,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(2,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(2,1)).setLevel(3);

        helper.map.getBoard().get(new Coordinate(-1,1)).placeTiger();
        helper.map.getBoard().get(new Coordinate(-1,1)).setSettlementID(3);
        helper.map.getSettlements().put(3, new Settlement(new Coordinate(-1,1)));
        helper.map.getSettlements().get(3).placeTiger();

        helper.findCoordinateWhereTigerCanBePlaced();
        Assert.assertTrue(helper.map.tigerCanBePlaced(helper.getPlaceWhereTigerCanBePlaced()));
        Assert.assertEquals(helper.getVisitedCoordinates().size(), 6);
        helper.map.switchPlayers();
        helper.map.foundNewSettlement(new Coordinate(-1,1));
        helper.findCoordinateWhereTigerCanBePlaced();
        Assert.assertEquals(helper.getVisitedCoordinates().size(), 6);
    }

    @Test
    public void shouldReturnCoordinateWhereTotoroCanBePlacedTwoPlayers() throws Exception {
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
                new Coordinate(1, 3), Orientation.FromBottomLeft);

        helper.map.foundNewSettlement(new Coordinate(1, 1));
        helper.map.expandSettlement(new Coordinate(1, 1), TerrainType.ROCK);
        helper.findCoordinateWhereTotoroCanBePlaced();
        Assert.assertTrue(helper.map.totoroCanBePlaced(helper.getPlaceWhereTotoroCanBePlaced()));
        Assert.assertEquals(helper.getVisitedCoordinates().size(), 16);

        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(-1,0), Orientation.FromBottomLeft);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.ROCK),
                new Coordinate(-1,-1), Orientation.FromTopLeft);

        helper.map.switchPlayers();
        helper.map.foundNewSettlement(new Coordinate(-1, 1));
        helper.map.expandSettlement(new Coordinate(-1,1), TerrainType.LAKE);

        Assert.assertEquals(helper.getVisitedCoordinates().size(), 16);
        Assert.assertEquals(helper.map.getSettlements().size(), 2);
    }

    @Test
    public void findSettlementToExandReturnsAnExpandableSettlement() throws Exception{
        helper.map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2,1), Orientation.FromBottom);
        helper.map.foundNewSettlement(new Coordinate(1,1));
        helper.findPlaceWhereSettlementCanBeExpanded();
        ExpandingParameters parameters = helper.getPlaceWhereSettlementCanBeExpanded();
        Assert.assertTrue(helper.map.settlementCanBeExpanded(parameters.getCoordinate(), parameters.getTerrainType()));
    }

    @Test
    public void makeAIPlaceTileTest(){
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);

        helper.findPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS);

        Assert.assertTrue(helper.getPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS) != null);
        TileParameters parameters = helper.getPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS);
        Assert.assertTrue(helper.map.tileCanBePlacedOnLevelOne(new Tile(parameters.getLeftTerrainType() ,parameters.getRightTerrainType()),
                parameters.getMainTerrainCoordinate(), parameters.getOrientattion()));
    }

    @Test
    public void testAICanFoundSettlement(){
        helper.findCoordinatesWhereSettlementCanBeFound();
        Assert.assertTrue(helper.getPlaceWhereSettlementCanBeFound() != null);
    }

    @Test
    public void nukeTest(){
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);
        helper.map.setCurrentPlayer(2);
        helper.map.foundNewSettlement(new Coordinate(-1,3));
        helper.map.expandSettlement(new Coordinate(-1,3), TerrainType.ROCK);
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(-1,3)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(0,2)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(1,2)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(1,1)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(0,1)).hasVillager());
        helper.map.setCurrentPlayer(1);
        Assert.assertEquals(helper.map.getSettlements().get(helper.map.getBoard().get(new Coordinate(0,1)).getSettlementID()).getPlayerID(), 2);
        Assert.assertEquals(helper.map.getSettlements().get(helper.map.getBoard().get(new Coordinate(0,1)).getSettlementID()).getSize(), 5);
        Assert.assertEquals(helper.map.getCurrentPlayerId(),1);
        helper.findPlaceWhereTileCanBePlaced(TerrainType.GRASS, TerrainType.JUNGLE);
        Assert.assertTrue(helper.getPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS) != null);
        TileParameters parameters = new TileParameters(helper.getPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS).getLeftTerrainType(),
                helper.getPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS).getRightTerrainType(),helper.getPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS).getMainTerrainCoordinate(),
                helper.getPlaceWhereTileCanBePlaced(TerrainType.ROCK,TerrainType.GRASS).getOrientattion());
        Tile tile = new Tile(parameters.getLeftTerrainType() ,parameters.getRightTerrainType());
        helper.map.placeTile(tile, parameters.getMainTerrainCoordinate(), parameters.getOrientattion());
        Assert.assertEquals(helper.map.getBoard().get(new Coordinate(parameters.getMainTerrainCoordinate().getXCoordinate(),
                parameters.getMainTerrainCoordinate().getYCoordinate())).getLevel(),2);
    }

    @Test
    public void testIntentionalTotoroNuking() {
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1, 1), Orientation.FromTop);
        helper.map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);
        helper.map.setCurrentPlayer(1);
        helper.map.foundNewSettlement(new Coordinate(-1,3));
        helper.map.expandSettlement(new Coordinate(-1,3), TerrainType.ROCK);
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(-1,3)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(0,2)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(1,2)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(1,0)).hasVillager());
        Assert.assertTrue(helper.map.getBoard().get(new Coordinate(0,1)).hasVillager());
        helper.map.placeTotoro(new Coordinate(2,0));
        Assert.assertEquals(helper.map.getSettlements().get((new Coordinate(-1,3)).hashCode()).getSize(), 6);
        TileParameters place = helper.getPlaceWhereTileCanBePlaced(TerrainType.GRASS, TerrainType.JUNGLE);
        Assert.assertEquals(place.getMainTerrainCoordinate(), new Coordinate(1,1));
        Assert.assertEquals(place.getOrientattion(), Orientation.FromTopLeft);
    }

    @Test
    public void testNukingIncreasesLevel() {
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(0, 0), Orientation.FromBottomRight);

        Assert.assertEquals(helper.map.getBoard().get(new Coordinate(0,0)).getLevel(), 2);
    }

    @After
    public void deallocateHexesInMap() throws Exception {
        helper.map.resetGame();
    }
}