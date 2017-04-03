package Tigerisland;

public class Tile {
    private static int numberOfCreatedTiles = 1;
    private int tileID;
    private Hex leftOfMainTerrain;
    private Hex mainTerrain;
    private Hex rightOfMainTerrain;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType){
        numberOfCreatedTiles++;
        tileID = numberOfCreatedTiles;
        leftOfMainTerrain = new Hex(leftTerrainType, tileID);
        rightOfMainTerrain = new Hex(rightTerrainType, tileID);
        mainTerrain = new Hex(TerrainType.VOLCANO, tileID);
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

    public void resetNumberOfTilesCreated() {
        numberOfCreatedTiles = 1;
    }

    public static int getNumberOfTilesCreated() {
        return numberOfCreatedTiles;
    }
}
