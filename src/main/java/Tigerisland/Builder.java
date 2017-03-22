package Tigerisland;

import java.util.*;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class Builder extends ActionHelper {
    private Coordinate coordinate;
    private TerrainType terrainType;
    private int settlementID;
    private Set<Coordinate> visitedCoordinates;
    private int settlementSize;
    private int minimumSizeOfSettlementAdjacentToTotoro;
    private TreeSet<Integer> uniqueIDsOfSettlementsOnGameBoard;
    private TreeSet<Integer> idOfSettlementsToBeMerged;
    private int amountOfSettlements;

    public void foundNewSettlement(Coordinate coordinate){
        this.coordinate = coordinate;
        this.settlementID = coordinate.hashCode();
        if(settlementCanBeFound()) {
            gameBoard.get(coordinate).placeVillagers();
            gameBoard.get(coordinate).setSettlementID(settlementID);
        }
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType){
        this.settlementID = coordinateOfAnyHexInSettlement.hashCode();
        this.terrainType = terrainType;
        if(settlementCanBeExpanded())
            expandSettlement();
    }

    public void placeTotoro(Coordinate coordinate){
        processCoordinate(coordinate);
        if(totoroCanBePlaced())
            placeTotoroAtGivenCoordinate();
    }

    private boolean adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(Coordinate tempCoordinate) {
        return gameBoard.containsKey(tempCoordinate)
                && gameBoard.get(tempCoordinate).getTerrainType() == terrainType
                && !visitedCoordinates.contains(tempCoordinate);
    }

    private void placeVillagersAndMarkCoordinateAsVisited(Coordinate tempCoordinate) {
        gameBoard.get(tempCoordinate).placeVillagers();
        gameBoard.get(tempCoordinate).setSettlementID(settlementID);
        visitedCoordinates.add(tempCoordinate);
    }

    private boolean settlementCanBeExpanded() {
        return terrainTypeIsNotAVolcano();
    }

    private boolean terrainTypeIsNotAVolcano() {
        return terrainType != TerrainType.Volcano;
    }

    private boolean settlementCanBeFound() {
        return terrainIsNotTaken()
                && terrainIsNotAVolcano()
                && terrainIsInLevelOne();
    }

    private boolean terrainIsNotAVolcano(){
        return gameBoard.get(coordinate).getTerrainType() != TerrainType.Volcano;
    }

    private boolean terrainIsInLevelOne(){
        return gameBoard.get(coordinate).getLevel() == 1;
    }

    private boolean terrainIsNotTaken(){
        return gameBoard.containsKey(coordinate);
    }

    private void expandSettlement() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    private void expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType() {
        for(Coordinate terrainCoordinate : gameBoard.keySet()){
            if(terrainBelongsToTheSameSettlementAndHasTheSameType(terrainCoordinate)){
                findCounterClockwiseCoordinatesAroundCoordinate(terrainCoordinate);
                expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
            }
        }
    }

    private boolean terrainBelongsToTheSameSettlementAndHasTheSameType(Coordinate terrainCoordinate) {
        return gameBoard.get(terrainCoordinate).getSettlementID() == settlementID
                && gameBoard.get(terrainCoordinate).getTerrainType() == terrainType;
    }

    private void expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited() {
        for(Coordinate tempCoordinate : counterClockwiseCoordinatesAroundCoordinate){
            if(adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(tempCoordinate))
                placeVillagersAndMarkCoordinateAsVisited(tempCoordinate);
        }
    }

    private void processCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.terrainType = gameBoard.get(coordinate).getTerrainType();
        getIDFromAdjacentSettlement();
    }

    private void getIDFromAdjacentSettlement() {
        getIdFromAnyOfTheSettlementsAroundCoordinate();

        mergeSettlementsThatCanBeMerged();
    }

    private void mergeSettlementsThatCanBeMerged() {
        idOfSettlementsToBeMerged = new TreeSet<>();
        amountOfSettlements = 0;
        for(int currentSettlementID : uniqueIDsOfSettlementsOnGameBoard)
            getIdOfFirstSettlementAndGetIDsOfSettlementsThatCanBeMerged(currentSettlementID);

        ifThereIsMoreThanOneSettlementMergeThem();
    }

    private void getIdFromAnyOfTheSettlementsAroundCoordinate() {
        uniqueIDsOfSettlementsOnGameBoard = new TreeSet<>();
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for(Coordinate tempCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
            getIdOfSettlementsAdjacentToCoordinate(tempCoordinate);
        }
    }

    private void ifThereIsMoreThanOneSettlementMergeThem() {
        if(amountOfSettlements > 0)
            mergeSettlement(idOfSettlementsToBeMerged);
    }

    private void getIdOfFirstSettlementAndGetIDsOfSettlementsThatCanBeMerged(int currentSettlementID) {
        if(amountOfSettlements == 0)
            this.settlementID = currentSettlementID;
        else {
            idOfSettlementsToBeMerged.add(currentSettlementID);
            amountOfSettlements++;
        }
    }

    private void getIdOfSettlementsAdjacentToCoordinate(Coordinate tempCoordinate) {
        if (coordinateHasVillagerOnTheSameTerrainType(tempCoordinate)){
            if (!uniqueIDsOfSettlementsOnGameBoard.contains(tempCoordinate.hashCode()))
                uniqueIDsOfSettlementsOnGameBoard.add(tempCoordinate.hashCode());
        }
    }

    private boolean coordinateHasVillagerOnTheSameTerrainType(Coordinate tempCoordinate) {
        return gameBoard.containsKey(tempCoordinate)
                && gameBoard.get(tempCoordinate).hasVillager()
                && gameBoard.get(tempCoordinate).getTerrainType() == terrainType;
    }

    private void mergeSettlement(TreeSet<Integer> idOfSettlementsToBeMerged) {
        for(Coordinate tempCoordinate : gameBoard.keySet()){
            if(gameBoard.containsKey(tempCoordinate)
                    && idOfSettlementsToBeMerged.contains(gameBoard.get(tempCoordinate).getSettlementID())){
                gameBoard.get(tempCoordinate).setSettlementID(settlementID);
            }
        }
    }

    private boolean totoroCanBePlaced() {
        return terrainIsNotAVolcano()
                && terrainIsNotTaken()
                && isAdjacentToSettlementOfAtLeastSizeFive()
                && thereIsNoOtherTotoroInSettlement();
    }

    private boolean thereIsNoOtherTotoroInSettlement() {
        for(Coordinate tempCoordinate : gameBoard.keySet()){
            if(ifCoordinateIsInSettlementAndHasATotoro(tempCoordinate))
                return false;
        }
        return true;
    }

    private boolean ifCoordinateIsInSettlementAndHasATotoro(Coordinate tempCoordinate) {
        return gameBoard.containsKey(tempCoordinate)
                && gameBoard.get(tempCoordinate).getSettlementID() == settlementID
                && gameBoard.get(tempCoordinate).hasTotoro();
    }

    private void placeTotoroAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTotoro();
        gameBoard.get(coordinate).setSettlementID(settlementID);
    }

    private boolean isAdjacentToSettlementOfAtLeastSizeFive() {
        settlementSize = 0;
        minimumSizeOfSettlementAdjacentToTotoro = 5;
        for(Coordinate coordinate : gameBoard.keySet()){
            ifCoordinateBelongsToSettlemnetIncreaseSettlementSize(coordinate);
            if(gameBoard.get(coordinate).hasTotoro())
                return false;
            if(settlementSize == minimumSizeOfSettlementAdjacentToTotoro)
                return true;
        }
        return false;
    }

    private void ifCoordinateBelongsToSettlemnetIncreaseSettlementSize(Coordinate coordinate) {
        if(gameBoard.containsKey(coordinate) && gameBoard.get(coordinate).getSettlementID() == settlementID)
            settlementSize++;
    }
}
