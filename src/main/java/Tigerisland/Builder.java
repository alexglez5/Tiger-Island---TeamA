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
    private TreeSet<Integer> uniqueIDsOfSettlementsAroundCoordinate;
    private TreeSet<Integer> idsOfsettlementsToBeMerged;
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

    public void placeTotoro(Coordinate coordinate, int settlementID){
        processCoordinate(coordinate, settlementID);
        if(totoroCanBePlaced())
            placeTotoroAtGivenCoordinate();

        mergeSettlementsThatCanBeMerged(coordinate);
    }

    public void placeTiger(Coordinate coordinate, int settlementID){
        processCoordinate(coordinate, settlementID);
        if(tigerCanBePlaced())
            placeTigerAtGivenCoordinate();

        mergeSettlementsThatCanBeMerged(coordinate);
    }

    private void placeTigerAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTiger();
        gameBoard.get(coordinate).setSettlementID(settlementID);
    }

    private boolean tigerCanBePlaced() {
        return terrainIsNotAVolcano()
                && terrainIsNotTaken()
                && levelIsAtLeastThree()
                && adjacentSettlementDoesNotContainATiger();
    }

    private boolean levelIsAtLeastThree() {
        return gameBoard.get(coordinate).getLevel() > 2;
    }

    private void mergeSettlementsThatCanBeMerged(Coordinate coordinate) {
        uniqueIDsOfSettlementsAroundCoordinate = new TreeSet<>();
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        idsOfsettlementsToBeMerged = new TreeSet<>();
        for(Coordinate tempCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
            if (gameBoard.containsKey(tempCoordinate)
                    && gameBoard.get(tempCoordinate).hasVillager()
                    && gameBoard.get(tempCoordinate).getSettlementID() != settlementID) {
                idsOfsettlementsToBeMerged.add(gameBoard.get(tempCoordinate).getSettlementID());
            }
        }

        for (Coordinate tempCoordinate : gameBoard.keySet()) {
            if (gameBoard.containsKey(tempCoordinate)
                    && idsOfsettlementsToBeMerged.contains(gameBoard.get(tempCoordinate).getSettlementID())) {
                gameBoard.get(tempCoordinate).setSettlementID(settlementID);
            }
        }
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
        minimumSizeOfSettlementAdjacentToTotoro = 5;
        for(Coordinate coordinate : gameBoard.keySet()){
            ifCoordinateBelongsToSettlementIncreaseSettlementSize(coordinate);
            if(settlementSize == minimumSizeOfSettlementAdjacentToTotoro)
                return true;
        }
        return false;
    }

    private boolean adjacentSettlementDoesNotContainATotoro(){
        for(Coordinate coordinate : gameBoard.keySet()){
            if(gameBoard.get(coordinate).hasTotoro())
                return false;
        }
        return true;
    }

    private boolean adjacentSettlementDoesNotContainATiger(){
        for(Coordinate coordinate : gameBoard.keySet()){
            if(gameBoard.get(coordinate).hasTiger())
                return false;
        }
        return true;
    }

    private void ifCoordinateBelongsToSettlementIncreaseSettlementSize(Coordinate coordinate) {
        if(gameBoard.containsKey(coordinate) && gameBoard.get(coordinate).getSettlementID() == settlementID)
            settlementSize++;
    }

