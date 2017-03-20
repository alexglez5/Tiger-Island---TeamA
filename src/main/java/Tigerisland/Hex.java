package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Hex {
    TerrainType terrainType;

    public Hex(TerrainType terrainType){
        this.terrainType = terrainType;
    }

    public boolean isEmpty() {
        return terrainType != null; //TerrainType.Volcano;
    }
}
