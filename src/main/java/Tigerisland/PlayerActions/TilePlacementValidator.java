package Tigerisland.PlayerActions;

import Tigerisland.*;

/**
 * Created by nathanbarnavon on 4/5/17.
 */
public class TilePlacementValidator extends TilePlacer {

    ActionHelper locator = new ActionHelper();

    public boolean tileCanBePlacedOnLevelOne() {
        return (!tileExistsBelow() && atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge());
    }

    private boolean touchesPreviouslyPlacedTileEdge(Coordinate terrainCoordinate) {
        locator.findCounterClockwiseCoordinatesAroundCoordinate(terrainCoordinate);
        for (int i = 0; i < 6; i++)
            if (gameBoard.containsKey(locator.surroundingCoordinates[i]))
                return true;
        return false;
    }

    public boolean tileCanNukeOtherTiles() {
        return hexesBelowAreAtTheSameLevel()
                && volcanoIsPlacedOnTopOfAnotherVolcano()
                && tileIsNotPerfectlyOnTopOfAnotherTile()
                && tileIsNotPlacedOnTopOfTotoro()
                && tileIsNotPlacedOnTopOfTiger();
                //&& tileDoesNotCompletelyWipeOutASettlement();
    }

    private boolean tileExistsBelow() {
        return gameBoard.containsKey(leftOfMainTerrainCoordinate)
                && gameBoard.containsKey(mainTerrainCoordinate)
                && gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }

    private boolean atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge() {
        return touchesPreviouslyPlacedTileEdge(leftOfMainTerrainCoordinate)
                || touchesPreviouslyPlacedTileEdge(mainTerrainCoordinate)
                || touchesPreviouslyPlacedTileEdge(rightOfMainTerrainCoordinate);
    }

    private boolean coordinateHasAPieceThatWillBeWipedOut(Coordinate terrainCoordinate) {
        return terrainContainsAPiece(terrainCoordinate)
                && settlementIdsOfHexesInTile.contains(gameBoard.get(terrainCoordinate).getSettlementID());
    }

    public boolean hexesBelowAreAtTheSameLevel() {
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

    private boolean tileIsNotPerfectlyOnTopOfAnotherTile() {
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
