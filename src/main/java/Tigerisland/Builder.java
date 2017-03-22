package Tigerisland;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class Builder extends ActionHelper {
    private int settlementsFounded;
    private Coordinate coordinate;
    private TerrainType terrainType;
    private int settlementID;


//    public static void updateSettlementsFounded(){
//        settlementsFounded = 0;
//    }

    public void expandASettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType){
        this.settlementID = coordinateOfAnyHexInSettlement.hashCode();
        this.terrainType = terrainType;
//        this.coordinate = coordinateOfAnyHexInSettlement;
        if(settlementCanBeExpanded())
            expandSettlement();

    }

    private void expandSettlement() {
        Set<Coordinate> visitedCoordinates = new HashSet<>();
        for(Coordinate terrainCoordinate : gameBoard.keySet()){
            if(gameBoard.get(terrainCoordinate).getSettlementID() == settlementID
                    && gameBoard.get(terrainCoordinate).getTerrainType() == terrainType){
                findCounterClockwiseCoordinatesAroundCoordinate(terrainCoordinate);
                for(Coordinate tempCoordinate : counterClockwiseCoordinatesAroundCoordinate){
                    if(gameBoard.containsKey(tempCoordinate)){
                        if(gameBoard.get(tempCoordinate).getTerrainType() == terrainType
                                && !visitedCoordinates.contains(tempCoordinate)){
                            placeVillagers(tempCoordinate);
                            visitedCoordinates.add(tempCoordinate);
                        }
                    }
                }
            }
        }
    }

    private void placeVillagers(Coordinate tempCoordinate) {
        gameBoard.get(tempCoordinate).placeVillagers();
        gameBoard.get(tempCoordinate).setSettlementID(settlementID);
    }

    private boolean settlementCanBeExpanded() {
        return terrainTypeIsNotAVolcano();
    }

//    private boolean thereIsAtLeastOneEmptyHexOfGivenTypeAdjacentToSettlement() {
//        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
//        for(Coordinate tempCoordinate : counterClockwiseCoordinatesAroundCoordinate){
//            if(gameBoard.containsKey(tempCoordinate)){
//                return gameBoard.get(coordinate).getTerrainType()
//                        == gameBoard.get(tempCoordinate).getTerrainType();
//            }
//        }
//        return false;
//    }

    private boolean terrainTypeIsNotAVolcano() {
        return terrainType != TerrainType.Volcano;
    }

    public void foundNewSettlement(Coordinate coordinate){
        this.coordinate = coordinate;
        if(settlementCanBeFound()) {
            gameBoard.get(coordinate).placeVillagers();
            gameBoard.get(coordinate).setSettlementID(coordinate.hashCode());
//            settlementsFounded++;
        }
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
}
