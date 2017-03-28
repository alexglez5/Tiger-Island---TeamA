package Tigerisland;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class Builder extends ActionHelper {
    private Coordinate coordinate;
    private TerrainType terrainType;
    private int settlementID;
    private Set<Coordinate> visitedCoordinates;
    private int settlementSize;
    private final int minimumSizeOfSettlementAdjacentToTotoro = 5;
    private TreeSet<Integer> idsOfSettlementsToBeMerged;

    public void foundNewSettlement(Coordinate coordinate) {
        processParameters(coordinate);
        if (settlementCanBeFound())
            foundSettlement(coordinate);

    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        processParameters(coordinateOfAnyHexInSettlement, terrainType);
        if (settlementCanBeExpanded())
            expandSettlement();

        mergeSettlementsThatCanBeMerged();
    }

    public void placeTotoro(Coordinate coordinate, int settlementID) {
        processParameters(coordinate, settlementID);
        if (totoroCanBePlaced())
            placeTotoroAtGivenCoordinate();

        mergeSettlementsThatCanBeMerged(coordinate);
    }

    public void placeTiger(Coordinate coordinate, int settlementID) {
        processParameters(coordinate, settlementID);
        if (tigerCanBePlaced())
            placeTigerAtGivenCoordinate();

        mergeSettlementsThatCanBeMerged(coordinate);
    }

    private boolean tigerCanBePlaced() {
        return terrainIsNotAVolcano()
                && terrainIsNotTaken()
                && levelIsAtLeastThree()
                && adjacentSettlementDoesNotContainATiger();
    }

    private void placeTigerAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTiger();
        gameBoard.get(coordinate).setSettlementID(settlementID);
    }

    private boolean levelIsAtLeastThree() {
        return gameBoard.get(coordinate).getLevel() > 2;
    }

    private boolean adjacentSettlementDoesNotContainATiger() {
        for (Coordinate coordinate : gameBoard.keySet())
            if (gameBoard.get(coordinate).hasTiger())
                return false;
        return true;
    }

    private void processParameters(Coordinate coordinate, int settlementID) {
        this.coordinate = coordinate;
        this.settlementID = settlementID;
    }

    private boolean totoroCanBePlaced() {
        return terrainIsNotAVolcano()
                && terrainIsNotTaken()
                && isAdjacentToSettlementOfAtLeastSizeFive()
                && adjacentSettlementDoesNotContainATotoro();
    }

    private void placeTotoroAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTotoro();
        gameBoard.get(coordinate).setSettlementID(settlementID);
    }

    private boolean isAdjacentToSettlementOfAtLeastSizeFive() {
        settlementSize = 0;
        for (Coordinate coordinate : gameBoard.keySet()) {
            ifCoordinateBelongsToSettlementIncreaseSettlementSize(coordinate);
            if (settlementSize == minimumSizeOfSettlementAdjacentToTotoro)
                return true;
        }
        return false;
    }

    private boolean adjacentSettlementDoesNotContainATotoro() {
        for (Coordinate coordinate : gameBoard.keySet())
            if (gameBoard.get(coordinate).hasTotoro())
                return false;
        return true;
    }

    private void ifCoordinateBelongsToSettlementIncreaseSettlementSize(Coordinate coordinate) {
        if (gameBoard.containsKey(coordinate)
                && gameBoard.get(coordinate).getSettlementID() == settlementID)
            settlementSize++;
    }

    private void processParameters(Coordinate coordinate, TerrainType terrainType) {
        this.settlementID = coordinate.hashCode();
        this.terrainType = terrainType;
    }

    private boolean settlementCanBeExpanded() {
        return terrainTypeIsNotAVolcano();
    }

    private void expandSettlement() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    private void mergeSettlementsThatCanBeMerged() {
        for (Coordinate visitedCoordinate : visitedCoordinates)
            mergeSettlementsThatCanBeMerged(visitedCoordinate);
    }

    private boolean terrainTypeIsNotAVolcano() {
        return terrainType != TerrainType.Volcano;
    }

    private void expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType() {
        for (Coordinate terrainCoordinate : gameBoard.keySet()) {
            if (terrainBelongsToTheSameSettlementAndHasTheSameType(terrainCoordinate)) {
                findCounterClockwiseCoordinatesAroundCoordinate(terrainCoordinate);
                expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
            }
        }
    }

    private void mergeSettlementsThatCanBeMerged(Coordinate coordinate) {
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        idsOfSettlementsToBeMerged = new TreeSet<>();

        saveAllDifferentIDsAroundACoordinate();
        assignEveryHexThatShouldBeMergedTheSameID();
    }

    private boolean terrainBelongsToTheSameSettlementAndHasTheSameType(Coordinate terrainCoordinate) {
        return gameBoard.get(terrainCoordinate).getSettlementID() == settlementID
                && gameBoard.get(terrainCoordinate).getTerrainType() == terrainType;
    }

    private void expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited() {
        for (Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
            if (adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(neighborCoordinate))
                placeVillagersAndMarkCoordinateAsVisited(neighborCoordinate);
        }
    }

    private void saveAllDifferentIDsAroundACoordinate() {
        for (Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate)
            if (coordinateBelongsToSettlementWithDifferentID(neighborCoordinate))
                idsOfSettlementsToBeMerged.add(gameBoard.get(neighborCoordinate).getSettlementID());
    }

    private void assignEveryHexThatShouldBeMergedTheSameID() {
        for (Coordinate neighborCoordinate : gameBoard.keySet())
            if (coordinateHasOneOfTheSettlementIDsThatShouldBeMerged(neighborCoordinate))
                gameBoard.get(neighborCoordinate).setSettlementID(settlementID);
    }

    private boolean adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && gameBoard.get(neighborCoordinate).getTerrainType() == terrainType
                && !visitedCoordinates.contains(neighborCoordinate);
    }

    private void placeVillagersAndMarkCoordinateAsVisited(Coordinate neighborCoordinate) {
        gameBoard.get(neighborCoordinate).placeVillagers();
        gameBoard.get(neighborCoordinate).setSettlementID(settlementID);
        visitedCoordinates.add(neighborCoordinate);
    }

    private boolean coordinateBelongsToSettlementWithDifferentID(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && gameBoard.get(neighborCoordinate).hasVillager()
                && gameBoard.get(neighborCoordinate).getSettlementID() != settlementID;
    }

    private boolean coordinateHasOneOfTheSettlementIDsThatShouldBeMerged(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && idsOfSettlementsToBeMerged.contains(gameBoard.get(neighborCoordinate).getSettlementID());
    }

    private void processParameters(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.settlementID = coordinate.hashCode();
    }

    private boolean settlementCanBeFound() {
        return terrainIsNotTaken()
                && terrainIsNotAVolcano()
                && terrainIsInLevelOne();
    }

    private void foundSettlement(Coordinate coordinate) {
        gameBoard.get(coordinate).placeVillagers();
        gameBoard.get(coordinate).setSettlementID(settlementID);
    }

    private boolean terrainIsNotTaken() {
        return gameBoard.containsKey(coordinate);
    }

    private boolean terrainIsNotAVolcano() {
        return gameBoard.get(coordinate).getTerrainType() != TerrainType.Volcano;
    }

    private boolean terrainIsInLevelOne() {
        return gameBoard.get(coordinate).getLevel() == 1;
    }
}