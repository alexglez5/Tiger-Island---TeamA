package Tigerisland.PlayerActions;

import Tigerisland.*;

public class TilePlacementValidator extends TilePlacer{

    public boolean tileCanBePlacedOnLevelOne() {
        return gameBoard.size() == 0
                || (!tileExistsBelow()
                && atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge());
    }

    private boolean  tileExistsBelow() {
        return gameBoard.containsKey(locator.leftOfMainTerrainCoordinate)
                || gameBoard.containsKey(locator.mainTerrainCoordinate)
                ||gameBoard.containsKey(locator.rightOfMainTerrainCoordinate);
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
                && tileIsNotPlacedOnTopOfTiger()
                && !tileCompletelyWipesOutASettlement();
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
                == TerrainType.VOLCANO;
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

    public boolean tileCompletelyWipesOutASettlement() {

        getDifferentSettlementIDsOfATile();


        // if there is only one settlement id in the set
        if (settlementIdsOfHexesUnderTile.size() == 1) {

            // count if both are occupied,
            if (terrainContainsAPiece(locator.leftOfMainTerrainCoordinate) &&
                    terrainContainsAPiece(locator.rightOfMainTerrainCoordinate)) {

                // check if the bfs of the underlying settlement is greater than 2
                int settlementIdToCheck = settlementIdsOfHexesUnderTile.iterator().next();
                if (settlements.containsKey(settlementIdToCheck) && settlements.get(settlementIdToCheck).bfs().size() <= 2)
                    return true;
            }
            // only one is occupied
            else {
                // check if bfs of underlying settlement is greater than 1
                int settlementIdToCheck = settlementIdsOfHexesUnderTile.iterator().next();
                if (settlements.containsKey(settlementIdToCheck) && settlements.get(settlementIdToCheck).bfs().size() == 1)
                    return true;
            }
        } else if (!settlementIdsOfHexesUnderTile.isEmpty() && settlementIdsOfHexesUnderTile.size() == 2) {
            // make sure that each underlying settlement has a bfs of greater than 1
            for (int sid : settlementIdsOfHexesUnderTile) {
                if (settlements.containsKey(sid) && settlements.get(sid).bfs().size() == 1)
                    return true;
            }
        }

        // if none of the above conditions are met, tile does not completely wipe out settlement
        return false;
    }

    public boolean checkForSplit(Settlement s) {
        if (!s.bfs().isEmpty() && s.bfs().size() < s.getSize())
            return true;
        else
            return false;
    }
}
