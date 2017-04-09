package Tigerisland.AIHelpers;

import Tigerisland.Coordinate;
import Tigerisland.Orientation;
import Tigerisland.TerrainType;

/**
 * Created by Alexander Gonzalez on 4/9/2017.
 */
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

    public void setLeftTerrainType(TerrainType leftTerrainType) {
        this.leftTerrainType = leftTerrainType;
    }

    public TerrainType getRightTerrainType() {
        return rightTerrainType;
    }

    public void setRightTerrainType(TerrainType rightTerrainType) {
        this.rightTerrainType = rightTerrainType;
    }

    public Coordinate getMainTerrainCoordinate() {
        return mainTerrainCoordinate;
    }

    public void setMainTerrainCoordinate(Coordinate mainTerrainCoordinate) {
        this.mainTerrainCoordinate = mainTerrainCoordinate;
    }

    public Orientation getOrientattion() {
        return orientattion;
    }

    public void setOrientattion(Orientation orientattion) {
        this.orientattion = orientattion;
    }
}
