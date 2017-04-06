package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends Game {
    private Tile tile;
    public int sizeLeftOfCurrentSettlement;
    private int idOfNewSettlement;
    public ArrayList<Integer> settlementIdsOfHexesInTile;
    protected Coordinate leftOfMainTerrainCoordinate;
    protected Coordinate mainTerrainCoordinate;
    protected Coordinate rightOfMainTerrainCoordinate;
    protected Orientation orientation;
    private ArrayList<Coordinate> coordinatesToBeMovedFromSettlement;
    private ActionHelper locator = new ActionHelper();

    public void placeOneStartingTile() {
        gameBoard.put(new Coordinate(0, -1), new Hex(TerrainType.Jungle, 1));
        gameBoard.put(new Coordinate(1, -1), new Hex(TerrainType.Lake, 1));
        gameBoard.put(new Coordinate(0, 0), new Hex(TerrainType.Volcano, 1));
        gameBoard.put(new Coordinate(-1, 1), new Hex(TerrainType.Rocky, 1));
        gameBoard.put(new Coordinate(0, 1), new Hex(TerrainType.Grasslands, 1));
    }

    public void processParameters(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        this.mainTerrainCoordinate = mainTerrainCoordinate;
        this.orientation = terrainsOrientation;
        this.tile = tile;
        locator.updateXAndYCoordinateOfCurrentTerrain(mainTerrainCoordinate);
        determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
    }

    public void determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation() {
        switch (orientation) {
            case FromBottom:
                leftOfMainTerrainCoordinate = locator.belowAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = locator.belowAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromBottomRight:
                leftOfMainTerrainCoordinate = locator.belowAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = locator.toTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTopRight:
                leftOfMainTerrainCoordinate = locator.toTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = locator.overAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTop:
                leftOfMainTerrainCoordinate = locator.overAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = locator.overAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromTopLeft:
                leftOfMainTerrainCoordinate = locator.overAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = locator.toTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromBottomLeft:
                leftOfMainTerrainCoordinate = locator.toTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = locator.belowAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
        }
    }

    public void nuke() {
        int level = gameBoard.get(mainTerrainCoordinate).getLevel();
        getDifferentSettlementIDsOfATile();
        placeTileOnMap();
        increaseLevel(level);
    }

    private void increaseLevel(int previousLevel) {
        gameBoard.get(mainTerrainCoordinate).setLevel(previousLevel + 1);
        gameBoard.get(leftOfMainTerrainCoordinate).setLevel(previousLevel + 1);
        gameBoard.get(rightOfMainTerrainCoordinate).setLevel(previousLevel + 1);
    }

    public void placeTileOnMap() {
        gameBoard.put(leftOfMainTerrainCoordinate, tile.getLeftOfMainTerrain());
        gameBoard.put(mainTerrainCoordinate, tile.getMainTerrain());
        gameBoard.put(rightOfMainTerrainCoordinate, tile.getRightOfMainTerrain());
    }

    public ArrayList<Integer> getDifferentSettlementIDsOfATile() {
        settlementIdsOfHexesInTile = new ArrayList<Integer>();
        if (terrainContainsAPiece(leftOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(leftOfMainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(mainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(mainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(rightOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(rightOfMainTerrainCoordinate).getSettlementID());

        return settlementIdsOfHexesInTile;
    }


}