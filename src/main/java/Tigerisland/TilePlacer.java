package Tigerisland;

import java.util.TreeSet;

/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends ActionHelper {
    //TODO more refactoring make tileID random and not required in tile's constructor
    private Coordinate leftOfMainTerrainCoordinate;
    private Coordinate mainTerrainCoordinate;
    private Coordinate rightOfMainTerrainCoordinate;
    private Tile tile;
    private Orientation orientation;
    private TreeSet<Integer> settlementIdsOfHexesInTile;
    private int sizeLeftOfCurrentSettlement;

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
        if (tileCanBePlacedOnLevelOne())
            placeTileOnLevelOne();
        else if (tileCanNukeOtherTiles())
            nuke();
    }

    private void processParameters(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        this.tile = tile;
        this.mainTerrainCoordinate = mainTerrainCoordinate;
        this.orientation = terrainsOrientation;
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

    private Boolean tileCanBePlacedOnLevelOne() {
        return tileIsTheFirstTilePlacedOnTheGameBoard()
                || (thereIsNoTileBelow()
                && atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge());
    }

    private void placeTileOnLevelOne() {
        placeTileOnMap();
    }

    private Boolean tileCanNukeOtherTiles() {
        return hexesBelowAreAtTheSameLevel()
                && volcanoIsPlacedOnTopOfAnotherVolcano()
                && tileIsNotPerfectlyOnTopOfAnotherTile()
                && tileIsNotPlacedOnTopOfTotoro()
                && tileIsNotPlacedOnTopOfTiger()
                && tileDoesNotCompletelyWipeOutASettlement();
    }

    private void nuke() {
        placeTileOnMap();
        increaseLevelOfTile();
    }

    private boolean tileIsTheFirstTilePlacedOnTheGameBoard() {
        return tile.getTileID() == 1;
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
                == TerrainType.VOLCANO;
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

    private int getSizeLeftOfCurrentSettlement(int idOfSettlementThanMightBeWipeOut) {
        for (Coordinate tempCoordinate : gameBoard.keySet()) {
            if (isOneOfTheCoordinatesThatWouldBeLeftThatBelongToTheSameSettlement(tempCoordinate, idOfSettlementThanMightBeWipeOut))
                sizeLeftOfCurrentSettlement++;
        }
        return sizeLeftOfCurrentSettlement;
    }

    private boolean isOneOfTheCoordinatesThatWouldBeLeftThatBelongToTheSameSettlement(
            Coordinate tempCoordinate, int idOfSettlementThanMightBeWipeOut) {
        return terrainContainsAPiece(tempCoordinate)
                && gameBoard.get(tempCoordinate).getSettlementID()
                == idOfSettlementThanMightBeWipeOut
                && isNotOneOfCoordinatesThatWillBeWipedOut(tempCoordinate);
    }

    private void getDifferentSettlementIDsOfATile() {
        if (terrainContainsAPiece(leftOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(leftOfMainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(mainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(mainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(rightOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(rightOfMainTerrainCoordinate).getSettlementID());
    }

    private boolean terrainContainsAPiece(Coordinate terrainCoordinate){
        return gameBoard.containsKey(terrainCoordinate)
                && (gameBoard.get(terrainCoordinate).hasVillager()
                || gameBoard.get(terrainCoordinate).hasTotoro()
                || gameBoard.get(terrainCoordinate).hasTiger());
    }

    private void increaseLevelOfTile() {
        gameBoard.get(leftOfMainTerrainCoordinate).increaseLevel();
        gameBoard.get(mainTerrainCoordinate).increaseLevel();
        gameBoard.get(rightOfMainTerrainCoordinate).increaseLevel();
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

    private boolean isNotOneOfCoordinatesThatWillBeWipedOut(Coordinate tempCoordinate) {
        return tempCoordinate == leftOfMainTerrainCoordinate
                || tempCoordinate == mainTerrainCoordinate
                || tempCoordinate == rightOfMainTerrainCoordinate;
    }
}