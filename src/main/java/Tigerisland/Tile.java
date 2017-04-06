package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Tile {
    private static int numOfTilesCreated = 0;
    private int tileID;
    private Hex mainTerrain;
    private Hex leftOfMainTerrain;
    private Hex rightOfMainTerrain;

    public Tile(TerrainType leftTerrainType, TerrainType rightTerrainType) {
        numOfTilesCreated++;
        tileID = numOfTilesCreated;
        mainTerrain = new Hex(TerrainType.Volcano, tileID);
        leftOfMainTerrain = new Hex(leftTerrainType, tileID);
        rightOfMainTerrain = new Hex(rightTerrainType, tileID);
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

    public static void setNumOfTilesCreated(int number) {
        numOfTilesCreated = number;
    }
}
