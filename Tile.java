/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Tile {
    private Hex leftOfMainTerrain;
    private final Hex mainTerrain = new Hex(TerrainType.Volcano);
    private Hex rightOfMainTerrain;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType){
        leftOfMainTerrain = new Hex(leftTerrainType);
        rightOfMainTerrain = new Hex(rightTerrainType);
    }

    public Hex getLeftOfMainTerrain(){
        return leftOfMainTerrain;
    }

    public Hex getMainTerrain(){
        return mainTerrain;
    }

    public Hex getRightOfMainTerrain(){
        return rightOfMainTerrain;
    }
}
