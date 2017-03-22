package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class ActionHelper extends GameBoard {
    protected final int sidesOfAHex = 6;
    private int currentTerrainXCoordinate;
    private int currentTerrainYCoordinate;
    protected Coordinate[] counterClockwiseCoordinatesAroundCoordinate;

    public void findCounterClockwiseCoordinatesAroundCoordinate(Coordinate terrainCoordinate){
        counterClockwiseCoordinatesAroundCoordinate = new Coordinate[sidesOfAHex];
        counterClockwiseCoordinatesAroundCoordinate[0] = belowAndToTheLeftOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[1] = belowAndToTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[2] = toTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[3] = overAndToTheRightOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[4] = overAndToTheLeftOfMain(terrainCoordinate);
        counterClockwiseCoordinatesAroundCoordinate[5] = toTheLeftOfMain(terrainCoordinate);
    }

    public Coordinate belowAndToTheLeftOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate + 1);
    }

    public Coordinate belowAndToTheRightOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate + 1);
    }

    public Coordinate toTheLeftOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate);
    }

    public Coordinate toTheRightOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate);
    }

    public Coordinate overAndToTheLeftOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate - 1);
    }

    public Coordinate overAndToTheRightOfMain(Coordinate terrainCoordinate){
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate - 1);
    }

    public void updateXAndYCoordinateOfCurrentTerrain(Coordinate terrainCoordinate){
        this.currentTerrainXCoordinate = terrainCoordinate.getXCoordinate();
        this.currentTerrainYCoordinate = terrainCoordinate.getYCoordinate();
    }
}
