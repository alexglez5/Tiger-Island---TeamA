package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends ActionHelper {
    private Tile tile;
    private long tileID;
    private int sizeLeftOfCurrentSettlement;
    private int idOfNewSettlement;
    private ArrayList<Coordinate> coordinatesToBeMovedFromSettlement;
    private Settlement newSettlement;
    private Set<Integer> coordinatesToTheLeftOfTile;
    private Set<Integer> coordinatesToTheRightOfTile;

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

    public void nuke() {
        int level = gameBoard.get(mainTerrainCoordinate).getLevel();
        getDifferentSettlementIDsOfATile();
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
        removeFromSettlementAllPiecesNukedByATile();
        gameBoard.put(leftOfMainTerrainCoordinate, tile.getLeftOfMainTerrain());
        gameBoard.put(mainTerrainCoordinate, tile.getMainTerrain());
        gameBoard.put(rightOfMainTerrainCoordinate, tile.getRightOfMainTerrain());
    }

    private void removeFromSettlementAllPiecesNukedByATile() {
        removePieceNukedFromSettlement(leftOfMainTerrainCoordinate);
        removePieceNukedFromSettlement(mainTerrainCoordinate);
        removePieceNukedFromSettlement(rightOfMainTerrainCoordinate);
    }

    private void removePieceNukedFromSettlement(Coordinate terrainCoordinate) {
        int tempId = 0;
        if (coordinateHasAPieceThatWillBeWipedOut(terrainCoordinate)) {
            tempId = gameBoard.get(terrainCoordinate).getSettlementID();
            settlements.get(tempId).settlementCoordinates.remove(terrainCoordinate);
        }
    }

    private boolean coordinateHasAPieceThatWillBeWipedOut(Coordinate terrainCoordinate) {
        return terrainContainsAPiece(terrainCoordinate)
                && settlementIdsOfHexesInTile.contains(gameBoard.get(terrainCoordinate).getSettlementID());
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
        sizeLeftOfCurrentSettlement = 0;
        getDifferentSettlementIDsOfATile();

        for (int idOfSettlementThanMightBeWipeOut : settlementIdsOfHexesInTile) {
            sizeLeftOfCurrentSettlement = getSizeLeftOfCurrentSettlement(idOfSettlementThanMightBeWipeOut);
            if (sizeLeftOfCurrentSettlement < 1)
                return false;
        }
        return true;
    }

    public void getDifferentSettlementIDsOfATile() {
        settlementIdsOfHexesInTile = new HashSet<>();
        if (terrainContainsAPiece(leftOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(leftOfMainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(mainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(mainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(rightOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(rightOfMainTerrainCoordinate).getSettlementID());
    }

    private void increaseLevelOfTile(int level) {
        gameBoard.get(leftOfMainTerrainCoordinate).setLevel(level + 1);
        gameBoard.get(mainTerrainCoordinate).setLevel(level + 1);
        gameBoard.get(rightOfMainTerrainCoordinate).setLevel(level + 1);
    }

    private void splitSettlementNukedIfNecessary() {
        findCoordinatesAroundATile();
        separateCoordinatesOfSettlementsIntoRightAndLeftSidesOfATile();
        ifThereIsNoPathBetweenCoordinatesOfSettlementsFromLeftToRightSideSplitThem();
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

    private int getSizeLeftOfCurrentSettlement(int idOfSettlementThanMightBeWipeOut) {
        for (Coordinate tempCoordinate : gameBoard.keySet())
            if (isOneOfTheCoordinatesThatWouldBeLeftThatBelongToTheSameSettlement(tempCoordinate, idOfSettlementThanMightBeWipeOut))
                sizeLeftOfCurrentSettlement++;
        return sizeLeftOfCurrentSettlement;
    }

    private void separateCoordinatesOfSettlementsIntoRightAndLeftSidesOfATile() {
        coordinatesToTheLeftOfTile = new HashSet<>();
        coordinatesToTheRightOfTile = new HashSet<>();
        for (Coordinate neighborCoordinate : coordinatesAroundATile)
            if (terrainContainsAPiece(neighborCoordinate))
                placeItInCorrespondingLeftOrRightSideList(neighborCoordinate);
    }

    private void ifThereIsNoPathBetweenCoordinatesOfSettlementsFromLeftToRightSideSplitThem() {
        for (int id : settlementIdsOfHexesInTile)
            if (thereIsAGapInSettlementsPath(id))
                splitIntoTwoSeparateSettlements(id);
    }

    private boolean isOneOfTheCoordinatesThatWouldBeLeftThatBelongToTheSameSettlement(
            Coordinate tempCoordinate, int idOfSettlementThanMightBeWipeOut) {
        return terrainContainsAPiece(tempCoordinate)
                && gameBoard.get(tempCoordinate).getSettlementID()
                == idOfSettlementThanMightBeWipeOut
                && isNotOneOfCoordinatesThatWillBeWipedOut(tempCoordinate);
    }

    private void placeItInCorrespondingLeftOrRightSideList(Coordinate neighborCoordinate) {
        if (isToTheRightOfTile(neighborCoordinate))
            coordinatesToTheRightOfTile.add(gameBoard.get(neighborCoordinate).getSettlementID());
        else
            coordinatesToTheLeftOfTile.add(gameBoard.get(neighborCoordinate).getSettlementID());
    }

    private boolean thereIsAGapInSettlementsPath(int id) {
        return thereAreCoordinatesOfTheSameSettlementInBothRightAndLeftSide(id)
                && thereIsNotAPathBetweenRightAndLeftSide(id);
    }

    private void splitIntoTwoSeparateSettlements(int id) {
        newSettlement = new Settlement();
        coordinatesToBeMovedFromSettlement = new ArrayList<>();
        idOfNewSettlement = 0;
        putCoordinatesOnTheRightSideInANewSettlement(id);
        removeCoordinatesThatWereMovedToANewSettlement(id);
    }

    private boolean isNotOneOfCoordinatesThatWillBeWipedOut(Coordinate tempCoordinate) {
        return tempCoordinate == leftOfMainTerrainCoordinate
                || tempCoordinate == mainTerrainCoordinate
                || tempCoordinate == rightOfMainTerrainCoordinate;
    }

    private boolean thereAreCoordinatesOfTheSameSettlementInBothRightAndLeftSide(int id) {
        return coordinatesToTheRightOfTile.contains(id) && coordinatesToTheLeftOfTile.contains(id);
    }

    private boolean thereIsNotAPathBetweenRightAndLeftSide(int id) {
        return thereIsNotALowerPath(id) && thereIsNotAHigherPath(id);
    }

    private void putCoordinatesOnTheRightSideInANewSettlement(int id) {
        boolean firstCoordinateOfNewSettlement = true;
        for (Coordinate coordinateInSettlement : settlements.get(id).settlementCoordinates) {
            if (isToTheRightOfTile(coordinateInSettlement)) {
                newSettlement.settlementCoordinates.add(coordinateInSettlement);
                coordinatesToBeMovedFromSettlement.add(coordinateInSettlement);
                if (firstCoordinateOfNewSettlement)
                    idOfNewSettlement = coordinateInSettlement.hashCode();
                gameBoard.get(coordinateInSettlement).setSettlementID(idOfNewSettlement);
            }
        }
    }

    private void removeCoordinatesThatWereMovedToANewSettlement(int id) {
        for (Coordinate coordinateToMove : coordinatesToBeMovedFromSettlement)
            settlements.get(id).settlementCoordinates.remove(coordinateToMove);
        settlements.put(idOfNewSettlement, newSettlement);
    }

    private boolean thereIsNotALowerPath(int id) {
        return !(lowerInLeftSideBelongsToSettlement(id) && lowerInRightSideBelongsToSettlement(id));
    }

    private boolean thereIsNotAHigherPath(int id) {
        return !(higherInLeftSideBelongsToSettlement(id) && higherInRightSideBelongsToSettlement(id));
    }

    private boolean lowerInLeftSideBelongsToSettlement(int id) {
        return gameBoard.containsKey(belowAndToTheRightOfMain(rightOfMainTerrainCoordinate))
                && gameBoard.get(belowAndToTheRightOfMain(rightOfMainTerrainCoordinate)).getSettlementID() == id;
    }

    private boolean lowerInRightSideBelongsToSettlement(int id) {
        return gameBoard.containsKey(toTheRightOfMain(rightOfMainTerrainCoordinate))
                && gameBoard.get(toTheRightOfMain(rightOfMainTerrainCoordinate)).getSettlementID() == id;
    }

    private boolean higherInLeftSideBelongsToSettlement(int id) {
        return gameBoard.containsKey(overAndToTheRightOfMain(mainTerrainCoordinate))
                && gameBoard.get(overAndToTheRightOfMain(mainTerrainCoordinate)).getSettlementID() == id;
    }

    private boolean higherInRightSideBelongsToSettlement(int id) {
        return gameBoard.containsKey(overAndToTheLeftOfMain(mainTerrainCoordinate))
                && gameBoard.get(overAndToTheLeftOfMain(mainTerrainCoordinate)).getSettlementID() == id;
    }
}