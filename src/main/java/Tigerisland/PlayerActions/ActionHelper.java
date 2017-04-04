package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.GameBoard;
import Tigerisland.Orientation;

import java.util.TreeSet;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class ActionHelper extends GameBoard {
    protected TreeSet<Integer> settlementIdsOfHexesInTile;
    protected Coordinate leftOfMainTerrainCoordinate;
    protected Coordinate mainTerrainCoordinate;
    protected Coordinate rightOfMainTerrainCoordinate;
    protected Orientation orientation;
    protected final int sidesOfAHex = 6;
    protected final int sidesOfATile = 9;
    protected Coordinate[] counterClockwiseCoordinatesAroundCoordinate;
    protected Coordinate[] coordinatesAroundATile;
    private int currentTerrainXCoordinate;
    private int currentTerrainYCoordinate;

    public boolean isToTheRightOfTile(Coordinate tempCoordinate){
        return tempCoordinate.getXCoordinate() > mainTerrainCoordinate.getXCoordinate();
    }

    public void determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation() {
        switch (orientation) {
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

    public void findCoordinatesAroundATile(){
        coordinatesAroundATile = new Coordinate[sidesOfATile];
        determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
        coordinatesAroundATile[0] = overAndToTheRightOfMain(mainTerrainCoordinate);
        coordinatesAroundATile[1] = overAndToTheLeftOfMain(mainTerrainCoordinate);
        coordinatesAroundATile[2] = toTheLeftOfMain(mainTerrainCoordinate);
        coordinatesAroundATile[3] = toTheLeftOfMain(leftOfMainTerrainCoordinate);
        coordinatesAroundATile[4] = belowAndToTheLeftOfMain(leftOfMainTerrainCoordinate);
        coordinatesAroundATile[5] = belowAndToTheRightOfMain(leftOfMainTerrainCoordinate);
        coordinatesAroundATile[6] = belowAndToTheRightOfMain(rightOfMainTerrainCoordinate);
        coordinatesAroundATile[7] = toTheRightOfMain(rightOfMainTerrainCoordinate);
        coordinatesAroundATile[8] = overAndToTheRightOfMain(rightOfMainTerrainCoordinate);
    }

    public void findCounterClockwiseCoordinatesAroundCoordinate(Coordinate terrainCoordinate) {
        counterClockwiseCoordinatesAroundCoordinate = new Coordinate[sidesOfAHex];
        counterClockwiseCoordinatesAroundCoordinate[0] = belowAndToTheLeftOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[1] = belowAndToTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[2] = toTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[3] = overAndToTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[4] = overAndToTheLeftOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[5] = toTheLeftOfMain(terrainCoordinate);
    }

    public Coordinate belowAndToTheLeftOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate + 1);
    }

    public Coordinate belowAndToTheRightOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate + 1);
    }

    public Coordinate toTheRightOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate);
    }

    public Coordinate overAndToTheRightOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate - 1);
    }

    public Coordinate overAndToTheLeftOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate - 1);
    }

    public Coordinate toTheLeftOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate);
    }

    public void updateXAndYCoordinateOfCurrentTerrain(Coordinate terrainCoordinate) {
        this.currentTerrainXCoordinate = terrainCoordinate.getXCoordinate();
        this.currentTerrainYCoordinate = terrainCoordinate.getYCoordinate();
    }

    public boolean terrainContainsAPiece(Coordinate terrainCoordinate) {
        return gameBoard.containsKey(terrainCoordinate)
                && (gameBoard.get(terrainCoordinate).hasVillager()
                || gameBoard.get(terrainCoordinate).hasTotoro()
                || gameBoard.get(terrainCoordinate).hasTiger());
    }
}
