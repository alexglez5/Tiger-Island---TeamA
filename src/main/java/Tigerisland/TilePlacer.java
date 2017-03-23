package Tigerisland;

import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends ActionHelper{
    private Coordinate leftOfMainTerrainCoordinate;
    private Coordinate mainTerrainCoordinate;
    private Coordinate rightOfMainTerrainCoordinate;
    private Orientation orientation;

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        this.mainTerrainCoordinate = mainTerrainCoordinate;
        this.orientation = terrainsOrientation;
        updateXAndYCoordinateOfCurrentTerrain(mainTerrainCoordinate);

        determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();

        placeTileInLevelOneOrNuke(tile);
    }

    private void determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation() {
        switch (orientation){
            case FromBottom:
                leftOfMainTerrainCoordinate = belowAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = belowAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromBottomRight:
                leftOfMainTerrainCoordinate = belowAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = toTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTopRight:
                leftOfMainTerrainCoordinate = toTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = overAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTop:
                leftOfMainTerrainCoordinate = overAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = overAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromTopLeft:
                leftOfMainTerrainCoordinate = overAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = toTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromBottomLeft:
                leftOfMainTerrainCoordinate = toTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = belowAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
        }
    }

    private void placeTileInLevelOneOrNuke(Tile tile) {
        if(tileIsPlacedOnLevelOne())
            placeHexesOfTileInMap(tile);
        else
            nuke(tile);
    }

    private boolean tileIsPlacedOnLevelOne(){
        return !gameBoard.containsKey(leftOfMainTerrainCoordinate) &&
                !gameBoard.containsKey(mainTerrainCoordinate) &&
                !gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }

    private void placeHexesOfTileInMap(Tile tile) {
        if(tileCanBePlacedInLevelOne() || firstTileInTheGame(tile)){
            putInMap(tile);
        }
    }

    private void nuke(Tile tile){
        if(canNuke())
            placeHexesOnTopOfOtherHexes(tile);
    }

    private Boolean tileCanBePlacedInLevelOne(){
        return atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge();
    }

    private boolean firstTileInTheGame(Tile tile) {
        return tile.getTileID() == 1;
    }

    private void putInMap(Tile tile) {
        gameBoard.put(leftOfMainTerrainCoordinate, tile.getLeftOfMainTerrain());
        gameBoard.put(mainTerrainCoordinate, tile.getMainTerrain());
        gameBoard.put(rightOfMainTerrainCoordinate, tile.getRightOfMainTerrain());
    }

    private Boolean canNuke(){
        return tileCompletelyCoversHexes()
                && tileIsNotPlacedOnTopOfTotoroOrTiger()
                && tileIsNotPerfectlyOnTopOfAnotherTile()
                && mainTerrainIsOnTopOfAnotherMainTerrain()
                && tileDoesNotCompletelyWipeOutASettlement();
    }

    private void placeHexesOnTopOfOtherHexes(Tile tile) {
        putInMap(tile);
        increaseLevelOfHexesOfATile();
    }

    private Boolean atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge(){
        return atLeastOneCoordinateAroundOneHexOfTheTileContainsAHex();
    }

    private boolean tileCompletelyCoversHexes() {
        return hexesAreAtTheSameLevel();

    }

    private boolean tileIsNotPlacedOnTopOfTotoroOrTiger(){
        return coordinateDoesNotContainTotoroOrTiger(leftOfMainTerrainCoordinate)
                && coordinateDoesNotContainTotoroOrTiger(mainTerrainCoordinate)
                && coordinateDoesNotContainTotoroOrTiger(rightOfMainTerrainCoordinate);
    }

    private Boolean tileIsNotPerfectlyOnTopOfAnotherTile(){
        return hexesArePartOfAtLeastTwoDifferentTiles();
    }

    private boolean mainTerrainIsOnTopOfAnotherMainTerrain() {
        return gameBoard.get(mainTerrainCoordinate).getTerrainType() == TerrainType.Volcano;
    }

    private boolean tileDoesNotCompletelyWipeOutASettlement() {
        TreeSet<Integer> settlementIdsOfHexesInTile = new TreeSet<>();
        if(gameBoard.containsKey(leftOfMainTerrainCoordinate) && gameBoard.get(leftOfMainTerrainCoordinate).hasVillager())
            settlementIdsOfHexesInTile.add(gameBoard.get(leftOfMainTerrainCoordinate).getSettlementID());
        if(gameBoard.containsKey(mainTerrainCoordinate) && gameBoard.get(mainTerrainCoordinate).hasVillager())
            settlementIdsOfHexesInTile.add(gameBoard.get(mainTerrainCoordinate).getSettlementID());
        if(gameBoard.containsKey(rightOfMainTerrainCoordinate) && gameBoard.get(rightOfMainTerrainCoordinate).hasVillager())
            settlementIdsOfHexesInTile.add(gameBoard.get(rightOfMainTerrainCoordinate).getSettlementID());

        if(settlementIdsOfHexesInTile.size() == 0)
            return true;

        for(int idOfSettlementThanMightBeWipeOut : settlementIdsOfHexesInTile) {
            int sizeLeftOfCurrentSettlement = 0;
            for (Coordinate tempCoordinate : gameBoard.keySet()) {
                if (gameBoard.get(tempCoordinate).hasVillager()
                        && gameBoard.get(tempCoordinate).getSettlementID() == idOfSettlementThanMightBeWipeOut
                        && isNotOneOfCoordinatesThatWillBeWipedOut(tempCoordinate)) {
                    sizeLeftOfCurrentSettlement++;
                }
            }
            if(sizeLeftOfCurrentSettlement < 1)
                return false;
        }
        return true;
    }

    private void increaseLevelOfHexesOfATile() {
        gameBoard.get(leftOfMainTerrainCoordinate).increaseLevel();
        gameBoard.get(mainTerrainCoordinate).increaseLevel();
        gameBoard.get(rightOfMainTerrainCoordinate).increaseLevel();
    }

    private boolean atLeastOneCoordinateAroundOneHexOfTheTileContainsAHex() {
        findCounterClockwiseCoordinatesAroundCoordinate(leftOfMainTerrainCoordinate);
        if (AtLeasOneAdjacentCoordinateContainsAHex())
            return true;
        findCounterClockwiseCoordinatesAroundCoordinate(rightOfMainTerrainCoordinate);
        if (AtLeasOneAdjacentCoordinateContainsAHex())
            return true;
        findCounterClockwiseCoordinatesAroundCoordinate(mainTerrainCoordinate);
        if (AtLeasOneAdjacentCoordinateContainsAHex())
            return true;
        return false;
    }

    private boolean hexesAreAtTheSameLevel(){
        return hexesOfTileAreOccupied() &&
                gameBoard.get(leftOfMainTerrainCoordinate).getLevel() ==
                        gameBoard.get(mainTerrainCoordinate).getLevel() &&
                gameBoard.get(mainTerrainCoordinate).getLevel() ==
                        gameBoard.get(rightOfMainTerrainCoordinate).getLevel();

    }

    private boolean coordinateDoesNotContainTotoroOrTiger(Coordinate terrainCoordinate){
        return !(gameBoard.containsKey(terrainCoordinate)
                && (gameBoard.get(terrainCoordinate).hasTotoro() || gameBoard.get(terrainCoordinate).hasTiger()));

    }

    private Boolean hexesArePartOfAtLeastTwoDifferentTiles(){
        return gameBoard.get(leftOfMainTerrainCoordinate).getTileID() !=
                gameBoard.get(mainTerrainCoordinate).getTileID() ||
                gameBoard.get(mainTerrainCoordinate).getTileID() !=
                gameBoard.get(rightOfMainTerrainCoordinate).getTileID() ||
                gameBoard.get(leftOfMainTerrainCoordinate).getTileID() !=
                gameBoard.get(rightOfMainTerrainCoordinate).getTileID();
    }

    private boolean isNotOneOfCoordinatesThatWillBeWipedOut(Coordinate tempCoordinate) {
        return tempCoordinate == leftOfMainTerrainCoordinate
                || tempCoordinate == mainTerrainCoordinate
                || tempCoordinate == rightOfMainTerrainCoordinate;
    }

    private boolean AtLeasOneAdjacentCoordinateContainsAHex() {
        for(int i = 0; i < sidesOfAHex; i++){
            if(gameBoard.containsKey(counterClockwiseCoordinatesAroundCoordinate[i]))
                return true;
        }
        return false;
    }

    private boolean hexesOfTileAreOccupied(){
        return gameBoard.containsKey(leftOfMainTerrainCoordinate) &&
                gameBoard.containsKey(mainTerrainCoordinate) &&
                gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }
}