package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Tile {
    private static int numOfTilesCreated = 0;
    private long tileID;
    private final Hex mainTerrain = new Hex(TerrainType.Volcano);
    private Hex leftOfMainTerrain;
    private Hex rightOfMainTerrain;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType) {
        leftOfMainTerrain = new Hex(leftTerrainType);
        rightOfMainTerrain = new Hex(rightTerrainType);
        numOfTilesCreated++;
    }

    public Hex getLeftOfMainTerrain() {
        return leftOfMainTerrain;
    }

    public Hex getMainTerrain() {
        return mainTerrain;
    }

    public Hex getRightOfMainTerrain() {
        return rightOfMainTerrain;
    }

    public void setTileID(long tileID) {
        this.tileID = tileID;
        leftOfMainTerrain.setTileID(tileID);
        mainTerrain.setTileID(tileID);
        rightOfMainTerrain.setTileID(tileID);
    }

    public static void setNumOfTilesCreated(int number) {
        numOfTilesCreated = number;
    }
}
