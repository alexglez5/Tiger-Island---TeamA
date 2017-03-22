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

    public void placeTotoro(Coordinate coordinate){
        this.coordinate = coordinate;
        this.terrainType = gameBoard.get(coordinate).getTerrainType();
        if(totoroCanBePlaced()){
            getIDFromAdjacentSettlement();
            placeTotoroAtGivenCoordinate();
        }
    }

    private void getIDFromAdjacentSettlement() {
        //Set<Integer> pa = new HashSet<>();
//        Map<Integer, Coordinate> hm = new HashMap<>();
        TreeSet<Integer> set = new TreeSet<>();
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
//        int tempSettlementID = 0;
        for(Coordinate tempCoordinate : counterClockwiseCoordinatesAroundCoordinate){
            if(gameBoard.containsKey(tempCoordinate)
                    && gameBoard.get(tempCoordinate).hasVillager()
                    && gameBoard.get(tempCoordinate).getTerrainType() == terrainType)
                if(!set.contains(tempCoordinate.hashCode()))
                    set.add(tempCoordinate.hashCode());


//                if(!pa.contains(tempCoordinate.hashCode())) {
//                    tempSettlementID = tempCoordinate.hashCode();
//                    pa.add(tempSettlementID);
//                }
//                else
//                    gameBoard.get(coordinate).setSettlementID(tempSettlementID);

        }
//        this.settlementID = tempSettlementID;
//        for (int i = 0; i < set.size(); i++){
//            this.settlementID =
//        }
        int p = 0;
//        ArrayList<Integer> idOfSettlementsToBeMerged = new ArrayList<>();
        TreeSet<Integer> idOfSettlementsToBeMerged = new TreeSet<>();
        for(int ID : set){
            if(p == 0)
                this.settlementID = ID;
            else {
                idOfSettlementsToBeMerged.add(ID);
                p++;
            }
        }
        if(p > 0)
            mergeSettlement(idOfSettlementsToBeMerged);
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
                && isAdjacentToSettlementOfAtLeastSizeFive();
    }

    private void placeTotoroAtGivenCoordinate() {
        gameBoard.get(coordinate).placeTotoro();
        gameBoard.get(coordinate).setSettlementID(settlementID);
    }

    private boolean isAdjacentToSettlementOfAtLeastSizeFive() {
        int settlementSize = 0;
        final int minimumSizeOfSettlementAdjacentToTotoro = 5;
        for(Coordinate coordinate : gameBoard.keySet()){
            if(gameBoard.containsKey(coordinate) && gameBoard.get(coordinate).getSettlementID() == settlementID){
                settlementSize++;
            }
            if(gameBoard.get(coordinate).hasTotoro())
                return false;
            if(settlementSize == minimumSizeOfSettlementAdjacentToTotoro)
                return true;
        }
        return false;
    }

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
}
