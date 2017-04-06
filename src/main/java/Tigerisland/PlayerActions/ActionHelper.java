package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.Game;
import Tigerisland.Orientation;

import java.util.Set;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class ActionHelper {
    protected final int sidesOfAHex = 6;
    protected Set<Integer> settlementIdsOfHexesInTile;
    protected Coordinate leftOfMainTerrainCoordinate;
    protected Coordinate mainTerrainCoordinate;
    protected Coordinate rightOfMainTerrainCoordinate;
    protected Orientation orientation;
    public Coordinate[] surroundingCoordinates;
    private int currentTerrainXCoordinate;
    private int currentTerrainYCoordinate;

    public void findCounterClockwiseCoordinatesAroundCoordinate(Coordinate terrainCoordinate) {
        surroundingCoordinates = new Coordinate[sidesOfAHex];
        surroundingCoordinates[0] = belowAndToTheLeftOfMain(terrainCoordinate);
        surroundingCoordinates[1] = belowAndToTheRightOfMain(terrainCoordinate);
        surroundingCoordinates[2] = toTheRightOfMain(terrainCoordinate);
        surroundingCoordinates[3] = overAndToTheRightOfMain(terrainCoordinate);
        surroundingCoordinates[4] = overAndToTheLeftOfMain(terrainCoordinate);
        surroundingCoordinates[5] = toTheLeftOfMain(terrainCoordinate);
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

}
