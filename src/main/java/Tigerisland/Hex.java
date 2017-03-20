package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Hex {
    private TerrainType terrainType;

    public Hex(TerrainType terrainType){
        this.terrainType = terrainType;
    }

    public TerrainType getTerrainType(){
        return terrainType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hex hex = (Hex) o;

        return terrainType == hex.terrainType;
    }

    @Override
    public int hashCode() {
        return terrainType != null ? terrainType.hashCode() : 0;
    }
}
