package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.GameBoard;
import Tigerisland.Orientation;

import java.util.Set;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class ActionHelper extends GameBoard {
    protected final int sidesOfAHex = 6;
    protected final int sidesOfATile = 9;
    protected Set<Integer> settlementIdsOfHexesInTile;
    protected Coordinate leftOfMainTerrainCoordinate;
    protected Coordinate mainTerrainCoordinate;
    protected Coordinate rightOfMainTerrainCoordinate;
    protected Orientation orientation;
    protected Coordinate[] counterClockwiseCoordinatesAroundCoordinate;
    protected Coordinate[] coordinatesAroundATile;
    private int currentTerrainXCoordinate;
    private int currentTerrainYCoordinate;
    private int ydiff;

    public boolean isToTheRightOfTile(Coordinate tempCoordinate) {
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

    public void findCoordinatesAroundATileAssumingBottomRotation() {
        coordinatesAroundATile = new Coordinate[sidesOfATile];
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

    public void findCoordinatesAroundATile() {
        switch (orientation) {
            case FromBottom:
                findCoordinatesAroundATileAssumingBottomRotation();
                break;
            case FromBottomRight:
                mainTerrainCoordinate = belowAndToTheRightOfMain(mainTerrainCoordinate);
                orientation = Orientation.FromTop;
                determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
                findCoordinatesAroundATileAssumingBottomRotation();
                flipAroundXAxis();
                break;
            case FromTopRight:
                mainTerrainCoordinate = overAndToTheRightOfMain(mainTerrainCoordinate);
                orientation = Orientation.FromBottom;
                determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
                findCoordinatesAroundATileAssumingBottomRotation();
                break;
            case FromTop:
                findCoordinatesAroundATileAssumingBottomRotation();
                flipAroundXAxis();
                break;
            case FromTopLeft:
                mainTerrainCoordinate = overAndToTheLeftOfMain(mainTerrainCoordinate);
                orientation = Orientation.FromBottom;
                determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
                findCoordinatesAroundATileAssumingBottomRotation();
                break;
            case FromBottomLeft:
                mainTerrainCoordinate = belowAndToTheLeftOfMain(mainTerrainCoordinate);
                orientation = Orientation.FromTop;
                determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
                findCoordinatesAroundATileAssumingBottomRotation();
                flipAroundXAxis();
                break;
        }
    }

    private void flipAroundXAxis() {
        ydiff = 0;
        for(int i = 0; i < coordinatesAroundATile.length; i++){
            ydiff = coordinatesAroundATile[i].getYCoordinate() - mainTerrainCoordinate.getYCoordinate();
            coordinatesAroundATile[i] = new Coordinate(coordinatesAroundATile[i].getXCoordinate() + ydiff,
                    coordinatesAroundATile[i].getYCoordinate() - 2 * ydiff);
        }
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
