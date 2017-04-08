package Tigerisland.PlayerActions;

import Tigerisland.TerrainType;

public class BuildValidator extends Builder {

    public boolean settlementCanBeFound() {
        return terrainIsOnMap()
                && terrainIsNotAVolcano()
                && !hexContainsAPiece()
                && terrainIsInLevelOne()
                && thereIsAVillagerLeft();
    }

    private boolean terrainIsOnMap() {
        return gameBoard.containsKey(coordinate);
    }

    private boolean terrainIsNotAVolcano() {
        return gameBoard.get(coordinate).getTerrainType() != TerrainType.Volcano;
    }

    private boolean hexContainsAPiece() {
        return gameBoard.get(coordinate).hasTiger()
                || gameBoard.get(coordinate).hasTotoro()
                || gameBoard.get(coordinate).hasVillager();
    }

    private boolean terrainIsInLevelOne() {
        return gameBoard.get(coordinate).getLevel() == 1;
    }

    private boolean thereIsAVillagerLeft() {
        return getPlayer().getNumberOfVillagersLeft() > 0;
    }

    public boolean settlementCanBeExpanded() {
        return thereIsAtLeastOneEmptyHexToExpand()
                && thereAreEnoughVillagersToExpand();
    }

    private boolean thereIsAtLeastOneEmptyHexToExpand() {
        return visitedCoordinates.size() > 0;
    }

    public boolean thereAreEnoughVillagersToExpand() {
        return possibleVillagersPlaced <= getPlayer().getNumberOfVillagersLeft();
    }

    public boolean totoroCanBePlaced() {
        return terrainIsOnMap()
                && terrainIsNotAVolcano()
                && !hexContainsAPiece()
                && adjacentSettlementIsAtLeastSizeFiveAndDoesNotContainTotoro()
                && thereIsATotoroLeft();
    }

    private boolean adjacentSettlementIsAtLeastSizeFiveAndDoesNotContainTotoro() {
        findIdOfSettlementTotoroCouldBeAdjacentTo();
        return settlements.get(settlementID).hasTotoro()
                && settlements.get(settlementID).getSize() >= 5;
    }

    private boolean thereIsATotoroLeft() {
        return getPlayer().getNumberOfTotoroLeft() > 0;
    }

    public boolean tigerCanBePlaced() {
        return terrainIsOnMap()
                && terrainIsNotAVolcano()
                && !hexContainsAPiece()
                && levelIsAtLeastThree()
                && atLeastOneAdjacentSettlementDoesNotContainATiger()
                && thereIsATigerLeft();
    }

    private boolean levelIsAtLeastThree() {
        return gameBoard.get(coordinate).getLevel() > 2;
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        return settlementID != -1;
    }

    private boolean thereIsATigerLeft() {
        return getPlayer().getNumberOfTigersLeft() > 0;
    }
}
