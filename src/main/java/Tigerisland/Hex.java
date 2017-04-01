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
    private int tileID;
    private int level;

    public Hex(TerrainType terrainType, int tileID) {
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

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public int getTileID() {
        return tileID;
    }

    public void increaseLevel() {
        level++;
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
