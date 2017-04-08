package Tigerisland;

public class Hex {
    private TerrainType terrainType;
    private boolean hasVillagers;
    private boolean hasTotoro;
    private boolean hasTiger;
    private int settlementID;
    private int playerID;
    private int tileID;
    private int level;

    public Hex(TerrainType terrainType, int tileID){
        this.terrainType = terrainType;
        this.tileID = tileID;
        level = 1;
    }

    public void placeVillagers() {
        this.hasVillagers = true;
    }

    public void placeTotoro() {
        this.hasTotoro = true;
    }

    public void placeTiger() {
        this.hasTiger = true;
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

    public int getPlayerID() { return playerID; }

    public void setPlayerID(int pid) { playerID = pid; }

    public void setTileID(int tileID){
        this.tileID = tileID;
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

    public void setLevel(int level) {
        this.level = level;
    }

}
