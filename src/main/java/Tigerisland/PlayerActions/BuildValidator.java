package Tigerisland.PlayerActions;

import Tigerisland.*;

/**
 * Created by nathanbarnavon on 4/5/17.
 */
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
                && isAdjacentToSettlementOfAtLeastSizeFive()
                && adjacentSettlementDoesNotContainATotoro()
                && thereIsATotoroLeft();
    }

    private boolean isAdjacentToSettlementOfAtLeastSizeFive() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        return atLeastOneOfTheSettlementsIsAtLeastSizeFive();
    }

    private boolean atLeastOneOfTheSettlementsIsAtLeastSizeFive() {
        for (int coordinateID : differentSettlementIDsAroundCoordinate) {
            if (getPlayer().findSettlement(coordinateID).getSize() >= 5) {
                settlementID = coordinateID;
                return true;
            }
        }
        return false;
    }

    private boolean adjacentSettlementDoesNotContainATotoro() {
        return !getPlayer().findSettlement(settlementID).hasTotoro();
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

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        return oneOfTheSettlementsDoesNotContainATiger();
    }

    private boolean oneOfTheSettlementsDoesNotContainATiger() {
        for (int id : differentSettlementIDsAroundCoordinate) {
            if (settlementDoesNotHaveATiger(id)) {
                settlementID = id;
                return true;
            }
        }
        return false;
    }



    private boolean settlementDoesNotHaveATiger(int id) {
        return !getPlayer().findSettlement(id).hasTiger();
    }

    private boolean thereIsATigerLeft() {
        return getPlayer().getNumberOfTigersLeft() > 0;
    }

    private boolean levelIsAtLeastThree() {
        return gameBoard.get(coordinate).getLevel() > 2;
    }



}
