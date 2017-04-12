package Tigerisland.AIHelpers;

import Tigerisland.Coordinate;
import Tigerisland.TerrainType;

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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