//    private boolean ifCoordinateIsInSettlementAndHasATotoro(Coordinate tempCoordinate) {
//        return gameBoard.containsKey(tempCoordinate)
//                && gameBoard.get(tempCoordinate).getSettlementID() == settlementID
//                && gameBoard.get(tempCoordinate).hasTotoro();
//    }

    private boolean settlementCanBeExpanded() {
        return terrainTypeIsNotAVolcano();
    }

    private void expandSettlement() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    private boolean terrainTypeIsNotAVolcano() {
        return terrainType != TerrainType.Volcano;
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
    
    
//    private void getIdFromAnyOfTheSettlementsAroundCoordinate() {
//        uniqueIDsOfSettlementsAroundCoordinate = new TreeSet<>();
//        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
//        for(Coordinate tempCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
//            getIdOfSettlementsAdjacentToCoordinate(tempCoordinate);
//        }
//    }

//    private void mergeSettlementsThatCanBeMerged() {
//        idsOfsettlementsToBeMerged = new TreeSet<>();
//        amountOfSettlements = 0;
//        for(int currentSettlementID : uniqueIDsOfSettlementsAroundCoordinate)
//            getIdOfFirstSettlementAndGetIDsOfSettlementsThatCanBeMerged(currentSettlementID);
//
//        ifThereIsMoreThanOneSettlementMergeThem();
//    }

//    private void getIdOfSettlementsAdjacentToCoordinate(Coordinate tempCoordinate) {
//        if (coordinateHasVillagerOnTheSameTerrainType(tempCoordinate)){
//            if (!uniqueIDsOfSettlementsAroundCoordinate.contains(tempCoordinate.hashCode()))
//                uniqueIDsOfSettlementsAroundCoordinate.add(tempCoordinate.hashCode());
//        }
//    }

//    private void getIdOfFirstSettlementAndGetIDsOfSettlementsThatCanBeMerged(int currentSettlementID) {
//        if(amountOfSettlements == 0)
//            this.settlementID = currentSettlementID;
//        else {
//            idsOfsettlementsToBeMerged.add(currentSettlementID);
//            amountOfSettlements++;
//        }
//    }

//    private void ifThereIsMoreThanOneSettlementMergeThem() {
//        if(amountOfSettlements > 0)
//            mergeSettlement(idsOfsettlementsToBeMerged);
//    }

//    private boolean coordinateHasVillagerOnTheSameTerrainType(Coordinate tempCoordinate) {
//        return gameBoard.containsKey(tempCoordinate)
//                && gameBoard.get(tempCoordinate).hasVillager()
//                && gameBoard.get(tempCoordinate).getTerrainType() == terrainType;
//    }

//    private void mergeSettlement(TreeSet<Integer> idsOfsettlementsToBeMerged) {
//        for(Coordinate tempCoordinate : gameBoard.keySet()){
//            if(gameBoard.containsKey(tempCoordinate)
//                    && idsOfsettlementsToBeMerged.contains(gameBoard.get(tempCoordinate).getSettlementID())){
//                gameBoard.get(tempCoordinate).setSettlementID(settlementID);
//            }
//        }
//    }

    private boolean settlementCanBeFound() {
        return terrainIsNotTaken()
                && terrainIsNotAVolcano()
                && terrainIsInLevelOne();
    }

    private boolean terrainIsNotTaken(){
        return gameBoard.containsKey(coordinate);
    }

    private boolean terrainIsNotAVolcano(){
        return gameBoard.get(coordinate).getTerrainType() != TerrainType.Volcano;
    }

    private void processCoordinate(Coordinate coordinate, int settlementID) {
        this.coordinate = coordinate;
        this.settlementID = settlementID;
    }

    private boolean terrainIsInLevelOne(){
        return gameBoard.get(coordinate).getLevel() == 1;
    }
//TODO find out if this function is really needed if not delete
//    private void mergeSettlementsIfPossibleAfterExpanding() {
//        for(Coordinate visitedCoordinate : visitedCoordinates) {
//            processCoordinate(visitedCoordinate);
//            findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
//            idsOfsettlementsToBeMerged = new TreeSet<>();
//            for(Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
//                if (coordinateHasVillagerOnTheSameTerrainType(neighborCoordinate)
//                        && gameBoard.get(neighborCoordinate).getSettlementID() != settlementID){
//                    idsOfsettlementsToBeMerged.add(gameBoard.get(neighborCoordinate).getSettlementID());
//                }
//            }
//            mergeSettlement(idsOfsettlementsToBeMerged);
//        }
//    }

}
