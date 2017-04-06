package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */

public class Builder extends Game {
    private final int pointsForTigerPlacement = 75;
    protected Coordinate coordinate;
    protected TerrainType terrainType;
    protected int settlementID;
    public Set<Coordinate> visitedCoordinates;
    protected Set<Integer> differentSettlementIDsAroundCoordinate;
    protected int possiblePointsAdded;
    protected int possibleVillagersPlaced;
    protected static ActionHelper locator = new ActionHelper();


    public void foundNewSettlement() {
        foundNewSettlement(coordinate);
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    public void expandSettlement() {
        findCoordinatesOfPossibleSettlementExpansion();
        completeSettlementExpansion();
        mergeSettlementsThatCanBeMerged();
    }

    public void placeTotoro() {
        placeTotoroAtGivenCoordinate();
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    public void placeTiger() {
        placeTigerAtGivenCoordinate();
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    private void placeTigerAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTiger();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        getPlayer().addPoints(pointsForTigerPlacement);
        getPlayer().useTiger();
    }

    private void setSettlementIdToPieceBeingPlaced(int id) {
        settlementID = id;
    }

    private void placeTotoroAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTotoro();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        getPlayer().findSettlement(settlementID).addToSettlement(coordinate);
        getPlayer().findSettlement(settlementID).placeTotoro();
        getPlayer().useTotoro();
        getPlayer().addPoints(200);
    }

    public void processParameters(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.settlementID = coordinate.hashCode();
//        this.settlementID = Settlement.getCreatedSettlements() + 1;
    }

    public void foundNewSettlement(Coordinate coordinate) {
        gameBoard.get(coordinate).placeVillagers();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        getPlayer().addSettlement(new Settlement(coordinate));
        getPlayer().addPoints(1);
        getPlayer().useVillagers(1);
    }

    private void mergeSettlementsThatCanBeMerged(Coordinate coordinate) {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate)
            mergeSettlementsIntoASingleSettlement(id);
    }

    public void getDifferentSettlementIDsAroundCoordinate(Coordinate coordinate) {
        locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        differentSettlementIDsAroundCoordinate = new HashSet<>();

        for (Coordinate neighborCoordinate : locator.surroundingCoordinates) {
            if (terrainContainsAPiece(neighborCoordinate)
                    && getPlayer().containsKey(gameBoard.get(neighborCoordinate).getSettlementID())) {
                differentSettlementIDsAroundCoordinate.add(gameBoard.get(neighborCoordinate).getSettlementID());
            }
        }
    }

    private void mergeSettlementsIntoASingleSettlement(int id) {
        if (id != settlementID) {
            for (Coordinate coordinatesToBeMoved : getPlayer().findSettlement(id).bfs()) {
                getPlayer().findSettlement(settlementID).addToSettlement(coordinatesToBeMoved);
                gameBoard.get(coordinatesToBeMoved).setSettlementID(settlementID);
            }
            removeSettlementThatWasMerged(id);
        }
    }

    private void removeSettlementThatWasMerged(int id) {
        getPlayer().removeSettlement(getPlayer().findSettlement(id));
    }

    public void processParameters(Coordinate coordinate, TerrainType terrainType) {
            this.settlementID = gameBoard.get(coordinate).getSettlementID();
            this.terrainType = terrainType;
            possibleVillagersPlaced = 0;
            possiblePointsAdded = 0;
    }

    public void findCoordinatesOfPossibleSettlementExpansion() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    private void completeSettlementExpansion() {
        for (Coordinate coordinateToExpand : visitedCoordinates) {
            gameBoard.get(coordinateToExpand).placeVillagers();
            gameBoard.get(coordinateToExpand).setSettlementID(settlementID);
            getPlayer().findSettlement(settlementID).addToSettlement(coordinateToExpand);
        }
        getPlayer().useVillagers(possibleVillagersPlaced);
        getPlayer().addPoints(possiblePointsAdded);
    }

    private void mergeSettlementsThatCanBeMerged() {
        for (Coordinate visitedCoordinate : visitedCoordinates)
            mergeSettlementsThatCanBeMerged(visitedCoordinate);
    }

    private void expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType() {
        for (Coordinate coordinateInSettlement : getPlayer().findSettlement(settlementID).bfs()) {
            locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinateInSettlement);
            expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
        }
    }

    private void expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited() {
        for (Coordinate neighborCoordinate : locator.surroundingCoordinates)
            if (adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(neighborCoordinate))
                expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(neighborCoordinate);
    }

    private boolean adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && gameBoard.get(neighborCoordinate).getTerrainType() == terrainType
                && !visitedCoordinates.contains(neighborCoordinate)
                && !gameBoard.get(neighborCoordinate).hasVillager();
    }

    private void expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(Coordinate neighborCoordinate) {
        markCoordinateAsVisited(neighborCoordinate);
        locator.findCounterClockwiseCoordinatesAroundCoordinate(neighborCoordinate);
        expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
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