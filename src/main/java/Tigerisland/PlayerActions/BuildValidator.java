package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.HashMap;

public class BuildValidator extends Builder{
//    private int settlementID = super.settlementID;
//    private Player player = new Player();
//    private HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
//    private HashMap<Integer, Settlement> settlements = new HashMap<>();
//
//    public void updtateComponents(ComponentsDTO dto) {
//        this.gameBoard = dto.getGameBoard();
//        this.settlements = dto.getSettlements();
//        this.player = dto.getPlayer();
//    }

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
        return gameBoard.get(coordinate).getTerrainType() != TerrainType.VOLCANO;
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
        return settlementID != -1;
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
        findIdOfSettlementTigerCouldBeAdjacentTo();
        return settlementID != -1;
    }

    private boolean thereIsATigerLeft() {
        return player.getNumberOfTigersLeft() > 0;
    }
}
