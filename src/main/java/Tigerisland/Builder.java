package Tigerisland;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class Builder extends ActionHelper {
    private Coordinate coordinate;
    private TerrainType terrainType;
    private int settlementID;
    private Set<Coordinate> visitedCoordinates;

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
