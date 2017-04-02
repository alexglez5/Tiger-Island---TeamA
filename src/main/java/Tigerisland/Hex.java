package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Hex {
    private TerrainType terrainType;
    private boolean hasVillagers;
    private boolean hasTotoro;
    private boolean hasTiger;
    private int settlementID;
    private long tileID;
    private int level;


    public Hex(TerrainType terrainType) {
        this.terrainType = terrainType;
        level =1;
    }

    public Hex(TerrainType terrainType, long tileID){
        this.terrainType = terrainType;
        this.tileID = tileID;
        level = 1;
    }

    public void placeVillagers() {
        hasVillagers = true;
    }

    public void placeTotoro() {
        hasTotoro = true;
    }

    public void placeTiger() {
        hasTiger = true;
    }

    public boolean hasVillager() {
        return hasVillagers;
    }

    public boolean hasTotoro() {
        return hasTotoro;
    }

    public boolean hasTiger() {
        return hasTiger;
    }

    public int getSettlementID() {
        return settlementID;
    }

    public void setSettlementID(int settlementID) {
        this.settlementID = settlementID;
    }

    public void setTileID(long tileID){
        this.tileID = tileID;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public long getTileID() {
        return tileID;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public void increaseLevel() {
        this.level++;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        return terrainType != null ? terrainType.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hex hex = (Hex) o;

        return terrainType == hex.terrainType;
    }
}
