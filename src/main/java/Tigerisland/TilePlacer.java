package Tigerisland;
/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends GameBoard{
    final int sidesOfAHex = 6;
    private int currentTerrainXCoordinate;
    private int currentTerrainYCoordinate;
    private Coordinate leftOfMainTerrainCoordinate;
    private Coordinate mainTerrainCoordinate;
    private Coordinate rightOfMainTerrainCoordinate;
    private Coordinate[] counterClockwiseCoordinatesAroundCoordinate;

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        this.mainTerrainCoordinate = mainTerrainCoordinate;
        currentTerrainXCoordinate = mainTerrainCoordinate.getXCoordinate();
        currentTerrainYCoordinate = mainTerrainCoordinate.getYCoordinate();

        switch (terrainsOrientation){
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
        
        if(tileIsPlacedOnLevelOne())
            placeHexesOfTileInMap(tile);
        else
            placeHexesOnTopOfOtherHexes(tile);
    }

    private void placeHexesOnTopOfOtherHexes(Tile tile) {
        putInMap(tile);
        increaseLevelOfHexesOfATile();
    }

    private void increaseLevelOfHexesOfATile() {
        gameBoard.get(leftOfMainTerrainCoordinate).increaseLevel();
        gameBoard.get(mainTerrainCoordinate).increaseLevel();
        gameBoard.get(rightOfMainTerrainCoordinate).increaseLevel();
    }

    private boolean tileIsPlacedOnLevelOne(){
        return !gameBoard.containsKey(leftOfMainTerrainCoordinate) &&
                !gameBoard.containsKey(mainTerrainCoordinate) &&
                !gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }

    public void nuke(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        if(canNuke())
            placeTile(tile,mainTerrainCoordinate,terrainsOrientation);
    }

    private void placeHexesOfTileInMap(Tile tile) {
        if(tileCanBePlacedInLevelOne() || firstTileInTheGame(tile)){
            putInMap(tile);
        }
    }

    private void putInMap(Tile tile) {
        gameBoard.put(leftOfMainTerrainCoordinate, tile.getLeftOfMainTerrain());
        gameBoard.put(mainTerrainCoordinate, tile.getMainTerrain());
        gameBoard.put(rightOfMainTerrainCoordinate, tile.getRightOfMainTerrain());
    }

    private boolean firstTileInTheGame(Tile tile) {
        return tile.getTileID() == 1;
    }

    private Boolean tileCanBePlacedInLevelOne(){
        return atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge();
    }

    private Boolean canNuke(){
        return tileCompletelyCoversHexes() &&
                tileIsNotPerfectlyOnTopOfAnotherTile();
    }

    private boolean hexesAreTaken(){
        return gameBoard.containsKey(leftOfMainTerrainCoordinate) &&
                gameBoard.containsKey(mainTerrainCoordinate) &&
                gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }

    private boolean hexesAreAtTheSameLevel(){
        return hexesAreTaken() &&
                gameBoard.get(leftOfMainTerrainCoordinate).getLevel() ==
                gameBoard.get(mainTerrainCoordinate).getLevel() &&
                gameBoard.get(mainTerrainCoordinate).getLevel() ==
                gameBoard.get(rightOfMainTerrainCoordinate).getLevel();

    }

    private boolean tileCompletelyCoversHexes() {
        return hexesAreAtTheSameLevel();

    }

    private Boolean tileIsNotPerfectlyOnTopOfAnotherTile(){
        return atLeastOneHexIsOnTopAnotherHexWithDifferentTileIDAsTheOthers();
    }

    private Boolean atLeastOneHexIsOnTopAnotherHexWithDifferentTileIDAsTheOthers(){
        return gameBoard.get(leftOfMainTerrainCoordinate).getTileID() !=
                gameBoard.get(mainTerrainCoordinate).getTileID() ||
                gameBoard.get(mainTerrainCoordinate).getTileID() !=
                gameBoard.get(rightOfMainTerrainCoordinate).getTileID() ||
                gameBoard.get(leftOfMainTerrainCoordinate).getTileID() !=
                gameBoard.get(rightOfMainTerrainCoordinate).getTileID();
    }

    private Boolean atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge(){
        return atLeastOneCoordinateAroundOneHexOfTheTileContainsAHex();
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

    private boolean AtLeasOneAdjacentCoordinateContainsAHex() {
        for(int i = 0; i < sidesOfAHex; i++){
            if(gameBoard.containsKey(counterClockwiseCoordinatesAroundCoordinate[i]))
                return true;
        }
        return false;
    }

    private void findCounterClockwiseCoordinatesAroundCoordinate(Coordinate terrainCoordinate){
        counterClockwiseCoordinatesAroundCoordinate = new Coordinate[sidesOfAHex];
        counterClockwiseCoordinatesAroundCoordinate[0] = belowAndToTheLeftOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[1] = belowAndToTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[2] = toTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[3] = overAndToTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[4] = overAndToTheLeftOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[5] = toTheLeftOfMain(terrainCoordinate);
    }

//    private Boolean hexesAreAdjacent(Coordinate c1, Coordinate c2){
//        return c2 == belowAndToTheLeftOfMain(c1) ||
//                c2 == belowAndToTheRightOfMain(c1) ||
//                c2 == toTheRightOfMain(c1) ||
//                c2 == overAndToTheRightOfMain(c1) ||
//                c2 == overAndToTheLeftOfMain(c1) ||
//                c2 == toTheLeftOfMain(c1);
//    }


    private Coordinate belowAndToTheLeftOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate + 1);
    }

    private Coordinate belowAndToTheRightOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate + 1);
    }

    private Coordinate toTheLeftOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate);
    }

    private Coordinate toTheRightOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate);
    }

    private Coordinate overAndToTheLeftOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate - 1);
    }

    private Coordinate overAndToTheRightOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate - 1);
    }

    private void updateXAndYCoordinateOfCurrentTerrain(Coordinate terrainCoordinate){
        this.currentTerrainXCoordinate = terrainCoordinate.getXCoordinate();
        this.currentTerrainYCoordinate = terrainCoordinate.getYCoordinate();
    }
}
