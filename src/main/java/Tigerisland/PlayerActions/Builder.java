package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.Settlement;
import Tigerisland.TerrainType;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class Builder extends ActionHelper {
    private final int pointsForTigerPlacement = 75;
    private Coordinate coordinate;
    private TerrainType terrainType;
    private int settlementID;
    private Set<Coordinate> visitedCoordinates;
    private TreeSet<Integer> differentSettlementIDsAroundCoordinate;
    private int possiblePointsAdded;
    private int possibleVillagersPlaced;
    private Settlement currentSettlement;

    public void foundNewSettlement(Coordinate coordinate) {
        processParameters(coordinate);
        if (settlementCanBeFound()) {
            foundSettlement(coordinate);
            mergeSettlementsThatCanBeMerged(coordinate);
        }
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        processParameters(coordinateOfAnyHexInSettlement, terrainType);
        findCoordinatesOfPossibleSettlementExpansion();
        if (settlementCanBeExpanded()) {
            expandSettlement();
            mergeSettlementsThatCanBeMerged();
        }
    }

    public void placeTotoro(Coordinate coordinate) {
        processParameters(coordinate);
        if (totoroCanBePlaced()) {
            placeTotoroAtGivenCoordinate();
            mergeSettlementsThatCanBeMerged(coordinate);
        }
    }

    public void placeTiger(Coordinate coordinate) {
        processParameters(coordinate);
        if (tigerCanBePlaced()) {
            placeTigerAtGivenCoordinate();
            mergeSettlementsThatCanBeMerged(coordinate);
        }
    }

    public boolean tigerCanBePlaced() {
        return terrainIsOnMap()
                && terrainIsNotAVolcano()
                && !hexContainsAPiece()
                && levelIsAtLeastThree()
                && atLeastOneAdjacentSettlementDoesNotContainATiger()
                && thereIsATigerLeft();
    }

    private void placeTigerAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTiger();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        settlements.get(settlementID).addCoordinateToSettlement(coordinate);
        settlements.get(settlementID).placeTiger();
        player.addPlayerPoints(pointsForTigerPlacement);
        player.updatePlacedTiger();
        gameBoard.get(coordinate).setWhichPlayerID(player.getPlayerID());
    }

    private boolean levelIsAtLeastThree() {
        return gameBoard.get(coordinate).getLevel() > 2;
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        return oneOfTheSettlementsDoesNotContainATiger();
    }

    private boolean thereIsATigerLeft() {
        return player.getNumberOfTigersLeft() > 0;
    }

    private boolean oneOfTheSettlementsDoesNotContainATiger() {
        for (int id : differentSettlementIDsAroundCoordinate) {
            if (settlementDoesNotHaveATiger(id)) {
                setSettlementIdToPieceBeingPlaced(id);
                return true;
            }
        }
        return false;
    }

    private boolean settlementDoesNotHaveATiger(int id) {
        return !settlements.get(id).hasTiger();
    }

    private void setSettlementIdToPieceBeingPlaced(int id) {
        settlementID = id;
    }

    public boolean totoroCanBePlaced() {
        return terrainIsOnMap()
                && terrainIsNotAVolcano()
                && !hexContainsAPiece()
                && isAdjacentToSettlementOfAtLeastSizeFive()
                && adjacentSettlementDoesNotContainATotoro()
                && thereIsATotoroLeft();
    }

    private boolean terrainIsOnMap() {
        return gameBoard.containsKey(coordinate);
    }

    private void placeTotoroAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTotoro();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        settlements.get(settlementID).addCoordinateToSettlement(coordinate);
        settlements.get(settlementID).placeTotoro();
        player.updatePlacedTotoro();
        player.addPlayerPoints(200);
        gameBoard.get(coordinate).setWhichPlayerID(player.getPlayerID());
    }

    private boolean isAdjacentToSettlementOfAtLeastSizeFive() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        return atLeastOneOfTheSettlementsIsAtLeastSizeFive();
    }

    private boolean adjacentSettlementDoesNotContainATotoro() {
        return !settlements.get(settlementID).hasTotoro();
    }

    private boolean thereIsATotoroLeft() {
        return player.getNumberOfTotoroLeft() > 0;
    }

    private boolean atLeastOneOfTheSettlementsIsAtLeastSizeFive() {
        for (int coordinateID : differentSettlementIDsAroundCoordinate) {
            if (settlements.get(coordinateID).settlementCoordinates.size() >= 5) {
                settlementID = coordinateID;
                return true;
            }
        }
        return false;
    }

    public void processParameters(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.settlementID = coordinate.hashCode();
    }

    public boolean settlementCanBeFound() {
        return terrainIsOnMap()
                && terrainIsNotAVolcano()
                && !hexContainsAPiece()
                && terrainIsInLevelOne()
                && thereIsAVillagerLeft();
    }

    private void foundSettlement(Coordinate coordinate) {
        gameBoard.get(coordinate).placeVillagers();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        currentSettlement = new Settlement();
        currentSettlement.addCoordinateToSettlement(coordinate);
        settlements.put(settlementID, currentSettlement);
        player.addPlayerPoints(1);
        player.updatePlacedVillagers(1);
        gameBoard.get(coordinate).setWhichPlayerID(player.getPlayerID());
    }

    private void mergeSettlementsThatCanBeMerged(Coordinate coordinate) {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate)
            mergeSettlementsIntoASingleSettlement(id);
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

    private void getDifferentSettlementIDsAroundCoordinate(Coordinate coordinate) {
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        differentSettlementIDsAroundCoordinate = new TreeSet<>();

        for (Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
            if (terrainContainsAPiece(neighborCoordinate)
                    && settlements.containsKey(gameBoard.get(neighborCoordinate).getSettlementID())) {
                differentSettlementIDsAroundCoordinate.add(gameBoard.get(neighborCoordinate).getSettlementID());
            }
        }
    }

    private void mergeSettlementsIntoASingleSettlement(int id) {
        if (id != settlementID) {
            for (Coordinate coordinatesToBeMoved : settlements.get(id).settlementCoordinates) {
                settlements.get(settlementID).addCoordinateToSettlement(coordinatesToBeMoved);
                gameBoard.get(coordinatesToBeMoved).setSettlementID(settlementID);
            }
            removeSettlementThatWasMerged(id);
        }
    }

    private void removeSettlementThatWasMerged(int id) {
        settlements.remove(id);
    }

    public void processParameters(Coordinate coordinate, TerrainType terrainType) {
        if (gameBoard.get(coordinate).getWhichPlayerID() == player.getPlayerID()) {
            this.settlementID = gameBoard.get(coordinate).getSettlementID();
            this.terrainType = terrainType;
            possibleVillagersPlaced = 0;
            possiblePointsAdded = 0;
        }
    }

    private void findCoordinatesOfPossibleSettlementExpansion() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    public boolean settlementCanBeExpanded() {
        return thereIsAtLeastOneEmptyHexToExpand()
                && thereAreEnoughVillagersToExpand();
    }

    private void expandSettlement() {
        for (Coordinate coordinateToExpand : visitedCoordinates) {
            gameBoard.get(coordinateToExpand).placeVillagers();
            gameBoard.get(coordinateToExpand).setSettlementID(settlementID);
            settlements.get(settlementID).addCoordinateToSettlement(coordinateToExpand);
        }
        player.updatePlacedVillagers(possibleVillagersPlaced);
        player.addPlayerPoints(possiblePointsAdded);
        gameBoard.get(coordinate).setWhichPlayerID(player.getPlayerID());
    }

    private void mergeSettlementsThatCanBeMerged() {
        for (Coordinate visitedCoordinate : visitedCoordinates)
            mergeSettlementsThatCanBeMerged(visitedCoordinate);
    }

    private void expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType() {
        for (Coordinate coordinateInSettlement : settlements.get(settlementID).settlementCoordinates) {
            findCounterClockwiseCoordinatesAroundCoordinate(coordinateInSettlement);
            expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
        }
    }

    private boolean thereIsAtLeastOneEmptyHexToExpand() {
        return visitedCoordinates.size() > 0;
    }

    public boolean thereAreEnoughVillagersToExpand() {
        return possibleVillagersPlaced <= player.getNumberOfVillagersLeft();
    }

    private void expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited() {
        for (Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate)
            if (adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(neighborCoordinate))
                expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(neighborCoordinate);
    }

    private void expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(Coordinate neighborCoordinate) {
        markCoordinateAsVisited(neighborCoordinate);
        findCounterClockwiseCoordinatesAroundCoordinate(neighborCoordinate);
        expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
    }

    private boolean adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && gameBoard.get(neighborCoordinate).getTerrainType() == terrainType
                && !visitedCoordinates.contains(neighborCoordinate)
                && !gameBoard.get(neighborCoordinate).hasVillager();
    }

    private void markCoordinateAsVisited(Coordinate neighborCoordinate) {
        visitedCoordinates.add(neighborCoordinate);
        updateScoreAndPlayersInventory(neighborCoordinate);
    }

    private void updateScoreAndPlayersInventory(Coordinate neighborCoordinate) {
        int level = gameBoard.get(neighborCoordinate).getLevel();
        possiblePointsAdded += level * level;
        possibleVillagersPlaced += level;
    }
}