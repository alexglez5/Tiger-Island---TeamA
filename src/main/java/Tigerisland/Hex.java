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
}
