package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.TerrainType;

public class TilePlacementValidator extends TilePlacer {

    public boolean tileCanBePlacedOnLevelOne() {
        return gameBoard.size() == 0
                || (!tileExistsBelow()
                && atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge());
    }

    private boolean tileExistsBelow() {
        return gameBoard.containsKey(locator.leftOfMainTerrainCoordinate)
                && gameBoard.containsKey(locator.mainTerrainCoordinate)
                && gameBoard.containsKey(locator.rightOfMainTerrainCoordinate);
    }

    private boolean atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge() {
        return touchesPreviouslyPlacedTileEdge(locator.leftOfMainTerrainCoordinate)
                || touchesPreviouslyPlacedTileEdge(locator.mainTerrainCoordinate)
                || touchesPreviouslyPlacedTileEdge(locator.rightOfMainTerrainCoordinate);
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

    public boolean hexesBelowAreAtTheSameLevel() {
        return hexesOfTileAreOccupied()
                && gameBoard.get(locator.leftOfMainTerrainCoordinate).getLevel()
                == gameBoard.get(locator.mainTerrainCoordinate).getLevel()
                && gameBoard.get(locator.mainTerrainCoordinate).getLevel()
                == gameBoard.get(locator.rightOfMainTerrainCoordinate).getLevel();
    }

    private boolean volcanoIsPlacedOnTopOfAnotherVolcano() {
        return gameBoard.get(locator.mainTerrainCoordinate).getTerrainType()
                == TerrainType.Volcano;
    }

    private boolean tileIsNotPerfectlyOnTopOfAnotherTile() {
        return gameBoard.get(locator.leftOfMainTerrainCoordinate).getTileID()
                != gameBoard.get(locator.mainTerrainCoordinate).getTileID()
                || gameBoard.get(locator.mainTerrainCoordinate).getTileID()
                != gameBoard.get(locator.rightOfMainTerrainCoordinate).getTileID()
                || gameBoard.get(locator.leftOfMainTerrainCoordinate).getTileID()
                != gameBoard.get(locator.rightOfMainTerrainCoordinate).getTileID();
    }

    private boolean tileIsNotPlacedOnTopOfTotoro() {
        return coordinateDoesNotContainTotoro(locator.leftOfMainTerrainCoordinate)
                && coordinateDoesNotContainTotoro(locator.mainTerrainCoordinate)
                && coordinateDoesNotContainTotoro(locator.rightOfMainTerrainCoordinate);
    }

    private boolean tileIsNotPlacedOnTopOfTiger() {
        return coordinateDoesNotContainTiger(locator.leftOfMainTerrainCoordinate)
                && coordinateDoesNotContainTiger(locator.mainTerrainCoordinate)
                && coordinateDoesNotContainTiger(locator.rightOfMainTerrainCoordinate);
    }

    private boolean hexesOfTileAreOccupied() {
        return gameBoard.containsKey(locator.leftOfMainTerrainCoordinate) &&
                gameBoard.containsKey(locator.mainTerrainCoordinate) &&
                gameBoard.containsKey(locator.rightOfMainTerrainCoordinate);
    }

    private boolean coordinateDoesNotContainTotoro(Coordinate terrainCoordinate) {
        return !(gameBoard.containsKey(terrainCoordinate)
                && gameBoard.get(terrainCoordinate).hasTotoro());
    }

    private boolean coordinateDoesNotContainTiger(Coordinate terrainCoordinate) {
        return !(gameBoard.containsKey(terrainCoordinate)
                && gameBoard.get(terrainCoordinate).hasTiger());
    }

    private boolean coordinateHasAPieceThatWillBeWipedOut(Coordinate terrainCoordinate) {
        return terrainContainsAPiece(terrainCoordinate)
                && settlementIdsOfHexesInTile.contains(gameBoard.get(terrainCoordinate).getSettlementID());
    }

    private boolean isOneOfTheCoordinatesThatWouldBeLeftThatBelongToTheSameSettlement(
            Coordinate tempCoordinate, int idOfSettlementThanMightBeWipeOut) {
        return terrainContainsAPiece(tempCoordinate)
                && gameBoard.get(tempCoordinate).getSettlementID()
                == idOfSettlementThanMightBeWipeOut
                && isNotOneOfCoordinatesThatWillBeWipedOut(tempCoordinate);
    }

    private boolean isNotOneOfCoordinatesThatWillBeWipedOut(Coordinate tempCoordinate) {
        return tempCoordinate == locator.leftOfMainTerrainCoordinate
                || tempCoordinate == locator.mainTerrainCoordinate
                || tempCoordinate == locator.rightOfMainTerrainCoordinate;
    }
}
