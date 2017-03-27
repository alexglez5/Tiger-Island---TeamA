package Tigerisland;

public class Tile {
    private int tileID;
    private Hex leftOfMainTerrain;
    private final Hex mainTerrain = new Hex(TerrainType.Volcano, tileID);
    private Hex rightOfMainTerrain;
    private Orientation orientation;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType, int tileID){
        leftOfMainTerrain = new Hex(leftTerrainType, tileID);
        rightOfMainTerrain = new Hex(rightTerrainType, tileID);
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

    public int getTileID() {return tileID;     }

    public void rotateTileClockwise() {

    }
}
