package Tigerisland;
/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends GameBoard{
    private final int sidesOfAHex = 6;
    private int currentTerrainXCoordinate;
    private int currentTerrainYCoordinate;
    private Coordinate leftOfMainTerrainCoordinate;
    private Coordinate mainTerrainCoordinate;
    private Coordinate rightOfMainTerrainCoordinate;
    private Orientation orientation;
    private Coordinate[] counterClockwiseCoordinatesAroundCoordinate;

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        this.mainTerrainCoordinate = mainTerrainCoordinate;
        this.orientation = terrainsOrientation;
        updateXAndYCoordinateOfCurrentTerrain(mainTerrainCoordinate);

        determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();

        placeTileInLevelOneOrNuke(tile);
    }

    private void placeTileInLevelOneOrNuke(Tile tile) {
        if(tileIsPlacedOnLevelOne())
            placeHexesOfTileInMap(tile);
        else
            nuke(tile);
    }

    private void nuke(Tile tile){
        if(canNuke())
            placeHexesOnTopOfOtherHexes(tile);
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

    private Boolean canNuke(){
        return tileCompletelyCoversHexes() &&
                tileIsNotPerfectlyOnTopOfAnotherTile() &&
                mainTerrainIsOnTopOfAnotherMainTerrain();
    }

    private void placeHexesOfTileInMap(Tile tile) {
        if(tileCanBePlacedInLevelOne() || firstTileInTheGame(tile)){
            putInMap(tile);
        }
    }

    private void placeHexesOnTopOfOtherHexes(Tile tile) {
        putInMap(tile);
        increaseLevelOfHexesOfATile();
    }

    private boolean firstTileInTheGame(Tile tile) {
        return tile.getTileID() == 1;
    }

    private Boolean tileCanBePlacedInLevelOne(){
        return atLeastOneEdgeIsTouchingAnyPreviouslyPlacedTileEdge();
    }

    private boolean tileIsPlacedOnLevelOne(){
        return !gameBoard.containsKey(leftOfMainTerrainCoordinate) &&
                !gameBoard.containsKey(mainTerrainCoordinate) &&
                !gameBoard.containsKey(rightOfMainTerrainCoordinate);
    }

    private boolean tileCompletelyCoversHexes() {
        return hexesAreAtTheSameLevel();

    }

    private boolean mainTerrainIsOnTopOfAnotherMainTerrain() {
        return gameBoard.get(mainTerrainCoordinate).getTerrainType() == TerrainType.Volcano;
    }

    private Boolean tileIsNotPerfectlyOnTopOfAnotherTile(){
        return hexesArePartOfAtLeastTwoDifferentTiles();
    }

    private void putInMap(Tile tile) {
        gameBoard.put(leftOfMainTerrainCoordinate, tile.getLeftOfMainTerrain());
        gameBoard.put(mainTerrainCoordinate, tile.getMainTerrain());
        gameBoard.put(rightOfMainTerrainCoordinate, tile.getRightOfMainTerrain());
    }

    private void increaseLevelOfHexesOfATile() {
        gameBoard.get(leftOfMainTerrainCoordinate).increaseLevel();
        gameBoard.get(mainTerrainCoordinate).increaseLevel();
        gameBoard.get(rightOfMainTerrainCoordinate).increaseLevel();
    }

    private boolean hexesAreAtTheSameLevel(){
        return hexesOfTileAreOccupied() &&
                gameBoard.get(leftOfMainTerrainCoordinate).getLevel() ==
                        gameBoard.get(mainTerrainCoordinate).getLevel() &&
                gameBoard.get(mainTerrainCoordinate).getLevel() ==
                        gameBoard.get(rightOfMainTerrainCoordinate).getLevel();

    }

    private Boolean hexesArePartOfAtLeastTwoDifferentTiles(){
        return gameBoard.get(leftOfMainTerrainCoordinate).getTileID() !=
                gameBoard.get(mainTerrainCoordinate).getTileID() ||
                gameBoard.get(mainTerrainCoordinate).getTileID() !=
                gameBoard.get(rightOfMainTerrainCoordinate).getTileID() ||
                gameBoard.get(leftOfMainTerrainCoordinate).getTileID() !=
                gameBoard.get(rightOfMainTerrainCoordinate).getTileID();
    }

    private boolean hexesOfTileAreOccupied(){
        return gameBoard.containsKey(leftOfMainTerrainCoordinate) &&
                gameBoard.containsKey(mainTerrainCoordinate) &&
                gameBoard.containsKey(rightOfMainTerrainCoordinate);
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