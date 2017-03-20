package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Tile {
    private int tileID;
    private Hex leftOfMainTerrain;
    private final Hex mainTerrain = new Hex(TerrainType.Volcano);
    private Hex rightOfMainTerrain;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType, int tileID){
        leftOfMainTerrain = new Hex(leftTerrainType);
        rightOfMainTerrain = new Hex(rightTerrainType);
        this.tileID = tileID;
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

    public void setTileID(int tileID) {this.tileID = tileID;    }

    public int getID() {
        return tileID;
    }
}
