package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Hex {
    private TerrainType terrainType;
    private boolean hasVillager;
    private boolean hasTotoro;
    private int tileID;
    private int level;

    public Hex(TerrainType terrainType, int tileID){
        this.terrainType = terrainType;
        this.tileID = tileID;
        level = 1;
    }

    public void placeVillager(){
        hasVillager = true;
    }

    public void placeTotoro(){
        hasTotoro = true;
    }

    public boolean hasVillager(){
        return hasVillager;
    }

    public boolean hasTotoro(){
        return hasTotoro;
    }

    public TerrainType getTerrainType(){
        return terrainType;
    }

    public int getTileID() {
        return tileID;
    }

    public void increaseLevel(){
        level++;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hex hex = (Hex) o;

        return terrainType == hex.terrainType;
    }

    @Override
    public int hashCode() {
        return terrainType != null ? terrainType.hashCode() : 0;
    }
}
