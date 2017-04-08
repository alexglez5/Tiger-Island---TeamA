package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.HashMap;

public class BuildValidator extends Builder{
//    private Builder = new Builder();
    protected HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    protected Player player;
    protected HashMap<Integer, Settlement> settlements;

    public void setSettlements(HashMap<Integer, Settlement> settlements){
        this.settlements = settlements;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setGameBoard(HashMap<Coordinate, Hex> gameBoard) {
        this.gameBoard = gameBoard;
    }

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
        return player.getNumberOfVillagersLeft() > 0;
    }

    public boolean settlementCanBeExpanded() {
        return thereIsAtLeastOneEmptyHexToExpand()
                && thereAreEnoughVillagersToExpand();
    }

    private boolean thereIsAtLeastOneEmptyHexToExpand() {
        return visitedCoordinates.size() > 0;
    }

    public boolean thereAreEnoughVillagersToExpand() {
        return possibleVillagersPlaced <= player.getNumberOfVillagersLeft();
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
        return player.getNumberOfTotoroLeft() > 0;
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
        return player.getNumberOfTigersLeft() > 0;
    }
}
