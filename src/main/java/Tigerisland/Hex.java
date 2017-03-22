package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Hex {
    private TerrainType terrainType;
    private boolean hasVillagers;
    private boolean hasTotoro;
    private int settlementID;
    private int tileID;
    private int level;

    public Hex(TerrainType terrainType, int tileID){
        this.terrainType = terrainType;
        this.tileID = tileID;
        level = 1;
    }

    public void placeVillagers(){
        hasVillagers = true;
    }

    public void placeTotoro(){
        hasTotoro = true;
    }

    public boolean hasVillager(){
        return hasVillagers;
    }

    public boolean hasTotoro(){
        return hasTotoro;
    }

    public int getSettlementID(){
        return settlementID;
    }

    public void setSettlementID(int settlementID){
        this.settlementID = settlementID;
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


}
