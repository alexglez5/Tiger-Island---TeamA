package Tigerisland;

import java.util.TreeSet;

/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends ActionHelper {
    private Coordinate leftOfMainTerrainCoordinate;
    private Coordinate mainTerrainCoordinate;
    private Coordinate rightOfMainTerrainCoordinate;
    private Tile tile;
    private long tileID;
    private Orientation orientation;
    private TreeSet<Integer> settlementIdsOfHexesInTile;
    private int sizeLeftOfCurrentSettlement;

    public void placeOneStartingTile() {
        gameBoard.put(new Coordinate(0, -1), new Hex(TerrainType.Jungle, 1));
        gameBoard.put(new Coordinate(1, -1), new Hex(TerrainType.Lake, 1));
        gameBoard.put(new Coordinate(0, 0), new Hex(TerrainType.Volcano, 1));
        gameBoard.put(new Coordinate(-1, 1), new Hex(TerrainType.Rocky, 1));
        gameBoard.put(new Coordinate(0, 1), new Hex(TerrainType.Grasslands, 1));
    }

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
        if (tileCanBePlacedOnLevelOne())
            placeTileOnLevelOne();
        else if (tileCanNukeOtherTiles())
            nuke();
    }

    public void processParameters(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        this.mainTerrainCoordinate = mainTerrainCoordinate;
        this.orientation = terrainsOrientation;
        this.tileID = getRandomTileID(mainTerrainCoordinate, terrainsOrientation);
        tile.setTileID(tileID);
        this.tile = tile;
        updateXAndYCoordinateOfCurrentTerrain(mainTerrainCoordinate);
    }

    private void determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation() {
        switch (orientation) {
            case FromBottom:
                leftOfMainTerrainCoordinate = belowAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = belowAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromBottomRight:
                leftOfMainTerrainCoordinate = belowAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = toTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTopRight:
                leftOfMainTerrainCoordinate = toTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = overAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTop:
                leftOfMainTerrainCoordinate = overAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = overAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromTopLeft:
                leftOfMainTerrainCoordinate = overAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = toTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromBottomLeft:
                leftOfMainTerrainCoordinate = toTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = belowAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
        }
    }

    public Boolean tileCanBePlacedOnLevelOne() {
        return tileIsTheFirstTilePlacedOnTheGameBoard()
                || (thereIsNoTileBelow()
                && atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge());
    }

    private void placeTileOnLevelOne() {
        placeTileOnMap();
    }

    public Boolean tileCanNukeOtherTiles() {
        return hexesBelowAreAtTheSameLevel()
                && volcanoIsPlacedOnTopOfAnotherVolcano()
                && tileIsNotPerfectlyOnTopOfAnotherTile()
                && tileIsNotPlacedOnTopOfTotoro()
                && tileIsNotPlacedOnTopOfTiger()
                && tileDoesNotCompletelyWipeOutASettlement();
    }

    private void nuke() {
        int level = gameBoard.get(mainTerrainCoordinate).getLevel();
        placeTileOnMap();
        increaseLevelOfTile(level);
        splitSettlementNukedIfNecessary();
    }

    private long getRandomTileID(Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        return mainTerrainCoordinate.hashCode() + terrainsOrientation.hashCode()
                + System.currentTimeMillis();
    }

    private boolean tileIsTheFirstTilePlacedOnTheGameBoard() {
        return gameBoard.size() == 0;
    }

    private boolean thereIsNoTileBelow() {
        return !gameBoard.containsKey(leftOfMainTerrainCoordinate)
                && !gameBoard.containsKey(mainTerrainCoordinate)
                && !gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }

    private Boolean atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge() {
        return touchesPreviouslyPlacedTileEdge(leftOfMainTerrainCoordinate)
                || touchesPreviouslyPlacedTileEdge(mainTerrainCoordinate)
                || touchesPreviouslyPlacedTileEdge(rightOfMainTerrainCoordinate);
    }

    private void placeTileOnMap() {
        gameBoard.put(leftOfMainTerrainCoordinate, tile.getLeftOfMainTerrain());
        gameBoard.put(mainTerrainCoordinate, tile.getMainTerrain());
        gameBoard.put(rightOfMainTerrainCoordinate, tile.getRightOfMainTerrain());
    }

    private boolean hexesBelowAreAtTheSameLevel() {
        return hexesOfTileAreOccupied()
                && gameBoard.get(leftOfMainTerrainCoordinate).getLevel()
                == gameBoard.get(mainTerrainCoordinate).getLevel()
                && gameBoard.get(mainTerrainCoordinate).getLevel()
                == gameBoard.get(rightOfMainTerrainCoordinate).getLevel();
    }

    private boolean volcanoIsPlacedOnTopOfAnotherVolcano() {
        return gameBoard.get(mainTerrainCoordinate).getTerrainType()
                == TerrainType.Volcano;
    }

    private Boolean tileIsNotPerfectlyOnTopOfAnotherTile() {
        return gameBoard.get(leftOfMainTerrainCoordinate).getTileID()
                != gameBoard.get(mainTerrainCoordinate).getTileID()
                || gameBoard.get(mainTerrainCoordinate).getTileID()
                != gameBoard.get(rightOfMainTerrainCoordinate).getTileID()
                || gameBoard.get(leftOfMainTerrainCoordinate).getTileID()
                != gameBoard.get(rightOfMainTerrainCoordinate).getTileID();
    }

    private boolean tileIsNotPlacedOnTopOfTotoro() {
        return coordinateDoesNotContainTotoro(leftOfMainTerrainCoordinate)
                && coordinateDoesNotContainTotoro(mainTerrainCoordinate)
                && coordinateDoesNotContainTotoro(rightOfMainTerrainCoordinate);
    }

    private boolean tileIsNotPlacedOnTopOfTiger() {
        return coordinateDoesNotContainTiger(leftOfMainTerrainCoordinate)
                && coordinateDoesNotContainTiger(mainTerrainCoordinate)
                && coordinateDoesNotContainTiger(rightOfMainTerrainCoordinate);
    }

    private boolean tileDoesNotCompletelyWipeOutASettlement() {
        settlementIdsOfHexesInTile = new TreeSet<>();
        sizeLeftOfCurrentSettlement = 0;
        getDifferentSettlementIDsOfATile();

        for (int idOfSettlementThanMightBeWipeOut : settlementIdsOfHexesInTile) {
            sizeLeftOfCurrentSettlement = getSizeLeftOfCurrentSettlement(idOfSettlementThanMightBeWipeOut);
            if (sizeLeftOfCurrentSettlement < 1)
                return false;
        }
        return true;
    }

    private void increaseLevelOfTile(int level) {
        gameBoard.get(leftOfMainTerrainCoordinate).setLevel(level + 1);
        gameBoard.get(mainTerrainCoordinate).setLevel(level + 1);
        gameBoard.get(rightOfMainTerrainCoordinate).setLevel(level + 1);
    }

    private void splitSettlementNukedIfNecessary() {
        //go around hexes of tile
        //to be worked on
    }

    private boolean touchesPreviouslyPlacedTileEdge(Coordinate terrainCoordinate) {
        findCounterClockwiseCoordinatesAroundCoordinate(terrainCoordinate);
        for (int i = 0; i < sidesOfAHex; i++)
            if (gameBoard.containsKey(counterClockwiseCoordinatesAroundCoordinate[i]))
                return true;
        return false;
    }

    private boolean hexesOfTileAreOccupied() {
        return gameBoard.containsKey(leftOfMainTerrainCoordinate) &&
                gameBoard.containsKey(mainTerrainCoordinate) &&
                gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }

    private boolean coordinateDoesNotContainTotoro(Coordinate terrainCoordinate) {
        return !(gameBoard.containsKey(terrainCoordinate)
                && gameBoard.get(terrainCoordinate).hasTotoro());
    }

    private boolean coordinateDoesNotContainTiger(Coordinate terrainCoordinate) {
        return !(gameBoard.containsKey(terrainCoordinate)
                && gameBoard.get(terrainCoordinate).hasTiger());
    }

    private void getDifferentSettlementIDsOfATile() {
        if (terrainContainsAPiece(leftOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(leftOfMainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(mainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(mainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(rightOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(rightOfMainTerrainCoordinate).getSettlementID());
    }

    private int getSizeLeftOfCurrentSettlement(int idOfSettlementThanMightBeWipeOut) {
        for (Coordinate tempCoordinate : gameBoard.keySet())
            if (isOneOfTheCoordinatesThatWouldBeLeftThatBelongToTheSameSettlement(tempCoordinate, idOfSettlementThanMightBeWipeOut))
                sizeLeftOfCurrentSettlement++;
        return sizeLeftOfCurrentSettlement;
    }

    private boolean isOneOfTheCoordinatesThatWouldBeLeftThatBelongToTheSameSettlement(
            Coordinate tempCoordinate, int idOfSettlementThanMightBeWipeOut) {
        return terrainContainsAPiece(tempCoordinate)
                && gameBoard.get(tempCoordinate).getSettlementID()
                == idOfSettlementThanMightBeWipeOut
                && isNotOneOfCoordinatesThatWillBeWipedOut(tempCoordinate);
    }

    private boolean isNotOneOfCoordinatesThatWillBeWipedOut(Coordinate tempCoordinate) {
        return tempCoordinate == leftOfMainTerrainCoordinate
                || tempCoordinate == mainTerrainCoordinate
                || tempCoordinate == rightOfMainTerrainCoordinate;
    }
}