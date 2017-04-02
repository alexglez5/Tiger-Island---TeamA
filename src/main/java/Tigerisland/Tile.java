package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Tile {
    private static int numOfTilesCreated = 0;
    private int tileID;
<<<<<<< HEAD
    private final Hex mainTerrain = new Hex(TerrainType.Volcano);
=======
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
    private Hex leftOfMainTerrain;
    private final Hex mainTerrain = new Hex(TerrainType.VOLCANO, tileID);
    private Hex rightOfMainTerrain;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType) {
<<<<<<< HEAD
        leftOfMainTerrain = new Hex(leftTerrainType);
        rightOfMainTerrain = new Hex(rightTerrainType);
=======
        numOfTilesCreated++;
        tileID = numOfTilesCreated;

        leftOfMainTerrain = new Hex(leftTerrainType, tileID);
        rightOfMainTerrain = new Hex(rightTerrainType, tileID);
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
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

    public void setTileID(int tileID) {
        this.tileID = tileID;
        leftOfMainTerrain.setTileID(tileID);
        mainTerrain.setTileID(tileID);
        rightOfMainTerrain.setTileID(tileID);
    }

    public static void setNumOfTilesCreated(int number) {
        numOfTilesCreated = number;
    }
}
