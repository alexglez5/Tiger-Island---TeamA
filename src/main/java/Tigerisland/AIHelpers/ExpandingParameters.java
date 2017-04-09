package Tigerisland.AIHelpers;

import Tigerisland.Coordinate;
import Tigerisland.TerrainType;

/**
 * Created by Alexander Gonzalez on 4/9/2017.
 */
public class ExpandingParameters {
    private Coordinate coordinate;
    private TerrainType terrainType;

    public ExpandingParameters(Coordinate coordinate, TerrainType terrainType){
        this.coordinate = coordinate;
        this.terrainType = terrainType;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
