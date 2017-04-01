package Tigerisland;

public class Tile {
    private static int numberOfCreatedTiles = 1;
    private int tileID;
    private Hex leftOfMainTerrain;
    private final Hex mainTerrain = new Hex(TerrainType.VOLCANO, tileID);
    private Hex rightOfMainTerrain;
    private int orientation;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType){
        leftOfMainTerrain = new Hex(leftTerrainType, tileID);
        rightOfMainTerrain = new Hex(rightTerrainType, tileID);

        numberOfCreatedTiles++;
        tileID = numberOfCreatedTiles;
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

    public void setTileID(int tileID) { this.tileID = tileID; }

    public int getTileID() {return tileID; }

    public void rotateTileClockwise() {

    }
}
