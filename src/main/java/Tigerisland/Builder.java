package Tigerisland;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class Builder extends ActionHelper {
    private static Player player = new Player();
    private final int minimumSizeOfSettlementAdjacentToTotoro = 5;
    private final int pointsForTigerPlacement = 75;
    private Coordinate coordinate;
    private TerrainType terrainType;
    private int settlementID;
    private Set<Coordinate> visitedCoordinates;
    private int settlementSize;
    private TreeSet<Integer> differentSettlementIDsAroundACoordinate;
    private int possiblePointsAdded;
    private int possibleVillagersPlaced;
    private TreeSet<Integer> idOfSettlementsThatContainATiger;

    public Player getPlayer() {
        return player;
    }

    public void foundNewSettlement(Coordinate coordinate) {
        processParameters(coordinate);
        if (settlementCanBeFound())
            foundSettlement(coordinate);
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        processParameters(coordinateOfAnyHexInSettlement, terrainType);
        findCoordinatesOfPossibleSettlementExpansion();
        if (settlementCanBeExpanded())
            expandSettlement();

        mergeSettlementsThatCanBeMerged();
    }

    public void placeTotoro(Coordinate coordinate) {
        processParameters(coordinate);
        if (totoroCanBePlaced())
            placeTotoroAtGivenCoordinate();

        mergeSettlementsThatCanBeMerged(coordinate);
    }

    public void placeTiger(Coordinate coordinate) {
        processParameters(coordinate);
        if (tigerCanBePlaced())
            placeTigerAtGivenCoordinate();

        mergeSettlementsThatCanBeMerged(coordinate);
    }

    private void processParameters(Coordinate coordinate, TerrainType terrainType) {
        this.settlementID = coordinate.hashCode();
        this.terrainType = terrainType;
        possibleVillagersPlaced = 0;
        possiblePointsAdded = 0;
    }

    private void findCoordinatesOfPossibleSettlementExpansion() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    private boolean settlementCanBeExpanded() {
        return thereIsAtLeastOneEmptyHexToExpand()
                && thereIsEnoughVillagersToExpand();
    }

    private void expandSettlement() {
        for (Coordinate coordinateToExpand : visitedCoordinates) {
            gameBoard.get(coordinateToExpand).placeVillagers();
            gameBoard.get(coordinateToExpand).setSettlementID(settlementID);
        }
        player.updatePlacedVillagers(possibleVillagersPlaced);
        player.addPlayerPoints(possiblePointsAdded);
    }

    private void mergeSettlementsThatCanBeMerged() {
        for (Coordinate visitedCoordinate : visitedCoordinates)
            mergeSettlementsThatCanBeMerged(visitedCoordinate);
    }

    private void expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType() {
        for (Coordinate adjacentCoordinate : gameBoard.keySet()) {
            if (terrainBelongsToTheSameSettlementAndHasTheSameType(adjacentCoordinate)) {
                findCounterClockwiseCoordinatesAroundCoordinate(adjacentCoordinate);
                expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
            }
        }
    }

    private boolean thereIsAtLeastOneEmptyHexToExpand() {
        return visitedCoordinates.size() > 0;
    }

    public boolean thereIsEnoughVillagersToExpand() {
        return possibleVillagersPlaced <= player.getNumberOfVillagersLeft();
    }

    private void mergeSettlementsThatCanBeMerged(Coordinate coordinate) {
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        differentSettlementIDsAroundACoordinate = new TreeSet<>();

        getAllDifferentSettlementIDsAroundACoordinate();
        assignEveryHexThatShouldBeMergedTheSameID();
    }

    private boolean terrainBelongsToTheSameSettlementAndHasTheSameType(Coordinate adjacentCoordinate) {
        return gameBoard.get(adjacentCoordinate).getSettlementID() == settlementID
                && gameBoard.get(adjacentCoordinate).getTerrainType() == terrainType;
    }

    private void expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited() {
        for (Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
            if (adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(neighborCoordinate))
                markCoordinateAsVisited(neighborCoordinate);
        }
    }

    private void getAllDifferentSettlementIDsAroundACoordinate() {
        for (Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate)
            if (coordinateBelongsToSettlementWithDifferentID(neighborCoordinate))
                differentSettlementIDsAroundACoordinate.add(gameBoard.get(neighborCoordinate).getSettlementID());
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

    private void markCoordinateAsVisited(Coordinate neighborCoordinate) {
        visitedCoordinates.add(neighborCoordinate);
        updateScoreAndPlayersInventory(neighborCoordinate);
    }

    private boolean coordinateBelongsToSettlementWithDifferentID(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && gameBoard.get(neighborCoordinate).hasVillager()
                && gameBoard.get(neighborCoordinate).getSettlementID() != settlementID;
    }

    private boolean coordinateHasOneOfTheSettlementIDsThatShouldBeMerged(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && differentSettlementIDsAroundACoordinate.contains(gameBoard.get(neighborCoordinate).getSettlementID());
    }

    private void updateScoreAndPlayersInventory(Coordinate neighborCoordinate) {
        int level = gameBoard.get(neighborCoordinate).getLevel();
        possiblePointsAdded += level * level;
        possibleVillagersPlaced += level;
    }

    private void processParameters(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.settlementID = coordinate.hashCode();
    }

    private boolean settlementCanBeFound() {
        return terrainIsNotTaken()
                && terrainIsNotAVolcano()
                && terrainIsInLevelOne()
                && thereIsAVillagerLeft();
    }

    private void foundSettlement(Coordinate coordinate) {
        gameBoard.get(coordinate).placeVillagers();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        player.addPlayerPoints(1);
        player.updatePlacedVillagers(1);
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

    private boolean thereIsAVillagerLeft() {
        return player.getNumberOfVillagersLeft() > 0;
    }

    private boolean tigerCanBePlaced() {
        return terrainIsNotAVolcano()
                && terrainIsNotTaken()
                && levelIsAtLeastThree()
                && atLeastOneAdjacentSettlementDoesNotContainATiger()
                && thereIsATigerLeft();
    }

    private void placeTigerAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTiger();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        player.addPlayerPoints(pointsForTigerPlacement);
        player.updatePlacedTiger();
    }

    private boolean levelIsAtLeastThree() {
        return gameBoard.get(coordinate).getLevel() > 2;
    }

    private boolean atLeastOneAdjacentSettlementDoesNotContainATiger() {
        getAllDifferentSettlementIDsAroundACoordinate();
        idOfSettlementsThatContainATiger = new TreeSet<>();
        getIDsOfSettlementsThatContainATiger();
        return oneOfTheSettlementsAroundCoordinateDoesNotContainATiger();
    }

    private boolean oneOfTheSettlementsAroundCoordinateDoesNotContainATiger() {
        for (int coordinateID : differentSettlementIDsAroundACoordinate)
            if (idIsNotOneOfTheOnesThatContainATiger(coordinateID)) {
                settlementID = coordinateID;
                return true;
            }
        return false;
    }

    private boolean idIsNotOneOfTheOnesThatContainATiger(int coordinateID) {
        return !idOfSettlementsThatContainATiger.contains(coordinateID);
    }

    private void getIDsOfSettlementsThatContainATiger() {
        for (int coordinateID : differentSettlementIDsAroundACoordinate) {
            settlementID = coordinateID;
            for (Coordinate coordinate : gameBoard.keySet())
                if (coordinateOfSettlementDoesNotContainATiger(coordinate))
                    idOfSettlementsThatContainATiger.add(settlementID);
        }
    }

    private boolean coordinateOfSettlementDoesNotContainATiger(Coordinate coordinate) {
        return gameBoard.get(coordinate).getSettlementID() == settlementID
                && gameBoard.get(coordinate).hasTiger();
    }

    private boolean thereIsATigerLeft() {
        return player.getNumberOfTigersLeft() > 0;
    }

    private boolean totoroCanBePlaced() {
        return terrainIsNotAVolcano()
                && terrainIsNotTaken()
                && isAdjacentToSettlementOfAtLeastSizeFive()
                && adjacentSettlementDoesNotContainATotoro()
                && thereIsATotoroLeft();
    }

    private void placeTotoroAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTotoro();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        player.updatePlacedTotoro();
        player.addPlayerPoints(200);
    }

    private boolean isAdjacentToSettlementOfAtLeastSizeFive() {
        getAllDifferentSettlementIDsAroundACoordinate();
        return atLeastOneOfTheSettlementsIsAtLeastSizeFive();
    }

    private boolean atLeastOneOfTheSettlementsIsAtLeastSizeFive() {
        for (int coordinateID : differentSettlementIDsAroundACoordinate) {
            settlementSize = 0;
            settlementID = coordinateID;
            if (settlementIsAtLeastSizeFive())
                return true;
        }
        return false;
    }

    private boolean settlementIsAtLeastSizeFive() {
        for (Coordinate coordinate : gameBoard.keySet()) {
            ifCoordinateBelongsToSettlementIncreaseSettlementSize(coordinate);
            if (settlementSize == minimumSizeOfSettlementAdjacentToTotoro)
                return true;
        }
        return false;
    }

    private boolean adjacentSettlementDoesNotContainATotoro() {
        for (Coordinate coordinate : gameBoard.keySet())
            if (coordinateOfSettlementDoesNotContainATotoro(coordinate))
                return false;
        return true;
    }

    private boolean coordinateOfSettlementDoesNotContainATotoro(Coordinate coordinate) {
        return gameBoard.get(coordinate).getSettlementID() == settlementID
                && gameBoard.get(coordinate).hasTotoro();
    }

    private boolean thereIsATotoroLeft() {
        return player.getNumberOfTotoroLeft() > 0;
    }

    private void ifCoordinateBelongsToSettlementIncreaseSettlementSize(Coordinate coordinate) {
        if (gameBoard.containsKey(coordinate)
                && gameBoard.get(coordinate).getSettlementID() == settlementID)
            settlementSize++;
    }

    private boolean terrainTypeIsNotAVolcano() {
        return terrainType != TerrainType.Volcano;
    }

}