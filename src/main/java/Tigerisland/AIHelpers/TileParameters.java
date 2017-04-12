package Tigerisland.AIHelpers;

import Tigerisland.Coordinate;
import Tigerisland.Orientation;
import Tigerisland.TerrainType;

public class TileParameters {
    private TerrainType leftTerrainType;
    private TerrainType rightTerrainType;
    private Coordinate mainTerrainCoordinate;
    private Orientation orientattion;

    public TileParameters(
            TerrainType leftTerrainType,
            TerrainType rightTerrainType,
            Coordinate mainTerrainCoordinate,
            Orientation orientattion){
        this.leftTerrainType = leftTerrainType;
        this.rightTerrainType = rightTerrainType;
        this.mainTerrainCoordinate = mainTerrainCoordinate;
        this.orientattion = orientattion;
    }


    public TerrainType getLeftTerrainType() {
        return leftTerrainType;
    }

    public TerrainType getRightTerrainType() {
        return rightTerrainType;
    }

    public Coordinate getMainTerrainCoordinate() {
        return mainTerrainCoordinate;
    }

    public Orientation getOrientattion() {
        return orientattion;
    }
}
